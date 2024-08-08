package com.example.finalyrera;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private List<Message> messageList;

    public MessagesAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (message.isUserMessage()) {
            holder.userMessageTextView.setVisibility(View.VISIBLE);
            holder.botMessageTextView.setVisibility(View.GONE);
            holder.userMessageTextView.setText(message.getText());
        } else {
            holder.botMessageTextView.setVisibility(View.VISIBLE);
            holder.userMessageTextView.setVisibility(View.GONE);
            holder.botMessageTextView.setText(message.getText());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView userMessageTextView;
        TextView botMessageTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            userMessageTextView = itemView.findViewById(R.id.userMessageTextView);
            botMessageTextView = itemView.findViewById(R.id.botMessageTextView);
        }
    }
}
