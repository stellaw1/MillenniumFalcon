package cpen221.mp2.spaceship;

import cpen221.mp2.controllers.GathererStage;
import cpen221.mp2.controllers.HunterStage;
import cpen221.mp2.controllers.Spaceship;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.models.*;
import java.util.*;

/**
 * An instance implements the methods needed to complete the mission.
 */
public class MillenniumFalcon implements Spaceship {

    /** start time of rescue phase */
    long startTime = System.nanoTime();

    // Representation Invariant:
    //
    // Abstract Function:
    //      An instance MF represents a spaceship that can find the planet Kamino
    //      and gather spices on its way back from Kamino to Earth.


    /**
     * method used in Hunt stage of Kamino game
     *
     * @param state HunterStage instance that represents the state of the space ship and provides all
     *              necessary methods to move through the galaxy and find the missing spaceship.
     */
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

            //sort allNeighbors from highest signal planet to lowest signal planet
            allNeighbors.sort(new Comparator<>() {
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
    }

    /**
     * Method used in the gather stage of the Kamino game
     *
     * @param state GathererStage instance that represents the state of the space ship and provides all
     *                    necessary methods to move through the galaxy and collect spice.
     */
    @Override
    public void gather(GathererStage state) {
        Graph<Planet, Link> allPlanetsGraph = (Graph) state.planetGraph();
        Set<Planet> allPlanets = allPlanetsGraph.allVertices();

        int longestPathLength = getLongestPathLength(state.currentPlanet(), allPlanets, allPlanetsGraph);

        ArrayList<Planet> spiciestPlanets = sortBySpiceLevel(allPlanets);

        HashSet<Planet> visitedPlanets = new HashSet<>();

        for (Planet nextMostSpicy : spiciestPlanets) {
            //begin journey to Earth if elapsed time is over 15 seconds
            if ((System.nanoTime() - startTime) > 15000000000L) {
                List<Planet> pathToHome = allPlanetsGraph.shortestPath(state.currentPlanet(), state.earth());
                pathToHome.remove(0);
                for (Planet next : pathToHome) {
                    state.moveTo(next);
                }
                break;
            } else if (!visitedPlanets.contains(nextMostSpicy)) {
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
    }

    /**
     * Helper method that sorts a set into a List in non-ascending order of planet spice level
     *
     * @param allPlanets Set of all planets in the universe
     * @return sorted ArrayList of type Planet in non-ascending order by spice
     */
    private ArrayList<Planet> sortBySpiceLevel(Set<Planet> allPlanets) {
        ArrayList<Planet> spiciestPlanets = new ArrayList<>(allPlanets);

        spiciestPlanets.sort(new Comparator<>() {
            @Override
            public int compare(Planet a, Planet b) {
                return b.spice() - a.spice();
            }
        });

        return spiciestPlanets;
    }

    /**
     * Calculates the spanning distance between corners in the universe using the coordinates of a planet in each corner
     *
     * @param currentPlanet Planet that MillenniumFalcon is on right now
     * @param allPlanets Set of all Planets in the universe
     * @param allPlanetsGraph Graph of all Planets and Links in the universe
     * @return integer that represents the length of the largest spanning distance
     */
    private int getLongestPathLength(Planet currentPlanet, Set<Planet> allPlanets, Graph allPlanetsGraph) {
        Planet topLeftPlanet = currentPlanet;
        Planet topRightPlanet =currentPlanet;
        Planet bottomLeftPlanet = currentPlanet;
        Planet bottomRightPlanet = currentPlanet;

        //find a planet in each corner:
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
        // plus 1/5 of the distance from top left to top right to give a little lee-way
        return (int) (0.2 * allPlanetsGraph.pathLength(allPlanetsGraph.shortestPath(topLeftPlanet, topRightPlanet)))
                + Math.max(Math.max(allPlanetsGraph.pathLength(allPlanetsGraph.shortestPath(topLeftPlanet, topRightPlanet)),
                allPlanetsGraph.pathLength(allPlanetsGraph.shortestPath(topLeftPlanet, bottomLeftPlanet))),
                allPlanetsGraph.pathLength(allPlanetsGraph.shortestPath(topLeftPlanet, bottomRightPlanet)));

    }
}


