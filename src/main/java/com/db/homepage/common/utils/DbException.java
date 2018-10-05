package com.db.homepage.common.utils;

import lombok.Data;

/**
 * 自定义一异常
 *
 * @author 大兵
 * @date 2018-10-04 22:19
 **/
@Data
public class DbException extends RuntimeException {
    private String msg;
    private int code = 500;

    public DbException() {
        super();
    }

    public DbException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public DbException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public DbException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public DbException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
