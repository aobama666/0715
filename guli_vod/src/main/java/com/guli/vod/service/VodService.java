package com.guli.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    /**
     * 上传视频文件
     * @param file
     * @return
     */
    String uploadVod(MultipartFile file);

    /**
     * 根据视频Id删除云端视频信息
     * @param videoSourceId
     */
    void removeById(String videoSourceId);

    /**
     * 根据多个Id删除云端视频
     * @param ids
     */
    void removeByIds(List<String> ids);

    /**
     * 根据视频Id获取播放凭证
     * @param videoSourceId
     * @return
     */
    String getPlayAuthById(String videoSourceId);
}
