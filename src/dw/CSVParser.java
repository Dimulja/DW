package dw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
	
public void readCSV(String path){
		String line= "";
		String cvsSplitBy = ";";
		//ArrayList<Stau> aListResult = new ArrayList<Stau>();
		if (path == null || path.isEmpty()){
			path="import.csv";
			
		}
		
		try {
			BufferedReader br= new BufferedReader(new FileReader(path));
		;
			
			while ((line = br.readLine()) != null){
				String[] data = line.split(cvsSplitBy);
				System.out.println(data.length);
					line=removeUmlaute(line);
					log(line);
					
					if(data.length <10){
//					String[] dataNew = new String[10];
//						for (int i=0; i<data.length; i++){
//							dataNew[i]=data[i];
//						}	
//						if(!isEmptyString(data[0])){
//							mainList.add(new Stau(dataNew[0],dataNew[1],dataNew[2],dataNew[3],dataNew[4],
//								dataNew[5],	dataNew[6],dataNew[7],dataNew[8]));	
//							}
						}else{
							mainList.add(new Stau(data[0],data[1],data[2],data[3],data[4],
								data[5], data[6],data[7],data[8]));
					}
			//	System.out.println(data[0]);
				
//				}
				//log("");
				
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return aListResult;
		
	}
}
