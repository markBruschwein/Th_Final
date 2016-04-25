package edu.up.cs301.texasHoldem;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.config.GameConfig;

/**
 * Bet action class
 * This class will tell the game when the user has bet in any form
 *
 * @author Mark Bruschwein
 * @author Luke McManamon
 * @author Hugh McGlynn
 *
 * @version 3/30/2016.
 */
public class Bet extends GameAction {
    private static final long serialVersionUID = 4209179064333132454L;

    int howMuch; // the amount of money tobet
    /**
     *  Constructor for the bet class
     *
     * @param player
     */
    public Bet(GamePlayer player, int money){

        super(player);
        this.howMuch = money;
    }


    /*
     * Tells the game that the user has placed a bet of their amount of choice
     */
    public boolean isBet(GamePlayer player) {
        return true;
    }

    public int getHowMuch(){ return this.howMuch;}

}
