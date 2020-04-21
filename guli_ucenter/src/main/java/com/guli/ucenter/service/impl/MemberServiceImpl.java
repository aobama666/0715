package com.guli.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.ucenter.entity.Member;
import com.guli.ucenter.mapper.MemberMapper;
import com.guli.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author huaan
 * @since 2019-12-11
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public Integer getRegisterCountByDay(String day) {
        //like 模糊查询效率低
//        QueryWrapper<Member> wrapper = new QueryWrapper<>();
//        wrapper.like("gmt_create", day);
//        Integer count = baseMapper.selectCount(wrapper);

        //2、自己写SQL语句进行查询
        Integer count = baseMapper.getRegisterCountByDay(day);

        return count;
    }

    @Override
    public Member getMemberByOpenId(String openid) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        Member member = baseMapper.selectOne(wrapper);
        return member;
    }
}
