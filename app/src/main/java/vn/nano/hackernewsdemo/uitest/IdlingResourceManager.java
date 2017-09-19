package vn.nano.hackernewsdemo.uitest;

/**
 * Created by alex on 9/19/17.
 */

public class IdlingResourceManager {

    private static IdlingResourceManager instance;

    private SimpleIdlingResource mIdlingResource;

    private IdlingResourceManager() {
    }

    public static IdlingResourceManager getInstance() {
        if (instance == null) instance = new IdlingResourceManager();
        return instance;
    }

    public void setIdlingResource(SimpleIdlingResource simpleIdlingResource) {
        mIdlingResource = simpleIdlingResource;
    }

    public SimpleIdlingResource getIdlingResource() {
        return mIdlingResource;
    }

}
