package liuliu.qs.ui;

import android.widget.ListView;

import net.tsz.afinal.view.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.config.Key;
import liuliu.qs.method.CommonAdapter;
import liuliu.qs.method.CommonViewHolder;
import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;
import liuliu.qs.model.JiaoYiModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/1/11.
 */

public class JiaoYiListActivity extends BaseActivity {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.main_lv)
    ListView mainLv;
    String title;
    List<JiaoYiModel.OrderlistBean> orderlist;
    CommonAdapter<JiaoYiModel.OrderlistBean> commonAdapter;

    @Override
    public void initViews() {
        setContentView(R.layout.ac_jy_list);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        titleBar.setCenter_str(title);
        titleBar.setLeftClick(() -> finish());
        orderlist = new ArrayList<>();
        commonAdapter = new CommonAdapter<JiaoYiModel.OrderlistBean>(this, orderlist, R.layout.item_jl) {
            @Override
            public void convert(CommonViewHolder holder, JiaoYiModel.OrderlistBean model, int position) {
                holder.setText(R.id.time_tv, model.getAddtime());
                holder.setText(R.id.price_tv, model.getSendFee());
            }
        };
        mainLv.setAdapter(commonAdapter);
    }

    @Override
    public void initEvents() {
        Map<String, String> map = new HashMap<>();
        map.put("did", Utils.getCache(Key.KEY_UserId));
        map.put("pagesize", "100");
        map.put("pageindex", "1");
        switch (title) {
            case "交易明细":
                HttpUtil.load()
                        .getJYList(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(model -> {
                            commonAdapter.refresh(model.getOrderlist());
                        }, error -> {

                        });
                break;
            case "充值记录":
                HttpUtil.load()
                        .getCZList(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(model -> {
                            commonAdapter.refresh(model.getOrderlist());
                        }, error -> {
                            String s = "";
                        });
                break;
        }

    }
}
