package liuliu.qs.listener;

import liuliu.qs.config.Key;
import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;
import liuliu.qs.view.IMain;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/30.
 */

interface IMainMView {
    void loadQSLatLngs();

    void checkUpdate();

    void getUserInfo();
}

public class MainListener implements IMainMView {
    IMain mView;

    public MainListener(IMain mView) {
        this.mView = mView;
    }

    @Override
    public void loadQSLatLngs() {
        HttpUtil.load()
                .getQsLatLngs(Utils.getCache("cid"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getSuccess())) {
                        if (model.getDeliverdata() != null) {
                            mView.getQsLatLng(model.getDeliverdata());
                        } else {
                            mView.getQsLatLng(null);
                        }
                    } else {
                        mView.getQsLatLng(null);
                    }
                }, error -> {
                    mView.getQsLatLng(null);
                });
    }

    @Override
    public void checkUpdate() {
        HttpUtil.load()
                .checkUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    mView.checkUpdate(model);
                }, error -> {
                    mView.checkUpdate(null);
                });
    }

    @Override
    public void getUserInfo() {
        String id = Utils.getCache(Key.KEY_UserId);
        HttpUtil.load()
                .getShopInfo(Utils.getCache(Key.KEY_UserId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    mView.getUserInfo(model);
                });
    }

}
