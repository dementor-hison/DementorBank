package kr.co.dementor.dementorbank.kr.co.dementor.dementorbank.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.LogTrace;

/**
 * Created by uos94 on 2015-11-25.
 */
public class DepositFragment extends Fragment
{
    private LinearLayout m_llDepositItem = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup item = (ViewGroup) inflater.inflate(R.layout.item_deposit_grid, null);

        Bundle args = getArguments();

        ((TextView)item.findViewById(R.id.tvDepositGridItemTitle)).setText(args.getString("title"));
        ((TextView)item.findViewById(R.id.tvDepositGridItemSub1)).setText(args.getString("sub1"));
        ((TextView)item.findViewById(R.id.tvDepositGridItemSub2)).setText(args.getString("sub2"));

        m_llDepositItem = (LinearLayout)item.findViewById(R.id.llDepositItem);
        return item;
    }

    public void setSelected(boolean isSelected)
    {
        m_llDepositItem.setSelected(isSelected);
    }

}
