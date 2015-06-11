package firesystem;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExceptionPopup extends Stage {

    private Image icon = new Image(getClass().getResourceAsStream("/firesystem/ErrorIcon.png"));
    private Label initMessage = new Label();
    private Label errorMessage = new Label();
    private Button okButton = new Button("Okay");

    public ExceptionPopup(String initMessage, String errorMessage) {
        VBox root = new VBox();
        root.getStyleClass().add("popup-vbox");
        initNodes(root);

        Scene scene = new Scene(root);

        this.setTitle("ERROR");
        this.setScene(scene);
        this.getIcons().add(icon);
        scene.getStylesheets().add("firesystem/style.css");

        display(initMessage,errorMessage);
    }

    public void initNodes(VBox root) {
        okButton.setOnAction(e -> {
            this.close();
        });
        okButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(okButton, Priority.ALWAYS);
        initMessage.setPrefWidth(400);
        initMessage.setWrapText(true);
        
        errorMessage.setPrefWidth(400);
        errorMessage.getStyleClass().add("error-label");
        errorMessage.setWrapText(true);
        
        root.getChildren().addAll(initMessage, errorMessage, okButton);
    }

    public void display(String sInitMessage, String sErrorMessage) {
        initMessage.setText(sInitMessage);
        errorMessage.setText(sErrorMessage);
        this.showAndWait();
    }
}
