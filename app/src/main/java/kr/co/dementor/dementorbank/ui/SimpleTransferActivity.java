package kr.co.dementor.dementorbank.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.LogTrace;
import kr.co.dementor.dementorbank.kr.co.dementor.dementorbank.adapter.DepositPagerAdapter;

/**
 * Created by dementor1 on 15. 11. 24..
 */
public class SimpleTransferActivity extends FragmentActivity
{
    private TopView m_transferTopView = null;
    private ViewPager m_vpDepositPager = null;
    private DepositPagerAdapter mDepositPagerAdapter = new DepositPagerAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.simple_transfer_activity);

        initView();

    }

    private void initView()
    {
        m_transferTopView = (TopView) findViewById(R.id.transferTopView);
        m_transferTopView.setOnTopViewListener(mOnTopViewListener);
        m_transferTopView.setRefreshButtonVisible(false);
        m_transferTopView.setHelpButtonVisible(false);
        m_transferTopView.setConfirmButtonVisible(false);

        m_vpDepositPager = (ViewPager)findViewById(R.id.vpDepositPager);
        m_vpDepositPager.setAdapter(mDepositPagerAdapter);
        m_vpDepositPager.setOnPageChangeListener(mPageChangeListener);
        m_vpDepositPager.setCurrentItem(1);

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


    ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            LogTrace.d("position : " + position);
        }

        @Override
        public void onPageSelected(int position)
        {
            LogTrace.d("position : " + position);
            LogTrace.d("isFocusableInTouchMode : " + m_vpDepositPager.isFocusableInTouchMode());

        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
            LogTrace.d("");
        }
    };


}
