package liuliu.qs.method;

import android.content.Context;
import android.util.Log;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import liuliu.qs.config.Key;
import liuliu.qs.wxapi.WX;

/**
 * 作者：lwj on 2016/7/21 21:40
 * 邮箱：1031066280@qq.com
 */
public class WxUtil {

    /**
     * @param context
     * @param body
     * @param desc
     * @param orderID
     * @param price
     * @return
     */
    public static boolean load(Context context, String body, String desc, String orderID, int price) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, Key.APP_ID);
        if (api != null) {
            api.registerApp(Key.APP_ID);//注册App到微信
        }
        SortedMap<Object, Object> map = new TreeMap<Object, Object>();
        map.put("appid", Key.APP_ID);//应用ID
        map.put("mch_id", Key.MCH_ID);//商户号
        map.put("nonce_str", "B" + genTimeStamp());//随机数
        map.put("body", body);//商品描述
        map.put("detail", desc);//商品详细信息
        map.put("out_trade_no", orderID);//商户订单号
        map.put("total_fee", price);//总金额
//        map.put("spbill_create_ip", "127.0.0.1");//终端ip
        map.put("notify_url", "http://wx.dakedaojia.com/weixinpay/payNotifyUrl.aspx");//通知地址
        map.put("trade_type", "APP");
        map.put("sign", WX.createSign("UTF-8", map));//创建微信签名
        Map maps = readStringXmlOut(sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", WX.loadXml(map)));//创建预订单
        if (getVal(maps.get("return_code").toString()).equals("SUCCESS")) {//生成预订单成功。准备调起支付页面
            PayReq req = new PayReq();
            SortedMap<Object, Object> bodyMap = new TreeMap<Object, Object>();
            req.appId = Key.APP_ID;
            req.partnerId = Key.MCH_ID;
            if (maps.get("prepay_id") != null) {
                req.prepayId = getVal(maps.get("prepay_id").toString());//返回的设备号
                req.nonceStr = getVal(maps.get("nonce_str").toString());//返回的随机数
                req.timeStamp = genTimeStamp() + "";
                req.packageValue = "Sign=WXPay";
                bodyMap.put("appid", Key.APP_ID);
                bodyMap.put("noncestr", getVal(maps.get("nonce_str").toString()));
                bodyMap.put("package", "Sign=WXPay");
                bodyMap.put("partnerid", Key.MCH_ID);
                bodyMap.put("prepayid", getVal(maps.get("prepay_id").toString()));
                bodyMap.put("timestamp", genTimeStamp() + "");
                req.sign = WX.createSign("UTF-8", bodyMap);//对sign进行二次签名
                api.sendReq(req);
            }
            return true;
        } else {//生成预订单失败
            return false;
        }
    }

    /**
     * 解析xml的val
     *
     * @param key
     * @return
     */
    private static String getVal(String key) {
        return key.replace("<![CDATA[", "").replace("]]", "");
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            Log.i("TAG", result);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获得时间戳
     *
     * @return 时间戳
     */
    private static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * @param xml
     * @return Map
     * @description 将xml字符串转换成map
     */
    public static Map readStringXmlOut(String xml) {
        Map map = new HashMap();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            map.put("return_code", rootElt.elementTextTrim("return_code"));
            map.put("prepay_id", rootElt.elementTextTrim("prepay_id"));
            map.put("nonce_str", rootElt.elementTextTrim("nonce_str"));
            map.put("sign_id", rootElt.elementTextTrim("sign"));
        } catch (Exception e) {

        }
        return map;
    }
}
