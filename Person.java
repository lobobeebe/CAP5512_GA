import java.util.ArrayList;

public class Person
{
	String mName;
	
	ArrayList<ArrayList<Integer>> mPreferences;
	
	public Person(String name)
	{
		mName = name;
		mPreferences = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < Preferences.DayList.length; i++)
		{
			mPreferences.add(new ArrayList<Integer>());
			
			for(int j = 0; j < Preferences.ShiftList.length; j++)
			{
				mPreferences.get(i).add(0);
			}
		}
	}
	
	public void addPreference(int day, int shift, Integer preference)
	{
		mPreferences.get(day).set(shift, preference);
	}
	
	public int getPreference(int day, int shift)
	{
		return mPreferences.get(day).get(shift);
	}
};