package liuliu.qs.ui;

import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.view.TitleBar;

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
 * Created by Administrator on 2017/1/11.
 */

public class QianBaoActivity extends BaseActivity {
    @Bind(R.id.title_bar)
    TitleBar title_bar;
    @Bind(R.id.ye_tv)
    TextView yeTv;
    @Bind(R.id.mx_tv)
    TextView mxTv;
    @Bind(R.id.cz_tv)
    TextView czTv;
    @Bind(R.id.czjl_ll)
    LinearLayout czjlLl;

    @Override
    public void initViews() {
        setContentView(R.layout.ac_money_package);
        ButterKnife.bind(this);
        title_bar.setLeftClick(() -> finish());
        mxTv.setOnClickListener(v -> Utils.IntentPost(JiaoYiListActivity.class, intent -> intent.putExtra("title", "交易明细")));
        czjlLl.setOnClickListener(v -> Utils.IntentPost(JiaoYiListActivity.class, intent -> intent.putExtra("title", "充值记录")));
        czTv.setOnClickListener(v -> Utils.IntentPost(PayActivity.class));

    }

    @Override
    public void initEvents() {
        HttpUtil.load()
                .getShopInfo(Utils.getCache(Key.KEY_UserId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    yeTv.setText(model.getKuaiPaoYuCun());
                }, error -> {
                    yeTv.setText("0.00");
                });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        HttpUtil.load()
                .getShopInfo(Utils.getCache(Key.KEY_UserId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    yeTv.setText(model.getKuaiPaoYuCun());
                }, error -> {
                    yeTv.setText("0.00");
                });
    }
}
