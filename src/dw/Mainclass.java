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

	//public static boolean defaultPath=false;
	
	
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
		
		
	
		
 if( !(args.length==2 && args[0].endsWith(".csv") && args[1].endsWith(".db"))  ){
			log("Programm was terminated. Not enought Args add path for files");
			log("Usage: java Mainclass import.csv import.db");
			
			System.exit(500);
		}
		
		// Read CSV and SQL
		
	
		CSVParser csvp = new CSVParser(mainList);
		csvp.readCSV(args[0]);
		log("MainLIST has after adding CSV "+mainList.size()+" Elemente");
		//log("************");
		//log(csvList);
		Sqlite3Parser slqp = new Sqlite3Parser(args[1]);
		slqp.getDataFromDB();
		log("MainLIST has after adding SQL "+mainList.size()+" Elemente");
		
		log("Anzahl der weggeworfenen unvollständigen Datensätze aus der CSV-Datei: "+badDataCounter);
		log("Anzahl der eliminierten Duplikate aus beiden Dateien: "+duplicateCounter);

		
	
	
	
	}//End of Main
		
		


	}


