package vn.nano.hackernewsdemo;

import vn.nano.core_library.mvp.BaseApplication;
import vn.nano.hackernewsdemo.dagger.component.AppComponent;
import vn.nano.hackernewsdemo.dagger.component.ComponentManager;
import vn.nano.hackernewsdemo.dagger.component.DaggerAppComponent;
import vn.nano.hackernewsdemo.dagger.module.AppModule;

/**
 * Created by alex on 9/14/17.
 */

public class HackerNewsApplication extends BaseApplication {

    private static HackerNewsApplication instance;

    public static HackerNewsApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ComponentManager.getInstance().init(DaggerAppComponent.builder().appModule(new AppModule()).build());
    }

}
