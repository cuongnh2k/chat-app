package com.vn.chat.views.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.chat.R;
import com.vn.chat.common.DataStatic;
import com.vn.chat.common.utils.ImageUtils;
import com.vn.chat.data.Message;
import com.vn.chat.views.activity.HomeActivity;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private static final Integer RES_ID_LEFT = R.layout.item_message_info_left;
    private static final Integer RES_ID_RIGHT = R.layout.item_message_info_right;
    private HomeActivity activity;
    private List<Message> list;

    public MessageAdapter(@NonNull HomeActivity activity, @NonNull List<Message> list) {
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
        holder.actionView(m1);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView tvContent, tvName;
        private ImageView ivThumb, ivMessageImg;
        private CardView cvThumb;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            connectView();
        }

        private void connectView(){
            this.tvName = this.view.findViewById(R.id.tv_name);
            this.tvContent = this.view.findViewById(R.id.tv_content);
            this.ivMessageImg = this.view.findViewById(R.id.iv_message_img);
            this.ivThumb = this.view.findViewById(R.id.iv_thumb);
            this.cvThumb = this.view.findViewById(R.id.cardView);
        }

        private void actionView(Message message){
            this.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    activity.getFragmentMessage().setTargetInfo(message);
                    return true;
                }
            });
        }

        public void showData(Message m0, Message m1){
            this.tvName.setText(m1.getSender().getName());
            if(m0 == null){
                this.tvName.setVisibility(View.VISIBLE);
                this.cvThumb.setVisibility(View.VISIBLE);
                ImageUtils.loadUrl(activity, ivThumb, m1.getSender().getAvatarUrl());
            }else{
                if(!m0.getSender().getUserId().equalsIgnoreCase(m1.getSender().getUserId())){
                    this.tvName.setVisibility(View.VISIBLE);
                    this.cvThumb.setVisibility(View.VISIBLE);
                    ImageUtils.loadUrl(activity, ivThumb, m1.getSender().getAvatarUrl());
                }else{
                    this.tvName.setVisibility(View.GONE);
                    this.cvThumb.setVisibility(View.GONE);
                }
            }
            if(m1.getContent().trim().length() == 0){
                this.tvContent.setVisibility(View.GONE);
            }else{
                this.tvContent.setVisibility(View.VISIBLE);
            }
            this.tvContent.setText(m1.getContent());
            if(m1.getMessageFiles() != null && m1.getMessageFiles().size() > 0){
                this.ivMessageImg.setVisibility(View.VISIBLE);
                ImageUtils.loadUrl(activity, ivMessageImg, m1.getMessageFiles().get(0).getUrl());
            }else{
                this.ivMessageImg.setVisibility(View.GONE);
            }
        }
    }
}
