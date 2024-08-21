package com.example.meow.ui.theme;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.activity.ComponentActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import com.example.meow.R;

import java.util.Locale;

public class MainActivity extends  ComponentActivity{

    private Button pronouncebtn;
    private RelativeLayout root_layout;
    private static final int REQUEST_MICROPHONE_PERMISSION = 1;
    private EditText addtxt;
    private TextToSpeech textToSpeech;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            enableEdgeToEdge(); // This method might need to be added manually in Java
            setContentView(R.layout.activity_main);

            root_layout = findViewById(R.id.root_layout);
            addtxt = findViewById(R.id.addtxt);
            pronouncebtn = findViewById(R.id.pronouncebtn);


            // Initialize TextToSpeech
            textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        textToSpeech.setLanguage(Locale.US);
                        textToSpeech.setSpeechRate(0.5f);
//                        textToSpeech.setPitch(1.2f);
                    }
                }
            });

            pronouncebtn.setOnClickListener(v -> {
                // Check microphone permission
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.RECORD_AUDIO},
                            REQUEST_MICROPHONE_PERMISSION);
                } else {
                    // Permission already granted, speak the text
                    speakText();
                }
            });


        }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_MICROPHONE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, speak the text
                speakText();
            } else {
                // Permission denied
            }
        }
    }

    // Function to speak the text
    private void speakText() {
        String text = addtxt.getText().toString();
        if (!text.isEmpty()) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

        private void enableEdgeToEdge() {
            // You might need to manually implement this function if it's not available in Java
        }
    }



