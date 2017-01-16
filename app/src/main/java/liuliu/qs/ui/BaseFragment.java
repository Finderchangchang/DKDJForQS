package liuliu.qs.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import liuliu.qs.R;
import liuliu.qs.base.BaseApplication;
import liuliu.qs.config.Key;
import liuliu.qs.listener.AddressManageListener;
import liuliu.qs.method.CommonAdapter;
import liuliu.qs.method.CommonViewHolder;
import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;
import liuliu.qs.model.PoiModel;
import liuliu.qs.view.IAddressList;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/29.
 */

public class BaseFragment extends Fragment implements IAddressList {
    ListView lv;
    AddressManageListener listener;
    boolean sc;

    public void loadPoint(List<PoiModel> list) {
        pois = list;
        mAdapter.refresh(list);
    }

    CommonAdapter mAdapter;
    List<PoiModel> pois;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, null, false);
        lv = (ListView) view.findViewById(R.id.address_list_lv);
        if (pois == null) {
            pois = new ArrayList<>();
        }
        mAdapter = new CommonAdapter<PoiModel>(BaseApplication.getContext(), pois, R.layout.item_poi) {
            @Override
            public void convert(CommonViewHolder holder, PoiModel model, int position) {
                holder.setText(R.id.address_title_tv, model.getPoiName());
                if (model.getPoiAddress().length() > 20) {
                    holder.setText(R.id.address_desc_tv, model.getPoiAddress().substring(0, 20) + "...");
                } else {
                    holder.setText(R.id.address_desc_tv, model.getPoiAddress());
                }
                if (model.getJvli() != null) {
                    holder.setText(R.id.jvli_tv, model.getJvli() + "米");
                    holder.setVisible(R.id.jvli_tv, true);
                    holder.setVisible(R.id.xin_iv, false);
                } else {
                    holder.setVisible(R.id.jvli_tv, false);
                    holder.setVisible(R.id.xin_iv, true);
                    holder.setImageResource(R.id.xin_iv, R.mipmap.xin_pressed);
                    holder.setOnClickListener(R.id.xin_iv, v -> {
                        Map<String, String> map = new HashMap<>();
                        map.put("userid", Utils.getCache(Key.KEY_UserId));
                        map.put("op", "-1");
                        map.put("aid", model.getAid());//添加为空
                        HttpUtil.load()
                                .addAddress(map)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(data -> {
                                    if (("1").equals(data.getSuccess())) {
                                        Toast.makeText(mContext, "取消成功", Toast.LENGTH_SHORT).show();
                                        listener.loadAddressList();
                                    } else {
                                        Toast.makeText(mContext, data.getErrorMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }, error -> {
                                    Toast.makeText(mContext, "取消失败", Toast.LENGTH_SHORT).show();
                                });
                    });
                }
            }
        };
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener((parent, view1, position, id) -> {
            if (pois.size() > 0) {
                SelectAddressActivity.mIntails.load(true, pois.get(position));
            } else {
                SelectAddressActivity.mIntails.load(false, null);
            }
        });
        if (result) {
            mAdapter.refresh(pois);
        }
        if (sc) {
            if (listener == null) {
                listener = new AddressManageListener(this);
            }
            listener.loadAddressList();
        }
        return view;
    }

    /**
     * 加载收藏
     */
    public void loadSC() {
        sc = true;
    }

    boolean result;

    /**
     * 加载历史
     */
    public void loadHistory() {
        FinalDb db = FinalDb.create(BaseApplication.getContext());
        pois = db.findAll(PoiModel.class);
        result = true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void loadAddressResult(List<PoiModel> list) {
        pois = list;
        if (list != null) {
            mAdapter.refresh(list);
        }
    }
}
