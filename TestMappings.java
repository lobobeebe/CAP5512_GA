import java.util.Random;

public class TestMappings
{
    public static void main(String[] args)
    {
    	// Test PersonToWeekdayMapping
    	testPersonToWeekdayMapping();
    	
    	// Test PersonToShiftMapping
    	testPersonToShiftMapping();
    	
    	// Test WeekdayToPersonMapping
    	testPersonToPersonMapping();
    }
    
    public static void testPersonToWeekdayMapping()
    {
    	Random randomizer = new Random();
    	
    	Chromo chromo = new Chromo(randomizer);
    	
    	chromo.mGeneList = new Integer[]
    	{
    		28, 29, 32, 33, 34, 
    		2, 3, 12, 13, 14,
    		15, 26, 27, 30, 31,
    		5, 6, 7, 8, 9,
    		0, 1, 11, 18, 19,
    		4, 16, 17, 24, 25,
    		10, 20, 21, 22, 23 
    	};
    	
    	chromo.sortGeneIndices();
    	
    	PersonToWeekdayMapping mapping1 = new PersonToWeekdayMapping();
    	
    	// Test Days
    	Integer[] testDays = new Integer[]
		{
			0, 0, 0, 0, 0,
			1, 1, 1, 1, 1,
			2, 2, 2, 2, 2,
			3, 3, 3, 3, 3,
			4, 4, 4, 4, 4,
			5, 5, 5, 5, 5,
			6, 6, 6, 6, 6
		};
    	
    	boolean testPass = true;
    	for(int geneIndex = 0; geneIndex < 35; geneIndex++)
    	{
        	testPass &= (mapping1.getDay(chromo, geneIndex) == testDays[geneIndex]);
    	}
    	
    	System.out.println(testPass);
    	
    	// Test Shifts
    	Integer[] testShifts = new Integer[]
		{
			0, 1, 2, 3, 4,
			0, 1, 2, 3, 4,
			0, 1, 2, 3, 4,
			0, 1, 2, 3, 4,
			0, 1, 2, 3, 4,
			0, 1, 2, 3, 4,
			0, 1, 2, 3, 4,
		};
    	
    	testPass = true;
    	for(int geneIndex = 0; geneIndex < 35; geneIndex++)
    	{
        	testPass &= (mapping1.getShift(chromo, geneIndex) == testShifts[geneIndex]);
    	}
    	
    	System.out.println(testPass);
    	
    	// Test People
    	Integer[] testPeople = new Integer[]
		{
			4, 4, 1, 1, 5,
			3, 3, 3, 3, 3,
			6, 4, 1, 1, 1,
			2, 5, 5, 4, 4,
			6, 6, 6, 6, 5,
			5, 2, 2, 0, 0,
			2, 2, 0, 0, 0
		};
    	
    	testPass = true;
    	for(int geneIndex = 0; geneIndex < 35; geneIndex++)
    	{
        	testPass &= (mapping1.getPerson(chromo, geneIndex) == testPeople[geneIndex]);
    	}
    	
    	System.out.println(testPass);
    }
    
    public static void testPersonToShiftMapping()
    {
    	Random randomizer = new Random();
    	
    	Chromo chromo = new Chromo(randomizer);
    	
    	chromo.mGeneList = new Integer[]
    	{
    		28, 29, 32, 33, 34, 
    		2, 3, 12, 13, 14,
    		15, 26, 27, 30, 31,
    		5, 6, 7, 8, 9,
    		0, 1, 11, 18, 19,
    		4, 16, 17, 24, 25,
    		10, 20, 21, 22, 23 
    	};
    	
    	chromo.sortGeneIndices();
    	
    	PersonToShiftMapping mapping1 = new PersonToShiftMapping();
    	
    	// Test Days
    	Integer[] testDays = new Integer[]
		{
			0, 1, 2, 3, 4, 5, 6,
			0, 1, 2, 3, 4, 5, 6,
			0, 1, 2, 3, 4, 5, 6,
			0, 1, 2, 3, 4, 5, 6,
			0, 1, 2, 3, 4, 5, 6
		};
    	
    	boolean testPass = true;
    	for(int geneIndex = 0; geneIndex < 35; geneIndex++)
    	{
        	testPass &= (mapping1.getDay(chromo, geneIndex) == testDays[geneIndex]);
    	}
    	
    	System.out.println(testPass);
    	
    	// Test Shifts
    	Integer[] testShifts = new Integer[]
		{
			0, 0, 0, 0, 0, 0, 0,
			1, 1, 1, 1, 1, 1, 1,
			2, 2, 2, 2, 2, 2, 2,
			3, 3, 3, 3, 3, 3, 3,
			4, 4, 4, 4, 4, 4, 4
		};
    	
    	testPass = true;
    	for(int geneIndex = 0; geneIndex < 35; geneIndex++)
    	{
        	testPass &= (mapping1.getShift(chromo, geneIndex) == testShifts[geneIndex]);
    	}
    	
    	System.out.println(testPass);
    	
    	// Test People
    	Integer[] testPeople = new Integer[]
		{
			4, 4, 1, 1, 5,
			3, 3, 3, 3, 3,
			6, 4, 1, 1, 1,
			2, 5, 5, 4, 4,
			6, 6, 6, 6, 5,
			5, 2, 2, 0, 0,
			2, 2, 0, 0, 0
		};
    	
    	testPass = true;
    	for(int geneIndex = 0; geneIndex < 35; geneIndex++)
    	{
        	testPass &= (mapping1.getPerson(chromo, geneIndex) == testPeople[geneIndex]);
    	}
    	
    	System.out.println(testPass);
    }
    
    public static void testPersonToPersonMapping()
    {
    	Random randomizer = new Random();
    	
    	Chromo chromo = new Chromo(randomizer);
    	
    	chromo.mGeneList = new Integer[]
    	{
    		28, 29, 32, 33, 34, 
    		2, 3, 12, 13, 14,
    		15, 26, 27, 30, 31,
    		5, 6, 7, 8, 9,
    		0, 1, 11, 18, 19,
    		4, 16, 17, 24, 25,
    		10, 20, 21, 22, 23 
    	};
    	
    	chromo.sortGeneIndices();
    	
    	WeekdayToPersonMapping mapping1 = new WeekdayToPersonMapping();

    	// Test Days
    	Integer[] testDays = new Integer[]
		{
			4, 4, 1, 1, 5,
			3, 3, 3, 3, 3,
			6, 4, 1, 1, 1,
			2, 5, 5, 4, 4,
			6, 6, 6, 6, 5,
			5, 2, 2, 0, 0,
			2, 2, 0, 0, 0
		};
    	
    	boolean testPass = true;
    	for(int geneIndex = 0; geneIndex < 35; geneIndex++)
    	{
        	testPass &= (mapping1.getDay(chromo, geneIndex) == testDays[geneIndex]);
    	}
    	
    	System.out.println(testPass);
    	
    	// Test Shifts
    	Integer[] testShifts = new Integer[]
		{
			0, 1, 2, 3, 4,
			0, 1, 2, 3, 4,
			0, 1, 2, 3, 4,
			0, 1, 2, 3, 4,
			0, 1, 2, 3, 4,
			0, 1, 2, 3, 4,
			0, 1, 2, 3, 4,
		};
    	
    	testPass = true;
    	for(int geneIndex = 0; geneIndex < 35; geneIndex++)
    	{
        	testPass &= (mapping1.getShift(chromo, geneIndex) == testShifts[geneIndex]);
    	}
    	
    	System.out.println(testPass);
    	
    	// Test People
    	Integer[] testPeople = new Integer[]
		{
			0, 0, 0, 0, 0,
			1, 1, 1, 1, 1,
			2, 2, 2, 2, 2,
			3, 3, 3, 3, 3,
			4, 4, 4, 4, 4,
			5, 5, 5, 5, 5,
			6, 6, 6, 6, 6,
		};
    	
    	testPass = true;
    	for(int geneIndex = 0; geneIndex < 35; geneIndex++)
    	{
        	testPass &= (mapping1.getPerson(chromo, geneIndex) == testPeople[geneIndex]);
    	}
    	
    	System.out.println(testPass);
    }
}
