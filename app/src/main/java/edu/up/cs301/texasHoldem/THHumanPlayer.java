package edu.up.cs301.texasHoldem;

import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.Image;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import edu.up.cs301.animation.AnimationSurface;
import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * This class will update the Human Player GUI and allow the player to make moves in gameplay
 *
 * @author Mark Bruschwein
 * @author Luke McManamon
 * @author Hugh McGlynn
 *
 * @version 3/30/2016.
 */
public class THHumanPlayer extends GameHumanPlayer implements TextView.OnClickListener, SeekBar.OnSeekBarChangeListener{

    /*
     External Citation
        Date: 4/12/2016
        Problem: Need to draw cards on the gui

        Resource: The slap jack game provided on moodle
     */

    //TODO this is a reminder for what you added since 4/12 @ 6:00
    private final static float CARD_HEIGHT_PERCENT = 98; // height of a card
    private final static float CARD_WIDTH_PERCENT = 95; // width of a card
    private final static float LEFT_BORDER_PERCENT = 1; // width of left border
    private final static float RIGHT_BORDER_PERCENT = 1; // width of right border
    private final static float VERTICAL_BORDER_PERCENT = 1; // width of top/bottom borders

    private THState state = null; // Current state that the GUI will use to update
    protected GameMainActivity activity;  // Will be used to know the current activity
    protected TextView playerMoney;


    protected int humanIndex = -1;

    protected ImageView cp1Card1;
    protected ImageView cp1Card2;
    protected ImageView cp2Card1;
    protected ImageView cp2Card2;
    protected ImageView cp3Card1;
    protected ImageView cp3Card2;

    private int backgroundColor;
    ///////////////////////////////////////////////////////////////////////////
    //textviews for everyone's current money
    protected TextView CPU1Money =  null;
    protected TextView CPU2Money = null;
    protected TextView CPU3Money = null;
    protected TextView userMoney = null;
    protected TextView potValue = null;

    protected TextView cpu1Name = null;
    protected TextView cpu2Name = null;
    protected TextView cpu3Name = null;
    protected TextView userDisplayName = null;

    //Text views for the cards in the middle
    protected ImageView midCard1;
    protected ImageView midCard2;
    protected ImageView midCard3;
    protected ImageView midCard4;
    protected ImageView midCard5;

    protected ImageView playerCard1;
    protected ImageView playerCard2;


    //instance variables for custom betting
    protected SeekBar betSeekBar = null;
    protected TextView betTextView = null;
    protected Button customBetButton;

    protected Button callBetButton;
    protected Button checkButton;
    protected Button newGameButton;
    protected Button foldButton;





    //two dimensional array used to draw the cards
    private static int[][] myResIdx = {

            {
                    R.drawable.card_2s, R.drawable.card_3s,
                    R.drawable.card_4s, R.drawable.card_5s, R.drawable.card_6s,
                    R.drawable.card_7s, R.drawable.card_8s, R.drawable.card_9s,
                    R.drawable.card_ts, R.drawable.card_js, R.drawable.card_qs,
                    R.drawable.card_ks, R.drawable.card_as,
            },

            {
                    R.drawable.card_2h, R.drawable.card_3h,
                    R.drawable.card_4h, R.drawable.card_5h, R.drawable.card_6h,
                    R.drawable.card_7h, R.drawable.card_8h, R.drawable.card_9h,
                    R.drawable.card_th, R.drawable.card_jh, R.drawable.card_qh,
                    R.drawable.card_kh, R.drawable.card_ah,
            },

            {
                    R.drawable.card_2c, R.drawable.card_3c,
                    R.drawable.card_4c, R.drawable.card_5c, R.drawable.card_6c,
                    R.drawable.card_7c, R.drawable.card_8c, R.drawable.card_9c,
                    R.drawable.card_tc, R.drawable.card_jc, R.drawable.card_qc,
                    R.drawable.card_kc, R.drawable.card_ac,
            },

            {
                    R.drawable.card_2d, R.drawable.card_3d,
                    R.drawable.card_4d, R.drawable.card_5d, R.drawable.card_6d,
                    R.drawable.card_7d, R.drawable.card_8d, R.drawable.card_9d,
                    R.drawable.card_td, R.drawable.card_jd, R.drawable.card_qd,
                    R.drawable.card_kd, R.drawable.card_ad,
            },

    };













    /**
     * The THHuman Player constructor that creates all the instance variables
     * @param name the player's name
     *
     */
    public THHumanPlayer(String name){
        super(name);

        //TODO make sure this makes it to the final version


        backgroundColor = Color.BLACK;
    }


    /**
     * This method will recieve new info about the game being updated
     */
    public void receiveInfo(GameInfo gameInfo){

        if(gameInfo instanceof IllegalMoveInfo || gameInfo instanceof NotYourTurnInfo){
            //do nothing
        }
        else if(!(gameInfo instanceof THState)){
            return;
        }
        else{
            this.state = (THState)gameInfo;

            updateDisplay();
        }



    }


    /**
     * this method will take into account the current activity and update the GUi accordingly
     */
    public void setAsGui(GameMainActivity mainActivity){

        activity = mainActivity;
        mainActivity.setContentView(R.layout.th_human_player);
        Card.initImages(activity);


        mainActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // initialize the player's cards image views
        this.cp1Card1 = (ImageView)mainActivity.findViewById(R.id.cp1Card1);
        this.cp1Card2 = (ImageView)mainActivity.findViewById(R.id.cp1Card2);
        this.cp2Card1 = (ImageView)mainActivity.findViewById(R.id.cp2Card1);
        this.cp2Card2 = (ImageView)mainActivity.findViewById(R.id.cp2Card2);
        this.cp3Card1 = (ImageView)mainActivity.findViewById(R.id.cp3Card1);
        this.cp3Card2 = (ImageView)mainActivity.findViewById(R.id.cp3Card2);

        this.potValue = (TextView)mainActivity.findViewById(R.id.potValue);

        //initialize midcard text views

        this.midCard1 = (ImageView)mainActivity.findViewById(R.id.midCard1);
        this.midCard2 = (ImageView)mainActivity.findViewById(R.id.midCard2);
        this.midCard3 = (ImageView)mainActivity.findViewById(R.id.midCard3);
        this.midCard4 = (ImageView)mainActivity.findViewById(R.id.midCard4);
        this.midCard5 = (ImageView)mainActivity.findViewById(R.id.midCard5);


        this.playerCard1 = (ImageView)mainActivity.findViewById(R.id.playerCard1);
        this.playerCard2 = (ImageView)mainActivity.findViewById(R.id.playerCard2);

        this.userDisplayName = (TextView)mainActivity.findViewById(R.id.userDisplayName);
        this.cpu1Name = (TextView)mainActivity.findViewById(R.id.cpu1Name);
        this.cpu2Name = (TextView)mainActivity.findViewById(R.id.cpu2Name);
        this.cpu3Name = (TextView)mainActivity.findViewById(R.id.cpu3Name);

        //everyone's money
        CPU1Money = (TextView)activity.findViewById(R.id.CPU1Money);
        CPU2Money = (TextView)activity.findViewById(R.id.CPU2Money);
        CPU3Money = (TextView)activity.findViewById(R.id.CPU3Money);
        userMoney = (TextView)activity.findViewById(R.id.userMoney);


        betSeekBar = (SeekBar)activity.findViewById(R.id.betSeekBar);
        betTextView = (TextView)activity.findViewById(R.id.betTextView);
        customBetButton = (Button)activity.findViewById(R.id.customBetButton);
        callBetButton = (Button)activity.findViewById(R.id.callBetButton);
        checkButton = (Button)activity.findViewById(R.id.checkButton);
        newGameButton = (Button)activity.findViewById(R.id.newGameButton);
        foldButton = (Button)activity.findViewById(R.id.foldButton);

        //setting listeners

        betSeekBar.setOnSeekBarChangeListener(this);

        callBetButton.setOnClickListener(this);

        checkButton.setOnClickListener(this);

        foldButton.setOnClickListener(this);

        newGameButton.setOnClickListener(this);

        customBetButton.setOnClickListener(this);

        if (state != null) {
            receiveInfo(state);
        }
    }



    /**
     * This method updates the human player gui display
     */
    public void updateDisplay(){

        Log.i("Number of players", "" + state.getPlayerNum());
        int totalPlayers = super.allPlayerNames.length;
        humanIndex = state.getRecieveIndex();

        Log.i("You're inedx", "" + humanIndex);

        if ( humanIndex != -1) {

            // These indexs will be used for displaying the proper positions to network players
            int cpu1Index = -1;
            int cpu2Index = -1;
            int cpu3Index = -1;

            if (humanIndex == 0) {
                if (totalPlayers == 4) {
                    cpu1Index = 1;
                    cpu2Index = 2;
                    cpu3Index = 3;
                } else if (totalPlayers == 3) {
                    cpu1Index = 1;
                    cpu2Index = 2;
                } else {
                    cpu1Index = 1;
                }
            } else if (humanIndex == 1) {
                if (totalPlayers == 4) {
                    cpu1Index = 2;
                    cpu2Index = 3;
                    cpu3Index = 0;
                } else if (totalPlayers == 3) {
                    cpu1Index = 2;
                    cpu2Index = 0;
                } else {
                    cpu1Index = 0;
                }
            } else if (humanIndex == 2) {
                if (totalPlayers == 4) {
                    cpu1Index = 3;
                    cpu2Index = 0;
                    cpu3Index = 1;
                } else if (totalPlayers == 3) {
                    cpu1Index = 0;
                    cpu2Index = 1;
                }
            } else if (humanIndex == 3) {
                cpu1Index = 0;
                cpu2Index = 1;
                cpu3Index = 2;
            }

            Log.i("cpu1index", "" + cpu1Index);
            Log.i("cpu2index", "" + cpu1Index);
            Log.i("cpu3index", "" + cpu1Index);


            if (cpu1Index != -1) {
                this.cpu1Name.setText(super.allPlayerNames[cpu1Index]);
            }

            if (cpu2Index != -1) {
                this.cpu2Name.setText(super.allPlayerNames[cpu2Index]);
            }

            if (cpu3Index != -1) {
                this.cpu3Name.setText(super.allPlayerNames[cpu3Index]);
            }

            if (humanIndex != -1) {
                this.userDisplayName.setText(super.allPlayerNames[humanIndex]);
            }


            //TODO changed for different amount of players mod

            int cp1Card1S = (state.getPlayer(cpu1Index).getCard1().getSuit().getSuitNum() - 1);
            int cp1Card1R = (state.getPlayer(cpu1Index).getCard1().getRank().getRankNum() - 2);

            int cp1Card2S = (state.getPlayer(cpu1Index).getCard2().getSuit().getSuitNum() - 1);
            int cp1Card2R = (state.getPlayer(cpu1Index).getCard2().getRank().getRankNum() - 2);

            int cp2Card1S = 0;
            int cp2Card1R = 0;
            int cp2Card2S = 0;
            int cp2Card2R = 0;

            int cp3Card1S = 0;
            int cp3Card1R = 0;
            int cp3Card2S = 0;
            int cp3Card2R = 0;

            if (state.getPlayerNum() >= 3) { //make sure that was dont make cards for players who dont exist
                cp2Card1S = (state.getPlayer(cpu2Index).getCard1().getSuit().getSuitNum() - 1);
                cp2Card1R = (state.getPlayer(cpu2Index).getCard1().getRank().getRankNum() - 2);

                cp2Card2S = (state.getPlayer(cpu2Index).getCard2().getSuit().getSuitNum() - 1);
                cp2Card2R = (state.getPlayer(cpu2Index).getCard2().getRank().getRankNum() - 2);
            }

            if (state.getPlayerNum() == 4) { //make sure that was dont make cards for players who dont exist
                cp3Card1S = (state.getPlayer(cpu3Index).getCard1().getSuit().getSuitNum() - 1);
                cp3Card1R = (state.getPlayer(cpu3Index).getCard1().getRank().getRankNum() - 2);

                cp3Card2S = (state.getPlayer(cpu3Index).getCard2().getSuit().getSuitNum() - 1);
                cp3Card2R = (state.getPlayer(cpu3Index).getCard2().getRank().getRankNum() - 2);
            }





             /*
                 External Citation
                    Date: 4/12/2016
                    Problem: Needed a picture for regular card backs

                    Resource: http://opengameart.org/content/colorful-poker-card-back
            */

            /*
                 External Citation
                    Date: 4/12/2016
                    Problem: Needed a picture for folded card backs

                    Resource: http://opengameart.org/content/colorful-poker-card-back
            */



            //display card values for cp players 1 - 3
            if (state.getCurRound() > 3) {
                cp1Card1.setImageResource(myResIdx[cp1Card1S][cp1Card1R]);
                cp1Card2.setImageResource(myResIdx[cp1Card2S][cp1Card2R]);

                if (state.getPlayerNum() >= 3) {    // only display the cards if they exist
                    cp2Card1.setImageResource(myResIdx[cp2Card1S][cp2Card1R]);
                    cp2Card2.setImageResource(myResIdx[cp2Card2S][cp2Card2R]);
                }

                if (state.getPlayerNum() == 4) {     // only display the cards if they exist
                    cp3Card1.setImageResource(myResIdx[cp3Card1S][cp3Card1R]);
                    cp3Card2.setImageResource(myResIdx[cp3Card2S][cp3Card2R]);
                }



            } else {
                //if any of the players have folded
                if (!state.getPlayer(cpu1Index).getIsActive()) {
                    cp1Card1.setImageResource(R.mipmap.fold_back);
                    cp1Card2.setImageResource(R.mipmap.fold_back);
                } else {
                    cp1Card1.setImageResource(R.mipmap.card_back);
                    cp1Card2.setImageResource(R.mipmap.card_back);
                }

                if (state.getPlayerNum() >= 3) {
                    if (!state.getPlayer(cpu2Index).getIsActive()) {
                        cp2Card1.setImageResource(R.mipmap.fold_back);
                        cp2Card2.setImageResource(R.mipmap.fold_back);
                    } else {
                        cp2Card1.setImageResource(R.mipmap.card_back);
                        cp2Card2.setImageResource(R.mipmap.card_back);
                    }
                }

                if (state.getPlayerNum() == 4) {
                    if (!state.getPlayer(cpu3Index).getIsActive()) {
                        cp3Card1.setImageResource(R.mipmap.fold_back);
                        cp3Card2.setImageResource(R.mipmap.fold_back);
                    } else {
                        cp3Card1.setImageResource(R.mipmap.card_back);
                        cp3Card2.setImageResource(R.mipmap.card_back);
                    }
                }

            }


            //display midcards
            int midCard1S = (state.getCardMid(0).getSuit().getSuitNum() - 1);
            int midCard1R = (state.getCardMid(0).getRank().getRankNum() - 2);

            int midCard2S = (state.getCardMid(1).getSuit().getSuitNum() - 1);
            int midCard2R = (state.getCardMid(1).getRank().getRankNum() - 2);

            int midCard3S = (state.getCardMid(2).getSuit().getSuitNum() - 1);
            int midCard3R = (state.getCardMid(2).getRank().getRankNum() - 2);

            int midCard4S = (state.getCardMid(3).getSuit().getSuitNum() - 1);
            int midCard4R = (state.getCardMid(3).getRank().getRankNum() - 2);

            int midCard5S = (state.getCardMid(4).getSuit().getSuitNum() - 1);
            int midCard5R = (state.getCardMid(4).getRank().getRankNum() - 2);


            //make the text view background red for whoever's turn it is
            if (state.getCurTurn() == humanIndex) {

                userMoney.setBackgroundColor(Color.RED);
                CPU1Money.setBackgroundColor(Color.rgb(0, 102, 0));
                CPU2Money.setBackgroundColor(Color.rgb(0, 102, 0));
                CPU3Money.setBackgroundColor(Color.rgb(0, 102, 0));
            } else if (state.getCurTurn() == cpu1Index) {

                userMoney.setBackgroundColor(Color.rgb(0, 102, 0));
                CPU1Money.setBackgroundColor(Color.RED);
                CPU2Money.setBackgroundColor(Color.rgb(0, 102, 0));
                CPU3Money.setBackgroundColor(Color.rgb(0, 102, 0));
            } else if (state.getCurTurn() == cpu2Index) {

                userMoney.setBackgroundColor(Color.rgb(0, 102, 0));
                CPU1Money.setBackgroundColor(Color.rgb(0, 102, 0));
                CPU2Money.setBackgroundColor(Color.RED);
                CPU3Money.setBackgroundColor(Color.rgb(0, 102, 0));
            } else {

                userMoney.setBackgroundColor(Color.rgb(0, 102, 0));
                CPU1Money.setBackgroundColor(Color.rgb(0, 102, 0));
                CPU2Money.setBackgroundColor(Color.rgb(0, 102, 0));
                CPU3Money.setBackgroundColor(Color.RED);
            }


            // if it is at the correct round, draw the first three cards
            if (state.getCurRound() > 0) {
                midCard1.setImageResource(myResIdx[midCard1S][midCard1R]);
                midCard2.setImageResource(myResIdx[midCard2S][midCard2R]);
                midCard3.setImageResource(myResIdx[midCard3S][midCard3R]);
            } else    // else only draw the card backs
            {
                midCard1.setImageResource(R.mipmap.card_back);
                midCard2.setImageResource(R.mipmap.card_back);
                midCard3.setImageResource(R.mipmap.card_back);
            }

            // if it is the correct round, draws the fourth card in the middle pile
            if (state.getCurRound() > 1) {
                midCard4.setImageResource(myResIdx[midCard4S][midCard4R]);
            } else    // else draw cardback
            {
                midCard4.setImageResource(R.mipmap.card_back);
            }

            // same as above for the fifth card
            if (state.getCurRound() > 2) {
                midCard5.setImageResource(myResIdx[midCard5S][midCard5R]);
            } else {
                midCard5.setImageResource(R.mipmap.card_back);
            }


            //get player's cards rank and suit
            int pc1R = (state.getPlayer(humanIndex).getCard1().getRank().getRankNum() - 2);
            int pc1S = (state.getPlayer(humanIndex).getCard1().getSuit().getSuitNum() - 1);

            int pc2R = (state.getPlayer(humanIndex).getCard2().getRank().getRankNum() - 2);
            int pc2S = (state.getPlayer(humanIndex).getCard2().getSuit().getSuitNum() - 1);


            //if you fold for your cards will show blue backs
            if (state.getPlayer(humanIndex).getIsActive() == false) {
                playerCard1.setImageResource(R.mipmap.fold_back);
                playerCard2.setImageResource(R.mipmap.fold_back);
            } else {
                // users cards
                playerCard1.setImageResource(myResIdx[pc1S][pc1R]);
                playerCard2.setImageResource(myResIdx[pc2S][pc2R]);
            }

            CPU1Money.setText("Money: $" + state.getPlayer(cpu1Index).getMoney() + "\n Current Bet: $" + state.getPlayer(cpu1Index).getCurBet());
            if (state.getPlayerNum() >= 3) {
                CPU2Money.setText("Money: $" + state.getPlayer(cpu2Index).getMoney() + "\n Current Bet: $" + state.getPlayer(cpu2Index).getCurBet());
            }
            if (state.getPlayerNum() == 4) {
                CPU3Money.setText("Money: $" + state.getPlayer(cpu3Index).getMoney() + "\n Current Bet: $" + state.getPlayer(cpu3Index).getCurBet());
            }
            userMoney.setText("Money: $" + state.getPlayer(humanIndex).getMoney() + "\n Current Bet: $" + state.getPlayer(humanIndex).getCurBet());
            potValue.setText("Pot: $" + state.getPot() + "\n Highest Bet: $" + state.getMinBet());

            //betSeekBar.setMax(state.getPlayer(humanIndex).getMoney());
            //betSeekBar.setMax(this.state.getPlayer(humanIndex).getMoney() + this.state.getPlayer(humanIndex).getCurBet() - this.state.getMinBet());
            betSeekBar.setMax(this.state.getPlayer(humanIndex).getMoney());
        }

    } /* End updateDisplay() */


    /**
     * This method will handle the event that the buttons on the Gui are clicked
     */
    public void onClick(View button) {

        if(button.getId() == R.id.customBetButton){

                game.sendAction(new Bet(this, betSeekBar.getProgress()));

        }
        else if(button.getId() == R.id.callBetButton){

            game.sendAction(new Call(this));
        }
        else if(button.getId() == R.id.checkButton){

            game.sendAction(new Check(this));
        }
        else if(button.getId() == R.id.foldButton){

            game.sendAction(new Fold(this));
        }
        else if(button.getId() == R.id.newGameButton){

            System.exit(0);
        }

    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        //state.setCurrentCustomBet(betSeekBar.getProgress());
        betTextView.setText("" + betSeekBar.getProgress());
    }

    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    public void onStopTrackingTouch(SeekBar seekBar) {


    }



    public View getTopView(){
        return activity.findViewById(R.id.topGUILayout);
    }

    public TextView getPlayerMoney() {
        return playerMoney;
    }

    public void setPlayerMoney(TextView playerMoney) {
        this.playerMoney = playerMoney;
    }

    public GameMainActivity getActivity() {
        return activity;
    }

    public void setActivity(GameMainActivity activity) {
        this.activity = activity;
    }

    public THState getState() {
        return state;
    }

    public void setState(THState state) {
        this.state = state;
    }

    public void setHumanIndex(int newIndex){ this.humanIndex = newIndex;}

    public int getHumanIndex(){return this.humanIndex;}
}
