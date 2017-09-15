package vn.nano.core_library.mvp;

import net.grandcentrix.thirtyinch.TiView;

/**
 * Created by alex on 8/22/17.
 */

/**
 * Base view which defines some default functions
 * Add more basic
 */
public interface BaseTiView extends TiView {

    void showLoading(boolean cancelable);

    void hideLoading();

    void showAlert(String message);

    void showError(Throwable throwable, Class clazz);

}
