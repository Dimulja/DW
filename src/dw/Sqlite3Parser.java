package dw;
import java.sql.*;

import dw.objects.staus.Stau;

public class Sqlite3Parser  {
	 
	
	String pathToDB=null;
	public Sqlite3Parser() {
	
		pathToDB="import.db";
	
	}
	
	public Sqlite3Parser(String pathToDB) {
		
		this.pathToDB=pathToDB;
	
	}
	
	public void getDataFromDB(){
	Connection c = null;
	Statement stmt =null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      // Note: /test.db is the test.db in the *current* working directory
	      c = DriverManager.getConnection("jdbc:sqlite:"+pathToDB,"","");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");
	      
	      
	      //Statement
	      
	      stmt = c.createStatement();
	      String sql="Select distinct z.datum, z.Uhrzeit, s.Autobahn, s.Richtung_Start, s.Richtung_Ende, s.Streckenabschnitt_Start, s.Streckenabschnitt_Ende, "
	      		+ "d.Art, (Select case when d.Laenge_angegeben==1 then d.Laenge else \"NULL\" end ) as Laenge, d.Beschreibung from Zeitpunkt z "
	      				+ "JOIN Stau s on s.Zeitpunkt=z.id JOIN Daten d on d.id =s.Daten";
	      
	      ResultSet rs = stmt.executeQuery(sql);
	      while ( rs.next() ) {
	          String Datum = rs.getString("Datum");
	          String Uhrzeit = rs.getString("Uhrzeit");
	          String Autobahn = rs.getString("Autobahn");
	          String Richtung_Start = rs.getString("Richtung_Start");
	          String Richtung_Ende = rs.getString("Richtung_Ende");
	          String Streckenabschnitt_Start = rs.getString("Streckenabschnitt_Start");
	          String Streckenabschnitt_Ende = rs.getString("Streckenabschnitt_Ende");
	          String Art = rs.getString("Art");
	          String Laenge = rs.getString("Laenge");
	          String Beschreibung = rs.getString("Beschreibung");
	         
	          
	          Stau tempStau = new  Stau(Datum,Uhrzeit,Autobahn,Richtung_Start,Richtung_Ende,
	        		  Streckenabschnitt_Start,	Streckenabschnitt_Ende,Art,Laenge, Beschreibung);


	          if(!Mainclass.mainList.contains(tempStau)){
	        	  
	          Mainclass.mainList.add(tempStau);
//	          Mainclass.log(Datum+" "+Uhrzeit+" "+Autobahn+" "+Richtung_Start+" "+Richtung_Ende+" "
//	    	          +Streckenabschnitt_Start+" "+Streckenabschnitt_Ende+" "+Art+" "+Laenge);
	          
	          }else{
	        	  Mainclass.duplicateCounter++;
	          }
	       }
	      rs.close();
	      stmt.close();
	      c.close();
	    } 
	    catch ( Exception e ) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(1);
	        try {
	        	if ( c != null && ! c.isClosed() ) {
	        		c.rollback();
	        		c.close();
	  	}
	        } catch ( SQLException sql ) { 
	  	// ignore
	        }
	    }
	    System.out.println("Operation done successfully");
	}
}
