package kr.co.dementor.dementorbank.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.ArrayList;

import kr.co.dementor.dementorbank.R;

/**
 * Created by dementor1 on 15. 11. 23..
 */
public class SelectImageGridAdapter extends BaseAdapter
{
    private Context            mContext = null;

    private ArrayList<ImageInfo> mList = new ArrayList<ImageInfo>();

    public SelectImageGridAdapter(Context context)
    {
        mContext = context;
    }

    public void setListItems(ArrayList<ImageInfo> arrayList)
    {
        mList = arrayList;
    }

    public ArrayList<ImageInfo> getListItems()
    {
        return mList;
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item_select_grid, parent, false);

            ViewHolder vh = new ViewHolder();

            vh.cb = (CheckBox) convertView.findViewById(R.id.cbSelectGridItemCheckBox);

            vh.iv = (ImageView) convertView.findViewById(R.id.ivSelectGridItem);

            convertView.setTag(vh);
        }

        ViewHolder vh = (ViewHolder)convertView.getTag();

        vh.iv.setImageResource(mList.get(position).getResId());

        vh.cb.setChecked(mList.get(position).isChecked());

        return convertView;
    }

    private class ViewHolder
    {
        public CheckBox  cb = null;
        public ImageView iv = null;
    }
}
