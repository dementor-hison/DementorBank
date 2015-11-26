package kr.co.dementor.dementorbank.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    View.OnDragListener       mOnDragListener    = new View.OnDragListener()
    {
        @Override
        public boolean onDrag(View v, DragEvent event)
        {
            float x = event.getX();
            float y = event.getY();

            LogTrace.d("x : " + x + " , y : " + y);

            switch (event.getAction())
            {
                case DragEvent.ACTION_DRAG_STARTED:
                    LogTrace.d("ACTION_DRAG_STARTED");
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    LogTrace.d("ACTION_DRAG_ENDED");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    LogTrace.d("ACTION_DRAG_ENTERED");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    LogTrace.d("ACTION_DRAG_EXITED");
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    LogTrace.d("ACTION_DRAG_LOCATION");
                    break;
            }

            return false;
        }
    };
    private ArrayList<ImageView> m_arrTargetList = new ArrayList<ImageView>(5);
    private ArrayList<ImageView> m_arrDragList   = new ArrayList<ImageView>(5);
    private String               mSaveName       = "";
    private ImageView            mDragImage      = null;
    private ListView m_lvSendList;
    private SendBankingAdapter         mSendListAdapter  = null;
    private ArrayList<SendBankingInfo> mArrayBankingList = new ArrayList<SendBankingInfo>();
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
    DepositSelectView.OnDepositSelectListener mOnDepositSelectListener = new DepositSelectView.OnDepositSelectListener()
    {
        @Override
        public void OnDepositSelect(int index, DepositInfo item)
        {
            m_tvDepositInfoTitle.setText(item.depositName);
            m_tvDepositInfoSub.setText(item.depositNum);
        }
    };
    private SquareImageView m_sivDragIcon;
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
                        SendBankingInfo sendInfo = (SendBankingInfo) mDragImage.getTag();

                        sendInfo.sendTargetName = (String) hitImage.getTag();

                        addSendListView(sendInfo);
                    }
                    else
                    {
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

    private void addSendListView(SendBankingInfo sendInfo)
    {
        if (mSendListAdapter == null || m_lvSendList.getAdapter() == null)
        {
            mSendListAdapter = new SendBankingAdapter(getApplicationContext(), R.layout.item_send_list);

            m_lvSendList.setAdapter(mSendListAdapter);
        }

        int position = -1;
        for (int i = 0 ; i < mArrayBankingList.size() ; i ++)
        {
            SendBankingInfo alreadyinfo = mArrayBankingList.get(i);
            if(alreadyinfo.sendTargetName == sendInfo.sendTargetName)
            {
                position = i;
                break;
            }
        }

        if(position == -1)
        {
            sendInfo.totalMoneyValue = sendInfo.getIntegerMoneyValue();
            mArrayBankingList.add(sendInfo);
        }
        else
        {
            SendBankingInfo alreadyInfo = mArrayBankingList.get(position);
            alreadyInfo.totalMoneyValue += sendInfo.getIntegerMoneyValue();
        }

        mSendListAdapter.setBankingInfoList(mArrayBankingList);

        mSendListAdapter.notifyDataSetChanged();

        m_lvSendList.smoothScrollToPosition(mArrayBankingList.size() - 1);
    }

    private void resetActivity()
    {
        mArrayBankingList.clear();
        if (mSendListAdapter != null)
        {
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

    private void makeDragImage(ImageView v, int x, int y)
    {
        SendBankingInfo info = (SendBankingInfo) v.getTag();

        if (info == null)
        {
            LogTrace.w("SendBankingInfo info null");
            m_sivDragIcon.setImageDrawable(v.getDrawable());
        }
        else
        {
            LogTrace.w("setImageResource ok");
            switch (info.getIntegerMoneyValue())
            {
                case 1000:
                    m_sivDragIcon.setImageResource(R.drawable.icon_money0);
                    break;
                case 10000:
                    m_sivDragIcon.setImageResource(R.drawable.icon_money1);
                    break;
                case 50000:
                    m_sivDragIcon.setImageResource(R.drawable.icon_money2);
                    break;
                case 100000:
                    m_sivDragIcon.setImageResource(R.drawable.icon_money3);
                    break;
                case 1000000:
                    m_sivDragIcon.setImageResource(R.drawable.icon_money4);
                    break;
                default:
                    LogTrace.e("What?? 0_o");
                    break;
            }

        }

        m_sivDragIcon.setVisibility(View.VISIBLE);


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) (v.getWidth()), (int) (v.getHeight()));

        LogTrace.d("x : " + x + " , y : " + y);

        params.setMargins(x - v.getWidth() / 2, y - v.getHeight() / 2, 0, 0);

        m_sivDragIcon.setLayoutParams(params);

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

    private int mSavedPosition = 1;
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
        m_lvSendList = (ListView) findViewById(R.id.lvSendList);

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

        SendBankingInfo info = new SendBankingInfo();
        info.setSendMoneyValue(1000);
        ivMoney1000.setTag(info);

        info = new SendBankingInfo();
        info.setSendMoneyValue(10000);
        ivMoney10000.setTag(info);

        info = new SendBankingInfo();
        info.setSendMoneyValue(50000);
        ivMoney50000.setTag(info);

        info = new SendBankingInfo();
        info.setSendMoneyValue(100000);
        ivMoney100000.setTag(info);

        info = new SendBankingInfo();
        info.setSendMoneyValue(1000000);
        ivMoney1000000.setTag(info);

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

        ivTarget0.setTag("저축");
        ivTarget1.setTag("관리비");
        ivTarget2.setTag("부모님");
        ivTarget3.setTag("월세");
        ivTarget4.setTag("피아노");

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
        infos[0].setTotalMoney(10000000);

        infos[1] = new DepositInfo();
        infos[1].depositName = "저축예금";
        infos[1].depositNum = "2222-333-4444";
        infos[1].setTotalMoney(20000000);

        infos[2] = new DepositInfo();
        infos[2].depositName = "청약통장";
        infos[2].depositNum = "3333-444-5555";
        infos[2].setTotalMoney(30000000);

        infos[3] = new DepositInfo();
        infos[3].depositName = "월급통장";
        infos[3].depositNum = "4444-555-6666";
        infos[3].setTotalMoney(40000000);

        return infos;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

}
