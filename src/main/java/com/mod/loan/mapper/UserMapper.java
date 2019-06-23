package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper extends MyBaseMapper<User> {


    User selectByMd5(@Param("str") String str);

    void insertGetId(User user);
}