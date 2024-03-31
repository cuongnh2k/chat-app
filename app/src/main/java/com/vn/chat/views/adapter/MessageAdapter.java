package com.vn.chat.views.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.chat.R;
import com.vn.chat.common.DataStatic;
import com.vn.chat.common.utils.ImageUtils;
import com.vn.chat.data.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private static final Integer RES_ID_LEFT = R.layout.item_message_info_left;
    private static final Integer RES_ID_RIGHT = R.layout.item_message_info_right;
    private Activity activity;
    private List<Message> list;

    public MessageAdapter(@NonNull Activity activity, @NonNull List<Message> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
//        String uid = SessionUtils.get(activity, DataStatic.SESSION.KEY.USER_ID, "");
        String uid = DataStatic.AUTHOR.USER_INFO.getId();
        if(uid.equals(list.get(position).getSender().getUserId())){
            return 1;
        }
        return 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view;
        if(viewType == 0){
            view = inflater.inflate(RES_ID_LEFT, parent, false);
        }else{
            view = inflater.inflate(RES_ID_RIGHT, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message m0 = (position > 0) ? list.get(position-1) : null;
        Message m1 = list.get(position);
        holder.showData(m0, m1);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView tvContent, tvName;
        private ImageView ivMessageImg;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            connectView();
        }

        private void connectView(){
            this.tvName = this.view.findViewById(R.id.tv_name);
            this.tvContent = this.view.findViewById(R.id.tv_content);
            this.ivMessageImg = this.view.findViewById(R.id.iv_message_img);
        }

        public void showData(Message m0, Message m1){
            this.tvName.setText(m1.getSender().getName());
            if(m0 == null){
                this.tvName.setVisibility(View.VISIBLE);
            }else{
                if(!m0.getSender().getUserId().equalsIgnoreCase(m1.getSender().getUserId())){
                    this.tvName.setVisibility(View.VISIBLE);
                }else{
                    this.tvName.setVisibility(View.GONE);
                }
            }
            this.tvContent.setText(m1.getContent());
            if(m1.getFiles() != null && m1.getFiles().size() > 0){
                this.ivMessageImg.setVisibility(View.VISIBLE);
                ImageUtils.loadUrl(activity, ivMessageImg, m1.getFiles().get(0).getUrl());
            }else{
                this.ivMessageImg.setVisibility(View.GONE);
            }
        }
    }
}
