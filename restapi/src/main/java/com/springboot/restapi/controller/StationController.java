package com.springboot.restapi.controller;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.springboot.restapi.queue.Mq;
import com.springboot.restapi.stationdata.CustomerStationData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@RestController
public class StationController {
    private final static String BROKER_URL = "tcp://localhost:61616"; //6616
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/chstation?user=admin&password=password";

    @PostMapping("/stations/stationsInfo/{IDCustomer}")
    public String sendIDCustomer(@PathVariable String IDCustomer) {

        Mq.send(IDCustomer, BROKER_URL);

        return IDCustomer;
    }

    @GetMapping(value ="/stations/{id}",produces = "application/json")
    public List<CustomerStationData> readOne(@PathVariable int id) {
        List<CustomerStationData> stations = new ArrayList<>();

        try (Connection conn = connect()) {

            String sql = "SELECT * FROM customerdata where customer_id = " + id;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CustomerStationData CustomerStationData = new CustomerStationData();

                CustomerStationData.id_customer = resultSet.getInt("customer_id");
                CustomerStationData.id_station = resultSet.getInt("station_id");
                CustomerStationData.kwh = resultSet.getInt("kwh");
                CustomerStationData.datetime = resultSet.getDate("datetime");

                stations.add(CustomerStationData);
            }

            generateInvoice(stations);


            for (CustomerStationData station : stations) {
                System.out.println(station.id_station);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();

        }

        return stations;
    }


    @GetMapping(value ="/stations",produces = "application/json")
    public List<CustomerStationData> readAll() {
        List<CustomerStationData> stations = new ArrayList<>();

        try (Connection conn = connect()) {

            String sql = "SELECT * FROM customerdata";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CustomerStationData CustomerStationData = new CustomerStationData();

                CustomerStationData.id_customer = resultSet.getInt("customer_id");
                CustomerStationData.id_station = resultSet.getInt("station_id");
                CustomerStationData.kwh = resultSet.getInt("kwh");
                CustomerStationData.datetime = resultSet.getDate("datetime");
                //System.out.println(CustomerStationData.id_customer);
                stations.add(CustomerStationData);
            }

            //generateInvoice();

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return stations;
    }
        private Connection connect() throws SQLException {
            return DriverManager.getConnection(DB_CONNECTION);
        }


        public void generateInvoice(List<CustomerStationData> stations) throws IOException {


            //System.out.println(stations);


           // System.out.println("pdpfdpfpdpfdppf");
            PdfWriter writer = new PdfWriter("new.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);




            Paragraph loremIpsumHeader = new Paragraph("Customer Invoice")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setBold()
                    .setFontColor(ColorConstants.BLACK);
            document.add(loremIpsumHeader);

            for(CustomerStationData csd : stations){
                document.add(new Paragraph(csd.toString()));
//                document.add(new Paragraph(String.valueOf(csd.id_station)));
//                document.add(new Paragraph(String.valueOf(csd.id_customer)));
            }
            document.add(new Paragraph("Please pay :)"));
            document.close();
        }

}

