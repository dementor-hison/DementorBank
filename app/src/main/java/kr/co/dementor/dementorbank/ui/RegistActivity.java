package kr.co.dementor.dementorbank.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
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
    private Context            mContext       = this;
    private ArrayList<Integer> mKeys          = new ArrayList<>(Defines.MAX_KEY_CAPACITY);
    private TopView            mRegistTopview = null;
    private ActionPopup        m_actionPopup  = null;

    private CustomGridView mRegistGridView      = null;
    private ImageView      m_ivRegistStatus     = null;
    private ImageView      m_ivRegistStatusAnim = null;
    private Animation      mAniFadeInOut        = null;
    CustomGridView.OnItemClickListener mOnItemClickListener = new CustomGridView.OnItemClickListener()
    {
        @Override
        public void OnItemClick(View view, int position, int imageSrcId)
        {
            LogTrace.d("position : " + position + " , resId : " + imageSrcId);

            Defines.RegistStatus currentState = Defines.RegistStatus.values()[m_ivRegistStatus.getDrawable().getLevel()];

            if (isValid(imageSrcId) == false)
            {
                Toast.makeText(getApplicationContext(), R.string.toast_msg_regist_key_wrong, Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentState.getValue() >= Defines.MAX_KEY_CAPACITY)
            {
                LogTrace.i("Not invalid...currentState.getValue() : " + currentState.getValue() + "mKeys.size() : " + mKeys.size());
                return;
            }

            switch (currentState)
            {
                case SELECTED_NONE:
                    mKeys.add(Defines.ImagePosition.LOCK, imageSrcId);
                    m_actionPopup.setHintImage(Defines.ImagePosition.LOCK, imageSrcId);
                    break;

                case SELECTED_LOCK:
                    mKeys.add(Defines.ImagePosition.KEY1, imageSrcId);
                    m_actionPopup.setHintImage(Defines.ImagePosition.KEY1, imageSrcId);
                    break;

                case SELECTED_KEY1:
                    mKeys.add(Defines.ImagePosition.KEY2, imageSrcId);
                    m_actionPopup.setHintImage(Defines.ImagePosition.KEY2, imageSrcId);
                    break;

                case SELECTED_KEY2:
                    mKeys.add(Defines.ImagePosition.KEY3, imageSrcId);
                    m_actionPopup.setHintImage(Defines.ImagePosition.KEY3, imageSrcId);

                    startConfirmActivity();

                    resetRegist();
                    break;

                case SELECTED_KEY3:
                default:
                    LogTrace.i("What??0_o");
                    break;
            }

            Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(200);

            m_ivRegistStatus.setImageLevel(currentState.nextState().getValue());
            m_ivRegistStatusAnim.setImageLevel(currentState.nextState().getValue());
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

        @Override
        public void OnConfirm() { }
    };
    private ImageButton m_ibCategoryPrivate = null;
    private ImageButton m_ibCategoryWord    = null;
    View.OnClickListener mOnClickListener = new View.OnClickListener()
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
                case R.id.ivStatus:
                case R.id.ivStatusAnim:
                    if (m_actionPopup.getVisibility() == View.VISIBLE)
                    {
                        m_actionPopup.setVisibilityWithAnimation(View.GONE);
                    }
                    else
                    {
                        m_actionPopup.setVisibilityWithAnimation(View.VISIBLE);
                    }
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
        editor.putInt(getString(R.string.preference_key_lock_res_id), mKeys.get(Defines.ImagePosition.LOCK));
        editor.putInt(getString(R.string.preference_key_key1_res_id), mKeys.get(Defines.ImagePosition.KEY1));
        editor.putInt(getString(R.string.preference_key_key2_res_id), mKeys.get(Defines.ImagePosition.KEY2));
        editor.putInt(getString(R.string.preference_key_key3_res_id), mKeys.get(Defines.ImagePosition.KEY3));
        editor.commit();

        Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
        startActivity(intent);

        finish();
    }

    private boolean isValid(int resID)
    {
        boolean result = true;

        if (m_ivRegistStatus.getDrawable().getLevel() == Defines.RegistStatus.SELECTED_NONE.getValue())
        {
            LogTrace.d("");
            result = true;
        }
        else
        {
            LogTrace.d("resID : " + resID + " lock : " + mKeys.get(Defines.ImagePosition.LOCK));
            result = !mKeys.contains(resID);
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

        mRegistGridView.setGridViewItems(listItems);
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
        m_ivRegistStatus = (ImageView) findViewById(R.id.ivStatus);

        m_ivRegistStatusAnim = (ImageView) findViewById(R.id.ivStatusAnim);

        m_ibCategoryPrivate = (ImageButton) findViewById(R.id.ibCategoryPrivate);

        m_ibCategoryWord = (ImageButton) findViewById(R.id.ibCategoryWord);

        m_ibPrevCategory = (ImageButton) findViewById(R.id.ibPrevCategory);

        m_ibNextCategory = (ImageButton) findViewById(R.id.ibNextCategory);

        mRegistTopview = (TopView) findViewById(R.id.topView);

        m_actionPopup = (ActionPopup) findViewById(R.id.registActionPopup);

        mRegistGridView = (CustomGridView) findViewById(R.id.gvCustomGridView);

        m_ibCategoryPrivate.setOnClickListener(mOnClickListener);

        m_ibCategoryWord.setOnClickListener(mOnClickListener);

        m_ibPrevCategory.setOnClickListener(mOnClickListener);

        m_ibNextCategory.setOnClickListener(mOnClickListener);

        m_ivRegistStatus.setOnClickListener(mOnClickListener);

        m_ivRegistStatusAnim.setOnClickListener(mOnClickListener);

        mRegistTopview.setOnTopViewListener(mOnTopViewListener);

        mRegistGridView.setOnItemClickListener(mOnItemClickListener);

        mRegistGridView.setDragable(false);

        mRegistTopview.setRefreshButtonVisible(true);

        mRegistTopview.setConfirmButtonVisible(false);

        mAniFadeInOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_inout);

        m_ibCategoryWord.setSelected(true);

        m_ibCategoryPrivate.setSelected(false);

    }

    private void resetRegist()
    {
        m_ivRegistStatus.setImageLevel(Defines.RegistStatus.SELECTED_NONE.getValue());

        m_ivRegistStatusAnim.setImageLevel(Defines.RegistStatus.SELECTED_NONE.getValue());

        m_ivRegistStatusAnim.startAnimation(mAniFadeInOut);

        mKeys.clear();

        m_actionPopup.clearHintImage();
    }
}
