package com.yum.oa.common.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一事务处理配置
 * @description
 * @author: gaoyu
 * @create: 2020-08-07
 * @version: 0.0.1
 **/
@Configuration
@Aspect
public class TransactionAdviceConfig {
    private static final int TX_METHOD_TIME_OUT = 5;
    private static final String POINTCUT_EXPRESSION = "execution(* com.yum.oa.service.*.*.*(..))";

    @Resource
    private TransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {

        NameMatchTransactionAttributeSource attributeSource = new NameMatchTransactionAttributeSource();
        Map<String, TransactionAttribute> attributeMap = new HashMap<>(16);
        /*事务管理规则*/
        RuleBasedTransactionAttribute readOnlyRule = new RuleBasedTransactionAttribute();
        /*设置当前事务是否为只读事务，true为只读*/
        readOnlyRule.setReadOnly(true);
        /* transactiondefinition 定义事务的隔离级别；
         *  PROPAGATION_REQUIRED 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中*/
        readOnlyRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        RuleBasedTransactionAttribute requireRule = new RuleBasedTransactionAttribute();
        /*抛出异常后执行切点回滚*/
        requireRule.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        /*PROPAGATION_REQUIRED:事务隔离性为1，若当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值。 */
        requireRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        /*设置事务失效时间，超过5秒,可根据hytrix，则回滚事务*/
        requireRule.setTimeout(TX_METHOD_TIME_OUT);

        attributeMap.put("add*", requireRule);
        attributeMap.put("save*", requireRule);
        attributeMap.put("insert*", requireRule);
        attributeMap.put("update*", requireRule);
        attributeMap.put("delete*", requireRule);
        attributeMap.put("remove*", requireRule);
        /*进行查询操作时*/
        attributeMap.put("get*", readOnlyRule);
        attributeMap.put("query*", readOnlyRule);
        attributeMap.put("find*", readOnlyRule);
        attributeMap.put("select*", readOnlyRule);
        attributeMap.put("count*", readOnlyRule);
        attributeSource.setNameMap(attributeMap);
        return new TransactionInterceptor(transactionManager, attributeSource);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
