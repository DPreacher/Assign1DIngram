package ca.on.sl.comp208.assign1dingram;

/**
 * Created by DIngram20 on 2017-01-23.
 */

public class CardModel {
    private int image;
    private int cardID;
    private CardState currentState;

    public CardModel(int cardID, int image) {
        this.cardID = cardID;
        this.image = image;
        currentState = CardState.FACE_DOWN;
    }

    public CardState getCurrentState() {return currentState;}
    public void setCurrentState(CardState currentState) {this.currentState = currentState;}

    public int getImage() {return image;}
    public void setImage(int image) {this.image = image;}

    public int getCardID() {return cardID;}
    public void setCardID(int cardID) {this.cardID = cardID;}
}
