/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firesystem;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DialogBox extends Stage {

    private Label messageLabel;
    private Button okButton, cancelButton;

    private MongoClient host;
    private DB db;
    private DBCollection temperatures;

    public DialogBox() {
        try {
            host = new MongoClient(DatabaseConnectionPopup.getsHostName());

            db = host.getDB(DatabaseConnectionPopup.getsDbname());

            //Get relevant connections
            temperatures = db.getCollection(DatabaseConnectionPopup.getCollectionName());
        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        }

        VBox root = new VBox();
        root.getStyleClass().add("popup-vbox");
        initNodes(root);

        Scene scene = new Scene(root);

        this.setTitle("Reset Temperatures");
        this.setScene(scene);
        scene.getStylesheets().add("firesystem/style.css");
        display();
    }

    private void initNodes(VBox root) {
        messageLabel = new Label("Are you sure you want to delete alle temperature documents?");
        messageLabel.getStyleClass().add("message-label");
        okButton = new Button("Yes");
        cancelButton = new Button("Cancel");
        HBox hb = new HBox(15);

        cancelButton.setMaxWidth(Double.MAX_VALUE);
        okButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(okButton, Priority.ALWAYS);
        HBox.setHgrow(cancelButton, Priority.ALWAYS);
        okButton.setOnAction(e -> {
            temperatures.remove(new BasicDBObject());
            host.close();
            this.close();
        });
        cancelButton.setOnAction(e -> {
            host.close();
            this.close();
        });
        okButton.setDefaultButton(true);

        hb.getChildren().addAll(okButton, cancelButton);
        root.getChildren().addAll(messageLabel, hb);
    }

    public void display() {
        this.showAndWait();
    }

}
