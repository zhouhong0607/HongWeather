package com.example.administrator.kossweather.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.kossweather.R;

import java.util.List;

/**
 * Created by Administrator on 2016/6/7.
 */
public class QuickAdapter extends BaseQuickAdapter<Item>
{

    public  QuickAdapter(Context context,int layoutResId,List data)
    {
        super(context,layoutResId,data);
    }

    @Override
    protected  void  convert(BaseViewHolder helper,Item item)
    {
        helper.setText(R.id.info_text,item.getTxt());
        CardView cardView=helper.getView(R.id.card_view);
        cardView.setBackgroundColor(Color.parseColor(item.getTxtColor()));
    }
}
