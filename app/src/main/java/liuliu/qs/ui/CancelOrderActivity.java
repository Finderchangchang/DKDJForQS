package liuliu.qs.ui;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import net.tsz.afinal.view.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.method.HttpUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 取消订单
 * Created by Finder丶畅畅 on 2016/12/3 10:38
 * QQ群481606175
 */

public class CancelOrderActivity extends BaseActivity {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.co_cb1)
    RadioButton coCb1;
    @Bind(R.id.co_cb2)
    RadioButton coCb2;
    @Bind(R.id.co_cb3)
    RadioButton coCb3;
    @Bind(R.id.co_cb4)
    RadioButton coCb4;
    @Bind(R.id.co_content)
    EditText coContent;
    @Bind(R.id.btn_co_back)
    Button btnCoBack;
    @Bind(R.id.btn_co_cancel)
    Button btnCoCancel;
    @Bind(R.id.total_rg)
    RadioGroup total_rg;
    String orderId;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_cancle_order);
        ButterKnife.bind(this);
        orderId = getIntent().getStringExtra("orderid");
    }

    @Override
    public void initEvents() {
        btnCoBack.setOnClickListener(v -> finish());
        titleBar.setLeftClick(() -> finish());
        btnCoCancel.setOnClickListener(v -> {//取消订单
            Map<String, String> map = new HashMap<>();
            map.put("orderid", orderId);
            map.put("remark", coContent.getText().toString().trim());
            HttpUtil.load()
                    .cancleOrder(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(model -> {
                        if (("1").equals(model.getSuccess())) {
                            ToastShort("取消成功");
                            setResult(99);
                            finish();//关闭当前页面
                        } else {
                            ToastShort("取消失败");
                        }
                    }, error -> {
                        ToastShort("请检查网络连接，是否正常");
                    });
        });
    }
}
