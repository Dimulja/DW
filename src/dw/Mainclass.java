package dw;

/**
 * @author Dmytro Shlyakhov
 */

import java.util.ArrayList;


import dw.objects.staus.Stau;


public class Mainclass {
	//Error counters
	public static int duplicateCounter=0;
	public static int badDataCounter=0;
	

	public static String csvPath ="import.csv" ;
	public static String dbPath="import.db";
	
	
	
	//The MainList for the whole data
	public static  ArrayList<Stau> mainList = new  ArrayList<Stau>();

	//Simple method for printing to stdOUT
	public static void log(String s){
		System.out.println(s);
		
	}
	
	//prints String+ Space in the same line
	public static void logPlusEmpty(String s){
		System.out.print(s+" ");
	}
	
	public static void log(String[] s){
		System.out.println(s);
		
	}
	public static void log(ArrayList<String> s){
		System.out.println(s);
		
	}

	

	
	
	public static void main(String[] args) {
		
		
	
		
 if( (args.length==2 && args[0].endsWith(".csv") && args[1].endsWith(".db"))  ){
//			log("Programm was terminated. Not enought Args add path for files");
//			log("Usage: java Mainclass import.csv import.db");
			
			csvPath=args[0];
			dbPath=args[1];
			
		}
		
		// Read CSV and SQL
		
	
		CSVParser csvp = new CSVParser(mainList);
		csvp.readCSV(csvPath);
		log("MainLIST has after adding CSV "+mainList.size()+" Elemente");
		//log("************");
		//log(csvList);
		Sqlite3Parser slqp = new Sqlite3Parser(dbPath);
		slqp.getDataFromDB();
		log("MainLIST has after adding SQL "+mainList.size()+" Elemente");
		
		log("Anzahl der weggeworfenen unvollständigen Datensätze aus der CSV-Datei: "+badDataCounter);
		log("Anzahl der eliminierten Duplikate aus beiden Dateien: "+duplicateCounter);

		
	
	
	
	}//End of Main
		
		


	}


