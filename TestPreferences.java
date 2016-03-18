
public class TestPreferences
{
    public static void main(String[] args)
    {
    	Preferences preferences = PreferencesGenerator.generatePreferences("hw2data.txt");
    	
    	Integer[][][] testPreferences = new Integer[][][]
    	{
    		// Alex
    		new Integer[][]
    		{
    			new Integer[] {3, 0, 3, 0, 4, 1, 2},
    			new Integer[] {3, 0, 3, 0, 4, 1, 2},
    			new Integer[] {0, 4, 0, 4, 0, 1, 2},
    			new Integer[] {0, 4, 0, 4, 0, 1, 2},
    			new Integer[] {3, 4, 4, 4, 4, 1, 2}
    		},
    		// Bonnie
    		new Integer[][]
    		{
    			new Integer[] {2, 4, 2, 4, 3, 4, 3},
    			new Integer[] {2, 4, 2, 4, 3, 4, 3},
    			new Integer[] {1, 0, 1, 0, 3, 4, 4},
    			new Integer[] {1, 0, 1, 0, 4, 4, 4},
    			new Integer[] {2, 0, 1, 0, 4, 4, 4}
    		},
    		// Colin
    		new Integer[][]
    		{
    			new Integer[] {2, 2, 2, 2, 2, 1, 1},
    			new Integer[] {3, 3, 3, 3, 3, 1, 1},
    			new Integer[] {0, 0, 0, 0, 0, 1, 4},
    			new Integer[] {0, 0, 0, 0, 0, 4, 4},
    			new Integer[] {0, 4, 0, 4, 0, 4, 4}
    		},
    		// Danielle
    		new Integer[][]
    		{
    			new Integer[] {4, 2, 2, 0, 0, 4, 4},
    			new Integer[] {4, 2, 2, 0, 0, 4, 4},
    			new Integer[] {4, 2, 1, 0, 0, 4, 3},
    			new Integer[] {4, 1, 1, 0, 0, 3, 3},
    			new Integer[] {4, 1, 1, 0, 0, 3, 3}
    		},
    		// Earl
    		new Integer[][]
    		{
    			new Integer[] {1, 3, 2, 4, 4, 2, 2},
    			new Integer[] {1, 3, 1, 4, 4, 2, 2},
    			new Integer[] {1, 3, 1, 4, 4, 0, 0},
    			new Integer[] {0, 3, 0, 4, 4, 0, 0},
    			new Integer[] {0, 3, 0, 4, 4, 0, 0}
    		},
    		// Fiona
    		new Integer[][]
    		{
    			new Integer[] {0, 3, 0, 3, 0, 1, 4},
    			new Integer[] {2, 3, 2, 3, 2, 1, 4},
    			new Integer[] {0, 4, 0, 3, 0, 2, 4},
    			new Integer[] {0, 4, 0, 4, 0, 2, 4},
    			new Integer[] {1, 4, 1, 4, 1, 4, 4}
    		},
    		// Gaston
    		new Integer[][]
    		{
    			new Integer[] {0, 0, 1, 0, 2, 4, 3},
    			new Integer[] {0, 4, 1, 4, 2, 4, 3},
    			new Integer[] {0, 4, 1, 4, 2, 4, 3},
    			new Integer[] {1, 4, 4, 4, 2, 4, 3},
    			new Integer[] {1, 0, 4, 0, 2, 4, 3}
    		},
    	};
    	
    	boolean testPass = true;
    	
    	for (int person = 0; person < 7; person++)
    	{
    		for (int shift = 0; shift < 5; shift++)
    		{
    			for (int day = 0; day < 7; day++)
    			{
    				testPass &= (testPreferences[person][shift][day] == preferences.getPreference(person, day, shift));
    			}
    		}
    	}
    	
    	System.out.println(testPass);
    }
}
