package by.uni.lab5_navmenu;

import java.util.List;

public class Event {
    private String title;
    private String description;
    private String date;
    private String time;
    private String mainImageUrl;
    private List<String> photos;

    public Event(String title, String description, String date, String time, List<String> photos) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.mainImageUrl = mainImageUrl;

        this.photos = photos;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}