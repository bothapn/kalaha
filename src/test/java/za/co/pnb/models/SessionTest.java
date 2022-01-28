package za.co.pnb.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import za.co.pnb.KalahaApplication;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionTest {

    @Test
    @DisplayName("Test - I may play again")
    void iPlayAgain() {
        Session session = new Session(UUID.randomUUID().toString());

        session.startPlay(PlayerType.NORTH, 1);
        session.startPlay(PlayerType.SOUTH, 1);
        session.startPlay(PlayerType.NORTH, 2);

        assertEquals(PlayerType.NORTH, session.getNext());
        assertTrue(session.getPlayer1().getHouse(1).isEmpty());
        assertTrue(session.getPlayer1().getHouse(2).isEmpty());
        assertTrue(session.getPlayer2().getHouse(1).isEmpty());
        assertEquals(1, session.getPlayer1().getPit());
        assertEquals(0, session.getPlayer2().getPit());
    }

    @Test
    @DisplayName("Test - Play sequence")
    void playSequence() {
        Session session = new Session(UUID.randomUUID().toString());
        session.startPlay(PlayerType.NORTH, 1);
        session.startPlay(PlayerType.SOUTH,1);
        session.startPlay(PlayerType.NORTH, 2);
        session.startPlay(PlayerType.NORTH, 3);

        assertTrue(session.getPlayer1().getHouse(1).isEmpty());
        assertTrue(session.getPlayer1().getHouse(2).isEmpty());
        assertTrue(session.getPlayer1().getHouse(3).isEmpty());
        assertEquals(1, session.getPlayer2().getHouse(1).seedsAvailable());
        assertEquals(2, session.getPlayer1().getPit());

        session.startPlay(PlayerType.SOUTH,2);
        session.startPlay(PlayerType.NORTH, 4);
        session.startPlay(PlayerType.SOUTH,3);

        assertTrue(session.getPlayer1().getHouse(4).isEmpty());
        assertTrue(session.getPlayer2().getHouse(3).isEmpty());
        assertEquals(3, session.getPlayer1().getPit());
        assertEquals(2, session.getPlayer2().getPit());

        session.startPlay(PlayerType.NORTH, 5);
        session.startPlay(PlayerType.SOUTH,4);
        session.startPlay(PlayerType.NORTH, 6);
        session.startPlay(PlayerType.SOUTH,5);

        assertTrue(session.getPlayer2().getHouse(5).isEmpty());
        assertEquals(5, session.getPlayer1().getPit());
        assertEquals(4, session.getPlayer2().getPit());
        assertEquals(5, session.getPlayer1().getHouse(1).seedsAvailable());
        assertEquals(10, session.getPlayer2().getHouse(6).seedsAvailable());

        session.startPlay(PlayerType.NORTH, 1);
        session.startPlay(PlayerType.SOUTH,6);
        session.startPlay(PlayerType.NORTH, 3);
        session.startPlay(PlayerType.SOUTH,4);

        assertTrue(session.getPlayer1().getHouse(2).isEmpty());
        assertTrue(session.getPlayer1().getHouse(3).isEmpty());
        assertTrue(session.getPlayer2().getHouse(4).isEmpty());
        assertTrue(session.getPlayer2().getHouse(5).isEmpty());
        assertTrue(session.getPlayer2().getHouse(6).isEmpty());
        assertEquals(6, session.getPlayer1().getPit());
        assertEquals(12, session.getPlayer2().getPit());
        assertEquals(7, session.getPlayer2().getHouse(1).seedsAvailable());
        assertEquals(3, session.getPlayer2().getHouse(3).seedsAvailable());
        assertEquals(5, session.getPlayer1().getHouse(4).seedsAvailable());
    }
}