package sau.stedu.sysc01.model;

public class RespBean {
    private Integer code;
    private String msg;
    private Object data;
    private String token;

    private RespBean() {}
    private RespBean(Integer code, String msg, Object data,String token) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.token = token;
    }
    private RespBean(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static RespBean success(Integer code, String msg) {
        return new RespBean(code,msg,null);
    }
    public static RespBean success(Integer code, String msg, Object data) {
        return new RespBean(code,msg,data);
    }
    public static RespBean error(Integer code, String msg) {
        return new RespBean(code,msg,null);
    }
    public static RespBean error(Integer code, String msg, Object data) {
        return new RespBean(code,msg,data);
    }


    public static RespBean success1(Integer code, String msg,String token) {
        return new RespBean(code,msg,null,token);
    }
    public static RespBean success1(Integer code, String msg, Object data,String token) {
        return new RespBean(code,msg,data,token);
    }
    public static RespBean error1(Integer code, String msg,String token) {
        return new RespBean(code,msg,null,token);
    }
    public static RespBean error1(Integer code, String msg, Object data,String token) {
        return new RespBean(code,msg,data,token);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}