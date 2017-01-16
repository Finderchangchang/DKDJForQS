package liuliu.qs.ui;

import android.webkit.WebView;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.method.HttpUtil;
import liuliu.qs.method.Utils;


/**
 * Created by Administrator on 2016/12/3.
 */

public class WebActivity extends BaseActivity {
    @Bind(liuliu.qs.R.id.title_bar)
    TitleBar titleBar;
    @Bind(liuliu.qs.R.id.web_view)
    WebView webView;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
    }

    @Override
    public void initEvents() {
        titleBar.setLeftClick(() -> finish());
        String title = getIntent().getStringExtra("web");
        titleBar.setCenter_str(title);
        String url = "";
        switch (title) {
            case "常见问题":
                url = HttpUtil.BASE_URL + "App/Cpaotui/aboutinfo.aspx?dataid=13";
                break;
            case "使用须知":
                url = HttpUtil.BASE_URL + "App/Cpaotui/aboutinfo.aspx?dataid=16";
                break;
            case "服务条款":
                url = HttpUtil.BASE_URL + "App/Cpaotui/aboutinfo.aspx?dataid=14";
                break;
            case "关于我们":
                url = HttpUtil.BASE_URL + "App/Cpaotui/aboutinfo.aspx?dataid=17";
                break;
            case "计价标准":
                url = HttpUtil.BASE_URL + "App/Cpaotui/aboutfee.aspx?cityid=" + Utils.getCache("cid");
                break;
        }
        webView.loadUrl(url);
    }
}
