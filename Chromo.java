import java.util.Random;

class Chromo
{
	private int[][] mGeneList;
	private int mNumGenes;
    private int mGeneSize;
    
	private double mRawFitness;
	private Random mRandomizer;

	Chromo(Random randomizer, int numGenes, int geneSize)
	{
		mRandomizer = randomizer;
        mNumGenes = numGenes;
        mGeneSize = geneSize;
		
		//  Set gene list to a sequence of random keys
		mGeneList = new int[mNumGenes][mGeneSize];

		if (mRandomizer != null)
		{
			for (int geneIndex = 0; geneIndex < mNumGenes; geneIndex++)
			{
				for (int dnaIndex = 0; dnaIndex < mGeneSize; dnaIndex++)
				{
					mGeneList[geneIndex][dnaIndex] = mRandomizer.nextInt(2);
				}
			}
		}
	}

	void copyDna(Chromo other)
	{
		// Copy all genes from other
		for(int geneIndex = 0; geneIndex < mNumGenes; geneIndex++)
		{
			mGeneList[geneIndex] = other.mGeneList[geneIndex].clone();
		}
	}

	void doMutation(double mutationRate)
	{		
        for (int geneIndex = 0; geneIndex < mNumGenes; geneIndex++)
        {
            for(int dnaIndex = 0; dnaIndex < mGeneSize; dnaIndex++)
            {
                if (mRandomizer.nextDouble() < mutationRate)
                {
                    // Flip the DNA value
					mGeneList[geneIndex][dnaIndex] = 1 - mGeneList[geneIndex][dnaIndex];
                }
            }
        }
	}

	public String toString()
	{
		String stringRepresentation = "";

		for (int geneIndex = 0; geneIndex < mNumGenes; geneIndex++)
		{
			for (int dnaIndex = 0; dnaIndex < mGeneSize; dnaIndex++)
			{
				stringRepresentation += (mGeneList[geneIndex][dnaIndex] + " ");
			}
		}

		return stringRepresentation;
	}

	int[][] getGeneList()
	{
		return mGeneList;
	}

	int getGeneSize()
	{
		return mGeneSize;
	}

	int getNumGenes()
	{
		return mNumGenes;
	}

	double getRawFitness()
	{
		return mRawFitness;
	}

	void performOnePointCrossover(Chromo parentA, Chromo parentB, int crossOverGeneIndex, int crossOverDnaIndex)
	{
		// Take the genes before the cross over point from parent A
		for(int geneIndex = 0; geneIndex < crossOverGeneIndex; geneIndex++)
		{
			mGeneList[geneIndex] = parentA.mGeneList[geneIndex].clone();
		}

		// Take the DNA before the DNA cross over point in the cross over gene from parent A
		for(int dnaIndex = 0; dnaIndex < crossOverDnaIndex; dnaIndex++)
		{
			mGeneList[crossOverGeneIndex][dnaIndex] = parentA.mGeneList[crossOverGeneIndex][dnaIndex];
		}

		// Take the DNA at and after the DNA cross over in the cross over gene from parent B
		for(int dnaIndex = crossOverDnaIndex; dnaIndex < mGeneSize; dnaIndex++)
		{
			mGeneList[crossOverGeneIndex][dnaIndex] = parentB.mGeneList[crossOverGeneIndex][dnaIndex];
		}

		// Take the rest of the genes from parent B
		for(int geneIndex = crossOverGeneIndex; geneIndex < mNumGenes; geneIndex++)
		{
			mGeneList[geneIndex] = parentB.mGeneList[geneIndex].clone();
		}
	}

	void setRawFitness(double fitness)
	{
		mRawFitness = fitness;
	}
}
