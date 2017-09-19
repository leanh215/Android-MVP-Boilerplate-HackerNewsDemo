package vn.nano.hackernewsdemo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import timber.log.Timber;
import vn.nano.hackernewsdemo.ui.TopStoriesFragment;
import vn.nano.hackernewsdemo.uitest.IdlingResourceManager;
import vn.nano.hackernewsdemo.uitest.SimpleIdlingResource;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_FRAGMENT_STORIES = "fragment_stories";
    public static final String TAG_FRAGMENT_COMMENT = "fragment_comment";

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, TopStoriesFragment.getInstance(), TAG_FRAGMENT_STORIES)
                    .commit();
        }
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
            IdlingResourceManager.getInstance().setIdlingResource(mIdlingResource);
        }
        return IdlingResourceManager.getInstance().getIdlingResource();
    }
}
