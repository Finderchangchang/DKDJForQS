package liuliu.qs.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import net.tsz.afinal.view.TitleBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;


/**
 * Created by Administrator on 2016/12/8.
 */

public class OrderDetailsActivity extends BaseActivity {
    public static OrderDetailsActivity mInstails;
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.tab_FindFragment_title)
    TabLayout tabFindFragmentTitle;
    @Bind(R.id.order_list_vp)
    ViewPager orderListVp;
    private String[] mTitles = {"订单详情", "订单状态"};
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();
    private OrderDetailFragment orderFragment1;
    private OrderDetailFragment orderFragment2;
    String orderId = "";

    @Override
    public void initViews() {
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        mInstails = this;
        orderListVp.setOffscreenPageLimit(mTitles.length);
        orderFragment1 = new OrderDetailFragment();
        orderFragment2 = new OrderDetailFragment();
        mFragments2.add(orderFragment1);
        mFragments2.add(orderFragment2);
        orderId = getIntent().getStringExtra("orderid");
        titleBar.setLeftClick(() -> finish());
        tabFindFragmentTitle.addTab(tabFindFragmentTitle.newTab().setText(mTitles[0]));
        tabFindFragmentTitle.addTab(tabFindFragmentTitle.newTab().setText(mTitles[1]));
    }

    int position = 0;

    @Override
    public void initEvents() {
        orderListVp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabFindFragmentTitle.setupWithViewPager(orderListVp);
        tabFindFragmentTitle.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                loadTag();
                orderListVp.setCurrentItem(tab.getPosition()); //解决单击Tab标签无法翻页的问题
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        loadTag();
    }

    private void loadTag() {
        TabLayout.Tab tab = tabFindFragmentTitle.getTabAt(position);
        if (tab != null) {
            tab.select();
        }
        switch (position) {
            case 0:
                orderFragment1.getOrderDetail(orderId, position);
                break;
            case 1:
                orderFragment2.getOrderDetail(orderId, position);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mTitles.length;
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
}
