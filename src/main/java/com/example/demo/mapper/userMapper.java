package com.example.demo.mapper;

import com.example.demo.bean.user;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface userMapper {
    @Select("SELECT * FROM user WHERE `phone`= #{phone};")
    public user getUser(String phone);
    @Insert("INSERT INTO`user`(phone,password) VALUES ( #{param1}, #{param2});")
    public int insertUser(String phone,String password);
    @Update("UPDATE `user` SET password=#{newpassword} WHERE phone=#{phone};")
    public int updatePassword(String phone,String newpassword);
    @Select("SELECT * FROM `user` WHERE phone =#{phone};")
    public user findUserMessage(String phone);
}
