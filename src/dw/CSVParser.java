package dw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import dw.objects.staus.Stau;

public class CSVParser {
	ArrayList<Stau> mainList = new ArrayList<Stau>();
	
	public CSVParser(ArrayList<Stau> mainList){
		
		this.mainList=mainList;
		
	}
	
	public String removeUmlaute(String s){
		
		s=s.replace("ÃƒÂ¶", "ö");
		s=s.replace("Ã¶", "ö");
		s=s.replace("Ã¼", "ü"); 
		s=s.replace("Ã¤", "ä"); 
		s=s.replace("ÃŸ", "ß");

		
		return s;
	}
	
	//Simple method for printing to stdOUT
	public static void log(String s){
		System.out.println(s);
		
	}
	
	//prints String+ Space in the same line
	public static void logPlusEmpty(String s){
		System.out.print(s+" ");
	}
	
	/**
	 * 
	 * @param s
	 * @return true if the String is empty otherwise false
	 */
public boolean isEmptyString(String s){
	if (s.isEmpty()){
		return true;
	}
	return false;
}
/**
 * 
 * @param  path String to a CSV or null, then the standard path will be import.csv
 * 
 */
public void readCSV(String path){
		String line= "";
		String cvsSplitBy = ";";
		if (path == null || path.isEmpty()){
			path="import.csv";
			
		}
		
		try {
			BufferedReader br= new BufferedReader(new FileReader(path));
		
			//Was used for debugging
			
//			Stau tempStauTest = new Stau("2011-01-04","17:36:00","A1","Köln","Dortmund"
//					,"Burscheid","Wermelskirchen","Stau","8");
//			mainList.add(tempStauTest);
		
		
			while ((line = br.readLine()) != null){
				line=removeUmlaute(line);
				String[] data = line.split(cvsSplitBy);
				//System.out.println(data.length);
					
					//log(line);
					
					if(!(data.length <10)){
						Stau tempStau = new  Stau(data[0],data[1],data[2],data[3],data[4],
								data[5], data[6],data[7],data[8]);
						if(!(mainList.contains(tempStau))){
						
						mainList.add(tempStau);
						
						}else{
							Mainclass.duplicateCounter++;
						}
						
						
						
					
					}else{
						Mainclass.badDataCounter++;
					}
			
					
					//	System.out.println(data[0]);
				
//				}
				//log("");
				
			}
			br.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
}
