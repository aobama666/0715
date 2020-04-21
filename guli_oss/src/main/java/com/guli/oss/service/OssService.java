package com.guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
