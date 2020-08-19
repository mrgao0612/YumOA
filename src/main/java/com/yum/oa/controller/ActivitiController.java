package com.yum.oa.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.yum.oa.common.result.ResultBean;
import com.yum.oa.common.result.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author gaoyu
 * @version v
 * @description
 * @date 2020/8/19
 **/
@RestController
@RequestMapping("/api/activiti")
public class ActivitiController {

    @Resource
    private RepositoryService repositoryService;

    @ApiOperation("获取模型列表")
    @GetMapping("/modelList")
    public ResultBean<PageInfo<Model>> search(@RequestParam(required = false) String name,
                                              @RequestParam Integer pageNum,
                                              @RequestParam Integer pageSize) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        if (StringUtils.isNotBlank(name)) {
            modelQuery.modelNameLike("%" + name + "%");
        }
        modelQuery.orderByCreateTime().desc();
        long total = modelQuery.count();
        List<Model> models = modelQuery.listPage(pageNum - 1, pageSize);
        PageInfo<Model> pageInfo = PageInfo.of(models);
        pageInfo.setTotal(total);
        return ResultGenerator.success(pageInfo);
    }

    @ApiOperation("创建模型")
    @PostMapping("/save")
    public ResultBean<String> save(@RequestBody Map<String, String> param) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace",
                    "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.putPOJO("stencilset", stencilSetNode);

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, param.get("name"));
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, param.get("desc"));
            Model modelData = repositoryService.newModel();
            if (StringUtils.isNotBlank(param.get("id"))) {
                modelData = repositoryService.getModel(param.get("id"));
            }
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(param.get("name"));
            modelData.setKey(param.get("key"));

            //保存模型
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new ActivitiException("创建流程模型失败", e);
        }
        return ResultGenerator.success("创建失败");
    }

    @ApiOperation("根据Id查询模型")
    @GetMapping("/{id}")
    public ResultBean<Model> getById(@PathVariable("id") String id) {
        Model model = repositoryService.createModelQuery().modelId(id).singleResult();
        return ResultGenerator.success(model);
    }

    @DeleteMapping("/{id}")
    public ResultBean<String> delete(@PathVariable("id") String id) {
        Model model = repositoryService.createModelQuery().modelId(id).singleResult();
        if (model == null) {
            return ResultGenerator.failed("模型不存在");
        }
        repositoryService.deleteModel(id);
        return ResultGenerator.success("删除成功");
    }

    @ApiOperation("部署模型")
    @GetMapping("/{modelId}/deployment")
    public ResultBean<String> deploy(@PathVariable("modelId") String modelId) {

        // 获取模型
        Model modelData = repositoryService.getModel(modelId);

        if (modelData == null) {
            return ResultGenerator.failed("模型不存在");
        }

        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            return ResultGenerator.failed("请先设计流程定义并成功保存，再进行部署");
        }

        try {
            JsonNode modelNode = new ObjectMapper().readTree(bytes);
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            if (model.getProcesses().size() == 0) {
                return ResultGenerator.failed("流程定义不符要求，请至少设计一条主线流程");
            }
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
            //发布流程
            String processName = modelData.getKey() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .key(modelData.getKey())
                    .category(modelData.getCategory())
                    .addString(processName, new String(bpmnBytes, StandardCharsets.UTF_8))
                    .enableDuplicateFiltering() // 在部署时会检测已部署的相同文件的最后一条记录，如果内容相同，则不会部署
                    .deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.success("部署成功");
    }
}
