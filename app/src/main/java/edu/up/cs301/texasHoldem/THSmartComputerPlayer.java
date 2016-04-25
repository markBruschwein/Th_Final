package edu.up.cs301.texasHoldem;

import android.widget.TextView;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * This class will make moves for the smart computer players throughout the game
 *
 * @author Mark Bruschwein
 * @author Luke McManamon
 * @author Hugh McGlynn
 *
 * @version 3/30/2016.
 */

/**
 * This class will make moves for the smart computer players throughout the game
 *
 * @author Mark Bruschwein
 * @author Luke McManamon
 * @author Hugh McGlynn
 *
 * @version 3/30/2016.
 */

public class THSmartComputerPlayer extends GameComputerPlayer {

    THState state; // Current state that the GUI will use to update
    GameMainActivity activity;  // Will be used to know the current activity
    TextView comPlayerMoney;
    player playerInfo;
    int compIndex = -1;

    /**
     * The THRandomComputer Player constructor that creates all the instance variables
     */
    public THSmartComputerPlayer(String playerName){
        super(playerName);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if(!(info instanceof THState)){
            return; //Not an update of the gamestate so we dont care
        }

        state = (THState) info;

        if (state.getPlayer(0).getIsActive()) {
            sleep(3000);
        } else {
            sleep(500);
        }

        int confidence = 5;
        Random rand = new Random();
        if(this.state.getCurRound() == 0) {
            if (this.state.getPlayer(compIndex).getCard1().getRank().getRankNum() == this.state.getPlayer(compIndex).getCard2().getRank().getRankNum()) {
                confidence = confidence + 10;
            } else if (this.state.getPlayer(compIndex).getCard1().getSuit().getSuitNum() == this.state.getPlayer(compIndex).getCard2().getSuit().getSuitNum()) {
                confidence = confidence + 4;
            } else if (this.state.getPlayer(compIndex).getCard1().getRank().getRankNum() - this.state.getPlayer(compIndex).getCard2().getRank().getRankNum() <= 4) {
                confidence = confidence + 3;
            }

            confidence = rand.nextInt(11) + confidence - 5;
        }else if(this.state.getCurRound() == 1){
            int handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(1),this.state.getCardMid(2));
            confidence = confidence + handValue;
            confidence = confidence + rand.nextInt(16)-8;
        }else if(this.state.getCurRound() == 2){
            int handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(1),this.state.getCardMid(2));
            if(this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(1),this.state.getCardMid(3)) > handValue){
                handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(1),this.state.getCardMid(3));
            }
            if(this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(2),this.state.getCardMid(3)) > handValue){
                handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(2),this.state.getCardMid(3));
            }
            if (this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(1), this.state.getCardMid(2), this.state.getCardMid(3)) > handValue) {
                handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(1), this.state.getCardMid(2),this.state.getCardMid(3));
            }
            confidence = confidence + handValue;
            confidence = confidence + rand.nextInt(16)-8;
        }
        else if(this.state.getCurRound() == 3){
            int handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(1),this.state.getCardMid(2));
            if (this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(1),this.state.getCardMid(3)) > handValue){
                handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(1),this.state.getCardMid(3));
            }
            if(this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(1),this.state.getCardMid(4)) > handValue){
                handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(1),this.state.getCardMid(4));
            }
            if(this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(2),this.state.getCardMid(3)) > handValue){
                handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(2),this.state.getCardMid(3));
            }
            if(this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(2),this.state.getCardMid(4)) > handValue){
                handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(2),this.state.getCardMid(4));
            }
            if(this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(3),this.state.getCardMid(4)) > handValue){
                handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(0), this.state.getCardMid(1),this.state.getCardMid(2));
            }
            if(this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(1), this.state.getCardMid(2), this.state.getCardMid(3)) > handValue){
                handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(1), this.state.getCardMid(2),this.state.getCardMid(3));
            }
            if(this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(1), this.state.getCardMid(2),this.state.getCardMid(4)) > handValue){
                handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(1), this.state.getCardMid(2),this.state.getCardMid(4));
            }
            if(this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(1), this.state.getCardMid(3),this.state.getCardMid(4)) > handValue){
                handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(1), this.state.getCardMid(3),this.state.getCardMid(4));
            }
            if(this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(2), this.state.getCardMid(3),this.state.getCardMid(4)) > handValue){
                handValue = this.state.handEvaluate(this.state.getPlayer(compIndex).getCard1(), this.state.getPlayer(compIndex).getCard2(), this.state.getCardMid(2), this.state.getCardMid(3),this.state.getCardMid(4));
            }
            confidence = confidence + handValue;
            confidence = confidence + rand.nextInt(16)-8;
        }
        if (this.state.getMinBet() - this.state.getPlayer(compIndex).getCurBet() < this.state.getPlayer(compIndex).getMoney()*Math.pow(.05*confidence,2) && this.state.getPlayer(compIndex).getNumBet() == 0){
            game.sendAction(new Bet(this, (int)(this.state.getPlayer(compIndex).getMoney()*Math.pow(.05*confidence,2) + this.state.getPlayer(compIndex).getCurBet() - this.state.getMinBet())));
        }else if(this.state.getMinBet() - this.state.getPlayer(compIndex).getCurBet() < this.state.getPlayer(compIndex).getMoney()*(.2 + .05*confidence)){
            game.sendAction(new Call(this));
        }else{
            game.sendAction(new Fold(this));
        }
    }


    /**
     * this method will take into account the CPU's moves and update the GUi accordingly
     */

    /**
     * This method will update the gui display based on canges in the gaemState
     */
    public void updateGUI()
    {
        comPlayerMoney.setText(state.getPlayer(compIndex).getMoney());
    }

    public void setCompIndex(int index){
        this.compIndex = index;
    }

    public int getCompIndex(){
        return this.compIndex;
    }

}