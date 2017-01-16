package liuliu.qs.listener;

import java.util.HashMap;
import java.util.Map;

import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;
import liuliu.qs.model.SaveOrderModel;
import liuliu.qs.view.IAddBuy;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/30.
 */

public class SuanLuListener implements ISuanLuMView {
    IAddBuy mBuy;

    public SuanLuListener(IAddBuy mBuy) {
        this.mBuy = mBuy;
    }

    @Override
    public void js(String juli, String isnearbuy) {
        String cid = Utils.getCache("cid");
        if (("").equals(cid)) {
            mBuy.slResult(null, "当前城市未开通快跑服务");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("juli", juli);
            map.put("cityid", Utils.getCache("cid"));
            map.put("isnearbuy", isnearbuy);
            HttpUtil.load()
                    .suanLu(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(model -> {
                        if (("1").equals(model.getSuccess())) {
                            mBuy.slResult(model.getFeedata(), null);
                        } else {
                            mBuy.slResult(null, model.getErrorMsg());
                        }
                    }, error -> {
                        mBuy.slResult(null, null);
                    });
        }
    }

    @Override
    public void saveOrder(SaveOrderModel model) {
        Map<String, String> map = new HashMap<>();
        map.put("ordertype", model.getOrdertype());
        map.put("userid", model.getUserid());
        map.put("remark", model.getRemark());
        map.put("cityid", Utils.getCache("cid"));
        if (model.getNearbuy() != null) {
            map.put("nearbuy", model.getNearbuy());
        }
        map.put("address1", model.getAddress1());
        map.put("address2", model.getAddress2());
        if (model.getTel1() != null) {
            map.put("tel1", model.getTel1());
        }
        if (model.getTel2() != null) {
            map.put("tel2", model.getTel2());
        }
        if (model.getIsknow() != null) {
            map.put("isknow", model.getIsknow());
        }
        if (model.getFoodfee() != null) {
            map.put("foodfee", model.getFoodfee());
        }

        map.put("sendfee", model.getSendfee());
        map.put("lichengfee", model.getLichengfee());
        map.put("totalfee", model.getTotalfee());
        map.put("juli", model.getJuli());
        if (model.getLat1() != null) {
            map.put("lat1", model.getLat1());
        }
        if (model.getLng1() != null) {
            map.put("lng1", model.getLng1());
        }
        map.put("lat2", model.getLat2());
        map.put("lng2", model.getLng2());
        map.put("isdaishoufee", model.getIsdaishoufee());
        if (model.getDaishoufee() != null) {
            map.put("daishoufee", model.getDaishoufee());
        }
        if (model.getFahuotime() != null) {
            map.put("fahuotime", model.getFahuotime());
        }
        map.put("source", model.getSource());
        HttpUtil.load()
                .saveOrder(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mod -> {
                    if (("1").equals(mod.getSuccess())) {
                        mBuy.saveResult(mod.getData().getOrderid(), model.getTotalfee(), null);
                    } else {
                        mBuy.saveResult(null, "0", mod.getErrorMsg());
                    }
                }, error -> {
                    mBuy.saveResult(null, "0", null);
                });
    }
}

interface ISuanLuMView {
    //算路
    void js(String juli, String isNearBy);

    //保存订单
    void saveOrder(SaveOrderModel model);
}
