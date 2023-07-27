/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library;

import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class returnBook {

    private final String title;
    private final String author;
    private final String genre;
    private final Date date;
    private final String image;

    public returnBook(String title, String author, String genre, String image, Date date) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.image = image;
        this.date = date;
    }

//    returnBook(String string, String string0, String string1, java.sql.Date date) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//    
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
