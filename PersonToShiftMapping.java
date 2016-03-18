
public class PersonToShiftMapping extends Hw2Mapping
{
	public String getChromosomeToString(Chromo chromo)
	{
		String chromoString = "Fitness: " + chromo.mRawFitness + "\n\t";
		
		for (int gene = 0; gene < 35; gene++)
		{
			chromoString += getPerson(chromo, gene);
			
			if (gene % Preferences.DayList.length == Preferences.DayList.length - 1)
			{
				chromoString += " ";
			}
		}
		
		return chromoString;
	}
	
	@Override
	public int getDay(Chromo chromo, int geneIndex)
	{
		// Every gene within a subset of 7 is a new day
		// TODO: Magic Number
		return geneIndex % 7;
	}

	@Override
	public int getPerson(Chromo chromo, int geneIndex)
	{
		// TODO: Magic Number
		return chromo.mSortedGeneListIndices[geneIndex] / 5;
	}

	@Override
	public int getShift(Chromo chromo, int geneIndex)
	{
		// Every 7 genes is a new shift
		// TODO: Magic Number
		return geneIndex / 7;
	}
}
