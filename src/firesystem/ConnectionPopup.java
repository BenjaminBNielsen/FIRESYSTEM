package firesystem;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConnectionPopup extends Stage {

    private TextField hostnameInput, dbnameInput;
    private Label hostname, dbname;
    private Button okButton;

    private static String sHostName, sDbname;

    public ConnectionPopup() {
//        VBox root = new VBox();
//        root.getStyleClass().add("popup-vbox");
//        initNodes(root);
//
//        Scene scene = new Scene(root);
//
//        this.setTitle("Connect to mongodb");
//        this.setScene(scene);
//        scene.getStylesheets().add("firesystem/style.css");
//        display();
    }

    private void initNodes(VBox root) {
//        hostnameInput = new TextField("localhost");
//        dbnameInput = new TextField("test");
//        hostname = new Label("Hostname");
//        hostname.getStyleClass().add("label");
//        dbname = new Label("Databasename");
//        okButton = new Button("Connect");
//
//        okButton.setMaxWidth(Double.MAX_VALUE);
//        HBox.setHgrow(okButton, Priority.ALWAYS);
//        okButton.setOnAction(e -> {
//            sHostName = hostnameInput.getText();
//            sDbname = dbnameInput.getText();
//            this.close();
//        });
//        okButton.setDefaultButton(true);
//
//        root.getChildren().addAll(hostname, hostnameInput, dbname, dbnameInput, okButton);
    }

//    public static String getsHostName() {
//        return sHostName;
//    }
//
//    public static void setsHostName(String host) {
//        sHostName = host;
//    }

//    public static String getsDbname() {
//        return sDbname;
//    }
//
//    public static void setsDbname(String name) {
//        sDbname = name;
//    }
    
    

    public void display() {
        this.showAndWait();
    }

}
