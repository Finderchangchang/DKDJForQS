package liuliu.qs.model;

/**
 * 生成订单的model
 * Created by Administrator on 2016/11/30.
 */

public class SaveOrderModel {
    private String ordertype;//	订单类型（1我要买 ，2帮我送，3帮我取）,
    private String userid;//	用户编号,
    private String remark;//	备注(帮我送，帮我取中【物品类型】字段存入remark中)示例：物品类型-文件，备注文字内容,
    private String nearbuy;//	帮我买---是否就近买(0否， 1 是),
    private String address1;//	购买地址(帮我买中可为空)；发货地址；取货地,
    private String address2;//	收货地址；收货地址；收货地址,
    private String tel1;//	取货人电话,
    private String tel2;//	收货人电话,
    private String isknow;//	帮我买---是否知道商品金额(0不知道 1知道),
    private String foodfee;//	帮我买---商品金额,
    private String sendfee;//	起步价,
    private String lichengfee;//	里程费,
    private String totalfee;//	支付总额,
    private String juli;//	距离,
    private String lat1;//	购买地址(帮我买中可为空)；发货地址；取货地  lat,
    private String lng1;//	购买地址(帮我买中可为空)；发货地址；取货地  lng,
    private String lat2;//	收货地址；收货地址；收货地址  lat2,
    private String lng2;//	收货地址；收货地址；收货地址  lng2,
    private String isdaishoufee;//	帮我送---是否代收货款 0否  1是,
    private String daishoufee;//	帮我送---代收货款金额,
    private String fahuotime;//	帮我送---发货时间,
    private String source;//	注册来源 [0 web; 2安卓; 3 ios]}

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNearbuy() {
        return nearbuy;
    }

    public void setNearbuy(String nearbuy) {
        this.nearbuy = nearbuy;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getIsknow() {
        return isknow;
    }

    public void setIsknow(String isknow) {
        this.isknow = isknow;
    }

    public String getFoodfee() {
        return foodfee;
    }

    public void setFoodfee(String foodfee) {
        this.foodfee = foodfee;
    }

    public String getSendfee() {
        return sendfee;
    }

    public void setSendfee(String sendfee) {
        this.sendfee = sendfee;
    }

    public String getLichengfee() {
        return lichengfee;
    }

    public void setLichengfee(String lichengfee) {
        this.lichengfee = lichengfee;
    }

    public String getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(String totalfee) {
        this.totalfee = totalfee;
    }

    public String getJuli() {
        return juli;
    }

    public void setJuli(String juli) {
        this.juli = juli;
    }

    public String getLat1() {
        return lat1;
    }

    public void setLat1(String lat1) {
        this.lat1 = lat1;
    }

    public String getLng1() {
        return lng1;
    }

    public void setLng1(String lng1) {
        this.lng1 = lng1;
    }

    public String getLat2() {
        return lat2;
    }

    public void setLat2(String lat2) {
        this.lat2 = lat2;
    }

    public String getLng2() {
        return lng2;
    }

    public void setLng2(String lng2) {
        this.lng2 = lng2;
    }

    public String getIsdaishoufee() {
        return isdaishoufee;
    }

    public void setIsdaishoufee(String isdaishoufee) {
        this.isdaishoufee = isdaishoufee;
    }

    public String getDaishoufee() {
        return daishoufee;
    }

    public void setDaishoufee(String daishoufee) {
        this.daishoufee = daishoufee;
    }

    public String getFahuotime() {
        return fahuotime;
    }

    public void setFahuotime(String fahuotime) {
        this.fahuotime = fahuotime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
