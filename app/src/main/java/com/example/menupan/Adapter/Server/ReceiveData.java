package com.example.menupan.Adapter.Server;

import android.graphics.drawable.Drawable;

public class ReceiveData {
    private String name, location, upinfo, downinfo, resnumber;
    private Float xco, yco;
    //private Drawable mainpic, menupic, respic;
    private byte[] mainpic, menupic, respic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUpinfo() {
        return upinfo;
    }

    public void setUpinfo(String upinfo) {
        this.upinfo = upinfo;
    }

    public String getDowninfo() {
        return downinfo;
    }

    public void setDowninfo(String downinfo) {
        this.downinfo = downinfo;
    }

    public String getResnumber() {
        return resnumber;
    }

    public void setResnumber(String resnumber) {
        this.resnumber = resnumber;
    }

    public Float getXco() {
        return xco;
    }

    public void setXco(Float xco) {
        this.xco = xco;
    }

    public Float getYco() {
        return yco;
    }

    public void setYco(Float yco) {
        this.yco = yco;
    }

    public byte[] getMainpic() {
        return mainpic;
    }

    public void setMainpic(byte[] mainpic) {
        this.mainpic = mainpic;
    }

    public byte[] getMenupic() {
        return menupic;
    }

    public void setMenupic(byte[] menupic) {
        this.menupic = menupic;
    }

    public byte[] getRespic() {
        return respic;
    }

    public void setRespic(byte[] respic) {
        this.respic = respic;
    }
}
