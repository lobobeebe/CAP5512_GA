//============================================================================**
// Imports
//============================================================================**

import java.util.Random;

//============================================================================**
// GeneticAlgorithm Class
//============================================================================**

class GeneticAlgorithm
{
    //----------------------------------------------------------------------------**
    // Private Constants
    //----------------------------------------------------------------------------**

    private static final String NO_CROSSOVER = "NONE";
    private static final String SINGLE_CROSSOVER = "SINGLE";

	private static final String NO_MUTATION = "NO_MUTATION";

    private static final String TOURNAMENT_SELECTION = "TOURNAMENT";
	private static final String FITNESS_PROPORTIONAL_SELECTION = "FITNESS_PROPORTIONAL";
	private static final String LINEAR_RANKING_SELECTION = "LINEAR_RANKING";

    //----------------------------------------------------------------------------**
    // Private member variables
    //----------------------------------------------------------------------------**

	private ConfigManager mParams;
	private Random mRandomizer;

	private Chromo[] mPopulation;
	private int mNumGenes;
	private int mGeneSize;
    private int mMinDnaValue;
    private int mMaxDnaValue;
    private int mNumRuns;
    private int mNumGenerations;

	private String mSelectionType;
	private String mCrossOverType;
    private double mCrossOverRate;

	private String mMutationType;
	private double mMutationRate;

	private FitnessFunction mFitnessFunction;
	private RunStatistics mStatistics;

    //============================================================================**
    // GeneticAlgorithm()
    //============================================================================**

    GeneticAlgorithm(ConfigManager params)
	{
		mParams = params;
	}

    //============================================================================**
    // setup()
    // Description: Initializes the Genetic Algorithm by initializing parameters
    // and Run Statistics
    //============================================================================**

    void setup()
	{
		mRandomizer = new Random(mParams.getIntParameter("RandomSeed"));
		mPopulation = new Chromo[mParams.getIntParameter("PopulationSize")];
		mNumGenes = mParams.getIntParameter("NumGenes");
		mGeneSize = mParams.getIntParameter("GeneSize");
        mNumRuns = mParams.getIntParameter("NumRuns");
        mMinDnaValue = mParams.getIntParameter("MinDnaValue");
        mMaxDnaValue = mParams.getIntParameter("MaxDnaValue");
        mNumGenerations = mParams.getIntParameter("NumGenerations");

		mMutationType = mParams.getStringParameter("MutationType");
		mMutationRate = mParams.getDoubleParameter("MutationRate");

        mCrossOverRate = mParams.getDoubleParameter("CrossOverRate");
        mCrossOverType = mParams.getStringParameter("CrossOverType");
        mSelectionType = mParams.getStringParameter("SelectionType");

        String problemType = mParams.getStringParameter("ProblemType");

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

			// Print Statistics
			mStatistics.printRun();
		}

		mStatistics.printOverAll();
	}

    //============================================================================**
    // performReproduction()
    //============================================================================**

	private Chromo[] performReproduction(Chromo[] population)
	{
        Chromo[] newPopulation = new Chromo[population.length];

        for (int childIndex = 0; childIndex < population.length - 1; childIndex += 2)
        {
            Chromo parentA = selectParent(population);
            Chromo parentB = selectParent(population);

            Chromo childA = new Chromo(mRandomizer, mNumGenes, mGeneSize, mMinDnaValue, mMaxDnaValue);
            Chromo childB = new Chromo(mRandomizer, mNumGenes, mGeneSize, mMinDnaValue, mMaxDnaValue);

            // Do Crossover
            if(mRandomizer.nextDouble() < mCrossOverRate && !mCrossOverType.equalsIgnoreCase(NO_CROSSOVER))
            {
                if (mCrossOverType.equalsIgnoreCase(SINGLE_CROSSOVER))
                {
                    performOnePointCrossOver(parentA, parentB, childA, childB);
                }
                else
                {
                    System.err.println("Invalid Cross Over type. Expect Exceptions.");
                    return null;
                }
            }
            else
            {
                // Simply use the dna from the parents
                childA.copyDna(parentA);
                childB.copyDna(parentB);
            }

            // Add the children to the new population
            newPopulation[childIndex] = childA;
            newPopulation[childIndex + 1] = childB;
        }

        // Perform mutation
        if (!mMutationType.equalsIgnoreCase(NO_MUTATION))
        {
            for (int childIndex = 0; childIndex < population.length; childIndex++)
            {
                newPopulation[childIndex].doMutation(mMutationRate);
            }
        }

        return newPopulation;
	}

    //============================================================================**
    // performOnePointCrossOver()
    //============================================================================**

    private void performOnePointCrossOver(Chromo parentA, Chromo parentB, Chromo childA, Chromo childB)
	{
        // Create cross over points
        int crossOverGeneIndex = mRandomizer.nextInt(mNumGenes);
        int crossOverDnaIndex = mRandomizer.nextInt(mGeneSize);

        // Create child with parent A's first section and parent B's second section
        childA.performOnePointCrossover(parentA, parentB, crossOverGeneIndex, crossOverDnaIndex);

        // Create child with parent B's first section and parent A's second section
        childB.performOnePointCrossover(parentB, parentA, crossOverGeneIndex, crossOverDnaIndex);
	}

    //============================================================================**
    // performGeneration()
    //============================================================================**

    private Chromo[] performGeneration(Chromo[] population, int generationNum, int runNum)
	{
		// Evaluate the individual's fitness
		for (int individual = 0; individual < population.length; individual++)
		{
			mFitnessFunction.doRawFitness(population[individual]);
			mStatistics.recordSolution(generationNum, runNum, population[individual]);
		}

		// Cross-Over
		return performReproduction(population);
	}

    //============================================================================**
    // performRun()
    //============================================================================**

    private Chromo[] performRun(Chromo[] population, int runNum, boolean initChromos)
	{
		if(initChromos)
		{
			// Initialize population
			for (int individual = 0; individual < population.length; individual++)
			{
				population[individual] = new Chromo(mRandomizer, mNumGenes, mGeneSize, mMinDnaValue, mMaxDnaValue);
			}
		}

		// Perform generations
		for (int generationNum = 1; generationNum <= mNumGenerations; generationNum++)
		{
			population = performGeneration(population, generationNum, runNum);

			mStatistics.printGeneration();
		}

		return population;
	}

    //============================================================================**
    // selectParent()
    //============================================================================**

	private Chromo selectParent(Chromo[] population)
	{
		int parentIndex = -1;

		// Tournament
		if(mSelectionType.equalsIgnoreCase(TOURNAMENT_SELECTION))
        {
            parentIndex = selectParentTournament(population);
        }
        else if(mSelectionType.equalsIgnoreCase(FITNESS_PROPORTIONAL_SELECTION))
        {
            // TODO: Fitness Proportional Selection
        }
        else if(mSelectionType.equalsIgnoreCase(LINEAR_RANKING_SELECTION))
        {
            // TODO: Linear Ranking Selection
        }
        else
        {
            System.err.println("Unsupported Selection Type: " + mSelectionType + " Expect Exceptions.");
		}

		return population[parentIndex];
	}

    //============================================================================**
    // selectParentTournament()
    //============================================================================**

	private int selectParentTournament(Chromo[] population)
	{
		int parentIndex;

		// TODO: Set these parameters to configurable
		int tournamentSize = 20;
		double selectBestProbability = 0.8;

		int bestIndex = 0;

		for(int i = 0; i < tournamentSize; i++)
		{
			int nextIndex = mRandomizer.nextInt(population.length);
			if(population[nextIndex].getRawFitness() > population[bestIndex].getRawFitness())
			{
				bestIndex = nextIndex;
			}
		}

		// Sort by fitness in descending order

		if(mRandomizer.nextDouble() < selectBestProbability)
		{
			parentIndex = bestIndex;
		}
		else
		{
			parentIndex = mRandomizer.nextInt(population.length);
		}

		return parentIndex;
	}
}
