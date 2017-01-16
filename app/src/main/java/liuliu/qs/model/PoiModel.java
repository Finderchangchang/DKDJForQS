package liuliu.qs.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/29.
 */

public class PoiModel implements Serializable {
    private int id;
    private int state;
    private String aid;
    private String poiName;
    private String poiAddress;
    private String detailAddress;
    private String jvli;
    private double lat;
    private double lng;

    public PoiModel() {
    }

    public PoiModel(int state, String aid) {
        this.state = state;
        this.aid = aid;
    }

    public PoiModel(String poiName, String poiAddress, String jvli, double lat, double lng) {
        this.poiName = poiName;
        this.poiAddress = poiAddress;
        this.jvli = jvli;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public String getPoiAddress() {
        return poiAddress;
    }

    public void setPoiAddress(String poiAddress) {
        this.poiAddress = poiAddress;
    }

    public String getJvli() {
        return jvli;
    }

    public void setJvli(String jvli) {
        this.jvli = jvli;
    }
}
