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
public class Main {
    
    static String searchStringBooking3 = "https://www.booking.com/searchresults.html?ss=Nafplio&no_rooms=1&group_adults=1&checkin_year=2021&checkin_month=1&checkin_monthday=1&checkout_year=2021&checkout_month=1&checkout_monthday=2";
    static String searchStringBooking4 = "https://www.booking.com/searchresults.html?ss=Anogia&no_rooms=1&group_adults=1&checkin_year=2021&checkin_month=1&checkin_monthday=1&checkout_year=2021&checkout_month=1&checkout_monthday=2&rows=100";
    
    public static void main(String[] args) {
        Document doc = null;
        Document doc2 = null;
        try {
            doc = Jsoup.connect(searchStringBooking3).get();
            System.out.println(doc.title());
            Elements hotels = doc.select("#hotellist_inner > div");
//            System.out.println("Name: " + hotels.get(0).getElementsByClass("sr-hotel__name").text());
//            System.out.println("Stars: " + hotels.get(0).getElementsByClass("bui-rating__item").size());
//            System.out.println("Reviews Score: " + hotels.get(0).getElementsByClass("bui-review-score__badge").text());
//            System.out.println("Price: " + hotels.get(0).getElementsByClass("bui-price-display__value").text());

//            System.out.println("hotelNames count = " + hotelNames.size());
//            Elements hotelReviews = doc.getElementsByClass("bui-review-score__badge");
//            System.out.println(hotelReviews);
//            System.out.println("hotelReviews count = " + hotelReviews.size());
            System.out.println("Hotel Count: " + hotels.size());
            List<Integer> falseHotels = new ArrayList<>();
            for (int i = 0; i < hotels.size(); i++) {
                if (hotels.get(i).getElementsByClass("sr-hotel__name").text().length() == 0) {
                    falseHotels.add(i);
                    continue;
                }
                System.out.print("Name: " + hotels.get(i).getElementsByClass("sr-hotel__name").text() + ", ");
                System.out.print("Stars: " + hotels.get(i).getElementsByClass("bui-rating__item").size() + ", ");
                System.out.print("Reviews Score: " + hotels.get(i).getElementsByClass("bui-review-score__badge").text() + ", ");
                System.out.print("Price: " + hotels.get(i).getElementsByClass("bui-price-display__value").text() + "\n");
            }
            for (Integer falseHotel : falseHotels) {
                hotels.remove(falseHotel.intValue());
            }
            System.out.println("Hotel Count: " + hotels.size());
//            doc2 = Jsoup.connect(searchStringBooking4).get(); //            System.out.println(doc);
//            Elements hotelNames2 = doc2.select("#hotellist_inner > div .sr_item_content > div > div > div h3 a span");
//            for (int i = 0; i < hotelNames.size() - 1; i++) {
//                if (!hotelNames.get(i).getElementsByTag("span").text().equalsIgnoreCase("Opens in new window")) {
//                    System.out.println("Name: " + hotelNames.get(i).text());// + ",Stars: " + hotelStars.get(i).children().size());
//                }
//            }
//            System.out.println("\n" + doc2.title());
//            System.out.println("hotelNames count = " + hotelNames2.size());
//            for (Element name : hotelNames2) {
//                if (!name.text().equalsIgnoreCase("Opens in new window")) {
//                    System.out.printf("%s, ", name.text());
//                }
//            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
