package vn.nano.hackernewsdemo.ui;

import vn.nano.core_library.mvp.BaseTiView;
import vn.nano.hackernewsdemo.data.model.Comment;

/**
 * Created by alex on 9/16/17.
 */

public interface StoryCommentView extends BaseTiView{

    void displayComment(Comment comment);

    void removeDeletedComment(Comment comment);

}
