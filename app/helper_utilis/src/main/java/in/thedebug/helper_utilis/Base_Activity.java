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
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created By Dev Saini
 **/


@SuppressLint("Registered, SourceLockedOrientationActivity")
public class Base_Activity extends AppCompatActivity {

    public Context context;
    private Dialog dialog;
    private Dialog dialogWithTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        context = this;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void gotoActivity(Intent intent) {
        startActivity(intent);
    }

    public void gotoActivity(Class<?> className, int code) {
        Intent intent = new Intent(this, className);
        startActivityForResult(intent, code);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void gotoActivity(Intent intent, int code) {
        startActivityForResult(intent, code);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void gotoActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);

    }

    public void gotoActivityFromBottom(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }

    public void gotoActivityWithBundle(Class<?> className, Bundle bundle) {
        Intent intent = new Intent(this, className);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void gotoActivityAndClearTop(Class<?> className) {
        Intent intent = new Intent(this, className);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void gotoActivityAndClearTop(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void showProgressDialog() {
        try {
            dismissProgressDialog();
            dialog = new Dialog(this, R.style.TransparentDialog);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_progress_dialog, null);
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

    public void showProgressDialogWithTitle(String str_title) {
        try {
            dismissProgressDialogWithTitle();
            dialogWithTitle = new Dialog(Base_Activity.this);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_progress_dialog_withtitle, null);

            TextView txt_title = view.findViewById(R.id.txt_title);
            txt_title.setText(str_title);

            dialogWithTitle.setContentView(view);
            dialogWithTitle.setCancelable(false);
            dialogWithTitle.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressDialogWithTitle() {
        try {
            if (dialogWithTitle != null && dialogWithTitle.isShowing()) {
                dialogWithTitle.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}




