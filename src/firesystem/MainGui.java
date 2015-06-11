/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firesystem;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import firesystem.model.FireSensor;
import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGui extends Application {

    private FireSensor[] sensors;
    private boolean hasConnection = true;
    private boolean enabled = true;
    private DatabaseConnectionPopup databaseConnectionPopup;
    private ApiConnectionPopup websitePopup;
    private final Image icon = new Image(getClass().getResourceAsStream("/firesystem/FireSystemIcon.png"));
    
    @Override
    public void start(Stage primaryStage) {
        try{
        databaseConnectionPopup = new DatabaseConnectionPopup();
        websitePopup = new ApiConnectionPopup();
        
        initSensors(primaryStage);

        VBox root = new VBox();

        Scene scene = new Scene(root);
        initHeader(root);
        initList(root);

        primaryStage.setTitle("Firesystem");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(icon);
        scene.getStylesheets().add("firesystem/style.css");
        primaryStage.show();
        }catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
            primaryStage.close();
        }

    }

    public FireSensor[] getSensors(Stage primaryStage) {
        FireSensor[] sensors = new FireSensor[13];
        try {

            MongoClient mongoClient = new MongoClient(DatabaseConnectionPopup.getsHostName());

            DB db = mongoClient.getDB(DatabaseConnectionPopup.getsDbname());

            DBCollection coll = db.getCollection("users");

            DBCursor cursor = coll.find();

            for (int i = 0; i < 13; i++) {
                DBObject thisUser;

                //Make sure the users exist
                if (cursor.hasNext()) {
                    thisUser = cursor.next();
                } else {
                    thisUser = new BasicDBObject();
                    coll.insert(thisUser);
                }
                int j = i + 1;
                sensors[i] = new FireSensor(j, thisUser.get("_id").toString());
            }
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            new ExceptionPopup("Der kunne ikke oprettes forbindelse til mongodb. Mongodb gav følgende fejlbesked: ", e.getMessage());
            System.exit(0);
        }

        return sensors;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void initSensors(Stage primaryStage) {
        sensors = getSensors(primaryStage);

        for (int i = 0; i < sensors.length; i++) {
            sensors[i].setEnabled(true);
            sensors[i].start();

        }

    }

    private void initHeader(VBox root) {
        BorderPane header = new BorderPane();
        header.getStyleClass().add("header");
        String hText = "Firesystem";
        Label headerText = new Label(hText);
        HBox buttonPanel = new HBox();
        buttonPanel.getStyleClass().add("hbox");

        Button browserButton = new Button();
        
        browserButton.getStyleClass().add("browser-button");
        browserButton.setTooltip(new Tooltip("View the display webside in a new window."));
        browserButton.setOnAction(e -> {
            Browser b = new Browser(ApiConnectionPopup.getsHostName());
            b.start(new Stage());
        });
        Button dropDBButton = new Button();
        dropDBButton.getStyleClass().add("dropdb-button");
        dropDBButton.setTooltip(new Tooltip("Delete alle documents in the temperatures collection"));
        dropDBButton.setOnAction(e -> {
            DialogBox dib = new DialogBox();
        });

        buttonPanel.getChildren().addAll(browserButton, dropDBButton);
        header.setLeft(headerText);
        header.setRight(buttonPanel);
        root.getChildren().add(header);
    }

    private void initList(VBox root) {
        ScrollPane sp = new ScrollPane();
        sp.getStyleClass().add("scroll-pane");
        VBox vb = new VBox();
        sp.setContent(vb);
        sp.setPrefViewportHeight(400);
        sp.setFitToWidth(true);
        sp.setPrefViewportWidth(250);
        for (int i = 0; i < sensors.length; i++) {
            ListItem li = new ListItem(sensors[i]);
            vb.getChildren().add(li);
        }

        root.getChildren().add(sp);
    }

}
