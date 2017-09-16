package vn.nano.hackernewsdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import timber.log.Timber;
import vn.nano.core_library.mvp.BaseTiFragment;
import vn.nano.hackernewsdemo.R;
import vn.nano.hackernewsdemo.data.model.Story;
import vn.nano.hackernewsdemo.databinding.FragmentTopStoriesBinding;
import vn.nano.hackernewsdemo.ui.adapter.TopStoriesAdapter;
import vn.nano.hackernewsdemo.utils.Callback;

/**
 * Created by alex on 9/14/17.
 */

public class TopStoriesFragment extends BaseTiFragment<TopStoriesPresenter, TopStoriesView>
    implements TopStoriesView{

    public static TopStoriesFragment getInstance() {
        return new TopStoriesFragment();
    }

    FragmentTopStoriesBinding mBinding;
    TopStoriesAdapter mStoriesAdapter;

    @NonNull
    @Override
    public TopStoriesPresenter providePresenter() {
        Timber.e("providePresenter()");
        return new TopStoriesPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.e("onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Timber.e("onCreateView()");
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_stories, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        if (getPresenter().getStoryIds() != null) {
            displayStoryIds(getPresenter().getStoryIds());
        } else {
            getPresenter().getTopStoryIds();
        }

    }

    private void initUI() {
        // recycle view
        mBinding.rvTopStories.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStoriesAdapter = new TopStoriesAdapter(getStoryCallback(), getCommentCallback());
        mBinding.rvTopStories.setAdapter(mStoriesAdapter);

        // pull to refresh
        mBinding.swipeLayout.setOnRefreshListener(() -> getPresenter().getTopStoryIds());
    }

    @Override
    public void showLoading(boolean cancelable) {
//        super.showLoading(cancelable);
        mBinding.swipeLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
//        super.hideLoading();
        mBinding.swipeLayout.setRefreshing(false);
    }

    private Callback<Integer> getStoryCallback() {
        return storyId -> getPresenter().getTopStory(storyId);
    }

    private Callback<Story> getCommentCallback() {
        return story ->
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, 0, 0, R.anim.exit_to_right)
                    .add(android.R.id.content, StoryCommentFragment.getInstance(story))
                    .addToBackStack(null)
                    .commit();
    }

    // ---------- API CALLBACK ---------
    // ---------- API CALLBACK ---------
    // ---------- API CALLBACK ---------

    @Override
    public void displayStoryIds(List<Integer> storyIds) {
        mStoriesAdapter.setTopStoryIds(storyIds);
    }

    @Override
    public void displayStory(Story story) {
//        mStoriesAdapter.addTopStory(topStory);
        mBinding.rvTopStories.post(() -> mStoriesAdapter.addTopStory(story));
    }


}
