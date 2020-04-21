package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.QueryTeacher;

/**
 * <p>
 * 讲师 服务类
 * 好习惯：在接口方法上加注释
 * </p>
 *
 * @author huaan
 * @since 2019-12-04
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 根据Id删除讲师信息
     * @param id
     * @return
     */
    Boolean removeTeacherById(String id);

    /**
     * 根据条件分页查询
     * @param pageParam
     * @param queryTeacher
     */
    void teacherByPage(Page<Teacher> pageParam, QueryTeacher queryTeacher);
}
