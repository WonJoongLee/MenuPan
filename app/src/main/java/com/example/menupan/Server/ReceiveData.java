package com.example.menupan.Server;

import java.io.Serializable;

public class ReceiveData implements Serializable {
    private String name;
    private String location;
    private String upinfo;
    private String downinfo;
    private String resnumber;
    private String mainpic;
    private String menupic;
    private String respic;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;
    private Float xco, yco;

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

    public String getMainpic() {
        return mainpic;
    }

    public void setMainpic(String mainpic) {
        this.mainpic = mainpic;
    }

    public String getMenupic() {
        return menupic;
    }

    public void setMenupic(String menupic) {
        this.menupic = menupic;
    }

    public String getRespic() {
        return respic;
    }

    public void setRespic(String respic) {
        this.respic = respic;
    }
}
