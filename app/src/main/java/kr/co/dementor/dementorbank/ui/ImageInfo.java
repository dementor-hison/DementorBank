package kr.co.dementor.dementorbank.ui;

/**
 * Created by dementor1 on 15. 11. 23..
 */
public class ImageInfo
{
    public int getResId()
    {
        return mResId;
    }

    public void setResId(int resId)
    {
        this.mResId = resId;
    }

    public String getResName()
    {
        return mResName;
    }

    public void setResName(String resName)
    {
        this.mResName = resName;
    }

    public boolean isChecked()
    {
        return mChecked;
    }

    public void setChecked(boolean isCheck)
    {
        this.mChecked = isCheck;
    }

    private int     mResId   = 0;
    private String  mResName = null;
    private boolean mChecked = false;

}
