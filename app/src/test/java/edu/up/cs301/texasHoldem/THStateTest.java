package edu.up.cs301.texasHoldem;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bruschwe18 on 4/25/2016.
 */
public class THStateTest {

        @Test
        public void testChangeTurn() throws Exception {
            THState gs = new THState(4);
            gs.setCurTurn(0);
            gs.changeTurn();
            assertEquals(1, gs.getCurTurn());
            gs.setCurTurn(3);
            gs.changeTurn();
            assertEquals(0, gs.getCurTurn());
        }

        @Test
        public void testBet() throws Exception {
            THState gs = new THState(4);
            player Paul = new player();
            gs.setPlayer(Paul, 0);
            gs.getPlayer(0).setMoney(800);
            gs.setPot(300);
            gs.bet(140);
            assertEquals(660, gs.getPlayer(0).getMoney());
            assertEquals(440, gs.getPot());
        }

        @Test
        public void testWinPot() throws Exception {
            THState gs = new THState(4);
            player Paul1 = new player();
            player Paul2 = new player();
            player Paul3 = new player();
            player Paul4 = new player();
            gs.setPlayer(Paul1, 0);
            gs.setPlayer(Paul2, 1);
            gs.setPlayer(Paul3, 2);
            gs.setPlayer(Paul4, 3);
            gs.setPot(500);
            int prevMoney = gs.getPlayer(1).getMoney();
            gs.winPot(1);
            assertEquals((prevMoney + 500), gs.getPlayer(1).getMoney());
        }

        @Test
        public void testDealCards() throws Exception {
            THState gs = new THState(4);
            player Paul1 = new player();
            player Paul2 = new player();
            player Paul3 = new player();
            player Paul4 = new player();
            gs.setPlayer(Paul1, 0);
            gs.setPlayer(Paul2, 1);
            gs.setPlayer(Paul3, 2);
            gs.setPlayer(Paul4, 3);

            gs.dealCards();
            assertNotEquals(gs.getPlayer(0).getCard1(), null);
            assertNotEquals(gs.getPlayer(3).getCard2(), null);
            assertNotEquals(gs.getPlayer(0).getCard1(), gs.getPlayer(0).getCard2());
            assertNotEquals(gs.getCardMid(0), null);
            assertNotEquals(gs.getCardMid(4), null);

        }

        @Test
        public void testCheck() throws Exception {
            THState gs = new THState(4);
            gs.check();

            assertTrue(gs.getCurTurn() == 1);

            gs.setMinBet(50);
            gs.check();

            assertTrue(gs.getCurTurn() == 1);

        }

        @Test
        public void testFold() throws Exception {
            THState gs = new THState(2);
            player Paul1 = new player();
            player Paul2 = new player();
            gs.setPlayer(Paul1, 0);
            gs.setPlayer(Paul2, 1);

            gs.fold();
            assertFalse(gs.getPlayer(0).getIsActive());

            gs.fold();
            assertFalse(gs.getPlayer(1).getIsActive());

        }

        @Test
        public void testCallBet() throws Exception {
            THState gs = new THState(2);
            player Paul1 = new player();
            player Paul2 = new player();
            gs.setPlayer(Paul1, 0);
            gs.setPlayer(Paul2, 1);

            gs.setMinBet(50);
            gs.callBet();

            assertTrue(gs.getPot() == 50);
            assertTrue(gs.getPlayer(0).getMoney() == 950);

        }

        @Test
        public void testChangeCurRound() throws Exception {
            THState gs = new THState(2);
            gs.changeCurRound();
            assertTrue(gs.getCurRound() == 1);
        }

        @Test
        public void testHandWinner() throws Exception {
            //ran out of time
        }

        @Test
        public void testHandEvaluate() throws Exception {
            //ran out of time
        }

}