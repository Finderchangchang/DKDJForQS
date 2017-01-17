package liuliu.qs.listener;

import java.util.HashMap;
import java.util.Map;

import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;
import liuliu.qs.view.IOrder;
import liuliu.qs.view.IOrderDetail;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/2.
 */
interface IOrderMView {

    void loadOrder(int pageindex, String sendstate);

    void cancleOrder(String orderType, String orderId);

    void getOrderDetail(String did);
}

public class OrderListener implements IOrderMView {
    IOrder mView;
    IOrderDetail mDetail;

    public OrderListener(IOrder mView) {
        this.mView = mView;
    }

    public OrderListener(IOrderDetail mDetail) {
        this.mDetail = mDetail;
    }

    @Override
    public void loadOrder(int pageindex, String sendstate) {
        Map<String, String> map = new HashMap<>();
        map.put("togoid", Utils.getCache("UserId"));
        switch (sendstate) {
            case "0":
                map.put("sendstate", "0");
                break;
            case "1":
                map.put("sendstate", "1,2,5");
                break;
            case "2":
                map.put("sendstate", "3");
                break;
        }
        map.put("pageindex", pageindex + "");
        map.put("pagesize", "50");
        HttpUtil.load()
                .getOrderList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getState())) {
                        mView.refreshOrder(model.getOrderlist());
                    } else {
                        mView.refreshOrder(null);
                    }
                }, error -> {
                    mView.refreshOrder(null);
                });
    }

    @Override
    public void cancleOrder(String orderType, String orderId) {

    }

    @Override
    public void getOrderDetail(String did) {
        HttpUtil.load()
                .getOrderDetail(did)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
//                    if (("1").equals(model.getSuccess())) {
//                        if (model != null) {
//                            mDetail.loadResult(model.getOrderinfodata());
//                        } else {
//                            mDetail.loadResult(null);
//                        }
//                    } else {
//                        mDetail.loadResult(null);
//                    }
                }, error -> {
                    mDetail.loadResult(null);
                });
    }
}
