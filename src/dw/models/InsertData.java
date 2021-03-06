/**
 * 
 */
package dw.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.TextArea;
import dw.objects.staus.Stau;


/**
 * @author Dimulja
 *
 */
public class InsertData {
 Connection connection;
 TextArea logWindow;
 Date date;
 int DateID, OrtID;
 ResultSet rs;
 String  dbname;
 private boolean exported=false;

	
public boolean isExported() {
	return exported;
}


public void setExported(boolean exported) {
	this.exported = exported;
}


public  InsertData(Connection connection, TextArea logWindow, String dbname){
		this.connection=connection;
		this.logWindow=logWindow;
		this.dbname=dbname;
	}
	

public void insertMainList() {
	try {
		 String queryDate ="INSERT IGNORE INTO `"+dbname+"`.`zeit` ( `zeitpunkt`, `tag`, `monat`, `jahr`,`kw`, `idZeit`) "
		 		+ "VALUES (?,?,?,?,?,?); ";
		String queryOrt ="INSERT IGNORE INTO `"+dbname+"`.`ort`"
		+ "(`autobahn`,`Richtung_Start`,`Richtung_Ende`,`Streckenabschnitt_Start`,`Streckenabschnitt_Ende`, `idOrt`)"
		+ "VALUES (?,?,?,?,?,?)";
		
		String queryStau ="INSERT IGNORE INTO `"+dbname+"`.`stau` (`laenge`, `art`, `beschreibung`, `Zeit_idZeit`, `Ort_idOrt`)"
				+ "VALUES (?,?,?,?,?);";
		PreparedStatement preparedStmt;
		//Statement stmt = connection.createStatement();
		log("Inserting DATA(if needed) into MySQL DB");
	for( Stau st : Mainclass.mainList){
		//primary keys for dimensions
		
		int zeitId=st.getZeitHash() ;
		int ortId=st.getOrtHash() ;
		//log("ZeitID="+zeitId);
		//log("ortId="+ortId);
		
		
		//DAte table
		preparedStmt = connection.prepareStatement(queryDate, Statement.RETURN_GENERATED_KEYS);
		preparedStmt.setString(1, st.Uhrzeit);
		DateFormat inputData = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			date = inputData.parse(st.Datum);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log(e.getMessage());
		}
		String day = new SimpleDateFormat("dd").format(date);
		String month = new SimpleDateFormat("MM").format(date);
		String year = new SimpleDateFormat("yyyy").format(date);
		String week = new SimpleDateFormat("w").format(date);
		
		preparedStmt.setInt(2, Integer.parseInt(day));
		preparedStmt.setInt(3, Integer.parseInt(month));
		preparedStmt.setInt(4, Integer.parseInt(year));
		preparedStmt.setInt(5, Integer.parseInt(week));
		preparedStmt.setInt(6, zeitId);
		
		preparedStmt.executeUpdate();
		rs = preparedStmt.getGeneratedKeys();
		
		if(rs.next()){
		//log("IN IF!!!!!!!!!!!!!");
			DateID= rs.getInt(1);
			//log(DateID+"");
		}
		rs.close();
		preparedStmt.close();
		//log("First Insert(Zeit) OK");
		//Ort Table
		preparedStmt = connection.prepareStatement(queryOrt, Statement.RETURN_GENERATED_KEYS);
		preparedStmt.setString(1, st.Autobahn);
		preparedStmt.setString(2, st.Richtung_Start);
		preparedStmt.setString(3, st.Richtung_Ende);
		preparedStmt.setString(4, st.Streckenabschnitt_Start);
		preparedStmt.setString(5, st.Streckenabschnitt_Ende);
		preparedStmt.setInt(6, ortId);
		
		preparedStmt.executeUpdate();
		//log("First Insert(ORT) OK");
		rs = preparedStmt.getGeneratedKeys();
//		if(rs.next()){
//			OrtID= rs.getInt(1);
//		}
		rs.close();
		preparedStmt.close();
		
		
		//Stau table
		preparedStmt = connection.prepareStatement(queryStau, Statement.RETURN_GENERATED_KEYS);
		if(st.Laenge.equalsIgnoreCase("NULL")){
			preparedStmt.setString(1, null);
		}else{
			preparedStmt.setInt(1, Integer.parseInt(st.Laenge));
		}
		preparedStmt.setString(2, st.Art);
		preparedStmt.setString(3, st.Beschreibung);
		preparedStmt.setInt(4, zeitId);
		preparedStmt.setInt(5, ortId);
		//log("DateID="+DateID+" OrtID="+OrtID);
		preparedStmt.executeUpdate();
		//log("First Insert(Zeit) OK");
		preparedStmt.close();
	}
	log("Data from MainLIst was added Successfuly");
	setExported(true);
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		log(e.getMessage());
		setExported(false);
	}
}
 public  void log (String s){
	 logWindow.appendText(s+"\n");
 }

}
