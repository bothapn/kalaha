package za.co.pnb.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private List<House> houses = new ArrayList<>();
    private int pit = 0;
    private PlayerType type;

    public Player(PlayerType type) {
        this.type = type;
        createHouses();
    }

    public Result playHouse(int houseNumber, Player opponent) {
        if (getHouse(houseNumber).isEmpty())
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "House is empty");
        int seeds = getHouse(houseNumber).takeSeeds();
        return receiveSeeds(seeds, houseNumber, this, opponent);
    }

    public Result receiveSeeds(int seeds, int houseNumber, Player seedsFromPayer, Player opponent) {
        if (seeds <= 0)
            return new Result();

        if (!seedsFromPayer.equals(this)) {
            houseNumber = 0;
        }

        for (House h : houses) {
            if (h.getHouseNumber() > houseNumber && seeds > 0) {
                if (seeds == 1 &&
                        h.isEmpty() && seedsFromPayer.equals(this) &&
                        !opponent.getHouse(houseNumber).isEmpty()) {
                    int opponentHouseNumber = 6-houseNumber;
                    pit += 1 + opponent.getHouse(opponentHouseNumber).takeSeeds();
                    seeds--;
                } else {
                    h.giveSeed();
                    seeds -= 1;
                }
            }
        }

        if (seeds > 0 && seedsFromPayer.equals(this)) {
            pit++;
            seeds--;
            Result result = new Result(seeds);
            if (seeds == 0)
                result.setiPlayAgain(true);
            return result;
        }
        return new Result(seeds);
    }

    public PlayerType getType() {
        return type;
    }

    public House getHouse(int houseNumber) {
        return houses.stream().filter(h -> h.getHouseNumber() == houseNumber).findFirst().get();
    }

    public boolean allEmpty() {
        return houses.stream().allMatch(House::isEmpty);
    }

    public void print() {
        List<House> printHouses = new ArrayList<>(houses);
        System.out.println("Player " + type.name());
        System.out.println("Pit " + pit);
        if(type.equals(PlayerType.NORTH))
            Collections.reverse(printHouses);
        printHouses.stream().forEach(h -> System.out.print(h.getHouseNumber() + " "));
        System.out.println("");
        printHouses.stream().forEach(h -> System.out.print(h.seedsAvailable() + " "));
        System.out.println("");
    }

    public int getPit() {
        return pit;
    }

    public int getTotal() {
        return pit + houses.stream().map(House::seedsAvailable).reduce(0, Integer::sum);
    }

    private void createHouses() {
        for (int k = 1; k < 7; k++)
            houses.add(new House(k));
    }

    public List<House> getHouses() {
        return houses;
    }
}
