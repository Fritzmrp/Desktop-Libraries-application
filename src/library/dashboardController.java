/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library;

import static com.jfoenix.svg.SVGGlyphLoader.clear;
import static com.mysql.cj.util.SaslPrep.prepare;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import static oracle.net.aso.C00.x;
import static oracle.net.aso.C00.y;

/**
 *
 * @author ASUS
 */
public class dashboardController implements Initializable {

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Button bars_btn;

    @FXML
    private Button arrow_btn;

    @FXML
    private AnchorPane nav_form;

    @FXML
    private Button availableBooks_btn;

    @FXML
    private AnchorPane availableBooks_form;

    @FXML
    private ImageView availableBooks_imageView;

    @FXML
    private TableView<availableBooks> availableBooks_tableView;

    @FXML
    private Label availableBooks_title;

    @FXML
    private Button borrowingBooks_btn;

    @FXML
    private Circle circle_image;

    @FXML
    private TableColumn<availableBooks, String> col_ab_author;

    @FXML
    private TableColumn<availableBooks, String> col_ab_bookTitle;

    @FXML
    private TableColumn<availableBooks, String> col_ab_bookType;

    @FXML
    private TableColumn<availableBooks, String> col_ab_publishedDate;

    @FXML
    private Button edit_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button returnBooks_btn;

    @FXML
    private Button addBooks_btn;

    @FXML
    private Label studentNumber_label;

    @FXML
    private Button take_btn;

    @FXML
    private Button halfNav_availablebtn;

    @FXML
    private Button halfNav_borrowingbtn;

    @FXML
    private Button halfNav_addbtn;

    @FXML
    private AnchorPane halfNav_form;

    @FXML
    private Button halfNav_returnbtn;

    @FXML
    private Circle smallCircle_image;

    @FXML
    private AnchorPane mainCenter_form;

    @FXML
    private AnchorPane borrowingBooks_form;

    @FXML
    private AnchorPane returnBooks_form;

    @FXML
    private AnchorPane addBooks_form;

    @FXML
    private Label currentForm_label;

    @FXML
    private Label take_StudentNumber;

    @FXML
    private TextField take_FirstName;

    @FXML
    private TextField take_LastName;

    @FXML
    private ComboBox<?> take_Gender;

    @FXML
    private TextField take_BookTitle;

    @FXML
    private Label take_IssueDate;

    @FXML
    private ImageView take_ImageView;

    @FXML
    private Label take_titleLabel;

    @FXML
    private Label take_authorLabel;

    @FXML
    private Label take_genreLabel;

    @FXML
    private Label take_dateLabel;

    @FXML
    private Button take_clearBtn;

    @FXML
    private Button take_takeBtn;

    @FXML
    private TableView<returnBook> return_tableView;

    @FXML
    private TableColumn<returnBook, String> return_BookTitle;

    @FXML
    private TableColumn<returnBook, String> return_Author;

    @FXML
    private TableColumn<returnBook, String> return_bookType;

    @FXML
    private TableColumn<returnBook, String> return_dateIssue;

    @FXML
    private TableColumn col_ab_image;

    @FXML
    private ImageView return_imageView;

    @FXML
    private Button return_button;

    @FXML
    private FontAwesomeIcon edit_icon;

    private Image image;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet result;

    private String comboBox[] = {"Male", "Female", "Others"};

    public void gender() {
        List<String> combo = new ArrayList<>();

        for (String data : comboBox) {

            combo.add(data);
        }

        ObservableList list = FXCollections.observableArrayList(combo);

        take_Gender.setItems(list);
    }

    public void takeBook() throws SQLException {
        Date sqlDate = new Date(System.currentTimeMillis());

        String query = "INSERT INTO take VALUES (?,?,?,?,?,?,?,?,?,?)";

        connection = Database.getDBConnection();

        try {
            Alert alert;

            if (take_FirstName.getText().isEmpty()
                    || take_LastName.getText().isEmpty()
                    || take_Gender.getSelectionModel().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Admin Mesage");
                alert.setHeaderText(null);
                alert.setContentText("Please type complete student Details");
                alert.showAndWait();
            } else if (take_titleLabel.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please indicate the book you want to take.");
                alert.showAndWait();
            } else {

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, take_StudentNumber.getText());
                preparedStatement.setString(2, take_FirstName.getText());
                preparedStatement.setString(3, take_LastName.getText());
                preparedStatement.setString(4, (String) take_Gender.getSelectionModel().getSelectedItem());
                preparedStatement.setString(5, take_titleLabel.getText());
                preparedStatement.setString(6, take_authorLabel.getText());
                preparedStatement.setString(7, take_genreLabel.getText());
                preparedStatement.setString(8, getData.path);
                preparedStatement.setDate(9, sqlDate);

                String check = "Not Return";

                preparedStatement.setString(10, check);
                preparedStatement.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Successful!y take the book!");
                alert.showAndWait();

                clearTakeData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findBook(ActionEvent event) throws SQLException {

        clearFindData();

        String query = "SELECT * FROM book WHERE bookTitle = '" + take_BookTitle.getText() + "'";

        connection = Database.getDBConnection();

        try {

            preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            boolean check = false;

            Alert alert;

            if (take_BookTitle.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the book.");
                alert.showAndWait();

            } else {

                while (result.next()) {

                    take_titleLabel.setText(result.getString("bookTitle"));
                    take_authorLabel.setText(result.getString("author"));
                    take_genreLabel.setText(result.getString("bookType"));
                    take_dateLabel.setText(result.getString("date"));

                    getData.path = result.getString("image");

                    String uri = "file:" + getData.path;

                    image = new Image(uri, 134, 171, false, true);
                    take_ImageView.setImage(image);
                    check = true;
                }

                if (!check) {
                    take_titleLabel.setText("Book is not available!");

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void studentNumberLabel() {
        take_StudentNumber.setText(getData.studentNumber);
    }

    public void clearTakeData() {

        take_BookTitle.setText("");
        take_titleLabel.setText("");
        take_authorLabel.setText("");
        take_genreLabel.setText("");
        take_dateLabel.setText("");
        take_ImageView.setImage(null);

    }

    public void clearFindData() {

        take_titleLabel.setText("");
        take_authorLabel.setText("");
        take_genreLabel.setText("");
        take_dateLabel.setText("");
        take_ImageView.setImage(null);

    }

    public void displayDate() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new java.util.Date());

        take_IssueDate.setText(date);
    }

    public ObservableList<returnBook> returnBook() throws SQLException {

        ObservableList<returnBook> bookReturnData = FXCollections.observableArrayList();

        String check = "Not Return";

        String sql = "SELECT * FROM take WHERE checkReturn = '" + check + "' and studentNumber = '"
                + getData.studentNumber + "'";

        connection = Database.getDBConnection();

        try {

            returnBook rBook;

            preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {

                rBook = new returnBook(result.getString("bookTitle"), result.getString("author"),
                        result.getString("bookType"), result.getString("image"),
                        result.getDate("date"));
                bookReturnData.add(rBook);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookReturnData;

    }

    public void returnBooks() throws SQLException {

        String sql = "UPDATE take SET checkReturn = 'Returned' WHERE bookTitle = '" + getData.takeBookTitle + "'";

        connection = Database.getDBConnection();

        Alert alert;

        try {

            if (return_imageView.getImage() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the book you want to return");
                alert.showAndWait();

            } else {

                statement = connection.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully returned!");
                alert.showAndWait();

                showBookReturn();

                return_imageView.setImage(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    ObservableList<returnBook> retBook;

    public void showBookReturn() throws SQLException {

        retBook = returnBook();

        return_BookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        return_Author.setCellValueFactory(new PropertyValueFactory<>("author"));
        return_bookType.setCellValueFactory(new PropertyValueFactory<>("genre"));
        return_dateIssue.setCellValueFactory(new PropertyValueFactory<>("date"));

        return_tableView.setItems(retBook);

    }

    public void selectReturnBook() {

        returnBook rBook = return_tableView.getSelectionModel().getSelectedItem();
        int num = return_tableView.getSelectionModel().getFocusedIndex();

        if ((num - 1) < -1) {
            return;
        }

        String uri = "file:" + rBook.getImage();

        image = new Image(uri, 143, 177, false, true);
        return_imageView.setImage(image);

        getData.takeBookTitle = rBook.getTitle();
    }

    public ObservableList<availableBooks> dataList() throws SQLException {

        ObservableList<availableBooks> listBooks = FXCollections.observableArrayList();

        String query = "SELECT * FROM book";

        connection = Database.getDBConnection();

        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            availableBooks aBooks;

            preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {

                aBooks = new availableBooks(result.getString("bookTitle"),
                        result.getString("author"), result.getString("bookType"),
                        result.getString("image"), result.getDate("date"));
                listBooks.addAll(aBooks);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listBooks;
    }

    private ObservableList<availableBooks> listBook;

    public void showAvailableBooks() throws SQLException {

        listBook = dataList();

        col_ab_bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_ab_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_ab_bookType.setCellValueFactory(new PropertyValueFactory<>("genre"));
        col_ab_publishedDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_ab_image.setCellValueFactory(new PropertyValueFactory<>("image"));

        availableBooks_tableView.setItems(listBook);
    }

    public void selectAvailableBooks() {
        availableBooks bookData = availableBooks_tableView.getSelectionModel().getSelectedItem();

        int num = availableBooks_tableView.getSelectionModel().getFocusedIndex();

        if ((num - 1) < -1) {
            return;
        }
        availableBooks_title.setText(bookData.getTitle());

        String uri = "file:" + bookData.getImage();

        image = new Image(uri, 134, 171, false, true);

        availableBooks_imageView.setImage(image);
    }

    public void abTakeButton(ActionEvent event) {

        if (event.getSource() == take_btn) {
            borrowingBooks_form.setVisible(true);
            availableBooks_form.setVisible(false);
            returnBooks_form.setVisible(false);
            addBooks_form.setVisible(false);

            borrowingBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            addBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_borrowingbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_availablebtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_addbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            currentForm_label.setText("Borrowing Books");

        }
    }

    public void studentNumber() {
        studentNumber_label.setText(getData.studentNumber);
    }

    public void insertImage() throws SQLException {

        FileChooser open = new FileChooser();
        open.setTitle("Image File");
        open.getExtensionFilters().add(new ExtensionFilter("Image file", "*png", "*jpg"));
        Stage stage = (Stage) nav_form.getScene().getWindow();

        File file = open.showOpenDialog(stage);

        if (file != null) {

            image = new Image(file.toURI().toString(), 105, 100, false, true);
            circle_image.setFill(new ImagePattern(image));
            smallCircle_image.setFill(new ImagePattern(image));

            getData.path = file.getAbsolutePath();

//            changeProfile();
        }
    }

    public void changeProfile() throws SQLException {

        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        String query = "UPDATE student SET image = '+" + uri + "' WHERE studentNumber = '" + getData.studentNumber + "'";

        connection = Database.getDBConnection();

        try {

            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showProfile() {

        String uri = "file:" + getData.path;

        image = new Image(uri, 105, 100, false, true);
        circle_image.setFill(new ImagePattern(image));
        smallCircle_image.setFill(new ImagePattern(image));

    }

    public void designInserImage() {

        circle_image.setOnMouseEntered((MouseEvent event) -> {

            edit_btn.setVisible(true);

        });

        circle_image.setOnMouseExited((MouseEvent event) -> {

            edit_btn.setVisible(false);

        });

        edit_btn.setOnMouseEntered((MouseEvent event) -> {

            edit_btn.setVisible(true);
            edit_icon.setFill(Color.valueOf("#fff"));

        });

        edit_btn.setOnMousePressed((MouseEvent event) -> {

            edit_btn.setVisible(true);
            edit_icon.setFill(Color.RED);

        });

        edit_btn.setOnMouseExited((MouseEvent event) -> {

            edit_btn.setVisible(false);

        });

    }

    public void sideNavButtonDesign(ActionEvent event) throws SQLException {

        if (event.getSource() == halfNav_availablebtn) {

            borrowingBooks_form.setVisible(false);
            availableBooks_form.setVisible(true);
            returnBooks_form.setVisible(false);
            addBooks_form.setVisible(false);

            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            borrowingBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            addBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_availablebtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_borrowingbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_addbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            currentForm_label.setText("Available Books");

        } else if (event.getSource() == halfNav_borrowingbtn) {

            borrowingBooks_form.setVisible(true);
            availableBooks_form.setVisible(false);
            returnBooks_form.setVisible(false);
            addBooks_form.setVisible(false);

            borrowingBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            addBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_borrowingbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_availablebtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_addbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            currentForm_label.setText("Borrowing Books");

        } else if (event.getSource() == halfNav_returnbtn) {

            borrowingBooks_form.setVisible(false);
            availableBooks_form.setVisible(false);
            returnBooks_form.setVisible(true);
            addBooks_form.setVisible(false);

            returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            borrowingBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            addBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_returnbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_borrowingbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_availablebtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_addbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            currentForm_label.setText("Return Books");

            showBookReturn();
        } else if (event.getSource() == halfNav_addbtn) {

            borrowingBooks_form.setVisible(false);
            availableBooks_form.setVisible(false);
            returnBooks_form.setVisible(false);
            addBooks_form.setVisible(true);

            addBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            borrowingBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_addbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_borrowingbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_availablebtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            currentForm_label.setText("Add Books");
        }
    }

    public void navButtonDesign(ActionEvent event) throws SQLException {

        if (event.getSource() == availableBooks_btn) {

            borrowingBooks_form.setVisible(false);
            availableBooks_form.setVisible(true);
            returnBooks_form.setVisible(false);
            addBooks_form.setVisible(false);

            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            borrowingBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            addBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_availablebtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_borrowingbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_addbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            showAvailableBooks();

            currentForm_label.setText("Available Books");

        } else if (event.getSource() == borrowingBooks_btn) {

            borrowingBooks_form.setVisible(true);
            availableBooks_form.setVisible(false);
            returnBooks_form.setVisible(false);
            addBooks_form.setVisible(false);

            borrowingBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            addBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_borrowingbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_availablebtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_addbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            currentForm_label.setText("Borrowing Books");

        } else if (event.getSource() == returnBooks_btn) {

            borrowingBooks_form.setVisible(false);
            availableBooks_form.setVisible(false);
            returnBooks_form.setVisible(true);
            addBooks_form.setVisible(false);

            returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            borrowingBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            addBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_returnbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_borrowingbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_availablebtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_addbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            currentForm_label.setText("Return Books");

            showBookReturn();

        } else if (event.getSource() == addBooks_btn) {

            borrowingBooks_form.setVisible(false);
            availableBooks_form.setVisible(false);
            returnBooks_form.setVisible(false);
            addBooks_form.setVisible(true);

            addBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            borrowingBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            halfNav_addbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
            halfNav_borrowingbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_returnbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
            halfNav_availablebtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

            currentForm_label.setText("Add Books");
        }
    }

    private double x = 0;
    private double y = 0;

    public void sliderArrow() {

        TranslateTransition slide = new TranslateTransition();

        slide.setDuration(Duration.seconds(.5));
        slide.setNode(nav_form);
        slide.setToX(-224);

        TranslateTransition slide1 = new TranslateTransition();

        slide1.setDuration(Duration.seconds(.5));
        slide1.setNode(mainCenter_form);
        slide1.setToX(-224 + 90);

        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(.5));
        slide2.setNode(halfNav_form);
        slide2.setToX(0);

        slide.setOnFinished((ActionEvent event) -> {

            arrow_btn.setVisible(false);
            bars_btn.setVisible(true);

        });

        slide2.play();
        slide1.play();
        slide.play();

    }

    public void slideBars() {

        TranslateTransition slide = new TranslateTransition();

        slide.setDuration(Duration.seconds(.5));
        slide.setNode(nav_form);
        slide.setToX(0);

        TranslateTransition slide1 = new TranslateTransition();

        slide1.setDuration(Duration.seconds(.5));
        slide1.setNode(mainCenter_form);
        slide1.setToX(0);

        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(.5));
        slide2.setNode(halfNav_form);
        slide2.setToX(-77);

        slide.setOnFinished((ActionEvent event) -> {

            arrow_btn.setVisible(true);
            bars_btn.setVisible(false);

        });

        slide2.play();
        slide1.play();
        slide.play();
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            if (event.getSource() == logout_btn) {
                // TO SWAP FROM DASHBOARD TO LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent e) -> {

                    stage.setX(e.getScreenX() - x);
                    stage.setY(e.getScreenY() - y);

                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

                logout_btn.getScene().getWindow().hide();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        designInserImage();

        showProfile();

        try {
            //         TO SHOW THE AVAILABLE BOOKS
            showAvailableBooks();
        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

        studentNumber();

        studentNumberLabel();

        displayDate();

        gender();

        try {
            showABook();
            showBookReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField txtTitleBook;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtTypeBook;

    @FXML
    private TextField txtImage;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TableView<availableBooks> aBook;

    @FXML
    private TableColumn cTitle;

    @FXML
    private TableColumn cAuthor;

    @FXML
    private TableColumn cType;

    @FXML
    private TableColumn cImage;

    @FXML
    private TableColumn cDate;

    public void insertBook() {
        String query = "INSERT INTO book(bookTitle, author, bookType, image, date) VALUES (?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, txtTitleBook.getText());
            preparedStatement.setString(2, txtAuthor.getText());
            preparedStatement.setString(3, txtTypeBook.getText());
            preparedStatement.setString(4, txtImage.getText());
            preparedStatement.setString(5, String.valueOf(txtDate.getValue()));
            preparedStatement.execute();
        } catch (Exception e) {

        }
    }

    public void save() throws SQLException {

        connection = Database.getDBConnection();
        String bookTitle = txtTitleBook.getText();
        String author = txtAuthor.getText();
        String type = txtTypeBook.getText();
        String image = txtImage.getText();
        String date = String.valueOf(txtDate.getValue());

        insertBook();
        showABook();
//            clear();ss

    }

    public void updateBook() {
        try {
            connection = Database.getDBConnection();
            String query1 = "UPDATE book SET "
                    + " bookTitle ='" + txtTitleBook.getText()
                    + "', author ='" + txtAuthor.getText()
                    + "', bookType ='" + txtTypeBook.getText()
                    + "', image ='" + txtImage.getText()
                    + "', date ='" + String.valueOf(txtDate.getValue())
                    + "' WHERE bookTitle = '" + txtTitleBook.getText() + "';";
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.execute();
            showABook();
//            clear();
        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
//            ex.getSQLState();
        }
    }

    public void showABook() throws SQLException {

        listBook = dataList();

        cTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        cAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        cType.setCellValueFactory(new PropertyValueFactory<>("genre"));
        cImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        cDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        aBook.setItems(listBook);
    }

    public void selectBook() {

        int num = aBook.getSelectionModel().getSelectedIndex();

        availableBooks b1 = aBook.getSelectionModel().getSelectedItem();

        if (num - 1 < -1) {
            return;
        }

        txtTitleBook.setText(b1.getTitle());
        txtAuthor.setText(b1.getAuthor());
        txtTypeBook.setText(b1.getGenre());
        txtImage.setText(b1.getImage());

    }

    public void deleteBook() throws SQLException {
        connection = Database.getDBConnection();
        String sql = "delete from book where bookTitle = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, txtTitleBook.getText());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "The Data Deleted Successfully");
            showABook();
            clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
}
