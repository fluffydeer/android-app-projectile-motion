package projectilemotionserver;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ProjectileMotion{
    private final double G = 9.8;
    private final int STEP = 100;

    private ArrayList<Coordinate> coordinates = new ArrayList();
    private double angle, force;

    public ProjectileMotion() {
    }

    public ArrayList<Coordinate> getCoordinates(){
        return coordinates;
    }

    public double getAngle() {
        return angle;
    }

    public double getForce() {
        return force;
    }

    public ArrayList<Coordinate> getCoordinates(double angle, double force) {
        this.angle = angle;
        this.force = force;
        coordinates.clear();
        angle = angle * (Math.PI / 180.0);
        double x, y, endTime = (2 * force * Math.sin(angle)) / G;

        for(double time = 0; time < endTime; time += endTime/ STEP){
            x = force*time*Math.cos(angle);
            y = force*time*Math.sin(angle) -0.5 * G *Math.pow(time, 2);
            coordinates.add(new Coordinate(x, y, time));
        }
        Coordinate lastCoord = coordinates.get(coordinates.size()-1);
        boolean b1 = (String.format("%.2f", lastCoord.getDistance())).equals(String.format("%.2f", force*endTime*Math.cos(angle)));
        boolean b2 = (String.format("%.2f", lastCoord.getHeight())).equals(String.format("%.2f", 0.0));
        boolean b3 = (String.format("%.2f", lastCoord.getTime())).equals(String.format("%.2f", endTime));
        if(!(b1 && b2 && b3)){
            System.out.println("TRUEEE");
            coordinates.add(new Coordinate(force*endTime*Math.cos(angle), 0.0, endTime));
        }
        return coordinates;
    }

    public Double getHeight(int i){
        return coordinates.get(i).getHeight();
    }
    
    public Double getDistance(int i){
        return coordinates.get(i).getDistance();
    }
        
    public Double getTime(int i){
        return coordinates.get(i).getTime();
    }
    
    
    public ArrayList<Double> getHeights(){
        ArrayList<Double> list = new ArrayList<>();
        for(int i = 0; i < coordinates.size(); i++){
            list.add(coordinates.get(i).getHeight());
        }
        return list;
    }

    public ArrayList<Double> getDistances(){
        ArrayList<Double> list = new ArrayList<>();
        for(int i = 0; i < coordinates.size(); i++){
            list.add(coordinates.get(i).getDistance());
        }
        return list;
    }

    public ArrayList<Double> getTimes(){
        ArrayList<Double> list = new ArrayList<>();
        for(int i = 0; i < coordinates.size(); i++){
            list.add(coordinates.get(i).getTime());
        }
        return list;
    }
}