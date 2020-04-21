package com.guli.vod.sdk;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

public class GetVideoPlayAuth {


    /*获取播放凭证函数*/
    /*public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("5acfd9e678d143ff80c064b493abeb3f");
        return client.getAcsResponse(request);
    }*/
    /*以下为调用示例*/
    public static void main(String[] argv) throws Exception{
        DefaultAcsClient client = VodUtils.initVodClient("LTAIjDanEwf9RVXP", "v23qo8tptfAscACX44QO56XedG5Owk");
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        try {
            request.setVideoId("ca2832c8d5ad4c66a76f1e802693aa15");
            response = client.getAcsResponse(request);
            //response = getVideoPlayAuth(client);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

}
