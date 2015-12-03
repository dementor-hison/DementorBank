package kr.co.dementor.dementorbank.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.co.dementor.dementorbank.R;

/**
 * Created by dementor1 on 15. 11. 27..
 */
public class BankMainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bank_main_activity);

        findViewById(R.id.ibLogOut).setOnClickListener(mOnClickListener);
        findViewById(R.id.ibSearchMyDeposit).setOnClickListener(mOnClickListener);
        findViewById(R.id.ibSimpleTransfer).setOnClickListener(mOnClickListener);
        findViewById(R.id.btBankMainVideo).setOnClickListener(mOnClickListener);
    }


    View.OnClickListener mOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = null;

            switch (v.getId())
            {
                case R.id.ibLogOut:
                    finish();
                    break;

                case R.id.ibSearchMyDeposit:
                    intent = new Intent(getApplicationContext(), SearchMyDepositActivity.class);
                    startActivity(intent);
                    break;

                case R.id.ibSimpleTransfer:
                    intent = new Intent(getApplicationContext(), SimpleTransferActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btBankMainVideo:
                    intent = new Intent(getApplicationContext(), MediaPlayerActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
