package vn.nano.hackernewsdemo.dagger.component;

/**
 * Created by alex on 9/16/17.
 */

public class ComponentManager {

    private static ComponentManager componentManager;
    private AppComponent appComponent;

    private ComponentManager() {
    }

    public static ComponentManager getInstance() {
        if (componentManager == null) componentManager = new ComponentManager();
        return componentManager;
    }

    public void init(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


}
