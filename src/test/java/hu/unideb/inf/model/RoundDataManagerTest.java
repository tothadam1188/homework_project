package hu.unideb.inf.model;

import game.State;
import hu.unideb.inf.model.persistence.Round;
import hu.unideb.inf.model.persistence.RoundDataManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoundDataManagerTest {

    private static final String TEST_FILENAME = "test_round.json";
    private RoundDataManager manager;

    @BeforeEach
    void setUp() {
        manager = new RoundDataManager();
        File file = new File(TEST_FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @AfterAll
    static void cleanUp(){
        File file = new File(TEST_FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testSaveAndLoadRound() throws IOException {
        Round round = new Round("Alice", "Bob", 10, "01010101010", State.Status.PLAYER_1_WINS);
        manager.saveRound(round, TEST_FILENAME);
        round = new Round("Bob", "Charlie", 12, "01010101010", State.Status.PLAYER_2_WINS);
        manager.saveRound(round, TEST_FILENAME);

        List<Round> rounds = manager.loadRounds(TEST_FILENAME);
        assertEquals(2, rounds.size());
        assertEquals("Alice", rounds.get(0).getPlayer1Name());
        assertEquals(State.Status.PLAYER_2_WINS, rounds.get(1).getStatus());
    }


    @Test
    void testLoadRoundsWhenFileDoesNotExist() {
        List<Round> rounds = manager.loadRounds(TEST_FILENAME);
        assertNotNull(rounds);
        assertTrue(rounds.isEmpty());
    }


}
