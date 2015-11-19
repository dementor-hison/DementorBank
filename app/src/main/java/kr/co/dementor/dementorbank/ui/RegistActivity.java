package kr.co.dementor.dementorbank.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.Defines;
import kr.co.dementor.dementorbank.common.LogTrace;

/**
 * Created by dementor1 on 15. 11. 17..
 */
public class RegistActivity extends FragmentActivity
{
    private Context        mContext        = this;
    private int[]          mKeys           = new int[4];
    private TopView        mTopview        = null;
    private CustomGridView mCustomGridView = null;
    private ImageView      m_ivStep        = null;
    private ImageView      m_ivStepAnim    = null;
    private Animation      mAniFadeOut     = null;
    private Animation      mAniFadeIn      = null;
    Animation.AnimationListener mAnimationFadeInListener = new Animation.AnimationListener()
    {
        @Override
        public void onAnimationStart(Animation animation)
        {
        }

        @Override
        public void onAnimationEnd(Animation animation)
        {
            Defines.RegistState currentState = Defines.RegistState.values()[m_ivStep.getDrawable().getLevel()];

            switch (currentState)
            {
                case SELECTED_NONE:
                    m_ivStep.setImageLevel(currentState.nextState().getValue());
                    break;

                case SELECTED_LOCK:
                    m_ivStep.setImageLevel(currentState.nextState().getValue());
                    break;

                case SELECTED_KEY1:
                    m_ivStep.setImageLevel(currentState.nextState().getValue());
                    break;

                case SELECTED_KEY2:
                    m_ivStep.setImageLevel(currentState.nextState().getValue());
                    break;

                case SELECTED_KEY3:
                default:
                    LogTrace.i("What??0_o");
                    break;
            }

            m_ivStepAnim.startAnimation(mAniFadeOut);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };

    Animation.AnimationListener mAnimationFadeOutListener = new Animation.AnimationListener()
    {
        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation)
        {
            m_ivStepAnim.setVisibility(ImageView.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };

    private ImageButton m_ibCategoryPrivate = null;
    private ImageButton m_ibCategoryWord    = null;
    CustomGridView.OnItemClickListener mOnItemClickListener = new CustomGridView.OnItemClickListener()
    {
        @Override
        public void OnItemClick(View view, int position, int imageSrcId)
        {
            LogTrace.d("position : " + position + " , resId : " + imageSrcId);

            Defines.RegistState currentState = Defines.RegistState.values()[m_ivStep.getDrawable().getLevel()];

            if (isValid(imageSrcId) == false)
            {
                Toast.makeText(getApplicationContext(), R.string.toast_msg_regist_key_wrong, Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentState.getValue() >= mKeys.length)
            {
                LogTrace.i("Not invalid...");
                return;
            }

            switch (currentState)
            {
                case SELECTED_NONE:
                    mKeys[Defines.ImagePosition.LOCK] = imageSrcId;
                    break;

                case SELECTED_LOCK:
                    mKeys[Defines.ImagePosition.KEY1] = imageSrcId;
                    break;

                case SELECTED_KEY1:
                    mKeys[Defines.ImagePosition.KEY2] = imageSrcId;
                    break;

                case SELECTED_KEY2:
                    mKeys[Defines.ImagePosition.KEY3] = imageSrcId;

                    resetRegist();

                    startConfirmActivity();
                    break;

                case SELECTED_KEY3:
                default:
                    LogTrace.i("What??0_o");
                    break;
            }
            m_ivStepAnim.setImageLevel(currentState.getValue());

            m_ivStepAnim.setVisibility(ImageView.VISIBLE);

            m_ivStepAnim.startAnimation(mAniFadeIn);
        }
    };
    TopView.OnTopViewListener          mOnTopViewListener   = new TopView.OnTopViewListener()
    {
        @Override
        public void OnBack()
        {
            finish();
        }

        @Override
        public void OnRefresh()
        {
            resetRegist();
        }

        @Override
        public void OnHelp()
        {
            Toast.makeText(getApplicationContext(), "아직 구현 안됨", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener               mOnClickListener     = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.ibPrevCategory:
                case R.id.ibCategoryWord:
                    m_ibCategoryWord.setSelected(true);
                    m_ibCategoryPrivate.setSelected(false);
                    break;

                case R.id.ibNextCategory:
                case R.id.ibCategoryPrivate:
                    m_ibCategoryWord.setSelected(false);
                    m_ibCategoryPrivate.setSelected(true);
                    break;

                default:
                    LogTrace.i("What??? 0_o");
                    break;
            }
        }
    };
    private ImageButton m_ibPrevCategory = null;
    private ImageButton m_ibNextCategory = null;

    private void startConfirmActivity()
    {
        //임시로 바로 저장 중...아직 confirm 디자인 없음.
        SharedPreferences        pref   = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(getString(R.string.preference_key_is_regist), true);
        editor.commit();
    }

    private boolean isValid(int resID)
    {
        boolean result = true;

        if (m_ivStep.getDrawable().getLevel() == Defines.RegistState.SELECTED_NONE.getValue())
        {
            LogTrace.d("");
            result = true;
        }
        else
        {
            LogTrace.d("resID : " + resID + " lock : " + mKeys[Defines.ImagePosition.LOCK]);
            result = mKeys[Defines.ImagePosition.LOCK] == resID ? false : true;
        }

        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.regist_activity);

        initView();

        resetRegist();

        ArrayList<Integer> listItems = loadItemsForGridView();

        mCustomGridView.setGridViewItems(listItems);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        resetRegist();
    }

    private ArrayList<Integer> loadItemsForGridView()
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        list.addAll(Defines.RES_ID_NUM);
        list.addAll(Defines.RES_ID_ENG);
        list.addAll(Defines.RES_ID_HAN);

        return list;
    }

    private void initView()
    {
        m_ivStep = (ImageView) findViewById(R.id.ivStep);

        m_ivStepAnim = (ImageView) findViewById(R.id.ivStepAnim);

        m_ibCategoryPrivate = (ImageButton) findViewById(R.id.ibCategoryPrivate);

        m_ibCategoryWord = (ImageButton) findViewById(R.id.ibCategoryWord);

        m_ibPrevCategory = (ImageButton) findViewById(R.id.ibPrevCategory);

        m_ibNextCategory = (ImageButton) findViewById(R.id.ibNextCategory);

        m_ibCategoryPrivate.setOnClickListener(mOnClickListener);
        m_ibCategoryWord.setOnClickListener(mOnClickListener);
        m_ibPrevCategory.setOnClickListener(mOnClickListener);
        m_ibNextCategory.setOnClickListener(mOnClickListener);

        mTopview = (TopView) findViewById(R.id.topView);

        mTopview.setOnTopViewListener(mOnTopViewListener);

        mCustomGridView = (CustomGridView) findViewById(R.id.gvCustomGridView);

        mCustomGridView.setOnItemClickListener(mOnItemClickListener);

        mCustomGridView.setDragable(false);

        mAniFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        mAniFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        mAniFadeIn.setAnimationListener(mAnimationFadeInListener);
        mAniFadeOut.setAnimationListener(mAnimationFadeOutListener);
    }


    private void resetRegist()
    {
        m_ivStep.setImageLevel(Defines.RegistState.SELECTED_NONE.getValue());
        m_ibCategoryWord.setSelected(true);
        m_ibCategoryPrivate.setSelected(false);
        mKeys[Defines.ImagePosition.LOCK] = 0;
        mKeys[Defines.ImagePosition.KEY1] = 0;
        mKeys[Defines.ImagePosition.KEY2] = 0;
        mKeys[Defines.ImagePosition.KEY3] = 0;
    }
}
