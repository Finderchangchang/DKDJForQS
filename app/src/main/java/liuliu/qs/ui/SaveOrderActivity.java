package liuliu.qs.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.WalkStep;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.view.TitleBar;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.config.Key;
import liuliu.qs.listener.AddressManageListener;
import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;
import liuliu.qs.model.PoiModel;
import liuliu.qs.model.ShopInfo;
import liuliu.qs.view.IAddressManage;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/29.
 */

public class SaveOrderActivity extends BaseActivity implements IAddressManage, RouteSearch.OnRouteSearchListener {
    public static SaveOrderActivity mIntails;
    @Bind(R.id.map)
    MapView mMapView;
    @Bind(R.id.center_iv)
    ImageView centerIv;
    @Bind(R.id.title_bar)
    TitleBar title_bar;
    AMap aMap;
    public AMapLocationClientOption mLocationOption = null;
    Dialog dialog;
    AddressManageListener mListener;
    String isSave;
    @Bind(R.id.address_tv)
    TextView addressTv;
    @Bind(R.id.tel_tv)
    TextView telTv;
    @Bind(R.id.sh_address_tv)
    TextView shAddressTv;
    @Bind(R.id.fei_tv)
    TextView feiTv;
    @Bind(R.id.pay_btn)
    Button payBtn;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    @Bind(R.id.tel_et)
    EditText tel_et;
    FinalDb db;
    private RouteSearch routeSearch;
    double juli;
    String sendfee;

    @Override
    public void initViews() {
        setContentView(R.layout.ac_save_order);
        ButterKnife.bind(this);
        db = FinalDb.create(this);
        mListener = new AddressManageListener(this);
        mIntails = this;
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        title_bar.setLeftClick(() -> {
            SaveOrderActivity.this.finish();
        });
        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(this);
        payBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(tel_et.getText().toString().trim())) {
                ToastShort("请填写手机号码");
            } else if (!Utils.isMobileNo(tel_et.getText().toString().trim())) {
                ToastShort("请输入正确的手机号码");
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.dialog_dake, null);
                TextView shop_name_tv = (TextView) dialog.findViewById(R.id.shop_name_tv);
                shop_name_tv.setText(list.get(0).getTogoname());
                TextView tel_tv = (TextView) dialog.findViewById(R.id.tel_tv);
                tel_tv.setText(list.get(0).getTogoaccount());
                TextView shop_address_tv = (TextView) dialog.findViewById(R.id.shop_address_tv);
                shop_address_tv.setText(list.get(0).getAddress());
                TextView user_tel_tv = (TextView) dialog.findViewById(R.id.user_tel_tv);
                user_tel_tv.setText(tel_et.getText().toString().trim());
                TextView fei_tv = (TextView) dialog.findViewById(R.id.fei_tv);
                fei_tv.setText(sendfee + "元");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setPositiveButton("取消", (dialog12, which) -> {

                });
                builder.setNegativeButton("确定", (dialog1, which) -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("togoid", Utils.getCache(Key.KEY_UserId));
                    map.put("ulat", poiModel.getLat() + "");
                    map.put("ulng", poiModel.getLng() + "");
                    map.put("diZhi", URLEncoder.encode(poiModel.getPoiName()));
                    map.put("juLi", juli + "");
                    map.put("sendFee", sendfee);
                    HttpUtil.load().addOrder(map)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(model -> {
                                ToastShort(model.getState());
                                finish();
                            }, error -> {
                                ToastShort("请检查网络连接");
                            });
                });
                builder.setView(dialog);
                builder.show();
            }
        });
    }

    private void closeThis() {
        Intent intent = new Intent();
        intent.putExtra("val", poiModel);
        setResult(8, intent);
        SaveOrderActivity.this.finish();
    }


    @Override
    public void manageResult(boolean result) {
        if (result) {
            closeThis();
            ToastShort("提交成功");
        } else {
            ToastShort("请检查网络连接是否正常");
        }
    }

    List<PoiModel> pois;
    String aid;
    List<ShopInfo> list;
    String tel;

    @Override
    public void initEvents() {
        dialog = Utils.ProgressDialog(this, "定位中，请稍后...", true);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        Serializable modd = getIntent().getSerializableExtra("model");
        tel = getIntent().getStringExtra("tel");
        if (tel != null) {
            tel_et.setText(tel);
            tel_et.setSelection(tel.length());
        }

        isSave = getIntent().getStringExtra("add");
        if (modd != null) {
            poiModel = (PoiModel) modd;
            aid = poiModel.getAid();
        }
        list = db.findAll(ShopInfo.class);
        if (list != null) {
            addressTv.setText("商家的地址：" + list.get(0).getAddress());
            shAddressTv.setText("收货人地址：" + poiModel.getPoiName());
            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(new LatLonPoint(Double.parseDouble(list.get(0).getLat()),
                    Double.parseDouble(list.get(0).getLng())), new LatLonPoint(poiModel.getLat(), poiModel.getLng()));
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
            routeSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }
        pois = new ArrayList<>();
        if (aMap == null) {
            aMap = mMapView.getMap();
            mUiSettings = aMap.getUiSettings();//实例化UiSettings类
            mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);
            aMap.setMyLocationEnabled(true);// 可触发定位并显示定位层
            if (poiModel != null) {
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(poiModel.getLat(), poiModel.getLng()), 14));
            } else {
                dialog.show();
            }
        }
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

    }

    PoiModel poiModel;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
        if (i == 1000 && walkRouteResult != null) {
            List<LatLng> latLngs = new ArrayList<LatLng>();
            if (walkRouteResult.getPaths().size() > 0) {
                WalkPath path = walkRouteResult.getPaths().get(0);
                double length = 0;
                for (WalkPath path1 : walkRouteResult.getPaths()) {
                    length += path1.getDistance();
                    for (WalkStep walkStep : path1.getSteps()) {
                        for (LatLonPoint point : walkStep.getPolyline()) {
                            latLngs.add(new LatLng(point.getLatitude(), point.getLongitude()));
                        }
                    }
                }
                juli = Double.valueOf(length) / 1000;
                aMap.addPolyline(new PolylineOptions().
                        addAll(latLngs).width(5).color(Color.argb(255, 1, 1, 1)));
                if (juli <= 0) {
                    ToastShort("送货距离有问题，请联系业务员~~");
                } else {
                    aMap.addMarker(new MarkerOptions().position(latLngs.get(0)));
                    if (latLngs.size() >= 1) {
                        aMap.addMarker(new MarkerOptions().position(latLngs.get(latLngs.size() - 1)));
                    }
                    Map<String, String> map = new HashMap<>();
                    map.put("togoid", Utils.getCache(Key.KEY_UserId));
                    map.put("gonglishu", juli + "");
                    HttpUtil.load().getPSFei(map)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(model -> {
                                feiTv.setText("订单配送费：" + model.getSendfee() + "元");
                                sendfee = model.getSendfee();
                                aMap.addPolyline(new PolylineOptions().
                                        addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
                            }, error -> {
                                feiTv.setText("算费失败");
                            });
                }
            }
        }
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
