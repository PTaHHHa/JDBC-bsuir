package controller;

import library.World;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField TypeField;

    @FXML
    private TextField AreaField;

    @FXML
    private TextField NumberField;

    @FXML
    private Button insertButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<World> TableView;

    @FXML
    private TableColumn<World, Integer> idColumn;

    @FXML
    private TableColumn<World, String> nameColumn;

    @FXML
    private TableColumn<World, String> TypeColumn;

    @FXML
    private TableColumn<World, Integer> AreaColumn;

    @FXML
    private TableColumn<World, Integer> NumberColumn;

    @FXML
    private void insertButton() {
    	String query = "insert into world values("+idField.getText()+",'"+ nameField.getText()+"','"+ TypeField.getText()+"',"+ AreaField.getText()+","+ NumberField.getText()+")";
    	executeQuery(query);
    	showWorld();
    }


    @FXML
    private void updateButton() {
    String query = "UPDATE world SET name='"+ nameField.getText()+"',Вид='"+ TypeField.getText()+"',Ареал="+ AreaField.getText()+",Численность="+ NumberField.getText()+" WHERE ID="+idField.getText()+"";
    executeQuery(query);
	showWorld();
    }

    @FXML
    private void deleteButton() {
    	String query = "DELETE FROM world WHERE ID="+idField.getText()+"";
    	executeQuery(query);
    	showWorld();
    }

    public void executeQuery(String query) {
    	Connection conn = getConnection();
    	Statement st;
    	try {
			st = conn.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	showWorld();
    }

    public Connection getConnection() {

    	Connection conn;
    	try {

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/world?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC","root","password");
    		return conn;
    	}
    	catch (Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }

    public ObservableList<World> getWorldList(){
    	ObservableList<World> worldList = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query = "SELECT * FROM world ";
    	Statement st;
    	ResultSet rs;

    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			World world;
			while(rs.next()) {
				world = new World(rs.getInt("Id"),rs.getString("Название"),rs.getString("Вид"),rs.getInt("Ареал"),rs.getInt("Численность"));
				worldList.add(world);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return worldList;
    }

    public void showWorld() {
    	ObservableList<World> list = getWorldList();

    	idColumn.setCellValueFactory(new PropertyValueFactory<World,Integer>("id"));
    	nameColumn.setCellValueFactory(new PropertyValueFactory<World,String>("Название"));
    	TypeColumn.setCellValueFactory(new PropertyValueFactory<World,String>("Вид"));
    	AreaColumn.setCellValueFactory(new PropertyValueFactory<World,Integer>("Ареал"));
    	NumberColumn.setCellValueFactory(new PropertyValueFactory<World,Integer>("Численность"));

    	TableView.setItems(list);
    }

}
