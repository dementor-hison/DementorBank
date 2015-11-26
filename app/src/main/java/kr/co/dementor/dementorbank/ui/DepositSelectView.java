package kr.co.dementor.dementorbank.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.LogTrace;

/**
 * Created by dementor1 on 15. 11. 26..
 */
public class DepositSelectView extends LinearLayout implements View.OnClickListener, ViewPager.OnPageChangeListener
{
    private ViewPager               mDepositViewPager;
    private DepositAdapter          mDepositAdapter;
    private ImageView               mPre;
    private ImageView               mNext;
    private DepositInfo[]           mItems;
    private OnDepositSelectListener mOnDepositSelectListener = null;

    public DepositSelectView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.deposit_select_view, this, true);

        mDepositViewPager = (ViewPager) findViewById(R.id.vpDepositPager);
        mDepositViewPager.setOnPageChangeListener(this);

        mPre = (ImageView) findViewById(R.id.ibPrevDeposit);
        mPre.setOnClickListener(this);
        mNext = (ImageView) findViewById(R.id.ibNextDeposit);
        mNext.setOnClickListener(this);
    }

    public void setDepositList(DepositInfo[] infos, FragmentManager fm, int defaultPosition)
    {
        LogTrace.d("infos length : " + infos.length);
        if (mDepositAdapter == null)
        {
            mDepositAdapter = new DepositAdapter(fm);
            mDepositViewPager.setAdapter(mDepositAdapter);
        }

        mItems = infos;
        mDepositAdapter.setDepositItems(mItems);
        mDepositAdapter.notifyDataSetChanged();
        mDepositViewPager.setPageMargin((int) (-mDepositViewPager.getWidth() * 0.67));
        mDepositViewPager.setOffscreenPageLimit(4);

        if(defaultPosition >= mDepositAdapter.getCount())
        {
            mDepositViewPager.setCurrentItem(0);
            mDepositAdapter.displaySelected(0);
        }

        mDepositViewPager.setCurrentItem(defaultPosition);
        mDepositAdapter.displaySelected(defaultPosition);

    }

    @Override
    public void onClick(View v) {
        int index = mDepositViewPager.getCurrentItem();

        if (v == mPre)
            mDepositViewPager.setCurrentItem(index-1, true);
        else if (v == mNext)
            mDepositViewPager.setCurrentItem(index+1, true);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int index) {
        mDepositAdapter.displaySelected(index);

        //if (mDepositViewPager.getCurrentItem() != index)
        //    mDepositViewPager.setCurrentItem(index, true);

        if(mOnDepositSelectListener != null)
        {
            mOnDepositSelectListener.OnDepositSelect(index, mItems[index]);
        }

    }

    public void setOnDepositSelectListener(OnDepositSelectListener listener)
    {
        mOnDepositSelectListener = listener;
    }

    public int getCurrentPosition()
    {
        return mDepositViewPager.getCurrentItem();
    }

    public interface OnDepositSelectListener
    {
        void OnDepositSelect(int index, DepositInfo item);
    }

    private class DepositAdapter extends FragmentStatePagerAdapter
    {
        private DepositFragment[] mFagments = null;
        private DepositInfo[]     mDepositInfos;

        public DepositAdapter(FragmentManager fm)
        {
            super(fm);
        }

        public void setDepositItems(DepositInfo[] infos)
        {
            LogTrace.d("infos length : " + infos.length);
            mDepositInfos = infos;
            if(mFagments == null)
            {
                mFagments = new DepositFragment[mDepositInfos.length];
            }
        }

        public void displaySelected(int position)
        {
            for (int i = 0; i < mFagments.length; i++)
            {
                DepositFragment fragemnt = mFagments[i];
                fragemnt.displaySelected(position == i);
            }
        }

        @Override
        public int getCount()
        {
            if (mDepositInfos == null)
                return 0;

            return mDepositInfos.length;
        }

        @Override
        public Fragment getItem(int position)
        {
            LogTrace.d("position : " + position);
            mFagments[position] = new DepositFragment(mDepositInfos[position], position);
            return mFagments[position];
        }
    }

    private class DepositFragment extends Fragment implements View.OnClickListener
    {

        private DepositInfo  mInfo;
        private int          mPosition;
        private LinearLayout m_llLayout;
        private TextView     m_tvTitle;
        private TextView     m_tvSub1;
        private TextView     m_tvSub2;

        public DepositFragment(DepositInfo info, int position)
        {
            super();
            mPosition = position;
            mInfo = info;
        }

        public void displaySelected(boolean isSelect)
        {
            m_llLayout.setSelected(isSelect);
            if(isSelect)
            {
                m_llLayout.setBackgroundResource(R.drawable.move_box);
            }
            else
            {
                m_llLayout.setBackgroundResource(android.R.color.transparent);
            }

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.item_deposit_grid, null);

            m_llLayout = (LinearLayout) rootView.findViewById(R.id.llDepositItem);
            m_tvTitle = (TextView) rootView.findViewById(R.id.tvDepositGridItemTitle);
            m_tvSub1 = (TextView) rootView.findViewById(R.id.tvDepositGridItemSub1);
            m_tvSub2 = (TextView) rootView.findViewById(R.id.tvDepositGridItemSub2);

            m_tvTitle.setText(mInfo.depositName);
            m_tvSub1.setText(mInfo.getTotalMoney());
            m_tvSub2.setText(mInfo.depositNum);

            m_llLayout.setOnClickListener(this);

            return rootView;
        }

        @Override
        public void onClick(View v)
        {
            DepositSelectView.this.mDepositViewPager.setCurrentItem(mPosition, true);
        }
    }
}
