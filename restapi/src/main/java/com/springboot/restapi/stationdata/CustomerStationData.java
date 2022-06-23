package com.springboot.restapi.stationdata;

import java.util.Date;

public class CustomerStationData {
        public int id_customer;
        public int id_station;
        public int kwh;
        public Date datetime;

        public CustomerStationData() {
        }

    public CustomerStationData(int id_customer, int id_station, int kwh, Date datetime) {
        this.id_customer = id_customer;
        this.id_station = id_station;
        this.kwh = kwh;
        this.datetime = datetime;
    }
}
