package com.guli.edu.service;

import com.guli.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface ChapterService extends IService<Chapter> {

    /**
     * 根据课程Id查询课程大纲
     * @param courseId
     * @return
     */
    List<Map<String,Object>> getChapterListByCourseId(String courseId);

    /**
     * 根据章节Id删除章节和小节信息
     * @param chapterId
     */
    void removeChapterAndVideoByChapterId(String chapterId);
}
