package com.guli.edu.controller;


import com.guli.common.entity.Result;
import com.guli.edu.entity.Subject;
import com.guli.edu.entity.vo.SubjectOne;
import com.guli.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author huaan
 * @since 2019-12-04
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * Excl表格课程的导入功能
     * 把excl文件上传到服务器上，解析这个Excl
     */
    @PostMapping("import")
    public Result importExcl(MultipartFile file){
        List<String> messageList = subjectService.importExcl(file);
        if(messageList.size() != 0){
            return Result.error().data("messageList", messageList);
        }
        return Result.ok();
    }

    /**
     * 课程分类的集合Tree
     * 1、List<Map<String, Object>> ——> map.put("id":); map.put("title",);map.put("childen",List<Map<String,Object>>);
     * 2、List<SubjectOne>：subjectOne:id,title,List<SubjectTwo> childen;  SubjectTwo:id;title
     * 作业：Vo对象的实现了；把Map封装的也实现一下；
     */
    @GetMapping("getTreeList")
    public Result getTreeList(){
        List<SubjectOne> list = subjectService.getTreeList();
            return Result.ok().data("list", list);
    }

    /**
     * 删除
     */
    @DeleteMapping("removeById/{id}")
    public Result removeById(@PathVariable("id") String id){
        //subjectService.removeById(id);
        subjectService.removeTreeById(id);
        return Result.ok();
    }


    /**
     * 保存
     */
    @PostMapping("save")
    public  Result save(@RequestBody Subject subject){
        subjectService.saveSubject(subject);
        return Result.ok();
    }

    /**
     * 根据ParentId获取分类列表
     */
    @GetMapping("getSubjectListByParentId/{parentId}")
    public Result getSubjectListByParentId(
            @PathVariable("parentId") String parentId){
        List<Subject> list = subjectService.getSubjectListByParentId(parentId);
        return Result.ok().data("list", list);
    }

}

