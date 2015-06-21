package firesystem;

import handler.FileHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DatabaseConnectionPopup extends ConnectionPopup {

    private TextField hostnameInput, dbnameInput, collectionInput;
    private Label hostname, dbname, collection;
    private Button okButton;
    private Image icon = new Image(getClass().getResourceAsStream("/firesystem/mongoDBicon.png"));
    private static String sHostName, sDbname, sCollectionName;

    public DatabaseConnectionPopup() throws FileNotFoundException {
        VBox root = new VBox();
        root.getStyleClass().add("popup-vbox");
        initNodes(root);
        
        Scene scene = new Scene(root);
        
        this.setTitle("Connect to mongodb");
        this.setScene(scene);
        this.getIcons().add(icon);
        scene.getStylesheets().add("firesystem/style.css");
        
        this.setOnCloseRequest(e -> {System.exit(0);});
        
        display();
    }

    private void initNodes(VBox root) throws FileNotFoundException {
        hostnameInput = new TextField("localhost");
        dbnameInput = new TextField("test");
        hostname = new Label("Hostname");
        hostname.getStyleClass().add("label");
        dbname = new Label("Databasename");
        collection = new Label("Collection");
        collectionInput = new TextField("Collection");
        readDbInfo();

        okButton = new Button("Connect");

        
        
        okButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(okButton, Priority.ALWAYS);
        okButton.setOnAction(e -> {
            try {
                sHostName = hostnameInput.getText();
                sDbname = dbnameInput.getText();
                sCollectionName = collectionInput.getText();
                FileHandler.writeFile("src/firesystem/dbSetup.txt", setDbInfo());
                this.close();
            } catch (IOException ex) {
                Logger.getLogger(DatabaseConnectionPopup.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        okButton.setDefaultButton(true);


        root.getChildren().addAll(hostname, hostnameInput, dbname, dbnameInput, collection, collectionInput, okButton);
    }

    public static String getsHostName() {
        return sHostName;
    }

    public static void setsHostName(String host) {
        sHostName = host;
    }

    public static String getsDbname() {
        return sDbname;
    }

    public static void setsDbname(String name) {
        sDbname = name;
    }
    
    public static String getCollectionName(){
        return sCollectionName;
    }
    
    public static void setsCollectionName(String collection){
        sCollectionName = collection;
    }
        
    public void readDbInfo() throws FileNotFoundException{
        ArrayList<String> fh = FileHandler.readFile("src/firesystem/dbSetup.txt");
        System.out.println(fh.get(0));
        if(fh.size() > 0) {
        hostnameInput.setText(fh.get(0));
        dbnameInput.setText(fh.get(1));
        collectionInput.setText(fh.get(2));
        }       
    }
    
    public String[] setDbInfo(){
        String[] lineArr = new String[3];
        lineArr[0] = hostnameInput.getText();
        lineArr[1] = dbnameInput.getText();
        lineArr[2] = collectionInput.getText();
        
        return lineArr;
    }
    

    public void display() {
        this.showAndWait();
    }

}
