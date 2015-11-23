package kr.co.dementor.dementorbank.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.Defines;
import kr.co.dementor.dementorbank.common.LogTrace;


/**
 * Created by dementor1 on 15. 11. 4..
 */
public class CustomGridView extends FrameLayout
{
    private GridView              mGridView        = null;
    private CustomGridViewAdapter mGridViewAdapter = new CustomGridViewAdapter();

    private ImageView mIvScrollMore = null;

    private OnDragListener      mOnCustomDragListener      = null;
    private OnItemClickListener mOnCustomItemClickListener = null;

    private boolean mIsDragable = false;

    private FrameLayout     mDragIconBounds         = null;
    private SquareImageView mIvDragView             = null;
    private int             m_iSelectedIconPosition = AdapterView.INVALID_POSITION;
    private int             m_iTargetIconPosition   = AdapterView.INVALID_POSITION;

    private OnTouchListener mOnTouchListener = new OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            float x = event.getX();
            float y = event.getY();

            int position = mGridView.pointToPosition((int) x, (int) y);

            int resID = Defines.WHITE_AREA;

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:

                    if (position == AdapterView.INVALID_POSITION)
                    {
                        return false;
                    }

                    m_iSelectedIconPosition = position;

                    resID = (int) mGridViewAdapter.getItem(m_iSelectedIconPosition);

                    if (mOnCustomDragListener != null && mIsDragable)
                    {
                        mOnCustomDragListener.OnDragStart(m_iSelectedIconPosition, resID);

                        makeImage(position, (int) x, (int) y);
                    }

                    break;

                case MotionEvent.ACTION_UP:

                    if (mOnCustomDragListener != null && mIsDragable && m_iSelectedIconPosition != AdapterView.INVALID_POSITION && m_iTargetIconPosition != AdapterView.INVALID_POSITION)
                    {
                        resID = (int) mGridViewAdapter.getItem(m_iTargetIconPosition);

                        mOnCustomDragListener.OnDragEnd(m_iTargetIconPosition, resID);

                        m_iSelectedIconPosition = AdapterView.INVALID_POSITION;

                        m_iTargetIconPosition = AdapterView.INVALID_POSITION;

                        Vibrator vib = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        vib.vibrate(200);

                        removeImage();
                    }

                    break;

                case MotionEvent.ACTION_MOVE:

                    if (mOnCustomDragListener != null && mIsDragable && m_iSelectedIconPosition != AdapterView.INVALID_POSITION)
                    {
                        m_iTargetIconPosition = position == AdapterView.INVALID_POSITION ? m_iTargetIconPosition : position;

                        moveImage(x, y);
                    }

                default:
                    break;
            }

            return false;
        }
    };

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            if (mOnCustomItemClickListener != null)
            {
                int imageSrcId = (int) mGridViewAdapter.getItem(position);
                mOnCustomItemClickListener.OnItemClick(view, position, imageSrcId);
            }
        }
    };

    private AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener()
    {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState)
        {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
        {
            if (firstVisibleItem + visibleItemCount >= totalItemCount)
            {
                mIvScrollMore.setVisibility(ImageView.INVISIBLE);
            }
            else
            {
                mIvScrollMore.setVisibility(ImageView.VISIBLE);
            }
        }
    };


    public CustomGridView(Context context)
    {
        super(context);

        createView(context);

        initView();
    }

    public CustomGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        createView(context);

        initView();

    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        createView(context);

        initView();

    }

    private void makeImage(int position, int x, int y)
    {
        SquareImageView selectedItem = (SquareImageView) mGridView.getChildAt(position);

        if (selectedItem == null)
        {
            LogTrace.w("notFound item at position : " + position);
            return;
        }

        LogTrace.d("width : " + selectedItem.getWidth() + " , height : " + selectedItem.getHeight());

        LayoutParams params = new LayoutParams((int) (selectedItem.getWidth() * 1.5f), (int) (selectedItem.getHeight() * 1.5f));

        params.setMargins(x - mIvDragView.getWidth() / 2, y - mIvDragView.getHeight() / 2, 0, 0);

        mIvDragView.setLayoutParams(params);

        //Bitmap b = Bitmap.createBitmap(selectedItem.getDrawingCache());

        Bitmap b = Bitmap.createBitmap(selectedItem.getMeasuredWidth(),

                                       selectedItem.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas screenShotCanvas = new Canvas(b);

        selectedItem.draw(screenShotCanvas);


        mIvDragView.setImageBitmap(b);

        mIvDragView.setVisibility(ImageView.VISIBLE);
    }

    private void moveImage(float x, float y)
    {
        LayoutParams params = (LayoutParams) mIvDragView.getLayoutParams();

        params.setMargins((int) x - mIvDragView.getWidth() / 2, (int) y - mIvDragView.getHeight() / 2, 0, 0);

        mIvDragView.setLayoutParams(params);
    }

    private void removeImage()
    {
        mIvDragView.setVisibility(ImageView.GONE);
    }

    private View createView(Context context)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(R.layout.custom_grid_view, this, true);
    }

    private void initView()
    {
        mGridView = (GridView) findViewById(R.id.gvGridView);

        mDragIconBounds = (FrameLayout) findViewById(R.id.flDragIconBounds);

        mIvDragView = (SquareImageView) findViewById(R.id.ivDrag);

        mIvScrollMore = (ImageView)findViewById(R.id.ivScrollMore);

        mGridViewAdapter = new CustomGridViewAdapter();

        mGridView.setAdapter(mGridViewAdapter);

        mGridView.setOnTouchListener(mOnTouchListener);

        mGridView.setOnItemClickListener(mOnItemClickListener);

        mGridView.setOnScrollListener(mOnScrollListener);
    }

    public void setGridViewItems(ArrayList<Integer> listBitmaps)
    {
        if (mGridViewAdapter == null)
        {
            LogTrace.e("GridView adapter not set");
            return;
        }

        mGridViewAdapter.setItemArrayList(listBitmaps);

        mGridViewAdapter.notifyDataSetChanged();
    }

    public void setOnDragListener(CustomGridView.OnDragListener listener)
    {
        mOnCustomDragListener = listener;
    }

    public boolean isDragable()
    {
        return mIsDragable;
    }

    public void setDragable(boolean isDragable)
    {
        mIsDragable = isDragable;
    }

    public void setOnItemClickListener(CustomGridView.OnItemClickListener listener)
    {
        mOnCustomItemClickListener = listener;
    }

    public interface OnDragListener
    {
        void OnDragStart(int position, int resId);

        void OnDragEnd(int position, int resId);
    }

    public interface OnItemClickListener
    {
        void OnItemClick(View view, int position, int imageSrcId);
    }

}
