package com.example.jsu.lab3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import android.widget.*;

public class CryptoLogic extends AppCompatActivity {

    private Random random = new Random();

    private ArrayList<String> secretWords = new ArrayList(Arrays.asList("APPLE", "BANANA", "CHERRY", "RAMBUTAN", "DURIAN", "STARFRUIT", "DRAGONFRUIT", "TOMATO"));
    private String currentWord;
    private String guessBuilder;
    private int guessCounter;
    private boolean gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_logic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setNextWord();
        gameOver = false;


    }

    public void onGuess(View v){

        EditText inputBox = (EditText) findViewById(R.id.editTextGuesses);
        TextView builder = (TextView) findViewById(R.id.textViewCorrect);
        TextView outputMessage = (TextView) findViewById(R.id.instructionsTextView);
        Button button = (Button) findViewById(R.id.buttonGuess);

        if(gameOver){
            setNextWord();
            outputMessage.setText(R.string.instructionsText);
            inputBox.setEnabled(true);
            button.setText(R.string.buttonGuessText);
            builder.setText("");
            gameOver = false;

        }

        else if(inputBox.getText().length()>0) {
            char letter = inputBox.getText().charAt(0);
            inputBox.setText("");


            if (currentWord.charAt(guessBuilder.length()) == letter) {
                guessBuilder += letter;
                builder.setText(guessBuilder.toUpperCase());
                if (currentWord.equals(guessBuilder)) {
                    inputBox.setEnabled(false);

                    outputMessage.setText(getString(R.string.resultText, currentWord.toUpperCase(), guessCounter));
                    button.setText(R.string.buttonNextWordText);
                    gameOver = true;
                }
            } else {
                guessCounter++;
                updateCounter();
            }
        }
    }

    private void setNextWord(){
        int index = random.nextInt(secretWords.size());
        currentWord = secretWords.get(index);
        TextView scrambleView = (TextView) findViewById(R.id.textViewScrambled);
        scrambleView.setText(scrambleWord(currentWord).toUpperCase());
        guessBuilder = "";
        guessCounter = 0;
        updateCounter();

    }

    private String scrambleWord(String word){

        String shuffledWord = "";
        ArrayList<String> splitWord = new ArrayList(Arrays.asList(word.split("")));
        Collections.shuffle(splitWord);
        for (String c : splitWord){
            shuffledWord += c;
        }
        return shuffledWord;

    }

    private void updateCounter(){
        TextView guesses = (TextView) findViewById(R.id.textViewGuessCounter);
        guesses.setText("" + guessCounter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crypto_logic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
