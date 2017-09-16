package vn.nano.hackernewsdemo.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import vn.nano.hackernewsdemo.dagger.module.AppModule;
import vn.nano.hackernewsdemo.ui.StoryCommentPresenter;
import vn.nano.hackernewsdemo.ui.TopStoriesPresenter;

/**
 * Created by alex on 9/14/17.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(TopStoriesPresenter presenter);

    void inject(StoryCommentPresenter presenter);

}
