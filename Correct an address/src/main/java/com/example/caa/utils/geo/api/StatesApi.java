package com.example.caa.utils.geo.api;

import com.example.caa.utils.geo.info.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class StatesApi {

    private JsonElement je;
    private Gson gson;
    private StateInfo[] states;

    public StatesApi(JsonElement je) {
        this.je = je;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.states= gson.fromJson(this.je, StateInfo[].class);
    }

    public List<StateInfo> getNewStates() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.states = gson.fromJson(this.je, StateInfo[].class);
      List<StateInfo> statesList=new ArrayList<>();
        List<String> wordsToRemove = new ArrayList<>();
        wordsToRemove.addAll(Arrays.asList("County", "Province", "Prefecture", "District", "Oblast", "municipality", "Region", "Municipality", "Department", "Governorate", "Regional", "Corporation", "Parish"));

        for (int i = 0; i < getStates().length; ++i) {
            if (getStates()[i].getCountry_id() == 181) {
                continue;
            }
            ArrayList<String> name = new ArrayList<>();
            String nameState = "";
            String[] words = states[i].getName().split(" ");
            for (var word : words) {
                name.add(word);
            }
            StringBuilder sb = new StringBuilder();
            for (var remWord : wordsToRemove) {
                if (name.contains(remWord)) {
                    int index = name.indexOf(remWord);
                    name.remove(index);
                }

            }
            for (var newWord : name) {
                sb.append(newWord).append(" ");
            }
            states[i].setName(sb.toString());
            statesList.add(states[i]);
        }


        return statesList;
    }
}
