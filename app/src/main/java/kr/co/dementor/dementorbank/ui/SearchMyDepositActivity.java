package kr.co.dementor.dementorbank.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.co.dementor.dementorbank.R;

/**
 * Created by dementor1 on 15. 11. 27..
 */
public class SearchMyDepositActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_my_deposit_activity);

        findViewById(R.id.ivMoveBankMain).setOnClickListener(mOnClickListener);
        findViewById(R.id.ivMoveAuth).setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.ivMoveBankMain:

                    finish();
                    break;

                case R.id.ivMoveAuth:

                    Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();

                default:
                    break;
            }
        }
    };
}
