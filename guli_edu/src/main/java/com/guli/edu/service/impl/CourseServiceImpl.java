package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.CourseDescription;
import com.guli.edu.entity.vo.CourseInfo;
import com.guli.edu.entity.vo.CourseQuery;
import com.guli.edu.mapper.CourseMapper;
import com.guli.edu.service.CourseDescriptionService;
import com.guli.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author huaan
 * @since 2019-12-04
 * 事务中包含事务
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Override
    public String saveCourseInfo(CourseInfo courseInfo) {
        //1、先插入课程基本信息
        baseMapper.insert(courseInfo.getCourse());
        //2、获取Id
        String courseId = courseInfo.getCourse().getId();
        //3、设置描述表中的id
        courseInfo.getCourseDescription().setId(courseId);
        //4、插入课程描述对象
        courseDescriptionService.save(courseInfo.getCourseDescription());

        return courseId;
    }

    @Override
    public void getPageByQuery(Page<Course> pageParam, CourseQuery courseQuery) {

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        if(courseQuery == null){
            baseMapper.selectPage(pageParam, wrapper);
            return ;
        }
        //获取条件
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title", title);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id", teacherId);
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id", subjectId);
        }
        baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public CourseInfo getCourseInfoById(String courseId) {
        CourseInfo courseInfo = new CourseInfo();
        //1、获取Course
        Course course = baseMapper.selectById(courseId);
        courseInfo.setCourse(course);
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfo.setCourseDescription(courseDescription);

        return courseInfo;
    }

    @Override
    public void updateCourseInfo(CourseInfo courseInfo) {
        //修改Course表
        baseMapper.updateById(courseInfo.getCourse());
        //修改CourseDescription
        courseDescriptionService.updateById(courseInfo.getCourseDescription());
    }

    @Override
    public Map<String, Object> getPublishInfoByCourseId(String courseId) {
        //获取数据有两种方式：咱们使用最简单的方式
        //1、根据每一个关联的表获取数据放在Map中，那么需要查询三个表
        //2、自己写SQL语句关联三个表，获取最终数据
        return baseMapper.getPublishInfoByCourseId(courseId);
    }

    @Override
    public List<Course> getCourseListByTeacherId(String teacherId) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId );
        List<Course> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }

    @Override
    public Map<String, Object> getCourseInfoWebByCourseId(String courseId) {
        return baseMapper.getCourseInfoWebByCourseId(courseId);
    }
}
