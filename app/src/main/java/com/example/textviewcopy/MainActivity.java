package com.example.textviewcopy;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editText;
    Button button;
    Button sendButton;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Instantiate button to point to button
        button = findViewById(R.id.button1);
        //Instantiate edit text
        editText = findViewById(R.id.editText1);
        //Instantiate text view
        textView = findViewById(R.id.textView1);
        sendButton = findViewById(R.id.sendButton);

        button.setOnClickListener(this);
    }

    public void onTextClick(View v) {
        if (!textView.getText().toString().isEmpty()){
        textView.setBackgroundResource(R.color.black);
        textView.setTextColor(Color.WHITE);
        }
    }


    @Override public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.button1) {
            String name = editText.getText().toString();
            textView.setText(name);

            if (!name.isEmpty()) {
                sendButton.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onSend(View v) {
        String message = textView.getText().toString();

        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:123455678"));
        smsIntent.putExtra("sms_body", message);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        } else {
            // Start the SMS app if permission is granted
            if (smsIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(smsIntent);  // Actually open the SMS app
            }
        }
    }
}