import java.util.Comparator;

class GeneIndexComparator implements Comparator<Integer>
{
	private Chromo mChromo;
	
	public GeneIndexComparator(Chromo chromo)
	{
		mChromo = chromo;
	}
	
	@Override
	public int compare(Integer arg0, Integer arg1)
	{
		return (mChromo.getGeneList()[arg0] - mChromo.getGeneList()[arg1]);
	}
}