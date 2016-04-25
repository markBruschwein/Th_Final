package edu.up.cs301.texasHoldem;

import android.util.Log;
import android.widget.TextView;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * This class will make moves for the random computer players throughout the game
 *
 * @author Mark Bruschwein
 * @author Luke McManamon
 * @author Hugh McGlynn
 *
 * @version 3/30/2016.
 */
public class THRandomComputerPlayer extends GameComputerPlayer{
    THState state; // Current state that the GUI will use to update
    GameMainActivity activity;  // Will be used to know the current activity
    TextView comPlayerMoney;

    int compIndex = -1;

    /**
     * The THRandomComputer Player constructor that creates all the instance variables
     */
    public THRandomComputerPlayer(String playerName){
        super(playerName);


    }


    /**
     * This method will update the gui display based on canges in the gaemState
     */
    public void updateGUI()
    {
        //TODO unsure on this whole shinanigan
        comPlayerMoney.setText(state.getPlayer(compIndex).getMoney());
    }


    /**
     * This method will recieve new info about the game being updated
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if(!(info instanceof THState)){
            return; //Not an update of the gamestate so we dont care
        }




        state = (THState) info;

        // if the user is player slow down the AI
        if (state.getPlayer(0).getIsActive()) {

            sleep(3000);
        } else {
            sleep(500);
        }

        Random rand = new Random();

        int curMoney = this.state.getPlayer(compIndex).getMoney(); // will use this for determining how much to bet

        int randNum = rand.nextInt(10) + 1;

        int minBet = this.state.getMinBet();

        if (randNum == -1) {
            game.sendAction(new Fold(this));
        } else if (randNum > 1 && randNum < 10) {

            if (minBet > (curMoney + state.getPlayer(compIndex).getCurBet())) {
                game.sendAction(new Fold(this));
            } else if (minBet == 0) {
                game.sendAction(new Check(this));
            } else {
                game.sendAction(new Call(this));
            }
        } else if (randNum == 10) {
            game.sendAction(new Bet(this, -1));
        }


    }

    public void setCompIndex(int index){
        this.compIndex = index;
    }

    public int getCompIndex(){
        return this.compIndex;
    }
}