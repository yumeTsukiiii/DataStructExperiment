package com.yumetsuki.second.model;

import java.util.Objects;

public final class Service {

    private String start;

    private String end;

    private double cost;

    private double distance;

    public Service(String start, String end, double cost, double distance) {
        this.start = start;
        this.end = end;
        this.cost = cost;
        this.distance = distance;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;
        Service service = (Service) o;
        return Double.compare(service.getCost(), getCost()) == 0 &&
                Double.compare(service.getDistance(), getDistance()) == 0 &&
                Objects.equals(getStart(), service.getStart()) &&
                Objects.equals(getEnd(), service.getEnd());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getStart(), getEnd(), getCost(), getDistance());
    }
}
