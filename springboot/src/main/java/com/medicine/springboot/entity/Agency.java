package com.medicine.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("agency")  // 表名
public class Agency {

    @TableId(type = IdType.AUTO)
    public String ano;
    public String aname;
    public String asex;
    public String aphone;
    public String aremark;

}
