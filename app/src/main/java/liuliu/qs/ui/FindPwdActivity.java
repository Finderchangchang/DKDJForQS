package liuliu.qs.ui;

import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
 * Created by Administrator on 2016/11/28.
 */

public class FindPwdActivity extends BaseActivity {
    @Bind(R.id.tel_tv)
    TextView telTv;
    @Bind(R.id.code_et)
    EditText codeEt;
    @Bind(R.id.getcode_btn)
    Button getcodeBtn;
    @Bind(R.id.pwd_et)
    EditText pwdEt;
    @Bind(R.id.pwd_confirm_et)
    EditText pwdConfirmEt;
    @Bind(R.id.reg_user_btn)
    Button regUserBtn;
    String tel = "";
    String code = "";
    @Bind(R.id.title_bar)
    TitleBar title_bar;
    boolean isUpdate = false;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);
        title_bar.setLeftClick(() -> finish());
        tel = getIntent().getStringExtra(Key.KEY_TEL);
        if (tel == null) {
            tel = Utils.getCache("tel");
            isUpdate = true;
        }
        if (tel.length() == 11) {
            telTv.setText(tel.replace(tel.substring(3, 7), "****"));
        }
    }

    @Override
    public void initEvents() {
        getcodeBtn.setOnClickListener(v -> {
            recLen = 60;
            handler.postDelayed(runnable, 1000);
            Map<String, String> map = new HashMap<>();
            map.put("phone", tel);
            map.put("type", "1");
            HttpUtil.load()
                    .getCode(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(model -> {
                        if (("1").equals(model.getSuccess())) {
                            if (model.getData() != null) {
                                code = model.getData().getCode();
                            } else {
                                recLen = 0;
                                ToastShort("获取验证码失败");
                            }
                        } else {
                            recLen = 0;
                            ToastShort("获取验证码失败");
                        }
                    }, error -> {
                        recLen = 0;
                        ToastShort("请检查网络连接" + error);
                    });
        });
        regUserBtn.setOnClickListener(v -> {
            if (!code.equals(codeEt.getText().toString().trim())) {
                ToastShort("验证码不正确");
            } else if (!(pwdConfirmEt.getText().toString().trim()).equals(pwdEt.getText().toString().trim()) && !("").equals(pwdEt.getText().toString().trim())) {
                ToastShort("前后密码不一致");
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("username", tel);
                map.put("password", pwdConfirmEt.getText().toString().trim());
                HttpUtil.load()
                        .findPwd(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(model -> {
                            if (("1").equals(model.getSuccess())) {
                                if (model.getData() != null) {
                                    if (!isUpdate) {
                                        Utils.putCache(Key.KEY_UserId, model.getData().getUserId());
                                        Utils.putCache("tel", tel);
                                        LoginActivity.mIntail.finish();
                                        ForgetPwd_CheckCodeActivity.mIntail.finish();
                                    } else {
                                        ToastShort("修改成功~~");
                                    }
                                    finish();
                                } else {
                                    recLen = 0;
                                    ToastShort("获取验证码失败");
                                }
                            } else {
                                recLen = 0;
                                ToastShort("获取验证码失败");
                            }
                        }, error -> {
                            recLen = 0;
                            ToastShort("请检查网络连接" + error);
                        });
            }
        });
    }

    int recLen = 60;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (recLen > 0) {
                recLen--;
                getcodeBtn.setEnabled(false);
                getcodeBtn.setText(recLen + "秒后重发");
                handler.postDelayed(this, 1000);
            } else {
                getcodeBtn.setEnabled(true);
                getcodeBtn.setText("重新发送");
            }
        }
    };
}
