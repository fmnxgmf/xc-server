package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * CustomException
 * 自定义异常类
 * @author gmf
 * @version V1.0
 * @date 2020/10/2
 **/
public class CustomException extends RuntimeException {

    private ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        //  异常信息为错误代码+错误信息
        super("错误代码:"+resultCode.code()+" 错误信息:"+resultCode.message());
        this.resultCode = resultCode;
    }
    public ResultCode getResultCode(){
        return this.resultCode;
    }
}
