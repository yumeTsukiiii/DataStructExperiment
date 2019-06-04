package com.yumetsuki.second.model;

public final class Path {

    private City startCity;

    private City endCity;

    private double distance;

    private double cost;

    private Path previousPath;

    private Path nextPath;

    public City getStartCity() {
        return startCity;
    }

    public void setStartCity(City startCity) {
        this.startCity = startCity;
    }

    public City getEndCity() {
        return endCity;
    }

    public void setEndCity(City endCity) {
        this.endCity = endCity;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Path getPreviousPath() {
        return previousPath;
    }

    public void setPreviousPath(Path previousPath) {
        this.previousPath = previousPath;
    }

    public Path getNextPath() {
        return nextPath;
    }

    public void setNextPath(Path nextPath) {
        this.nextPath = nextPath;
    }

    @Override
    public String toString() {
        Path current = this;
        StringBuilder resultMessage = new StringBuilder();
        while (current != null){
            cost += current.getCost();
            distance += current.getDistance();
            resultMessage.append(current.getStartCity().getName());
            resultMessage.append(" to ");
            if (current.getNextPath() == null){
                resultMessage.append(current.getEndCity().getName());
            }
            current = current.getNextPath();
        }
        return resultMessage.toString();
    }
}
