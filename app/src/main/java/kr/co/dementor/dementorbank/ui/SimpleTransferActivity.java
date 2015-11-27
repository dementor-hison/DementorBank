package kr.co.dementor.dementorbank.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.adapter.SendBankingAdapter;
import kr.co.dementor.dementorbank.common.LogTrace;

/**
 * Created by dementor1 on 15. 11. 24..
 */
public class SimpleTransferActivity extends FragmentActivity
{
    TopView.OnTopViewListener mOnTopViewListener = new TopView.OnTopViewListener()
    {
        @Override
        public void OnBack()
        {
            finish();
        }

        @Override
        public void OnRefresh()
        {
            //not used...
        }

        @Override
        public void OnHelp()
        {
            //not used...
        }

        @Override
        public void OnConfirm()
        {
            //not used...
        }
    };
    DepositSelectView.OnDepositSelectListener mOnDepositSelectListener = new DepositSelectView.OnDepositSelectListener()
    {
        @Override
        public void OnDepositSelect(int index, DepositInfo item)
        {

        }
    };
    private ArrayList<ImageView> m_arrTargetList = new ArrayList<ImageView>(5);
    private ArrayList<ImageView> m_arrDragList   = new ArrayList<ImageView>(5);
    private String               mSaveName       = "";
    private ImageView            mDragImage      = null;
    private ExpandableListView m_lvSendList;
    private SendBankingAdapter mSendListAdapter = null;
    View.OnClickListener mOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.ibJaju:
                    Intent intent = new Intent(getApplicationContext(), JajuActivity.class);
                    startActivity(intent);
                    resetActivity();
                    break;

                case R.id.ibNextActivity1:
                case R.id.ibNextActivity2:
                    Toast.makeText(getApplicationContext(), "계좌이체 실행", Toast.LENGTH_SHORT).show();
                    resetActivity();
                    break;

                case R.id.ibPrevActivity1:
                case R.id.ibPrevActivity2:
                    resetActivity();
                    finish();
                    break;
            }
        }
    };
    private TopView m_transferTopView = null;
    private DepositSelectView m_depositSelectView;
    private TextView          m_tvDepositInfoTitle;
    private TextView          m_tvDepositInfoSub;
    private SquareImageView m_sivDragIcon;
    private BankingUnit mCurrentBankUnit = null;
    View.OnTouchListener mOnTouchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            float x = event.getX();
            float y = event.getY();

            LogTrace.d("x : " + x + " , y : " + y);
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    LogTrace.d("ACTION_DOWN name : " + (String) v.getTag());

                    mDragImage = getDragImageView((int) event.getRawX(), (int) event.getRawY());

                    if (mDragImage == null)
                    {
                        Toast.makeText(getApplicationContext(), "금액을 선택 해 주세요.", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    makeDragImage(mDragImage, (int) x, (int) y);

                    break;

                case MotionEvent.ACTION_UP:

                    removeImage();

                    if (mDragImage == null)
                    {
                        return false;
                    }

                    Vibrator vib = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    vib.vibrate(200);

                    ImageView hitImage = getHitImageView((int) event.getRawX(), (int) event.getRawY());

                    if (hitImage != null)
                    {
                        setTargetName(mCurrentBankUnit, hitImage);

                        addSendListView(mCurrentBankUnit);
                    }
                    else
                    {
                        mCurrentBankUnit = null;
                        Toast.makeText(getApplicationContext(), "정확히 드래그 해 주세요.", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case MotionEvent.ACTION_MOVE:
                    moveImage((int) x, (int) y);

                default:
                    break;
            }

            return false;
        }
    };
    private int mSavedPosition = 1;

    private BankingUnit setTargetName(BankingUnit bankUnit, ImageView hitImage)
    {
        switch (hitImage.getId())
        {
            case R.id.ivTarget0:
                bankUnit.targetName = ((TextView) findViewById(R.id.tvTarget0)).getText().toString();
                break;
            case R.id.ivTarget1:
                bankUnit.targetName = ((TextView) findViewById(R.id.tvTarget1)).getText().toString();
                break;
            case R.id.ivTarget2:
                bankUnit.targetName = ((TextView) findViewById(R.id.tvTarget2)).getText().toString();
                break;
            case R.id.ivTarget3:
                bankUnit.targetName = ((TextView) findViewById(R.id.tvTarget3)).getText().toString();
                break;
            case R.id.ivTarget4:
                bankUnit.targetName = ((TextView) findViewById(R.id.tvTarget4)).getText().toString();
                break;
        }

        return bankUnit;
    }

    private void addSendListView(BankingUnit unit)
    {
        if (m_depositSelectView.getCurrentDepositInfo() == null)
        {
            LogTrace.e("Null");
        }

        DepositInfo currentDepositInfo = m_depositSelectView.getCurrentDepositInfo();

        if (mSendListAdapter.contains(currentDepositInfo.depositName))
        {
            mSendListAdapter.updateBankingData(currentDepositInfo.depositName, unit);
        }
        else
        {
            BankingInfomation newInfomation = new BankingInfomation(currentDepositInfo);
            newInfomation.addBanking(unit);

            mSendListAdapter.addBankingData(newInfomation);
        }

        mSendListAdapter.notifyDataSetChanged();

        m_lvSendList.setSelectedGroup(mSendListAdapter.findGroupPosition(currentDepositInfo.depositName));

        m_lvSendList.expandGroup(mSendListAdapter.findGroupPosition(currentDepositInfo.depositName));
    }

    private void resetActivity()
    {
        if (mSendListAdapter != null)
        {
            if (mSendListAdapter.getBankingData() != null)
            {
                mSendListAdapter.getBankingData().clear();
            }

            mSendListAdapter.notifyDataSetChanged();
        }

        mDragImage = null;
        mDragImage = null;
    }

    private void removeImage()
    {
        if (m_sivDragIcon.getVisibility() == View.VISIBLE)
        {
            m_sivDragIcon.setVisibility(View.GONE);
        }
    }

    private void moveImage(int x, int y)
    {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) m_sivDragIcon.getLayoutParams();

        params.setMargins(x - m_sivDragIcon.getWidth() / 2, y - m_sivDragIcon.getHeight() / 2, 0, 0);

        m_sivDragIcon.setLayoutParams(params);
    }

    private BankingUnit makeDragImage(ImageView v, int x, int y)
    {
        if (v == null)
        {
            return null;
        }

        mCurrentBankUnit = new BankingUnit();
        switch (v.getId())
        {
            case R.id.ivMoney1000:
                m_sivDragIcon.setImageResource(R.drawable.icon_money0);
                mCurrentBankUnit.sendMoney = 1000;
                break;
            case R.id.ivMoney10000:
                m_sivDragIcon.setImageResource(R.drawable.icon_money1);
                mCurrentBankUnit.sendMoney = 10000;
                break;
            case R.id.ivMoney50000:
                m_sivDragIcon.setImageResource(R.drawable.icon_money2);
                mCurrentBankUnit.sendMoney = 50000;
                break;
            case R.id.ivMoney100000:
                m_sivDragIcon.setImageResource(R.drawable.icon_money3);
                mCurrentBankUnit.sendMoney = 100000;
                break;
            case R.id.ivMoney1000000:
                m_sivDragIcon.setImageResource(R.drawable.icon_money4);
                mCurrentBankUnit.sendMoney = 1000000;
                break;
            default:
                LogTrace.d("Wrong click..");
                mCurrentBankUnit = null;
                return null;
        }

        m_sivDragIcon.setVisibility(View.VISIBLE);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) (v.getWidth()), (int) (v.getHeight()));

        LogTrace.d("x : " + x + " , y : " + y);

        params.setMargins(x - v.getWidth() / 2, y - v.getHeight() / 2, 0, 0);

        m_sivDragIcon.setLayoutParams(params);

        return mCurrentBankUnit;
    }

    private ImageView getHitImageView(int x, int y)
    {
        int[] location = new int[2];

        for (int i = 0; i < m_arrTargetList.size(); i++)
        {
            ImageView iv = m_arrTargetList.get(i);

            iv.getLocationOnScreen(location);

            Rect rect = new Rect(location[0], location[1], location[0] + iv.getWidth(), location[1] + iv.getHeight());

            if (rect.contains(x, y))
            {
                return m_arrTargetList.get(i);
            }
        }

        return null;
    }

    private ImageView getDragImageView(int rawX, int rawY)
    {
        int[] location = new int[2];

        for (int i = 0; i < m_arrDragList.size(); i++)
        {
            ImageView iv = m_arrDragList.get(i);

            iv.getLocationOnScreen(location);

            Rect rect = new Rect(location[0], location[1], location[0] + iv.getWidth(), location[1] + iv.getHeight());

            if (rect.contains(rawX, rawY))
            {
                return m_arrDragList.get(i);
            }
        }

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.simple_transfer_activity);

        initView();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mSavedPosition = m_depositSelectView.getCurrentPosition();
    }

    @Override
    protected void onResumeFragments()
    {
        super.onResumeFragments();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                m_depositSelectView.setDepositList(makeDepositItems(), getSupportFragmentManager(), mSavedPosition);
            }
        }, 10);
    }

    @Override
    public void onAttachFragment(Fragment fragment)
    {
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onPostResume()
    {
        super.onPostResume();
    }

    private void initView()
    {
        m_lvSendList = (ExpandableListView) findViewById(R.id.exListSendView);

        mSendListAdapter = new SendBankingAdapter(getApplicationContext());

        m_lvSendList.setAdapter(mSendListAdapter);

        m_transferTopView = (TopView) findViewById(R.id.transferTopView);
        m_transferTopView.setOnTopViewListener(mOnTopViewListener);
        m_transferTopView.setRefreshButtonVisible(false);
        m_transferTopView.setHelpButtonVisible(false);
        m_transferTopView.setConfirmButtonVisible(false);

        findViewById(R.id.ibNextActivity1).setOnClickListener(mOnClickListener);
        findViewById(R.id.ibNextActivity2).setOnClickListener(mOnClickListener);
        findViewById(R.id.ibPrevActivity1).setOnClickListener(mOnClickListener);
        findViewById(R.id.ibPrevActivity2).setOnClickListener(mOnClickListener);

        m_depositSelectView = (DepositSelectView) findViewById(R.id.depositSelectView);
        m_depositSelectView.setOnDepositSelectListener(mOnDepositSelectListener);

        m_tvDepositInfoTitle = (TextView) findViewById(R.id.tvDepositInfoTitle);
        m_tvDepositInfoSub = (TextView) findViewById(R.id.tvDepositInfoSub);

        LinearLayout llTransferTouchArea = (LinearLayout) findViewById(R.id.llTransferTouchArea);
        ImageView    ivMoney1000         = (ImageView) findViewById(R.id.ivMoney1000);
        ImageView    ivMoney10000        = (ImageView) findViewById(R.id.ivMoney10000);
        ImageView    ivMoney50000        = (ImageView) findViewById(R.id.ivMoney50000);
        ImageView    ivMoney100000       = (ImageView) findViewById(R.id.ivMoney100000);
        ImageView    ivMoney1000000      = (ImageView) findViewById(R.id.ivMoney1000000);

        llTransferTouchArea.setOnTouchListener(mOnTouchListener);

        m_arrDragList.clear();
        m_arrDragList.add(ivMoney1000);
        m_arrDragList.add(ivMoney10000);
        m_arrDragList.add(ivMoney50000);
        m_arrDragList.add(ivMoney100000);
        m_arrDragList.add(ivMoney1000000);

        ImageView ivTarget0 = (ImageView) findViewById(R.id.ivTarget0);
        ImageView ivTarget1 = (ImageView) findViewById(R.id.ivTarget1);
        ImageView ivTarget2 = (ImageView) findViewById(R.id.ivTarget2);
        ImageView ivTarget3 = (ImageView) findViewById(R.id.ivTarget3);
        ImageView ivTarget4 = (ImageView) findViewById(R.id.ivTarget4);

        m_arrTargetList.clear();
        m_arrTargetList.add(ivTarget0);
        m_arrTargetList.add(ivTarget1);
        m_arrTargetList.add(ivTarget2);
        m_arrTargetList.add(ivTarget3);
        m_arrTargetList.add(ivTarget4);

        m_sivDragIcon = (SquareImageView) findViewById(R.id.sivSendDragIcon);

        findViewById(R.id.ibJaju).setOnClickListener(mOnClickListener);
    }


    private DepositInfo[] makeDepositItems()
    {
        DepositInfo[] infos = new DepositInfo[4];
        infos[0] = new DepositInfo();
        infos[0].depositName = "보통예금";
        infos[0].depositNum = "1111-222-3333";
        infos[0].setTotalSavedMoney(10000000);

        infos[1] = new DepositInfo();
        infos[1].depositName = "저축예금";
        infos[1].depositNum = "2222-333-4444";
        infos[1].setTotalSavedMoney(20000000);

        infos[2] = new DepositInfo();
        infos[2].depositName = "청약통장";
        infos[2].depositNum = "3333-444-5555";
        infos[2].setTotalSavedMoney(30000000);

        infos[3] = new DepositInfo();
        infos[3].depositName = "월급통장";
        infos[3].depositNum = "4444-555-6666";
        infos[3].setTotalSavedMoney(40000000);

        return infos;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

}
