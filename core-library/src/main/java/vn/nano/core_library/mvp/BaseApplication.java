package vn.nano.core_library.mvp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;

import timber.log.Timber;
/**
 * Created by alex on 9/14/17.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Stetho
        Stetho.initializeWithDefaults(this);

        // Fresco
        Fresco.initialize(this);

        // Timber
        Timber.plant(new Timber.DebugTree());

    }
}
