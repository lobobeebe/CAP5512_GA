
public class PersonToWeekdayMapping extends Hw2Mapping
{
	
	public String getChromosomeToString(Chromo chromo)
	{
		String chromoString = "Fitness: " + chromo.mRawFitness + "\n\t";
		
		for (int gene = 0; gene < 35; gene++)
		{
			chromoString += getPerson(chromo, gene);
			
			if (gene % Preferences.ShiftList.length == Preferences.ShiftList.length - 1)
			{
				chromoString += " ";
			}
		}
		
		return chromoString;
	}
	
	public int getGeneIndex(Chromo chromo, int day, int shift)
	{
		// There are 5 genes per day and 1 per shift.
		// TODO: Magic Number
		return day * 5 + shift;
	}
	
	@Override
	public int getDay(Chromo chromo, int geneIndex)
	{
		// Every 5 genes is a new day
		// TODO: Magic Number
		return geneIndex / 5;
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
		// Every gene within a subset of 5 is a new shift
		// TODO: Magic Number
		return geneIndex % 5;
	}
}
