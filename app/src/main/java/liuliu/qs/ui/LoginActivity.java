package liuliu.qs.ui;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.listener.LoginListener;
import liuliu.qs.method.Utils;
import liuliu.qs.view.ILogin;

/**
 * Created by Administrator on 2016/11/26.
 */

public class LoginActivity extends BaseActivity implements ILogin {

    @Bind(R.id.title_bar)
    TitleBar title_bar;
    LoginListener mListener;
    public static LoginActivity mIntail;
    @Bind(R.id.tel_et)
    EditText telEt;
    @Bind(R.id.pwd_et)
    EditText pwdEt;
    @Bind(R.id.login_btn)
    Button loginBtn;

    @Override
    public void initViews() {
        setContentView(R.layout.ac_login);
        ButterKnife.bind(this);
        mListener = new LoginListener(this);
        mIntail = this;
    }

    @Override
    public void initEvents() {
        title_bar.setLeftClick(() -> finish());
        loginBtn.setOnClickListener(v -> {
            if (!Utils.isMobileNo(telEt.getText().toString().trim())) {
                ToastShort("请输入正确的手机号码");
            } else if (TextUtils.isEmpty(pwdEt.getText().toString().trim())) {
                ToastShort("密码不能为空");
            } else {
                mListener.login(telEt.getText().toString().trim(), pwdEt.getText().toString().trim());
                loginBtn.setEnabled(false);
            }
        });
    }


    /**
     * 账号密码登录处理
     *
     * @param result 登录成功
     */
    @Override
    public void loginResult(boolean result, String errorMsg) {
        loginBtn.setEnabled(true);
        if (result) {//登录成功
            Utils.putCache("tel", telEt.getText().toString().trim());
            Utils.IntentPost(MainActivity.class);
            finish();
        } else {
            ToastShort(errorMsg);
        }
    }
}
