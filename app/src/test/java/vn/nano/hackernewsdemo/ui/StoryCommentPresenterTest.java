package vn.nano.hackernewsdemo.ui;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import vn.nano.hackernewsdemo.TrampolineSchedulerRule;
import vn.nano.hackernewsdemo.dagger.component.ComponentManager;
import vn.nano.hackernewsdemo.dagger.component.DaggerAppComponentTest;
import vn.nano.hackernewsdemo.dagger.module.AppModuleTest;
import vn.nano.hackernewsdemo.data.model.Comment;
import vn.nano.hackernewsdemo.data.remote.HackerNewsService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alex on 9/17/17.
 */

public class StoryCommentPresenterTest {

    @Rule
    public TrampolineSchedulerRule trampolineSchedulerRule = new TrampolineSchedulerRule();

    @Mock
    HackerNewsService hackerNewsService;

    @Mock
    StoryCommentView storyCommentView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ComponentManager.getInstance().init(
                DaggerAppComponentTest
                        .builder()
                        .appModuleTest(new AppModuleTest(hackerNewsService))
                        .build());
    }

    @Test
    public void getComment_TestOkResponse() throws InterruptedException {
        // create presenter
        StoryCommentPresenter presenter = new StoryCommentPresenter();
        presenter.create();
        presenter.attachView(storyCommentView);
        // force ui thread run on current thread
        presenter.setUiThreadExecutor(Runnable::run);

        // create fake data
        Comment comment = getFakeComment();

        // return fake data when getComment() called
        when(hackerNewsService.getComment(comment.getId())).thenReturn(Observable.just(comment));

        // start request
        presenter.getComment(comment.getId());

        // verify getComment() called
        verify(hackerNewsService).getComment(comment.getId());

        ArgumentCaptor<Comment> argumentCaptorComment = ArgumentCaptor.forClass(Comment.class);

        // verify displayComment() called
        verify(storyCommentView).displayComment(argumentCaptorComment.capture());

        // verify returned data
        assertEquals(comment, argumentCaptorComment.getValue());
    }

    @Test
    public void getComment_TestFailedResponse() {
        // create presenter
        StoryCommentPresenter presenter = new StoryCommentPresenter();
        presenter.create();
        presenter.attachView(storyCommentView);
        // force ui thread run on current thread
        presenter.setUiThreadExecutor(Runnable::run);

        // return error when getComment() called
        when(hackerNewsService.getComment(anyInt())).thenReturn(Observable.error(new Throwable("error")));

        // start request
        presenter.getComment(anyInt());

        // verify getComment() called
        verify(hackerNewsService).getComment(anyInt());
    }

    @Test
    public void getComment_RemoveDeletedComment() {
        // create presenter
        StoryCommentPresenter presenter = new StoryCommentPresenter();
        presenter.create();
        presenter.attachView(storyCommentView);
        presenter.setUiThreadExecutor(Runnable::run);

        // create fake comment, set deleted = true
        Comment comment = getFakeComment();
        comment.setDeleted(true);

        // set returned Comment when getComment() called
        when(hackerNewsService.getComment(anyInt())).thenReturn(Observable.just(comment));

        // start request
        presenter.getComment(anyInt());

        ArgumentCaptor<Comment> argumentCaptorComment = ArgumentCaptor.forClass(Comment.class);

        // verify removeDeletedComment() called
        verify(storyCommentView).removeDeletedComment(argumentCaptorComment.capture());

        // verify data
        assertEquals(comment, argumentCaptorComment.getValue());
    }

    @Test
    public void getComment_FromCached() {
        // create presenter
        StoryCommentPresenter presenter = new StoryCommentPresenter();
        presenter.create();
        presenter.attachView(storyCommentView);
        // force ui thread run on current thread
        presenter.setUiThreadExecutor(Runnable::run);

        // get fake comment. put to map
        Comment comment = getFakeComment();
        presenter.getMapDownloadedComments().put(comment.getId(), comment);

        // verify map contains comment
        assertTrue(presenter.getMapDownloadedComments().containsKey(comment.getId()));

        // start request
        presenter.getComment(comment.getId());

        ArgumentCaptor<Comment> argumentCaptorComment = ArgumentCaptor.forClass(Comment.class);

        // verify showComment() called
        verify(storyCommentView).displayComment(argumentCaptorComment.capture());

        // verify data
        assertEquals(comment, argumentCaptorComment.getValue());


    }

    private Comment getFakeComment() {
        Comment comment = new Comment();
        comment.setId(12345);
        return comment;
    }

}
