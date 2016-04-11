//============================================================================**
// RunStatistics Class
//============================================================================**

public class RunStatistics
{
	//----------------------------------------------------------------------------**
	// Private member variables
	//----------------------------------------------------------------------------**

	private Chromo mBestOfGenChromo;
	private Chromo mBestOfRunChromo;
	private Chromo mBestOverAllChromo;
	private int mBestOverAllRun;
	private int mBestOverAllGeneration;

	private int mPopulationFitnessSum;
    private int mPopulationSquaredFitnessSum;

	private double mRunBestFitnessSum;
    private double mGenerationAverageFitnessSum;
    private double mRunAverageFitnessSum;

    private int mNumPopulation;
	private int mNumGenerations;
	private int mNumRuns;

	//============================================================================**
	// RunStatistics()
	//============================================================================**

	public RunStatistics()
	{
	}

	//============================================================================**
	// recordSolution()
	//============================================================================**

	public void recordSolution(int generationNum, int runNum, Chromo solution)
	{
		Chromo clone = new Chromo(null, solution.getNumGenes(), solution.getGeneSize(), 0, 0);
		clone.copyDna(solution);
        clone.setRawFitness(solution.getRawFitness());

        // Population Stats
        mPopulationFitnessSum += clone.getRawFitness();
        mPopulationSquaredFitnessSum += clone.getRawFitness() * clone.getRawFitness();
        mNumPopulation++;

		// Check if its the best of the generation.
		if(mBestOfGenChromo == null ||
			clone.getRawFitness() > mBestOfGenChromo.getRawFitness())
		{
			mBestOfGenChromo = clone;

			// Check if its the best of the run.
			// Can only be the best of the run if its also the best of the generation.
			if(mBestOfRunChromo == null ||
				clone.getRawFitness() > mBestOfRunChromo.getRawFitness())
			{
				mBestOfRunChromo = clone;

				// Check if its the best overall.
				// Can only be the best overall if its also the best of the run.

				if(mBestOverAllChromo == null ||
					clone.getRawFitness() > mBestOverAllChromo.getRawFitness())
				{
					mBestOverAllChromo = clone;
					mBestOverAllGeneration = generationNum;
					mBestOverAllRun = runNum;
				}
			}
		}
	}

	//============================================================================**
	// printGeneration()
	//============================================================================**

	public void printGeneration()
	{
        double generationAverageFitness = (mPopulationFitnessSum / (double) mNumPopulation);
        mGenerationAverageFitnessSum += generationAverageFitness;

        double stdDev = Math.sqrt(Math.abs(
                (mPopulationSquaredFitnessSum - ((mPopulationFitnessSum * mPopulationFitnessSum) / (double) mNumPopulation)))
                / (double) (mNumPopulation - 1));

		System.out.printf("%d\t%d\t%.3f\t%.3f\t%.3f\n", mNumRuns + 1, mNumGenerations + 1, mBestOfGenChromo.getRawFitness(),
				generationAverageFitness, stdDev);

		resetGeneration();
	}

	//============================================================================**
	// printRun()
	//============================================================================**

	public void printRun()
	{
        double bestFitnessOfRun = mBestOfRunChromo.getRawFitness();
        mRunBestFitnessSum += bestFitnessOfRun;
        mRunAverageFitnessSum += mGenerationAverageFitnessSum / (double)mNumGenerations;

		System.out.println("Executing FF Gene Output");
        System.out.println((mNumRuns + 1) + "\tB\t" + bestFitnessOfRun);
        System.out.println();
		// Reset the run for new populations
		resetRun();
	}

	//============================================================================**
	// printOverAll()
	//============================================================================**

	public void printOverAll()
	{
		System.out.println("Over All:");
		System.out.println("\tAverage Run Average Fitness: " + (mRunAverageFitnessSum / (double)mNumRuns));
		System.out.println("\tAverage Run Best Fitness: " + (mRunBestFitnessSum / (double)mNumRuns));
        System.out.println("\tBest Fitness: " + mBestOverAllChromo.getRawFitness());
        // TODO: Fix print outs
        System.out.println("\tBest Chromo: Run " + mBestOverAllRun + ": Generation " + mBestOverAllGeneration
             + ": " + mBestOverAllChromo);
	}

	//============================================================================**
	// resetGeneration()
	//============================================================================**

	private void resetGeneration()
	{
        mNumPopulation = 0;
        mPopulationFitnessSum = 0;
        mPopulationSquaredFitnessSum = 0;

		mBestOfGenChromo = null;
		mNumGenerations++;
	}

	//============================================================================**
	// resetRun()
	//============================================================================**

	private void resetRun()
	{
        mGenerationAverageFitnessSum = 0;
		mNumGenerations = 0;

		mBestOfRunChromo = null;
		mNumRuns++;
	}
}
