package cpen221.mp2.spaceship;

import cpen221.mp2.controllers.GathererStage;
import cpen221.mp2.controllers.HunterStage;
import cpen221.mp2.controllers.Spaceship;
import cpen221.mp2.graph.ImGraph;
import cpen221.mp2.models.Link;
import cpen221.mp2.models.Planet;
import cpen221.mp2.models.PlanetStatus;
import cpen221.mp2.util.Heap;

import java.util.*;

/**
 * An instance implements the methods needed to complete the mission.
 */
public class MillenniumFalcon implements Spaceship {
    long startTime = System.nanoTime(); // start time of rescue phase

    @Override
    public void hunt(HunterStage state) {
        HashMap<Integer, Integer> stagesMap = new HashMap<>();
        HashSet<Integer> visitedPlanetIDs = new HashSet<>();

        stagesMap.put(state.currentID(), state.currentID());
        visitedPlanetIDs.add(state.currentID());

        while(!state.onKamino()){
            ArrayList<PlanetStatus> allNeighbors = new ArrayList<>();
            HashSet<Integer> allNeighborsIDSet = new HashSet<>();

            for (PlanetStatus thisNeighbor : state.neighbors()){
                allNeighbors.add(thisNeighbor);
                allNeighborsIDSet.add(thisNeighbor.id());
            }
            Collections.sort(allNeighbors);


            for (int i = allNeighbors.size()-1; i > 0; i--){
                int thisID = allNeighbors.get(i).id();
                if (!visitedPlanetIDs.contains(thisID)){
                    stagesMap.put(thisID, state.currentID());
                    visitedPlanetIDs.add(thisID);
                    state.moveTo(thisID);
                    break;
                }
            }


        }
        return;
    }


    @Override
    public void gather(GathererStage state) {
        // TODO: Implement this method
    }

}
