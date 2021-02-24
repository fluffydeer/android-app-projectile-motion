package projectilemotionserver;

import java.io.Serializable;


public class Coordinate{
    private double distance;
    private double height;
    private double time;

    public Coordinate(double distance, double height, double time) {
        this.distance = distance;
        this.height = height;
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    public double getHeight() {
        return height;
    }

    public double getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "distance=" + distance +
                ", height=" + height +
                ", time=" + time +
                "}\n";
    }
}
