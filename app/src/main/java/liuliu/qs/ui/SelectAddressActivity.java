package liuliu.qs.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.AoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Tip;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import net.tsz.afinal.view.TitleBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.base.BaseApplication;
import liuliu.qs.listener.AddressManageListener;
import liuliu.qs.method.Utils;
import liuliu.qs.model.PoiModel;
import liuliu.qs.model.TabEntity;
import liuliu.qs.view.IAddressManage;

/**
 * Created by Administrator on 2016/11/29.
 */

public class SelectAddressActivity extends BaseActivity implements IAddressManage {
    public static SelectAddressActivity mIntails;
    @Bind(R.id.send_address_tv)
    TextView sendAddressTv;
    @Bind(R.id.select_position_ll)
    LinearLayout selectPositionLl;
    @Bind(R.id.map)
    MapView mMapView;
    @Bind(R.id.center_iv)
    ImageView centerIv;
    @Bind(R.id.address_title_tv)
    TextView address_title_tv;
    @Bind(R.id.address_desc_tv)
    TextView address_desc_tv;
    @Bind(R.id.title_bar)
    TitleBar title_bar;
    AMap aMap;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();
    private String[] mTitles = {"附近的点", "历史记录"};
    private int[] mIconUnselectIds = {
            R.mipmap.address_normal, R.mipmap.ls_normal};
    private int[] mIconSelectIds = {
            R.mipmap.address_pressed, R.mipmap.ls_pressed};
    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout_2;
    BaseFragment fujin_bf;
    BaseFragment lishi_bf;
    @Bind(R.id.search_poi_ll)
    LinearLayout search_poi_ll;
    @Bind(R.id.save_address_btn)
    Button save_address_btn;
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient = null;
    Dialog dialog;
    @Bind(R.id.mp_et)
    EditText mp_et;
    AddressManageListener mListener;
    String isSave;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    @Bind(R.id.location_iv)
    ImageView location_iv;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);
        mListener = new AddressManageListener(this);
        mIntails = this;
        fujin_bf = new BaseFragment();
        lishi_bf = new BaseFragment();
        mViewPager = (ViewPager) findViewById(R.id.vp_2);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mFragments2.add(fujin_bf);
        mFragments2.add(lishi_bf);
        mViewPager = (ViewPager) findViewById(R.id.vp_2);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout_2 = (CommonTabLayout) findViewById(R.id.tl_2);
        tl_2();
        title_bar.setLeftClick(() -> {
            SelectAddressActivity.this.finish();
        });
        search_poi_ll.setOnClickListener(v -> {
            Intent intent = new Intent(SelectAddressActivity.this, SearchPoiActivity.class);
            startActivityForResult(intent, 0);
        });
        location_iv.setOnClickListener(v -> {
            mlocationClient.startLocation();
        });
    }

    private void closeThis() {
        Intent intent = new Intent();
        poiModel.setDetailAddress(mp_et.getText().toString().trim());
        intent.putExtra("val", poiModel);
        setResult(8, intent);
        SelectAddressActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 9:
                Tip tip = data.getParcelableExtra("tip");
                load(true, new PoiModel(tip.getName(), tip.getAddress(), "0", tip.getPoint().getLatitude(), tip.getPoint().getLongitude()));
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void tl_2() {
        mTabLayout_2.setTabData(mTabEntities);
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments2.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments2.get(position);
        }
    }

    List<PoiModel> pois;
    String aid;

    @Override
    public void initEvents() {
        dialog = Utils.ProgressDialog(this, "定位中，请稍后...", true);
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(mLocationOption);
        Serializable modd = getIntent().getSerializableExtra("model");
        isSave = getIntent().getStringExtra("add");
        if (modd != null) {
            poiModel = (PoiModel) modd;
            aid = poiModel.getAid();
        }
        save_address_btn.setOnClickListener(v -> {
            if (isSave == null) {
                closeThis();
            } else {//保存或修改以后跳页
                if (("true").equals(isSave)) {//修改
                    poiModel.setState(1);
                } else {//添加
                    poiModel.setState(0);
                    poiModel.setAid(aid);
                }
                poiModel.setDetailAddress(Utils.URLEncodeImage(mp_et.getText().toString().trim()));
                mListener.addAddress(poiModel);
            }
        });
        mlocationClient.setLocationListener(aMapLocation -> {
            poiModel = new PoiModel(aMapLocation.getAoiName(), aMapLocation.getAddress(), "0", aMapLocation.getLatitude(), aMapLocation.getLongitude());
            address_title_tv.setText(poiModel.getPoiName());
            address_desc_tv.setText(poiModel.getPoiAddress());
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 16));
            dialog.dismiss();
        });
        pois = new ArrayList<>();
        if (aMap == null) {
            aMap = mMapView.getMap();
            mUiSettings = aMap.getUiSettings();//实例化UiSettings类
            mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);
            aMap.setMyLocationEnabled(true);// 可触发定位并显示定位层
            if (poiModel != null) {
                address_title_tv.setText(poiModel.getPoiName());
                address_desc_tv.setText(poiModel.getPoiAddress());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(poiModel.getLat(), poiModel.getLng()), 16));
            } else {
                dialog.show();
                mlocationClient.startLocation();
            }
            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {

                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                    if (cameraPosition.target != null) {
                        pois = new ArrayList<>();
                        GeocodeSearch geocoderSearch = new GeocodeSearch(BaseApplication.getContext());//传入context
                        LatLonPoint latLonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
                        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                            @Override
                            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                                if (rCode == 1000) {
                                    List<AoiItem> poiItems = result.getRegeocodeAddress().getAois();
                                    if (poiItems.size() > 0) {
                                        String s = result.getRegeocodeAddress().getDistrict() + result.getRegeocodeAddress().getStreetNumber().getStreet() +
                                                result.getRegeocodeAddress().getStreetNumber().getNumber();
                                        pois.add(new PoiModel(poiItems.get(0).getAoiName(), s, "0", poiItems.get(0).getAoiCenterPoint().getLatitude(), poiItems.get(0).getAoiCenterPoint().getLongitude()));
                                    }
                                    List<PoiItem> aois = result.getRegeocodeAddress().getPois();
                                    for (PoiItem model : aois) {
                                        pois.add(new PoiModel(model.getTitle(), model.getSnippet(), model.getDistance() + "", model.getLatLonPoint().getLatitude(), model.getLatLonPoint().getLongitude()));
                                    }
                                    fujin_bf.loadPoint(pois);
                                    if (!click) {
                                        if (pois.size() > 0) {
                                            address_title_tv.setText(pois.get(0).getPoiName());
                                            address_desc_tv.setText(pois.get(0).getPoiAddress());
                                            poiModel = new PoiModel(pois.get(0).getPoiName(), pois.get(0).getPoiAddress(), "0", pois.get(0).getLat(), pois.get(0).getLng());
                                            click = false;
                                        } else {
                                            click = false;
                                        }
                                    } else {
                                        click = false;
                                    }
                                } else {

                                }
                            }

                            @Override
                            public void onGeocodeSearched(GeocodeResult arg0, int arg1) {

                            }
                        });
                        geocoderSearch.getFromLocationAsyn(query);
                    }
                }
            });
        }
        lishi_bf.loadHistory();
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }

    boolean click;
    PoiModel poiModel;

    /**
     * 通知顶部文字改变
     */
    public void load(boolean itemClick, PoiModel model) {
        click = itemClick;
        poiModel = model;
        if (model != null) {
            address_title_tv.setText(model.getPoiName());
            address_desc_tv.setText(model.getPoiAddress());
            if (itemClick) {
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(model.getLat(), model.getLng()), 16));
            }
        }
    }

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
}
