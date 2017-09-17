package vn.nano.hackernewsdemo.ui;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import vn.nano.hackernewsdemo.TrampolineSchedulerRule;
import vn.nano.hackernewsdemo.dagger.component.DaggerAppComponentTest;
import vn.nano.hackernewsdemo.dagger.module.AppModuleTest;
import vn.nano.hackernewsdemo.dagger.component.ComponentManager;
import vn.nano.hackernewsdemo.data.model.Story;
import vn.nano.hackernewsdemo.data.remote.HackerNewsService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;

/**
 * Created by alex on 9/16/17.
 */

public class TopStoriesPresenterTest {

    @Rule
    public TrampolineSchedulerRule mTrampolineSchedulerRule = new TrampolineSchedulerRule();

    @Mock
    HackerNewsService hackerNewsService;

    @Mock
    TopStoriesView topStoriesView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ComponentManager.getInstance().init(
                DaggerAppComponentTest.builder()
                        .appModuleTest(new AppModuleTest(hackerNewsService))
                        .build());
    }

    @Test
    public void getTopStoryIds_TestOkResponse() {
        TopStoriesPresenter presenter;presenter = new TopStoriesPresenter();
        presenter.create();
        presenter.attachView(topStoriesView);
        // force ui thread runs on current thread
        presenter.setUiThreadExecutor(Runnable::run);

        // create fake data
        List<Integer> topStoryIds = getFakeStoryIds();

        // return fake data when getTopStoryIds() called
        when(hackerNewsService.getTopStoryIds()).thenReturn(Observable.just(topStoryIds));

        // start request
        presenter.getTopStoryIds();

        // verify service calls getTopStoryIds() called
        verify(hackerNewsService).getTopStoryIds();

        // verify show loading
        verify(topStoriesView).showLoading(true);

        ArgumentCaptor<List<Integer>> argumentCaptorStoryIds = ArgumentCaptor.forClass(topStoryIds.getClass());

        // verify display data
        verify(topStoriesView).displayStoryIds(argumentCaptorStoryIds.capture());

        // verify data
        assertEquals(topStoryIds, argumentCaptorStoryIds.getValue());

        // verify hide loading
        verify(topStoriesView).hideLoading();

        // verify order of view calls
        InOrder inOrder = inOrder(topStoriesView);
        inOrder.verify(topStoriesView).showLoading(true);
        inOrder.verify(topStoriesView).displayStoryIds(argumentCaptorStoryIds.getValue());
        inOrder.verify(topStoriesView).hideLoading();
    }

    @Test
    public void getTopStoryIds_TestFailedResponse() {
        TopStoriesPresenter presenter = new TopStoriesPresenter();
        presenter.create();
        presenter.attachView(topStoriesView);
        // force ui thread runs on current thread
        presenter.setUiThreadExecutor(Runnable::run);

        when(hackerNewsService.getTopStoryIds()).thenReturn(Observable.error(new Throwable("error")));

        // start call request
        presenter.getTopStoryIds();

        // verify service call getTopStoryIds()
        verify(hackerNewsService).getTopStoryIds();

        // verify show loading
        verify(topStoriesView).showLoading(true);

        // verify show failed
        verify(topStoriesView).showError(any(Throwable.class), any());

        // verify hide loading
        verify(topStoriesView).hideLoading();

        // verify order of view calls
        InOrder inOrder = inOrder(topStoriesView);
        inOrder.verify(topStoriesView).showLoading(true);
        inOrder.verify(topStoriesView).showError(any(), any());
        inOrder.verify(topStoriesView).hideLoading();
    }

    @Test
    public void getTopStory_TestOkResponse() {
        TopStoriesPresenter presenter = new TopStoriesPresenter();
        presenter.create();
        presenter.attachView(topStoriesView);
        // force ui thread runs on current thread
        presenter.setUiThreadExecutor(Runnable::run);

        // create fake data
        Story story = getFakeStory();

        // return fake data when getTopStory() called
        when(hackerNewsService.getStory(story.getId())).thenReturn(Observable.just(story));

        // start request
        presenter.getTopStory(story.getId());

        // verify getTopStory() called
        verify(hackerNewsService).getStory(story.getId());

        ArgumentCaptor<Story> argumentCaptorStory = ArgumentCaptor.forClass(Story.class);

        // verify display data called
        verify(topStoriesView).displayStory(argumentCaptorStory.capture());

        // verify data
        assertEquals(story, argumentCaptorStory.getValue());
    }

    @Test
    public void getTopStory_TestFailedResponse() {
        TopStoriesPresenter presenter = new TopStoriesPresenter();
        presenter.create();
        presenter.attachView(topStoriesView);
        // force ui thread runs on current thread
        presenter.setUiThreadExecutor(Runnable::run);

        // return fake error
        when(hackerNewsService.getStory(anyInt())).thenReturn(Observable.error(new Throwable("error")));

        // start request
        presenter.getTopStory(anyInt());

        // verify getTopStory() called
        verify(hackerNewsService).getStory(anyInt());
    }

    private List<Integer> getFakeStoryIds() {
        return Arrays.asList(15263467, 15262829, 15262620);
    }

    private Story getFakeStory() {
        Story story = new Story();
        story.setId(15263467);
        return story;
    }

}
