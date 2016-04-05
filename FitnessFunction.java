/******************************************************************************
*  A Teaching GA					  Developed by Hal Stringer & Annie Wu, UCF
*  Version 2, January 18, 2004
*******************************************************************************/


public abstract class FitnessFunction
{

	public String mName;

	public FitnessFunction(String name)
	{
		mName = name;
		System.out.println("Setting up Fitness Function....." + mName);
	}

	public abstract void doRawFitness(Chromo X, Chromo[] population);
}

