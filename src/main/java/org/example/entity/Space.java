package org.example.entity;

public class Space {

    private Integer space_id;
    private String name;
    private String location;
    private Integer capacity;
    private Integer price;
    private boolean available;

    public Space(Integer space_id, String name, String location, Integer capacity, Integer price, boolean available) {
        this.space_id = space_id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.price = price;
        this.available = available;
    }

    public Space() {
    }


    public Integer getSpace_id() {
        return space_id;
    }

    public void setSpace_id(Integer space_id) {
        this.space_id = space_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
