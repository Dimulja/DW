package dw.controllers;

import java.util.ArrayList;

import javafx.scene.control.TextArea;

public class Analize {
	TextArea logWindow;
	
	public Analize(TextArea logWindow){
		this.logWindow=logWindow;
	}
	
	public boolean hasOrt(String s){
		
		if(s.toUpperCase().contains("ORT")){
			return true;
		}else{
			return false;
		}
	
	}

	
	public boolean hasZeit(String s){
		
		if(s.toUpperCase().contains("ZEIT")){
			return true;
		}else{
			return false;
		}
	
	}
	
	public boolean hasStau(String s){
		
		if(s.toUpperCase().contains("STAU")){
			return true;
		}else{
			return false;
		}
	
	}
	
	/**
	 * Get 
	 * @param q 
	 * @return
	 */
	public String[] selectedFields(String q){
		String[] splittedSelect = q.toUpperCase().split(",");


		
		
		
		return splittedSelect;
	}
	
	
	public ArrayList<String> whereTeil(String s){
		ArrayList<String> bed = new ArrayList<String>();
		String[] bediengugen = s.toUpperCase().split(" AND ");
		
		String st=null;
		String or=null;
		String ze=null;
		
		for(String word : bediengugen){
			
			if(word.toUpperCase().startsWith("STAU.") && !( word.toUpperCase().endsWith(".IDZEIT")
				|| word.toUpperCase().endsWith(".IDZEIT;") || word.toUpperCase().endsWith(".IDORT")
				||	word.toUpperCase().endsWith(".IDORT;"))) {
				//Spechere als Stau Bedingeung
				if(st!=null){
					st= st + " AND " + word ;
				}else{
					st=word;
				}
				
				
			}else if(word.toUpperCase().startsWith("ORT.") && !( word.toUpperCase().endsWith(".ZEIT_IDZEIT") 
					|| word.toUpperCase().endsWith(".ZEIT_IDZEIT;") || word.toUpperCase().endsWith(".ORT_IDORT")
					|| word.toUpperCase().endsWith(".ORT_IDORT;"))){
				//Spechere als ORT Bedingeung
				if(or!=null){
					or= or + " AND " + word ;
				}else{
					or=word;
				}
			}else if(word.toUpperCase().startsWith("ZEIT.") && !( word.toUpperCase().endsWith(".ZEIT_IDZEIT") 
					|| word.toUpperCase().endsWith(".ZEIT_IDZEIT;") || word.toUpperCase().endsWith(".ORT_IDORT")
					|| word.toUpperCase().endsWith(".ORT_IDORT;")) ){
				//Spechere als ZEIT Bedingeung
				if(ze!=null){
					ze= ze + " AND " + word ;
				}else{
					ze=word;
				}
				
			}
		}
		/**
		 * i0=Zeit
		 * i1=ort
		 * i2=stau
		 */
		bed.add(ze);
		bed.add(or);
		bed.add(st);
		return bed;
		
	}
	
	
}