package liuliu.qs.listener;

import java.util.HashMap;
import java.util.Map;

import liuliu.qs.config.Key;
import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;
import liuliu.qs.model.PoiModel;
import liuliu.qs.view.IAddressList;
import liuliu.qs.view.IAddressManage;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 地址管理
 * Created by Administrator on 2016/12/2.
 */
interface IAddressMView {
    //加载地址列表
    void loadAddressList();

    //增删改地址
    void addAddress(PoiModel model);
}

public class AddressManageListener implements IAddressMView {
    IAddressManage mView;//增删改管理
    IAddressList mResult;//地址列表

    public AddressManageListener(IAddressManage mView, IAddressList mResult) {
        this.mView = mView;
        this.mResult = mResult;
    }

    public AddressManageListener(IAddressManage mView) {
        this.mView = mView;
    }

    public AddressManageListener(IAddressList mResult) {
        this.mResult = mResult;
    }

    @Override
    public void loadAddressList() {
        String user = Utils.getCache(Key.KEY_UserId);
        Map<String,String> map=new HashMap<>();
        map.put("userid",Utils.getCache(Key.KEY_UserId));
        HttpUtil.load()
                .getAddressList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getSuccess())) {
                        if (model.getDatalist() != null) {
                            mResult.loadAddressResult(model.getDatalist());
                        } else {
                            mResult.loadAddressResult(null);
                        }
                    } else {
                        mResult.loadAddressResult(null);
                    }
                }, error -> {
                    mResult.loadAddressResult(null);
                });
    }

    @Override
    public void addAddress(PoiModel model) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", Utils.getCache(Key.KEY_UserId));
        map.put("op", model.getState() + "");
        if (model.getAid() != null) {
            map.put("aid", model.getAid());//添加为空
        }
        if (model.getPoiName() != null) {
            map.put("poiName", model.getPoiName());
        }
        if (model.getPoiAddress() != null) {
            map.put("poiAddress", model.getPoiAddress());
        }
        if (model.getDetailAddress() != null) {
            map.put("detailAddress", model.getDetailAddress());
        }
        if (model.getLat() != 0) {
            map.put("lat", model.getLat() + "");
            map.put("lng", model.getLng() + "");
        }
        HttpUtil.load()
                .addAddress(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if (("1").equals(data.getSuccess())) {
                        mView.manageResult(true);
                    } else {
                        mView.manageResult(false);
                    }
                }, error -> {
                    mView.manageResult(false);
                });
    }
}

