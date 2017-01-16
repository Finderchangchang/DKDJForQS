package liuliu.qs.view;

import liuliu.qs.model.FeiModel;

/**
 * Created by Administrator on 2016/11/30.
 */

public interface IAddBuy {
    void slResult(FeiModel model, String error);

    void saveResult(String orderId, String totalPrice,String error);
}
