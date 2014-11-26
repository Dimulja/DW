package dw.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


import javafx.scene.control.TextArea;



public class MySQLConnector {
	TextArea logWindow;
	String  dbname;

	
	
	public MySQLConnector(String host, String username, String password,String dbname, 
			String port, TextArea logWindow){
		this.logWindow=logWindow;
		//logWindow.appendText("Using Schemata: "+dbname+"\n");
		if(!dbname.equals("")){
			this.dbname=dbname;
			}else{
				this.dbname="dw";
			}
		logWindow.appendText("Using Schemata: "+this.dbname+"\n");
		try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            log("MySQL JDBC Driver not found !!");
            return;
        }
        log("MySQL JDBC Driver Registered!");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/", username, password);
            //System.out.println("MySQL Connection to database established!");
            log("MySQL Connection to database established!");
            initMySqlDb(connection);
            
 
        } catch (SQLException e) {
            log("Connection Failed! Check output console");
            logWindow.appendText(e.getMessage()+"\n");
            return;
        } finally {
            try
            {
                if(connection != null)
                    connection.close();
                logWindow.appendText("Connection closed !!\n");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	public void initMySqlDb(Connection conn){
		try {
			Statement stmt = conn.createStatement();
		
		String sql[]={"CREATE SCHEMA IF NOT EXISTS `"+dbname+"` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;",
				"CREATE TABLE IF NOT EXISTS `"+dbname+"`.`Zeit` ("
				+ " `idZeit` INT NOT NULL AUTO_INCREMENT,"
				+ "  `zeitpunkt` TIMESTAMP NULL,"
				+ "  `tag` INT NULL,"
				+ "  `monat` INT NULL,"
				+ " `jahr` INT NULL,"
				+ " `kw` INT NULL,"
				+ "  PRIMARY KEY (`idZeit`))"
				+ "ENGINE = InnoDB;",
				
				"USE `"+dbname+"` ;",
//				+ "-- -----------------------------------------------------"
//				+ "-- Table `dw`.`Ort`"
//				+ "-- -----------------------------------------------------"
				 "CREATE TABLE IF NOT EXISTS `"+dbname+"`.`Ort` ("
				+ "  `abschnitt` VARCHAR(45) NULL,"
				+ "  `richtung` VARCHAR(45) NULL,"
				+ "  `autobahn` VARCHAR(45) NULL,"
				+ "  `idOrt` INT NOT NULL AUTO_INCREMENT,"
				+ "  PRIMARY KEY (`idOrt`))"
				+ "ENGINE = InnoDB;",
//				+ "-- -----------------------------------------------------"
//				+ "-- Table `dw`.`Stau`"
//				+ "-- -----------------------------------------------------"
				 "CREATE TABLE IF NOT EXISTS `"+dbname+"`.`Stau` ("
				+ "  `laenge` INT NULL,"
				+ "  `art` VARCHAR(45) NULL,"
				+ "  `beschreibung` VARCHAR(45) NULL,"
				+ "  `Zeit_idZeit` INT NOT NULL,"
				+ "  `Ort_idOrt` INT NOT NULL,"
				+ "  PRIMARY KEY (`Zeit_idZeit`, `Ort_idOrt`),"
				+ "  INDEX `fk_Stau_Zeit_idx` (`Zeit_idZeit` ASC),"
				+ "  INDEX `fk_Stau_Ort1_idx` (`Ort_idOrt` ASC),"
				+ "  CONSTRAINT `fk_Stau_Zeit`"
				+ "FOREIGN KEY (`Zeit_idZeit`)"
				+ "REFERENCES `"+dbname+"`.`Zeit` (`idZeit`)"
				+ "    ON DELETE NO ACTION"
				+ "    ON UPDATE NO ACTION,"
				+ " CONSTRAINT `fk_Stau_Ort1`"
				+ "    FOREIGN KEY (`Ort_idOrt`)"
				+ "    REFERENCES `"+dbname+"`.`Ort` (`idOrt`)"
				+ "    ON DELETE NO ACTION"
				+ "    ON UPDATE NO ACTION)"
				+ "ENGINE = InnoDB;"};
		int i=0;
		for(String command : sql){
		
		stmt.executeUpdate(command);
		
		log("MySQL command " +i+" executed");
		i++;
		}
		
		try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      
	      }// nothing we can do
		
		
		
		} catch (SQLException ex) {
			
			logWindow.appendText("SQLException: " + ex.getMessage()+"\n");
			logWindow.appendText("SQLState: " + ex.getSQLState()+"\n");
			logWindow.appendText("VendorError: " + ex.getErrorCode()+"\n");
		}
		
	}
	/**
	 * Simple method for printing to logWindow
	 * @param s String
	 * 
	 */
	public void log(String s){
		logWindow.appendText(s+"\n");
	}
	
}
