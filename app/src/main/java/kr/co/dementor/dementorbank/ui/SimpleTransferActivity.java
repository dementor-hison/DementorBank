package kr.co.dementor.dementorbank.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.Set;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.adapter.DepositPagerAdapter;
import kr.co.dementor.dementorbank.common.LogTrace;
import kr.co.dementor.dementorbank.kr.co.dementor.dementorbank.fragment.DepositFragment;

/**
 * Created by dementor1 on 15. 11. 24..
 */
public class SimpleTransferActivity extends FragmentActivity
{
    private TopView m_transferTopView = null;
    private ViewPager m_vpDepositPager = null;
    private DepositPagerAdapter mDepositPagerAdapter = null;
    private ArrayList<DepositInfo> mArraylist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.simple_transfer_activity);

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();

        setPagerView();
    }

    private void setPagerView() {
        mDepositPagerAdapter = new DepositPagerAdapter(getSupportFragmentManager());
        mDepositPagerAdapter.setDepositItems(makeTempList());

        m_vpDepositPager.setAdapter(mDepositPagerAdapter);
        m_vpDepositPager.setOffscreenPageLimit(4);
        m_vpDepositPager.addOnPageChangeListener(mPageChangeListener);
        m_vpDepositPager.setCurrentItem(0);
    }

    private void initView()
    {
        m_transferTopView = (TopView) findViewById(R.id.transferTopView);
        m_transferTopView.setOnTopViewListener(mOnTopViewListener);
        m_transferTopView.setRefreshButtonVisible(false);
        m_transferTopView.setHelpButtonVisible(false);
        m_transferTopView.setConfirmButtonVisible(false);

        m_vpDepositPager = (ViewPager)findViewById(R.id.vpDepositPager);
    }

    private ArrayList<DepositInfo> makeTempList() {
        //가라....
        mArraylist = new ArrayList<>();
        DepositInfo item = new DepositInfo();
        item.depositName = "보통예금";
        item.depositNum = "1234-5678-900";
        item.setTotalMoney(1000000);
        mArraylist.add(item);
        item = new DepositInfo();
        item.depositName = "저축예금";
        item.depositNum = "1234-5678-900";
        item.setTotalMoney(2000000);
        mArraylist.add(item);
        item = new DepositInfo();
        item.depositName = "청약저축";
        item.depositNum = "1234-5678-900";
        item.setTotalMoney(3000000);
        mArraylist.add(item);
        item = new DepositInfo();
        item.depositName = "월급통장";
        item.depositNum = "1234-5678-900";
        item.setTotalMoney(3000000);
        mArraylist.add(item);
        return  mArraylist;
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
            DepositFragment f = (DepositFragment)mDepositPagerAdapter.getItem(position);
        }

        @Override
        public void onPageSelected(int position)
        {
            LogTrace.d("position : " + position);
            DepositFragment f = (DepositFragment)mDepositPagerAdapter.getItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
            LogTrace.d("");
        }
    };


}
