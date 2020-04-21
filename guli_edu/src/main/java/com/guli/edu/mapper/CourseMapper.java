package com.guli.edu.mapper;

import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author huaan
 * @since 2019-12-04
 */
public interface CourseMapper extends BaseMapper<Course> {

    Map<String,Object> getPublishInfoByCourseId(String courseId);

    Map<String,Object> getCourseInfoWebByCourseId(String courseId);

}
