import java.util.Comparator;

public class FitnessComparator implements Comparator<Integer>
{
	private Chromo[] mChromoList;
	
	public FitnessComparator(Chromo[] chromoList)
	{
		mChromoList = chromoList;
	}
	
	@Override
	public int compare(Integer o1, Integer o2)
	{
		return (int)(mChromoList[o1].mRawFitness - mChromoList[o2].mRawFitness);
	}
	
}
