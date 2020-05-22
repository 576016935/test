package com.qf.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ResultData<T> implements Serializable {

    /**
     * 状态码：0-登录成功，1-密码错误，2-账号错误
     */
    private Integer code;

    /**
     * 错误内容
     */
    private String msg;

    /**
     * 存放用户对象
     */
    private T data;

    public ResultData(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
