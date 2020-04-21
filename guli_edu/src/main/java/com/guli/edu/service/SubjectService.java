package com.guli.edu.service;

import com.guli.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.SubjectOne;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author huaan
 * @since 2019-12-04
 */
public interface SubjectService extends IService<Subject> {

    /**
     * 上传Excl导入课程分类
     * @param file
     * @return
     */
    List<String> importExcl(MultipartFile file);

    /**
     * 获取课程分类列表
     * @return
     */
    List<SubjectOne> getTreeList();

    /**
     * 根据课程分类的Id删除所关联的所有的ID数据
     * @param id
     */
    void removeTreeById(String id);

    /**
     * 保存课程分类
     * @param subject
     */
    void saveSubject(Subject subject);

    /**
     * 根据ParentId获取课程分类列表
     * @param parentId
     * @return
     */
    List<Subject> getSubjectListByParentId(String parentId);
}
