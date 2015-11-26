package kr.co.dementor.dementorbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.LogTrace;
import kr.co.dementor.dementorbank.ui.SendBankingInfo;

/**
 * Created by dementor1 on 15. 11. 26..
 */
public class SendBankingAdapter extends ArrayAdapter<SendBankingInfo>
{
    private LayoutInflater             inflater   = null;
    private ArrayList<SendBankingInfo> infoList   = new ArrayList<SendBankingInfo>();
    private ViewHolder                 viewHolder = null;
    private Context                    mContext   = null;


    public SendBankingAdapter(Context context, int resource)
    {
        super(context, resource);
        mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    public SendBankingAdapter(Context context, int resource, int textViewResourceId)
    {
        super(context, resource, textViewResourceId);
        mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    public SendBankingAdapter(Context context, int resource, SendBankingInfo[] objects)
    {
        super(context, resource, objects);
        mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    public SendBankingAdapter(Context context, int resource, int textViewResourceId, SendBankingInfo[] objects)
    {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    public SendBankingAdapter(Context context, int resource, List<SendBankingInfo> objects)
    {
        super(context, resource, objects);
        mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    public SendBankingAdapter(Context context, int resource, int textViewResourceId, List<SendBankingInfo> objects)
    {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setBankingInfoList(ArrayList<SendBankingInfo> list)
    {
        infoList = list;
    }

    @Override
    public int getCount()
    {
        return infoList.size();
    }

    @Override
    public SendBankingInfo getItem(int position)
    {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent)
    {
        if (convertview == null)
        {
            viewHolder = new ViewHolder();
            convertview = inflater.inflate(R.layout.item_send_list, null);
            viewHolder.tvSendItemTargetName = (TextView) convertview.findViewById(R.id.tvSendItemTargetName);
            viewHolder.tvSendItemMoney = (TextView)convertview.findViewById(R.id.tvSendItemMoney);

            convertview.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertview.getTag();
        }

        LogTrace.d("targetName : " + getItem(position).sendTargetName + " , Money : " + getItem(position).getSendTotalMoneyValue());

        SendBankingInfo info = getItem(position);

        viewHolder.tvSendItemTargetName.setText(info.sendTargetName);

        viewHolder.tvSendItemMoney.setText(info.getSendTotalMoneyValue());

        return convertview;
    }

    public ArrayList<SendBankingInfo> getItems()
    {
        return infoList;
    }

    private class ViewHolder
    {
        public TextView tvSendItemTargetName;
        public TextView tvSendItemMoney;
    }
}
