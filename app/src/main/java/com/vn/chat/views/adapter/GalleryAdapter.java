package com.vn.chat.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.vn.chat.R;
import com.vn.chat.common.utils.ImageUtils;
import com.vn.chat.data.File;

import java.util.List;

public class GalleryAdapter extends ArrayAdapter<File> {
	private static final Integer RES_ID = R.layout.item_gallery;
	private Activity context;
	private List<File> files;

	public GalleryAdapter(Activity activity, List<File> arraylist) {
		super(activity, RES_ID, arraylist);

		this.context = activity;
		this.files = arraylist;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null){
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_gallery, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.showData(files.get(position));
		return convertView;
	}
	class ViewHolder{
		ImageView iv;
		public ViewHolder(View convertView){
			this.iv = (ImageView) convertView.findViewById(R.id.iv_gallery);
		}

		public void showData(File file){
			ImageUtils.loadUrl(context, this.iv, file.getUrl());
		}
	}
}
