package liuliu.qs.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */

public class MessageModel {
    private String success;
    private String errorMsg;
    private CodeModel data;
    private List<LatLngModel> deliverdata;//骑士坐标集合
    private FeiModel feedata;//算费
    private List<PoiModel> datalist;
    private OrderListModel orderdata;//
    private UserModel userdata;
    private OrderDetailModel orderinfodata;
    private List<CityModel> citydata;
    private String cityid;
    private String msg;
    private String shopid;
    private String state;
    private String togoname;
    private String newordercount;
    private String sendtype;
    private String lat;
    private String lng;
    /**
     * sendfee : 30.00
     */

    private String sendfee;


    public List<CityModel> getCitydata() {
        return citydata;
    }

    public void setCitydata(List<CityModel> citydata) {
        this.citydata = citydata;
    }

    public OrderDetailModel getOrderinfodata() {
        return orderinfodata;
    }

    public void setOrderinfodata(OrderDetailModel orderinfodata) {
        this.orderinfodata = orderinfodata;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public UserModel getUserdata() {
        return userdata;
    }

    public void setUserdata(UserModel userdata) {
        this.userdata = userdata;
    }

    public List<PoiModel> getDatalist() {
        return datalist;
    }

    public OrderListModel getOrderdata() {
        return orderdata;
    }

    public void setOrderdata(OrderListModel orderdata) {
        this.orderdata = orderdata;
    }

    public void setDatalist(List<PoiModel> datalist) {
        this.datalist = datalist;
    }

    public List<LatLngModel> getDeliverdata() {
        return deliverdata;
    }

    public void setDeliverdata(List<LatLngModel> deliverdata) {
        this.deliverdata = deliverdata;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public CodeModel getData() {
        return data;
    }

    public void setData(CodeModel data) {
        this.data = data;
    }

    public FeiModel getFeedata() {
        return feedata;
    }

    public void setFeedata(FeiModel feedata) {
        this.feedata = feedata;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTogoname() {
        return togoname;
    }

    public void setTogoname(String togoname) {
        this.togoname = togoname;
    }

    public String getNewordercount() {
        return newordercount;
    }

    public void setNewordercount(String newordercount) {
        this.newordercount = newordercount;
    }

    public String getSendtype() {
        return sendtype;
    }

    public void setSendtype(String sendtype) {
        this.sendtype = sendtype;
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

    public String getSendfee() {
        return sendfee;
    }

    public void setSendfee(String sendfee) {
        this.sendfee = sendfee;
    }
}
