package liuliu.qs.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import net.tsz.afinal.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.method.CommonAdapter;
import liuliu.qs.method.CommonViewHolder;
import liuliu.qs.method.HttpUtil;
import liuliu.qs.model.CityModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/3.
 */

public class CityActivity extends BaseActivity {
    private List<CityModel> contactses;
    private ListView lv;
    private CommonAdapter<CityModel> adapter;
    private EditText pl_et_search;
    TitleBar title_bar;
    ProgressBar total_pb;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_phonenumber_list);
        contactses = new ArrayList<>();
        total_pb = (ProgressBar) findViewById(R.id.total_pb);
        lv = (ListView) findViewById(R.id.phonenumber_lv);
        pl_et_search = (EditText) findViewById(R.id.pl_et_search);
        title_bar = (TitleBar) findViewById(R.id.title_bar);
        loadCity();
        title_bar.setLeftClick(() -> finish());
        pl_et_search.setVisibility(View.GONE);
        title_bar.setCenter_str("城市切换");
    }

    @Override
    public void initEvents() {
        title_bar.setOnClickListener(v -> finish());
        adapter = new CommonAdapter<CityModel>(this, contactses, R.layout.item_city) {
            @Override
            public void convert(CommonViewHolder holder, CityModel contact, int position) {
                if (position != 0) {
                    if (!contact.getShouzimu().equals(contactses.get(position - 1).getShouzimu())) {
                        holder.setVisible(R.id.item_phone_title, true);
                        holder.setText(R.id.item_phone_title, contact.getShouzimu());
                    }
                } else {
                    holder.setVisible(R.id.item_phone_title, true);
                    holder.setText(R.id.item_phone_title, contact.getShouzimu());
                }

                holder.setText(R.id.item_phone_name, contact.getCname());
            }
        };
        total_pb.setVisibility(View.VISIBLE);
        lv.setVisibility(View.GONE);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent();
            intent.putExtra("city", contactses.get(position));
            setResult(77, intent);
            finish();
        });
    }

    private void loadCity() {
        HttpUtil.load()
                .getCityList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    total_pb.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);
                    if (("1").equals(model.getSuccess())) {
                        contactses = model.getCitydata();
                        adapter.refresh(contactses);
                    } else {
                        ToastShort(model.getErrorMsg());
                    }
                }, error -> {
                    ToastShort("请检查网络连接");
                });
    }

}
