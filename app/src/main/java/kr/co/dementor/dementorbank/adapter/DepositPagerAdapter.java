package kr.co.dementor.dementorbank.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.LogTrace;
import kr.co.dementor.dementorbank.kr.co.dementor.dementorbank.fragment.DepositFragment;
import kr.co.dementor.dementorbank.ui.DepositInfo;

/**
 * Created by dementor1 on 15. 11. 24..
 */
public class DepositPagerAdapter extends FragmentStatePagerAdapter
{
    private ArrayList<DepositInfo> mArrayList = new ArrayList<DepositInfo>();
    private DepositFragment[] fragments = null;

    public DepositPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setDepositItems(ArrayList<DepositInfo> items)
    {
        mArrayList.clear();
        mArrayList.addAll(items);
        fragments = new DepositFragment[items.size()];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);

    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
        ((ViewPager)container).setPageMargin((int) (container.getWidth() * -0.7));
    }

    @Override
    public Fragment getItem(int position) {
        fragments[position] = new DepositFragment();
        Bundle args = new Bundle();
        args.putString("title", mArrayList.get(position).depositName);
        args.putString("sub1", mArrayList.get(position).getTotalMoney());
        args.putString("sub2", mArrayList.get(position).depositNum);

        fragments[position].setArguments(args);
        return fragments[position];
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }
}
