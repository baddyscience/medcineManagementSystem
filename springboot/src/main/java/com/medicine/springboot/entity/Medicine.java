package com.medicine.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@Data
@TableName("medicine")  // 表名
public class Medicine {

    @Setter
    @TableId(type = IdType.AUTO)
    public Integer mno;

    public String mname;
    public String mmode;
    public String mefficacy;

}
