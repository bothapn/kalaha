package za.co.pnb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.pnb.services.GameService;
import za.co.pnb.models.PlayerType;
import za.co.pnb.models.Session;

@RestController(value = "/games")
public class KalahaRest {

    private final GameService gameService;

    @Autowired
    public KalahaRest(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public Session createSession() {
        return gameService.startSession();
    }

    @DeleteMapping("/{sessionId}")
    public Session endSession(@PathVariable String sessionId) {
        return gameService.endSession(sessionId);
    }

    @PutMapping("/{sessionId}/{playerType}/{houseNumber}")
    public Session playHouse(@PathVariable String sessionId, @PathVariable PlayerType playerType, @PathVariable String houseNumber) {
        return gameService.play(sessionId, playerType, houseNumber);
    }
}
