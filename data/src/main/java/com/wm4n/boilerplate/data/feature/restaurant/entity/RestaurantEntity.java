package com.wm4n.boilerplate.data.feature.restaurant.entity;

public class RestaurantEntity {

  private final String id;
  private String name;
  private String city;
  private String category;
  private String photo;
  private int price;
  private int numRatings;
  private double avgRating;

  public RestaurantEntity(String id) {
    this.id = id;
  }

  public RestaurantEntity(String id, String name, String city, String category, String photo, int price, int numRatings, double avgRating) {
    this.id = id;
    this.name = name;
    this.city = city;
    this.category = category;
    this.photo = photo;
    this.price = price;
    this.numRatings = numRatings;
    this.avgRating = avgRating;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getNumRatings() {
    return numRatings;
  }

  public void setNumRatings(int numRatings) {
    this.numRatings = numRatings;
  }

  public double getAvgRating() {
    return avgRating;
  }

  public void setAvgRating(double avgRating) {
    this.avgRating = avgRating;
  }
}
