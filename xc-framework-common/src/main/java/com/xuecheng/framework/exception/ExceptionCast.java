package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * ExceptionCast
 * 异常抛出类
 * @author gmf
 * @version V1.0
 * @date 2020/10/2
 **/
public class ExceptionCast {

    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
