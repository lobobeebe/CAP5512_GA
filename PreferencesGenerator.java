/**
 * Includes
 */
import java.io.*;

/**
 * Class that will read in the preferences of Lab Monitors for HW2.
 */
public class PreferencesGenerator
{    
    /**
     * Reads an input file holding preferences, returns a Preferences object.
     * @param fileName
     * @return Object from which the preferences of Lab Monitors can be easily read.
     */
    public static Preferences generatePreferences(String fileName)
    {
        Preferences preferences = new Preferences();
        
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            
            String line = reader.readLine();
            
            // Read to the end of the file
            while (line != null)
            {
            	// The first line is a name
        		Person currentPerson = new Person(line);

    			line = reader.readLine();
    			
        		// For each shift line
        		for(int shift = 0; shift < Preferences.ShiftList.length; shift++)
        		{
        			String[] weekdayShiftPreferences = line.split("\t");
        			
        			// For each day of the week
        			for(int day = 0; day < Preferences.DayList.length; day++)
        			{
        				// Add the person's preference
        				currentPerson.addPreference(day, shift,
        						Integer.parseInt(weekdayShiftPreferences[day]));
        			}

        			line = reader.readLine();
        		}
        		
        		// Add the person to the preferences object
        		preferences.addPerson(currentPerson);
            }
            
            reader.close();
        }
        catch (IOException ioException) 
        {
            System.out.println("Error: " + ioException.getMessage());
        }
        
        return preferences;
    }
}