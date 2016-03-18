import java.util.Arrays;
import java.util.Random;

public class Chromo
{
	private int[][] mDnaList;
	private int mNumGenes;
    private int mGeneSize;
    
	public double mRawFitness;
	public Random mRandomizer;

	public Chromo(Random randomizer, int numGenes, int geneSize)
	{
		mRandomizer = randomizer;
        mNumGenes = numGenes;
        mGeneSize = geneSize;
		
		//  Set gene list to a sequence of random keys
		mDnaList = new Integer[mNumGenes][mGeneSize];
		
		for (int geneIndex = 0; geneIndex < mNumGenes; geneIndex++)
		{
            for (int dnaIndex = 0; dnaIndex < mGeneSize; dnaIndex++)
            {
                mDnaList[geneIndex][dnaIndex] = mRandomizer.nextInt(100);   
            }
		}
	}
    
    // TODO: Mutation rate as parameter?
	public void doMutation()
	{		
        for (int geneIndex = 0; geneIndex < mNumGenes; geneIndex++)
        {
            for(int dnaIndex = 0; dnaIndex < mGeneSize; dnaIndex++)
            {
                if (mRandomizer.nextDouble() < Parameters.mutationRate)
                {
                    // Flip the DNA value
                    mDnaList[geneIndex][dnaIndex] = 1 - mDnaList[geneIndex];
                }
            }
        }
	}
	
	public int[] getDnaList()
	{
		return mDnaList;
	}
}
