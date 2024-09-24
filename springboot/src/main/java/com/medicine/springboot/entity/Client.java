package com.medicine.springboot.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("client")  // 表名
public class Client {

    @TableId(type = IdType.AUTO)
    public String cno;//编号

    public String cname;//姓名
    public String csex;
    public String cage;
    public String caddress;
    public String cphone;
    public String csymptom;

    @TableField(fill = FieldFill.INSERT)
    private String mno;

    @TableField(fill = FieldFill.INSERT)
    private String ano;

    public String cdate;
    public String cremark;
}
