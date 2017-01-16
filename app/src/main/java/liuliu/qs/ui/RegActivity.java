package liuliu.qs.ui;

import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import net.tsz.afinal.view.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/28.
 */

public class RegActivity extends BaseActivity {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.tel_et)
    EditText telEt;
    @Bind(R.id.send_code_btn)
    Button sendCodeBtn;
    @Bind(R.id.code_et)
    EditText codeEt;
    @Bind(R.id.pwd_et)
    EditText pwdEt;
    @Bind(R.id.pwd_confirm_et)
    EditText pwdConfirmEt;
    @Bind(R.id.login_btn)
    Button loginBtn;
    String code = "";//验证码
    int recLen = 60;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
    }

    @Override
    public void initEvents() {
        titleBar.setLeftClick(() -> finish());
        sendCodeBtn.setOnClickListener(v -> {
            recLen = 60;
            handler.postDelayed(runnable, 1000);
            String tel = telEt.getText().toString().trim();
            if (Utils.isMobileNo(tel)) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone", tel);
                map.put("type", "0");
                HttpUtil.load()
                        .getCode(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(model -> {
                            if (("1").equals(model.getSuccess())) {
                                ToastShort("发送成功");
                                if (model.getData() != null) {
                                    code = model.getData().getCode();
                                }
                            } else {
                                recLen = 0;
                                ToastShort(model.getErrorMsg());
                            }
                        }, error -> {
                            recLen = 0;
                            ToastShort("请检查网络连接");
                        });
            }
        });
        loginBtn.setOnClickListener(v -> {
            if (("").equals(telEt.getText().toString().trim()) && !Utils.isMobileNo(telEt.getText().toString().trim())) {
                ToastShort("手机号码不能为空或格式不正确");
            } else if (("").equals(codeEt.getText().toString().trim())) {
                ToastShort("验证码不能为空");
            } else if (!(pwdConfirmEt.getText().toString().trim()).equals(pwdEt.getText().toString().trim()) && !("").equals(pwdEt.getText().toString().trim())) {
                ToastShort("前后密码不一致");
            } else if (!code.equals(codeEt.getText().toString().trim())) {
                ToastShort("验证码不正确");//15231287220
            } else {
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", telEt.getText().toString().trim());
                map.put("password", pwdEt.getText().toString().trim());
                map.put("source", "2");//
                map.put("tokens", "");
                map.put("clientid", Utils.getCache("cid"));
                HttpUtil.load()
                        .regUser(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(model -> {
                            if (("1").equals(model.getSuccess())) {
                                if (model.getData() != null) {
                                    String userid = model.getData().getUserId();//用户id
                                    String tel = model.getData().getUserTel();//用户电话
                                    Map<String, String> maps = new HashMap<String, String>();
                                    maps.put("UserId", userid);
                                    maps.put("tel", tel);
                                    Utils.putCache(maps);
                                    LoginActivity.mIntail.finish();
                                    Utils.IntentPost(MainActivity.class);
                                    finish();
                                }
                            } else {
                                ToastShort(model.getErrorMsg());
                            }
                        }, error -> {
                            ToastShort("请检查网络连接" + error);
                        });
            }
        });

    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (recLen > 0) {
                recLen--;
                sendCodeBtn.setEnabled(false);
                sendCodeBtn.setText(recLen + "秒后重发");
                handler.postDelayed(this, 1000);
            } else {
                sendCodeBtn.setEnabled(true);
                sendCodeBtn.setText("重新发送");
            }
        }
    };
}
