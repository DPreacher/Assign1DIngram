package ca.on.sl.comp208.assign1dingram;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[] choosenImgs;
    private int[] cardImgs;
    private String[] cardIds;
    private ImageButton lastCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shuffleCards();
    }

    public void flipCard(View v){
        ImageButton currentCard = (ImageButton)v;
        CardModel cardTags = (CardModel)currentCard.getTag();

        switch(cardTags.getCurrentState()){
            case FACE_DOWN:
                cardTags.setCurrentState(CardState.FACE_UP);
                currentCard.setImageResource(cardTags.getImage());
                // TODO: check if another card is flipped
                // TODO: check if cards match
                // TODO: after delay flip card back
                break;
            case FACE_UP:
                currentCard.setImageResource(R.drawable.final_fantasy_cover);
                break;
            case MATCHED:

                break;
        }
    }
    private void shuffleCards(){
        Resources res = getResources();
        Random rndNum = new Random(System.currentTimeMillis());

        //Select All Images
        int[] cardImgs = cardImgs = res.getIntArray(R.array.cardImgs);
        List<Integer> orgCards = new ArrayList<>();
        for(int img:cardImgs){ orgCards.add(img);}

        //Choose 8 Random Images
        List<Integer> choosenImg = new ArrayList<>();
        for(int i=0;i<8;i++){
            int rndImgIndex = rndNum.nextInt(orgCards.size());
            choosenImg.add(orgCards.get(rndImgIndex));
            choosenImg.add(orgCards.get(rndImgIndex));
            orgCards.remove(rndImgIndex);
        }
        // Shuffle the images 16 times for good shuffle
        for(int i=0;i<16;i++){Collections.shuffle(choosenImg);}

        //Tag data with new image
        int [] cardIDs = res.getIntArray(R.array.buttonIDs);
        for(int i=0;i<16;i++) {
            ImageButton btn = (ImageButton)findViewById(cardIDs[i]);
            CardModel card
                = new CardModel(
                    btn.getId(),
                    choosenImg.get(i)
                );

            btn.setImageResource(R.drawable.final_fantasy_cover);
            btn.setTag(card);
        }


    }
    private void gameOver(){

    }
    /*
//Get arrays for use later
        Resources res = getResources();
        Random rndImgNum = new Random(System.currentTimeMillis());

        //Retrieve Array of Image Resources
        int[] cardImgs = cardImgs = res.getIntArray(R.array.cardImgs);
        List<Integer> availableImgs = new ArrayList<>();
        for(int i:cardImgs){availableImgs.add(i);} //store image ids in removeable array
/*
        //choose 8 images
        int []  choosenImgs = new int[8];
        for(int i = 0; i<16;i++){
            int rndImgIndex = rndImgNum.nextInt(availableImgs.size());
            choosenImgs[i] = availableImgs.get(rndImgIndex);
            availableImgs.remove(rndImgIndex);
        }

    //Retrieve Arrays of ImageButtons
    //String[] cardIDs = res.getStringArray(R.array.buttonIDs);
    //List<ImageButton> cards = new ArrayList<>();
        // initialize temporary arrays for shuffle
        List<Integer> availableImgs = new ArrayList<>();
        List<ImageButton> setCards = new ArrayList<>();
        List<Integer> choosenImgs = new ArrayList<>();

        for(int i:imgs){availableImgs.add(i);}
        for(int i:cardIds){setCards.add((ImageButton)findViewById(i));}

        //Choose 4 images to use for the game
        Random rndImgNum = new Random(System.currentTimeMillis());
        Random rndCardNum = new Random(System.currentTimeMillis());

        //loop through available images and assign 8 images to 2 cards each
        for(int choosImage=0;choosImage<8;choosImage++){
            //generate the random image index
            int rndImgIndex = rndImgNum.nextInt(availableImgs.size());
            //Assign choosen image to 2 cards
            for(int chooseCard=0;chooseCard<2;chooseCard++){
                //choose a card to assign the image to
                int removeCardIndex = rndCardNum.nextInt(setCards.size());
                //Assign Image to tag
                setCards.get(removeCardIndex).setTag(1,availableImgs.get(rndImgIndex));
                //remove card from assignment list
                setCards.remove(removeCardIndex);
            }
            //choosenImgs.add(availableImgs.get(rndImgIndex));
            //remove choosen image from options
            availableImgs.remove(rndImgIndex);
        }





    }*/
}
