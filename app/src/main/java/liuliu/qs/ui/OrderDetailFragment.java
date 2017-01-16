package liuliu.qs.ui;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import liuliu.qs.R;
import liuliu.qs.config.Key;
import liuliu.qs.listener.OrderListener;
import liuliu.qs.method.WxUtil;
import liuliu.qs.model.OrderDetailModel;
import liuliu.qs.view.IOrderDetail;
import liuliu.qs.wxapi.PayResult;
import liuliu.qs.wxapi.SignUtils;

/**
 * Created by Administrator on 2016/12/2.
 */

public class OrderDetailFragment extends Fragment implements IOrderDetail {
    int tab_index;
    String orderid;
    OrderListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    LinearLayout order_list_ll;
    ListView order_state_lv;
    ImageView state1_iv;
    TextView state1_tv;
    TextView state1_time_tv;
    TextView state1_orderid_tv;
    ImageView state2_iv;
    TextView state2_tv;
    TextView state2_time_tv;
    ImageView state3_iv;
    TextView state3_tv;
    TextView state3_time_tv;
    ImageView state4_iv;
    TextView state4_tv;
    TextView state4_time_tv;
    ImageView state5_iv;
    TextView state5_tv;
    TextView state5_time_tv;
    ImageView state6_iv;
    TextView state6_tv;
    TextView state6_time_tv;
    LinearLayout order_state_ll;
    LinearLayout order2_ll;
    LinearLayout order3_ll;
    LinearLayout order4_ll;
    LinearLayout order5_ll;
    LinearLayout order6_ll;
    TextView good_type_tv;
    TextView order_state_tv;
    TextView order_time_tv;
    TextView send_price_tv;
    TextView order_id_tv;
    TextView jl_tv;
    TextView q_address_tv;
    LinearLayout q_tel_ll;
    LinearLayout s_tel_ll;
    TextView s_address_tv;
    LinearLayout qs_ll;
    TextView qs_name_tv;
    TextView remark_tv;
    TextView pay_state_tv;
    Button pay_btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_order_detail, null, false);
        order_list_ll = (LinearLayout) view.findViewById(R.id.order_list_ll);
        order_state_lv = (ListView) view.findViewById(R.id.order_state_lv);
        order_state_ll = (LinearLayout) view.findViewById(R.id.order_state_ll);
        state1_iv = (ImageView) view.findViewById(R.id.state1_iv);
        state1_tv = (TextView) view.findViewById(R.id.state1_tv);
        state1_time_tv = (TextView) view.findViewById(R.id.state1_time_tv);
        state1_orderid_tv = (TextView) view.findViewById(R.id.state1_orderid_tv);
        state2_iv = (ImageView) view.findViewById(R.id.state2_iv);
        state2_tv = (TextView) view.findViewById(R.id.state2_tv);
        state2_time_tv = (TextView) view.findViewById(R.id.state2_time_tv);
        state3_iv = (ImageView) view.findViewById(R.id.state3_iv);
        state3_tv = (TextView) view.findViewById(R.id.state3_tv);
        state3_time_tv = (TextView) view.findViewById(R.id.state3_time_tv);
        state4_iv = (ImageView) view.findViewById(R.id.state4_iv);
        state4_tv = (TextView) view.findViewById(R.id.state4_tv);
        state4_time_tv = (TextView) view.findViewById(R.id.state4_time_tv);
        state5_iv = (ImageView) view.findViewById(R.id.state5_iv);
        state5_tv = (TextView) view.findViewById(R.id.state5_tv);
        state5_time_tv = (TextView) view.findViewById(R.id.state5_time_tv);
        state6_iv = (ImageView) view.findViewById(R.id.state6_iv);
        state6_tv = (TextView) view.findViewById(R.id.state6_tv);
        state6_time_tv = (TextView) view.findViewById(R.id.state6_time_tv);
        order2_ll = (LinearLayout) view.findViewById(R.id.order2_ll);
        order3_ll = (LinearLayout) view.findViewById(R.id.order3_ll);
        order4_ll = (LinearLayout) view.findViewById(R.id.order4_ll);
        order5_ll = (LinearLayout) view.findViewById(R.id.order5_ll);
        order6_ll = (LinearLayout) view.findViewById(R.id.order6_ll);

        order_state_tv = (TextView) view.findViewById(R.id.order_state_tv);
        order_time_tv = (TextView) view.findViewById(R.id.order_time_tv);
        send_price_tv = (TextView) view.findViewById(R.id.send_price_tv);
        order_id_tv = (TextView) view.findViewById(R.id.order_id_tv);
        jl_tv = (TextView) view.findViewById(R.id.jl_tv);
        q_address_tv = (TextView) view.findViewById(R.id.q_address_tv);
        s_address_tv = (TextView) view.findViewById(R.id.s_address_tv);
        qs_name_tv = (TextView) view.findViewById(R.id.qs_name_tv);
        remark_tv = (TextView) view.findViewById(R.id.remark_tv);
        pay_state_tv = (TextView) view.findViewById(R.id.pay_state_tv);
        pay_btn = (Button) view.findViewById(R.id.pay_btn);
        q_tel_ll = (LinearLayout) view.findViewById(R.id.q_tel_ll);
        s_tel_ll = (LinearLayout) view.findViewById(R.id.s_tel_ll);
        qs_ll = (LinearLayout) view.findViewById(R.id.qs_ll);
        good_type_tv = (TextView) view.findViewById(R.id.good_type_tv);
        mListener = new OrderListener(this);
        mList = new ArrayList<>();
        inflate = LayoutInflater.from(OrderDetailsActivity.mInstails).inflate(R.layout.dialog_pay, null);
        bottom_dialog = new Dialog(OrderDetailsActivity.mInstails, R.style.ActionSheetDialogStyle);
        if (tab_index == 0) {//订单详情
            order_list_ll.setVisibility(View.VISIBLE);
            order_state_ll.setVisibility(View.GONE);
        } else {
            order_list_ll.setVisibility(View.GONE);
            order_state_ll.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public void getOrderDetail(String orderid, int position) {
        orderid = orderid;
        tab_index = position;
        if (mListener == null) {
            mListener = new OrderListener(this);
        }
        mListener.getOrderDetail(orderid);
        if (order_list_ll != null) {
            if (position == 0) {//订单详情
                order_list_ll.setVisibility(View.VISIBLE);
                order_state_ll.setVisibility(View.GONE);
            } else {
                order_list_ll.setVisibility(View.GONE);
                order_state_ll.setVisibility(View.VISIBLE);
            }
        }
    }

    List<String> mList;

    @Override
    public void loadResult(OrderDetailModel model) {
        if (model != null) {
            order_id_tv.setText(model.getOrderid());
            order_time_tv.setText(model.getOrderTime());
            if (model.getQAddress() == null || ("").equals(model.getQAddress())) {
                q_address_tv.setText("任意地址购买");
            } else {
                q_address_tv.setText(model.getQAddress());
            }
            s_address_tv.setText(model.getSAddress());
            //odShouPhone.setText(model.getSTell());
//            model.getDeliverPhone()
            q_tel_ll.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + model.getQTell()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            });
            s_tel_ll.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + model.getSTell()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            });
            qs_ll.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + model.getDeliverPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            });
            qs_name_tv.setText(model.getDeliverName());
            send_price_tv.setText(model.getTotalPrice() + "元");
            jl_tv.setText("约" + model.getJuLi() + "公里");
            good_type_tv.setText(model.getOrderType());
            remark_tv.setText(model.getRemark());
            state1_time_tv.setText(model.getOrderTime());
            String state = getState(model);
            state1_orderid_tv.setText("订单号：" + model.getOrderid());
            switch (state) {
                case "未支付":
                    order6_ll.setVisibility(View.GONE);
                    order5_ll.setVisibility(View.GONE);
                    order4_ll.setVisibility(View.GONE);
                    order3_ll.setVisibility(View.GONE);
                    state2_tv.setText("订单未支付");
                    order2_ll.setVisibility(View.GONE);
                    state1_iv.setImageResource(R.mipmap.state1_pressed);
                    pay_btn.setVisibility(View.VISIBLE);
                    pay_btn.setOnClickListener(v -> {
                        showDialog(model.getOrderid(), model.getTotalPrice());
                    });
                    pay_state_tv.setText("未支付");
                    break;
                case "待接单":
                case "取消":
                    order6_ll.setVisibility(View.GONE);
                    order5_ll.setVisibility(View.GONE);
                    order4_ll.setVisibility(View.GONE);
                    if (("待接单").equals(state)) {
                        state3_tv.setText("等待骑士接单");
                        state3_iv.setImageResource(R.mipmap.state3_pressed);
                    } else {
                        state3_tv.setText("订单已取消");
                        state3_iv.setImageResource(R.mipmap.order_cancle);
                    }
                    if (model.getDeliverQiangDate() != null) {
                        if (!model.getDeliverQiangDate().contains("1900")) {
                            state3_time_tv.setText(model.getDeliverQiangDate());
                        }
                    }
                    break;
                case "进行中":
                    order6_ll.setVisibility(View.GONE);
                    order5_ll.setVisibility(View.GONE);
                    if (model.getDeliverDaoDate() != null) {
                        if (!model.getDeliverDaoDate().contains("1900")) {
                            state4_time_tv.setText(model.getDeliverDaoDate());
                        }
                    }
                    state4_iv.setImageResource(R.mipmap.state4_pressed);
                    break;
                case "配送中":
                    order6_ll.setVisibility(View.GONE);
                    state5_iv.setImageResource(R.mipmap.state5_pressed);
                    state5_time_tv.setText(model.getDeliverZouDate());
                    break;
                case "完成":
                    state6_time_tv.setText(model.getSLngDeliverWanDate());
                    break;
            }
        }
    }

    //    未支付：orderStatus=2 paystate=0
//    待接单：orderStatus=7 sendstate=0
//    进行中： orderStatus=7 sendstate in (1,5)
//    配送中： orderStatus=7 sendstate=2
//    完成：orderStatus=3
//    取消：orderStatus=4 IsShopSet=2
    private String getState(OrderDetailModel model) {
        String state = "";
        switch (model.getOrderStatus()) {
            case "2":
                if (("0").equals(model.getPayState())) {
                    state = "未支付";
                }
                break;
            case "7":
                switch (model.getSendState()) {
                    case "0":
                        state = "待接单";
                        break;
                    case "2":
                        state = "配送中";
                        break;
                    default:
                        state = "进行中";
                        break;
                }
                break;
            case "3":
                state = "完成";
                break;
            case "4":
                if (("2").equals(model.getIsShopSet())) {
                    state = "取消";
                }
                break;
        }
        return state;
    }

    boolean pay_is_wx = true;
    private View inflate;
    Dialog bottom_dialog;

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
        pay_btn.setOnClickListener(v -> {
            if (orderId != null) {
                if (!pay_is_wx) {
                    String orderInfo = getOrderInfo("大可快跑", "大可快跑Android支付", price, orderId);
                    String sign = sign(orderInfo);
                    try {
                        sign = URLEncoder.encode(sign, "UTF-8");
                    } catch (UnsupportedEncodingException e) {

                    }
                    /**
                     * 完整的符合支付宝参数规范的订单信息
                     */
                    final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
                    Runnable payRunnable = () -> {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(OrderListActivity.mIntails);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(payInfo, true);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    };
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } else {
                    new MyThread(orderId, price).start();
                }
            } else {

            }
        });
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

    class MyThread extends Thread {
        String Order_Id = "";
        int Order_Price;

        public MyThread(String orderId, String price) {
            Order_Id = orderId;
            Order_Price = (int) (Double.parseDouble(price) * 100);
        }

        public void run() {
            WxUtil.load(OrderDetailsActivity.mInstails, "大可快跑", "大可快跑Android支付", Order_Id, Order_Price);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (bottom_dialog != null) {
                bottom_dialog.dismiss();
            }
            switch (msg.what) {
                case 1: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OrderListActivity.mIntails, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderListActivity.mIntails, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OrderListActivity.mIntails, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price, String orderid) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Key.PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Key.SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderid + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://www.dakedaojia.com/Alipay/iosnotify.aspx" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, Key.RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
