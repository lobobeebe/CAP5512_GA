import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm
{
	private Parameters mParams;
	private Random mRandomizer;
	
	private Chromo[] mPopulation;
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
		
		// TODO: Magic string
		if (mParams.problemType.equals("HW2"))
		{
			mFitnessFunction = new Hw2(mRandomizer);
		}
		
		mStatistics = new RunStatistics(mFitnessFunction);
	}
	
	public void begin()
	{
		// Do regular runs
		int runNum = 1;
		for (; runNum <= mParams.numRuns; runNum++)
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
		else if (mParams.xoverType == 4)
		{
			return performPmxCrossOver(population);
		}
		else
		{
			System.out.println("Invalid Cross Over type. Expect Exceptions.");
			return null;
		}
	}
	
	public void normalizeForPmx(Chromo chromo)
	{
		for (int geneIndex = 0; geneIndex < chromo.mSortedGeneListIndices.length; geneIndex++)
		{
			chromo.mGeneList[geneIndex] = chromo.mSortedGeneListIndices[geneIndex];
		}
	}
	
	public Chromo[] performPmxCrossOver(Chromo[] population)
	{
		Chromo[] newPopulation = new Chromo[population.length];
		
		for (int childIndex = 0; childIndex < population.length; childIndex++)
		{
			Chromo parentA = selectParent(population);
			normalizeForPmx(parentA);
			Chromo parentB = selectParent(population);
			normalizeForPmx(parentB);
			
			// TODO: Magic Number
			int startPoint = mRandomizer.nextInt(35);
			int length = mRandomizer.nextInt(35 - startPoint);
			
			boolean[] usedValueArray = new boolean[35];
			ArrayList<Integer> unusedParentBValues = new ArrayList<Integer>();
			
			Chromo child = new Chromo(mRandomizer);
			Arrays.fill(child.mGeneList, -1);
			System.arraycopy(parentA.mGeneList, startPoint, child.mGeneList, startPoint, length);
			
			// Mark the swath from parentA as used
			for (int i = startPoint; i < startPoint + length; i++)
			{
				usedValueArray[child.mGeneList[i]] = true;			
			}
			
			// Fill the rest of the child with unused values from B
			for (int i = 0; i < 35; i++)
			{
				// Only copy outside of the swath patch
				if(i < startPoint || i >= startPoint + length)
				{
					// Check if the value has been used
					if(usedValueArray[parentB.mGeneList[i]])
					{
						// Search for the first unused value in the swath
						for (int j = startPoint; j < startPoint + length; j++)
						{
							if(!usedValueArray[parentB.mGeneList[j]])
							{
								child.mGeneList[i] = parentB.mGeneList[j];
								usedValueArray[parentB.mGeneList[j]] = true;
								break;
							}
						}
					}
					else
					{
						// Copy the data from ParentB directly
						child.mGeneList[i] = parentB.mGeneList[i];
						usedValueArray[parentB.mGeneList[i]] = true;
					}
				}
			}
			
			newPopulation[childIndex] = child;
		}
		
		return newPopulation;
	}
	
	public Chromo[] performOnePointCrossOver(Chromo[] population)
	{
		Chromo[] newPopulation = new Chromo[population.length];
		
		// Do Crossover
		for (int childIndex = 0; childIndex < population.length - 1; childIndex += 2)
		{
			Chromo parentA = selectParent(population);
			Chromo parentB = selectParent(population);			
			
			if(mRandomizer.nextDouble() < mParams.xoverRate)
			{
				int crossOverIndex = mRandomizer.nextInt(mParams.numGenes);

				// Create child with parent A's first section and parent B's second section
				newPopulation[childIndex] = new Chromo(mRandomizer);
				System.arraycopy(parentA.mGeneList, 0, newPopulation[childIndex].mGeneList, 0, crossOverIndex);
				System.arraycopy(parentB.mGeneList, crossOverIndex, newPopulation[childIndex].mGeneList, crossOverIndex, 
						newPopulation[childIndex].mGeneList.length - crossOverIndex);

				// Create child with parent B's first section and parent A's second section
				newPopulation[childIndex + 1] = new Chromo(mRandomizer);
				System.arraycopy(parentB.mGeneList, 0, newPopulation[childIndex + 1].mGeneList, 0, crossOverIndex);
				System.arraycopy(parentA.mGeneList, crossOverIndex, newPopulation[childIndex].mGeneList, crossOverIndex, 
						newPopulation[childIndex].mGeneList.length - crossOverIndex);
			}
			else
			{
				newPopulation[childIndex] = parentA.clone();
				newPopulation[childIndex + 1] = parentB.clone();
			}
		}
		
		// Perform mutation
		for (int childIndex = 0; childIndex < population.length; childIndex++)
		{
			newPopulation[childIndex].doMutation();
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
				population[individual] = new Chromo(mRandomizer);
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
	
	public Chromo selectParent(Chromo[] population)
	{
		int parentIndex = -1;
		
		switch (mParams.selectType)
		{
			// Proportional
			case 1:
				parentIndex = selectParentProportional(population);
				break;
				
			// Tournament
			case 2:
				parentIndex = selectParentTournament(population);
				break;
				
			// No Selection
			default:
				System.out.println("Unsupported Selection Type. Expect Exceptions.");
				break;
		}
		
		return population[parentIndex];
	}

	private int selectParentProportional(Chromo[] population)
	{
		Chromo[] tempPopulation = new Chromo[population.length];
		Integer[] sortedPopulationIndices = new Integer[population.length];
		
		for (int i = 0; i < population.length; i++)
		{
			sortedPopulationIndices[i] = i;
			tempPopulation[i] = population[i].clone();
		}
		// Sort by fitness
		Arrays.sort(sortedPopulationIndices, new FitnessComparator(tempPopulation));
		
		// Set the temp population's fitness to the scale fitness
		double sumScaleFitness = 0;
		for (int i = 0; i < population.length; i++)
		{
			tempPopulation[sortedPopulationIndices[i]].mRawFitness = population.length - 1 - i;
			sumScaleFitness += population.length - 1 - i;
		}
		
		// Set the temp population's fitness to the proportional fitness
		for (int i = 0; i < population.length; i++)
		{
			tempPopulation[i].mRawFitness = tempPopulation[i].mRawFitness / sumScaleFitness;
		}
		
		double random = mRandomizer.nextDouble();
		double wheel = 0;
		
		for(int chromoIndex = 0; chromoIndex < population.length; chromoIndex++)
		{
			wheel += tempPopulation[chromoIndex].mRawFitness;
			
			if(wheel > random)
			{
				return chromoIndex;
			}
		}
		
		return Parameters.popSize - 1;
	}
	
	private int selectParentTournament(Chromo[] population)
	{
		// TODO: Set these parameters to configurable
		int tournamentSize = 20;
		double selectBestProbability = 0.7;
		
		int bestIndex = -1;
		
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
