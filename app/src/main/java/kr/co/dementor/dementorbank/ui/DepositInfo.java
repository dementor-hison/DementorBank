package kr.co.dementor.dementorbank.ui;

import java.text.NumberFormat;

/**
 * Created by dementor1 on 15. 11. 24..
 */
public class DepositInfo
{
    public String depositName = null;

    public String depositNum = null;

    private int totalMoney = 0;

    public String getTotalMoney()
    {
        NumberFormat format = NumberFormat.getCurrencyInstance();

        return format.format((long)totalMoney);
    }

    public void setTotalMoney(int totalMoney)
    {
        this.totalMoney = totalMoney;
    }
}
