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
public interface ISearchEngine {

    public void executeSearch(String searchString, String checkInDate, String checkOutDate);
}
