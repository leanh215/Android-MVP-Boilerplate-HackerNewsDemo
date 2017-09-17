package vn.nano.hackernewsdemo.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import vn.nano.hackernewsdemo.dagger.module.AppModuleTest;

/**
 * Created by alex on 9/14/17.
 */

@Singleton
@Component(modules = {AppModuleTest.class})
public interface AppComponentTest extends AppComponent{

}
