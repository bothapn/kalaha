package za.co.pnb.models;

public class Result {
    private int seedsForOpponent=0;
    private boolean playerAllEmpty = false;
    private boolean iPlayAgain = false;

    protected Result(int seedsForOpponent) {
        this.seedsForOpponent = seedsForOpponent;
    }

    public Result(boolean playerAllEmpty) {
        this.playerAllEmpty = playerAllEmpty;
    }

    public Result() {
    }

    protected int getSeedsForOpponent() {
        return seedsForOpponent;
    }

    protected boolean isPlayerAllEmpty() {
        return playerAllEmpty;
    }

    protected void setiPlayAgain(boolean iPlayAgain) {
        this.iPlayAgain = iPlayAgain;
    }

    protected boolean isiPlayAgain() {
        return iPlayAgain;
    }
}
