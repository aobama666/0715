package com.guli.edu.service;

import com.guli.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author huaan
 * @since 2019-12-04
 */
public interface VideoService extends IService<Video> {

    /**
     * 根据课时Id删除课时信息
     * @param videoId
     */
    void removeVideoById(String videoId);

    /**
     * 根据章节Id删除课时信息
     * @param chapterId
     */
    void removeVideoByChapterId(String chapterId);

}
