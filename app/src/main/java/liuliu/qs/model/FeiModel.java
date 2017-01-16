package liuliu.qs.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/30.
 */

public class FeiModel implements Serializable{

    private String totalfee;
    private String alljuli;
    private String qibufee;
    private String lichengfee;

    public String getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(String totalfee) {
        this.totalfee = totalfee;
    }

    public String getAlljuli() {
        return alljuli;
    }

    public void setAlljuli(String alljuli) {
        this.alljuli = alljuli;
    }

    public String getQibufee() {
        return qibufee;
    }

    public void setQibufee(String qibufee) {
        this.qibufee = qibufee;
    }

    public String getLichengfee() {
        return lichengfee;
    }

    public void setLichengfee(String lichengfee) {
        this.lichengfee = lichengfee;
    }
}
