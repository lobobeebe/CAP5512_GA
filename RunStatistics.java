
public class RunStatistics
{
	private Chromo mBestOfGenChromo;
	private int mBestOfGenRun;
	private int mBestOfGenGeneration;
	private Chromo mBestOfRunChromo;
	private int mBestOfRunGeneration;
	private int mBestOfRunRun;
	private Chromo mBestOverAllChromo;
	private int mBestOverAllRun;
	private int mBestOverAllGeneration;

	private int mPopulationSumFitness;
	private int mGenerationSumFitness;
	private int mRunSumAverageFitness;
	private int mRunSumBestFitness;

    private int mNumPopulation;
	private int mNumGenerations;
	private int mNumRuns;
	
	private FitnessFunction mFitnessFunction;
	
	public RunStatistics(FitnessFunction fitnessFunction)
	{
		mFitnessFunction = fitnessFunction;
	}

	public void recordSolution(int generationNum, int runNum, Chromo solution)
	{
		Chromo clone = new Chromo(null, solution.getNumGenes(), solution.getGeneSize());
		clone.copyDna(solution);
        clone.setRawFitness(solution.getRawFitness());

        // Population Stats
        mPopulationSumFitness += clone.getRawFitness();
        mNumPopulation++;

        // Generation Stats
        mGenerationSumFitness += clone.getRawFitness();

		// Check if its the best of the generation.
		if(mBestOfGenChromo == null ||
			clone.getRawFitness() > mBestOfGenChromo.getRawFitness())
		{
			mBestOfGenChromo = clone;
			mBestOfGenGeneration = generationNum;
			mBestOfGenRun = runNum;
			
			// Check if its the best of the run.
			// Can only be the best of the run if its also the best of the generation.
			if(mBestOfRunChromo == null ||
				clone.getRawFitness() > mBestOfRunChromo.getRawFitness())
			{
				mBestOfRunChromo = clone;
				mBestOfRunGeneration = generationNum;
				mBestOfRunRun = runNum;
				
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
	
	public void printGeneration()
	{
		System.out.println("Run " + mBestOfGenRun + " Generation " + mBestOfGenGeneration + ":");
		// TODO: Fix print outs
        System.out.println("\tAverage Fitness: " + (mPopulationSumFitness / mNumPopulation));
        System.out.println("\tBest Fitness: " + mBestOfGenChromo.getRawFitness());
        System.out.println("\tBest Chromo Of Population: " + mBestOfGenChromo);
		
		// Reset the generation for new populations
		resetGeneration();
	}
	
	public void printRun()
	{
		System.out.println();
		System.out.println("Run " + mBestOfRunRun + ":");
		System.out.println("\tAverage Fitness: " + (mGenerationSumFitness / mNumGenerations));
        // TODO: Fix print outs
		System.out.println("\tBest Fitness: " + mBestOfRunChromo.getRawFitness());
        System.out.println("\tBest Chromo Of Run: Generation " + mBestOfRunGeneration + ": " + mBestOfRunChromo);
		// Reset the run for new populations
		resetRun();
	}
	
	public void printOverAll()
	{
		System.out.println();
		System.out.println();
		System.out.println("Over All:");
		System.out.println("\tAverage Run Average Fitness: " + (mRunSumAverageFitness / mNumRuns));
		System.out.println("\tAverage Run Best Fitness: " + (mRunSumBestFitness / mNumRuns));
        System.out.println("\tBest Fitness: " + mBestOverAllChromo.getRawFitness());
        // TODO: Fix print outs
        System.out.println("\tBest Chromo: Run " + mBestOverAllRun + ": Generation " + mBestOverAllGeneration
             + ": " + mBestOverAllChromo);
	}
	
	private void resetGeneration()
	{
        mPopulationSumFitness = 0;
        mNumPopulation = 0;

		mBestOfGenChromo = null;
		mBestOfGenGeneration = -1;
		mBestOfGenRun = -1;
		mNumGenerations++;
	}
	
	private void resetRun()
	{
		mRunSumBestFitness += mBestOfRunChromo.getRawFitness();
		mRunSumAverageFitness += (mGenerationSumFitness / mNumGenerations);
		mNumGenerations = 0;
		mGenerationSumFitness = 0;
		
		mBestOfRunChromo = null;
		mBestOfRunGeneration = -1;
		mBestOfRunRun = -1;
		mNumRuns++;
	}
}
