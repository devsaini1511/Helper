package in.thedebug.helper_utilis;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;


@SuppressLint("SourceLockedOrientationActivity")
public class Base_Fragment extends Fragment {

    String userID;
    Context context;
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = getContext();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void gotoActivity(Intent intent) {
        startActivity(intent);
    }

    public void gotoActivity(Class<?> className) {
        Intent intent = new Intent(getActivity(), className);
        startActivity(intent);
    }

    public void gotoActivity(Class<?> className, int code) {
        Intent intent = new Intent(getActivity(), className);
        startActivityForResult(intent, code);
        Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void showProgressDialog() {
        try {
            dismissProgressDialog();
            dialog = new Dialog(Objects.requireNonNull(getContext()), R.style.TransparentDialog);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_progress_dialog, null);
            dialog.setContentView(view);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
