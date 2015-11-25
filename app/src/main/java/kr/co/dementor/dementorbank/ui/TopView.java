package kr.co.dementor.dementorbank.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.LogTrace;

/**
 * Created by dementor1 on 15. 11. 17..
 */
public class TopView extends LinearLayout
{
    private OnTopViewListener mOnTopViewListener = null;
    private String mText = null;

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

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.TopViewAttr);

        setTitle(array.getString(R.styleable.TopViewAttr_titleText));
    }

    public TopView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        createView(context);

        initView();

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.TopViewAttr);

        setTitle(array.getString(R.styleable.TopViewAttr_titleText));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TopView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

        createView(context);

        initView();

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.TopViewAttr);

        setTitle(array.getString(R.styleable.TopViewAttr_titleText));
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
        findViewById(R.id.ibConfirm).setOnClickListener(mOnClickListener);
    }

    OnClickListener mOnClickListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            LogTrace.d("Click!!!");
            if (mOnTopViewListener == null)
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

                case R.id.ibConfirm:
                    mOnTopViewListener.OnConfirm();
                    break;

                default:
                    LogTrace.w("What??? 0_oa");
                    break;
            }
        }
    };

    private void setTitle(String title)
    {
        ((TextView) findViewById(R.id.tvTopViewTitle)).setText(title);
    }

    public void setOnTopViewListener(OnTopViewListener listener)
    {
        mOnTopViewListener = listener;
    }

    public void setRefreshButtonVisible(boolean isVisible)
    {
        findViewById(R.id.ibRefresh).setVisibility(isVisible == true ? ImageButton.VISIBLE : ImageButton.INVISIBLE);
    }

    public void setConfirmButtonVisible(boolean isVisible)
    {
        findViewById(R.id.ibConfirm).setVisibility(isVisible == true ? ImageButton.VISIBLE : ImageButton.GONE);
    }
    public void setHelpButtonVisible(boolean isVisible)
    {
        findViewById(R.id.ibHelp).setVisibility(isVisible == true ? ImageButton.VISIBLE : ImageButton.GONE);
    }

    public interface OnTopViewListener
    {
        void OnBack();

        void OnRefresh();

        void OnHelp();

        void OnConfirm();
    }
}
