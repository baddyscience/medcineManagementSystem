package com.medicine.springboot.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

@Data
@TableName("client")  // 表名
public class Client {

    @Setter
    @TableId(type = IdType.AUTO)
    public Integer cno;//编号

    public String cname;//姓名
    public String csex;
    public String cage;
    public String caddress;
    public String cphone;
    public String csymptom;

    @TableField(fill = FieldFill.INSERT)
    private Integer mno;

    @TableField(fill = FieldFill.INSERT)
    private Integer ano;

    public String cdate;
    public String cremark;
}
