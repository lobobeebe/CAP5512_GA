import java.util.Arrays;
import java.util.Random;

public class Chromo implements Cloneable
{
	public Integer[] mGeneList;
	public Integer[] mSortedGeneListIndices;
	
	public double mRawFitness;
	public Random mRandomizer;

	public Chromo(Random randomizer)
	{
		mRandomizer = randomizer;
		
		//  Set gene list to a sequence of random keys
		mGeneList = new Integer[35];
		mSortedGeneListIndices = new Integer[35];
		
		for (int i = 0; i < 35; i++)
		{
			mGeneList[i] = mRandomizer.nextInt(100);
			mSortedGeneListIndices[i] = i;
		}
		
		mRawFitness = 9999999;   //  Fitness not yet evaluated
	}
	
	public Chromo clone()
	{
		Chromo clone = new Chromo(mRandomizer);
		
		clone.mGeneList = mGeneList.clone();
		clone.mSortedGeneListIndices = mSortedGeneListIndices.clone();
		clone.mRawFitness = mRawFitness;
		
		return clone;
	}

	public void sortGeneIndices()
	{
		Arrays.sort(mSortedGeneListIndices, new GeneIndexComparator(this));
	}

	public void doMutation()
	{
		sortGeneIndices();
		
		// Mutation per Gene
		if(Parameters.mutationType == 1)
		{
			for (int j = 0; j < Parameters.geneSize * Parameters.numGenes; j++)
			{
				if (mRandomizer.nextDouble() < Parameters.mutationRate)
				{
					int mutationIndex = mRandomizer.nextInt(35);
					
					int tempValue = mGeneList[mSortedGeneListIndices[mutationIndex]];
					mGeneList[mSortedGeneListIndices[mutationIndex]] = mGeneList[mSortedGeneListIndices[j]];
					mGeneList[mSortedGeneListIndices[j]] = tempValue;
				}
			}
		}
		// Mutation per Chromosome
		else if(Parameters.mutationType == 2)
		{
			double mutationRandom = mRandomizer.nextDouble();
			if (mutationRandom < Parameters.mutationRate)
			{
				for (int i = 0; i < Parameters.numGenes; i++)
				{
					mGeneList[i] = mRandomizer.nextInt(100);
				}
			}
		}
		else
		{
			System.out.println("Invalid mutation type");
		}
	}
	
	public Integer[] getGeneList()
	{
		return mGeneList;
	}
}
