package kr.co.dementor.dementorbank.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.Defines;
import kr.co.dementor.dementorbank.common.LogTrace;

/**
 * Created by dementor1 on 15. 11. 20..
 */
public class ActionPopup extends LinearLayout
{
    private ImageView[] m_arrIvActionPopupKeys  = new ImageView[Defines.MAX_KEY_CAPACITY];
    private ImageView[] m_arrIvActionPopupArrow = new ImageView[Defines.MAX_KEY_CAPACITY - 1];
    private Animation m_aniFadeIn = null;
    private Animation m_aniFadeOut = null;

    public ActionPopup(Context context)
    {
        super(context);

        createView(context);

        initView();
    }

    public ActionPopup(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        createView(context);

        initView();
    }

    public ActionPopup(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        createView(context);

        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ActionPopup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

        createView(context);

        initView();
    }

    private View createView(Context context)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.action_popup, this, true);
    }

    private void initView()
    {
        m_arrIvActionPopupKeys[Defines.ImagePosition.LOCK] = (ImageView) findViewById(R.id.ivActionPopupLock);
        m_arrIvActionPopupKeys[Defines.ImagePosition.KEY1] = (ImageView) findViewById(R.id.ivActionPopupKey1);
        m_arrIvActionPopupKeys[Defines.ImagePosition.KEY2] = (ImageView) findViewById(R.id.ivActionPopupKey2);
        m_arrIvActionPopupKeys[Defines.ImagePosition.KEY3] = (ImageView) findViewById(R.id.ivActionPopupKey3);

        m_arrIvActionPopupKeys[Defines.ImagePosition.LOCK].setTag(Defines.RES_ID_DEFAULT_HINT_IMAGE.get(Defines.ImagePosition.LOCK));
        m_arrIvActionPopupKeys[Defines.ImagePosition.KEY1].setTag(Defines.RES_ID_DEFAULT_HINT_IMAGE.get(Defines.ImagePosition.KEY1));
        m_arrIvActionPopupKeys[Defines.ImagePosition.KEY2].setTag(Defines.RES_ID_DEFAULT_HINT_IMAGE.get(Defines.ImagePosition.KEY2));
        m_arrIvActionPopupKeys[Defines.ImagePosition.KEY3].setTag(Defines.RES_ID_DEFAULT_HINT_IMAGE.get(Defines.ImagePosition.KEY3));

        m_arrIvActionPopupArrow[Defines.ArrowImagePosition.ARROW1] = (ImageView) findViewById(R.id.ivActionPopupArrow1);
        m_arrIvActionPopupArrow[Defines.ArrowImagePosition.ARROW2] = (ImageView) findViewById(R.id.ivActionPopupArrow2);
        m_arrIvActionPopupArrow[Defines.ArrowImagePosition.ARROW3] = (ImageView) findViewById(R.id.ivActionPopupArrow3);

        for (int i = 0; i < m_arrIvActionPopupKeys.length ; i++)
        {
            m_arrIvActionPopupKeys[i].setImageResource(Defines.RES_ID_DEFAULT_HINT_IMAGE.get(i));
            m_arrIvActionPopupKeys[i].setBackgroundResource(android.R.color.transparent);
        }

        m_arrIvActionPopupArrow[Defines.ArrowImagePosition.ARROW1].setSelected(false);
        m_arrIvActionPopupArrow[Defines.ArrowImagePosition.ARROW2].setSelected(false);
        m_arrIvActionPopupArrow[Defines.ArrowImagePosition.ARROW3].setSelected(false);

        m_aniFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        m_aniFadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
    }

    public void setHintImage(int position, int resId)
    {
        if(position >= m_arrIvActionPopupKeys.length)
        {
            LogTrace.e("out of range...Image MaxLength : " + m_arrIvActionPopupKeys.length + " , try index : " + position);
            return;
        }

        m_arrIvActionPopupKeys[position].setImageResource(resId);
        m_arrIvActionPopupKeys[position].setTag(resId);

        for (int i = 0; i < m_arrIvActionPopupArrow.length; i++)
        {
            m_arrIvActionPopupArrow[i].setSelected(false);

            if(i == position)
            {
                m_arrIvActionPopupArrow[position].setSelected(true);
            }
        }

        for (int index = 0 ; index < m_arrIvActionPopupKeys.length; index++)
        {
            int currentImgId = (int)m_arrIvActionPopupKeys[index].getTag();

            if(currentImgId != Defines.RES_ID_DEFAULT_HINT_IMAGE.get(index))
            {
                m_arrIvActionPopupKeys[index].setBackgroundResource(R.drawable.grid_item_background);
            }
            else
            {
                m_arrIvActionPopupKeys[index].setBackgroundResource(android.R.color.transparent);
            }
        }
    }

    public void clearHintImage()
    {
        initView();
    }

    public void setVisibilityWithAnimation(final int visibility)
    {
        clearAnimation();

        if(visibility == VISIBLE)
        {
            setVisibility(visibility);

            Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_show);

            startAnimation(ani);
        }
        else
        {
            Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_hide);

            ani.setAnimationListener(new Animation.AnimationListener()
            {
                @Override
                public void onAnimationStart(Animation animation)
                {

                }

                @Override
                public void onAnimationEnd(Animation animation)
                {
                    setVisibility(visibility);
                }

                @Override
                public void onAnimationRepeat(Animation animation)
                {

                }
            });

            startAnimation(ani);
        }
    }
}
