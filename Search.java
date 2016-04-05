/******************************************************************************
*  HW2 Brian J. Olive
*  The following code is an adaptation of:
*
*  A Teaching GA					  Developed by Hal Stringer & Annie Wu, UCF
*  Version 2, January 18, 2004
*******************************************************************************/

import java.util.*;

public class Search
{
	public static void main(String[] args) throws java.io.IOException
	{
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		Calendar dateAndTime = Calendar.getInstance(); 
		Date startTime = dateAndTime.getTime();

		//  Read Parameter File
		System.out.println("\nParameter File Name is: " + args[0] + "\n");
		ConfigManager params = new ConfigManager();
		params.decodeFile(args[0]);
		
		GeneticAlgorithm algorithm = new GeneticAlgorithm(params);
		
		// Setup algorithm
		algorithm.setup();
		
		// Run algorithm
		algorithm.begin();

		System.out.println("Start:  " + startTime);
		dateAndTime = Calendar.getInstance(); 
		Date endTime = dateAndTime.getTime();
		System.out.println("End  :  " + endTime);
	} 

} 

