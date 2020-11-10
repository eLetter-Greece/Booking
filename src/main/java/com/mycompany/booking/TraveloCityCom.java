/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.booking;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author mac
 */
public class TraveloCityCom implements ISearchEngine {

    @Override
    public void executeSearch(String searchString, String checkInDate, String checkOutDate) {
        String s = "https://www.travelocity.com/Hotel-Search?adults=1&d1=" + checkInDate + "&d2=" + checkOutDate + "&destination=" + searchString + "&endDate=" + checkOutDate + "&rooms=1&semdtl=&sort=RECOMMENDED&startDate=2020-12-01&theme=&useRewards=false&userIntent&star=10&star=20&star=30&star=40&star=50";
        Document doc = null;
        String data = "";
        try {
            doc = Jsoup.connect(s).get();
            System.out.println(doc.title());
            data = "Travelocity.com" + "\n";
            Elements hotels = doc.select(".results-list > li"); // thelei to panw panw css selector
//            System.out.println(hotels);
            System.out.println("Hotel Count: " + hotels.size());
            for (int i = 0; i < hotels.size() - 1; i++) {
                data += "Name: " + hotels.get(i).getElementsByAttributeValue("data-stid", "content-hotel-title").text() + ", ";
                data += "Stars: " + hotels.get(i).getElementsByClass("uitk-rating").text() + ", ";
                data += "Reviews Score: " + hotels.get(i).getElementsByAttributeValue("data-stid", "content-hotel-reviews-rating").text() + ", ";
                data += "Price: " + hotels.get(i).getElementsByAttributeValue("data-stid", "content-hotel-lead-price").text() + "\n";
                // allagh twn css selectors
                System.out.print("Name: " + hotels.get(i).getElementsByAttributeValue("data-stid", "content-hotel-title").text() + ", ");
                System.out.print("Stars: " + hotels.get(i).getElementsByClass("uitk-rating").text() + ", ");
                System.out.print("Reviews Score: " + hotels.get(i).getElementsByAttributeValue("data-stid", "content-hotel-reviews-rating").text() + ", ");
                System.out.print("Price: " + hotels.get(i).getElementsByAttributeValue("data-stid", "content-hotel-lead-price").text() + "\n");
            }
            Main.writeUsingFileWriter(data, "Travelocity.csv");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
