package za.co.pnb.models;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class GameService {
    private Map<String, Session> sessions = new HashMap();

    public Session startSession() {
        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(sessionId);
        sessions.put(sessionId, session);
        return session;
    }

    public Session endSession(String sessionId) {
        if (!sessions.containsKey(sessionId))
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        Session session = sessions.get(sessionId);
        sessions.remove(sessionId);
        return session;
    }

    public Session play(String sessionId, PlayerType playerType, String houseNumber) {
        if (!sessions.containsKey(sessionId))
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        Session session =  sessions.get(sessionId);
        session.startPlay(playerType, Integer.parseInt(houseNumber));
        return session;
    }
}
