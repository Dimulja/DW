package dw.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dw.controllers.Analize;
import javafx.scene.control.TextArea;


public class MySQLOptimizer {
	TextArea logWindow;
	boolean hasWhere=false;
	
	
	

	Connection connection;
	Analize analize;

String selectField, fromField, whereField;
String fullQuery;
String optimizedString;
String stauString;

	public MySQLOptimizer(TextArea logWindow, Connection connection) {
		
		this.logWindow = logWindow;
		this.connection=connection;
		this.analize = new Analize(logWindow);
		
	}
	/**
	 *  Setting MySqlConnection for this object
	 * @param connection
	 */
	public void setConn(Connection connection){
		
		try {
			this.connection=connection;
			log("Conntection in Optimizer set");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Printing to std.out the results of the request
	 * 
	 * @param selectField
	 * @param fromField
	 * @param whereField
	 */
	
	public void printFull(String selectField, String fromField, String whereField){
		
		this.selectField=selectField;
		this.fromField=fromField;
		this.whereField=whereField;
		
		try {
		

		PreparedStatement preparedStmt=null;
		PreparedStatement preparedStmt2=null;
		if (!whereField.equals("")){
			//whereField= " WHERE "+ whereField;
			hasWhere = true;
		}
		
		if(hasWhere){
		fullQuery = "SELECT "+ selectField +" FROM "+ fromField
				+ " WHERE "+ whereField;
		}else{
			
			fullQuery = "SELECT "+ selectField +" FROM "+ fromField;
		}

	
	
	// if there are all 3 tables . Other cases with 2 tables can be solved with one JOIN.
	if(analize.hasStau(fromField)  && analize.hasOrt(fromField) && analize.hasZeit(fromField)){
		
		
		//OPTIMIERUNG!!!
		
		if (!hasWhere){
		optimizedString="SELECT * FROM ZEIT, ORT";
		stauString ="SELECT * FROM STAU";			
		}else{
			ArrayList<String> whereTeil = analize.whereTeil(whereField);
			if((whereTeil.get(0))!=null && (whereTeil.get(1)!=null)){
				optimizedString="SELECT * FROM ZEIT, ORT WHERE "+ whereTeil.get(0) +" AND " + whereTeil.get(1);
			} else if((whereTeil.get(0))==null && (whereTeil.get(1)!=null)){
				optimizedString="SELECT * FROM ZEIT, ORT WHERE "+ whereTeil.get(1);
			} else if((whereTeil.get(0))!=null && (whereTeil.get(1)==null)){
				optimizedString="SELECT * FROM ZEIT, ORT WHERE "+ whereTeil.get(0);
//			optimizedString="SELECT * FROM ZEIT, ORT WHERE "
			} else 
			{
				optimizedString="SELECT * FROM ZEIT, ORT";
			}
			if((whereTeil.get(2))!=null){
				stauString ="SELECT * FROM STAU WHERE "+ whereTeil.get(2);	
			}else{
				stauString ="SELECT * FROM STAU";
			}
		}
		
		
		
		preparedStmt = connection.prepareStatement(optimizedString);
		//log(optimizedString);
		System.out.println(optimizedString);
		ResultSet resDim = preparedStmt.executeQuery();
		//int columns = resDim.getMetaData().getColumnCount();
		
		preparedStmt2 = connection.prepareStatement(stauString);
		System.out.println(stauString);
		ResultSet resStau = preparedStmt2.executeQuery();
		String[] splittedSelect = analize.selectedFields(selectField);
		int countResult=0;
		while(resDim.next()){
			resStau.beforeFirst();
			while(resStau.next()){
				//System.out.println("IN INNEREM WHILE");
				
				
				
				String outputString="";
				if(resDim.getInt("idZeit")==resStau.getInt("Zeit_idZeit")
				&&  resDim.getInt("idOrt")==resStau.getInt("Ort_idOrt")){
					for (String item :splittedSelect ){
						//System.out.println("Item= "+item);
						if(item.trim().toUpperCase().startsWith("STAU.")){
						
							if(outputString.isEmpty()){
								outputString = resStau.getString(item.trim());
							}else{
								outputString = outputString + " "+ resStau.getString(item.trim());
							}
								
						
						
						}else{
							
							if(outputString.isEmpty()){
								outputString = resDim.getString(item.trim());
							}else{
								outputString = outputString + " "+ resDim.getString(item.trim());
							}
							
							
							
						}
					
					
					}
				
					System.out.println(outputString);
					countResult++;
				}
				
				//log(outputString);
//				if (outputString !=null){
//					System.out.println(outputString);
//				}
			}// END of inner while
		}
		System.out.println(countResult+ " row returned (printed to Console)");
		log(countResult+ " row(s) returned (printed to Console)");
		
	}else{
		
		//Optimization is not needed
		
		
		
		preparedStmt = connection.prepareStatement(fullQuery);
		ResultSet resFull = preparedStmt.executeQuery();
		int columns = resFull.getMetaData().getColumnCount();
		
		
		StringBuilder resultLine = new StringBuilder();
		
		while(resFull.next()){
			
			// TODO Print the full Query
			for (int i = 1; i <= columns; i++) {
				resultLine.append(resFull.getString(i) + " ");
		    }
			resultLine.append("\n");
		System.out.println(resultLine);
		
	}
	
	}
		} catch (SQLException e) {
			
			e.printStackTrace();
			log(e.getMessage());
			//log("Buuug");
		}catch (Exception e2){
			log(e2.getMessage());
			e2.printStackTrace();
		}
		
		
		
	}
	
	public  void log (String s){
		 logWindow.appendText(s+"\n");
	 }
}
