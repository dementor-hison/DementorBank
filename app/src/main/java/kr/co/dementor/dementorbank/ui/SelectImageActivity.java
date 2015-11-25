package kr.co.dementor.dementorbank.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.Defines;
import kr.co.dementor.dementorbank.kr.co.dementor.dementorbank.adapter.SelectImageGridAdapter;

/**
 * Created by dementor1 on 15. 11. 23..
 */
public class SelectImageActivity extends FragmentActivity
{
    private TopView                mSelectImageTopview    = null;
    private GridView               mSelectGridView        = null;
    private SelectImageGridAdapter mSelectGridViewAdapter = new SelectImageGridAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_image_activity);

        initView();

        setGridItem();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    private void initView()
    {
        mSelectImageTopview = (TopView) findViewById(R.id.selectImageTopview);

        mSelectImageTopview.setOnTopViewListener(mOnTopViewListener);

        mSelectImageTopview.setRefreshButtonVisible(false);

        mSelectImageTopview.setConfirmButtonVisible(true);

        mSelectGridView = (GridView)findViewById(R.id.gvSelectImageGrid);

        mSelectGridView.setAdapter(mSelectGridViewAdapter);

        mSelectGridView.setOnItemClickListener(mOnItemClickListener);
    }

    TopView.OnTopViewListener mOnTopViewListener = new TopView.OnTopViewListener()
    {
        @Override
        public void OnBack()
        {
            setResult(Activity.RESULT_CANCELED);

            finish();
        }

        @Override
        public void OnRefresh()
        {
            //not used..
        }

        @Override
        public void OnHelp()
        {
            //not used..
        }

        @Override
        public void OnConfirm()
        {
            ArrayList<Integer> arrListData = new ArrayList<Integer>();

            for (ImageInfo info : mSelectGridViewAdapter.getListItems())
            {
                if (info.isChecked())
                {
                    arrListData.add(info.getResId());
                }
            }

            Intent intent = new Intent();

            intent.putIntegerArrayListExtra("items", arrListData);

            setResult(Activity.RESULT_OK, intent);

            finish();
        }
    };

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            ImageInfo info = (ImageInfo) mSelectGridViewAdapter.getItem(position);

            info.setChecked(!info.isChecked());

            mSelectGridViewAdapter.notifyDataSetChanged();
        }
    };


    private void setGridItem()
    {
        ArrayList<ImageInfo> arrayList = new ArrayList<ImageInfo>();

        for (int resId : Defines.RES_ID_PRIVATE)
        {
            ImageInfo info = new ImageInfo();

            info.setChecked(false);

            info.setResId(resId);

            info.setResName(getResources().getResourceName(resId));

            arrayList.add(info);
        }

        mSelectGridViewAdapter.setListItems(arrayList);

        mSelectGridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        setResult(Activity.RESULT_CANCELED);
    }
}
