/**
 * 
 */
package dw.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Dimulja
 *
 */
public class InsertData {
 Connection connection;
 
	public  InsertData(){
		
	}
	
public  InsertData(Connection connection){
		this.connection=connection;
	}
	

public void insertData(){
	try {
		Statement stmt = connection.createStatement();
	
	
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


}
