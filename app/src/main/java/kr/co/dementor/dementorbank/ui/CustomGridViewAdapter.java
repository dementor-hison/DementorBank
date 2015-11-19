package kr.co.dementor.dementorbank.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.LogTrace;

/**
 * Created by dementor1 on 15. 11. 5..
 */
public class CustomGridViewAdapter extends BaseAdapter
{
    private ArrayList<Integer> mListItems = new ArrayList<Integer>();

    public void setItemArrayList(ArrayList<Integer> listItem)
    {
        mListItems.clear();

        if (listItem == null)
        {
            LogTrace.e("listItem null");
            return;
        }

        mListItems.addAll(listItem);
    }

    public ArrayList<Integer> getListItems()
    {
        return mListItems;
    }

    @Override
    public int getCount()
    {
        return mListItems.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mListItems.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        SquareImageView iv = (SquareImageView) convertView;

        if (convertView == null)
        {
            iv = new SquareImageView(parent.getContext());

            iv.setBackgroundResource(R.drawable.grid_item_background);

            iv.setDrawingCacheEnabled(true);

            iv.setScaleType(ImageView.ScaleType.FIT_XY);

        }

        iv.setImageResource(mListItems.get(position));

        return iv;
    }

}
