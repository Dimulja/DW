package dw.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dw.models.InsertData;
import dw.models.Mainclass;
import dw.models.MySQLConnector;
import dw.models.MySQLOptimizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class StartViewController implements Initializable {
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		logWindow.setWrapText(true);
		logWindow.setEditable(false);
		exportToMysqlButton.setDisable(true);
		executeButton.setDisable(true);
		viewTextArea.setWrapText(true);
		viewTextArea.setEditable(false);
		
		 
	}
	
	
	//Define all vars needed
	String [] args = {"just some", "dummy strings"};
	String [] tables ={"ORT", "STAU", "ZEIT"};
	//trigger_event: { INSERT | UPDATE | DELETE }
	String [] triggerEvents ={"INSERT", "UPDATE", "DELETE"};
	private boolean initalized=false;
	//private boolean exported=false;
	
	MySQLOptimizer opt;
	
	Connection connection;
	
	@FXML
    private Button viewDeleteButtonDo;
	
	@FXML
	private Button viewConnectButton;
	
	@FXML
	private Button  viewUpdateButton;
	
	@FXML
	private TextArea viewTextArea;

    @FXML
    private Button viewExecuteButton;
	@FXML
	private TextField viewCreateQuery;
	
	@FXML
	private TextField viewCreateName;
	@FXML
	private TextField viewDeleteName;
	
	@FXML
	private ResourceBundle resources;
	
	@FXML
	private Button ReadData;
	
	@FXML
	private Button exportToMysqlButton;
	
	@FXML
	private Button executeButton;
	
	@FXML
	private TextArea logWindow;
	
	@FXML
	private TextField username ;
	
	@FXML
	private TextField  password;
	
	@FXML
	private TextField hostaddress;
	
	@FXML
	private TextField port;
	
	@FXML
	private TextField dbname;
	
	@FXML
	private TextField selectField;
	
	@FXML
	private TextField fromField;
	
	@FXML
	private TextField whereField;
// TODO to imlement table view	
//	@FXML
//	private TableView<Stau> resultTable;
	
	
	@FXML
	void viewExecute(ActionEvent event) {
		//TODO  Create oder delete view.
		
		
		PreparedStatement preparedStmt=null;
		String viewName = viewCreateName.getText();
		String query = viewCreateQuery.getText();
		String dbQuery = "CREATE TABLE "+viewName+"_view"+" AS (" +query+ ")";
		try {
			preparedStmt=connection.prepareStatement(dbQuery);
			preparedStmt.executeUpdate();
			// Create Trigger
			
			//Create list of tables(views)
		String addToTableList = "CREATE TABLE IF NOT EXISTS `mv_tableslist` ("
				+ "`tablename` varchar(255) NOT NULL,"
				+ "`statement` text,"
				+ "PRIMARY KEY (`tablename`),"
				+ "UNIQUE KEY `tablename_UNIQUE` (`tablename`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		preparedStmt=connection.prepareStatement(addToTableList);
		preparedStmt.executeUpdate();
		
		
		//Inserting new table to the table list
		String insertTheViewEntry = "INSERT INTO `mv_tableslist`"
				+ "(`tablename`, `statement`) VALUES( ?, ?);";
		preparedStmt=connection.prepareStatement(insertTheViewEntry);
		preparedStmt.setString(1, viewName+"_view");	
		preparedStmt.setString(2, query);	
		
		preparedStmt.executeUpdate();
		
		//Create stored Procedure
		
		String dropProcedureQuery = "DROP PROCEDURE IF EXISTS refresh";
		preparedStmt=connection.prepareStatement(dropProcedureQuery);
		preparedStmt.executeUpdate();
		preparedStmt=null;
		
		viewLog("View "+viewName+" has been created");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			viewLog(e.getMessage());
		}
		
		String createRefresh =  
				//"USE `"+dbname.getText()+"`$$ " +
				 "CREATE PROCEDURE `refresh`() "
				+ "BEGIN "
				+ "DECLARE v_finished INTEGER DEFAULT 0; "
				+ "DECLARE tableName text DEFAULT \"\"; "
				+ "DECLARE tableQuery text DEFAULT \"\"; "
				+ "DEClARE table_cursor CURSOR FOR (SELECT * FROM mv_tableslist); "
				+ "DECLARE CONTINUE HANDLER "
				+ "FOR NOT FOUND SET v_finished = 1; "
				+ "OPEN table_cursor; "
				+ "read_loop:LOOP "
				+ "FETCH table_cursor INTO tableName, tableQuery; "
				+ "IF v_finished = 1 THEN "
				+ "LEAVE read_loop ;"
				+ "END IF; "
				+ "SET @SQL = CONCAT('DROP TABLE IF EXISTS ', CONCAT(\"\",tableName)); "
				+ "PREPARE stmt FROM @SQL; "
				+ "EXECUTE stmt; "
				+ "DEALLOCATE PREPARE stmt;"
				+ "SET @SQL = CONCAT('CREATE TABLE ',CONCAT(\"\",tableName),' AS ',CONCAT(\"\",tableQuery)); "
				+ "PREPARE stmt FROM @SQL; "
				+ "EXECUTE stmt; "
				+ "DEALLOCATE PREPARE stmt; "
				+ "END LOOP; "
				+ "CLOSE table_cursor; "
				+ "END ";
			//	+ "DELIMITER ;";
		
		
		//viewLog(createRefresh);
		try {
			preparedStmt=connection.prepareStatement(createRefresh);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			viewLog(e.getMessage());
		}
		
/**
 *  Unfortunatly triggers can't call procedures :(((
 */
		
		
//		for (String triggerOption: triggerEvents) {
//			//Create triggers
//			for (String tableName : tables) {
//				String dropTrigger = "DROP TRIGGER IF EXISTS " + tableName
//						+ "_AFTER_"+triggerOption+" ; ";
//				String createTrigger = "CREATE TRIGGER `" + tableName
//						+ "_AFTER_"+triggerOption+"` " + "AFTER "+triggerOption+" ON `" + tableName
//						+ "` FOR EACH ROW " + "BEGIN " + "CALL refresh(); "
//						+ "END ";
//				//System.out.println(createTrigger);
//				try {
//					preparedStmt = connection.prepareStatement(dropTrigger);
//					preparedStmt.executeUpdate();

//					preparedStmt = connection.prepareStatement(createTrigger);
//					preparedStmt.executeUpdate();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}//end for Table name
//		} // End for Trigger Option
		
		
		
		
			
			
			
	
	  }
	
	@FXML
	public void initImport(ActionEvent event){
		if(!initalized){
		Mainclass m = new Mainclass(args, logWindow);
		initalized=true;
		ReadData.setDisable(true);
		exportToMysqlButton.setDisable(false);
		//logWindow.setFocusTraversable(false);
		
		}
	}
	@FXML
	public void exportToMysql(ActionEvent event){
		MySQLConnector mysqlconn = new MySQLConnector(hostaddress.getText(), username.getText(),
				password.getText(), dbname.getText(), port.getText(), logWindow);
		//Init DB
		if(mysqlconn.getState()){
		mysqlconn.initMySqlDb(mysqlconn.getConn());
		InsertData insd = new InsertData(mysqlconn.getConn(), logWindow, dbname.getText());
		insd.insertMainList();
		
		if(insd.isExported()){
			hostaddress.setDisable(true);
			username.setDisable(true);
			password.setDisable(true);
			dbname.setDisable(true);
			port.setDisable(true);
			exportToMysqlButton.setDisable(true);
			this.connection=mysqlconn.getConn();
			executeButton.setDisable(false);
			opt = new MySQLOptimizer(logWindow, connection);
			
			

			
			
		}
		}
	}
	@FXML
	public void executeSQL(ActionEvent event){
		

		
		opt.printFull(selectField.getText(), fromField.getText(), whereField.getText());
	}
	
	
    @FXML
    void viewDeleteButtonDo(ActionEvent event) {
    	
    	String deleteString ="DELETE FROM `mv_tableslist` WHERE tablename= '"+ viewDeleteName.getText()+"_view';";
    	try {
			PreparedStatement preparedStmt=connection.prepareStatement(deleteString);
			preparedStmt.executeUpdate();
		String drop =" DROP TABLE "+viewDeleteName.getText()+"_view;";
			preparedStmt=connection.prepareStatement(drop);
			preparedStmt.executeUpdate();
			viewLog("View "+viewDeleteName.getText()+" has been deleted");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.getMessage();
			viewLog(e.getMessage());
		}
    }

    @FXML
    void viewUpdateDo(ActionEvent event) {
    	try {
			PreparedStatement preparedStmt =connection.prepareStatement("CALL refresh()");
			preparedStmt.executeUpdate();
			viewLog("Views have been refreshed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			viewLog(e.getMessage());
		}
    }

    @FXML
    void viewConnectDo(ActionEvent event) {
    
    MySQLConnector connector = new MySQLConnector(hostaddress.getText(), username.getText(),
			password.getText(), dbname.getText(), port.getText(), viewTextArea);
     if(connector.getState()){
    	connection= connector.getConn();
     }
     
    }
	
	
	
	
	
	public  void log (String s){
		 logWindow.appendText(s+"\n");
	 }

	public void viewLog(String s){
		viewTextArea.appendText(s+"\n");
	}
}
