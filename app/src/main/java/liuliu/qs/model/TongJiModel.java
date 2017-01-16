package liuliu.qs.model;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class TongJiModel {

    /**
     * success : 1
     * errorMsg :
     * todayOrderCount : 1
     * thisWeekOrderCount : 7
     * lastWeekOrderCount : 0
     * thisMonthOrderCount : 7
     * totalOrderCount : 110
     * orderlist : [{"qi":"10","end":"20","money":"2.00"},{"qi":"21","end":"10000","money":"5.00"}]
     */

    private String success;
    private String errorMsg;
    private String todayOrderCount;
    private String thisWeekOrderCount;
    private String lastWeekOrderCount;
    private String thisMonthOrderCount;
    private String totalOrderCount;
    private List<OrderlistBean> orderlist;

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

    public String getTodayOrderCount() {
        return todayOrderCount;
    }

    public void setTodayOrderCount(String todayOrderCount) {
        this.todayOrderCount = todayOrderCount;
    }

    public String getThisWeekOrderCount() {
        return thisWeekOrderCount;
    }

    public void setThisWeekOrderCount(String thisWeekOrderCount) {
        this.thisWeekOrderCount = thisWeekOrderCount;
    }

    public String getLastWeekOrderCount() {
        return lastWeekOrderCount;
    }

    public void setLastWeekOrderCount(String lastWeekOrderCount) {
        this.lastWeekOrderCount = lastWeekOrderCount;
    }

    public String getThisMonthOrderCount() {
        return thisMonthOrderCount;
    }

    public void setThisMonthOrderCount(String thisMonthOrderCount) {
        this.thisMonthOrderCount = thisMonthOrderCount;
    }

    public String getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(String totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public List<OrderlistBean> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<OrderlistBean> orderlist) {
        this.orderlist = orderlist;
    }

    public static class OrderlistBean {
        /**
         * qi : 10
         * end : 20
         * money : 2.00
         */

        private String qi;
        private String end;
        private String money;

        public String getQi() {
            return qi;
        }

        public void setQi(String qi) {
            this.qi = qi;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
