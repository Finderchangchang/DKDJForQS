package liuliu.qs.ui;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.view.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.config.Key;
import liuliu.qs.listener.LoginListener;
import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;
import liuliu.qs.view.IRequestCode;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 找回密码，验证验证码
 * Created by Administrator on 2016/11/28.
 */

public class ForgetPwd_CheckCodeActivity extends BaseActivity implements TextWatcher, IRequestCode {
    boolean isForget = true;//默认显示找回密码
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.title_iv)
    ImageView titleIv;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.tel_et)
    EditText telEt;
    @Bind(R.id.tel_send_tv)
    TextView telSendTv;
    @Bind(R.id.login_btn)
    Button login_btn;
    String tel;
    String code;
    int recLen = 60;
    @Bind(R.id.code1_et)
    EditText code1Et;
    @Bind(R.id.code2_et)
    EditText code2Et;
    @Bind(R.id.code3_et)
    EditText code3Et;
    @Bind(R.id.code4_et)
    EditText code4Et;
    @Bind(R.id.code_ll)
    LinearLayout code_ll;
    public static ForgetPwd_CheckCodeActivity mIntail;
    LoginListener mListener;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);
//        mListener = new LoginListener(this);
        tel = getIntent().getStringExtra(Key.KEY_TEL);
        code = getIntent().getStringExtra(Key.KEY_Code);
        mIntail = this;
        titleBar.setLeftClick(() -> finish());
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (recLen > 0) {
                recLen--;
                if (recLen == 0) {
                    telSendTv.setText("");
                } else {
                    telSendTv.setText(tel + " " + recLen + "秒后重发");
                }
                handler.postDelayed(this, 1000);
            } else {
                login_btn.setVisibility(View.VISIBLE);
                login_btn.setEnabled(true);
                login_btn.setText("重新获取");
            }
        }
    };
    String code_str = "";

    @Override
    public void initEvents() {
        telEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    login_btn.setEnabled(true);
                    login_btn.setBackgroundResource(R.drawable.chongzhi_bg);
                } else {
                    login_btn.setEnabled(false);
                    login_btn.setBackgroundResource(R.drawable.normal_bg);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (tel == null) {
            isForget = true;
            telSendTv.setVisibility(View.GONE);
            telEt.setVisibility(View.VISIBLE);
            code_ll.setVisibility(View.GONE);
        } else {
            isForget = false;
            titleTv.setText("输入验证码");
            telEt.setVisibility(View.GONE);
            code_ll.setVisibility(View.VISIBLE);
            telSendTv.setVisibility(View.VISIBLE);
            handler.postDelayed(runnable, 1000);
            login_btn.setVisibility(View.GONE);
        }
        login_btn.setOnClickListener(v -> {//忘记密码
            login_btn.setEnabled(false);
            if (("下一步").equals(login_btn.getText().toString())) {
                login_btn.setEnabled(true);
                String tel = telEt.getText().toString().trim();
                if (!("").equals(tel) && Utils.isMobileNo(tel)) {
                    Utils.IntentPost(FindPwdActivity.class, listener -> {
                        listener.putExtra(Key.KEY_TEL, tel);
                    });
                } else {
                    ToastShort("手机号码不能为空或格式不正确");
                }
            } else {
                recLen = 60;
                handler.postDelayed(runnable, 1000);
//                mListener.reqCode(tel);
            }
        });
        code1Et.addTextChangedListener(this);
        code2Et.addTextChangedListener(this);
        code3Et.addTextChangedListener(this);
        code4Et.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (("").equals(code1Et.getText().toString())) {
            code1Et.requestFocus();
        } else if (("").equals(code2Et.getText().toString())) {
            code2Et.requestFocus();
        } else if (("").equals(code3Et.getText().toString())) {
            code3Et.requestFocus();
        } else if (("").equals(code4Et.getText().toString())) {
            code4Et.requestFocus();
        } else {
            code_str = code1Et.getText().toString() + code2Et.getText().toString() + code3Et.getText().toString() + code4Et.getText().toString();
            if (code.equals(code_str) && code_str.length() == 4) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", tel);
                map.put("type", "2");
                map.put("source", "2");
                HttpUtil.load()
                        .login(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(model -> {
                            if (("1").equals(model.getSuccess())) {
                                if (LoginActivity.mIntail != null) {
                                    LoginActivity.mIntail.finish();//关闭登录页面
                                }
                                ForgetPwd_CheckCodeActivity.this.finish();//关闭当前页面
                                if (model.getData() != null) {
                                    Utils.putCache(Key.KEY_UserId, model.getData().getUserId());
                                    Utils.putCache("tel", tel);
                                }
                            } else {
                                ToastShort(model.getErrorMsg());
                            }
                        }, error -> {
                            ToastShort("登录失败，请检查网络连接");
                        });
            } else {
                ToastShort("验证码输入错误，请重新输入");
            }
        }
    }

    @Override
    public void reqCodeResult(boolean result, String code) {
        if (result) {
            if (code != null) {
                ToastShort("发送成功");
            } else {
                ToastShort("验证码不正确，请重新获取");
            }
        } else {
            login_btn.setEnabled(true);
            recLen = 0;
            if (code != null) {
                ToastShort(code);
            } else {
                ToastShort("请检查网络连接");
            }
        }
    }
}
