package liuliu.qs.listener;

import liuliu.qs.config.Key;
import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;
import liuliu.qs.view.IUser;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/3.
 */
interface IUserMView {
    void getUser();
}

public class UserListener implements IUserMView {
    IUser mView;

    public UserListener(IUser mView) {
        this.mView = mView;
    }

    @Override
    public void getUser() {
        HttpUtil.load()
                .getUserById(Utils.getCache(Key.KEY_UserId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getSuccess())) {
                        mView.setUserInfo(model.getUserdata());
                    } else {
                        mView.setUserInfo(null);
                    }
                }, error -> {
                    mView.setUserInfo(null);
                });
    }
}
