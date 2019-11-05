package cpen221.mp2.spaceship;

import cpen221.mp2.controllers.GathererStage;
import cpen221.mp2.controllers.HunterStage;
import cpen221.mp2.controllers.Spaceship;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.ImGraph;
import cpen221.mp2.models.*;
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

        while (!state.onKamino()) {
            ArrayList<PlanetStatus> allNeighbors = new ArrayList<>();
            HashSet<Integer> allNeighborsIDSet = new HashSet<>();

            for (PlanetStatus thisNeighbor : state.neighbors()) {
                allNeighbors.add(thisNeighbor);
                allNeighborsIDSet.add(thisNeighbor.id());
            }

            //sort allNeighbors from highest signal to lowest signal
            Collections.sort(allNeighbors, new Comparator<PlanetStatus>() {
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

            for (int i = 0; i < allNeighbors.size(); i++) {
                int thisID = allNeighbors.get(i).id();
                if (!visitedPlanetIDs.contains(thisID)) {
                    stagesMap.put(thisID, state.currentID());
                    visitedPlanetIDs.add(thisID);
                    state.moveTo(thisID);
                    break;
                } else if (i == allNeighbors.size() - 1) {
                    int prevStateID = stagesMap.get(state.currentID());
                    state.moveTo(prevStateID);
                }
            }
        }
        return;
    }


    @Override
    public void gather(GathererStage state) {
        Graph<Planet, Link> allPlanetsGraph = (Graph) state.planetGraph();
        Set<Planet> allPlanets = allPlanetsGraph.allVertices();
        Planet topLeftPlanet = state.currentPlanet();
        Planet topRightPlanet = state.currentPlanet();
        Planet bottomLeftPlanet = state.currentPlanet();
        Planet bottomRightPlanet = state.currentPlanet();
        for (Planet checkPlanet : allPlanets) {
            int x = checkPlanet.x();
            int y = checkPlanet.y();
            if (x <= topLeftPlanet.x() && y <= topLeftPlanet.y()) {
                topLeftPlanet = checkPlanet;
            }
            if (x >= topRightPlanet.x() && y <= topRightPlanet.y()) {
                topRightPlanet = checkPlanet;
            }
            if (x <= bottomLeftPlanet.x() && y >= bottomLeftPlanet.y()) {
                bottomLeftPlanet = checkPlanet;
            }
            if (x >= bottomRightPlanet.x() && y >= bottomRightPlanet.y()) {
                bottomRightPlanet = checkPlanet;
            }
        }

        //Define most amount of fuel needed to be the distance from the farthest corner nodes
        //plus 1/5 of the distance from top left to top right to give a little lee-way
        int longestPathLength = (int)(0.2*allPlanetsGraph.pathLength(allPlanetsGraph.shortestPath(topLeftPlanet, topRightPlanet))) +
                Math.max(Math.max(allPlanetsGraph.pathLength(allPlanetsGraph.shortestPath(topLeftPlanet, topRightPlanet)),
                        allPlanetsGraph.pathLength(allPlanetsGraph.shortestPath(topLeftPlanet, bottomLeftPlanet))),
                        allPlanetsGraph.pathLength(allPlanetsGraph.shortestPath(topLeftPlanet, bottomRightPlanet)));

        ArrayList<Planet> spiciestPlanets = new ArrayList<>();
        spiciestPlanets.addAll(allPlanets);

        Collections.sort(spiciestPlanets, new Comparator<Planet>() {
            @Override
            public int compare(Planet a, Planet b) {
                return b.spice() - a.spice();
            }
        });
        int totalSpiceInUniverse = 0;
        for (Planet a : allPlanets){
            totalSpiceInUniverse += a.spice();
        }
        System.out.println("max spice = " + totalSpiceInUniverse);

        HashSet<Planet> visitedPlanets = new HashSet<>();

        for (Planet nextMostSpicy : spiciestPlanets) {
            if (!visitedPlanets.contains(nextMostSpicy)) {
                List<Planet> pathToNextPlanet = allPlanetsGraph.shortestPath(state.currentPlanet(), nextMostSpicy);
                pathToNextPlanet.remove(0);
                for (Planet nextPlanet : pathToNextPlanet) {
                    if (state.fuelRemaining() - allPlanetsGraph.getEdge(state.currentPlanet(), nextPlanet).fuelNeeded() > longestPathLength) {
                        visitedPlanets.add(nextPlanet);
                        state.moveTo(nextPlanet);
                    } else {
                        List<Planet> pathToHome = allPlanetsGraph.shortestPath(state.currentPlanet(), state.earth());
                        pathToHome.remove(0);
                        for (Planet next : pathToHome) {
                            state.moveTo(next);
                        }
                        break;
                    }
                }
            }
        }

        return;
    }
}


