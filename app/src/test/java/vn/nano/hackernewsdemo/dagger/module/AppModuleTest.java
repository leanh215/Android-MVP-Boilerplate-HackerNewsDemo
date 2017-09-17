package vn.nano.hackernewsdemo.dagger.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import vn.nano.hackernewsdemo.data.remote.HackerNewsService;

/**
 * Created by alex on 9/14/17.
 */

@Module
public class AppModuleTest {

    private HackerNewsService hackerNewsService;

    public AppModuleTest(HackerNewsService hackerNewsService) {
        this.hackerNewsService = hackerNewsService;
    }

    @Provides
    public HackerNewsService provideHackerNewsService() {
        return hackerNewsService;
    }

}
