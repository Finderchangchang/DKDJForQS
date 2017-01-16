package liuliu.qs.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */

public class OrderListModel {
    String page;//当前页
    String total;//总页数
    List<OrderModel> orderlist;//订单列表

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

    public List<OrderModel> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<OrderModel> orderlist) {
        this.orderlist = orderlist;
    }
}
