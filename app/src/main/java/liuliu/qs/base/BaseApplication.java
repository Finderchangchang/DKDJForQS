package liuliu.qs.base;

import android.app.Application;
import android.content.Context;

import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;
import com.igexin.sdk.PushManager;
import com.tencent.bugly.crashreport.CrashReport;

public class BaseApplication extends Application {
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        PushManager.getInstance().initialize(this, null);
        GrowingIO.startWithConfiguration(this, new Configuration()
                .useID()
                .trackAllFragments()
                .setChannel("应用宝"));
        CrashReport.initCrashReport(getApplicationContext(), "01db3ab7d4", false);
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        BaseApplication.context = context;
    }
}