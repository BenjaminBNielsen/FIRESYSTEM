/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firesystem;

import firesystem.model.FireSensor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ListItem extends BorderPane {

    private Label nameLabel;
    private Button configButton;
    private ToggleButton statusButton;
    private VBox labelPanel;
    private HBox buttonPanel;
    private ConfigPopup cp;

    //fields
    private FireSensor sensor;

    public ListItem(FireSensor sensor) {
        this.sensor = sensor;
        initLabels();
        initButtons();
        this.getStyleClass().add("list-item");
    }

    private void initLabels() {
        String sn = sensor.getSensorName().toUpperCase();
        sn = sn.charAt(0) + sn.substring(1).toLowerCase();
        nameLabel = new Label(sn);
        labelPanel = new VBox();
        labelPanel.getChildren().addAll(nameLabel);

        this.setLeft(labelPanel);
    }

    private void initButtons() {
        configButton = new Button();
        configButton.getStyleClass().add("config-button");
        configButton.setOnAction(e -> {
            if (cp == null) {
                cp = new ConfigPopup(sensor);
            } else {
                cp.display();
            }
        });
        statusButton = new ToggleButton();
        statusButton.setPrefWidth(35);
        statusButton.setOnAction(e -> {
            sensor.setEnabled(!sensor.isEnabled());
            System.out.println(sensor.isEnabled());
        });

        buttonPanel = new HBox();
        buttonPanel.getStyleClass().add("hbox");
        buttonPanel.getChildren().addAll(configButton, statusButton);

        this.setRight(buttonPanel);
    }

}
