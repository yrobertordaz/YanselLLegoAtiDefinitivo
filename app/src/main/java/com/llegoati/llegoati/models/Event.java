package com.llegoati.llegoati.models;

/**
 * Author: Yansel
 * Created by: ModelGenerator on 7/15/2017
 */
public class Event {
    private String Id;    // 35c32c8a_8927_e
    private String Title;    // La implantación
    private String Description;    // Aunque existen
    private FrontDescription FrontDescription;
    private String Clasification;    // Artesania
    private boolean Aleatory;    // false
    private boolean AlwysFront;    // true
    private boolean Active;    // true
    private String DatePublish;    // 2017_04_22T02:2
    private String EventStart;    // 2017_05_05T07:1
    private String EventEnd;    // 2017_05_05T07:1
    private MainImage MainImage;
    private ImagesItem[] Images;
    private String DatePublishString;    // 22/04/2017

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public com.llegoati.llegoati.models.FrontDescription getFrontDescription() {
        return FrontDescription;
    }

    public void setFrontDescription(com.llegoati.llegoati.models.FrontDescription frontDescription) {
        FrontDescription = frontDescription;
    }

    public String getClasification() {
        return Clasification;
    }

    public void setClasification(String clasification) {
        Clasification = clasification;
    }

    public boolean isAleatory() {
        return Aleatory;
    }

    public void setAleatory(boolean aleatory) {
        Aleatory = aleatory;
    }

    public boolean isAlwysFront() {
        return AlwysFront;
    }

    public void setAlwysFront(boolean alwysFront) {
        AlwysFront = alwysFront;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public String getDatePublish() {
        return DatePublish;
    }

    public void setDatePublish(String datePublish) {
        DatePublish = datePublish;
    }

    public String getEventStart() {
        return EventStart;
    }

    public void setEventStart(String eventStart) {
        EventStart = eventStart;
    }

    public String getEventEnd() {
        return EventEnd;
    }

    public void setEventEnd(String eventEnd) {
        EventEnd = eventEnd;
    }

    public com.llegoati.llegoati.models.MainImage getMainImage() {
        return MainImage;
    }

    public void setMainImage(com.llegoati.llegoati.models.MainImage mainImage) {
        MainImage = mainImage;
    }

    public ImagesItem[] getImages() {
        return Images;
    }

    public void setImages(ImagesItem[] images) {
        Images = images;
    }

    public String getDatePublishString() {
        return DatePublishString;
    }

    public void setDatePublishString(String datePublishString) {
        DatePublishString = datePublishString;
    }
}