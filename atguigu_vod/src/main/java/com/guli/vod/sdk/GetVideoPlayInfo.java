package com.guli.vod.sdk;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;

public class GetVideoPlayInfo {


    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("f9a07bf3ab994b69827b59293b506d18");
        return client.getAcsResponse(request);
    }
    /*以下为调用示例*/
    public static void main(String[] argv) throws Exception{
        DefaultAcsClient client = VodUtils.initVodClient("LTAIjDanEwf9RVXP", "v23qo8tptfAscACX44QO56XedG5Owk");
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = getPlayInfo(client);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
