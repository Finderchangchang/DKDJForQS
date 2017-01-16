package liuliu.qs.wxapi;

import java.io.Serializable;

/**
 * 支付状态
 * 作者：lwj on 2016/7/12 20:24
 * 邮箱：1031066280@qq.com
 */
public class PayState implements Serializable{

    /**
     * orderid : 16071220151058000
     * orderstate : 1
     * totalprice : 38.00
     * msg : 订单总金额：38.00元.
     */

    private String orderid;
    private String orderstate;
    private String totalprice;
    private String msg;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
