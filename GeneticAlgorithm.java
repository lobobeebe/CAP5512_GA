import java.util.Arrays;
import java.util.Random;

class GeneticAlgorithm
{
	private Parameters mParams;
	private Random mRandomizer;
	
	private Chromo[] mPopulation;
	private int mNumGenes;
	private int mGeneSize;
	private double mMutationRate;

	private FitnessFunction mFitnessFunction;
	private RunStatistics mStatistics;
	
	public GeneticAlgorithm(Parameters params)
	{
		mParams = params;
	}
	
	public void setup()
	{
		mRandomizer = new Random(mParams.seed);
		mPopulation = new Chromo[mParams.popSize];
		mNumGenes = mParams.numGenes;
		mGeneSize = mParams.geneSize;
		mMutationRate = mParams.mutationRate;

		// TODO: Magic string
		if (mParams.problemType.equals("PT"))
		{
			mFitnessFunction = new PrisonersTournament(mNumGenes, mGeneSize);
		}
		else
		{
			System.err.println("Invalid problem Type: " + mParams.problemType);
		}
		
		mStatistics = new RunStatistics(mFitnessFunction);
	}
	
	public void begin()
	{
		// Do regular runs
		for (int runNum = 1; runNum <= mParams.numRuns; runNum++)
		{
			// Perform a single run with new chromos
			performRun(mPopulation, runNum, true);

			// Print Statistics
			mStatistics.printRun();
		}
		
		mStatistics.printOverAll();
	}
	
	public Chromo[] performCrossOver(Chromo[] population)
	{
		// One Point
		if (mParams.xoverType == 1)
		{
			return performOnePointCrossOver(population);
		}
		else
		{
			System.err.println("Invalid Cross Over type. Expect Exceptions.");
			return null;
		}
	}

	// TODO: Consider putting single crossover function in Chromo?
	public Chromo[] performOnePointCrossOver(Chromo[] population)
	{
		Chromo[] newPopulation = new Chromo[population.length];
		
		// Do Crossover
		for (int childIndex = 0; childIndex < population.length - 1; childIndex += 2)
		{
			Chromo parentA = selectParent(population);
			Chromo parentB = selectParent(population);

			Chromo childA = new Chromo(mRandomizer, mNumGenes, mGeneSize);
            Chromo childB = new Chromo(mRandomizer, mNumGenes, mGeneSize);
			
			if(mRandomizer.nextDouble() < mParams.xoverRate)
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
	
	public Chromo[] performGeneration(Chromo[] population, int generationNum, int runNum)
	{
		// Evaluate the individual's fitness
		for (int individual = 0; individual < population.length; individual++)
		{
			mFitnessFunction.doRawFitness(population[individual]);
			mStatistics.recordSolution(generationNum, runNum, population[individual]);
		}
		
		// Cross-Over
		return performCrossOver(population);
	}
	
	public Chromo[] performRun(Chromo[] population, int runNum, boolean initChromos)
	{
		if(initChromos)
		{
			// Initialize population
			for (int individual = 0; individual < population.length; individual++)
			{
				population[individual] = new Chromo(mRandomizer, mNumGenes, mGeneSize);
			}
		}
		
		// Perform generations
		for (int generationNum = 1; generationNum <= mParams.generations; generationNum++)
		{
			population = performGeneration(population, generationNum, runNum);
			
			mStatistics.printGeneration();
		}
		
		return population;
	}
	
	private Chromo selectParent(Chromo[] population)
	{
		int parentIndex = -1;
		
		switch (mParams.selectType)
		{
			// Tournament
			case 2:
				parentIndex = selectParentTournament(population);
				break;
				
			// No Selection
			default:
				// TODO: Make selectType parameter?
				System.out.println("Unsupported Selection Type: " + mParams.selectType + " Expect Exceptions.");
				break;
		}
		
		return population[parentIndex];
	}
	
	private int selectParentTournament(Chromo[] population)
	{
		// TODO: Set these parameters to configurable
		int tournamentSize = 20;
		double selectBestProbability = 0.7;
		
		int bestIndex;
		
		Integer[] tournament = new Integer[tournamentSize];
		
		for(int i = 0; i < tournamentSize; i++)
		{
			// Choose a random index from the total population
			tournament[i] = mRandomizer.nextInt(population.length);
		}
		
		// Sort by fitness
		Arrays.sort(tournament, new FitnessComparator(population));
		
		if(mRandomizer.nextDouble() < selectBestProbability)
		{
			bestIndex = tournament[0];
		}
		else
		{
			bestIndex = tournament[mRandomizer.nextInt(tournamentSize)];
		}
		
		return bestIndex;
	}
}
