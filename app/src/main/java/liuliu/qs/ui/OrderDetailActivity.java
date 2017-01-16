package liuliu.qs.ui;

import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.method.HttpUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2016/12/3.
 */

public class OrderDetailActivity extends BaseActivity {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.top1_tv)
    TextView top1Tv;
    @Bind(R.id.top2_tv)
    TextView top2Tv;
    @Bind(R.id.top3_tv)
    TextView top3Tv;
    @Bind(R.id.top4_tv)
    TextView top4Tv;
    @Bind(R.id.top5_tv)
    TextView top5Tv;
    @Bind(R.id.od_title)
    LinearLayout odTitle;
    @Bind(R.id.od_id)
    TextView odId;
    @Bind(R.id.gl_tv)
    TextView glTv;
    @Bind(R.id.fei_tv)
    TextView feiTv;
    @Bind(R.id.fb_time_tv)
    TextView fbTimeTv;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.sh_tel_tv)
    TextView shTelTv;
    @Bind(R.id.sh_address_tv)
    TextView shAddressTv;
    @Bind(R.id.qs_name_tv)
    TextView qsNameTv;
    @Bind(R.id.qs_tel_tv)
    TextView qsTelTv;
    @Bind(R.id.qs_order_tv)
    TextView qsOrderTv;
    @Bind(R.id.song_state_tv)
    TextView songStateTv;
    @Bind(R.id.shop_name_tv)
    TextView shopNameTv;
    @Bind(R.id.shop_tel_tv)
    TextView shopTelTv;
    @Bind(R.id.shop_address_tv)
    TextView shopAddressTv;
    @Bind(R.id.od_content)
    LinearLayout odContent;
    String dataid;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        dataid = getIntent().getStringExtra("orderid");
    }

    @Override
    public void initEvents() {
        titleBar.setLeftClick(() -> finish());
        HttpUtil.load()
                .getOrderDetail(dataid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(order -> {
                    if (order != null) {
                        odId.setText(order.getOrderid());
                        top1Tv.setText("下单时间\n" + order.getXiaDanTime());
                        top2Tv.setText("接单时间\n" + order.getJieDanTime());
                        top3Tv.setText("到达时间\n" + order.getDaoDaTime());
                        top4Tv.setText("离开时间\n" + order.getLiKaiTime());
                        top5Tv.setText("完成时间\n" + order.getWanChengTime());
                        glTv.setText(order.getJuLi());
                        feiTv.setText(order.getPackagefree());
                        fbTimeTv.setText(order.getSentTime());
                        shTelTv.setText(order.getPhone());
                        shAddressTv.setText(order.getAddress());
                        qsNameTv.setText(order.getDeliverName());
                        qsTelTv.setText(order.getDeliverTel());
                        qsOrderTv.setText(order.getDeliverID());
                        songStateTv.setText(order.getSendstate());
                        shopNameTv.setText(order.getShopname());
                        shopTelTv.setText(order.getShoptel());
                        shopAddressTv.setText(order.getShopAddress());
                    }
                }, error -> {

                });

    }
}
