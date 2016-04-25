package edu.up.cs301.texasHoldem;

import java.io.Serializable;

import edu.up.cs301.card.Card;
import edu.up.cs301.card.Rank;
import edu.up.cs301.card.Suit;

/**
 * Created by bruschwe18 on 3/16/2016.
 */
public class player implements Serializable {
    private static final long serialVersionUID = 4209179064365413754L;


    private int money;
    private int curBet;
    private int numBet;
    private Card[] hand = new Card[5];
    private boolean isActive;
    private boolean isEliminated;
    private Card card1 = null;
    private Card card2 = null;
    private Card card3 = null;
    private Card card4 = null;
    private Card card5 = null;
    private boolean isTie;
    private int handValue;
    private int playerNum;
    private boolean inSidePot;

    public boolean getInSidePot(){return this.inSidePot;}

    public void setInSidePot(boolean bool){this.inSidePot = bool;}

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCurBet() {
        return curBet;
    }

    public void setCurBet(int curBet) {
        this.curBet = curBet;
    }

    public Card[] getHand() {
        return hand;
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }


    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsActive(){
        return this.isActive;
    }

    public Card getCard1() {
        return card1;
    }

    public void setCard1(Card newCard){

        Rank newRank = newCard.getRank();
        Suit newSuit = newCard.getSuit();
        this.card1 = new Card(newRank, newSuit);
    }

    public Card getCard2() {
        return card2;
    }

    public void setCard2(Card newCard){

        Rank newRank = newCard.getRank();
        Suit newSuit = newCard.getSuit();
        this.card2 = new Card(newRank, newSuit);
    }


    public Card getCard3() {
        return card3;
    }

    public void setCard3(Card newCard){

        Rank newRank = newCard.getRank();
        Suit newSuit = newCard.getSuit();
        this.card3 = new Card(newRank, newSuit);
    }

    public Card getCard4() {
        return card4;
    }

    public void setCard4(Card newCard){

        Rank newRank = newCard.getRank();
        Suit newSuit = newCard.getSuit();
        this.card4 = new Card(newRank, newSuit);
    }

    public Card getCard5() {
        return card5;
    }

    public void setCard5(Card newCard){

        Rank newRank = newCard.getRank();
        Suit newSuit = newCard.getSuit();
        this.card5 = new Card(newRank, newSuit);
    }

    public void setIsTie(boolean bool) {
        this.isTie = bool;
    }

    public boolean getIsTie() {
        return this.isTie;
    }

    public int getHandValue() {
        return handValue;
    }

    public void setHandValue(int handValue) {
        this.handValue = handValue;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public int getNumBet() {
        return numBet;
    }

    public void setNumBet(int numBet) {
        this.numBet = numBet;
    }

    public void setHandInd(Card card1, Card card2, Card card3, Card card4, Card card5){
        Rank newRank = card1.getRank();
        Suit newSuit = card1.getSuit();
        this.hand[0] = new Card(newRank, newSuit);
        this.card1 = hand[0];
        Rank newRank2 = card2.getRank();
        Suit newSuit2 = card2.getSuit();
        this.hand[1] = new Card(newRank2, newSuit2);
        this.card2 = hand[1];
        Rank newRank3 = card3.getRank();
        Suit newSuit3 = card3.getSuit();
        this.hand[2] = new Card(newRank3, newSuit3);
        this.card3 = hand[2];
        Rank newRank4 = card4.getRank();
        Suit newSuit4 = card4.getSuit();
        this.hand[3] = new Card(newRank4, newSuit4);
        this.card4 = hand[3];
        Rank newRank5 = card5.getRank();
        Suit newSuit5 = card5.getSuit();
        this.hand[4] = new Card(newRank5, newSuit5);
        this.card5 = hand[4];
    }

    public boolean getIsEliminated() {

        return this.isEliminated;
    }

    public void setIsEliminated(boolean isEliminated) {
        this.isEliminated = isEliminated;
    }



    public player(){
        money = 1000;
        curBet = 0;
        isActive = true;
        isEliminated = false;
        isTie = false;
        numBet = 0;
        inSidePot = false;
    }

}

