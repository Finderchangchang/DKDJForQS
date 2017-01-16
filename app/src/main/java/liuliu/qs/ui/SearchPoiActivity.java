package liuliu.qs.ui;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.view.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.method.Utils;
import liuliu.qs.model.CityModel;


/**
 * Created by Administrator on 2016/11/30.
 */

public class SearchPoiActivity extends BaseActivity {
    private String city = "保定";
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.input_edittext)
    AutoCompleteTextView inputEdittext;
    @Bind(R.id.search_bar_layout)
    LinearLayout searchBarLayout;
    @Bind(R.id.inputlist)
    ListView minputlist;
    FinalDb db;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_search_poi);
        ButterKnife.bind(this);
        db = FinalDb.create(this);
        String cid = Utils.getCache("cid");
        List<CityModel> list = db.findAllByWhere(CityModel.class, "cid='" + cid + "'");
        if (list.size() > 0) {
            city = list.get(0).getCname();
            titleBar.setCenter_str(city);
        }
        titleBar.setLeftClick(() -> finish());
    }

    @Override
    public void initEvents() {
        inputEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().trim();

                InputtipsQuery inputquery = new InputtipsQuery(newText, city);
                inputquery.setCityLimit(true);
                Inputtips inputTips = new Inputtips(SearchPoiActivity.this, inputquery);
                inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
                    @Override
                    public void onGetInputtips(List<Tip> tipList, int rCode) {
                        if (rCode == 1000) {
                            List<HashMap<String, String>> listString = new ArrayList<HashMap<String, String>>();
                            for (int i = 0; i < tipList.size(); i++) {
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("name", tipList.get(i).getName());
                                map.put("address", tipList.get(i).getDistrict());
                                listString.add(map);
                            }
                            SimpleAdapter aAdapter = new SimpleAdapter(getApplicationContext(), listString, R.layout.item_layout,
                                    new String[]{"name", "address"}, new int[]{R.id.poi_field_id, R.id.poi_value_id});

                            minputlist.setAdapter(aAdapter);
                            aAdapter.notifyDataSetChanged();
                            minputlist.setOnItemClickListener((parent, view, position, id) -> {
                                Intent intent = new Intent();
                                intent.putExtra("tip", tipList.get(position));
                                setResult(9, intent);
                                finish();
                            });
                        }
                    }
                });
                inputTips.requestInputtipsAsyn();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
