package vn.nano.hackernewsdemo;

import vn.nano.core_library.mvp.BaseApplication;
import vn.nano.hackernewsdemo.dagger.component.AppComponent;
import vn.nano.hackernewsdemo.dagger.component.DaggerAppComponent;
import vn.nano.hackernewsdemo.dagger.module.AppModule;

/**
 * Created by alex on 9/14/17.
 */

public class HackerNewsApplication extends BaseApplication {

    private static HackerNewsApplication instance;
    private AppComponent appComponent;

    public static HackerNewsApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
