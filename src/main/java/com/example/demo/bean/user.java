package com.example.demo.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class user {

    @Length(min=11,max = 11,message = "手机号码格式不正确")
  private String phone;
  @Length(min = 8,message = "密码不能少于八位")
  private String password;

}
