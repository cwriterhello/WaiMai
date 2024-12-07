package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface UserMapper {
    @Select("select *from user where openid = #{openid}")
    User getByOpenid(String openid);

    @SelectKey(statement="select by openid", keyProperty="id", before=false, resultType=Long.class)

    void add(User user);

    User getById(Long userId);
}
