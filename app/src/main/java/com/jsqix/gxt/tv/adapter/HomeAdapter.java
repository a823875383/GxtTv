package com.jsqix.gxt.tv.adapter;

import android.content.Context;

import com.jsqix.gxt.tv.R;
import com.jsqix.gxt.tv.obj.HomeResult;
import com.jsqix.gxt.tv.utils.BitmapHelp;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by dongqing on 2016/10/21.
 */

public class HomeAdapter extends CommonAdapter<HomeResult.ObjBean> {
    private int[] colors = {R.mipmap.bg_menu_green, R.mipmap.bg_menu_blue};

    public HomeAdapter(Context context, int layoutId, List<HomeResult.ObjBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, HomeResult.ObjBean item, int position) {
        viewHolder.setBackgroundRes(R.id.rel_image, colors[position % 2]);
        viewHolder.setText(R.id.tv_name, item.getModel_name());
        viewHolder.setText(R.id.tv_dec, item.getModel_subtitle());
        BitmapHelp.getBitmap(mContext).display(viewHolder.getView(R.id.iv_logo), item.getModel_icon());
    }
}
