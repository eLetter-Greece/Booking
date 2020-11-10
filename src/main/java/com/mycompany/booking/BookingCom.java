/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author mac
 */
public class BookingCom implements ISearchEngine {

    @Override
    public void executeSearch(String searchString, String checkInDate, String checkOutDate) {
        String checkInYear = checkInDate.substring(0, 4);
        String checkInMonth = checkInDate.substring(5, 6);
        String checkInDay = checkInDate.substring(8, 9);

        String checkOutYear = checkOutDate.substring(0, 4);
        String checkOutMonth = checkOutDate.substring(5, 6);
        String checkOutDay = checkOutDate.substring(8, 9);

        String s = "https://www.booking.com/searchresults.html?ss=" + searchString + "&no_rooms=1&group_adults=1&checkin_year="
                + checkInYear + "&checkin_month=" + checkInMonth + "&checkin_monthday=" + checkInDay + "&checkout_year="
                + checkOutYear + "&checkout_month=" + checkOutMonth + "&checkout_monthday=" + checkOutDay;
        Document doc = null;
        String data = "";
        try {
            doc = Jsoup.connect(s).get();
            data = doc.title() + "\n";
            System.out.println(doc.title());
            Elements hotels = doc.select("#hotellist_inner > div");
            System.out.println("Hotel Count: " + hotels.size());
            List<Integer> falseHotels = new ArrayList<>();
            for (int i = 0; i < hotels.size(); i++) {
                if (hotels.get(i).getElementsByClass("sr-hotel__name").text().length() == 0) {
                    falseHotels.add(i);
                    continue;
                }

            }
            for (Integer falseHotel : falseHotels) {
                hotels.remove(falseHotel.intValue());
            }
            for (int i = 0; i < hotels.size(); i++) {
                data += "Name: " + hotels.get(i).getElementsByClass("sr-hotel__name").text() + ", ";
                data += "Stars: " + hotels.get(i).getElementsByClass("bui-rating__item").size() + ", ";
                data += "Reviews Score: " + hotels.get(i).getElementsByClass("bui-review-score__badge").text() + ", ";
                data += "Price: " + hotels.get(i).getElementsByClass("bui-price-display__value").text() + "\n";
                System.out.print("Name: " + hotels.get(i).getElementsByClass("sr-hotel__name").text() + ", ");
                System.out.print("Stars: " + hotels.get(i).getElementsByClass("bui-rating__item").size() + ", ");
                System.out.print("Reviews Score: " + hotels.get(i).getElementsByClass("bui-review-score__badge").text() + ", ");
                System.out.print("Price: " + hotels.get(i).getElementsByClass("bui-price-display__value").text() + "\n");
            }
            Main.writeUsingFileWriter(data, "BookingCom.csv");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
