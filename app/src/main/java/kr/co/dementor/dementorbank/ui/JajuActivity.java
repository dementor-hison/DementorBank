package kr.co.dementor.dementorbank.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import kr.co.dementor.dementorbank.R;

/**
 * Created by dementor1 on 15. 11. 26..
 */
public class JajuActivity extends Activity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jaju_activity);

        TopView topview = (TopView) findViewById(R.id.jajuTopview);
        topview.setConfirmButtonVisible(false);
        topview.setHelpButtonVisible(false);
        topview.setRefreshButtonVisible(false);
        topview.setOnTopViewListener(mOnTopviewListener);

        findViewById(R.id.ibJajuNextActivity1).setOnClickListener(this);
        findViewById(R.id.ibJajuPrevActivity1).setOnClickListener(this);
        findViewById(R.id.ibJajuNextActivity2).setOnClickListener(this);
        findViewById(R.id.ibJajuPrevActivity2).setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ibJajuNextActivity1:
            case R.id.ibJajuNextActivity2:
                Toast.makeText(getApplicationContext(), "자주쓰는계좌 추가기능 예정입니다.", Toast.LENGTH_SHORT).show();
                break;

            default:
                finish();
                break;
        }
    }

    TopView.OnTopViewListener mOnTopviewListener = new TopView.OnTopViewListener()
    {
        @Override
        public void OnBack()
        {
            finish();
        }

        @Override
        public void OnRefresh()
        {

        }

        @Override
        public void OnHelp()
        {

        }

        @Override
        public void OnConfirm()
        {

        }
    };

}
