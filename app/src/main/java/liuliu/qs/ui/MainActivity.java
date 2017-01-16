package liuliu.qs.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.AoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.view.TitleBar;
import net.tsz.afinal.view.UPMarqueeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.listener.MainListener;
import liuliu.qs.method.UpdateManager;
import liuliu.qs.method.Utils;
import liuliu.qs.model.CityModel;
import liuliu.qs.model.LatLngModel;
import liuliu.qs.model.PoiModel;
import liuliu.qs.model.ShopInfo;
import liuliu.qs.model.VersionModel;
import liuliu.qs.view.IMain;

import static liuliu.qs.R.id.sj_address_et;

public class MainActivity extends BaseActivity implements IMain {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.map)
    MapView map;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.center_iv)
    ImageView center_iv;
    AMap aMap;
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient = null;
    @Bind(R.id.send_address_tv)
    TextView sendAddressTv;
    @Bind(R.id.qishi_num_tv)
    TextView qishiNumTv;
    @Bind(R.id.select_position_ll)
    LinearLayout selectPositionLl;
    @Bind(R.id.send_address_ll)
    LinearLayout send_address_ll;
    @Bind(R.id.no_address_tv)
    TextView no_address_tv;
    @Bind(R.id.location_iv)
    ImageView location_iv;
    String point_title;
    String point_address;
    LatLonPoint point_latlng;
    MainListener mListener;
    @Bind(R.id.change_city_ll)
    LinearLayout change_city_ll;
    FinalDb db;
    public static MainActivity mInstails;
    @Bind(R.id.upview1)
    UPMarqueeView upview1;
    List<String> data = new ArrayList<>();
    List<View> views = new ArrayList<>();
    LocalBroadcastManager broadcastManager;
    @Bind(sj_address_et)
    EditText sjAddressEt;
    @Bind(R.id.sj_tel_et)
    EditText sjTelEt;
    @Bind(R.id.yh_address_et)
    EditText yhAddressEt;
    @Bind(R.id.fd_iv)
    LinearLayout fd_iv;

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private void setView() {
        for (int i = 0; i < data.size(); i = i + 2) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_view, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);
            TextView tv2 = (TextView) moreView.findViewById(R.id.tv2);
            moreView.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, position + "你点击了" + data.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
            });
            moreView.findViewById(R.id.rl2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, position + "你点击了" + data.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
            });
            //进行对控件赋值
            tv1.setText(data.get(i).toString());
            if (data.size() > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                tv2.setText(data.get(i + 1).toString());
            } else {
                moreView.findViewById(R.id.rl2).setVisibility(View.GONE);
            }

            //添加到循环滚动数组里面去
            views.add(moreView);
        }
        upview1.setViews(views);
    }

    /**
     * 初始化数据
     */
    private void initdata() {
        data = new ArrayList<>();
        data.add("家人给2岁孩子喝这个，孩子智力倒退10岁!!!");
        data.add("iPhone8最感人变化成真，必须买买买买!!!!");
        data.add("简直是白菜价！日本玩家33万甩卖15万张游戏王卡");
        data.add("iPhone7价格曝光了！看完感觉我的腰子有点疼...");
        data.add("主人内疚逃命时没带够，回废墟狂挖30小时！");
    }


    private UiSettings mUiSettings;//定义一个UiSettings对象
    AMapLocation mapLocation;
    Dialog dialog;
    @Bind(R.id.city_name_tv)
    TextView city_name_tv;
    @Override
    public void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        map.onCreate(savedInstanceState);
        mListener = new MainListener(this);
        change_city_ll.setOnClickListener(v -> {
            Intent intent = new Intent(this, CityActivity.class);
            startActivityForResult(intent, 0);
        });
        db = FinalDb.create(this);
        mInstails = this;
        mListener.checkUpdate();
        mListener.getUserInfo();
        initdata();
        setView();
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("data");
                if ("refresh".equals(msg)) {

                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
        titleBar.setLeftClick(() -> {
            Utils.IntentPost(UserActivity.class);
        });
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        if (aMap == null) {
            aMap = map.getMap();
            mUiSettings = aMap.getUiSettings();//实例化UiSettings类
            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    send_address_ll.setVisibility(View.GONE);
                    no_address_tv.setVisibility(View.VISIBLE);
                    no_address_tv.setText("正在获取当前位置");
                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                    LatLng latLng = cameraPosition.target;
                    GeocodeSearch geocoderSearch = new GeocodeSearch(MainActivity.this);//传入context
                    LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
                    // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                    RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
                    geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                        @Override
                        public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                            city_name_tv.setText(result.getRegeocodeAddress().getCity());
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                            if (rCode == 1000) {
                                List<AoiItem> poiItems = result.getRegeocodeAddress().getAois();
                                if (poiItems.size() > 0) {
                                    String address = poiItems.get(0).getAoiName();
                                    point_title = address;
                                    point_address = result.getRegeocodeAddress().getFormatAddress();
                                    point_latlng = result.getRegeocodeQuery().getPoint();
                                    if (address.length() > 10) {
                                        sendAddressTv.setText(address.substring(0, 10) + "..");
                                        yhAddressEt.setText("收货人地址：" + address.substring(0, 10) + "..");
                                    } else {
                                        sendAddressTv.setText(address);
                                        yhAddressEt.setText("收货人地址：" + address);
                                    }
                                    model = new PoiModel(point_title, point_address, "0", point_latlng.getLatitude(), point_latlng.getLongitude());
                                    send_address_ll.setVisibility(View.VISIBLE);
                                    no_address_tv.setVisibility(View.GONE);
                                } else {
                                    List<PoiItem> aois = result.getRegeocodeAddress().getPois();
                                    if (aois.size() > 0) {
                                        String address = aois.get(0).getTitle();
                                        point_title = address;
                                        point_address = aois.get(0).getSnippet();
                                        point_latlng = aois.get(0).getLatLonPoint();
                                        if (address.length() > 10) {
                                            sendAddressTv.setText(address.substring(0, 10) + "..");
                                            yhAddressEt.setText("收货人地址：" + address.substring(0, 10) + "..");
                                        } else {
                                            sendAddressTv.setText(address);
                                            yhAddressEt.setText("收货人地址：" + address);
                                        }
                                        send_address_ll.setVisibility(View.VISIBLE);
                                        no_address_tv.setVisibility(View.GONE);
                                        model = new PoiModel(point_title, point_address, "0", point_latlng.getLatitude(), point_latlng.getLongitude());
                                    } else {
                                        no_address_tv.setText("没有位置信息，请尝试其他结果");
                                        send_address_ll.setVisibility(View.GONE);
                                        no_address_tv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                no_address_tv.setText("没有位置信息，请尝试其他结果");
                                send_address_ll.setVisibility(View.GONE);
                                no_address_tv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onGeocodeSearched(GeocodeResult arg0, int arg1) {

                        }
                    });
                    geocoderSearch.getFromLocationAsyn(query);
                }
            });
            mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
            aMap.setMyLocationEnabled(true);// 可触发定位并显示定位层
        }

        mlocationClient.setLocationListener(aMapLocation -> {
            sendAddressTv.setText(aMapLocation.getPoiName());
            mapLocation = aMapLocation;
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 16));
            city_name_tv.setText(aMapLocation.getCity());
        });
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
        dialog = Utils.ProgressDialog(this, "算路中，请稍后...", true);
        dialog.show();
        mListener.loadQSLatLngs();
    }
    @Override
    public void initEvents() {
        send_address_ll.setVisibility(View.VISIBLE);
        no_address_tv.setVisibility(View.GONE);
        location_iv.setOnClickListener(v -> {
            mlocationClient.startLocation();
        });
        send_address_ll.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SelectAddressActivity.class);
            intent.putExtra("model", model);
            startActivityForResult(intent, 8);
        });
        fd_iv.setOnClickListener(v -> {
            Utils.IntentPost(SaveOrderActivity.class, mListener -> {
                mListener.putExtra("model", model);
                mListener.putExtra("tel", sjTelEt.getText().toString().trim());
            });
        });
    }

    private UpdateManager mUpdateManager;
    PoiModel model;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 8://选择地址页面跳转回来
                model = (PoiModel) data.getSerializableExtra("val");
                if (model != null) {
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(model.getLat(), model.getLng()), 16));
                }
                break;
            case 77:
                CityModel city = (CityModel) data.getSerializableExtra("city");
                if (city != null) {
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(city.getLat()), Double.parseDouble(city.getLng())), 16));
                    city_name_tv.setText(city.getCname() + "市");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    MarkerOptions markerOption;

    @Override
    public void getQsLatLng(List<LatLngModel> list) {
        if (list != null) {
            if (list.size() > 50) {
                qishiNumTv.setText("附近有超过50位骑士为您服务");
            } else {
                qishiNumTv.setText("附近有" + list.size() + "位骑士为您服务");
            }
            aMap.clear();
            for (LatLngModel model : list) {
                markerOption = new MarkerOptions();
                try {
                    markerOption.position(new LatLng(Double.parseDouble(model.getGlat()), Double.parseDouble(model.getGlng())));
                    markerOption.draggable(true);
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(
                            BitmapFactory.decodeResource(getResources(), R.mipmap.qs_dian)));
                    aMap.addMarker(markerOption);
                } catch (Exception e) {

                }
            }
        } else {
            qishiNumTv.setText("附近有0位骑士为您服务");
        }
    }

    @Override
    public void checkUpdate(VersionModel model) {
        if (model != null) {//调起更新
            int oldVersion = Integer.parseInt(Utils.getVersion().replace(".", ""));//当前app版本
            int newVersion = Integer.parseInt(model.getVersion().replace(".", ""));//系统最新版本
            if (newVersion > oldVersion) {//需要更新
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage(model.getContent());
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", (dialog1, which) -> {
                    mUpdateManager = new UpdateManager(this);
                    mUpdateManager.checkUpdateInfo(model.getDownload());
                });
                builder.show();
            }
        }
    }

    @Override
    public void getUserInfo(ShopInfo shopInfo) {
        if (shopInfo != null) {
            db.save(shopInfo);
            sjAddressEt.setText(shopInfo.getAddress());
        }
    }
}
