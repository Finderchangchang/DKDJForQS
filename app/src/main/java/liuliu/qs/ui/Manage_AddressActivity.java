package liuliu.qs.ui;

import android.content.Intent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.view.TitleBar;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.listener.AddressManageListener;
import liuliu.qs.model.PoiModel;
import liuliu.qs.view.IAddressManage;


/**
 * 添加修改地址
 * Created by Administrator on 2016/12/2.
 */

public class Manage_AddressActivity extends BaseActivity implements IAddressManage {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.send_address_tv)
    TextView sendAddressTv;
    @Bind(R.id.address_title_tv)
    TextView addressTitleTv;
    @Bind(R.id.search_poi_ll)
    LinearLayout searchPoiLl;
    @Bind(R.id.mp_et)
    EditText mpEt;
    AddressManageListener manageListener;
    boolean isSave = true;//false，修改

    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        manageListener = new AddressManageListener(this);
        Serializable mdd = getIntent().getSerializableExtra("model");
        if (mdd != null) {
            model = (PoiModel) mdd;
            refreshUi();
            isSave = false;
        }
    }

    PoiModel model;

    @Override
    public void initEvents() {
        titleBar.setRightClick(() -> {
            if (model == null) {
                ToastShort("地址没有选择");
            } else {
                if (isSave) {
                    model.setState(1);
                } else {
                    model.setState(0);
                }
                model.setDetailAddress(mpEt.getText().toString().trim());
                manageListener.addAddress(model);
            }
        });
        searchPoiLl.setOnClickListener(v -> {
            Intent intent = new Intent(Manage_AddressActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, 0);
        });
        refreshUi();
    }

    @Override
    public void manageResult(boolean result) {

    }

    /**
     * 刷新当前页面数据
     */
    private void refreshUi() {
        if (model != null) {
            addressTitleTv.setText(model.getPoiName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 8://选择地址页面跳转回来
                model = (PoiModel) data.getSerializableExtra("val");
                refreshUi();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
