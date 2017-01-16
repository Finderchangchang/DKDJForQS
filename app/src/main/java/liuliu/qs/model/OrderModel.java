package liuliu.qs.model;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Administrator on 2016/12/2.
 */


public class OrderModel implements Serializable {
    private String orderid;
    private String dataid;
    private String orderTime;
    private String TotalPrice;
    private String orderStatus;
    private String sendState;
    private String IsShopSet;
    private String orderType;
    private String payState;
    private String qAddress;
    private String sAddress;
    private String qTell;
    private String sTell;
    private String deliverName;
    private String deliverPhone;
    private String dingdanzhuangtai;
    private String state;
    private String page;
    private String total;
    private String record;
    private List<OrderlistBean> orderlist;


    public String getqAddress() {
        return qAddress;
    }

    public void setqAddress(String qAddress) {
        this.qAddress = qAddress;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getqTell() {
        return qTell;
    }

    public void setqTell(String qTell) {
        this.qTell = qTell;
    }

    public String getsTell() {
        return sTell;
    }

    public void setsTell(String sTell) {
        this.sTell = sTell;
    }

    public String getDingdanzhuangtai() {
        return dingdanzhuangtai;
    }

    public void setDingdanzhuangtai(String dingdanzhuangtai) {
        this.dingdanzhuangtai = dingdanzhuangtai;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSendState() {
        return sendState;
    }

    public void setSendState(String sendState) {
        this.sendState = sendState;
    }

    public String getIsShopSet() {
        return IsShopSet;
    }

    public void setIsShopSet(String IsShopSet) {
        this.IsShopSet = IsShopSet;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getQAddress() {
        return qAddress;
    }

    public void setQAddress(String qAddress) {
        this.qAddress = qAddress;
    }

    public String getSAddress() {
        return sAddress;
    }

    public void setSAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getQTell() {
        return qTell;
    }

    public void setQTell(String qTell) {
        this.qTell = qTell;
    }

    public String getSTell() {
        return sTell;
    }

    public void setSTell(String sTell) {
        this.sTell = sTell;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }

    public String getDeliverPhone() {
        return deliverPhone;
    }

    public void setDeliverPhone(String deliverPhone) {
        this.deliverPhone = deliverPhone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public List<OrderlistBean> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<OrderlistBean> orderlist) {
        this.orderlist = orderlist;
    }

    public static class OrderlistBean {

        private String dname;
        private String dphone;
        private String OrderDateTime;
        private String orderid;
        private String sendstate;
        private String sendfee;
        private String isshopset;
        private String OrderAddress;

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public String getDphone() {
            return dphone;
        }

        public void setDphone(String dphone) {
            this.dphone = dphone;
        }

        public String getOrderDateTime() {
            return OrderDateTime;
        }

        public void setOrderDateTime(String OrderDateTime) {
            this.OrderDateTime = OrderDateTime;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getSendstate() {
            return sendstate;
        }

        public void setSendstate(String sendstate) {
            this.sendstate = sendstate;
        }

        public String getSendfee() {
            return sendfee;
        }

        public void setSendfee(String sendfee) {
            this.sendfee = sendfee;
        }

        public String getIsshopset() {
            return isshopset;
        }

        public void setIsshopset(String isshopset) {
            this.isshopset = isshopset;
        }

        public String getOrderAddress() {
            return OrderAddress;
        }

        public void setOrderAddress(String OrderAddress) {
            this.OrderAddress = OrderAddress;
        }
    }
}
