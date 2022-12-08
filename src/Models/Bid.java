package Models;

import java.io.Serializable;
import java.util.Date;

public class Bid implements Serializable {
    private int id, price, duration;
    private String title, description, image_path, creation_date ,seller;

    public Bid(int id, int price, int duration, String title, String description, String image_path, String creation_date, String seller) {
        this.id = id;
        this.price = price;
        this.duration = duration;
        this.title = title;
        this.description = description;
        this.image_path = image_path;
        this.creation_date = creation_date;
        this.seller = seller;
    }

    public Bid(int price, int duration, String title, String description, String image_path, String creation_date, String seller) {
        this.price = price;
        this.duration = duration;
        this.title = title;
        this.description = description;
        this.image_path = image_path;
        this.creation_date = creation_date;
        this.seller = seller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
