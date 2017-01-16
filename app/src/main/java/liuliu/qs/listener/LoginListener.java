package liuliu.qs.listener;

import java.util.HashMap;
import java.util.Map;

import liuliu.qs.config.Key;
import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;
import liuliu.qs.view.ILogin;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/26.
 */
interface ILoginMView {
    void login(String tel, String pwd);//账号密码登录
}

public class LoginListener implements ILoginMView {
    ILogin mView;

    public LoginListener(ILogin mView) {
        this.mView = mView;
    }

    @Override
    public void login(String tel, String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("userpwd", pwd);
        map.put("type", "2");
        map.put("token", "");
        HttpUtil.load()
                .login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getState())) {
                        Utils.putCache(Key.KEY_UserId, model.getShopid());
                        Utils.putCache("cid", model.getCityid());
                        mView.loginResult(true, null);
                    } else {
                        mView.loginResult(false, model.getMsg());
                    }
                }, error -> {
                    mView.loginResult(false, "请检查网络连接");
                });

    }
}
