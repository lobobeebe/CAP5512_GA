
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

	private int mGenerationSumFitness;
	private int mRunSumAverageFitness;
	private int mRunSumBestFitness;
	
	private int mNumGenerations;
	private int mNumRuns;
	
	private FitnessFunction mFitnessFunction;
	
	public RunStatistics(FitnessFunction fitnessFunction)
	{
		mFitnessFunction = fitnessFunction;
	}
	
	public Chromo getBestChromoOfRun()
	{
		return mBestOfRunChromo;
	}
	
	public int getGenerationSumFitness()
	{
		return mGenerationSumFitness;
	}
	
	public void recordSolution(int generationNum, int runNum, Chromo solution)
	{
		Chromo clone = solution.clone();
		
		// Check if its the best of the generation.
		if(mBestOfGenChromo == null ||
			clone.mRawFitness < mBestOfGenChromo.mRawFitness)
		{
			mBestOfGenChromo = clone;
			mBestOfGenGeneration = generationNum;
			mBestOfGenRun = runNum;
			
			// Check if its the best of the run.
			// Can only be the best of the run if its also the best of the generation.
			if(mBestOfRunChromo == null ||
				clone.mRawFitness < mBestOfRunChromo.mRawFitness)
			{
				mBestOfRunChromo = clone;
				mBestOfRunGeneration = generationNum;
				mBestOfRunRun = runNum;
				
				// Check if its the best overall.
				// Can only be the best overall if its also the best of the run.

				if(mBestOverAllChromo == null ||
					clone.mRawFitness < mBestOverAllChromo.mRawFitness)
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
		System.out.println("\tBest: " + mFitnessFunction.getChromosomeToString(mBestOfGenChromo));
		
		// Reset the generation for new populations
		resetGeneration();
	}
	
	public void printRun()
	{
		System.out.println();
		System.out.println("Run " + mBestOfRunRun + ":");
		System.out.println("\tAverage: " + (mGenerationSumFitness / mNumGenerations));
		System.out.println("\tBest: Generation " + mBestOfRunGeneration + ": " + 
		    mFitnessFunction.getChromosomeToString(mBestOfRunChromo));
		
		// Reset the run for new populations
		resetRun();
	}
	
	public void printOverAll()
	{
		System.out.println();
		System.out.println();
		System.out.println("Over All:");
		System.out.println("\tAverage Average: " + (mRunSumAverageFitness / mNumRuns));
		System.out.println("\tAverage Best: " + (mRunSumBestFitness / mNumRuns));
		System.out.println("\tBest: Run " + mBestOverAllRun + ": Generation " + mBestOverAllGeneration
             + ": " + mFitnessFunction.getChromosomeToString(mBestOverAllChromo));
	}
	
	private void resetGeneration()
	{
		mGenerationSumFitness += mBestOfGenChromo.mRawFitness;
		
		mBestOfGenChromo = null;
		mBestOfGenGeneration = -1;
		mBestOfGenRun = -1;
		mNumGenerations++;
	}
	
	private void resetRun()
	{
		mRunSumBestFitness += mBestOfRunChromo.mRawFitness;
		mRunSumAverageFitness += (mGenerationSumFitness / mNumGenerations);
		mNumGenerations = 0;
		mGenerationSumFitness = 0;
		
		mBestOfRunChromo = null;
		mBestOfRunGeneration = -1;
		mBestOfRunRun = -1;
		mNumRuns++;
	}
}
