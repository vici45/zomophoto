package com.zomo.photo.common;

import lombok.Data;

@Data
public class ServiceResponse<T> {
    private String msg;
    private Integer code;
    private  T data;

    private ServiceResponse(Integer code,String msg,T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    private ServiceResponse(Integer code,String msg){
        this.msg=msg;
        this.code=code;
    }

    private ServiceResponse(Integer code,T data){
        this.data=data;
        this.code=code;
    }

    private ServiceResponse(Integer code){
        this.code=code;
    }

    public boolean isSuccess(){
       return this.code==ResponseCode.SUCCESS.getCode();
    }

    public static <T> ServiceResponse<T> createBySuccess(){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServiceResponse<T> createBySuccess(T data){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServiceResponse<T> createBySuccess(T data,String msg){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> ServiceResponse<T> createBySuccessMessage(String msg){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }
    public static <T> ServiceResponse<T> createByError(){
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode());
    }
    public static <T> ServiceResponse<T> createByErrorMessage(String msg){
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(),msg);
    }
    public static <T> ServiceResponse<T> createByerrorCodeMessage(Integer code,String msg){
        return new ServiceResponse<T>(code,msg);
    }

}
