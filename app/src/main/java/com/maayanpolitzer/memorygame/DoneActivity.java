package com.maayanpolitzer.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DoneActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REMATCH = 5;
    public static final int QUIT = 6;
    private TextView scoreTextView;
    private Button rematchButton;
    private Button quitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        scoreTextView = (TextView) findViewById(R.id.activity_done_score_text_view);
        rematchButton = (Button) findViewById(R.id.activity_done_play_again_button);
        quitButton = (Button) findViewById(R.id.activity_done_quit_button);

        int score = getIntent().getIntExtra("SCORE", 0);

        scoreTextView.setText("Your score is: " + score);
        rematchButton.setOnClickListener(this);
        quitButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == rematchButton) {
            setResult(REMATCH);
        } else {
            setResult(QUIT);
        }
        finish();
    }
}
