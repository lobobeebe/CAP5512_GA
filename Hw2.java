import java.util.Random;

/******************************************************************************
*  A Teaching GA					  Developed by Hal Stringer & Annie Wu, UCF
*  Version 2, January 18, 2004
*******************************************************************************/


public class Hw2 extends FitnessFunction
{
	private Preferences mPreferences;
	private Hw2Mapping mMapping;
	
	public Hw2(Random randomizer)
	{
		super("HW2 Problem");
		
        mPreferences = PreferencesGenerator.generatePreferences(Parameters.dataInputFileName);
        
        switch (Parameters.mapping)
        {
        	// Person to Weekday group by Day
        	case 1:
        		mMapping = new PersonToWeekdayMapping();
        		break;
        	// Person to Weekday group by shift
        	case 2:
        		mMapping = new PersonToShiftMapping();
        		break;
        	// Weekday to Person
        	case 3:
        		mMapping = new WeekdayToPersonMapping();
        		break;
        	// Invalid case
    		default:
    			System.out.println("No mapping selected. Expect Exceptions.");
    			break;
        }
	}
	
	public void doRawFitness(Chromo chromo)
	{
		chromo.sortGeneIndices();

		chromo.mRawFitness = 0;
		
		for(int geneNum = 0; geneNum < Parameters.numGenes; geneNum++)
		{
			int person = mMapping.getPerson(chromo, geneNum);
			int day = mMapping.getDay(chromo, geneNum);
			int shift = mMapping.getShift(chromo, geneNum);

			int fitness = mPreferences.getPreference(person, day, shift);
			
			if(fitness == 0)
			{
				fitness = 5;
			}
			
			chromo.mRawFitness += (fitness * fitness);
		}
	}

	@Override
	public String getChromosomeToString(Chromo chromo)
	{
		return mMapping.getChromosomeToString(chromo);
	}	
}
