package hu.unideb.inf.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RoundDataManager {
    private static final String FILENAME="round_data.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveRound(Round round) throws IOException {
        List<Round> rounds = loadRounds();
        rounds.add(round);
        try (FileWriter writer = new FileWriter(FILENAME)) {
            gson.toJson(rounds, writer);
        }
    }

    public List<Round> loadRounds(){
        try (FileReader reader=new FileReader(FILENAME)){
            Type roundListType = new TypeToken<List<Round>>(){}.getType();
            List<Round> rounds = gson.fromJson(reader, roundListType);
            if(rounds!=null) return rounds;
            else return new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
