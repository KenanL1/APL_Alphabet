package com.example.asl_alphabet;

import android.os.Bundle;
import android.app.Activity;
import android.app.Service;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Color;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    //View Objects
    Button translate;
    EditText enterText;
    TextView displayText;
    ImageView aslImages;

    //Varaible for translation and display
    int phraseIndex = 0; //keep track of the array indexes
    String letters; //Messsage to be Translated
    String display; //Will hod the letters already displayed and show them
    int i = 1;

    //Array libraries for characters and Image references
    char letterIndex[] = {'1', '2', '3', 'a', 'b', 'c', 'd',
                          'e', 'f', 'g', 'h',
                          'i', 'j', 'k', 'l',
                          'm', 'n', 'o', 'p',
                          'q', 'r', 's', 't',
                          'u', 'v', 'w', 'x',
                          'y', 'z', ' '};

    TableRow layout1;
    TableLayout lay1;
    TableRow layout2;


    int aslPics[] = {R.drawable.goodmorning, R.drawable.goodafernoon, R.drawable.goodnight, R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p, R.drawable.q, R.drawable.r, R.drawable.s, R.drawable.t, R.drawable.u, R.drawable.v, R.drawable.w, R.drawable.x, R.drawable.y, R.drawable.z, R.drawable.space};


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout1 = findViewById(R.id.tableRow2);
        layout1.setBackgroundColor(Color.parseColor("#00ff00"));

        layout2 = findViewById(R.id.tableRow4);
        layout2.setBackgroundColor(Color.parseColor("#ffffff"));

        lay1 = findViewById(R.id.lay);
        lay1.setBackgroundColor(Color.parseColor("#ADD8E6"));

        //Attach objects to View objects
        translate = (Button) findViewById(R.id.buttonTranslate);
        enterText = (EditText) findViewById(R.id.textInput);
        displayText = (TextView) findViewById(R.id.displayText);
        aslImages = (ImageView) findViewById(R.id.aslViewer);

        //Select all the text in the entertext field
        enterText.setSelectAllOnFocus(true);
    }

    //@Override
//public boolean onCreateOptionsMenu (Menu menu){
    //Inflate the menu; this adds items to the action bar if it is present
    //  getMenuInflater().inflate(R.menu.activity_main, menu);
    //return true;
//}

    //setString Function
//Acts when the Translate Button is pressed
    public void setString(final View v) throws InterruptedException{
        //set the text of displayText to '---'"
        displayText.setText("------");

        //close the keyboard on lost focus
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);

        //Reset the Phrase Index
        phraseIndex = 0;
        display = "";

        //Get the input text
        Editable input = enterText.getText();

        //Convert to a string
        String phrase = input.toString();
        letters = phrase.toLowerCase();
        if(!letters.equals("good morning") && letters!="good afternoon" && letters != "good night") {
            i = 1;
            final ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
            exe.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    //for (int i = 0; i < letters.length(); i++){\
                    while (i <= letters.length()) {
                        System.out.println(i);
                        i++;
                        try {
                            Thread.sleep(1000);
                            translateLetter(v);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    exe.shutdown();
                }
            }, 0, 1, TimeUnit.SECONDS);
        }

    }

    //Display Image when Image View Touched.
//Also display text of letters alreadty translated
    public void translateLetter(View v){
        //Checks if letters string is null - display message
        //If the phrase has not been converted to a string


        if (letters == null) {
            displayText.setText("Press the Translate Button");
        }
        //Check if letters string is null - will not display
        //ASL letters until Translate Button is pressed
        if (letters != null){
            if(!letters.equals("good morning") && letters!="good afternoon" && letters != "good night") {
                //Fetch the current character in the phrase
                char currentLetter = letters.charAt(phraseIndex);

                //add the letter to the display string
                display += currentLetter;

                //Search for the corresponding ASL image by Index
                for (int i = 0; i < letterIndex.length; i++) {
                    if (letterIndex[i] == currentLetter) {
                        //Display the image
                        aslImages.setImageResource(aslPics[i]);
                    }
                }

                //set the text to display the letters translated
                displayText.setText(display);

                //Advance to the Next letter in the phrase
                phraseIndex++;

                //check to see if you reach the end of the phrase
                if (phraseIndex > letters.length() - 1) {
                    //Reset back to the first character
                    phraseIndex = 0;
                    display = "";
                }
            }
            else{
                if(letters.equals("good morning"))
                    aslImages.setImageResource(aslPics[0]);
                else if (letters == "good afternoon")
                    aslImages.setImageResource(aslPics[1]);
                else
                    aslImages.setImageResource((aslPics[2]));

            }
        }
    }
}
