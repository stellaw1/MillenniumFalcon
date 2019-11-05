package cpen221.mp2;

import cpen221.mp2.controllers.Kamino;
import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.Vertex;
import cpen221.mp2.models.Universe;
import cpen221.mp2.spaceship.MillenniumFalcon;
import cpen221.mp2.views.BenchmarkView;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class Stage3Test {

    //create a universe instance
    Universe myUniverse;
    Kamino myKamino;

    @Test
    public void testHunt() {
        String[] args = {"--benchmark"};
        myKamino.main(args);

        //view.scoreStats();
    }

    @Test
    public void testGather() {
        long seed = 4646556996762522249L;
        BenchmarkView view = new BenchmarkView();
        Kamino test = new Kamino(seed, new MillenniumFalcon(), view);

        String[] args = {"--benchmark"};

        test.main(args);

        view.scoreStats();

    }


}
