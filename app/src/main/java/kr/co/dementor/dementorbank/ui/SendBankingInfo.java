package kr.co.dementor.dementorbank.ui;

/**
 * Created by dementor1 on 15. 11. 24..
 */
public class SendBankingInfo
{
    public String sendTargetName = null;

    public String format = "%,d";

    private int sendMoneyValue = 0;

    public void setSendMoneyValue(int money)
    {
        sendMoneyValue = money;
    }

    public String getSendMoneyValue()
    {
        return String.format(format, sendMoneyValue);
    }

}
