package cn.qf.entity;/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日
 *
 * @author 陶雪峰
 * @version : 1.0
 */

/**
 * @ program: scala_studying
 * @ author:  TaoXueFeng
 * @ create: 2019-11-04 16:57
 * @ desc:  
 **/

public class PerHourProvinceTop3 {
    private int id;
    private String province;
    private String hour;
    private int adId;
    private int cnt;

    public PerHourProvinceTop3() {
    }

    public PerHourProvinceTop3(int id, String province, String hour, int adId, int cnt) {
        this.id = id;
        this.province = province;
        this.hour = hour;
        this.adId = adId;
        this.cnt = cnt;
    }

    public PerHourProvinceTop3(String province, String hour, int adId, int cnt) {
        this.province = province;
        this.hour = hour;
        this.adId = adId;
        this.cnt = cnt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
