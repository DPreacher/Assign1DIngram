package ca.on.sl.comp208.assign1dingram;

import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //Globaal Variables
    private ImageButton lastCard, prevCard;
    private int numFlips,numMatches,hour,min,sec;
    private boolean gameStarted,gameOver;
    private String totalTime;
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeArrays();
        //shuffleCards();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void flipCard(View v) {
        if(!gameStarted) {
            gameStarted = true;
            startTime();
        }
        if(lastCard==null){
            ImageButton currentCard = (ImageButton) v;
            CardModel cardTags = (CardModel) currentCard.getTag();
            switch (cardTags.getCurrentState()) {
                case FACE_DOWN:
                    // change tag to Face up
                    cardTags.setCurrentState(CardState.FACE_UP);
                    numFlips++;
                    ((TextView)findViewById(R.id.txtvClicks)).setText(numFlips+"");
                    //Show card image
                    currentCard.setImageResource(cardTags.getImage());
                    currentCard.setBackgroundColor(ContextCompat.getColor(this, R.color.card_flipped));
                    //Check if there is a card flipped already
                    if (prevCard == null) {
                        // Store the Card for Matching later
                        prevCard = currentCard;
                    } else {
                        lastCard = currentCard;
                        //Open stored Card
                        CardModel prevCardFlipped = (CardModel) prevCard.getTag();
                        //check if images match
                        if (prevCardFlipped.getImage() == cardTags.getImage()) {
                            //Set both Cards to Matched

                            prevCardFlipped.setCurrentState(CardState.MATCHED);

                            prevCard.setBackgroundColor(ContextCompat.getColor(this, R.color.match));
                            lastCard.setBackgroundColor(ContextCompat.getColor(this, R.color.match));
                            cardTags.setCurrentState(CardState.MATCHED);
                            numMatches++;
                            ((TextView)findViewById(R.id.txtMatchs)).setText("/"+numMatches);
                            Log.i("Match"," Found");
                            lastCard = null;
                            prevCard = null;
                            if(numMatches == 8){
                                Log.i("Match","Win");
                                t.interrupt();
                                Toast.makeText(this,"You win", Toast.LENGTH_LONG);
                            }


                        } else {
                            // TODO: Add time delay so player can see second card
                            Timer timer = new Timer();


                            TimerTask timerTask = new TimerTask(){
                                @Override
                                public void run(){
                                    runOnUiThread(new Runnable (){
                                        @Override
                                        public void run(){
                                            Log.i("runOnUiThread"," started");
                                            //Flip stored card over
                                            ((CardModel) prevCard.getTag()).setCurrentState(CardState.FACE_DOWN);
                                            prevCard.setImageResource(R.drawable.final_fantasy_cover);
                                            prevCard.setBackgroundColor(Color.parseColor("#53D0F4"));
                                            lastCard.setBackgroundColor(Color.parseColor("#53D0F4"));
                                            prevCard = null;
                                            //Flip current card over
                                            ((CardModel) lastCard.getTag()).setCurrentState(CardState.FACE_DOWN);
                                            lastCard.setImageResource(R.drawable.final_fantasy_cover);
                                            lastCard =null;
                                        }
                                    });
                                }
                            };
                            timer.schedule(timerTask,1000);
                        }
                    }
                    break;
                case FACE_UP:
                    //if Card is face up, flip back over
                    currentCard.setImageResource(R.drawable.final_fantasy_cover);
                    currentCard.setBackgroundColor(ContextCompat.getColor(this,R.color.card_cover));
                    ((CardModel)currentCard.getTag()).setCurrentState(CardState.FACE_DOWN);
                    prevCard = null;
                    lastCard=null;
                    break;
                case MATCHED:
                    break;
            }
        }
    }

    private void startTime(){
        t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!gameOver){
                                    sec++;
                                    if(sec==60){
                                        sec=0;
                                        min++;
                                        if(min==60){
                                            min=0;
                                            hour++;
                                        }
                                    }
                                    String tmp,time="";
                                    if(hour<10) time = "0";
                                    time = time +hour+":";
                                    if(min<10) time = time +"0";
                                    time=time+min+":";
                                    if(sec<10) time = time+"0";
                                    time=time+sec;
                                    ((TextView)findViewById(R.id.txtvTime)).setText(time);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

    private void initializeArrays(){
        gameOver=gameStarted = false;
        hour=min=sec=numMatches=numFlips = 0;
        List<Integer> imgs = new ArrayList<>();
        List<Integer> btnIDs = new ArrayList<>();

        Log.i("Init Arrays", "Storing Imgs");
        imgs.add(R.drawable.black_mage);
        imgs.add(R.drawable.white_mage);
        imgs.add(R.drawable.dancer);
        imgs.add(R.drawable.dark_knight);
        imgs.add(R.drawable.dragoon);
        imgs.add(R.drawable.knight);
        imgs.add(R.drawable.ninja);
        imgs.add(R.drawable.norm);
        imgs.add(R.drawable.scholar);
        imgs.add(R.drawable.thief);

        Log.i("Init Arrays", "Storing Btns");
        btnIDs.add(R.id.imageButton1);
        btnIDs.add(R.id.imageButton2);
        btnIDs.add(R.id.imageButton3);
        btnIDs.add(R.id.imageButton4);
        btnIDs.add(R.id.imageButton5);
        btnIDs.add(R.id.imageButton6);
        btnIDs.add(R.id.imageButton7);
        btnIDs.add(R.id.imageButton8);
        btnIDs.add(R.id.imageButton9);
        btnIDs.add(R.id.imageButton10);
        btnIDs.add(R.id.imageButton11);
        btnIDs.add(R.id.imageButton12);
        btnIDs.add(R.id.imageButton13);
        btnIDs.add(R.id.imageButton14);
        btnIDs.add(R.id.imageButton15);
        btnIDs.add(R.id.imageButton16);

        Log.i("Init Arrays", "Shuffle Imgs");
        Log.i("Init Arrays", "    "+imgs.toString());
        Collections.shuffle(imgs);

        Log.i("Init Arrays", "Shuffle Imgs Complete");
        Log.i("Init Arrays", "    "+imgs.toString());

        Log.i("Init Arrays", "Shuffle Btns");
        Log.i("Init Arrays", "    "+btnIDs.toString());
        Collections.shuffle(btnIDs);

        Log.i("Init Arrays", "Shuffle Btns Complete");
        Log.i("Init Arrays", "    "+btnIDs.toString());

        Log.i("Init Arrays","Assign Imgs to Btns");
        for(int i=0;i<16;i++){
            CardModel cardData;
            if(i>=8) {
                int tmpInt = i-8;
                cardData = new CardModel(imgs.get(tmpInt));
            }
            else {
                cardData = new CardModel(imgs.get(i));
            }
            ImageButton tmpBtn = (ImageButton) findViewById(btnIDs.get(i));
            tmpBtn.setTag(cardData);
            tmpBtn.setImageResource(R.drawable.final_fantasy_cover);
            Log.i("Init Arrays", "Set CardData to btn "+(i+1));
        }

    }

    private void gameOver() {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
