import java.util.ArrayList;

/**
 * Class that will read in the preferences of Lab Monitors for HW2.
 */
public class Preferences
{
	public enum Shift
	{
		FROM_8_TO_10,
		FROM_10_TO_12,
		FROM_12_TO_2,
		FROM_2_TO_4,
		FROM_4_TO_6
	}
	
	public static final Shift[] ShiftList =
	{
		Shift.FROM_8_TO_10,
		Shift.FROM_10_TO_12,
		Shift.FROM_12_TO_2,
		Shift.FROM_2_TO_4,
		Shift.FROM_4_TO_6
	};
	
	public enum Day
	{
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY,
		SUNDAY
	}
	
	public static final Day[] DayList =
	{
		Day.MONDAY,
		Day.TUESDAY,
		Day.WEDNESDAY,
		Day.THURSDAY,
		Day.FRIDAY,
		Day.SATURDAY,
		Day.SUNDAY
	};
	
	private ArrayList<Person> mPeopleList;
	
	public Preferences()
	{
		mPeopleList = new ArrayList<Person>();
	}
	
	public void addPerson(Person person)
	{
		mPeopleList.add(person);
	}
	
	public int getPreference(int person, int day, int shift)
	{
		boolean areParametersValid = true;
		
		if(person >= mPeopleList.size())
		{
			areParametersValid = false;
			System.out.println("Attempted to get a person by an index out of bounds.");
		}
		
		if(day >= DayList.length)
		{
			areParametersValid = false;
			System.out.println("Attempted to get a day by an index out of bounds.");
		}
		
		if(shift >= ShiftList.length)
		{
			areParametersValid = false;
			System.out.println("Attempted to get a shift by an index out of bounds.");
		}
		
		if(areParametersValid)
		{
			return mPeopleList.get(person).getPreference(day, shift);
		}
		else
		{
			return 0;
		}
	}
	
	public void printPreferences()
	{
		for (Person person : mPeopleList)
		{
			System.out.println(person.mName);
			
			for (int day = 0; day < DayList.length; day++)
			{
				System.out.print("Day " + day + ":");
				for(int shift = 0; shift < ShiftList.length; shift++)
				{
					System.out.print("\t" + person.getPreference(day, shift));
				}
				System.out.println();
			}
			
			System.out.println();
		}
	}
}