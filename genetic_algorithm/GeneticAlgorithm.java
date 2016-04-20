package genetic_algorithm;//============================================================================**
// Imports
//============================================================================**

import java.util.Random;
import java.util.ArrayList;

//============================================================================**
// genetic_algorithm.GeneticAlgorithm Class
//============================================================================**

class GeneticAlgorithm
{
    //----------------------------------------------------------------------------**
    // Private member variables
    //----------------------------------------------------------------------------**

	private ConfigManager mParams;
	private Random mRandomizer;

	private Population mPopulation;
    private int mNumRuns;
    private int mNumGenerations;

	private FitnessFunction mFitnessFunction;
	private RunStatistics mStatistics;

	private ArrayList<ArrayList<Integer>> mNeighborhoods;
	private boolean mInitCheck;

    //============================================================================**
    // genetic_algorithm.GeneticAlgorithm()
    //============================================================================**

    GeneticAlgorithm(ConfigManager params)
	{
		mParams = params;
		mNeighborhoods = new ArrayList<ArrayList<Integer>>();
		mInitCheck = true;
	}

    //============================================================================**
    // setup()
    // Description: Initializes the Genetic Algorithm by initializing parameters
    // and Run Statistics
    //============================================================================**

    void setup()
	{
		mRandomizer = new Random(mParams.getIntParameter("RandomSeed"));
        mNumRuns = mParams.getIntParameter("NumRuns");
        mNumGenerations = mParams.getIntParameter("NumGenerations");

        String problemType = mParams.getStringParameter("ProblemType");

        System.out.println("Setting up problem...\n");
		mPopulation = new Population(mRandomizer, mParams, true);

		if (problemType.equals("Topology"))
		{
			mFitnessFunction = new DecimalValueFitnessFunction();
		}
		else
		{
			System.err.println("Invalid problem Type: " + problemType);
		}

		mStatistics = new RunStatistics();
	}

    //============================================================================**
    // begin()
    // Description: Run loop of the Genetic Algorithm.
    //============================================================================**

    void begin()
	{
		// Do regular runs
		for (int runNum = 1; runNum <= mNumRuns; runNum++)
		{
			// Perform a single run with new chromos
			performRun(mPopulation, runNum, true);
		}
	}

    //============================================================================**
    // performGeneration()
    //============================================================================**

    private void performGeneration(Population population, int generationNum, int runNum)
	{
		// Calculate the fitness of the population
		population.calculationFitnessByFunction(mFitnessFunction, mStatistics, generationNum, runNum);

		// Reproduction
		population.performReproduction();
	}

    //============================================================================**
    // performRun()
    //============================================================================**

    private void performRun(Population population, int runNum, boolean initChromos)
	{

	    ArrayList<ArrayList<Integer>> local;
		if(initChromos)
		{
		    //If it's the first run, store the list of neighbors to avoid recomputing them
			if(mInitCheck)
            {
                local = mPopulation.getNeighborhoods();
                for(int i = 0; i<local.size(); i++){
                    ArrayList<Integer> pop = new ArrayList<>();
                    for(int j = 0; j < local.get(i).size(); j++)
                        pop.add(local.get(i).get(j));
                    mNeighborhoods.add(pop);
                }
            }
            mPopulation = new Population(mRandomizer, mParams, false);
            mPopulation.setNeighborhoods(mNeighborhoods);

            mInitCheck = false;
		}


		// Perform generations
		for (int generationNum = 1; generationNum <= mNumGenerations; generationNum++)
		{
			performGeneration(population, generationNum, runNum);

			mStatistics.printGeneration();
		}
	}
}
