package vn.nano.hackernewsdemo.ui;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import vn.nano.core_library.mvp.BaseTiPresenter;
import vn.nano.hackernewsdemo.HackerNewsApplication;
import vn.nano.hackernewsdemo.dagger.component.ComponentManager;
import vn.nano.hackernewsdemo.data.model.Comment;
import vn.nano.hackernewsdemo.data.model.Story;
import vn.nano.hackernewsdemo.data.remote.HackerNewsService;

/**
 * Created by alex on 9/16/17.
 */

public class StoryCommentPresenter extends BaseTiPresenter<StoryCommentView> {

    @Inject
    HackerNewsService hackerNewsService;

    private Map<Integer, Comment> mapDownloadedComments;
    private Map<Integer, Boolean> mapDownloadingComments;

    private Story story;

    public StoryCommentPresenter() {
        ComponentManager.getInstance().getAppComponent().inject(this);
        mapDownloadedComments = new HashMap<>();
        mapDownloadingComments = new HashMap<>();
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Map<Integer, Comment> getMapDownloadedComments() {
        return mapDownloadedComments;
    }

    public void getComment(int commentId) {
        if (mapDownloadingComments.get(commentId) == null || !mapDownloadingComments.get(commentId)) {
            if (mapDownloadedComments.get(commentId) != null) {
                sendToView(view -> view.displayComment(mapDownloadedComments.get(commentId)));
            } else {
                mapDownloadingComments.put(commentId, true);

                // with disposable manager
                manageDisposable(
                        hackerNewsService.getComment(commentId)
                                .doAfterTerminate(() -> mapDownloadingComments.put(commentId, false))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableObserver<Comment>() {
                                    @Override
                                    public void onNext(@NonNull Comment comment) {
                                        sendToView(view -> {
                                            if (comment.isDeleted()) {
                                                view.removeDeletedComment(comment);
                                            } else {
                                                mapDownloadedComments.put(commentId, comment);
                                                view.displayComment(comment);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable throwable) {
                                        throwable.printStackTrace();
                                    }

                                    @Override
                                    public void onComplete() {
                                    }
                                })
                );

                // without disposable manager
//                hackerNewsService.getComment(commentId)
//                        .doAfterTerminate(() -> mapDownloadingComments.put(commentId, false))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(comment -> {
//                            sendToView(view -> {
//                                if (comment.isDeleted()) {
//                                    view.removeDeletedComment(comment);
//                                } else {
//                                    mapDownloadedComments.put(commentId, comment);
//                                    view.displayComment(comment);
//                                }
//                            });
//                        }, throwable -> {
//                            throwable.printStackTrace();
//                        });
            }
        }
    }

}
