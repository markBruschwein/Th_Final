package edu.up.cs301.texasHoldem;

import android.os.Message;
import android.util.Log;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.util.MessageBox;

/**
 * This class will update the GameState and send it to the players
 *
 * @author Mark Bruschwein
 * @author Hugh McGlynn
 * @author Luke McManamon
 *
 * @version 3/30/2016.
 */
public class THLocalGame extends LocalGame {

    THState currentState = null; // the game state that will be updated

    /**
     * constructor for THLocal Game
     *  creates a Local game with a new currentState
     *
     */
    public THLocalGame(){
        super();


    }


    /**
     * Takes the THState and sends it to the specified player
     * @param p
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        if(currentState == null){
            currentState = new THState(super.players.length);
        }

        int i;
        for(i = 0; i < super.players.length; i++){
            if(super.players[i] instanceof THRandomComputerPlayer ){
                THRandomComputerPlayer indexSetter = (THRandomComputerPlayer)super.players[i];
                if(indexSetter.getCompIndex() == -1) {
                    indexSetter.setCompIndex(i);
                }
            }
            else if(super.players[i] instanceof THSmartComputerPlayer ){
                THSmartComputerPlayer indexSetter = (THSmartComputerPlayer)super.players[i];
                if(indexSetter.getCompIndex() == -1) {
                    indexSetter.setCompIndex(i);
                }
            }
            else if(super.players[i] instanceof THHumanPlayer){
                THHumanPlayer indexSetter = (THHumanPlayer)super.players[i];
                if(indexSetter.getHumanIndex() == -1) {
                    indexSetter.setHumanIndex(i);
                }
            }
        }

        Log.i("NumActive", "" + currentState.getNumActive());

        int recieveIdx = getPlayerIdx(p);
        currentState.setRecieveIndex(recieveIdx);
        THState update = new THState(currentState);
        p.sendInfo(update);
    }


    /**
     *  Determins if the specified player is allowed to make a move
     *
     */
    @Override
    protected boolean canMove(int playerIdx) {
        if(this.currentState.getCurTurn() == playerIdx){
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * Checks if the game is over, if so returns a message telling who won
     * @return
     */
    @Override
    protected String checkIfGameOver() {
        int counter = this.currentState.getPlayerNum();
        for(int i = 0; i < this.currentState.getPlayerNum(); i++) {
            if(this.currentState.getPlayer(i).getIsEliminated() ) {
                counter--;
            }
        }

        if( counter <= 1){

            //TODO : make a game over message
            return "We have a winner!";
        }

        return null;
    }


    /**
     * Takes an action and updates the Game State accordingly if it is a legal move
     * @param action
     * 			The move that the player has sent to the game
     * @return
     */
    @Override
    protected boolean makeMove(GameAction action) {
        int playerIdx = getPlayerIdx(action.getPlayer());

        /**
         * if the bet action is from the player whose turn it is then perform it
         */
        if (playerIdx == currentState.getCurTurn()) {

            if(currentState.getCurRound() == 4){
                currentState.winPot(currentState.handWinner());

            }
            if (action instanceof Bet) {
                Bet newBet = (Bet)action;
                currentState.bet(newBet.getHowMuch());

                return true;

            } else if (action instanceof Call) {
                currentState.callBet();

                return  true;

            } else if (action instanceof Check){
                currentState.check();

                return true;
            } else if (action instanceof Fold){
                currentState.fold();

                return true;
            }


        }
        return false;
    }
}
