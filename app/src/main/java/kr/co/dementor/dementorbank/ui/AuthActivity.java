package kr.co.dementor.dementorbank.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.LogTrace;

/**
 * Created by dementor1 on 15. 11. 17..
 */
public class AuthActivity extends FragmentActivity
{
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_activity);

        mContext = this;

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);

        boolean isKeyRegisted = pref.getBoolean(getString(R.string.preference_key_is_regist), false);
        if(isKeyRegisted == false)
        {
            showRegistDialog();
            return;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    private AlertDialog mAlertDialog = null;

    private void showRegistDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);

        dialogBuilder.setMessage(R.string.dialog_msg_need_regist);

        dialogBuilder.setPositiveButton(R.string.dialog_button_ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(mContext, RegistActivity.class);
                startActivity(intent);

                if (mAlertDialog != null && mAlertDialog.isShowing())
                {
                    mAlertDialog.dismiss();
                }

                finish();
            }
        });
        dialogBuilder.setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (mAlertDialog != null && mAlertDialog.isShowing())
                {
                    mAlertDialog.dismiss();
                }

                finish();
            }
        });
        dialogBuilder.setCancelable(false);

        mAlertDialog = dialogBuilder.show();
    }

    CustomGridView.OnDragListener mOnDragListener = new CustomGridView.OnDragListener()
    {
        @Override
        public void OnDragStart(int position)
        {
            LogTrace.d("position : " + position);
        }

        @Override
        public void OnDragEnd(int position)
        {
            LogTrace.d("position : " + position);
        }
    };

}
