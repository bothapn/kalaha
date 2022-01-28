package za.co.pnb.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private String id;
    private Player player1 = new Player(PlayerType.NORTH);
    private Player player2 = new Player(PlayerType.SOUTH);
    private Player next;
    private Map<PlayerType, Integer> currentScore = new HashMap<>();

    public Session(String id) {
        this.id = id;
        next = player1;
        calculateScore();
    }

    public void startPlay(PlayerType playerType, int houseNumber) {
        if (!playerType.equals(next.getType()))
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "The next player is " + next.getType());
        if (player1.allEmpty() || player2.allEmpty())
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Game has ended");

        if (next.getType().equals(PlayerType.NORTH)) {
            if (doPlay(playerType, player1, player2, houseNumber, 0))
                next = player1;
            else
                next = player2;
        } else {
            if (doPlay(playerType, player2, player1, houseNumber, 0))
                next = player2;
            else
                next = player1;
        }
        calculateScore();
    }

    private boolean doPlay(PlayerType playingPlayer, Player player1, Player player2, int houseNumber, int transferSeeds) {
        Result result = new Result(true);
        if (playingPlayer.equals(player1.getType())) {
            if (transferSeeds > 0) {
                result = player1.receiveSeeds(transferSeeds, houseNumber, player1, player2);
                if (result.getSeedsForOpponent() > 0)
                    return doPlay(playingPlayer, player2, player1, 0, result.getSeedsForOpponent());
            } else {
                result = player1.playHouse(houseNumber, player2);
                if (result.getSeedsForOpponent() > 0)
                    return doPlay(playingPlayer, player2, player1, 0, result.getSeedsForOpponent());
            }
        } else {
            if (transferSeeds > 0) {
                result = player1.receiveSeeds(transferSeeds, houseNumber, player2, player1);
                if (result.getSeedsForOpponent() > 0)
                    return doPlay(playingPlayer, player2, player1, 0, result.getSeedsForOpponent());
            }
        }
        return result.isiPlayAgain();
    }

    private void calculateScore() {
        currentScore.put(PlayerType.NORTH, player1.getTotal());
        currentScore.put(PlayerType.SOUTH, player2.getTotal());
    }

    public String getId() {
        return id;
    }

    public PlayerType getNext() {
        return next.getType();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
