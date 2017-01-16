package liuliu.qs.ui;

import android.widget.TextView;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.model.FeiModel;


/**
 * 价格明细
 * Created by Administrator on 2016/12/1.
 */

public class PriceDetailActivity extends BaseActivity {
    @Bind(R.id.total_price_tv)
    TextView totalPriceTv;
    @Bind(R.id.juli_tv)
    TextView juliTv;
    @Bind(R.id.qibu_price_tv)
    TextView qibuPriceTv;
    @Bind(R.id.licheng_price_tv)
    TextView lichengPriceTv;
    FeiModel feiyong;
    @Bind(R.id.title_bar)
    TitleBar bar;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_price_detail);
        ButterKnife.bind(this);
        feiyong = (FeiModel) getIntent().getSerializableExtra("model");
    }

    @Override
    public void initEvents() {
        bar.setLeftClick(() -> {
            PriceDetailActivity.this.finish();
        });
        totalPriceTv.setText("￥" + feiyong.getTotalfee());
        juliTv.setText(feiyong.getAlljuli() + "公里");
        qibuPriceTv.setText(feiyong.getQibufee());
        lichengPriceTv.setText(feiyong.getLichengfee());
    }
}
