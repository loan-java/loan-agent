package com.mod.loan.service.biz;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.mapper.UserMapper;
import com.mod.loan.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @ author liujianjian
 * @ date 2019/6/23 21:51
 */
@Service
public class BizUserService extends BaseServiceImpl<User, Long> {
    @Resource
    private UserMapper userMapper;

    public User queryByMd5(String md5) {
        return userMapper.selectByMd5(md5);
    }

    public void insertGetId(User user) {
        userMapper.insertGetId(user);
    }

    public long updateUserMobile(long uid, String mobileNo) {
        User user = new User();
        user.setId(uid);
        user.setMobileNo(mobileNo);
        super.updateByPrimaryKeySelective(user);
        return user.getId();
    }
}
