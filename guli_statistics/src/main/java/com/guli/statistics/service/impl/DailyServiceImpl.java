package com.guli.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.entity.Result;
import com.guli.common.exception.GuliException;
import com.guli.statistics.client.UcenterClient;
import com.guli.statistics.entity.Daily;
import com.guli.statistics.mapper.DailyMapper;
import com.guli.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.x509.AttributeNameEnumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author huaan
 * @since 2019-12-11
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void createDailyByDay(String day) {

        //1、先根据时间查询统计表中是否有这个时间的统计
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        Daily daily = baseMapper.selectOne(wrapper);
        //2、如果有先删除
        if(daily != null){
            baseMapper.deleteById(daily.getId());
        }
        //3、然后再生成统计 : 这些统计的数据应该否是远程调用过来的
        //Integer registerNum = RandomUtils.nextInt(100, 200);//TODO
        Integer registerNum = null;
        Result result = ucenterClient.registerCount(day);
        if(result.getSuccess()){
            registerNum = (Integer) result.getData().get("count");
        } else{
            throw new GuliException(20025,"用户统计服务网络异常");
        }
        //Integer registerNum = (Integer) ucenterClient.registerCount(day).getData().get("count");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO
        //4、获取用户注册人数个数、登陆人数的个数、视频播放量、课程数等
        daily = new Daily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);
        //5、插入数据库
        baseMapper.insert(daily);
    }

    @Override //建议传递过来的类型和数据表中的类型字段名要一致 register_num
    public Map<String, Object> getShow(String type, String begin, String end) {

        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated", type);
        wrapper.between("date_calculated", begin, end);
        wrapper.orderByAsc("date_calculated");
        // select date_calculated， register_num from statistics_daily where date_calculated between begin and  end
        List<Daily> selectList = baseMapper.selectList(wrapper);
//        ["6","8"...."45","19"]
//        ["2019-01-01",""...."","2019-01-10"] == 被统计的时间段
        // 创建两个集合，来存储查询出来的数据
        List<Integer> dataList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();

        for(Daily daily : selectList){
            //把集合中的每一个对象遍历
            //获取被统计的时间
            dateList.add(daily.getDateCalculated());
            //判断type类型是什么类型并添加在dataList
            switch (type){
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);
        return map;
    }
}
