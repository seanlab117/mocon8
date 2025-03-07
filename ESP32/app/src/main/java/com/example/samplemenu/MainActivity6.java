package com.example.samplemenu;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity6 extends AppCompatActivity {

    TextView textViewColorHex, textViewRGB, textViewRed, textViewGreen, textViewBlue;
    ImageView imageView;
    SeekBar seekBarRed, seekBarGreen, seekBarBlue;
    int redValue = 64;
    int greenValue = 128;
    int blueValue = 0;

    private void updateColor(){
        int color = Color.rgb(redValue, greenValue, blueValue);
        imageView.setBackgroundColor(color);

        String hex = String.format("#%02X%02X%02X", redValue, greenValue, blueValue);
        textViewColorHex.setText(hex);
        Log.d("haha", "updateColor:hex::"+hex);

        String redGreenBlue = String.format("(%d, %d, %d)", redValue, greenValue, blueValue);
        textViewRGB.setText(redGreenBlue);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main6);

        textViewColorHex = findViewById(R.id.textViewColorHex);
        textViewColorHex.setText("");
        textViewRGB = findViewById(R.id.textViewRGB);
        textViewRGB.setText("");
        textViewRed = findViewById(R.id.textViewRed);
        textViewGreen = findViewById(R.id.textViewGreen);
        textViewBlue = findViewById(R.id.textViewBlue);
        imageView = findViewById(R.id.imageView);
        seekBarRed = findViewById(R.id.seekBarRed);
        seekBarGreen = findViewById(R.id.seekBarGreen);
        seekBarBlue = findViewById(R.id.seekBarBlue);

        updateColor();

        seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blueValue = progress;
                updateColor();
                textViewBlue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                greenValue = progress;
                updateColor();
                textViewGreen.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                redValue = progress;
                updateColor();
                textViewRed.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.whiteButton).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                textViewColorHex.setText("");
                textViewRGB.setText("");
                redValue = 255;
                greenValue = 255;
                blueValue = 255;
                updateColor();

                seekBarRed.setProgress(redValue);
                seekBarGreen.setProgress(greenValue);
                seekBarBlue.setProgress(blueValue);

                textViewRed.setText(String.valueOf(redValue));
                textViewGreen.setText(String.valueOf(greenValue));
                textViewBlue.setText(String.valueOf(blueValue));
            }
        });

        findViewById(R.id.blackButton).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                textViewColorHex.setText("");
                textViewRGB.setText("");
                redValue = 0;
                greenValue = 0;
                blueValue = 0;
                updateColor();

                seekBarRed.setProgress(redValue);
                seekBarGreen.setProgress(greenValue);
                seekBarBlue.setProgress(blueValue);

                textViewRed.setText(String.valueOf(redValue));
                textViewGreen.setText(String.valueOf(greenValue));
                textViewBlue.setText(String.valueOf(blueValue));
            }
        });

        findViewById(R.id.blueButton).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                textViewColorHex.setText("");
                textViewRGB.setText("");
                redValue = 0;
                greenValue = 0;
                blueValue = 255;
                updateColor();

                seekBarRed.setProgress(redValue);
                seekBarGreen.setProgress(greenValue);
                seekBarBlue.setProgress(blueValue);

                textViewRed.setText(String.valueOf(redValue));
                textViewGreen.setText(String.valueOf(greenValue));
                textViewBlue.setText(String.valueOf(blueValue));
            }
        });

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textViewColorHex.setText("");
                textViewRGB.setText("");
                redValue = 64;
                greenValue = 128;
                blueValue = 0;
                updateColor();

                seekBarRed.setProgress(redValue);
                seekBarGreen.setProgress(greenValue);
                seekBarBlue.setProgress(blueValue);

                textViewRed.setText(String.valueOf(redValue));
                textViewGreen.setText(String.valueOf(greenValue));
                textViewBlue.setText(String.valueOf(blueValue));
            }
        });

    }


}
