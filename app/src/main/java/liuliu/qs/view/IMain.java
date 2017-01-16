package liuliu.qs.view;

import java.util.List;

import liuliu.qs.model.LatLngModel;
import liuliu.qs.model.ShopInfo;
import liuliu.qs.model.VersionModel;

/**
 * Created by Administrator on 2016/11/26.
 */

public interface IMain {
    void getQsLatLng(List<LatLngModel> list);

    void checkUpdate(VersionModel model);

    void getUserInfo(ShopInfo shopInfo);
}
