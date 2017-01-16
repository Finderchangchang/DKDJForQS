package liuliu.qs.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import liuliu.qs.R;
import liuliu.qs.config.Key;
import liuliu.qs.ui.AddBuyActivity;
import liuliu.qs.ui.AddSongActivity;
import liuliu.qs.ui.PayActivity;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Key.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case 0:
                    Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    if (AddBuyActivity.mInstail != null) {
                        AddBuyActivity.mInstail.finish();
                    }
                    if (AddSongActivity.mInstail != null) {
                        AddSongActivity.mInstail.finish();
                    }
                    if (PayActivity.mInstail != null) {
                        PayActivity.mInstail.finish();
                    }
                    break;
                case -1:
                    Toast.makeText(WXPayEntryActivity.this, "支付失败，请清理微信缓存后重试", Toast.LENGTH_SHORT).show();
                    break;
                default://支付失败只是关闭当前页面
                    break;
            }
            this.finish();
        }
    }
}