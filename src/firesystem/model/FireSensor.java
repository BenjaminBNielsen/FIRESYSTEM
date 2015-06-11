/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firesystem.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import firesystem.ApiConnectionPopup;
import firesystem.DatabaseConnectionPopup;
import java.io.IOException;
import java.net.URL;
import org.bson.types.ObjectId;

/* @author Benjamin */
public class FireSensor extends Thread {

    public static final int REFRESH_RATE = 10000; // refresh rate in ms
    public static final String BRAIN_URL_STRING = "http://"+ApiConnectionPopup.getsHostName()+"/sensor-api.php"; // url to brain
    public static final String SMS_API_STRING = "http://ecuanota.com/bridge-send-sms-to-mobile.php"; // url to sms api !might get outdated
    public static final String EMAIL_API_STRING = "http://ecuafactura.com/send-email.php"; // url to email api !might get outdated

    private boolean enabled = false;
    private int number;
    private String jsonId;
    private String name;
    private int temperature = 0;
    private int humidity = 0;
    private DBCollection users;
    private DBCollection temperatureCollection;

    public FireSensor(int number, String id) {
        this.setDaemon(true);
        this.number = number;
        this.jsonId = id;
        name = "sensor-" + number;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled() {
        enabled = false;
    }

    /**
     * Saves output every x ms based on the constant refresh rate. Saves in mongodb and uploads to server on the given server brain url.
     */
    @Override
    public void run() {
        while (true) {
            try {
                if (enabled == true) {
                    
//                    System.out.println(BRAIN_URL_STRING);
                    //Create mongodb connection
                    MongoClient mdb = new MongoClient(DatabaseConnectionPopup.getsHostName());

                    DB db = mdb.getDB(DatabaseConnectionPopup.getsDbname());

                    //Get relevant connections
                    users = db.getCollection("users");
                    temperatureCollection = db.getCollection("temperatures");

                    //Find my document in the users collection
                    DBObject ME = findDocumentById(jsonId, users);

                    //If document doesn't exist, add it to the users collection
                    if (ME == null) {
                        ME = new BasicDBObject("_id", new ObjectId(jsonId));
                        users.insert(ME);
                    }

                    temperature = (int) (Math.random() * 100);
                    humidity = (int) (Math.random() * 100);
                    String mobile = "";
                    String email = "";

                    if (ME.containsField("mobile")) {
                        mobile = (String) ME.get("mobile");
                    }
                    if (ME.containsField("email")) {
                        email = (String) ME.get("email");
                    }

                    //Write temperature to mongodb
                    temperatureCollection.insert(new BasicDBObject("temperature", temperature).append("humidity", humidity));

                    //Send temperature to server
                    String temperatureJson = "{\"name\":\"sensor-" + number + "\",\"temperature\":" + temperature + ",\"humidity\":" + humidity
                            + ",\"mobile\":\"" + mobile + "\",\"email\":\"" + email + "\"}";

                    URL url = new URL(BRAIN_URL_STRING + "?temperature=" + temperatureJson
                            + "&sensorId=" + number);

                    url.openStream();
                    //Hvis temperaturen er over 90 så send email og sms.
                    if (temperature > 90) {
                        //Send sms
                        String message = "Room%20for%20sensor%20" + number + "%20is%20on%20fire!";
                        if (ME.containsField("mobile")) {
                            url = new URL(SMS_API_STRING + "?sMobileNumber=" + mobile + "&sMessage=" + message);

                            url.openStream();
                        }

                        //Send email
                        if (ME.containsField("email")) {
                            String subject = "FIRE";
                            url = new URL(EMAIL_API_STRING + "?email=" + email + "&subject=" + subject + "&message=" + message);

                            url.openStream();
                        }

                    }

                    mdb.close();
                }

                Thread.sleep(REFRESH_RATE);
            } catch (InterruptedException ex) {
                //
            } catch (IOException ex) {
                //
            } catch (Exception e) {
                enabled = false;
            }

        }
    }
    
    public static DBObject findDocumentById(String id, DBCollection coll) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject dbObj = coll.findOne(query);
        return dbObj;
    }

    public String getJsonId() {
        return jsonId;
    }

    public void setJsonId(String jsonId) {
        this.jsonId = jsonId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.enabled = isEnabled;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getSensorName() {
        return name;
    }

}
