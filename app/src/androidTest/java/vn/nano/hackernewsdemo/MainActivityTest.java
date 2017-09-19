package vn.nano.hackernewsdemo;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.nano.hackernewsdemo.ui.StoryCommentFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void mainActivityTest() {
        // Scenario
        // 1. Wait for TopStoriesFragment was added and top stories were loaded
        // 2. Move to StoryCommentFragment, wait for comment were loaded
        // 3. Back to TopStoriesFragment


        // Step 1. Wait for TopStoriesFragment was added and top stories were loaded

        try { // wait for TopStoriesFragment was added
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click first item to move to StoryCommentFragment
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_top_stories),
                        withParent(withId(R.id.swipe_layout)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        try { // wait to move to StoryCommentFragment
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // verify StoryCommentFragment was added
        StoryCommentFragment storyCommentFragment = (StoryCommentFragment) mActivityTestRule.getActivity()
                .getSupportFragmentManager().findFragmentByTag(MainActivity.TAG_FRAGMENT_COMMENT);
        assertNotNull(storyCommentFragment);

        // click back to TopStoriesFragment
        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")),
                        withParent(withId(R.id.tool_bar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        try { // wait to move to StoryCommentFragment
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // verify StoryCommentFragment was removed
        StoryCommentFragment storyCommentFragmentRemoved = (StoryCommentFragment) mActivityTestRule.getActivity()
                .getSupportFragmentManager().findFragmentByTag(MainActivity.TAG_FRAGMENT_COMMENT);
        assertNull(storyCommentFragmentRemoved);
    }

    @Test
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }


}
