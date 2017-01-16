package liuliu.qs.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.view.TitleBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.method.Utils;
import liuliu.qs.model.ShopInfo;


/**
 * 个人中心
 * Created by Administrator on 2016/12/2.
 */

public class UserActivity extends BaseActivity {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.exit_btn)
    Button exitBtn;
    @Bind(R.id.user_iv)
    ImageView userIv;
    @Bind(R.id.user_tel_tv)
    TextView userTelTv;
    @Bind(R.id.user_info_ll)
    LinearLayout userInfoLl;
    @Bind(R.id.user1_ll)
    LinearLayout user1Ll;
    @Bind(R.id.user2_ll)
    LinearLayout user2Ll;
    @Bind(R.id.user3_ll)
    LinearLayout user3Ll;
    @Bind(R.id.user4_ll)
    LinearLayout user4Ll;
    @Bind(R.id.user5_ll)
    LinearLayout user5Ll;
    @Bind(R.id.user6_ll)
    LinearLayout user6Ll;
    FinalDb db;
    List<ShopInfo> list;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        db = FinalDb.create(this);
    }

    @Override
    public void initEvents() {
        list = db.findAll(ShopInfo.class);
        if (list.size() > 0) {
            ShopInfo info = list.get(0);
            userTelTv.setText(info.getTogoname() + "\n" + info.getTogoaccount());
            Glide.with(this)
                    .load(info.getPicture())
                    .into(userIv);
        }
        user1Ll.setOnClickListener(v -> Utils.IntentPost(OrderListActivity.class));
        user2Ll.setOnClickListener(v -> Utils.IntentPost(QianBaoActivity.class));
        user3Ll.setOnClickListener(v -> Utils.IntentPost(HistoryOrderActivity.class));
        titleBar.setLeftClick(() -> finish());
        user5Ll.setOnClickListener(v -> {//拨打电话
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4001663779"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        user6Ll.setOnClickListener(v -> Utils.IntentPost(ChangePwdActivity.class));
        user4Ll.setOnClickListener(v -> Utils.IntentPost(WebActivity.class, intent -> intent.putExtra("web", "关于我们")));
        exitBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("确定要退出账号吗?");
            builder.setNegativeButton("确定", (dialog1, which) -> {
                Utils.putCache(liuliu.qs.config.Key.KEY_UserId, "");
                finish();
                MainActivity.mInstails.finish();
            });
            builder.setPositiveButton("取消", null);
            builder.show();
        });
    }
}
