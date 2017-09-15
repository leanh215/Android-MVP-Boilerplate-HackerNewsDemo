package vn.nano.hackernewsdemo.dagger.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import vn.nano.hackernewsdemo.data.remote.HackerNewsService;

/**
 * Created by alex on 9/14/17.
 */

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    public HackerNewsService provideHackerNewsService() {
        return HackerNewsService.Factory.create();
    }

}
