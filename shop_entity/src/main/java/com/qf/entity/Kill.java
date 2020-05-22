package com.qf.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
public class Kill implements Serializable{

    private Integer id;
    private String title;//商品名称
    private String image;//商品图片
    private double price;//商品价格
    private Integer save;//商品库存
    private Date starttime;//秒杀开始时间
    private Date endtime;//秒杀结束时间
    private Integer statu;//状态
    private Integer version;
}
