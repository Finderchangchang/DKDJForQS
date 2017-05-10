package liuliu.qs.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

/**
 * BaseActivity声明相关通用方法
 * <p/>
 * Created by LiuWeiJie on 2015/7/22 0022.
 * Email:1031066280@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Toolbar toolbar;
    public Bundle savedInstanceState;
    private int netMobile;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState = savedInstanceState;
        initViews();
        initEvents();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public abstract void initViews();

    public abstract void initEvents();

    private Toast toast = null;

    public void ToastShort(String msg) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
