package vn.nano.core_library.mvp;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler;
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by alex on 8/22/17.
 */

public class BaseTiPresenter <V extends BaseTiView>extends TiPresenter<V> {

    RxTiPresenterDisposableHandler handler = new RxTiPresenterDisposableHandler(this);

    public void manageDisposable(Disposable disposable) {
        handler.manageDisposable(disposable);
    }


}
