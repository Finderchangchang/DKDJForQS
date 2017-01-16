package liuliu.qs.view;

import java.util.List;

import liuliu.qs.model.OrderListModel;
import liuliu.qs.model.OrderModel;

/**
 * Created by Administrator on 2016/12/2.
 */

public interface IOrder {
    //刷新后返回数据
    void refreshOrder(List<OrderModel.OrderlistBean> model);

    //加载更多显示数据
    void loadMoreOrder(OrderListModel model);

    //改变订单状态处理结果
    void changeStateResult(boolean result);
}
