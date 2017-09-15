package vn.nano.hackernewsdemo.ui;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.nano.core_library.mvp.BaseTiPresenter;
import vn.nano.hackernewsdemo.HackerNewsApplication;
import vn.nano.hackernewsdemo.data.model.TopStory;
import vn.nano.hackernewsdemo.data.remote.HackerNewsService;

/**
 * Created by alex on 9/14/17.
 */

public class TopStoriesPresenter extends BaseTiPresenter<TopStoriesView> {

    @Inject
    HackerNewsService hackerNewsService;

    private Map<Integer, TopStory> mapDownloadedStory;
    private Map<Integer, Boolean> mapDownloadingStory;

    public TopStoriesPresenter() {
        HackerNewsApplication.getInstance().getAppComponent().inject(this);
        mapDownloadedStory = new HashMap<>();
        mapDownloadingStory = new HashMap<>();
    }

    public void getTopStoryIds() {
        hackerNewsService.getTopStoryIds()
            .doOnSubscribe(disposable -> sendToView(view -> view.showLoading(true)))
            .doAfterTerminate(() -> sendToView(view -> view.hideLoading()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(storyIds -> {
                sendToView(view -> {
                    view.displayStoryIds(storyIds);
                });
            }, throwable -> {
                throwable.printStackTrace();
                sendToView(view -> {
                    view.showError(throwable, HackerNewsService.ApiError.class);
                });
            });
    }

    public void getTopStory(int storyId) {
        if (mapDownloadingStory.get(storyId) == null || !mapDownloadingStory.get(storyId)) {

            if (mapDownloadedStory.get(storyId) != null) {
                sendToView(view -> view.displayStory(mapDownloadedStory.get(storyId)));
                return;
            }

            mapDownloadingStory.put(storyId, true);
            hackerNewsService.getTopStory(storyId)
                    .doAfterTerminate(() -> mapDownloadingStory.put(storyId, false))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(topStory -> {
                        sendToView(view -> view.displayStory(topStory));
                    }, throwable -> {
                        throwable.printStackTrace();
                    });
        }
    }

}
