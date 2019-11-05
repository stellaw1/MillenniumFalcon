package cpen221.mp2;

import cpen221.mp2.controllers.Kamino;
import cpen221.mp2.spaceship.MillenniumFalcon;
import cpen221.mp2.views.BenchmarkView;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;


import static org.junit.Assert.*;

public class Stage3Test {

    @Test
    public void testMF() {
        long seed = 4646556996762522249L;
        BenchmarkView view = new BenchmarkView();
        Kamino myKamino = new Kamino(seed, new MillenniumFalcon(), view);

        String[] args = {"--benchmark", Long.toString(seed)};

        myKamino.main(args);

        assertTrue(myKamino.huntSucceeded());
        assertTrue(myKamino.gatherSucceeded());

        view.scoreStats();
        view.timeStats();
    }

    @Test
    public void testMF1() {
        long seed = 5380112710085299282L;
        BenchmarkView view = new BenchmarkView();
        Kamino myKamino = new Kamino(seed, new MillenniumFalcon(), view);

        String[] args = {"--benchmark", Long.toString(seed)};

        myKamino.main(args);

        assertTrue(myKamino.huntSucceeded());
        assertTrue(myKamino.gatherSucceeded());

        view.scoreStats();
        view.timeStats();
    }

    @Test
    public void testMF2() {
        long seed = 8865034956231574663L;
        BenchmarkView view = new BenchmarkView();
        Kamino myKamino = new Kamino(seed, new MillenniumFalcon(), view);

        String[] args = {"--benchmark", Long.toString(seed)};

        myKamino.main(args);

        assertTrue(myKamino.huntSucceeded());
        assertTrue(myKamino.gatherSucceeded());

        view.scoreStats();
        view.timeStats();
    }

    //test on huge planet with 700+ planets
    @Test
    public void testMF3() {
        long seed = -2912025469858177646L;
        BenchmarkView view = new BenchmarkView();
        Kamino myKamino = new Kamino(seed, new MillenniumFalcon(), view);

        String[] args = {"--benchmark", Long.toString(seed)};

        myKamino.main(args);

        assertTrue(myKamino.huntSucceeded());
        assertTrue(myKamino.gatherSucceeded());

        view.scoreStats();
        view.timeStats();
    }

}
