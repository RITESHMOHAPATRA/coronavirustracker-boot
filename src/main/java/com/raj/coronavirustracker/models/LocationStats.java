package com.raj.coronavirustracker.models;

public class LocationStats {
    private String state;
    private String country;
    private int latestTotalCase;
    private int diffFromPrevCase;

    public int getDiffFromPrevCase() {
        return diffFromPrevCase;
    }

    public void setDiffFromPrevCase(int diffFromPrevCase) {
        this.diffFromPrevCase = diffFromPrevCase;
    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCase() {
        return latestTotalCase;
    }

    public void setLatestTotalCase(int latestTotalCase) {
        this.latestTotalCase = latestTotalCase;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCase=" + latestTotalCase +
                '}';
    }
    public LocationStats(String state,String country,int totalCase){
        this.country = country;
        this.state = state;
        this.latestTotalCase = totalCase;
    }
}
