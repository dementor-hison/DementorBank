package kr.co.dementor.dementorbank.ui;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.dementor.dementorbank.common.LogTrace;

/**
 * Created by dementor1 on 15. 11. 27..
 */

public class BankingInfomation
{
    public static final int ERROR_TARGET_NAME = -1;

    private DepositInfo depositInfo = null;

    private HashMap<String, Integer> mapBankingList = new HashMap<>();

    private String moneyformat = "%,d";

    public BankingInfomation(DepositInfo depositInfo)
    {
        this.depositInfo = depositInfo;
    }

    public DepositInfo getDepositInfo()
    {
        return depositInfo;
    }

    public void setDepositInfo(DepositInfo depositInfo)
    {
        this.depositInfo = depositInfo;
    }

    public void addBanking(BankingUnit unit)
    {
        if(mapBankingList.containsKey(unit.targetName))
        {
            int intentedSendMoney = mapBankingList.get(unit.targetName);

            mapBankingList.remove(unit.targetName);

            mapBankingList.put(unit.targetName, intentedSendMoney+unit.sendMoney);
        }
        else
        {
            mapBankingList.put(unit.targetName, unit.sendMoney);
        }
    }

    public int getTotalSendMoneyInTarget(String targetName)
    {
        if(mapBankingList.containsKey(targetName))
        {
            return mapBankingList.get(targetName);
        }

        return ERROR_TARGET_NAME;
    }

    public String getTotalSendMoneyInTargetString(String targetName)
    {
        if(mapBankingList.containsKey(targetName))
        {
            return String.format(moneyformat, mapBankingList.get(targetName));
        }

        return null;
    }

    public int getBankingTargetCount()
    {
        return mapBankingList.size();
    }

    public String getTargetKeyIndexOf(int position)
    {
        ArrayList<String> arrayString = new ArrayList<String>(mapBankingList.keySet());

        if(position >= arrayString.size())
        {
            LogTrace.e("out of index ...position : " + position + " , maxIndex : " + (arrayString.size()-1) );
            return null;
        }
        else
        {
            return arrayString.get(position);
        }
    }

}



