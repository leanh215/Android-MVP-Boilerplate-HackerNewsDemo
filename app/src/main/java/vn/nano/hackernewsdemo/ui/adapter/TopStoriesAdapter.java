package vn.nano.hackernewsdemo.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.nano.hackernewsdemo.R;
import vn.nano.hackernewsdemo.data.model.TopStory;
import vn.nano.hackernewsdemo.databinding.ItemTopStoryBinding;
import vn.nano.hackernewsdemo.utils.Callback;

/**
 * Created by alex on 9/15/17.
 */

public class TopStoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> topStoryIds;
    private Map<Integer, TopStory> mapStoryContent;
    private Callback<Integer> getStoryCallback;

    public TopStoriesAdapter(Callback<Integer> getStoryCallback) {
        topStoryIds = new ArrayList<>();
        mapStoryContent = new HashMap<>();
        this.getStoryCallback = getStoryCallback;
    }

    public void setTopStoryIds(List<Integer> topStoryIds) {
        this.topStoryIds = topStoryIds;
        notifyDataSetChanged();
    }

    public void addTopStory(TopStory topStory) {
        mapStoryContent.put(topStory.getId(), topStory);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTopStoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_top_story, parent, false);
        return new TopStoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemTopStoryBinding binding = ((TopStoryViewHolder)holder).binding;
        int topStoryId = topStoryIds.get(position);
        TopStory topStory = mapStoryContent.get(topStoryId);

        binding.tvPosition.setText((position+1) + "");

        if (topStory == null) {
            binding.pbLoading.setVisibility(View.VISIBLE);
            binding.layoutInfo.setVisibility(View.INVISIBLE);
            getStoryCallback.onCallback(topStoryId);
        } else {
            binding.pbLoading.setVisibility(View.GONE);
            binding.layoutInfo.setVisibility(View.VISIBLE);

            binding.tvTitle.setText(topStory.getTitle());
            binding.tvPointAndAuthor.setText(String.format(binding.getRoot().getContext()
                    .getString(R.string.point_and_author),
                    topStory.getScore()+"",
                    topStory.getBy()));
            binding.tvTime.setReferenceTime(topStory.getTime() * 1000);
            binding.tvComment.setText(String.format(binding.getRoot().getContext()
                    .getString(R.string.comment),
                    topStory.getKids() == null ? "0" : topStory.getKids().size() + ""));
        }
    }

    @Override
    public int getItemCount() {
        return topStoryIds.size();
    }

    class TopStoryViewHolder extends RecyclerView.ViewHolder {

        ItemTopStoryBinding binding;

        public TopStoryViewHolder(ItemTopStoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }


}
