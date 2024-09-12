package org.example.entity;

public class SpaceType {

    private Integer spaceType_id;
    private String name;
    private String politic;

    public SpaceType(Integer spaceType_id, String name, String politic) {
        this.spaceType_id = spaceType_id;
        this.name = name;
        this.politic = politic;
    }

    public SpaceType() {
    }

    public Integer getSpaceType_id() {
        return spaceType_id;
    }

    public void setSpaceType_id(Integer spaceType_id) {
        this.spaceType_id = spaceType_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPolitic() {
        return politic;
    }

    public void setPolitic(String politic) {
        this.politic = politic;
    }
}
