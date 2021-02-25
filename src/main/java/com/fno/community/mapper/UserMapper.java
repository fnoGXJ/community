package com.fno.community.mapper;

import com.fno.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO USER (name,account_id,token,gmt_create,gmt_modified,avatar_url) VALUES (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);
    @Select("SELECT* FROM USER WHERE token = #{token}")
    User findByToken(@Param("token") String token);
    @Select("SELECT* FROM USER WHERE id = #{id}")
    User findById(@Param("id") Integer id);
    @Select("SELECT* FROM USER WHERE account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);
}
