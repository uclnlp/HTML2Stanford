package uk.ac.ucl.cs.mr.util;

import java.io.*;
import java.util.Scanner;

import de.l3s.boilerpipe.extractors.*;
import de.l3s.boilerpipe.BoilerpipeProcessingException;



public class HTML2Text{
	
	/*
	This function takes as arg an html file, extracts the text from it and create a new text file in the extracts directory.
	 */
	public static void convert(File htmlFileName, File extractsDirectory) throws BoilerpipeProcessingException, IOException{
	    FileReader HTMLReader = new FileReader(htmlFileName);
	    // DeafultExtractor is the default, throws away a lot of the garbage.
	    // If higher recall is needed, go for the KeepEverythingExtractor instead.
	    String text =  KeepEverythingExtractor.INSTANCE.getText(HTMLReader);
	    //String text =  DefaultExtractor.INSTANCE.getText(HTMLReader);
	    // Create a file to save the text:
	    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(extractsDirectory, htmlFileName.getName())), "UTF-8"));
		
	    // The second newline is to help the syntactic parser 
	    Scanner scanner = new Scanner(text);
	    while (scanner.hasNextLine()) {
		String line = scanner.nextLine();		    
		out.write(line);
		out.newLine();
		out.newLine();
	    }
	    out.close();
	    scanner.close();
	}
	
	// the input is the directory with the htmls, the output is a new directory
    public static void main(final String[] args){
        		
    	
	try{
	    // Get the html directory
	    File htmlDirectory = new File(args[0]);
	    // Get the text directory
	    File extractsDirectory = new File(args[1]);
	    extractsDirectory.mkdir();


	    // For each file in the html dir:
	    File[] htmlFileNames = htmlDirectory.listFiles();

	    for (int i = 0; i < htmlFileNames.length; i++){
		System.out.println(htmlFileNames[i].getName());
		try{
			HTML2Text.convert(htmlFileNames[i], extractsDirectory);
		}
		catch(BoilerpipeProcessingException bpe){
		    System.err.println("Error: " + bpe);
		}
	    } 
	}
	catch(IOException ioe){
	    System.err.println("Error: " + ioe);
	}
    }
}

