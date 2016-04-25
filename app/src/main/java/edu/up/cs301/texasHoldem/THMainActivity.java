package edu.up.cs301.texasHoldem;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * Texas Hold 'Em main activity
 *
 * @author Mark Bruschwein
 * @author Luke McManamon
 * @author Hugh McGlynn
 *
 * @version 3/30/2016.
 */
public class THMainActivity extends GameMainActivity {

    /**
     * This class will setup the default configuration of the game
     * @return
     */

    public static final int PORT_NUMBER = 4912;

    public GameConfig createDefaultConfig() {

        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        playerTypes.add(new GamePlayerType("Human Player") {
            public GamePlayer createPlayer(String name){ return new THHumanPlayer(name); }
        });
        playerTypes.add(new GamePlayerType("CPU Player (random)") {
            public GamePlayer createPlayer(String name) {

                return new THRandomComputerPlayer(name);
            }
        });
        playerTypes.add(new GamePlayerType("DoyleBot (The Brunsenator)") {
            public GamePlayer createPlayer(String name) {

                return new THSmartComputerPlayer(name);
            }
        });

        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 4, "Texas Hold 'Em", PORT_NUMBER);
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("The Brunsen Burner", 2);
        defaultConfig.addPlayer("CPU2", 1);
        defaultConfig.addPlayer("CPU3", 1);
        defaultConfig.setRemoteData("Guest", "", 1);

        return defaultConfig;
    }
/*
    public void onClick(View button) {


    }*/

    /**
     * Creates the local game that will be used
     *
     * @return
     */
    public THLocalGame createLocalGame() {

        return new THLocalGame();
    }
}
