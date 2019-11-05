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

        while(!state.onKamino()){
            ArrayList<PlanetStatus> allNeighbors = new ArrayList<>();
            HashSet<Integer> allNeighborsIDSet = new HashSet<>();

            for (PlanetStatus thisNeighbor : state.neighbors()){
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

            for (int i = 0; i < allNeighbors.size(); i++){
                int thisID = allNeighbors.get(i).id();
                if (!visitedPlanetIDs.contains(thisID)){
                    stagesMap.put(thisID, state.currentID());
                    visitedPlanetIDs.add(thisID);
                    state.moveTo(thisID);
                    break;
                } else if (i == allNeighbors.size() -1){
                    int prevStateID = stagesMap.get(state.currentID());
                    state.moveTo(prevStateID);
                }
            }
        }
        return;
    }


    @Override
    public void gather(GathererStage state) {

    }
//    {
//        int fuelRemaining = state.fuelRemaining();
//        Graph allPlanetsGraph = (Graph) state.planetGraph();
//        Planet start = state.currentPlanet();
//        Planet end = state.earth();
//        ArrayList<Planet> pathFromKamino = new ArrayList<>();
//        pathFromKamino.add(start);
//        ArrayList<Planet> pathFromEarth = new ArrayList<>();
//        pathFromEarth.add(end);
//        HashSet<Planet> visitedPlanets = new HashSet<Planet>();
//
//        ArrayList<Planet> travelList = new ArrayList<>();
//        addNextMostProfitablePlanet(start, end, visitedPlanets, allPlanetsGraph, fuelRemaining, travelList);
//        travelList.addAll(allPlanetsGraph.shortestPath(travelList.get(travelList.size() -1), state.earth()));
//        travelList.remove(0);
//
//        for (int i = 0; i < travelList.size() - 2; i ++){
//            if (travelList.get(i).name().equals(travelList.get(i+1).name())){
//                travelList.remove(i);
//                i--;
//            }
//        }
//
//        for (Planet planet : travelList) {
//            state.moveTo(planet);
//        }
//        return;
//    }
//
//    private void addNextMostProfitablePlanet(Planet start, Planet end, HashSet<Planet> visitedPlanets, Graph allPlanetsGraph,
//                                             int fuelRemaining, ArrayList<Planet> travelList) {
//        if (fuelRemaining <= allPlanetsGraph.pathLength(allPlanetsGraph.shortestPath(start, end))) {
//            return;
//        }
//        Planet bestPlanet = null;
//        double bestWorth = 0.0;
//        List<Planet> pathToBestPlanet = new ArrayList<>();
//        for(Object next : allPlanetsGraph.allVertices()){
//            Planet thisPlanet = (Planet) next;
//            if (!visitedPlanets.contains(thisPlanet)){
//                double thisWorth = thisPlanet.spice()/
//                        Math.sqrt((thisPlanet.x()-start.x())*(thisPlanet.x()-start.x()) +
//                                (thisPlanet.y()-start.y())*(thisPlanet.y()-start.y()));
//                if ( thisWorth > bestWorth) {
//                    List<Planet> shortestPathToPlanet = allPlanetsGraph.shortestPath(start, thisPlanet);
//                    int pathLengthToPlanet = allPlanetsGraph.pathLength(shortestPathToPlanet);
//                    if ( pathLengthToPlanet + allPlanetsGraph.pathLength(allPlanetsGraph.shortestPath(thisPlanet, end)) <= fuelRemaining){
//                        bestPlanet = thisPlanet;
//                        bestWorth = thisWorth;
//                        pathToBestPlanet = shortestPathToPlanet;
//                    }
//                }
//
//            }
//        }
//        if (bestPlanet == null){
//            return;
//        }
//        fuelRemaining = fuelRemaining - allPlanetsGraph.pathLength(allPlanetsGraph.shortestPath(start, bestPlanet));
//        travelList.addAll(pathToBestPlanet);
//        visitedPlanets.addAll(pathToBestPlanet);
//        addNextMostProfitablePlanet(bestPlanet, end, visitedPlanets, allPlanetsGraph,
//                fuelRemaining, travelList);
//
//        return;
//    }

//        boolean notAbleToConnect = false;
//        while(!pathFromKamino.get(pathFromKamino.size() -1).equals(pathFromEarth.get(0))) {
//            if(!addToPath(start, end, pathFromKamino, pathFromEarth, allPlanetsGraph, fuelRemaining, emptyPlanets)){
//                notAbleToConnect = true;
//                break;
//            }
//        }
//
//        List<Planet> connectPath = new ArrayList<Planet>();
//        if (notAbleToConnect){
//            connectPath = allPlanetsGraph.shortestPath(
//                    pathFromKamino.get(pathFromKamino.size() -1), pathFromEarth.get(0) );
//        }
//
//        ArrayList<Planet> gatherPath = new ArrayList<>();
//        gatherPath.addAll(pathFromKamino);
//        if (notAbleToConnect){
//            gatherPath.addAll(connectPath);
//        }
//        gatherPath.addAll(pathFromEarth);
//        gatherPath.remove(0);
//        for (int i = 0; i < gatherPath.size() -2; i ++){
//            if (gatherPath.get(i).equals(gatherPath.get(i+1))){
//                gatherPath.remove(i);
//            }
//        }
//
//        for (Planet a : pathFromKamino){
//            System.out.println("from kamino " + a);
//        }
//        for (Planet b : connectPath){
//            System.out.println("connect " + b);
//        }
//        for (Planet c : pathFromKamino){
//            System.out.println("from earth " + c);
//        }
//        for (Planet next : gatherPath){
//            System.out.println(next);
//        }
//        for (Planet next : gatherPath){
//            state.moveTo(next);
//        }
//        return;
    }

//    private boolean addToPath (Planet start, Planet end, ArrayList<Planet> pathFromKamino, ArrayList<Planet> pathFromEarth,
//                            Graph allPlanetsGraph, int fuelRemaining, HashSet<Planet> emptyPlanets){
//
//        HashSet<Planet> startNeighbors = new HashSet<>();
//        HashSet<Planet> endNeighbors = new HashSet<>();
//        ArrayList<Planet> allNeighbors = new ArrayList<>();
//        startNeighbors.addAll(allPlanetsGraph.getNeighbours(start).keySet());
//        endNeighbors.addAll(allPlanetsGraph.getNeighbours(end).keySet());
//        allNeighbors.addAll(startNeighbors);
//        allNeighbors.addAll(endNeighbors);
//        Collections.sort(allNeighbors, new Comparator<Planet>(){
//            @Override
//            public int compare(Planet a, Planet b){
//                return b.spice() - a.spice();
//            }
//        });
//
//        boolean notAbleToConnect = true;
//        for (Planet neighbor : allNeighbors){
//            if (pathFromKamino.get(pathFromKamino.size()-1).equals(pathFromEarth.get(0))){
//                notAbleToConnect = false;
//                break;
//            }
//            if (startNeighbors.contains(neighbor)) {
//                List<Planet> shortestPath = allPlanetsGraph.shortestPath(neighbor, end);
//                if (allPlanetsGraph.pathLength(shortestPath) + allPlanetsGraph.edgeLength(start, neighbor) <= fuelRemaining
//                        && !emptyPlanets.contains(neighbor)){
//                    pathFromKamino.add(neighbor);
//                    System.out.println("added neighbor to start " + neighbor);
//                    emptyPlanets.add(neighbor);
//                    addToPath(neighbor, end, pathFromKamino, pathFromEarth, allPlanetsGraph,
//                            fuelRemaining - allPlanetsGraph.edgeLength(start, neighbor), emptyPlanets);
//                    break;
//                }
//            }
//            else {
//                List<Planet> shortestPath = allPlanetsGraph.shortestPath(start, neighbor);
//                if (allPlanetsGraph.pathLength(shortestPath) + allPlanetsGraph.edgeLength(neighbor, end) <= fuelRemaining
//                        && !emptyPlanets.contains(neighbor)){
//                    pathFromEarth.add(0,neighbor);
//                    System.out.println("added neighbor to end " + neighbor);
//                    emptyPlanets.add(neighbor);
//                    addToPath(start, neighbor, pathFromKamino, pathFromEarth, allPlanetsGraph,
//                            fuelRemaining - allPlanetsGraph.edgeLength(neighbor, end), emptyPlanets);
//                    break;
//                }
//
//            }
//        }
//        if (notAbleToConnect){
//            return false;
//        }
//        return true;
//
//    }

