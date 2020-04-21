package com.guli.statistics.service;

import com.guli.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author huaan
 * @since 2019-12-11
 */
public interface DailyService extends IService<Daily> {

    /**
     * 根据时间来生成统计数据
     * @param day
     */
    void createDailyByDay(String day);

    /**
     * 根据时间和类型查询注册人数和时间集合
     * @param type
     * @param begin
     * @param end
     * @return
     */
    Map<String,Object> getShow(String type, String begin, String end);
}
