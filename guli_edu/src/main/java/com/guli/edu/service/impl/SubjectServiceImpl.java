package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.exception.GuliException;
import com.guli.edu.entity.Subject;
import com.guli.edu.entity.vo.SubjectOne;
import com.guli.edu.entity.vo.SubjectTwo;
import com.guli.edu.mapper.SubjectMapper;
import com.guli.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author huaan
 * @since 2019-12-04
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    /**
     * 注意：重点
     * @param file
     * @return
     */
    @Override
    public List<String> importExcl(MultipartFile file) {

        List<String> messageList = new ArrayList<>();

        try {
            //1、获取文件流
            InputStream inputStream = file.getInputStream();
            //2、根据这个流创建一个WorkBook对象
            Workbook workbook = new HSSFWorkbook(inputStream);
            //3、获取第0个Sheet
            Sheet sheet = workbook.getSheetAt(0);
            //4、获取最后一行
            int rowNum = sheet.getLastRowNum();
            //5、判断最后一行是否为0
            if(rowNum < 1){

                messageList.add("没有分类数据");
                return messageList;
            }
            //6、不为0就说明有分类的数据
            //7、遍历行：获取分类的数据
            for(int row = 1; row <= rowNum; row++){
                //8、获取第一行
                Row row1 = sheet.getRow(row);

                //9、判断行是否存在
                if(row1 != null){
                    //10、如果存在、获取列
                    Cell cell = row1.getCell(0);
                    //11、判断列是否存在
                    if(cell == null){
                        messageList.add("第"+(row+1)+"行第1列为空");
                        continue;
                    }
                    String stringCellValue = null;
                    if(cell != null){
                        //12、如果存在获取值
                        stringCellValue = cell.getStringCellValue();
                        //13、判断值是否存在
                        if(StringUtils.isEmpty(stringCellValue)){
                            messageList.add("第"+(row+1)+"行第1列为空");
                            continue;
                        }
                    }
                    //14、如果存在：根据一级分类名称和parentId = 0 查询数据库是否存在这个分类名称
                    Subject subject = this.getSubjectByTitleAndParentId(stringCellValue, "0");
                    //15、如果存在获取Id
                    String subjectOneId = null;
                    //16、如果不存在：添加一级分类；获取Id
                    if(subject == null){
                        subject = new Subject();
                        subject.setTitle(stringCellValue);
                        subject.setSort(row);
                        subject.setParentId("0");
                        baseMapper.insert(subject);
                        subjectOneId = subject.getId();
                    } else{
                        subjectOneId = subject.getId();
                    }
                    //17、获取第二列
                    Cell cell1 = row1.getCell(1);
                    //18、判断第二列是否存在
                    if(cell1 == null){
                        messageList.add("第"+(row+1)+"行第2列为空");
                        continue;
                    }
                    //19、如果存在获取第二列的值
                    String cellValue = null;
                    if(cell1 != null){
                        cellValue = cell1.getStringCellValue();
                        //20、判断值是否存在；
                        if(StringUtils.isEmpty(cellValue)){
                            messageList.add("第"+(row+1)+"行第2列为空");
                            continue;
                        }
                    }
                    //21、如果存在：根据第二级分类的名称和刚添加进去的一级分类Id为ParentId 查询是否有此二级分类名称存在
                    Subject subjectTwo = this.getSubjectByTitleAndParentId(cellValue, subjectOneId);
                    //22、如果不存在直接添加
                    if(subjectTwo == null){
                        subjectTwo = new Subject();
                        subjectTwo.setTitle(cellValue);
                        subjectTwo.setSort(row);
                        subjectTwo.setParentId(subjectOneId);
                        baseMapper.insert(subjectTwo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messageList;
    }

    @Override
    public List<SubjectOne> getTreeList() {

        List<SubjectOne> subjectOneList = new ArrayList<>();
        //1、先获取一级分类：集合
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        wrapper.orderByAsc("sort");
        List<Subject> subjectList = baseMapper.selectList(wrapper);
        //2、遍历每一个一级分类
        for(Subject subject : subjectList){
            //3、获取每一个一级分类下的二级分类集合
            SubjectOne subjectOne = new SubjectOne();
            BeanUtils.copyProperties(subject, subjectOne);
            //4、把每一个二级分类数据添加到一级分类的children中
            QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", subjectOne.getId());
            wrapper.orderByAsc("sort");
            List<Subject> list = baseMapper.selectList(queryWrapper);
            for(Subject su : list){
                SubjectTwo subjectTwo = new SubjectTwo();
                BeanUtils.copyProperties(su, subjectTwo);
                subjectOne.getChildren().add(subjectTwo);
            }
            //5、把每一个一级分类添加到总集合中
            subjectOneList.add(subjectOne);
        }

        return subjectOneList;
    }

    @Override
    public void removeTreeById(String id) {

        List<String> ids = new ArrayList<>();
        ids.add(id);
        //还有子节点/孙子节点
        this.getIds(ids, id);
        //删除多个Id
        baseMapper.deleteBatchIds(ids);
    }

    @Override
    public void saveSubject(Subject subject) {
        //1、首先要考虑是否是一级分类
        //2、判断此课程分类是否存在
        //3、添加
        Subject su = this.getSubjectByTitleAndParentId(subject.getTitle(), subject.getParentId());
        if(su == null){
            baseMapper.insert(subject);
        } else {
            throw new GuliException(20002,"此课程分类已存在");
        }
    }

    @Override
    public List<Subject> getSubjectListByParentId(String parentId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        wrapper.orderByAsc("sort");
        List<Subject> subjectList = baseMapper.selectList(wrapper);
        return subjectList;
    }

    /**
     * 递归删除
     * @param ids
     * @param id
     */
    private void getIds(List<String> ids, String id) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        //根据要删除的课程分类的Id，作为ParentId查询子节点列表
        List<Subject> subjectList = baseMapper.selectList(wrapper);
        // java php
        for(Subject subject : subjectList){
            ids.add(subject.getId());
            this.getIds(ids, subject.getId());
        }
    }

    /**
     * 根据课程分类的title和ParentId查询课程分类数据
     * @param title
     * @param parentId
     * @return
     */
    private Subject getSubjectByTitleAndParentId(String title, String parentId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        wrapper.eq("parent_id", parentId);
        Subject subject = baseMapper.selectOne(wrapper);
        return subject;
    }
}
