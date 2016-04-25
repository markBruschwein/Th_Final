package edu.up.cs301.texasHoldem;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by bruschwe18 on 4/11/2016.
 */
public class Check extends GameAction{
    private static final long serialVersionUID = 4209179064333345954L;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public Check(GamePlayer player) {
        super(player);
    }

    public boolean isCheck() {
        return true;
    }
}
