package vn.nano.core_library.mvp;

import android.support.annotation.NonNull;

/**
 * Created by alex on 8/22/17.
 */

/**
 * BaseFragment with default functions implemented. Provide
 */
public class BaseFragment extends BaseTiFragment<BaseTiPresenter<BaseTiView>, BaseTiView>

    implements BaseTiView{
    @NonNull
    @Override
    public BaseTiPresenter providePresenter() {
        return new BaseTiPresenter();
    }

}
