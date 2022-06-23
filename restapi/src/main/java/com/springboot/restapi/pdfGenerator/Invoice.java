package com.springboot.restapi.pdfGenerator;

import com.springboot.restapi.stationdata.CustomerStationData;

public class Invoice {
    CustomerStationData person = new CustomerStationData();

    public CustomerStationData getPerson() {
        return person;
    }

    public void setPerson(CustomerStationData person) {
        this.person = person;
    }
}
