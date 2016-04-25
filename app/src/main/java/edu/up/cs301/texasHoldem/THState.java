package edu.up.cs301.texasHoldem;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.card.Card;
import edu.up.cs301.card.Rank;
import edu.up.cs301.card.Suit;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * Created by bruschwe18 on 3/16/2016.
 */
public class THState extends GameState{

    private static final long serialVersionUID = 4209179064333136954L;

    private int pot;
    private int minBet;
    private int curTurn;
    private int curRound;
    private Card[] cardMid;
    private int playerNum;
    private Deck deck;
    private edu.up.cs301.texasHoldem.player[] player;
    private int currentCustomBet;
    private int latestBet;
    private int recieveIndex;
    private boolean sidePot;
    private int numActive;
    private int sidePotNum = 1;


    public THState(){
        pot = 0;
        minBet = 25;
        curTurn = 0;
        curRound = 0;
        cardMid = new Card[5];
        deck = new Deck();
        currentCustomBet = 0;
        player = new player[4];
        player[0] = new player();
        player[1] = new player();
        player[2] = new player();
        player[3] = new player();
        playerNum = 4;
        recieveIndex = 0;
        sidePot = false;
        numActive = 0;
        dealCards();
    }

    public THState(int numPlayers){
        //TODO changed for different amount of players mod
        pot = 0;
        minBet = 25;
        curTurn = 0;
        curRound = 0;
        playerNum = numPlayers;
        cardMid = new Card[5];
        player = new player[numPlayers];

        int i;
        for(i = 0; i < numPlayers; i++) {
            player[i] = new player();
        }

        deck = new Deck();
        currentCustomBet = 0;
        recieveIndex = 0;
        sidePot = false;
        numActive = numPlayers;
        dealCards();
    }


    /**
     * This method makes and returns a deep copy of the current gamestate.
     *
     *
     * @return  copied state
     */
    public THState(THState state) {

        //TODO put in the full copy constructor

        recieveIndex = state.getRecieveIndex();

        pot =state.getPot();

        minBet = state.getMinBet();

        curTurn = state.getCurTurn();

        curRound = state.getCurRound();

        playerNum = state.getPlayerNum();

        deck = state.getDeck();

        sidePot = state.getSidePot();

        //copy cardMid array
        cardMid = new Card[5];
        int j;

        for(j = 0; j < 5; j++){
            setCardMid(j, state.getCardMid(j));
        }

        player = new player[playerNum];
        int i;
        for(i = 0 ; i < state.getPlayerNum() ; i++ ) {



            player[i] = new player();
            player[i].setMoney(state.player[i].getMoney());
            player[i].setCurBet(state.player[i].getCurBet());
            player[i].setNumBet(state.player[i].getNumBet());
            player[i].setIsTie(state.player[i].getIsTie());
            player[i].setHandValue(state.player[i].getHandValue());
            player[i].setIsActive(state.player[i].getIsActive());
            player[i].setIsEliminated(state.player[i].getIsEliminated());
            player[i].setPlayerNum(state.player[i].getPlayerNum());
            player[i].setCard1(state.getPlayer(i).getCard1());
            player[i].setCard2(state.getPlayer(i).getCard2());
            if(!(state.getPlayer(i).getCard3() == null))
            {
                player[i].setCard3(state.getPlayer(i).getCard3());
            }

            if(!(state.getPlayer(i).getCard4() == null)){
                player[i].setCard4(state.getPlayer(i).getCard4());

            }
            if(!(state.getPlayer(i).getCard5() == null)){
                player[i].setCard5(state.getPlayer(i).getCard5());

            }


            //thCopy.player[i].setHand();

        }

    }


    /*
     * This method changes the turn to the next player
     * This allows the next player to bet
     * this is called after a player has completed their turn
     */
    public void changeTurn() {
        this.setCurTurn(this.getCurTurn() + 1); //set the turn to the next player
        if(this.getCurTurn() == this.player.length) {  //if it was the last player's turn, go back to the first player
            this.setCurTurn(0);
        }
        if(this.player[this.getCurTurn()].getIsActive() == false ){
            this.changeTurn();
        }

    }


    /*
     * This method allows the player to bet
     * takes the money they bet out of their "bank" and adds it to the pot
     * adds the money to their current bet variable and adds one to their betCount
     */
    public void bet(int amountToBet) {
        //handles easy AI
        if(amountToBet == -1){
            Random rand = new Random();
            int nextInt = (this.getPlayer(this.getCurTurn()).getMoney() + this.getPlayer(this.getCurTurn()).getCurBet()  - this.getMinBet());
            Log.i("Player" + this.getCurTurn(), " nextInt:" + nextInt);
            Log.i("Latest Bet:", "" + this.getLatestBet());
            if(nextInt < 0){
                this.fold();
                return;
            }
            if(nextInt == 0){
                this.check();
                return;
            }
            amountToBet = rand.nextInt(nextInt) + 1;
        }
        if(amountToBet > this.getPlayer(this.getCurTurn()).getMoney() + this.getPlayer(this.getCurTurn()).getCurBet()  - this.getMinBet()){
            amountToBet = this.getPlayer(this.getCurTurn()).getMoney() + this.getPlayer(this.getCurTurn()).getCurBet()  - this.getMinBet();
        }
        /*
        else{
            this.setSidePot(true);
            this.getPlayer(this.getCurTurn()).setInSidePot(true);
            sidePotNum = numActive;
        }
        */
        this.player[this.getCurTurn()].setMoney(this.player[this.getCurTurn()].getMoney() - amountToBet - this.getMinBet() + this.getPlayer(this.getCurTurn()).getCurBet());  //subtract from their bank
        this.setPot(this.getPot() + amountToBet + this.getMinBet() - this.getPlayer(this.getCurTurn()).getCurBet());  //add to the pot
        this.player[this.getCurTurn()].setCurBet(this.getMinBet() + amountToBet);
        this.player[this.getCurTurn()].setNumBet(this.player[this.getCurTurn()].getNumBet() + 1);
        this.player[this.getCurTurn()].setNumBet(this.player[curTurn].getNumBet() + 1);
        this.changeTurn();
        if(amountToBet > 0) {
            this.setLatestBet(0);
        }
        this.setMinBet(this.getMinBet() + amountToBet);
        this.setLatestBet(this.getLatestBet() + 1);
        if(this.getLatestBet() >= numActive){
            this.changeCurRound();
        }
    }

    /*
     * this method is called when a player wins a pot
     * changes the amount of money the player has to their previous money + the amount that was in the pot
     */
    public void winPot(int winningIndex) {

        player[winningIndex].setMoney(player[winningIndex].getMoney() + this.getPot());
        this.setLatestBet(0);
        this.setCurRound(0);
        this.setMinBet(25);
        this.setCurTurn(0);
        this.setPot(0);
        int i = 0;
        for(i=0; i<this.player.length; i++){
            this.player[i].setCurBet(0);
            this.player[i].setHandValue(-1);
            this.player[i].setIsTie(false);
            this.player[i].setNumBet(0);
            if(this.player[i].getMoney() <= 25){
                this.player[i].setIsActive(false);
                this.player[i].setIsEliminated(true);
            }else{
                this.player[i].setIsActive(true);
            }
        }
        this.dealCards();
    }

    /*
     * This method shuffles the deck and deals the cards to the players and the middle
     */
    public void dealCards() {
        Deck newDeck = new Deck();  //creates a deck of 52 cards and shuffles it
        this.setDeck(newDeck);
        this.deck.add52();
        this.deck.shuffle();
        for(int i = 0; i < playerNum; i++) {  //deals a first card to each player
            this.player[i].setCard1(this.deck.removeTopCard());
        }
        for(int j = 0; j < playerNum; j++) {  //deals a second card to each player
            this.player[j].setCard2(this.deck.removeTopCard());
        }
        Card[] newMid = new Card[5];  //array of 5 cards for the middle
        for(int k = 0; k < 5; k++) {  //deals the 5 cards in the miiddle
            newMid[k] = this.deck.removeTopCard();
            this.setCardMid(k, newMid[k]);
        }

    }


    /**
     *  This is called when the min bet on the table is still zero and the player who's turn it is
     *  does not want to raise the bet.
     *
     *  This method basically just changes the turn for that special scenario
     *
     *
     */
    public void check() {
        // make sure that the min bet is still zero
        if( this.getMinBet() == 0){

            this.changeTurn();
            this.setLatestBet(this.getLatestBet() + 1);
            if(this.getLatestBet() >= numActive){
                this.changeCurRound();
            }

        }
        else
        {
            callBet();
        }

        //otherwise do nothing

    } /* End check() */


    /**
     *  This method is called when a player not longer wants to bet any higher.
     *  It will set the player who's current turn it is 'isActive' variable to false,
     *  and change the turn.
     *
     */
    public void fold(){

        this.player[this.getCurTurn()].setIsActive(false);
        this.changeTurn();
        this.setLatestBet(this.getLatestBet() + 1);
        if(this.getLatestBet() >= numActive){
            this.changeCurRound();
        }
        int i = 0;
        int isOver = 0;
        int isOverInd = 0;
        for(i=0;i<this.player.length;i++){
            if(this.player[i].getIsActive() == true){
                isOver++;
                isOverInd = i;
            }
        }
        numActive--;
        if(isOver == 1){
            this.winPot(isOverInd);
        }

    } /* End fold() */


    /**
     *  This method bets bet the current minimum bet that is on the table.
     *  It will add the min bet to the pot, and subtract the min bet from the player who's turn
     *  it is. Also keeps track of how many times a player has bet in a round.
     */
    public void callBet(){

        //checks that the player has enough money
        /*
        if(this.player[this.getCurTurn()].getMoney() + this.player[this.getCurTurn()].getCurBet() >= this.getMinBet() ){


            this.player[this.getCurTurn()].setMoney(this.player[this.getCurTurn()].getMoney()  + this.player[this.getCurTurn()].getCurBet() - this.getMinBet());
            this.setPot(this.getPot() + this.getMinBet() - this.player[this.getCurTurn()].getCurBet());
            this.player[this.getCurTurn()].setNumBet(this.player[this.getCurTurn()].getNumBet() + 1);
            this.player[this.getCurTurn()].setCurBet(this.getMinBet());
            this.changeTurn();
            this.setLatestBet(this.getLatestBet() + 1);
            if(this.getLatestBet() >= 4){
                this.changeCurRound();
            }
        }
        else{
            fold();
        }
        */
        this.bet(0);
    }



    public void changeCurRound(){
        this.setCurRound(this.getCurRound() + 1);
        int i;
        for(i=0; i<player.length;i++){
            player[i].setNumBet(0);
        }
        this.setLatestBet(0);
    }


    //takes variable number of players and returns integer that refers to the index of the player who wins the hand
    public int handWinner(){
        int i = 0;
        //iterates through all players
        for(i=0; i<this.player.length; i++){
            //checks to make sure player is still active in the round
            if(this.player[i].getIsActive()){
                //evaluates quality of hand for all possible card combos, sets users hand and handvalue to whatever it catches as the highest

                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(3)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(2));


                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(3)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(3)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(3));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(4)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(4)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(1), this.getCardMid(4));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(3)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(3)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(3));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(4)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(4)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(2), this.getCardMid(4));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(3), this.getCardMid(4)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(3), this.getCardMid(4)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(0), this.getCardMid(3), this.getCardMid(4));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(2), this.getCardMid(3)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(2), this.getCardMid(3)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(2), this.getCardMid(3));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(2), this.getCardMid(4)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(2), this.getCardMid(4)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(2), this.getCardMid(4));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(3), this.getCardMid(4)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(3), this.getCardMid(4)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(1), this.getCardMid(3), this.getCardMid(4));
                }

                if(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(2), this.getCardMid(3), this.getCardMid(4)) > this.player[i].getHandValue()){
                    this.player[i].setHandValue(handEvaluate(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(2), this.getCardMid(3), this.getCardMid(4)));
                    this.player[i].setHandInd(this.player[i].getCard1(), this.player[i].getCard2(), this.getCardMid(2), this.getCardMid(3), this.getCardMid(4));
                }
            } else{ //sets inactive player hand value below 0
                this.player[i].setHandValue(-1);
            }
        }
        int valMax = -1;
        int valMaxInd = 0;
        int tie = 0;

        //finds index of highest hand value player
        for (i = 0; i < this.player.length; i++) {
            if (this.player[i].getHandValue() > valMax) {
                valMax = this.player[i].getHandValue();
                valMaxInd = i;
                tie = 1;
            } else if (this.player[i].getHandValue() == valMax) {
                tie++;
            }
        }

        for (i = 0; i < this.player.length; i++) {
            if (this.player[i].getHandValue() == valMax) {
                this.player[i].setIsTie(true);
            }
        }

        int highIndex = -1;
        int high = 0;
        if (tie > 1) {
            Log.i("XXXXXXXXXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXXXXXXXXXXXXX");
            tie = 0;
            if (valMax == 0 || valMax == 4 || valMax == 5 || valMax == 8) {
                for (i = 0; i < this.player.length; i++) {
                    if (this.player[i].getIsTie() == true) {
                        high = this.player[i].getHand()[0].getRank().getRankNum();
                        int cardIndex = 1;
                        for (cardIndex = 1; cardIndex < 5; cardIndex++) {
                            if (high < this.player[i].getHand()[cardIndex].getRank().getRankNum()) {
                                high = this.player[i].getHand()[cardIndex].getRank().getRankNum();
                                valMaxInd = cardIndex;
                            }
                        }
                    }
                }
            } else {
                for (i = 0; i < this.player.length; i++) {
                    if (this.player[i].getIsTie() == true) {
                        if (findPair(this.player[i].getCard1(), this.player[i].getCard2(), this.player[i].getCard3(), this.player[i].getCard4(), this.player[i].getCard5()) > high) {
                            high = findPair(this.player[i].getCard1(), this.player[i].getCard2(), this.player[i].getCard3(), this.player[i].getCard4(), this.player[i].getCard5());
                            valMaxInd = i;
                        }
                    }
                }
            }
        }

        try{
            Thread.sleep(8000);
        } catch (InterruptedException ie){
            //we don't care
        }

        return valMaxInd;
    }

    //takes 5 cards, returns int corresponding to hand type(straight flush, 4 of a kind, etc.), the higher the better
    public int handEvaluate(Card card1, Card card2, Card card3, Card card4, Card card5){
        int pairCount = 0;

        //checks to see how many value matches between a card and any card later, iterates pairCount if it catches a match
        if(card1.getRank().getRankNum()== card2.getRank().getRankNum()){
            pairCount++;
        }
        if(card1.getRank().getRankNum()== card3.getRank().getRankNum()){
            pairCount++;
        }
        if(card1.getRank().getRankNum()== card4.getRank().getRankNum()){
            pairCount++;
        }
        if(card1.getRank().getRankNum()== card5.getRank().getRankNum()){
            pairCount++;
        }
        if(card1.getRank().getRankNum()== card5.getRank().getRankNum()){
            pairCount++;
        }
        if(card2.getRank().getRankNum()== card3.getRank().getRankNum()){
            pairCount++;
        }
        if(card2.getRank().getRankNum()== card4.getRank().getRankNum()){
            pairCount++;
        }
        if(card2.getRank().getRankNum()== card5.getRank().getRankNum()){
            pairCount++;
        }
        if(card3.getRank().getRankNum()== card4.getRank().getRankNum()){
            pairCount++;
        }
        if(card3.getRank().getRankNum()== card5.getRank().getRankNum()){
            pairCount++;
        }
        if(card4.getRank().getRankNum()== card5.getRank().getRankNum()){
            pairCount++;
        }

        //1 match means 1 pair
        if(pairCount == 1){
            return 1;
        }
        //2 matches means 2 pairs
        if(pairCount == 2){
            return 2;
        }
        //3 matches means 3 of a kind
        if(pairCount == 3){
            return 3;
        }
        //4 matches means full house
        if(pairCount == 4){
            return 6;
        }
        //6 matches means 4 of a kind
        if(pairCount == 6){
            return 7;
        }

        boolean isStraight = false;
        boolean isFlush = false;
        int cardMin = 20;

        //finds minimum card number
        if(card1.getRank().getRankNum() < cardMin){
            cardMin = card1.getRank().getRankNum();
        }
        if(card2.getRank().getRankNum() < cardMin){
            cardMin = card2.getRank().getRankNum();
        }
        if(card3.getRank().getRankNum() < cardMin){
            cardMin = card3.getRank().getRankNum();
        }
        if(card4.getRank().getRankNum() < cardMin){
            cardMin = card4.getRank().getRankNum();
        }
        if(card5.getRank().getRankNum() < cardMin){
            cardMin = card5.getRank().getRankNum();
        }

        //checks all suits are the same
        if(card1.getSuit().getSuitNum() == card2.getSuit().getSuitNum() && card2.getSuit().getSuitNum() == card3.getSuit().getSuitNum() && card3.getSuit().getSuitNum() == card4.getSuit().getSuitNum() && card4.getSuit().getSuitNum() == card5.getSuit().getSuitNum()){
            isFlush = true;
        }


        //checks if there is 1 higher than min value of hand, then 2, then 3, then 4
        if((card1.getRank().getRankNum()== cardMin + 1 || card2.getRank().getRankNum()== cardMin + 1 || card3.getRank().getRankNum()== cardMin + 1 || card4.getRank().getRankNum()== cardMin + 1 || card5.getRank().getRankNum()== cardMin + 1) && (card1.getRank().getRankNum()== cardMin + 2 || card2.getRank().getRankNum()== cardMin + 2 || card3.getRank().getRankNum()== cardMin + 2 || card4.getRank().getRankNum()== cardMin + 2 || card5.getRank().getRankNum()== cardMin + 2) && (card1.getRank().getRankNum()== cardMin + 3 || card2.getRank().getRankNum()== cardMin + 3 || card3.getRank().getRankNum()== cardMin + 3 || card4.getRank().getRankNum()== cardMin + 3 || card5.getRank().getRankNum()== cardMin + 3) && (card1.getRank().getRankNum()== cardMin + 4 || card2.getRank().getRankNum()== cardMin + 4 || card3.getRank().getRankNum()== cardMin + 4 || card4.getRank().getRankNum()== cardMin + 4 || card5.getRank().getRankNum()== cardMin + 4)){
            isStraight = true;
        }
        //straight flush
        if(isStraight && isFlush){
            return 8;
        }
        //flush
        if(isFlush){
            return 5;
        }
        //straight
        if(isStraight){
            return 4;
        }
        //no matches, therefore high card
        return 0;


    }



    public int getPot() {
        return pot;
    }

    public int getMinBet() {
        return minBet;
    }

    public int getCurTurn() {
        return curTurn;
    }

    public int getCurRound() {
        return this.curRound;
    }

    public Card getCardMid(int index) {
        return this.cardMid[index];
    }

    public edu.up.cs301.texasHoldem.player getPlayer(int index) {
        return this.player[index];
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public void setMinBet(int minBet) {
        this.minBet = minBet;
    }

    public void setCurTurn(int curTurn) {
        this.curTurn = curTurn;
    }

    public void setCurRound(int curRound) {
        this.curRound = curRound;
    }

    public void setCardMid(int index, Card cardSource) {
        Rank newRank = cardSource.getRank();
        Suit newSuit = cardSource.getSuit();
        this.cardMid[index] = new Card(newRank, newSuit);
    }

    public void setPlayer(player player, int index) {
        this.player[index] = player;
    }


    public void setDeck(Deck deck) {
        this.deck = deck;
    }



    public Deck getDeck() {
        return deck;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public void setLatestBet(int x) { this.latestBet = x; }

    public int getLatestBet() { return latestBet; }

    public int getCurrentCustomBet() {
        return currentCustomBet;
    }

    public void setCurrentCustomBet(int currentCustomBet) {
        this.currentCustomBet = currentCustomBet;
    }


    public void setRecieveIndex(int idx){ this.recieveIndex = idx;}

    public int getRecieveIndex(){ return this.recieveIndex;}

    public void setSidePot(boolean newBool){this.sidePot = newBool;}

    public boolean getSidePot(){return this.sidePot;}

    public void setNumActive(int num){this.numActive = num;}

    public int getNumActive(){return this.numActive;}

    public int findPair(Card card1, Card card2, Card card3, Card card4, Card card5){
        if(card1.getRank().getRankNum() == card2.getRank().getRankNum()){
            return card1.getRank().getRankNum();
        }
        if(card1.getRank().getRankNum() == card3.getRank().getRankNum()){
            return card1.getRank().getRankNum();
        }
        if(card1.getRank().getRankNum() == card4.getRank().getRankNum()){
            return card1.getRank().getRankNum();
        }
        if(card1.getRank().getRankNum() == card5.getRank().getRankNum()){
            return card1.getRank().getRankNum();
        }
        if(card2.getRank().getRankNum() == card3.getRank().getRankNum()){
            return card2.getRank().getRankNum();
        }
        if(card2.getRank().getRankNum() == card4.getRank().getRankNum()){
            return card2.getRank().getRankNum();
        }
        if(card2.getRank().getRankNum() == card5.getRank().getRankNum()){
            return card2.getRank().getRankNum();
        }
        if(card3.getRank().getRankNum() == card4.getRank().getRankNum()){
            return card3.getRank().getRankNum();
        }
        if(card3.getRank().getRankNum() == card5.getRank().getRankNum()){
            return card3.getRank().getRankNum();
        }
        return card4.getRank().getRankNum();
    }
}
