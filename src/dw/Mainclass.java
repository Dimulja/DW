package dw;


import java.util.ArrayList;


import dw.objects.staus.Stau;

public class Mainclass {

	public static int duplicateCounter=0;
	public static int badDataCounter=0;
	
	
	public static  ArrayList<Stau> mainList = new  ArrayList<Stau>();
	//public static ArrayList<Stau> sqliteList;
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
		// TODO Read CSV and SQL
		
		// = new ArrayList<String>();
	//ArrayList<Stau> sqliteList = new ArrayList<Stau>();
		CSVParser csvp = new CSVParser(mainList);
		csvp.readCSV("");
		log("MainLIST has after adding CSV "+mainList.size()+" Elemente");
		//log("************");
		//log(csvList);
		Sqlite3Parser slqp = new Sqlite3Parser();
		log("MainLIST has after adding SQL "+mainList.size()+" Elemente");
		
		log("Anzahl der weggeworfenen unvollständigen Datensätze aus der CSV-Datei: "+badDataCounter);
		log("Anzahl der eliminierten Duplikate aus beiden Dateien: "+duplicateCounter);
		//log(mainList.get(0).Autobahn);
		
	
	
	
	}//End of Main
		
		


	}


