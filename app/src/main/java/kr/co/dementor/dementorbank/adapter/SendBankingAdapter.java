package kr.co.dementor.dementorbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.LogTrace;
import kr.co.dementor.dementorbank.ui.BankingInfomation;
import kr.co.dementor.dementorbank.ui.BankingUnit;

/**
 * Created by dementor1 on 15. 11. 26..
 */
public class SendBankingAdapter extends BaseExpandableListAdapter
{
    private LayoutInflater               inflater   = null;
    private ArrayList<BankingInfomation> bankingList   = new ArrayList<BankingInfomation>();

    public SendBankingAdapter(Context context)
    {
        super();

        inflater = LayoutInflater.from(context);
    }

    public void setBankingData(ArrayList<BankingInfomation> arrBankingList)
    {
        bankingList.clear();

        if(arrBankingList != null)
        {
            bankingList.addAll(arrBankingList);
        }
    }

    public void addBankingData(BankingInfomation bankingInfomation)
    {
        if(bankingInfomation != null)
        {
            bankingList.add(bankingInfomation);
        }
    }

    public void updateBankingData(String depositName, BankingUnit unit)
    {
        BankingInfomation info = getGroupInfo(depositName);
        info.addBanking(unit);
    }

    private BankingInfomation getGroupInfo(String depositName)
    {
        for (int index = 0; index < bankingList.size(); index++)
        {
            BankingInfomation info = bankingList.get(index);
            if(info.getDepositInfo().depositName == depositName)
            {
                return info;
            }
        }

        return null;
    }

    public ArrayList<BankingInfomation> getBankingData()
    {
        return bankingList;
    }

    public boolean contains(String depositName)
    {
        boolean result = false;

        for (BankingInfomation info : bankingList)
        {
            LogTrace.d("Find : " + depositName + " , target : " + info.getDepositInfo().depositName);

            if(info.getDepositInfo().depositName == depositName)
            {
                result = true;
                break;
            }
        }

        return result;
    }

    public int findGroupPosition(String targetName)
    {
        int index = 0;

        for (index = 0 ; index < bankingList.size(); index++)
        {
            if(bankingList.get(index).getDepositInfo().depositName == targetName)
            {
                return index;
            }
        }

        return index;
    }

    @Override
    public BankingInfomation getGroup(int groupPosition)
    {
        return bankingList.get(groupPosition);
    }

    @Override
    public int getGroupCount()
    {
        return bankingList.size();
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        GroupViewHolder groupViewHolder = null;

        if(convertView == null)
        {
            groupViewHolder = new GroupViewHolder();

            convertView = inflater.inflate(R.layout.item_send_list_group, parent, false);

            groupViewHolder.tvDepositInfoTitle = (TextView)convertView.findViewById(R.id.tvDepositInfoTitle);

            groupViewHolder.tvDepositInfoSub = (TextView)convertView.findViewById(R.id.tvDepositInfoSub);

            convertView.setTag(groupViewHolder);
        }
        else
        {
            groupViewHolder = (GroupViewHolder)convertView.getTag();
        }

        BankingInfomation groupItem = bankingList.get(groupPosition);

        if(groupViewHolder.tvDepositInfoTitle == null)
        {
            LogTrace.d("3");
        }

        groupViewHolder.tvDepositInfoTitle.setText(groupItem.getDepositInfo().depositName);

        groupViewHolder.tvDepositInfoSub.setText(groupItem.getDepositInfo().depositNum);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return bankingList.get(groupPosition).getBankingTargetCount();
    }

    @Override
    public String getChild(int groupPosition, int childPosition)
    {
        BankingInfomation group = bankingList.get(groupPosition);
        return group.getTargetKeyIndexOf(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        ChildViewHolder childViewHolder = null;

        if(convertView == null)
        {
            childViewHolder = new ChildViewHolder();

            convertView = inflater.inflate(R.layout.item_send_list_sub, parent, false);

            childViewHolder.tvSendItemTargetName = (TextView)convertView.findViewById(R.id.tvSendItemTargetName);

            childViewHolder.tvSendItemMoney = (TextView)convertView.findViewById(R.id.tvSendItemMoney);

            convertView.setTag(childViewHolder);
        }
        else
        {
            childViewHolder = (ChildViewHolder)convertView.getTag();
        }

        BankingInfomation groupItem = bankingList.get(groupPosition);

        String targetName = groupItem.getTargetKeyIndexOf(childPosition);

        childViewHolder.tvSendItemTargetName.setText(targetName);

        childViewHolder.tvSendItemMoney.setText(groupItem.getTotalSendMoneyInTargetString(targetName));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

    private class GroupViewHolder
    {
        public TextView tvDepositInfoTitle = null;
        public TextView tvDepositInfoSub = null;
    }

    private class ChildViewHolder
    {

        public TextView tvSendItemTargetName = null;
        public TextView tvSendItemMoney = null;
    }
}
