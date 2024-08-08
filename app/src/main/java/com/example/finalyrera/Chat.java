package com.example.finalyrera;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    private static final String CHANNEL_ID = "admin_notifications";
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private Button buttonSend;
    private MessagesAdapter messagesAdapter;
    private List<Message> messageList;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        messageList = new ArrayList<>();
        messagesAdapter = new MessagesAdapter(messageList);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(messagesAdapter);

        sharedPreferences = getSharedPreferences("ChatPrefs", Context.MODE_PRIVATE);

        // Load any previous message
        loadMessages();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = editTextMessage.getText().toString();
                if (!userMessage.isEmpty()) {
                    sendMessage(userMessage);
                    editTextMessage.setText("");
                }
            }
        });

        // Simulate receiving an admin message (for demo purposes)
        receiveAdminMessage("Hello! This is an important update.");
    }

    private void loadMessages() {
        // Retrieve message from SharedPreferences
        String lastMessage = sharedPreferences.getString("lastMessage", null);
        if (lastMessage != null) {
            // Add the message to the list and update UI
            messageList.add(new Message(lastMessage, false));
            messagesAdapter.notifyDataSetChanged();
            recyclerViewMessages.smoothScrollToPosition(messageList.size() - 1);

            // Clear the stored message
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("lastMessage");
            editor.apply();
        }
    }

    private void sendMessage(String userMessage) {
        // Add user message to the list
        messageList.add(new Message(userMessage, true));
        messagesAdapter.notifyDataSetChanged();
        recyclerViewMessages.smoothScrollToPosition(messageList.size() - 1);

        // Simulate bot response
        String botResponse = getBotResponse(userMessage);
        messageList.add(new Message(botResponse, false));
        messagesAdapter.notifyDataSetChanged();
        recyclerViewMessages.smoothScrollToPosition(messageList.size() - 1);
    }

    private String getBotResponse(String userMessage) {
        // Simple bot logic with predefined responses
        userMessage = userMessage.toLowerCase().trim();

        if (userMessage.contains("hello") || userMessage.contains("hi")) {
            return "Hello! How can I help you today? ";
        } else if (userMessage.contains("help")) {
            return "Sure, I'm here to help! What do you need assistance with?";
        } else if (userMessage.contains("incident")) {
            return "Please provide more details about the incident.";
        } else if (userMessage.contains("thank you") || userMessage.contains("thanks")) {
            return "You're welcome! Is there anything else I can assist with?";
        } else if (userMessage.contains("emergency")) {
            return "In case of an emergency, please call the appropriate emergency number listed in the app.";
        } else if (userMessage.contains("report")) {
            return "You can report an incident by filling out the form in the 'Incident Reporting' section.";
        } else if (userMessage.contains("contact")) {
            return "You can find emergency contact numbers in the 'Emergency Contacts' section.";
        } else if (userMessage.contains("location")) {
            return "Your current location can be viewed on the map in the 'Maps' section.";
        } else if (userMessage.contains("safety guidelines")) {
            return "You can access the safety guidelines document from the 'Emergency Contacts' page.";
        } else if (userMessage.contains("panic button")) {
            return "The panic button is available in the 'Maps' section. Use it to alert responders in case of an emergency.";
        } else if (userMessage.contains("register") || userMessage.contains("sign up")) {
            return "To register, go to the 'Signup' page and enter your details.";
        } else if (userMessage.contains("login")) {
            return "To log in, go to the 'Login' page and enter your credentials.";
        } else {
            return "I'm not sure how to respond to that. Could you please provide more details?";
        }
    }

    private void receiveAdminMessage(String message) {
        // Add admin message to the list and update UI
        messageList.add(new Message(message, false));
        messagesAdapter.notifyDataSetChanged();
        recyclerViewMessages.smoothScrollToPosition(messageList.size() - 1);

        // Show notification
        NotificationUtils.showNotification(this, "New message from Admin", message);
    }
}
