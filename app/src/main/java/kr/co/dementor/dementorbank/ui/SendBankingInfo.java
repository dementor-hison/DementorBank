package kr.co.dementor.dementorbank.ui;

/**
 * Created by dementor1 on 15. 11. 24..
 */
public class SendBankingInfo
{
    public String sendTargetName = null;

    public String format = "%,d";

    private int sendMoneyValue = 0;

    public int totalMoneyValue = 0;

    public void setSendMoneyValue(int money)
    {
        sendMoneyValue = money;
    }

    public String getSendTotalMoneyValue()
    {
        return String.format(format, totalMoneyValue);
    }

    public int getIntegerMoneyValue()
    {
        return sendMoneyValue;
    }

}
