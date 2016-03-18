
public class WeekdayToPersonMapping extends Hw2Mapping
{

	@Override
	public String getChromosomeToString(Chromo chromo)
	{
		String chromoString = "Fitness: " + chromo.mRawFitness + "\n\t";
		for (int gene = 0; gene < 35; gene++)
		{
			if(gene % Preferences.ShiftList.length == 0)
			{
				chromoString += "[";
			}
			
			chromoString += getDay(chromo, gene) + "." + getShift(chromo, gene);
			
			if (gene % Preferences.ShiftList.length == Preferences.ShiftList.length - 1)
			{
				chromoString += "] ";
			}
			else
			{
				chromoString += ", ";
			}
		}
		
		return chromoString;
	}

	@Override
	public int getDay(Chromo chromo, int geneIndex)
	{
		// Each day has 5 shifts
		return chromo.mSortedGeneListIndices[geneIndex] / 5;
	}

	@Override
	public int getPerson(Chromo chromo, int geneIndex)
	{
		// Each person has 5 shifts
		return geneIndex / 5;
	}

	@Override
	public int getShift(Chromo chromo, int geneIndex)
	{
		// Each of a subset of 5 is a shift
		return chromo.mSortedGeneListIndices[geneIndex] % 5;
	}

}
