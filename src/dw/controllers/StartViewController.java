package dw.controllers;

import java.net.URL;
import java.sql.Connection;
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
		
		 
	}
	
	
	//Define all vars needed
	String [] args = {"just some", "dummy strings"};
	private boolean initalized=false;
	//private boolean exported=false;
	
	MySQLOptimizer opt;
	
	Connection connection;
	
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
	
	public  void log (String s){
		 logWindow.appendText(s+"\n");
	 }

}
