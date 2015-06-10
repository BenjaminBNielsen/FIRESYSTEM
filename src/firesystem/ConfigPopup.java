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
import firesystem.model.FireSensor;
import java.net.UnknownHostException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfigPopup extends Stage {

    private TextField mobileInput, emailInput;
    private Label mobile, email;
    private Button okButton, cancelButton;
    private FireSensor sensor;

    private MongoClient host;
    private DB db;
    private DBCollection users;

    private String sMobile, sEmail;

    public ConfigPopup(FireSensor sensor) {
        try{
        host = new MongoClient(DatabaseConnectionPopup.getsHostName());

        db = host.getDB(DatabaseConnectionPopup.getsDbname());

        //Get relevant connections
        users = db.getCollection("users");
        }catch(UnknownHostException ex){
            System.out.println(ex.getMessage());
        }

        this.sensor = sensor;
        VBox root = new VBox();
        root.getStyleClass().add("popup-vbox");
        initNodes(root);

        Scene scene = new Scene(root);

        this.setTitle("Connect to mongodb");
        this.setScene(scene);
        scene.getStylesheets().add("firesystem/style.css");
        display();
    }

    private void initNodes(VBox root) {
        mobileInput = new TextField();
        emailInput = new TextField();
        mobile = new Label("Mobile");
        mobile.getStyleClass().add("label");
        email = new Label("Email");
        okButton = new Button("Okay");
        cancelButton = new Button("Cancel");
        HBox hb = new HBox(15);

        cancelButton.setMaxWidth(Double.MAX_VALUE);
        okButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(okButton, Priority.ALWAYS);
        HBox.setHgrow(cancelButton, Priority.ALWAYS);
        okButton.setOnAction(e -> {
            if (!mobileInput.getText().equals("")) {
                updateMobile(mobileInput.getText());
            }
            if (!emailInput.getText().equals("")) {
                updateEmail(emailInput.getText());
            }
            host.close();
            this.close();
        });
        cancelButton.setOnAction(e -> {
            host.close();
            this.close();
        });
        okButton.setDefaultButton(true);

        hb.getChildren().addAll(okButton, cancelButton);
        root.getChildren().addAll(mobile, mobileInput, email, emailInput, hb);
    }

    public String getsHostName() {
        return sMobile;
    }

    public void setsHostName(String sHostName) {
        this.sMobile = sHostName;
    }

    public String getsDbname() {
        return sEmail;
    }

    public void setsDbname(String sDbname) {
        this.sEmail = sDbname;
    }

    public void display() {
        this.showAndWait();
    }

    public void updateMobile(String mobile) {
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject().append("mobile", mobile));

        users.update(FireSensor.findDocumentById(sensor.getJsonId(), users), newDocument);
    }

    public void updateEmail(String email) {
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject().append("email", email));

        users.update(FireSensor.findDocumentById(sensor.getJsonId(), users), newDocument);
    }
    
}
