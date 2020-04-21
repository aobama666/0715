package com.guli.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.entity.Result;
import com.guli.common.exception.GuliException;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.Teacher;
import com.guli.edu.entity.vo.QueryTeacher;
import com.guli.edu.service.CourseService;
import com.guli.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 *
 * 做项目主要是思维逻辑、只要逻辑走通了、代码慢慢写
 * 为什么让你们预习；
 *
 * </p>
 *
 * @author huaan
 * @since 2019-12-04
 */
@RestController
@CrossOrigin //解决跨域的！ 因为前段js异步请求另一个服务器的后台接口那么就造成了跨域
@RequestMapping("/edu/teacher")
@Api(description = "讲师管理")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 讲师列表显示
     */
    @GetMapping("list")
    @ApiOperation(value = "讲师列表查询")
    public Result list(

            @ApiParam(value = "讲师编号", name = "id") String id){

        //int i = 1/0;

        List<Teacher> teacherList = teacherService.list(null);
        //return teacherList;
        return Result.ok().data("rows", teacherList);
    }

    /**
     * 讲师删除
     */
    @ApiOperation(value = "根据Id删除讲师信息")
    @DeleteMapping("removeById/{id}")
    public Result removeById(
            @ApiParam(name = "id", value = "讲师编号", required = true)
            @PathVariable("id") String id){

        try {
            //boolean b = teacherService.removeById(id);
            //自己写一个方法
            Boolean b = teacherService.removeTeacherById(id);
            if(b){
                return Result.ok();
            } else {
                throw new RuntimeException("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "讲师新增")
    @PostMapping("save")
    public Result save(@RequestBody Teacher teacher){
        teacherService.save(teacher);
        return Result.ok();
    }

    @ApiOperation(value = "根据Id查询讲师基本信息")
    @GetMapping("{id}")
    public Result getTeacherById(@PathVariable("id") String id){
        Teacher teacher = teacherService.getById(id);
        if(teacher == null){
            throw new GuliException(20002,"没有找到此讲师信息");
        }
        return Result.ok().data("item", teacher);
    }

    @ApiOperation(value = "修改讲师信息")
    @PutMapping("update")
    public Result updateById(@RequestBody Teacher teacher){
        teacherService.updateById(teacher);
        return Result.ok();
    }

    /**
     * 分页查询的话、传递什么参数？
     * 当前页、每页显示记录数
     * @return
     */
    @ApiOperation(value = "讲师分页查询无条件的")
    @GetMapping("{page}/{limit}")
    public Result teacherByPage(
            @ApiParam(name = "page", value = "当前页数")
            @PathVariable("page") Integer page,
            @ApiParam(name = "limit", value = "每页显示的记录数")
            @PathVariable("limit") Integer limit){
        Page<Teacher> pageParam = new Page<>(page, limit);
        teacherService.page(pageParam, null);
        //返回什么数据json中包含什么数据：total，总记录数， rows : pageParam.getRecords()
        return Result.ok()
                .data("total", pageParam.getTotal())
                .data("rows", pageParam.getRecords());
    }

    /**
     * 根据条件分页查询
     */
    @ApiOperation(value = "根据条件分页查询")
    @PostMapping("page/{page}/{limit}")
    public Result page(
            @ApiParam(name = "page", value = "当前页数")
            @PathVariable("page") Integer page,
            @ApiParam(name = "limit", value = "每页显示的记录数")
            @PathVariable("limit") Integer limit,
            @ApiParam(name = "queryTeacher", value = "分页的条件")
            @RequestBody QueryTeacher queryTeacher){
        Page<Teacher> pageParam = new Page<>(page, limit);
        teacherService.teacherByPage(pageParam,queryTeacher);
        //返回的数据一致
        return Result.ok()
                .data("total", pageParam.getTotal())
                .data("rows", pageParam.getRecords());
    }

    /**
     * 获取前端工程页面的讲师列表
     */
    @GetMapping("pageWeb/{page}/{limit}")
    public Result getPageListWeb(
            @PathVariable("page") Integer page,
            @PathVariable("limit") Integer limit){
        Page<Teacher> pageParam = new Page<>(page, limit);
        teacherService.page(pageParam,null);
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
    private CourseService courseService;

    /**
     * 根据讲师的ID，获取讲师的基本信息和讲师课程列表
     */
    @GetMapping("getListById/{teacherId}")
    public Result getListById(@PathVariable("teacherId") String teacherId){
        //1、讲师的信息
        Teacher teacher = teacherService.getById(teacherId);
        //2、讲师的课程列表
        List<Course> courseList = courseService.getCourseListByTeacherId(teacherId);

        return Result.ok()
                .data("teacher", teacher)
                .data("courseList", courseList);
    }

}

