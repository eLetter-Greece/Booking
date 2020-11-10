/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.booking;

import java.io.File;
import java.io.FileWriter;
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
public class Main {

    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        UserInputs inputs = menu.getInputs();
        SearchEngineFactory searchEngineFactory = new SearchEngineFactory();
        ISearchEngine seBooking = searchEngineFactory.getSearchEngine("BOOKING");
        ISearchEngine seTraveloCity = searchEngineFactory.getSearchEngine("TRAVELOCITYCOM");

        seBooking.executeSearch(inputs.destination, inputs.checkIn, inputs.checkOut);
        seTraveloCity.executeSearch(inputs.destination, inputs.checkIn, inputs.checkOut);

//        searchBooking(inputs.destination, inputs.checkIn, inputs.checkOut);
//        searchTravelocity(inputs.destination, inputs.checkIn, inputs.checkOut);
//        searchBooking("Nafplio", "2021/01/01", "2021/01/02");
//        searchTravelocity("Nafplio", "2021/01/01", "2021/01/02");
    }

    public static void searchBooking(String searchString, String checkInDate, String checkOutDate) {
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
            writeUsingFileWriter(data, "BookingCom.csv");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void searchTravelocity(String searchString, String checkInDate, String checkOutDate) {
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
            writeUsingFileWriter(data, "Travelocity.csv");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void writeUsingFileWriter(String data, String fileName) {
        File file = new File(fileName);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
