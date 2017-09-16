package vn.nano.hackernewsdemo.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.nano.hackernewsdemo.R;
import vn.nano.hackernewsdemo.data.model.Comment;
import vn.nano.hackernewsdemo.databinding.ItemCommentBinding;
import vn.nano.hackernewsdemo.utils.Callback;

/**
 * Created by alex on 9/16/17.
 */

public class StoryCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> commentIds;
    private Map<Integer, Comment> mapComments;
    private Callback<Integer> getCommentCallback;

    public StoryCommentAdapter(Callback<Integer> getCommentCallback) {
        commentIds = new ArrayList<>();
        mapComments = new HashMap<>();
        this.getCommentCallback = getCommentCallback;
    }

    public void setCommentIds(List<Integer> commentIds) {
        this.commentIds = commentIds;
        notifyDataSetChanged();
    }

    public void addComment(Comment comment) {
        mapComments.put(comment.getId(), comment);
        notifyDataSetChanged();
    }

    public void removeDeletedComment(Comment comment) {
        for (int i = 0; i < commentIds.size(); i++) {
            if (commentIds.get(i).equals(comment.getId())) {
                commentIds.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_comment, parent, false);
        return new StoryCommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemCommentBinding binding = ((StoryCommentViewHolder)holder).binding;
        int commentId = commentIds.get(position);

        binding.tvPosition.setText((position+1) + "");

        Comment comment = mapComments.get(commentId);
        if (comment == null) {
            binding.pbLoading.setVisibility(View.VISIBLE);
            binding.layoutInfo.setVisibility(View.INVISIBLE);
            getCommentCallback.onCallback(commentId);
        } else {
            binding.pbLoading.setVisibility(View.GONE);
            binding.layoutInfo.setVisibility(View.VISIBLE);
            binding.tvAuthor.setText(comment.getBy());
            binding.tvTime.setReferenceTime(comment.getTime() * 1000);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                binding.tvComment.setText(Html.fromHtml(comment.getText(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                binding.tvComment.setText(Html.fromHtml(comment.getText()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return commentIds.size();
    }

    class StoryCommentViewHolder extends RecyclerView.ViewHolder {

        ItemCommentBinding binding;

        public StoryCommentViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
