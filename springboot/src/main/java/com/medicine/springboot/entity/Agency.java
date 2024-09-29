package com.medicine.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@Data
@TableName("agency")  // 表名
public class Agency {

    @Setter
    @TableId(type = IdType.AUTO)
    public Integer ano;
    public String aname;
    public String asex;
    public String aphone;
    public String aremark;

}
