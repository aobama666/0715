package com.guli.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.guli.vod.service.VodService;
import com.guli.vod.utils.ConstantPropertiesUtil;
import com.guli.vod.utils.VodUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVod(MultipartFile file) {

        try {
            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET, file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")), file.getOriginalFilename(), file.getInputStream());
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                return response.getVideoId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void removeById(String videoSourceId) {

        try {
            //初始化client
            DefaultAcsClient client = VodUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建一个删除的Request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //在request中设置要删除的IDS "videoId1,videoId2,videoId3,..."
            request.setVideoIds(videoSourceId);
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeByIds(List<String> ids) {
        try {
            //初始化client
            DefaultAcsClient client = VodUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建一个删除的Request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //在request中设置要删除的IDS "videoId1,videoId2,videoId3,..."
            //把集合转成字符串形式
            //String[] array = (String[]) ids.toArray();
            String str = StringUtils.join(ids.toArray(),",");
            String strTest = org.springframework.util.StringUtils.arrayToDelimitedString(ids.toArray(),",");
            // 目的：把集合类型的IDS传递过来后，想着把这个集合转成字符串并且是通过“,”来分割的这样的话有利于咱们的批量删除
            request.setVideoIds(str);
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPlayAuthById(String videoSourceId) {

        try {
            DefaultAcsClient client = VodUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            request.setVideoId(videoSourceId);
            GetVideoPlayAuthResponse response  = client.getAcsResponse(request);
            //response = getVideoPlayAuth(client);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            return response.getPlayAuth();
            //VideoMeta信息
            // System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        return null;
    }
}
