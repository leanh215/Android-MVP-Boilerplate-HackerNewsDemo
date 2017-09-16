package vn.nano.hackernewsdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import vn.nano.core_library.mvp.BaseTiFragment;
import vn.nano.hackernewsdemo.R;
import vn.nano.hackernewsdemo.data.model.Comment;
import vn.nano.hackernewsdemo.data.model.Story;
import vn.nano.hackernewsdemo.databinding.FragmentStoryCommentBinding;
import vn.nano.hackernewsdemo.ui.adapter.StoryCommentAdapter;
import vn.nano.hackernewsdemo.utils.Callback;

/**
 * Created by alex on 9/16/17.
 */

public class StoryCommentFragment extends BaseTiFragment<StoryCommentPresenter, StoryCommentView>
    implements StoryCommentView{

    private static final String KEY_STORY = "story";

    public static StoryCommentFragment getInstance(Story story) {
        StoryCommentFragment fragment = new StoryCommentFragment();
        Bundle args = new Bundle();
        args.putString(KEY_STORY, new Gson().toJson(story));
        fragment.setArguments(args);
        return fragment;
    }

    FragmentStoryCommentBinding mBinding;
    StoryCommentAdapter mCommentAdapter;

    @NonNull
    @Override
    public StoryCommentPresenter providePresenter() {
        StoryCommentPresenter presenter = new StoryCommentPresenter();
        presenter.setStory(new Gson().fromJson(getArguments().getString(KEY_STORY), Story.class));
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_story_comment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        mBinding.getRoot().setOnClickListener(null);
        mBinding.toolBar.setNavigationOnClickListener(view -> getActivity().onBackPressed());

        // title
        Story story = getPresenter().getStory();
        mBinding.toolBar.setTitle(story.getTitle());

        // comment adapter
        mBinding.rvComment.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCommentAdapter = new StoryCommentAdapter(getCommentCallback());
        mBinding.rvComment.setAdapter(mCommentAdapter);

        // check comment
        if (story.getKids() == null) {
            mBinding.layoutNoComment.setVisibility(View.VISIBLE);
            mBinding.rvComment.setVisibility(View.GONE);
        } else {
            mBinding.layoutNoComment.setVisibility(View.GONE);
            mBinding.rvComment.setVisibility(View.VISIBLE);
            mCommentAdapter.setCommentIds(story.getKids());
        }
    }

    private Callback<Integer> getCommentCallback() {
        return commentId -> getPresenter().getComment(commentId);
    }

    // ---------- API CALLBACK ---------
    // ---------- API CALLBACK ---------
    // ---------- API CALLBACK ---------

    @Override
    public void displayComment(Comment comment) {
        mBinding.rvComment.post(() -> mCommentAdapter.addComment(comment));
    }

    @Override
    public void removeDeletedComment(Comment comment) {
        mBinding.rvComment.post(() -> mCommentAdapter.removeDeletedComment(comment));
    }
}
