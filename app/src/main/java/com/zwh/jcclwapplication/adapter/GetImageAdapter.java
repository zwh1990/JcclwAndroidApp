package com.zwh.jcclwapplication.adapter;

import com.zwh.jcclwapplication.R;
import com.zwh.jcclwapplication.entity.Picture;

import java.util.List;

public class GetImageAdapter extends BaseRvAdapter<Picture> {

    public GetImageAdapter(List<Picture> datas, int layoutId) {
        super(datas, layoutId);
    }

    @Override
    public void convert(CompatViewHolder holder, int position, Picture picture) {
        holder.setImageGlide(R.id.imv, picture.getUrl());
        holder.setText(R.id.title, picture.getName());
    }
}
