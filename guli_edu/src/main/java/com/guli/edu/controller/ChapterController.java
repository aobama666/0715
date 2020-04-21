package com.guli.edu.controller;


import com.guli.common.entity.Result;
import com.guli.edu.entity.Chapter;
import com.guli.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/edu/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    /**
     * 根据课程Id获取课程的大纲
     */
    @GetMapping("getChapterList/{courseId}")
    public Result getChapterList(@PathVariable("courseId") String courseId){
        List<Map<String, Object>> mapList = chapterService.getChapterListByCourseId(courseId);
        return Result.ok().data("list", mapList);
    }

    /**
     * 保存
     */
    @PostMapping("save")
    public Result save(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return Result.ok();
    }

    /**
     * 根据章节Id获取章节信息
     */
    @GetMapping("getChapterById/{chapterId}")
    public Result getChapterById(@PathVariable("chapterId") String chapterId){
        Chapter chapter = chapterService.getById(chapterId);
        return Result.ok().data("chapter", chapter);
    }

    /**
     * 修改章节
     */
    @PutMapping("update")
    public Result updateById(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return Result.ok();
    }

    /**
     * 根据章节Id删除章节信息
     */
    @DeleteMapping("removeByChapterId/{chapterId}")
    public Result removeByChapterId(@PathVariable("chapterId") String chapterId){
        //删除：章节和小节
        chapterService.removeChapterAndVideoByChapterId(chapterId);
        return Result.ok();
    }

}

