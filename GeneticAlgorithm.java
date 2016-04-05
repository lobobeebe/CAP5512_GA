import java.util.Arrays;
import java.util.Random;

class GeneticAlgorithm
{
    private static final String SINGLE_CROSSOVER = "SINGLE";

    private static final String TOURNAMENT_SELECTION = "TOURNAMENT";

	private ConfigManager mParams;
	private Random mRandomizer;

	private Chromo[] mPopulation;
	private int mNumGenes;
	private int mGeneSize;
    private int mMinDnaValue;
    private int mMaxDnaValue;
    private int mNumRuns;
    private int mNumGenerations;
    private String mCrossOverType;
    private double mCrossOverRate;
	private double mMutationRate;
    private String mSelectionType;

	private FitnessFunction mFitnessFunction;
	private RunStatistics mStatistics;

	GeneticAlgorithm(ConfigManager params)
	{
		mParams = params;
	}

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
		mMutationRate = mParams.getDoubleParameter("MutationRate");
        mCrossOverRate = mParams.getDoubleParameter("CrossOverRate");
        mCrossOverType = mParams.getStringParameter("CrossOverType");
        mSelectionType = mParams.getStringParameter("SelectionType");

        String problemType = mParams.getStringParameter("ProblemType");

		if (problemType.equals("PrisonersTournament"))
		{
			mFitnessFunction = new PrisonersTournament(mParams, mRandomizer);
		}
		else
		{
			System.err.println("Invalid problem Type: " + problemType);
		}

		mStatistics = new RunStatistics();
	}

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

	private Chromo[] performCrossOver(Chromo[] population)
	{
		// One Point
		if (mCrossOverType.equalsIgnoreCase(SINGLE_CROSSOVER))
		{
			return performOnePointCrossOver(population);
		}
		else
		{
			System.err.println("Invalid Cross Over type. Expect Exceptions.");
			return null;
		}
	}

	private Chromo[] performOnePointCrossOver(Chromo[] population)
	{
		Chromo[] newPopulation = new Chromo[population.length];

		// Do Crossover
		for (int childIndex = 0; childIndex < population.length - 1; childIndex += 2)
		{
			Chromo parentA = selectParent(population);
			Chromo parentB = selectParent(population);

			Chromo childA = new Chromo(mRandomizer, mNumGenes, mGeneSize, mMinDnaValue, mMaxDnaValue);
            Chromo childB = new Chromo(mRandomizer, mNumGenes, mGeneSize, mMinDnaValue, mMaxDnaValue);

			if(mRandomizer.nextDouble() < mCrossOverRate)
			{
                // Create cross over points
				int crossOverGeneIndex = mRandomizer.nextInt(mNumGenes);
				int crossOverDnaIndex = mRandomizer.nextInt(mGeneSize);

				// Create child with parent A's first section and parent B's second section
				childA.performOnePointCrossover(parentA, parentB, crossOverGeneIndex, crossOverDnaIndex);

				// Create child with parent B's first section and parent A's second section
				childB.performOnePointCrossover(parentB, parentA, crossOverGeneIndex, crossOverDnaIndex);

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
		for (int childIndex = 0; childIndex < population.length; childIndex++)
		{
			newPopulation[childIndex].doMutation(mMutationRate);
		}

		return newPopulation;
	}

	private Chromo[] performGeneration(Chromo[] population, int generationNum, int runNum)
	{
		// Evaluate the individual's fitness
		for (int individual = 0; individual < population.length; individual++)
		{
			mFitnessFunction.doRawFitness(population[individual], population);
			mStatistics.recordSolution(generationNum, runNum, population[individual]);
		}

		// Cross-Over
		return performCrossOver(population);
	}

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

	private Chromo selectParent(Chromo[] population)
	{
		int parentIndex = -1;


		// Tournament
		if(mSelectionType.equalsIgnoreCase(TOURNAMENT_SELECTION))
        {
            parentIndex = selectParentTournament(population);
        }
        else
        {
            System.out.println("Unsupported Selection Type: " + mSelectionType + " Expect Exceptions.");
		}

		return population[parentIndex];
	}

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
