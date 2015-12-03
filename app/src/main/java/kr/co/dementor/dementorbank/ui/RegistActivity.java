package kr.co.dementor.dementorbank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.adapter.HelpAdapter;
import kr.co.dementor.dementorbank.common.Defines;
import kr.co.dementor.dementorbank.common.DementorUtil;
import kr.co.dementor.dementorbank.common.LogTrace;

/**
 * Created by dementor1 on 15. 11. 17..
 */
public class RegistActivity extends FragmentActivity
{
    private Context            mContext = this;
    private ArrayList<Integer> mKeys    = new ArrayList<>(Defines.MAX_KEY_CAPACITY);

    private TopView     mRegistTopview = null;
    private ActionPopup m_actionPopup  = null;

    private CustomGridView mRegistGridView      = null;
    private ImageView      m_ivRegistStatus     = null;
    private ImageView      m_ivRegistStatusAnim = null;
    private Animation      mAniFadeInOut        = null;
    CustomGridView.OnItemClickListener mOnItemClickListener = new CustomGridView.OnItemClickListener()
    {
        @Override
        public void OnItemClick(View view, int position, int imageSrcId)
        {
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
            showHelp();
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

                    setNormalList();

                    break;

                case R.id.ibNextCategory:
                case R.id.ibCategoryPrivate:

                    setPrivateList();

                    break;
                case R.id.ivStatus:
                case R.id.ivStatusAnim:
                    handler.post(runShowActionPopup);
                case R.id.ibRegisterNeverSee:

                    DementorUtil.savePreferance(getApplicationContext(), getString(R.string.preference_key_never_see_regist), true);

                    hideHelp();

                    break;

                case R.id.ibRegisterGuideClose:

                    hideHelp();

                default:
                    LogTrace.i("What??? 0_o");
                    break;
            }
        }
    };

    private Handler  handler            = new Handler();
    private Runnable runShowActionPopup = new Runnable()
    {
        @Override
        public void run()
        {
            handler.removeCallbacks(runHideActionPopup);

            m_actionPopup.setVisibilityWithAnimation(View.VISIBLE);

            handler.postDelayed(runHideActionPopup, 1000);
        }
    };

    private Runnable runHideActionPopup = new Runnable()
    {
        @Override
        public void run()
        {
            m_actionPopup.setVisibilityWithAnimation(View.GONE);
        }
    };
    private ViewPager m_vpRegHelpImage = null;
    private ImageView[] m_ivHelpDot = new ImageView[3];

    private void hideHelp()
    {
        m_flGuide.setVisibility(FrameLayout.GONE);
    }

    private void showHelp()
    {
        m_flGuide.setVisibility(FrameLayout.VISIBLE);

        m_vpRegHelpImage.setCurrentItem(0);

        m_ivHelpDot[0].setSelected(true);
        m_ivHelpDot[1].setSelected(false);
        m_ivHelpDot[2].setSelected(false);
    }

    private ImageButton        m_ibPrevCategory          = null;
    private ImageButton        m_ibNextCategory          = null;
    private ArrayList<Integer> mSelectedPrivateItems     = new ArrayList<Integer>();
    private FrameLayout        m_flGuide                 = null;
    private ImageButton        m_ibRegisterHelp1NeverSee = null;
    private ImageButton        m_ibRegisterGuideClose    = null;

    private void startConfirmActivity()
    {
        //임시로 바로 저장 중...아직 confirm 디자인 없음.
        DementorUtil.savePreferance(getApplicationContext(), getString(R.string.preference_key_is_regist), true);

        String strLockName = getResources().getResourceName(mKeys.get(Defines.ImagePosition.LOCK));
        String strKey1Name = getResources().getResourceName(mKeys.get(Defines.ImagePosition.KEY1));
        String strKey2Name = getResources().getResourceName(mKeys.get(Defines.ImagePosition.KEY2));
        String strKey3Name = getResources().getResourceName(mKeys.get(Defines.ImagePosition.KEY3));

        DementorUtil.savePreferance(getApplicationContext(), getString(R.string.preference_key_lock_res_id), strLockName);
        DementorUtil.savePreferance(getApplicationContext(), getString(R.string.preference_key_key1_res_id), strKey1Name);
        DementorUtil.savePreferance(getApplicationContext(), getString(R.string.preference_key_key2_res_id), strKey2Name);
        DementorUtil.savePreferance(getApplicationContext(), getString(R.string.preference_key_key3_res_id), strKey3Name);

        ArrayList<Integer> list = mRegistGridView.getGridViewItems();

        for (int i = 0; i < mKeys.size(); i++)
        {
            if (list.contains(mKeys.get(i)))
            {
                list.remove(mKeys.get(i));
            }
        }

        Collections.shuffle(list);

        Set<String> set = new HashSet<String>();

        int i = 0;
        while (set.size() < Defines.MAX_DUMMY_ICON_CAPACITY)
        {
            set.add(getResources().getResourceEntryName(list.get(i)));
            i++;
        }

        DementorUtil.savePreferance(getApplicationContext(), getString(R.string.preference_key_dummy_set), set);

        Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
        startActivity(intent);

        finish();
    }

    private boolean isValid(int resID)
    {
        boolean result = true;

        if (m_ivRegistStatus.getDrawable().getLevel() == Defines.RegistStatus.SELECTED_NONE.getValue())
        {
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

        setNormalList();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        resetRegist();
    }

    private ArrayList<Integer> loadItemsForGridView(boolean isPrivate)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        if (isPrivate)
        {
            list.addAll(Defines.RES_ID_PRIVATE);
        }
        else
        {
            list.addAll(Defines.RES_ID_NUM);
            list.addAll(Defines.RES_ID_ENG);
            list.addAll(Defines.RES_ID_HAN);
        }

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

        m_flGuide = (FrameLayout) findViewById(R.id.flRegisterGuide);

        m_ibRegisterHelp1NeverSee = (ImageButton)findViewById(R.id.ibRegisterNeverSee);

        m_ibRegisterGuideClose = (ImageButton)findViewById(R.id.ibRegisterGuideClose);

        m_ibCategoryPrivate.setOnClickListener(mOnClickListener);

        m_ibCategoryWord.setOnClickListener(mOnClickListener);

        m_ibPrevCategory.setOnClickListener(mOnClickListener);

        m_ibNextCategory.setOnClickListener(mOnClickListener);

        m_ivRegistStatus.setOnClickListener(mOnClickListener);

        m_ivRegistStatusAnim.setOnClickListener(mOnClickListener);

        m_ibRegisterHelp1NeverSee.setOnClickListener(mOnClickListener);

        m_ibRegisterGuideClose.setOnClickListener(mOnClickListener);

        mRegistTopview.setOnTopViewListener(mOnTopViewListener);

        mRegistGridView.setOnItemClickListener(mOnItemClickListener);

        mRegistGridView.setDragable(false);

        mRegistTopview.setRefreshButtonVisible(true);

        mRegistTopview.setConfirmButtonVisible(false);

        mRegistTopview.setHelpButtonVisible(true);

        mAniFadeInOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_inout);

        boolean isNeverSee = (boolean)DementorUtil.loadPreferance(getApplicationContext(), getString(R.string.preference_key_never_see_regist), false);

        m_flGuide.setVisibility(isNeverSee == true ? FrameLayout.GONE : FrameLayout.VISIBLE);

        m_vpRegHelpImage = (ViewPager)findViewById(R.id.vpRegHelpImage);

        HelpAdapter adapter = new HelpAdapter(getApplicationContext(), Defines.RES_ID_REGISTER_HELP);

        m_vpRegHelpImage.setAdapter(adapter);

        m_ivHelpDot[0] = (ImageView)findViewById(R.id.ivRegisterHelpDot1);
        m_ivHelpDot[1] = (ImageView)findViewById(R.id.ivRegisterHelpDot2);
        m_ivHelpDot[2] = (ImageView)findViewById(R.id.ivRegisterHelpDot3);

        m_vpRegHelpImage.addOnPageChangeListener(mOnPageChangeListener);

        m_vpRegHelpImage.setCurrentItem(0);

    }

    private void resetRegist()
    {
        m_ivRegistStatus.setImageLevel(Defines.RegistStatus.SELECTED_NONE.getValue());

        m_ivRegistStatusAnim.setImageLevel(Defines.RegistStatus.SELECTED_NONE.getValue());

        m_ivRegistStatusAnim.startAnimation(mAniFadeInOut);

        mKeys.clear();

        m_actionPopup.clearHintImage();
    }

    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            LogTrace.d("position : " + position);
        }

        @Override
        public void onPageSelected(int position) {
            LogTrace.d("position : " + position);
            for (int i = 0 ; i < m_ivHelpDot.length ; i++)
            {
                m_ivHelpDot[i].setSelected(i == position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setPrivateList()
    {
        resetRegist();

        mRegistGridView.setGridViewItems(loadItemsForGridView(true));

        m_ibCategoryWord.setSelected(false);

        m_ibCategoryPrivate.setSelected(true);
    }

    private void setNormalList()
    {
        resetRegist();

        mRegistGridView.setGridViewItems(loadItemsForGridView(false));

        m_ibCategoryWord.setSelected(true);

        m_ibCategoryPrivate.setSelected(false);

    }
}
