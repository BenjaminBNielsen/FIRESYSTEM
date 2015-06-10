package firesystem;

import handler.FileHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ApiConnectionPopup extends ConnectionPopup {

    private TextField urlInput;
    private Label url;
    private Button okButton;
    private Image icon = new Image(getClass().getResourceAsStream("/firesystem/BrowserButton.png"));
    private static String sHostName, sDbname;

    public ApiConnectionPopup() throws FileNotFoundException {
        VBox root = new VBox();
        root.getStyleClass().add("popup-vbox");
        initNodes(root);

        Scene scene = new Scene(root);

        this.setTitle("Connect to API");
        this.setScene(scene);
        this.getIcons().add(icon);
        scene.getStylesheets().add("firesystem/style.css");
        display();
    }

    private void initNodes(VBox root) throws FileNotFoundException {
        urlInput = new TextField("localhost");
        url = new Label("Url");
        url.getStyleClass().add("label");
        okButton = new Button("Connect");

        readApiInfo();
        
        okButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(okButton, Priority.ALWAYS);
        okButton.setOnAction(e -> {
            try {
                sHostName = urlInput.getText();
                FileHandler.writeFile("src/firesystem/apiSetup.txt", setApiInfo());
                this.close();
            } catch (IOException ex) {
                Logger.getLogger(ApiConnectionPopup.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        okButton.setDefaultButton(true);

//                okButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
//            
//        @Override
//        public void handle(ActionEvent event) {
//
//            if (event.getEventType().equals(ActionEvent.ACTION)) {
//                try {
//                    FileHandler.writeFile("apiSetup.txt", setApiInfo());
//                } catch (IOException ex) {
//                    Logger.getLogger(DatabaseConnectionPopup.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }     
//        });
        root.getChildren().addAll(url, urlInput, okButton);
    }

    public static String getsHostName() {
        return sHostName;
    }

    public static void setsHostName(String host) {
        sHostName = host;
    }
    
      public void readApiInfo() throws FileNotFoundException{
        ArrayList<String> fh = FileHandler.readFile("src/firesystem/apiSetup.txt");
        System.out.println(fh.get(0));
        if(fh.size() != 0) {
        urlInput.setText(fh.get(0));
        }
    }
    
         public String[] setApiInfo(){
        String[] lineArr = new String[1];
        lineArr[0] = urlInput.getText();
        
        
        return lineArr;
    }
    
    

    public void display() {
        this.showAndWait();
    }

}
