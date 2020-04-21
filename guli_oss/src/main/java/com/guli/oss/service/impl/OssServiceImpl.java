package com.guli.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.guli.common.exception.GuliException;
import com.guli.oss.service.OssService;
import com.guli.oss.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    //校验文件类型：后缀；
    public static final String[]  TYPE = {".png",".jpg",".gif"};

    @Override
    public String upload(MultipartFile file) {
        OSSClient ossClient = null;
        boolean flag = false;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClient(
                    ConstantPropertiesUtil.END_POINT,
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //作业：
            /**
             * 1、上传文件校验文件类型；
             * 2、上传文件校验文件内容；
             */

            //校验文件类型
            for(String type : TYPE){
                if(StringUtils.endsWithIgnoreCase(file.getOriginalFilename(),type)){
                    flag = true;
                    break;
                }
            }
            if(flag){
                System.err.println("文件类型正确");
            } else{
                throw new GuliException(20002,"文件类型不正确");
            }

            //校验内容
            BufferedImage read = ImageIO.read(file.getInputStream()); //获取图片的流数据
            if(read == null){ //如果不是图片那么就为null
                throw new GuliException(20002,"文件内容不正确");
            } else {
                System.err.println("圖片的高度："+read.getHeight());
                System.err.println("圖片的宽度："+read.getWidth());
            }

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            String filePath = new DateTime().toString("yyyy/MM/dd");
            String fileName = file.getOriginalFilename();
            //获取文件后缀  李进.很帅.png
            String ext = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID()+ext;

            String path = ConstantPropertiesUtil.FILE_HOST +"/"+filePath + "/" + newFileName; // avatar/2019/12/09/234567435673456.png
            //参数说明：1、buckName; 2、是保存的文件、里面也包含着保存的文件路径; 3、文件流
            ossClient.putObject(ConstantPropertiesUtil.BUCKET_NAME, path, inputStream);
            String url = "https://"
                    +ConstantPropertiesUtil.BUCKET_NAME
                    +"."
                    +ConstantPropertiesUtil.END_POINT
                    +"/"
                    +path;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
        return null;
    }
}
