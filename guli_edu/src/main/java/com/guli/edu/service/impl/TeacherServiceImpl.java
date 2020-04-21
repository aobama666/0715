package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Teacher;
import com.guli.edu.entity.vo.QueryTeacher;
import com.guli.edu.mapper.TeacherMapper;
import com.guli.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author huaan
 * @since 2019-12-04
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Boolean removeTeacherById(String id) {
        //执行影响行数
        int i = baseMapper.deleteById(id);
        return i != 0;
    }

    @Override
    public void teacherByPage(Page<Teacher> pageParam, QueryTeacher queryTeacher) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        //如果没有条件，就只做简单的查询
        if(queryTeacher == null){
            baseMapper.selectPage(pageParam, wrapper);
            return ;
        }
        //如果有条件，获取条件参数，把条件加到wrapper中
        String name = queryTeacher.getName();
        Integer level = queryTeacher.getLevel();
        String begin = queryTeacher.getBegin();
        String end = queryTeacher.getEnd();

        //判断各参数是否有值，没有值，不管了；有值，把值加在wrapper中
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create", end);

        }
        baseMapper.selectPage(pageParam, wrapper);
    }
}
