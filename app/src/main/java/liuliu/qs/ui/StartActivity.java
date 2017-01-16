package liuliu.qs.ui;

import android.text.TextUtils;

import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.method.Utils;

/**
 * 启动页面
 * Created by Administrator on 2016/12/5.
 */

public class StartActivity extends BaseActivity {
    @Override
    public void initViews() {
        setContentView(R.layout.activity_start);
    }

    @Override
    public void initEvents() {
        if (TextUtils.isEmpty(Utils.getCache("UserId"))) {
            Utils.IntentPost(LoginActivity.class);
        } else {
            Utils.IntentPost(MainActivity.class);
        }
        finish();
    }
}
