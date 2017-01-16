package liuliu.qs.method;

import java.util.Map;

import liuliu.qs.model.JiaoYiModel;
import liuliu.qs.model.MessageModel;
import liuliu.qs.model.OrderDetailsModel;
import liuliu.qs.model.OrderModel;
import liuliu.qs.model.ShopInfo;
import liuliu.qs.model.TongJiModel;
import liuliu.qs.model.VersionModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/20.
 */

public interface GitHubAPI {
    //登录
    @GET("app/GetuiLogin.aspx")
    Observable<MessageModel> login(@QueryMap Map<String, String> map);

    @GET("app/Shop/getShopInfo.aspx")
    Observable<ShopInfo> getShopInfo(@Query("shopid") String shopid);

    //获得配送费
    @GET("App/shop/kuaipaoHuoQuPeiSongFei.aspx")
    Observable<MessageModel> getPSFei(@QueryMap Map<String, String> map);

    //生成订单
    @GET("App/shop/kuaipaoAddOrder.aspx")
    Observable<MessageModel> addOrder(@QueryMap Map<String, String> map);

    //获得验证码
    @GET("App/Cpaotui/getCode.aspx")
    Observable<MessageModel> getCode(@QueryMap Map<String, String> map);

    //修改密码
    @GET("App/shop/UpdateShopByTogoId.aspx")
    Observable<MessageModel> changePwd(@QueryMap Map<String, String> map);

    //注册FindPassword
    @GET("App/Cpaotui/register.aspx")
    Observable<MessageModel> regUser(@QueryMap Map<String, String> map);

    //忘记密码
    @GET("App/Cpaotui/FindPassword.aspx")
    Observable<MessageModel> findPwd(@QueryMap Map<String, String> map);

    //2.2.1.	获取骑士坐标
    @GET("App/Cpaotui/Getdeliverlatlng.aspx")
    Observable<MessageModel> getQsLatLngs(@Query("cityid") String cityid);

    //算路
    @GET("App/Cpaotui/GetSendfee.aspx")
    Observable<MessageModel> suanLu(@QueryMap Map<String, String> map);

    //添加订单
    @GET("App/Cpaotui/SubmitOrder.aspx")
    Observable<MessageModel> saveOrder(@QueryMap Map<String, String> map);

    //获得地址列表
    @GET("App/Cpaotui/GetAddresslist.aspx")
    Observable<MessageModel> getAddressList(@QueryMap Map<String, String> userid);

    //增删改地址
    @GET("App/Cpaotui/AddMyaddress.aspx")
    Observable<MessageModel> addAddress(@QueryMap Map<String, String> map);

    //获得订单列表
    @GET("App/shop/kuaipaoGetOrder.aspx")
    Observable<OrderModel> getOrderList(@QueryMap Map<String, String> map);

    //获得交易明细
    @GET("App/shop/KuaiPaoJiaoYiJiLu.aspx")
    Observable<JiaoYiModel> getJYList(@QueryMap Map<String, String> map);

    //获得充值明细
    @GET("App/shop/kuaipaoChongZhiList.aspx")
    Observable<JiaoYiModel> getCZList(@QueryMap Map<String, String> map);

    //获得订单详情
    @GET("App/shop/GetOrderDetailByOrderId.aspx")
    Observable<OrderDetailsModel> getOrderDetail(@Query("orderid") String orderid);

    //订单统计
    @GET("App/shop/DingDanTongJi.aspx")
    Observable<TongJiModel> getOrderTongJi(@Query("togoid") String orderid);

    //取消订单
    @GET("App/Cpaotui/CancelOrder.aspx")
    Observable<MessageModel> cancleOrder(@QueryMap Map<String, String> map);

    //获得用户信息
    @GET("App/Cpaotui/GetUserinfo.aspx")
    Observable<MessageModel> getUserById(@Query("userid") String orderid);

    //获得城市列表
    @GET("App/Cpaotui/GetCityList.aspx")
    Observable<MessageModel> getCityList();

    @GET("download/version.aspx?c=5")
    Observable<VersionModel> checkUpdate();
}
