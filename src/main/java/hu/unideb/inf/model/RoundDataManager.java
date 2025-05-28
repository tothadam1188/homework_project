package hu.unideb.inf.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.tinylog.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages saving and loading {@link Round} data to/from a JSON file.
 */
public class RoundDataManager {
    private static final String FILENAME="round_data.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Saves a single round by appending it to the existing list of rounds.
     * @param round the round to be saved
     * @throws IOException if writing to the file fails
     */
    public void saveRound(Round round) throws IOException {
        Logger.debug("Saving new round: {}", round);
        List<Round> rounds = loadRounds();
        rounds.add(round);
        try (FileWriter writer = new FileWriter(FILENAME)) {
            Logger.info("Round saved successfully. Total rounds: {}", rounds.size());
            gson.toJson(rounds, writer);
        } catch (IOException e) {
        Logger.error(e, "Failed to write round data to file");
        throw e;
    }
    }

    /**
     * Loads all saved rounds from the JSON file.
     * @return a list of rounds; returns an empty list if file doesn't exist or is unreadable
     */
    public List<Round> loadRounds(){
        try (FileReader reader=new FileReader(FILENAME)){
            Type roundListType = new TypeToken<List<Round>>(){}.getType();
            List<Round> rounds = gson.fromJson(reader, roundListType);
            Logger.debug("Loaded rounds from file");
            if(rounds!=null) return rounds;
            else return new ArrayList<>();
        } catch (IOException e) {
            Logger.warn(e, "Could not read round data file, returning empty list");
            return new ArrayList<>();
        }
    }
}
