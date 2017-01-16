package liuliu.qs.ui;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import net.tsz.afinal.view.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.config.Key;
import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/1/13.
 */

public class ChangePwdActivity extends BaseActivity {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.pwd_et)
    EditText pwdEt;
    @Bind(R.id.pwd_confirm_et)
    EditText pwdConfirmEt;
    @Bind(R.id.change_btn)
    Button changeBtn;

    @Override
    public void initViews() {
        setContentView(R.layout.ac_change_pwd);
        ButterKnife.bind(this);
        titleBar.setLeftClick(() -> finish());
    }

    @Override
    public void initEvents() {
        changeBtn.setOnClickListener(v -> {
            String old = pwdEt.getText().toString().trim();
            String pwd = pwdConfirmEt.getText().toString().trim();
            if (TextUtils.isEmpty(old)) {
                ToastShort("请输入旧密码");
            } else if (TextUtils.isEmpty(pwd)) {
                ToastShort("请输入新密码");
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("togoid", Utils.getCache(Key.KEY_UserId));
                map.put("oldpwd", old);
                map.put("password", pwd);
                HttpUtil.load()
                        .changePwd(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(model -> {
                            if (model != null) {
                                ToastShort(model.getErrorMsg());
                            } else {
                                ToastShort("修改失败，请重试");
                            }
                        }, error -> {
                            ToastShort("请检查网络连接");
                        });
            }
        });
    }
}
