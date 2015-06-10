/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firesystem;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Browser extends Application {

    
    private Image icon = new Image(getClass().getResourceAsStream("/firesystem/BrowserButton.png"));

    private Scene scene;
    private String urlInfo;
    
    Browser(String sHostName) {
       urlInfo = sHostName;
    }

    @Override
    public void start(Stage stage) {
        // create the scene
        stage.setTitle("Web View");
        stage.getIcons().add(icon);
        scene = new Scene(new BrowserRegion(urlInfo), 1000, 850, Color.web("#666970"));
        stage.setScene(scene);
        stage.show();
    }

}

class BrowserRegion extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public BrowserRegion(String urlInfo) {
        //apply the styles
        //getStyleClass().add("browser");
        // load the web page
        //getClass().getResource("index.html").toExternalForm()
        if (urlInfo.equals("iqvsiq.com")){
           webEngine.load(getClass().getResource("index.html").toExternalForm());
            System.out.println("DEN RETTE IP");
        }
        else { webEngine.load("http://"+ApiConnectionPopup.getsHostName());
        }
        //add the web view to the scene
        getChildren().add(browser);

    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computePrefWidth(double height) {
        return 750;
    }

    @Override
    protected double computePrefHeight(double width) {
        return 500;
    }
}
