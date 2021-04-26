package sau.stedu.sysc01.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Category {
    private Long gameid;
    private String gameName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date endTime;

    public Category(String gameName,Date beginTime,Date endTime){
        this.beginTime =beginTime;
        this.gameName = gameName;
        this.endTime = endTime;
    }
    public Category(Long gameid,String gameName,Date beginTime,Date endTime){
        this.gameid = gameid;
        this.beginTime =beginTime;
        this.gameName = gameName;
        this.endTime = endTime;
    }

    public Long getGameid(String gameName,Date beginTime,Date endTime) {
        return gameid;
    }

    public void setGameid(Long gameid) {
        this.gameid = gameid;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
