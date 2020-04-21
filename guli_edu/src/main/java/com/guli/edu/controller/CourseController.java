package com.guli.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.entity.Result;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.Teacher;
import com.guli.edu.entity.vo.CourseInfo;
import com.guli.edu.entity.vo.CourseQuery;
import com.guli.edu.service.ChapterService;
import com.guli.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author huaan
 * @since 2019-12-04
 */
@RestController
@CrossOrigin
@RequestMapping("/edu/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 添加课程
     */
    @PostMapping("save")
    public Result save(@RequestBody CourseInfo courseInfo){
        String courseId = courseService.saveCourseInfo(courseInfo);
        return Result.ok().data("courseId", courseId);
    }

    /**
     * 有条件的分页查询
     */
    @PostMapping("page/{page}/{limit}")
    public Result page(
            @PathVariable("page") Integer page,
            @PathVariable("limit") Integer limit,
            @RequestBody CourseQuery courseQuery){
        Page<Course> pageParam = new Page<>(page, limit);
        courseService.getPageByQuery(pageParam, courseQuery);
        return Result.ok()
                .data("total", pageParam.getTotal())
                .data("rows", pageParam.getRecords());
    }

    /**
     * 根据课程Id获取课程的详细信息
     */
    @GetMapping("getCourseInfoById/{courseId}")
    public Result getCourseInfoById(@PathVariable("courseId") String courseId){
        CourseInfo courseInfo = courseService.getCourseInfoById(courseId);
        return Result.ok().data("courseInfo", courseInfo);
    }

    /**
     * 修改
     */
    @PostMapping("update")
    public Result updateCourseInfo(@RequestBody CourseInfo courseInfo){
        courseService.updateCourseInfo(courseInfo);
        return Result.ok();
    }

    /**
     * 根据课程的Id，获取课程最终发布信息
     */
    @GetMapping("publish/{courseId}")
    public Result getPublishInfoByCourseId(@PathVariable("courseId") String courseId){
       Map<String, Object> map = courseService.getPublishInfoByCourseId(courseId);
       return Result.ok().data(map);
    }

    /**
     * 前端工程分页获取课程列表
     */
    @GetMapping("pageWeb/{page}/{limit}")
    public Result getPageListWeb(
            @PathVariable("page") Integer page,
            @PathVariable("limit") Integer limit){
        Page<Course> pageParam = new Page<>(page, limit);
        courseService.page(pageParam,null);
        Map<String, Object> map = new HashMap<>();
        map.put("items", pageParam.getRecords());
        map.put("total", pageParam.getTotal());
        map.put("hasPrevious", pageParam.hasPrevious());
        map.put("hasNext", pageParam.hasNext());
        map.put("pages", pageParam.getPages());
        map.put("current", pageParam.getCurrent());

        return Result.ok().data(map);
    }

    @Autowired
    private ChapterService chapterService;

    /**
     * 根据课程Id查询课程的详情页面信息
     */
    @GetMapping("courseInfoWeb/{courseId}")
    public Result getCourseInfoWebById(@PathVariable("courseId") String courseId){
        Map<String, Object> map = courseService.getCourseInfoWebByCourseId(courseId);
        //这个Map中有课程基本信息、描述信息、讲师信息、课程分类
        List<Map<String, Object>> chapterList = chapterService.getChapterListByCourseId(courseId);
        //如果有课程评价：那么注入课程评价的Service.getListByCourseId,返回集合

        return Result.ok()
                .data("dataList", map)
                .data("chapterList", chapterList);
    }


}

