package com.example.javafx;

//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
//import org.json.JSONArray;
//import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class HelloController {
    private static final String API = "http://localhost:8080/stations";
    //private static final String API = "http://localhost:8080/IDCustomer";
    @FXML
    private Label welcomeText;
    @FXML
    private TextField IDCustomer;
    @FXML
    //protected void onHelloButtonClick(){welcomeText.setText(IDCustomer.getText());}

    private void stationInfo() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API + "/stationsInfo/" + IDCustomer.getText()))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        IDCustomer.setText("");
    }

}