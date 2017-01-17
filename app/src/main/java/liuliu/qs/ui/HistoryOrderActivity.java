package liuliu.qs.ui;

import android.widget.ListView;
import android.widget.TextView;

import net.tsz.afinal.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.config.Key;
import liuliu.qs.method.CommonAdapter;
import liuliu.qs.method.CommonViewHolder;
import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;
import liuliu.qs.model.TongJiModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/1/11.
 */

public class HistoryOrderActivity extends BaseActivity {
    @Bind(R.id.title_bar)
    TitleBar title_bar;
    @Bind(R.id.order1_tv)
    TextView order1Tv;
    @Bind(R.id.order2_tv)
    TextView order2Tv;
    @Bind(R.id.order3_tv)
    TextView order3Tv;
    @Bind(R.id.main_lv)
    ListView mainLv;
    CommonAdapter<TongJiModel.OrderlistBean> commonAdapter;
    List<TongJiModel.OrderlistBean> list;

    @Override
    public void initViews() {
        setContentView(R.layout.ac_history_order);
        ButterKnife.bind(this);
        title_bar.setLeftClick(() -> finish());
        list = new ArrayList<>();
        commonAdapter = new CommonAdapter<TongJiModel.OrderlistBean>(this, list, R.layout.item_tv) {
            @Override
            public void convert(CommonViewHolder holder, TongJiModel.OrderlistBean model, int position) {
                if (("10000").equals(model.getEnd())) {
                    holder.setText(R.id.tv, model.getQi() + "~" + model.getEnd() + "单：返利" + model.getMoney() + "元");
                } else {
                    holder.setText(R.id.tv, model.getQi() + "单以上：返利" + model.getMoney() + "元");
                }
            }
        };
        mainLv.setAdapter(commonAdapter);
        HttpUtil.load()
                .getOrderTongJi(Utils.getCache(Key.KEY_UserId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    order1Tv.setText(model.getTodayOrderCount());
                    order2Tv.setText(model.getThisWeekOrderCount());
                    order3Tv.setText(model.getTotalOrderCount());
                    list=model.getOrderlist();
                    commonAdapter.refresh(list);
                }, error -> {

                });
    }

    @Override
    public void initEvents() {

    }
}
