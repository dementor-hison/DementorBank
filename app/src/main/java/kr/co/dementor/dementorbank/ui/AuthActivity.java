package kr.co.dementor.dementorbank.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
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
public class AuthActivity extends FragmentActivity
{
    private boolean            m_isAuthSuccess      = true;
    private int                m_dragImageId        = 0;
    private ArrayList<Integer> mKeys                = new ArrayList<>(Defines.MAX_KEY_CAPACITY);
    private ArrayList<Integer> mListRandomIconResID = new ArrayList<>(Defines.MAX_AUTH_ICON_CAPACITY);
    private TopView            mTopview             = null;
    private CustomGridView     mCustomGridView      = null;
    private ImageView          m_ivStatus           = null;
    private ImageView          m_ivStatusAnim       = null;
    private Animation          mAniFadeInOut        = null;
    private AlertDialog        mAlertDialog         = null;
    private ImageButton        m_ibRefresh          = null;
    private ImageButton        m_ibSecurityLevel    = null;
    private ImageButton        m_ibKeySetting       = null;
    private ImageButton        m_ibIconSetting      = null;
    private ActionPopup        m_actionPopup        = null;
    CustomGridView.OnDragListener mOnDragListener    = new CustomGridView.OnDragListener()
    {
        @Override
        public void OnDragStart(int position, int resId)
        {
            LogTrace.d("position : " + position + " , resID : " + resId);

            m_dragImageId = resId;
        }

        @Override
        public void OnDragEnd(int position, int currentLockId)
        {
            LogTrace.d("position : " + position + " , resID : " + currentLockId);

            Defines.AuthStatus currentState = Defines.AuthStatus.values()[m_ivStatus.getDrawable().getLevel()];

            switch (currentState)
            {
                case INSERT_NONE:
                    m_ivStatus.setImageLevel(currentState.nextState().getValue());
                    m_ivStatusAnim.setImageLevel(currentState.nextState().getValue());
                    m_actionPopup.setHintImage(Defines.ImagePosition.LOCK, currentLockId);
                    m_actionPopup.setHintImage(Defines.ImagePosition.KEY1, m_dragImageId);

                    if (m_dragImageId != mKeys.get(Defines.ImagePosition.KEY1))
                    {
                        m_isAuthSuccess = false;
                    }
                    break;

                case INSERTED_KEY1:
                    m_ivStatus.setImageLevel(currentState.nextState().getValue());
                    m_ivStatusAnim.setImageLevel(currentState.nextState().getValue());
                    m_actionPopup.setHintImage(Defines.ImagePosition.KEY2, m_dragImageId);

                    if (m_dragImageId != mKeys.get(Defines.ImagePosition.KEY2))
                    {
                        m_isAuthSuccess = false;
                    }
                    break;

                case INSERTED_KEY2:

                    m_actionPopup.setHintImage(Defines.ImagePosition.KEY3, m_dragImageId);

                    if (m_dragImageId != mKeys.get(Defines.ImagePosition.KEY3) && mKeys.get(Defines.ImagePosition.LOCK) != currentLockId)
                    {
                        m_isAuthSuccess = false;
                    }

                    if (m_isAuthSuccess == false)
                    {
                        Toast.makeText(getApplicationContext(), "인증실패", Toast.LENGTH_SHORT).show();
                        refreshAuth();
                    }
                    else
                    {
                        refreshAuth();
                        Intent intent = new Intent(getApplicationContext(), BankMainActivity.class);
                        startActivity(intent);
                    }
                    break;

                case INSERTED_KEY3:
                    LogTrace.i("What??0_o");
                    break;

                default:
                    LogTrace.i("What??0_o");
                    break;
            }

            if (mKeys.get(Defines.ImagePosition.LOCK) != currentLockId)
            {
                Toast.makeText(getApplicationContext(), "wrong LOCK", Toast.LENGTH_SHORT).show();
                m_isAuthSuccess = false;
            }
        }
    };
    private FrameLayout m_flAuthGuide = null;
    private Handler  handler            = new Handler();
    private Runnable runHideActionPopup = new Runnable()
    {
        @Override
        public void run()
        {
            m_actionPopup.setVisibilityWithAnimation(View.GONE);
        }
    };
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
    View.OnClickListener mOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.ivAuthStatusAnim:

                    handler.post(runShowActionPopup);

                    break;
                case R.id.ibAuthRefresh:
                    refreshAuth();
                    break;

                case R.id.ibAuthSecurityLevel:
                    break;

                case R.id.ibAuthKeySetting:
                    Intent intent = new Intent(getApplicationContext(), RegistActivity.class);
                    startActivity(intent);

                    if (mAlertDialog != null && mAlertDialog.isShowing())
                    {
                        mAlertDialog.dismiss();
                    }

                    break;

                case R.id.ibAuthGuideClose:
                    hideHelp();
                    break;

                case R.id.ibAuthNeverSee:
                    DementorUtil.savePreferance(getApplicationContext(), getString(R.string.preference_key_never_see_auth), true);

                    hideHelp();
                    break;

                default:
                    break;
            }
        }
    };
    private ImageButton m_ibAuthGuideClose = null;
    private ImageButton m_ibAuthNeverSee   = null;
    private ViewPager   m_vpAuthHelpImage  = null;
    private ImageView[] m_ivHelpDot        = new ImageView[Defines.RES_ID_AUTH_HELP.size()];
    TopView.OnTopViewListener     mOnTopViewListener = new TopView.OnTopViewListener()
    {
        @Override
        public void OnBack()
        {
            refreshAuth();

            finish();
        }

        @Override
        public void OnRefresh() {}

        @Override
        public void OnHelp()
        {
            showHelp();
        }

        @Override
        public void OnConfirm() {}
    };
    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {

        }

        @Override
        public void onPageSelected(int position)
        {
            LogTrace.d("position = " + position);

            for (int i = 0; i < m_ivHelpDot.length; i++)
            {
                m_ivHelpDot[i].setSelected(i == position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    };

    private void hideHelp()
    {
        m_ibKeySetting.setClickable(true);
        m_flAuthGuide.setVisibility(FrameLayout.GONE);
    }

    private void showHelp()
    {
        m_ibKeySetting.setClickable(false);
        m_flAuthGuide.setVisibility(FrameLayout.VISIBLE);

        m_vpAuthHelpImage.setCurrentItem(0);

        m_ivHelpDot[0].setSelected(true);
        m_ivHelpDot[1].setSelected(false);
        m_ivHelpDot[2].setSelected(false);
        m_ivHelpDot[3].setSelected(false);
        m_ivHelpDot[4].setSelected(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_activity);

        boolean isKeyRegisted = (boolean) DementorUtil.loadPreferance(getApplicationContext(), getString(R.string.preference_key_is_regist), false);

        initView();

        if (isKeyRegisted == false)
        {
            showRegistDialog();
            return;
        }

        mKeys.addAll(loadKeyData());

        refreshAuth();
    }

    private ArrayList<Integer> generateRandomList(ArrayList<Integer> fixedIconList)
    {
        ArrayList<Integer> resultList = new ArrayList<Integer>(Defines.MAX_AUTH_ICON_CAPACITY);

        resultList.addAll(fixedIconList);

        Set<String> set = (Set<String>) DementorUtil.loadPreferance(getApplicationContext(), getString(R.string.preference_key_dummy_set), new HashSet<String>());

        for (String name : set)
        {
            resultList.add(getResources().getIdentifier(name, "drawable", getApplicationContext().getPackageName()));
        }

        Collections.shuffle(resultList);

        return resultList;
    }

    private ArrayList<Integer> loadKeyData()
    {
        ArrayList<Integer> list = new ArrayList<>(Defines.MAX_KEY_CAPACITY);

        String strLock = (String) DementorUtil.loadPreferance(getApplicationContext(), getString(R.string.preference_key_lock_res_id), "");

        String strKey1 = (String) DementorUtil.loadPreferance(getApplicationContext(), getString(R.string.preference_key_key1_res_id), "");

        String strKey2 = (String) DementorUtil.loadPreferance(getApplicationContext(), getString(R.string.preference_key_key2_res_id), "");

        String strKey3 = (String) DementorUtil.loadPreferance(getApplicationContext(), getString(R.string.preference_key_key3_res_id), "");

        int lockId = getResources().getIdentifier(strLock, "drawable", getApplicationContext().getPackageName());

        int key1Id = getResources().getIdentifier(strKey1, "drawable", getApplicationContext().getPackageName());

        int key2Id = getResources().getIdentifier(strKey2, "drawable", getApplicationContext().getPackageName());

        int key3Id = getResources().getIdentifier(strKey3, "drawable", getApplicationContext().getPackageName());

        list.add(Defines.ImagePosition.LOCK, lockId);

        list.add(Defines.ImagePosition.KEY1, key1Id);

        list.add(Defines.ImagePosition.KEY2, key2Id);

        list.add(Defines.ImagePosition.KEY3, key3Id);

        return list;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    private void showRegistDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setMessage(R.string.dialog_msg_need_regist);

        dialogBuilder.setPositiveButton(R.string.dialog_button_ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(getApplicationContext(), RegistActivity.class);
                startActivity(intent);

                if (mAlertDialog != null && mAlertDialog.isShowing())
                {
                    mAlertDialog.dismiss();
                }

                finish();
            }
        });
        dialogBuilder.setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (mAlertDialog != null && mAlertDialog.isShowing())
                {
                    mAlertDialog.dismiss();
                }

                finish();
            }
        });
        dialogBuilder.setCancelable(false);

        mAlertDialog = dialogBuilder.show();
    }

    private void initView()
    {
        m_ivStatus = (ImageView) findViewById(R.id.ivAuthStatus);

        m_ivStatusAnim = (ImageView) findViewById(R.id.ivAuthStatusAnim);

        mTopview = (TopView) findViewById(R.id.authTopView);

        mCustomGridView = (CustomGridView) findViewById(R.id.gvAuthGridView);

        m_ibRefresh = (ImageButton) findViewById(R.id.ibAuthRefresh);

        m_ibSecurityLevel = (ImageButton) findViewById(R.id.ibAuthSecurityLevel);

        m_ibKeySetting = (ImageButton) findViewById(R.id.ibAuthKeySetting);

        m_ibIconSetting = (ImageButton) findViewById(R.id.ibAuthIconSetting);

        m_actionPopup = (ActionPopup) findViewById(R.id.authActionPopup);

        m_flAuthGuide = (FrameLayout) findViewById(R.id.flAuthGuide);

        m_ibAuthGuideClose = (ImageButton) findViewById(R.id.ibAuthGuideClose);

        m_ibAuthNeverSee = (ImageButton) findViewById(R.id.ibAuthNeverSee);

        mTopview.setRefreshButtonVisible(false);

        mTopview.setConfirmButtonVisible(false);

        mTopview.setHelpButtonVisible(true);

        mTopview.setOnTopViewListener(mOnTopViewListener);

        mCustomGridView.setOnDragListener(mOnDragListener);

        m_ivStatusAnim.setOnClickListener(mOnClickListener);

        m_ibRefresh.setOnClickListener(mOnClickListener);

        m_ibSecurityLevel.setOnClickListener(mOnClickListener);

        m_ibKeySetting.setOnClickListener(mOnClickListener);

        m_ibIconSetting.setOnClickListener(mOnClickListener);

        m_ibAuthGuideClose.setOnClickListener(mOnClickListener);

        m_ibAuthNeverSee.setOnClickListener(mOnClickListener);

        mCustomGridView.setDragable(true);

        mAniFadeInOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_inout);

        m_vpAuthHelpImage = (ViewPager) findViewById(R.id.vpAuthHelpImage);

        HelpAdapter adapter = new HelpAdapter(getApplicationContext(), Defines.RES_ID_AUTH_HELP);

        m_vpAuthHelpImage.setAdapter(adapter);

        m_ivHelpDot[0] = (ImageView) findViewById(R.id.ivAuthHelpDot1);
        m_ivHelpDot[1] = (ImageView) findViewById(R.id.ivAuthHelpDot2);
        m_ivHelpDot[2] = (ImageView) findViewById(R.id.ivAuthHelpDot3);
        m_ivHelpDot[3] = (ImageView) findViewById(R.id.ivAuthHelpDot4);
        m_ivHelpDot[4] = (ImageView) findViewById(R.id.ivAuthHelpDot5);

        m_vpAuthHelpImage.addOnPageChangeListener(mOnPageChangeListener);

        m_vpAuthHelpImage.setCurrentItem(0);

        boolean isNeverSee = (boolean) DementorUtil.loadPreferance(getApplicationContext(), getString(R.string.preference_key_never_see_auth), false);

        if(isNeverSee == false)
        {
            showHelp();
        }

    }

    private void refreshAuth()
    {
        m_ivStatus.setImageLevel(Defines.AuthStatus.INSERT_NONE.getValue());

        m_ivStatusAnim.setImageLevel(Defines.AuthStatus.INSERT_NONE.getValue());

        m_ivStatusAnim.startAnimation(mAniFadeInOut);

        mListRandomIconResID.clear();

        mListRandomIconResID.addAll(generateRandomList(mKeys));

        mCustomGridView.setGridViewItems(mListRandomIconResID);

        m_actionPopup.clearHintImage();

        m_isAuthSuccess = true;
    }
}
