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

            System.out.println("at planet " + state.currentID());
            System.out.println("planet " + state.currentID() + " has signal " + state.signal());
            for (PlanetStatus thisNeighbor : state.neighbors()){
                System.out.println("check neighbor " + thisNeighbor.name() + " has signal " + thisNeighbor.signal());
                allNeighbors.add(thisNeighbor);
                allNeighborsIDSet.add(thisNeighbor.id());
            }

            //sort allNeighbors from highest signal to lowest signal
            Collections.sort(allNeighbors, new Comparator<PlanetStatus>(){
                @Override
                public int compare(PlanetStatus thisOne, PlanetStatus otherOne) {
                    if (thisOne.signal() > otherOne.signal()) {
                        return -1;
                    } else if (thisOne.signal() < otherOne.signal()) {
                        return 1;
                    }
                    return 0;
                }
            });
            for (int i = 0; i < allNeighbors.size(); i ++){
                System.out.println("neighbor " + allNeighbors.get(i).name() + "  has signal " + allNeighbors.get(i).signal());
            }

            for (int i = 0; i < allNeighbors.size(); i++){
                int thisID = allNeighbors.get(i).id();
                if (!visitedPlanetIDs.contains(thisID)){
                    stagesMap.put(thisID, state.currentID());
                    visitedPlanetIDs.add(thisID);
                    state.moveTo(thisID);
                    System.out.println("moved to " + allNeighbors.get(i).name() + " ID " + state.currentID());
                    break;
                } else if (i == allNeighbors.size() -1){
                    int prevStateID = stagesMap.get(state.currentID());
                    state.moveTo(prevStateID);
                    System.out.println("moved to " + state.currentID());
                }
            }
        }
        System.out.println("checkpoint");
        return;
    }


    @Override
    public void gather(GathererStage state) {


        // TODO: Implement this method
    }

}
