package kr.co.dementor.dementorbank.kr.co.dementor.dementorbank.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.LogTrace;
import kr.co.dementor.dementorbank.ui.DepositInfo;

/**
 * Created by dementor1 on 15. 11. 24..
 */
public class DepositPagerAdapter extends PagerAdapter
{
    private Context                mContext   = null;
    private LayoutInflater mInflater = null;
    private ArrayList<DepositInfo> mArraylist = new ArrayList<DepositInfo>();

    public DepositPagerAdapter(Context context)
    {
        mContext = context;

        //가라....
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
    }

    public void setItemList(ArrayList<DepositInfo> items)
    {
        mArraylist = items;
    }

    @Override
    public int getCount()
    {
        return mArraylist.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        LogTrace.d("position : " + position);

        mInflater = LayoutInflater.from(mContext);

        View item = mInflater.inflate(R.layout.item_deposit_grid, container, false);

        ((TextView)item.findViewById(R.id.tvDepositGridItemTitle)).setText(mArraylist.get(position).depositName);

        ((TextView)item.findViewById(R.id.tvDepositGridItemSub1)).setText(mArraylist.get(position).getTotalMoney());

        ((TextView)item.findViewById(R.id.tvDepositGridItemSub2)).setText(mArraylist.get(position).depositNum);

        container.addView(item);

        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return (view == object);
    }

    @Override
    public float getPageWidth(int position)
    {
        //return ((float) position / 3);
        LogTrace.d("position : " + position);
        return 0.33f;
    }
}
