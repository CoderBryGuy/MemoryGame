package com.maayanpolitzer.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GAME_OVER_REQUEST_CODE = 1;
    LinearLayout mainLayout;
    int counter = 0;
    int[] images = {R.drawable.one, R.drawable.two, R.drawable.three,
            R.drawable.four, R.drawable.five, R.drawable.six};
    int[] numbers = {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5};
    private int turns = 0;
    private ImageView previousCard;
    private int rightGuesses = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shuffle();

        mainLayout = (LinearLayout) findViewById(R.id.activity_main_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;

        for (int i = 0; i < 4; i++) {
            LinearLayout rowLayout = new LinearLayout(this);

            for (int j = 0; j < 3; j++) {
                ImageView imageView = new ImageView(this);
                imageView.setTag(counter++);
                imageView.setOnClickListener(this);
                imageView.setImageResource(R.drawable.card_back);
                rowLayout.addView(imageView, params);
            }
            mainLayout.addView(rowLayout, params);
        }


    }

    private void startNewGame(){
        counter = 0;
        rightGuesses = 0;
        turns = 0;
        for (int i = 0; i < numbers.length; i++){
            previousCard = (ImageView) mainLayout.findViewWithTag(i);
            previousCard.setImageResource(R.drawable.card_back);
            previousCard.setOnClickListener(this);
        }

        shuffle();
    }



    private void shuffle(){
        // shuffle to number array...
        int temp = 0;
        Random random = new Random();
        for (int i = 0; i < numbers.length; i++){
            int r = random.nextInt(numbers.length);
            temp = numbers[i];
            numbers[i] = numbers[r];
            numbers[r] = temp;
        }
    }


    @Override
    public void onClick(View v) {
        ImageView clickedCard = (ImageView) v;
        int image = images[numbers[(int) v.getTag()]];
        clickedCard.setImageResource(image);
        if (clickedCard != previousCard) {
            if (turns % 2 != 0) {
                // check cards...
                if (checkCards(clickedCard, previousCard)) {
                    // they are the same!
                    clickedCard.setOnClickListener(null);
                    previousCard.setOnClickListener(null);
                    rightGuesses++;
                } else {
                    // flip them.
                    FlipThread thread = new FlipThread(clickedCard, previousCard);
                    thread.start();
                }

            } else {
                previousCard = clickedCard;
            }

            turns++;
            if (rightGuesses == images.length){
                Intent intent = new Intent(this, DoneActivity.class);
                intent.putExtra("SCORE", turns);
                startActivityForResult(intent, GAME_OVER_REQUEST_CODE);
            }
        } else {
            Toast.makeText(this, "Same card...", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkCards(ImageView clickedCard, ImageView previousCard) {
        return numbers[(int) previousCard.getTag()] == numbers[(int) clickedCard.getTag()];
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GAME_OVER_REQUEST_CODE){
            if (resultCode == DoneActivity.REMATCH){
                // start new game..
                startNewGame();
                Toast.makeText(this, "New game", Toast.LENGTH_SHORT).show();
            }else if (resultCode == DoneActivity.QUIT){
                finish();
            }
        }
    }
}










