package com.guli.ucenter.service;

import com.guli.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author huaan
 * @since 2019-12-11
 */
public interface MemberService extends IService<Member> {

    /**
     * 根据时间来获取注册人数的个数
     * @param day
     * @return
     */
    Integer getRegisterCountByDay(String day);

    /**
     * 根据OpenId获取用户信息
     * @param openid
     * @return
     */
    Member getMemberByOpenId(String openid);
}
