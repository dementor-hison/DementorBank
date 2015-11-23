package kr.co.dementor.dementorbank.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.LogTrace;

/**
 * Created by dementor1 on 15. 11. 17..
 */
public class TopView extends LinearLayout
{
    private OnTopViewListener mOnTopViewListener = null;

    public TopView(Context context)
    {
        super(context);

        createView(context);

        initView();
    }

    public TopView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        createView(context);

        initView();
    }

    public TopView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        createView(context);

        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TopView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private View createView(Context context)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.top_view, this, true);
    }

    private void initView()
    {
        findViewById(R.id.ibBack).setOnClickListener(mOnClickListener);
        findViewById(R.id.ibRefresh).setOnClickListener(mOnClickListener);
        findViewById(R.id.ibHelp).setOnClickListener(mOnClickListener);
    }

    OnClickListener mOnClickListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(mOnTopViewListener == null)
            {
                LogTrace.i("TopViewListener not set");
                return;
            }

            switch (v.getId())
            {
                case R.id.ibBack:
                    mOnTopViewListener.OnBack();
                    break;

                case R.id.ibRefresh:
                    mOnTopViewListener.OnRefresh();
                    break;

                case R.id.ibHelp:
                    mOnTopViewListener.OnHelp();
                    break;

                default:
                    LogTrace.w("What??? 0_oa");
                    break;
            }
        }
    };

    public void setOnTopViewListener(OnTopViewListener listener)
    {
        mOnTopViewListener = listener;
    }

    public void setRefreshButtonVisivle(boolean isVisible)
    {
        findViewById(R.id.ibRefresh).setVisibility(isVisible == true ? ImageButton.VISIBLE : ImageButton.INVISIBLE);
    }

    public interface OnTopViewListener
    {
        void OnBack();
        void OnRefresh();
        void OnHelp();
    }
}
