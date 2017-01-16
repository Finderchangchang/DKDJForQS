package liuliu.qs.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/5.
 */

public class CityModel implements Serializable{
    private int id;
    private String cid;
    private String cname;
    private String shouzimu;
    private String lat;
    private String lng;

    public CityModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getShouzimu() {
        return shouzimu;
    }

    public void setShouzimu(String shouzimu) {
        this.shouzimu = shouzimu;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

}
