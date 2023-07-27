/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package library;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ASUS
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button close;

    @FXML
    private Button login_btn;

    @FXML
    private Button minimize;

    @FXML
    private PasswordField password;

    @FXML
    private TextField studentNumber;

    // DATABASE TOOLS, DON'T FORGET TO ADD THE JAR FILE!!
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    private double x = 0;
    private double y = 0;

    @SuppressWarnings("empty-statement")
    public void login() throws SQLException {
        try {

            connection = Database.getDBConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String query = "SELECT * FROM student WHERE studentNumber = ? and password = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentNumber.getText());
            preparedStatement.setString(2, password.getText());
            resultSet = preparedStatement.executeQuery();

            Alert alert;

            if (studentNumber.getText().isEmpty() || password.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields.");
                alert.showAndWait();
            } else {
                if (resultSet.next()) {

                    getData.studentNumber = studentNumber.getText();

                    // DON'T FORGET THIS!!!!
                    System.out.print(resultSet.getString("image"));
                    getData.path = resultSet.getString("image");;

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Admin Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    // TO HIDE THE LOGIN FORM
                    login_btn.getScene().getWindow().hide();

                    // FOR DASHBOARD
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));

                    Stage stage = new Stage();

                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) -> {

                        x = event.getSceneX();
                        y = event.getSceneY();

                    });

                    root.setOnMouseDragged((MouseEvent event) -> {

                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);

                    });

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Admin Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username or password");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // ONLY NUMBERS ALLOWED
    public void numbersOnly(KeyEvent event) {

        if (event.getCharacter().matches("[^\\e\t\r\\d+$]")) {
            event.consume();

            studentNumber.setStyle("-fx-border-color:#e04040");
        } else {
            studentNumber.setStyle("-fx-border-color:#fff");
        }

    }

    //    LET'S PROCEED TO DASHBOARD 
    @FXML
    public void minimize() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
