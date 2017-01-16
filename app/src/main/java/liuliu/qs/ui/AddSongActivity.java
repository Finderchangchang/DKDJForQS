package liuliu.qs.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.view.TitleBar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.config.Key;
import liuliu.qs.listener.AddressManageListener;
import liuliu.qs.listener.SuanLuListener;
import liuliu.qs.method.Utils;
import liuliu.qs.method.WxUtil;
import liuliu.qs.model.FeiModel;
import liuliu.qs.model.PoiModel;
import liuliu.qs.model.SaveOrderModel;
import liuliu.qs.view.IAddBuy;
import liuliu.qs.view.IAddressManage;
import liuliu.qs.wxapi.PayResult;
import liuliu.qs.wxapi.SignUtils;

/**
 * 帮我送
 * Created by Administrator on 2016/12/1.
 */

public class AddSongActivity extends BaseActivity implements IAddBuy, IAddressManage {
    public static AddSongActivity mInstail;
    @Bind(R.id.ll_send1)
    LinearLayout llSend1;
    @Bind(R.id.ll_send2)
    LinearLayout llSend2;
    @Bind(R.id.ll_send3)
    LinearLayout llSend3;
    @Bind(R.id.title_bar)
    TitleBar title_bar;
    @Bind(R.id.change_address_iv)
    ImageView changeAddressIv;
    @Bind(R.id.buy_address_tv)
    EditText buyAddressTv;
    @Bind(R.id.buy_address_ll)
    LinearLayout buyAddressLl;
    @Bind(R.id.sc_buy_address_iv)
    ImageView scBuyAddressIv;
    @Bind(R.id.send_address_tv)
    EditText sendAddressTv;
    @Bind(R.id.send_address_ll)
    LinearLayout sendAddressLl;
    @Bind(R.id.sc_send_address_iv)
    ImageView scSendAddressIv;
    @Bind(R.id.change_address_ivs)
    ImageView changeAddressIvs;
    @Bind(R.id.price_tv)
    TextView priceTv;
    @Bind(R.id.price_detail_ll)
    LinearLayout priceDetailLl;
    @Bind(R.id.pay_btn)
    TextView payBtn;
    private boolean firstIsbuy = true;
    PoiModel buy_poi;
    PoiModel send_poi;
    @Bind(R.id.tel1_et)
    EditText tel1Et;
    @Bind(R.id.tel2_et)
    EditText tel2Et;
    @Bind(R.id.good_type_et)
    EditText goodTypeEt;
    @Bind(R.id.buy_address)
    TextView buyAddress;
    @Bind(R.id.tel1_pop_tv)
    TextView tel1PopTv;
    @Bind(R.id.ti1_tv)
    TextView ti1Tv;
    @Bind(R.id.ti2_tv)
    TextView ti2Tv;
    @Bind(R.id.address_length_tv)
    TextView address_length_tv;
    private RouteSearch routeSearch;
    private double address_length = 0;
    private SuanLuListener mListener;
    private Dialog dialog;
    private String tel1;
    private String tel2;
    private SaveOrderModel save;
    private boolean isSong = true;
    private boolean pay_is_wx = true;
    boolean isTel1 = true;
    AddressManageListener addressManageListener;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_send);
        ButterKnife.bind(this);
        mInstail = this;
        mListener = new SuanLuListener(this);
        addressManageListener = new AddressManageListener(this);
        scBuyAddressIv.setOnClickListener(v -> {//收藏购买地址
            if (firstIsbuy) {
                if (buy_poi != null) {
                    buy_poi.setState(1);
                    addressManageListener.addAddress(buy_poi);
                }
            } else {
                if (send_poi != null) {
                    send_poi.setState(1);
                    addressManageListener.addAddress(send_poi);
                }
            }
        });
        scSendAddressIv.setOnClickListener(v -> {//收藏收货地址
            if (!firstIsbuy) {
                if (buy_poi != null) {
                    buy_poi.setState(1);
                    addressManageListener.addAddress(buy_poi);
                }
            } else {
                if (send_poi != null) {
                    send_poi.setState(1);
                    addressManageListener.addAddress(send_poi);
                }
            }
        });
        save = new SaveOrderModel();
        dialog = Utils.ProgressDialog(this, "算路中，请稍后...", true);
        buy_poi = (PoiModel) getIntent().getSerializableExtra("model");
        isSong = getIntent().getBooleanExtra("isSong", true);
        title_bar.setRightClick(() -> Utils.IntentPost(WebActivity.class, intent -> intent.putExtra("web", "计价标准")));
        if (isSong) {
            title_bar.setCenter_str("送东西");
            buyAddress.setText("发货地");
            tel1PopTv.setText("发货人");
            tel1Et.setText(Utils.getCache("tel"));
            buyAddressTv.setHint("请选择发货地址");
            ti1Tv.setVisibility(View.VISIBLE);
            ti2Tv.setVisibility(View.INVISIBLE);

        } else {
            title_bar.setCenter_str("取东西");
            buyAddress.setText("取货地");
            tel2Et.setText(Utils.getCache("tel"));
            buyAddressTv.setHint("请选择取货地址");
            tel1PopTv.setText("取货人");
            ti1Tv.setVisibility(View.INVISIBLE);
            ti2Tv.setVisibility(View.VISIBLE);
        }
        tel1_ll.setOnClickListener(v -> {
            isTel1 = true;
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivityForResult(intent, 0);
        });
        tel2_ll.setOnClickListener(v -> {
            isTel1 = false;
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivityForResult(intent, 0);
        });
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_pay, null);
        bottom_dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
    }

    @Bind(R.id.tel1_ll)
    LinearLayout tel1_ll;
    @Bind(R.id.tel2_ll)
    LinearLayout tel2_ll;

    @Override
    public void initEvents() {
        title_bar.setLeftClick(() -> finish());
        loadUI();
        changeAddressIv.setOnClickListener(v -> {
            firstIsbuy = !firstIsbuy;
            loadUI();
        });
        changeAddressIvs.setOnClickListener(v -> {
            tel1 = tel1Et.getText().toString().trim();
            tel2 = tel2Et.getText().toString().trim();
            changeTel();
        });
        buyAddressLl.setOnClickListener(v -> {
            Intent intent = new Intent(AddSongActivity.this, SelectAddressActivity.class);
            if (firstIsbuy) {
                intent.putExtra("model", buy_poi);
            } else {
                intent.putExtra("model", send_poi);
            }
            startActivityForResult(intent, 0);
        });
        sendAddressLl.setOnClickListener(v -> {
            Intent intent = new Intent(AddSongActivity.this, SelectAddressActivity.class);
            if (firstIsbuy) {
                intent.putExtra("model", send_poi);
            } else {
                intent.putExtra("model", buy_poi);
            }
            startActivityForResult(intent, 1);
        });
        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
                if (i == 1000 && walkRouteResult != null) {
                    if (walkRouteResult.getPaths().size() > 0) {
                        WalkPath path = walkRouteResult.getPaths().get(0);
                        address_length = Double.valueOf(path.getDistance()) / 1000;
                        address_length_tv.setText("距离：" + address_length + "公里");
                        if (address_length <= 0) {
                            ToastShort("送货距离有问题，请联系业务员~~");
                        } else {
                            mListener.js(address_length + "", "0");
                        }
                    }
                } else {
                    dialog.dismiss();
                    priceDetailLl.setVisibility(View.VISIBLE);
                    ToastShort("当前仅支持本市配送");
                    priceTv.setText("当前仅支持本市配送");
                }
            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
        priceDetailLl.setOnClickListener(v -> {
            if (feiyong != null) {
                Utils.IntentPost(PriceDetailActivity.class, intent -> {
                    intent.putExtra("model", feiyong);
                });
            }
        });
        payBtn.setOnClickListener(v -> {
            String tel1_str = tel1Et.getText().toString().trim();
            String tel2_str = tel2Et.getText().toString().trim();
            if (buy_poi == null) {
                if (firstIsbuy) {
                    ToastShort("请选择发货地址");
                } else {
                    ToastShort("请选择收货地址");
                }
            } else if (send_poi == null) {
                if (firstIsbuy) {
                    ToastShort("请选择收货地址");
                } else {
                    ToastShort("请选择发货地址");
                }
            } else if (("").equals(tel1_str) || !Utils.isMobileNo(tel1_str)) {
                ToastShort("发货人电话不正确");
            } else if (("").equals(tel2) || !Utils.isMobileNo(tel2_str)) {
                ToastShort("收货人电话不正确");
            } else if (("").equals(goodTypeEt.getText().toString().trim())) {
                ToastShort("请填写物品类型");
            } else {
                if (isSong) {
                    save.setOrdertype("2");
                } else {
                    save.setOrdertype("3");
                }
                save.setUserid(Utils.getCache(Key.KEY_UserId));
                save.setRemark(Utils.URLEncodeImage(goodTypeEt.getText().toString()));
                if (firstIsbuy) {
                    save.setLat1(buy_poi.getLat() + "");
                    save.setLng1(buy_poi.getLng() + "");
                    save.setLat2(send_poi.getLat() + "");
                    save.setLng2(send_poi.getLng() + "");
                    if (buy_poi != null) {
                        save.setAddress1(Utils.URLEncodeImage(buy_poi.getPoiAddress()));
                    } else {
                        save.setAddress1("");
                    }
                    if (send_poi != null) {
                        save.setAddress2(Utils.URLEncodeImage(send_poi.getPoiAddress()));
                    } else {
                        save.setAddress2("");
                    }
                } else {
                    save.setLat1(send_poi.getLat() + "");
                    save.setLng1(send_poi.getLng() + "");
                    save.setLat2(buy_poi.getLat() + "");
                    save.setLng2(buy_poi.getLng() + "");
                    save.setAddress2(Utils.URLEncodeImage(buy_poi.getPoiAddress()));
                    save.setAddress1(Utils.URLEncodeImage(send_poi.getPoiAddress()));
                }
                save.setTel1(tel1Et.getText().toString().trim());
                save.setTel2(tel2Et.getText().toString().trim());
                if (feiyong != null) {
                    if (!("0.00").equals(feiyong.getTotalfee())) {
                        save.setSendfee(feiyong.getQibufee());
                        save.setLichengfee(feiyong.getLichengfee());
                        save.setTotalfee(feiyong.getTotalfee());
                        save.setJuli(feiyong.getAlljuli());
                        save.setSource("2");//android设备
                        save.setIsdaishoufee("0");
                        FinalDb db = FinalDb.create(this);
                        if (send_poi != null) {
                            db.save(send_poi);
                        }

                        if (buy_poi != null) {
                            db.save(buy_poi);
                        }
                        showDialog();//显示底部弹出框
                    } else {
                        ToastShort("距离太远，请重新下单");
                    }
                } else {
                    ToastShort("请先计算费用");
                }
            }
        });
        priceDetailLl.setOnClickListener(v -> {
            if (feiyong != null) {
                Utils.IntentPost(PriceDetailActivity.class, intent -> {
                    intent.putExtra("model", feiyong);
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 8) {
            switch (requestCode) {
                case 0://购买地址跳转回来
                    if (firstIsbuy && isSong) {
                        buy_poi = (PoiModel) data.getSerializableExtra("val");
                    } else {
                        send_poi = (PoiModel) data.getSerializableExtra("val");
                    }
                    break;
                case 1://送达地址跳转回来
                    if (firstIsbuy & isSong) {
                        send_poi = (PoiModel) data.getSerializableExtra("val");
                    } else {
                        buy_poi = (PoiModel) data.getSerializableExtra("val");
                    }
                    break;
            }
            if (buy_poi != null && send_poi != null) {
                sl(new LatLonPoint(buy_poi.getLat(), buy_poi.getLng()), new LatLonPoint(send_poi.getLat(), send_poi.getLng()));
            }
            loadUI();
        }
        if (resultCode == 77) {
            if (isTel1) {
                tel1Et.setText(data.getStringExtra("tel"));
            } else {
                tel2Et.setText(data.getStringExtra("tel"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sl(LatLonPoint start, LatLonPoint end) {
        dialog.show();
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(start, end);
        RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
        routeSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
    }

    FeiModel feiyong;

    @Override
    public void slResult(FeiModel model, String error) {
        feiyong = model;
        priceDetailLl.setVisibility(View.VISIBLE);
        if (error != null) {
            priceTv.setText(error);
        } else {
            if (model != null) {
                priceTv.setText("￥" + model.getTotalfee());
            }
        }
        dialog.dismiss();
    }

    @Override
    public void saveResult(String orderId, String totalPrice, String error) {
        bottom_dialog.dismiss();
        if (orderId != null && error == null) {
            if (!pay_is_wx) {
                String orderInfo = getOrderInfo("大可快跑", "大可快跑Android支付", totalPrice, orderId);
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
                    PayTask alipay = new PayTask(AddSongActivity.this);
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
                new MyThread(orderId, totalPrice).start();
            }
        } else if (orderId == null && error != null) {
            ToastShort(error);
        }else{
            ToastShort("请检查网络连接");
        }
    }

    class MyThread extends Thread {
        String Order_Id = "";
        int Order_Price;

        public MyThread(String orderId, String price) {
            Order_Id = orderId;
            Order_Price = (int) (Double.parseDouble(price) * 100);
        }

        public void run() {
            WxUtil.load(AddSongActivity.this, "大可快跑", "大可快跑Android支付", Order_Id, Order_Price);
        }
    }

    private void loadUI() {
        if (isSong) {//送
            if (firstIsbuy) {
                if (send_poi != null) {
                    sendAddressTv.setText(send_poi.getPoiName());
                } else {
                    sendAddressTv.setText("");
                }
                if (buy_poi != null) {
                    buyAddressTv.setText(buy_poi.getPoiName());
                } else {
                    buyAddressTv.setText("");
                }
            } else {
                if (send_poi != null) {
                    buyAddressTv.setText(send_poi.getPoiName());
                } else {
                    buyAddressTv.setText("");
                }
                if (buy_poi != null) {
                    sendAddressTv.setText(buy_poi.getPoiName());
                } else {
                    sendAddressTv.setText("");
                }
            }
        } else {
            if (!firstIsbuy) {
                if (send_poi != null) {
                    sendAddressTv.setText(send_poi.getPoiName());
                } else {
                    sendAddressTv.setText("");
                }
                if (buy_poi != null) {
                    buyAddressTv.setText(buy_poi.getPoiName());
                } else {
                    buyAddressTv.setText("");
                }
            } else {
                if (send_poi != null) {
                    buyAddressTv.setText(send_poi.getPoiName());
                } else {
                    buyAddressTv.setText("");
                }
                if (buy_poi != null) {
                    sendAddressTv.setText(buy_poi.getPoiName());
                } else {
                    sendAddressTv.setText("");
                }
            }
        }

    }

    private void changeTel() {
        tel1Et.setText(tel2);
        tel2Et.setText(tel1);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(AddSongActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                        /*后期需要实现跳转到订单相信页面，是支付失败还是再来一单*/
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(AddSongActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddSongActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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

    View inflate;

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    private void showDialog() {
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
        pay_tv.setText(feiyong.getTotalfee() + "元");
        pay_btn.setOnClickListener(v -> {
            mListener.saveOrder(save);//生成预订单，调起支付
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

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        Display display = getWindowManager().getDefaultDisplay();
        lp.width = display.getWidth(); //设置宽度
        lp.y = intheight / 2;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        bottom_dialog.show();//显示对话框
    }

    Dialog bottom_dialog;

    @Override
    public void manageResult(boolean result) {
        if (result) {
            ToastShort("收藏成功");
        } else {
            ToastShort("收藏失败");
        }
    }
}
