/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.booking;

/**
 *
 * @author mac
 */
public class SearchEngineFactory {

    public ISearchEngine getSearchEngine(String type) {
        switch (type) {
            case "booking":
            case "Booking":
            case "BOOKING":
                return new BookingCom();
            // no need for break;
            case "travelocitycom":
            case "TraveloCityCom":
            case "TRAVELOCITYCOM":
                return new TraveloCityCom();
            // no need for break;
        }
        return null;
    }

}
