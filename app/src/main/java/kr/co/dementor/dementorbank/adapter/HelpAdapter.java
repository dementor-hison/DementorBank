package kr.co.dementor.dementorbank.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import kr.co.dementor.dementorbank.R;

/**
 * Created by dementor1 on 15. 12. 3..
 */
public class HelpAdapter extends PagerAdapter{

    private final ArrayList<Integer> mListItems = new ArrayList<>();
    private Context mContext = null;
    private LayoutInflater mLayoutInflater = null;
    private ViewPager m_viewPager = null;

    public HelpAdapter(Context context, ArrayList<Integer> listItems) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListItems.clear();
        mListItems.addAll(listItems);
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        m_viewPager = (ViewPager)container;
        View itemView = mLayoutInflater.inflate(R.layout.item_help, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.ivHelp);
        imageView.setImageResource(mListItems.get(position));
        imageView.setOnClickListener(mOnClickListener);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            m_viewPager.setCurrentItem(m_viewPager.getCurrentItem() + 1 , true);
        }
    };
}
