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
public class SendTargetSelectView extends LinearLayout implements View.OnClickListener, ViewPager.OnPageChangeListener
{
    private ViewPager               mSendTargetViewPager;
    private SendTargetAdapter          mSendTargetAdapter;
    private ImageView               mPre;
    private ImageView               mNext;
    private SendTargetInfo[]           mItems;
    private OnSendTargetSelectListener mOnSendTargetSelectListener = null;

    public SendTargetSelectView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.send_target_select_view, this, true);

        mSendTargetViewPager = (ViewPager) findViewById(R.id.vpTargetIcons);
        mSendTargetViewPager.setOnPageChangeListener(this);

        mPre = (ImageView) findViewById(R.id.ibPrevTarget);
        mPre.setOnClickListener(this);
        mNext = (ImageView) findViewById(R.id.ibNextTarget);
        mNext.setOnClickListener(this);
    }

    public void setSendTargetList(SendTargetInfo[] infos, FragmentManager fm)
    {
        LogTrace.d("infos length : " + infos.length);
        if (mSendTargetAdapter == null)
        {
            mSendTargetAdapter = new SendTargetAdapter(fm);
            mSendTargetViewPager.setAdapter(mSendTargetAdapter);
        }

        mItems = infos;
        mSendTargetAdapter.setSendTargetItems(mItems);
        mSendTargetAdapter.notifyDataSetChanged();
        mSendTargetViewPager.setPageMargin((int) (-mSendTargetViewPager.getWidth() * 0.67));
        mSendTargetViewPager.setOffscreenPageLimit(4);

        mSendTargetViewPager.setCurrentItem(0);
        if (mItems.length > 2)
            mSendTargetViewPager.setCurrentItem(1);
    }

    @Override
    public void onClick(View v) {
        int index = mSendTargetViewPager.getCurrentItem();

        if (v == mPre)
            mSendTargetViewPager.setCurrentItem(index-1, true);
        else if (v == mNext)
            mSendTargetViewPager.setCurrentItem(index+1, true);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int index) {
        mSendTargetAdapter.displaySelected(index);

        if (mSendTargetViewPager.getCurrentItem() != index)
            mSendTargetViewPager.setCurrentItem(index, true);

        if(mOnSendTargetSelectListener != null)
        {
            mOnSendTargetSelectListener.OnSendTargetSelect(index, mItems[index]);
        }

    }

    public void setOnSendTargetSelectListener(OnSendTargetSelectListener listener)
    {
        mOnSendTargetSelectListener = listener;
    }

    public interface OnSendTargetSelectListener
    {
        void OnSendTargetSelect(int index, SendTargetInfo item);
    }

    private class SendTargetAdapter extends FragmentStatePagerAdapter
    {
        private SendTargetFragment[] mFagments;
        private SendTargetInfo[]     mSendTargetInfos;

        public SendTargetAdapter(FragmentManager fm)
        {
            super(fm);
        }

        public void setSendTargetItems(SendTargetInfo[] infos)
        {
            LogTrace.d("infos length : " + infos.length);
            mSendTargetInfos = infos;
            mFagments = new SendTargetFragment[mSendTargetInfos.length];
        }

        public void displaySelected(int position)
        {
            for (int i = 0; i < mFagments.length; i++)
            {
                SendTargetFragment fragemnt = mFagments[i];
                fragemnt.displaySelected(position == i);
            }
        }

        @Override
        public int getCount()
        {
            if (mSendTargetInfos == null)
                return 0;

            return mSendTargetInfos.length;
        }

        @Override
        public Fragment getItem(int position)
        {
            LogTrace.d("position : " + position);
            mFagments[position] = new SendTargetFragment(mSendTargetInfos[position], position);
            return mFagments[position];
        }
    }

    private class SendTargetFragment extends Fragment implements OnClickListener
    {

        private SendTargetInfo  mInfo;
        private int          mPosition;
        private LinearLayout m_llLayout;
        private TextView     m_tvTitle;
        private TextView     m_tvSub1;
        private TextView     m_tvSub2;

        public SendTargetFragment(SendTargetInfo info, int position)
        {
            super();
            LogTrace.d("position : " + position);
            mPosition = position;
            mInfo = info;
        }

        public void displaySelected(boolean isSelect)
        {
            m_llLayout.setSelected(isSelect);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.item_deposit_fragment, null);

            m_tvTitle = (ImageView) rootView.findViewById(R.id.ivSelectGridItem);
            m_tvSub1 = (TextView) rootView.findViewById(R.id.tvSendTargetGridItemSub1);
            m_tvSub2 = (TextView) rootView.findViewById(R.id.tvSendTargetGridItemSub2);

            m_tvTitle.setText(mInfo.depositName);
            m_tvSub1.setText(mInfo.getTotalMoney());
            m_tvSub2.setText(mInfo.depositNum);

            m_llLayout.setOnClickListener(this);

            return rootView;
        }

        @Override
        public void onClick(View v)
        {
            SendTargetSelectView.this.mSendTargetViewPager.setCurrentItem(mPosition, true);
        }
    }
}
