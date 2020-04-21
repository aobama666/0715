package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.CourseInfo;
import com.guli.edu.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author huaan
 * @since 2019-12-04
 */
public interface CourseService extends IService<Course> {

    /**
     * 添加课程信息
     * @param courseInfo
     */
    String saveCourseInfo(CourseInfo courseInfo);

    /**
     * 根据条件分页查询课程列表
     * @param pageParam
     * @param courseQuery
     */
    void getPageByQuery(Page<Course> pageParam, CourseQuery courseQuery);

    /**
     * 根据课程Id获取课程的详细信息
     * @param courseId
     * @return
     */
    CourseInfo getCourseInfoById(String courseId);

    /**
     * 修改课程详细信息
     * @param courseInfo
     */
    void updateCourseInfo(CourseInfo courseInfo);

    /**
     * 根据 课程Id获取最终发布课程信息
     * @param courseId
     * @return
     */
    Map<String,Object> getPublishInfoByCourseId(String courseId);

    /**
     * 根据讲师ID查询课程集合
     * @param teacherId
     * @return
     */
    List<Course> getCourseListByTeacherId(String teacherId);

    /**
     * 根据课程Id获取课程详情页面数据
     * @param courseId
     * @return
     */
    Map<String,Object> getCourseInfoWebByCourseId(String courseId);
}
