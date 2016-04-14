package tests;

import genetic_algorithm.ConfigManager;
import genetic_algorithm.Population;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PopulationTest extends TestCase
{
    /*
     * Private member variables
     */

    private Random mRandomizer;
    private ConfigManager mConfigManager;
    private Population mPopulation;

    @org.junit.Before
    public void setUp() throws Exception
    {
        mRandomizer = new Random();

        mConfigManager = new ConfigManager();

        mConfigManager.setIntParameter("NumGenes", 1);
        mConfigManager.setIntParameter("GeneSize", 1);
        mConfigManager.setIntParameter("MinDnaValue", 0);
        mConfigManager.setIntParameter("MaxDnaValue", 1000000);

        mConfigManager.setIntParameter("PopulationDimensionSize", 10);
        mConfigManager.setIntParameter("NumPopulationDimensions", 3);

        mConfigManager.setStringParameter("NeighborhoodType", "COMPACT");
        mConfigManager.setIntParameter("NeighborhoodRadius", 1);

        mConfigManager.setStringParameter("MutationType", "NO_MUTATION");
        mConfigManager.setIntParameter("MutationRate", 0);

        mConfigManager.setStringParameter("CrossOverType", "NO_CROSSOVER");
        mConfigManager.setIntParameter("CrossOverRate", 0);

        mConfigManager.setStringParameter("SelectionType", "FITNESS_PROPORTIONAL");

        mPopulation = new Population(mRandomizer, mConfigManager);
    }

    @org.junit.Test
    public void testGetNeighborhoodCompact() throws Exception
    {
        ArrayList<int[]> neighborhoodList = new ArrayList<>();
        int[] initPosition;

        // Radius 1, Dimension Size 10, Num Dimensions 3
        // Test inner location
        initPosition = new int[] {4, 5, 6};
        mPopulation.getNeighborhood(initPosition, initPosition, neighborhoodList);

        // At (4, 5, 6), there should be 7 locations in the neighborhood of radius 1
        assertEquals(neighborhoodList.size(), 7);
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 5, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {3, 5, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {5, 5, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 4, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 6, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 5, 5}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 5, 7}));

        // Radius 2, Dimension Size 5, Num Dimension 2

        mConfigManager.setIntParameter("PopulationDimensionSize", 5);
        mConfigManager.setIntParameter("NumPopulationDimensions", 2);
        mConfigManager.setIntParameter("NeighborhoodRadius", 2);
        mPopulation = new Population(mRandomizer, mConfigManager);

        // Test corner case
        neighborhoodList = new ArrayList<>();
        initPosition = new int[] {0, 0};
        mPopulation.getNeighborhood(initPosition, initPosition, neighborhoodList);

        // At (0, 0), there should be 6 location in the neighborhood of radius 2
        assertEquals(6, neighborhoodList.size());
        assertTrue(Population.listContains(neighborhoodList, new int[] {0, 0}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {1, 0}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {0, 1}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {1, 1}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {2, 0}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {0, 2}));
    }


    @org.junit.Test
    public void testGetNeighborhoodLinear() throws Exception
    {
        mConfigManager.setStringParameter("NeighborhoodType", "LINEAR");
        mConfigManager.setIntParameter("NeighborhoodRadius", 2);
        mPopulation = new Population(mRandomizer, mConfigManager);

        ArrayList<int[]> neighborhoodList = new ArrayList<>();
        int[] initPosition;

        // Radius 2, Dimension Size 10, Num Dimensions 3
        // Test inner location
        initPosition = new int[] {4, 5, 6};
        mPopulation.getNeighborhood(initPosition, initPosition, neighborhoodList);

        // At (4, 5, 6), there should be 13 locations in the neighborhood of radius 1
        assertEquals(13, neighborhoodList.size());
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 5, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {2, 5, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {3, 5, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {5, 5, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {6, 5, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 3, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 4, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 6, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 7, 6}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 5, 4}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 5, 5}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 5, 7}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {4, 5, 8}));

        // Radius 2, Dimension Size 5, Num Dimension 2

        mConfigManager.setIntParameter("PopulationDimensionSize", 5);
        mConfigManager.setIntParameter("NumPopulationDimensions", 2);
        mConfigManager.setIntParameter("NeighborhoodRadius", 2);
        mPopulation = new Population(mRandomizer, mConfigManager);

        // Test corner case
        neighborhoodList = new ArrayList<>();
        initPosition = new int[] {0, 0};
        mPopulation.getNeighborhood(initPosition, initPosition, neighborhoodList);

        // At (0, 0), there should be 5 location in the neighborhood of radius 2
        assertEquals(5, neighborhoodList.size());
        assertTrue(Population.listContains(neighborhoodList, new int[] {0, 0}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {1, 0}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {0, 1}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {2, 0}));
        assertTrue(Population.listContains(neighborhoodList, new int[] {0, 2}));
    }

    @org.junit.Test
    public void testIndexToDimensionalPosition() throws Exception
    {
        int[] expectedDimensionalPosition = {7, 3, 2};
        int[] actualDimensionalPosition = mPopulation.indexToDimensionalPosition(732);
        assertTrue("Testing (7, 3, 2) in 3 dimensions and 10 per dimension",
                Arrays.equals(expectedDimensionalPosition, actualDimensionalPosition));

        mConfigManager.setIntParameter("PopulationDimensionSize", 5);
        mConfigManager.setIntParameter("NumPopulationDimensions", 2);
        mPopulation = new Population(mRandomizer, mConfigManager);

        expectedDimensionalPosition = new int[] {3, 4};
        actualDimensionalPosition = mPopulation.indexToDimensionalPosition(19);
        assertTrue("Testing (3, 4) in 2 dimensions and 5 per dimension",
                Arrays.equals(expectedDimensionalPosition, actualDimensionalPosition));

        mConfigManager.setIntParameter("PopulationDimensionSize", 20);
        mConfigManager.setIntParameter("NumPopulationDimensions", 4);
        mPopulation = new Population(mRandomizer, mConfigManager);

        expectedDimensionalPosition = new int[] {4, 3, 2, 1};
        actualDimensionalPosition = mPopulation.indexToDimensionalPosition(33241);
        assertTrue("Testing (4, 3, 2, 1) in 4 dimensions and 20 per dimension",
                Arrays.equals(expectedDimensionalPosition, actualDimensionalPosition));
    }

    @org.junit.Test
    public void testDimensionalPositionToIndex() throws Exception
    {
        int expectedIndex = 732;
        int actualIndex = mPopulation.dimensionalPositionToIndex(new int[] {7, 3, 2});
        assertEquals(expectedIndex, actualIndex);

        mConfigManager.setIntParameter("PopulationDimensionSize", 5);
        mConfigManager.setIntParameter("NumPopulationDimensions", 2);
        mPopulation = new Population(mRandomizer, mConfigManager);

        expectedIndex = 19;
        actualIndex = mPopulation.dimensionalPositionToIndex(new int[] {3, 4});
        assertEquals(expectedIndex, actualIndex);

        mConfigManager.setIntParameter("PopulationDimensionSize", 20);
        mConfigManager.setIntParameter("NumPopulationDimensions", 4);
        mPopulation = new Population(mRandomizer, mConfigManager);

        expectedIndex = 33241;
        actualIndex = mPopulation.dimensionalPositionToIndex(new int[] {4, 3, 2, 1});
        assertEquals(expectedIndex, actualIndex);
    }
}