package vn.nano.hackernewsdemo.ui;

import java.util.List;

import vn.nano.core_library.mvp.BaseTiView;
import vn.nano.hackernewsdemo.data.model.Story;

/**
 * Created by alex on 9/14/17.
 */

public interface TopStoriesView extends BaseTiView {

    void displayStoryIds(List<Integer> storyIds);

    void displayStory(Story topStory);

}
