package za.co.pnb.models;

public class House {
    private int seeds = 4;
    private int houseNumber;

    public House(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    protected boolean isEmpty() {
        return seeds == 0;
    }

    public int takeSeeds() {
        int copyOfSeeds = seeds;
        seeds=0;
        return copyOfSeeds;
    }

    public void takeSeed() {
        seeds--;
    }

    public void giveSeed() {
        seeds++;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public int seedsAvailable() {
        return seeds;
    }

    public int getSeeds() {
        return seeds;
    }
}
