package liuliu.qs.ui;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import net.tsz.afinal.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.listener.AddressManageListener;
import liuliu.qs.method.CommonAdapter;
import liuliu.qs.method.CommonViewHolder;
import liuliu.qs.method.RefreshLayout;
import liuliu.qs.model.PoiModel;
import liuliu.qs.view.IAddressList;
import liuliu.qs.view.IAddressManage;

/**
 * 地址管理页面
 * Created by Administrator on 2016/12/2.
 */

public class AddressActivity extends BaseActivity implements IAddressList, IAddressManage {
    @Bind(R.id.add_address_ll)
    LinearLayout addAddressLl;
    @Bind(R.id.main_lv)
    ListView mainLv;
    @Bind(R.id.empty_tv)
    TextView emptyTv;
    @Bind(R.id.refresh_rfl)
    RefreshLayout refreshRfl;
    @Bind(R.id.title_bar)
    TitleBar title_bar;
    CommonAdapter<PoiModel> mAdapter;
    List<PoiModel> mList;
    AddressManageListener mListener;

    //userid--1.添加传model。2.更新传model和aid。3.删除op，aid
    @Override
    public void initViews() {
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        mListener = new AddressManageListener(this, this);
        title_bar.setLeftClick(() -> this.finish());
        mAdapter = new CommonAdapter<PoiModel>(this, mList, R.layout.item_address) {
            @Override
            public void convert(CommonViewHolder holder, PoiModel model, int position) {
                holder.setText(R.id.title_tv, model.getPoiName());
                holder.setText(R.id.address_desc_tv, model.getPoiAddress());
                if (model.getPoiAddress() != null) {
                    holder.setText(R.id.address_bc_tv, model.getDetailAddress());
                    holder.setVisible(R.id.address_bc_tv, true);
                } else {
                    holder.setVisible(R.id.address_bc_tv, false);
                }
                holder.setOnClickListener(R.id.delete_address_ll,
                        v -> mListener.addAddress(new PoiModel(-1, model.getAid()))
                );
            }
        };
        mainLv.setAdapter(mAdapter);
        mainLv.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(AddressActivity.this, SelectAddressActivity.class);
            intent.putExtra("model", mList.get(position));
            intent.putExtra("add", mList.get(position).getAid());//false修改
            startActivityForResult(intent, 88);
        });
    }

    @Override
    public void initEvents() {
        addAddressLl.setOnClickListener(v -> {
            Intent intent = new Intent(AddressActivity.this, SelectAddressActivity.class);
            intent.putExtra("add", "true");
            startActivityForResult(intent, 88);
        });//跳转到添加页面
        mainLv.setEmptyView(emptyTv);
        refreshRfl.setOnLoadListener(() -> {//加载更多
            refreshRfl.setLoading(false);
        });
        refreshRfl.setOnRefreshListener(() -> {//刷新操作
            refreshRfl.setRefreshing(false);
        });
        mListener.loadAddressList();
    }

    @Override
    public void loadAddressResult(List<PoiModel> list) {
        mList = new ArrayList<>();
        mList = list;
        mAdapter.refresh(list);
        refreshRfl.setRefreshing(false);
    }

    PoiModel model;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 8) {
            mListener.loadAddressList();
            refreshRfl.setRefreshing(true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void manageResult(boolean result) {
        if (result) {
            mListener.loadAddressList();
            refreshRfl.setRefreshing(true);
            ToastShort("操作成功");
        } else {
            ToastShort("操作失败");
        }
    }
}
