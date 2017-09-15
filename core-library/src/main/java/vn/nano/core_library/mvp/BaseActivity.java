package vn.nano.core_library.mvp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;

import net.grandcentrix.thirtyinch.TiActivity;

import vn.nano.core_library.R;

/**
 * Created by alex on 8/22/17.
 */

/**
 * BaseActivity with default functions implemented
 */
public abstract class BaseActivity extends TiActivity<BaseTiPresenter<BaseTiView>, BaseTiView> implements
    BaseTiView{

    MaterialDialog loadingDialog;
    MaterialDialog alertDialog;

    @NonNull
    @Override
    public BaseTiPresenter<BaseTiView> providePresenter() {
        return new BaseTiPresenter<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        loadingDialog = new MaterialDialog.Builder(this)
                .customView(R.layout.layout_progress_dialog, false)
                .backgroundColor(Color.parseColor("#01000000"))
                .build();

        alertDialog = new MaterialDialog.Builder(this)
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

    }
}
