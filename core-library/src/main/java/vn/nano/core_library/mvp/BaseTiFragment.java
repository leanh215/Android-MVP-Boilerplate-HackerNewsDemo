package vn.nano.core_library.mvp;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;

import net.grandcentrix.thirtyinch.TiFragment;

import vn.nano.core_library.R;
import vn.nano.core_library.utils.ApiErrorUtils;


/**
 * Created by alex on 8/22/17.
 */

/**
 * This base fragment implement basic functions defined in BaseTiView
 * Override these functions to change default behaviour (such as you might want to override
 * showLoading() to replace loading dialog by SwipeRefreshLayout )
 * @param <P>
 * @param <V>
 */
public abstract class BaseTiFragment<P extends BaseTiPresenter<V>, V extends BaseTiView> extends TiFragment<P, V>
    implements BaseTiView{

    MaterialDialog loadingDialog;
    MaterialDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        loadingDialog = new MaterialDialog.Builder(getActivity())
                .customView(R.layout.layout_progress_dialog, false)
                .backgroundColor(Color.parseColor("#01000000"))
                .build();

        alertDialog = new MaterialDialog.Builder(getActivity())
                .positiveText(R.string.ok)
                .build();
    }

    @Override
    public void showLoading(boolean cancelable) {
        loadingDialog.setCancelable(cancelable);
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void showAlert(String message) {
        alertDialog.setContent(message);
        alertDialog.show();
    }

    @Override
    public void showError(Throwable throwable, Class clazz) {
        String errorMessage = ApiErrorUtils.getError(throwable, clazz);
        showAlert(errorMessage);
    }

}
