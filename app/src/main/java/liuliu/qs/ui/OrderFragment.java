package liuliu.qs.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import liuliu.qs.R;
import liuliu.qs.base.BaseApplication;
import liuliu.qs.listener.OrderListener;
import liuliu.qs.method.CommonAdapter;
import liuliu.qs.method.CommonViewHolder;
import liuliu.qs.method.RefreshLayout;
import liuliu.qs.method.Utils;
import liuliu.qs.model.OrderListModel;
import liuliu.qs.model.OrderModel;
import liuliu.qs.view.IOrder;

/**
 * Created by Administrator on 2016/12/2.
 */

public class OrderFragment extends Fragment implements IOrder {
    CommonAdapter mAdapter;
    ListView lv;
    List<OrderModel.OrderlistBean> order;
    int tab_index;
    int page_num = 0;//当前页数
    RefreshLayout refresh_rfl;
    Dialog bottom_dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_list, null, false);
        lv = (ListView) view.findViewById(R.id.address_list_lv);
        refresh_rfl = (RefreshLayout) view.findViewById(R.id.refresh_rfl);
        order = new ArrayList<>();
        inflate = LayoutInflater.from(OrderListActivity.mIntails).inflate(R.layout.dialog_pay, null);
        bottom_dialog = new Dialog(OrderListActivity.mIntails, R.style.ActionSheetDialogStyle);
        mAdapter = new CommonAdapter<OrderModel.OrderlistBean>(BaseApplication.getContext(), order, R.layout.item_order) {
            @Override
            public void convert(CommonViewHolder holder, OrderModel.OrderlistBean model, int position) {
                holder.setText(R.id.time_tv, model.getOrderDateTime() + "  " + model.getSendfee() + "元");
                holder.setText(R.id.tel_tv, model.getDphone());
                String address = model.getOrderAddress();
                if (!TextUtils.isEmpty(address)) {
                    if (address.length() > 20) {
                        address = address.substring(0, 20) + "...";
                    }
                    holder.setText(R.id.address_tv, address);
                }
                switch (model.getSendstate()) {
                    case "0":
                        holder.setText(R.id.state_tv, "未接单");
                        break;
                    case "1":
                        holder.setText(R.id.state_tv, "已接单");
                        break;
                    case "2":
                        holder.setText(R.id.state_tv, "配送中");
                        break;
                    case "3":
                        holder.setText(R.id.state_tv, "已完成");
                        break;
                    default:
                        holder.setText(R.id.state_tv, "到达商家");
                        break;
                }
            }
        };
        lv.setAdapter(mAdapter);
        refresh_rfl.setLoading(true);
        refresh_rfl.setOnLoadListener(() -> {
            if (listener != null) {
                if (!bottom) {
                    listener.loadOrder(page_num++, tab_index + "");
                }
            } else {
                refresh_rfl.setLoading(false);
            }
        });
        refresh_rfl.setOnRefreshListener(() -> {
            page_num = 1;
            listener.loadOrder(page_num, tab_index + "");
        });
        lv.setOnItemClickListener((parent, view1, position, id) -> {
            Utils.IntentPost(OrderDetailActivity.class, intent -> intent.putExtra("orderid", order.get(position).getOrderid()));
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 99://取消成功通知刷新
                refreshList(tab_index);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    boolean pay_is_wx = true;
    private View inflate;

    private void showDialog(String orderId, String price) {
        //将布局设置给Dialog
        bottom_dialog.setContentView(inflate);
        Button pay_btn = (Button) inflate.findViewById(R.id.pay_btn);
        TextView pay_tv = (TextView) inflate.findViewById(R.id.pay_tv);
        RelativeLayout wx_pay_rl = (RelativeLayout) inflate.findViewById(R.id.wx_pay_rl);
        RelativeLayout zfb_pay_rl = (RelativeLayout) inflate.findViewById(R.id.zfb_pay_rl);
        CheckBox wx_cb = (CheckBox) inflate.findViewById(R.id.wx_cb);
        CheckBox zfb_cb = (CheckBox) inflate.findViewById(R.id.zfb_cb);
        wx_cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            zfb_cb.setChecked(!isChecked);
            pay_is_wx = isChecked;
        });
        zfb_cb.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            wx_cb.setChecked(!isChecked);
            pay_is_wx = !isChecked;
        }));
        wx_pay_rl.setOnClickListener(v -> {
            wx_cb.setChecked(true);
            zfb_cb.setChecked(false);
        });
        zfb_pay_rl.setOnClickListener(v -> {
            wx_cb.setChecked(false);
            zfb_cb.setChecked(true);
        });
        pay_tv.setText(price + "元");
        ImageView dialog_close_iv = (ImageView) inflate.findViewById(R.id.dialog_close_iv);
        dialog_close_iv.setOnClickListener(v -> bottom_dialog.dismiss());//关闭当前dialog
        //获取当前Activity所在的窗体
        Window dialogWindow = bottom_dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        int intw = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int inth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        inflate.measure(intw, inth);
        int intheight = inflate.getMeasuredHeight();

        WindowManager.LayoutParams lp = bottom_dialog.getWindow().getAttributes();
        Display display = OrderListActivity.mIntails.getWindowManager().getDefaultDisplay();
        lp.width = display.getWidth(); //设置宽度
        lp.y = -intheight;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        bottom_dialog.show();//显示对话框
    }

    OrderListener listener;

    public void refreshList(int position) {
        tab_index = position;
        if (listener == null) {
            listener = new OrderListener(this);
        }
        page_num = 1;
        listener.loadOrder(page_num, position + "");
    }

    boolean bottom = false;


    @Override
    public void refreshOrder(List<OrderModel.OrderlistBean> model) {
        if (model != null) {
            if (page_num > 1) {
                for (OrderModel.OrderlistBean orderModel : model) {
                    order.add(orderModel);
                }
                mAdapter.refresh(order);
            } else {
                order = new ArrayList<>();
                order = model;
                mAdapter.refresh(order);
                page_num = 1;
            }
        }
        if (refresh_rfl != null) {
            refresh_rfl.setRefreshing(false);
        }
//        loadMore(model);
    }

    boolean isBottom;//没到底部

    /**
     * 判断是否有加载更多。。。
     *
     * @param model
     */
    private void loadMore(List<OrderListModel> model) {
        if (model != null) {
//            if (model() != null && model.getPage() != null) {
//                if (("0").equals(model.getTotal()) || model.getTotal().equals(model.getPage())) {
//                    isBottom = true;
//                    bottom = true;
//                    refresh_rfl.closeBottom();
//                } else {
//                    isBottom = false;
//                    bottom = false;
//                    refresh_rfl.setLoading(isBottom);
//                }
//            } else {
//                isBottom = false;
//                bottom = false;
//                refresh_rfl.setLoading(isBottom);
//            }
        }
    }

    @Override
    public void loadMoreOrder(OrderListModel model) {

    }

    @Override
    public void changeStateResult(boolean result) {

    }
}
