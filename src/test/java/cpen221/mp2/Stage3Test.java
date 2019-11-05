package cpen221.mp2;

import cpen221.mp2.controllers.Kamino;
import cpen221.mp2.spaceship.MillenniumFalcon;
import cpen221.mp2.views.BenchmarkView;
import cpen221.mp2.views.CLIView;
import cpen221.mp2.views.QuietView;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;


import static org.junit.Assert.*;

public class Stage3Test {

    //test MillenniumFalcon.hunt and MillenniumFalcon.gather methods using a QuietView
    @Test
    public void testMF() {
        long seed = 4646556996762522249L;
        QuietView view = new QuietView();
        Kamino myKamino = new Kamino(seed, new MillenniumFalcon(), view);

        String[] args = {"-q", "--seed=" + seed};

        myKamino.main(args);

        assertTrue(myKamino.huntSucceeded());
        assertTrue(myKamino.gatherSucceeded());
    }

    @Test
    public void testMF1() {
        long seed = 5380112710085299282L;
        QuietView view = new QuietView();
        Kamino myKamino = new Kamino(seed, new MillenniumFalcon(), view);

        String[] args = {"-q", "--seed=" + seed};

        myKamino.main(args);

        assertTrue(myKamino.huntSucceeded());
        assertTrue(myKamino.gatherSucceeded());
    }

    @Test
    public void testMF2() {
        long seed = 8865034956231574663L;
        QuietView view = new QuietView();
        Kamino myKamino = new Kamino(seed, new MillenniumFalcon(), view);

        String[] args = {"-q", "--seed=" + seed};

        myKamino.main(args);

        assertTrue(myKamino.huntSucceeded());
        assertTrue(myKamino.gatherSucceeded());
    }

    //test on huge planet with 700+ planets
    @Test
    public void testMF3() {
        long seed = -2912025469858177646L;
        QuietView view = new QuietView();
        Kamino myKamino = new Kamino(seed, new MillenniumFalcon(), view);

        String[] args = {"-q", "--seed=" + seed};

        myKamino.main(args);

        assertTrue(myKamino.huntSucceeded());
        assertTrue(myKamino.gatherSucceeded());
    }

}
