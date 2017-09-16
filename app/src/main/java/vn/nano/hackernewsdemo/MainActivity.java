package vn.nano.hackernewsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import timber.log.Timber;
import vn.nano.hackernewsdemo.ui.TopStoriesFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_FRAGMENT_STORIES = "fragment_stories";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timber.e("savedInstanceState=" + savedInstanceState);
        Timber.e("containsFragmentStories=" + getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_STORIES));
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, TopStoriesFragment.getInstance(), TAG_FRAGMENT_STORIES)
                    .commit();
        }

    }
}
