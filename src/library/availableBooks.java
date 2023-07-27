/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library;

import java.util.Date;

/**
 *
 * @author ASUS
 */


public class availableBooks {

    private final String title;
    private final String author;
    private final String genre;
    private final String image;
    private final Date date;

    public availableBooks(String title, String author, String genre, String image, Date date) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.image = image;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }
}

