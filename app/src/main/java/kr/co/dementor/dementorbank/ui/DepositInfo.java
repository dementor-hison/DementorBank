package kr.co.dementor.dementorbank.ui;

/**
 * Created by dementor1 on 15. 11. 24..
 */

public class DepositInfo
{
    public DepositInfo(){}

    private String moneyformat = "%,d";

    public String depositName = null;

    public String depositNum = null;

    private int totalSavedMoney = 0;

    public String getTotalSavedMoneyString()
    {
        return String.format(moneyformat, totalSavedMoney);
    }

    public void setTotalSavedMoney(int totalSavedMoney)
    {
        this.totalSavedMoney = totalSavedMoney;
    }

    public int getTotalSavedMoney()
    {
        return totalSavedMoney;
    }
}