package com.wjx.common;

import com.wjx.common.constant.Constant;
import lombok.Data;

/**
 * @Desc:
 * @File name：com.wjx.common.Result
 * @Create on：2022/3/8 13:56
 * @Author：wjx
 */
@Data
public class Result<T> {
    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    private T data;

    public Result() {
    }

    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param msg
     * @param data
     */
    public Result(int code, String msg, T data) {
        this(code + "", msg, data);
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result ok() {
        return new Result(0,"success",null);
    }

    public static Result ok(Object data) {
        return new Result(0,"success",data);
    }


    public static Result ok(String msg,Object data) {
        return new Result(0,msg,data);
    }

    public static Result error(int code, String msg) {
        return new Result(code,msg,null);
    }

    public static Result error(String code, String msg) {
        return new Result(code,msg,null);
    }

    public static Result error(String msg) {
        return Result.error(Constant.RESULT.EX500,msg);
    }

    public static Result error(int code) {
        return Result.error(code,"操作失败");
    }

    public static Result error() {
        return Result.error("操作失败");
    }

}

