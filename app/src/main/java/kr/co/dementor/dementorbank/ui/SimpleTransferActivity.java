package kr.co.dementor.dementorbank.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.LogTrace;

/**
 * Created by dementor1 on 15. 11. 24..
 */
public class SimpleTransferActivity extends FragmentActivity
{
    private TopView m_transferTopView = null;
    private DepositSelectView m_depositSelectView;
    private TextView          m_tvDepositInfoTitle;
    private TextView          m_tvDepositInfoSub;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.simple_transfer_activity);

        initView();

    }

    @Override
    protected void onResume()
    {

        LogTrace.d("");

        super.onResume();
    }

    @Override
    protected void onResumeFragments()
    {
        LogTrace.d("");
        super.onResumeFragments();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                m_depositSelectView.setDepositList(makeDepositItems(), getSupportFragmentManager());
            }
        }, 100);
    }

    @Override
    public void onAttachFragment(Fragment fragment)
    {
        LogTrace.d("");
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onPostResume()
    {
        LogTrace.d("");
        super.onPostResume();
    }



    private void initView()
    {
        m_transferTopView = (TopView) findViewById(R.id.transferTopView);
        m_transferTopView.setOnTopViewListener(mOnTopViewListener);
        m_transferTopView.setRefreshButtonVisible(false);
        m_transferTopView.setHelpButtonVisible(false);
        m_transferTopView.setConfirmButtonVisible(false);

        m_depositSelectView = (DepositSelectView)findViewById(R.id.depositSelectView);
        m_depositSelectView.setOnDepositSelectListener(mOnDepositSelectListener);

        m_tvDepositInfoTitle = (TextView)findViewById(R.id.tvDepositInfoTitle);
        m_tvDepositInfoSub = (TextView)findViewById(R.id.tvDepositInfoSub);
    }

    private DepositInfo[] makeDepositItems()
    {
        DepositInfo[] infos = new DepositInfo[4];
        infos[0] = new DepositInfo();
        infos[0].depositName = "보통예금";
        infos[0].depositNum = "111-222-3333";
        infos[0].setTotalMoney(1000000);

        infos[1] = new DepositInfo();
        infos[1].depositName = "저축예금";
        infos[1].depositNum = "111-222-3333";
        infos[1].setTotalMoney(2000000);

        infos[2] = new DepositInfo();
        infos[2].depositName = "청약통장";
        infos[2].depositNum = "111-222-3333";
        infos[2].setTotalMoney(3000000);

        infos[3] = new DepositInfo();
        infos[3].depositName = "월급통장";
        infos[3].depositNum = "111-222-3333";
        infos[3].setTotalMoney(4000000);

        return infos;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }


    TopView.OnTopViewListener mOnTopViewListener = new TopView.OnTopViewListener()
    {
        @Override
        public void OnBack()
        {
            finish();
        }

        @Override
        public void OnRefresh()
        {
            //not used...
        }

        @Override
        public void OnHelp()
        {
            //not used...
        }

        @Override
        public void OnConfirm()
        {
            //not used...
        }
    };

    DepositSelectView.OnDepositSelectListener mOnDepositSelectListener = new DepositSelectView.OnDepositSelectListener()
    {
        @Override
        public void OnDepositSelect(int index, DepositInfo item)
        {
            m_tvDepositInfoTitle.setText(item.depositName);
            m_tvDepositInfoSub.setText(item.depositNum);
        }
    };

}
