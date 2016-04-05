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
		return (int)(mChromoList[o2].getRawFitness() - mChromoList[o1].getRawFitness());
	}
	
}
