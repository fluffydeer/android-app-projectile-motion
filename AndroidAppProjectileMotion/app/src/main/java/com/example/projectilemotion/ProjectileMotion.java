package com.example.projectilemotion;

import android.os.AsyncTask;
import android.util.Log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ProjectileMotion implements Serializable{
    private static final String TAG = "ProjectileMotion";
    private final double G = 9.8;
    private final int STEP = 100;
    private Boolean communicationDone = false;
    private String SERVER_IP;
    private int SERVER_PORT = 8888;

    private ArrayList<Coordinate> coordinates = new ArrayList();
    private double angle, force;

    public ProjectileMotion() {
    }

    public Boolean getCommunicationDone() {
        return communicationDone;
    }

    public void setCommunicationDone(Boolean communicationDone) {
        this.communicationDone = communicationDone;
    }


    public void setUpVariables(double angle, double force){
        this.angle=angle;
        this.force=force;
        coordinates.clear();
    }

    public double getAngle() {
        return angle;
    }

    public double getForce() {
        return force;
    }

    public ArrayList<Coordinate> getCoordinates(double angle, double force) {
        setUpVariables(angle, force);
        angle = angle * (Math.PI / 180.0);
        double x, y, endTime = (2 * force * Math.sin(angle)) / G;

        for(double time = 0; time < endTime; time += endTime/ STEP){
            x = force*time*Math.cos(angle);
            y = force*time*Math.sin(angle) -0.5 * G *Math.pow(time, 2);
            coordinates.add(new Coordinate(x, y, time));
        }
        //to add the last row of y = 0.0 when the step doesnt count correctly
        Coordinate lastCoord = coordinates.get(coordinates.size()-1);
        boolean b1 = (String.format("%.2f", lastCoord.getDistance())).equals(String.format("%.2f", force*endTime*Math.cos(angle)));
        boolean b2 = (String.format("%.2f", lastCoord.getHeight())).equals(String.format("%.2f", 0.0));
        boolean b3 = (String.format("%.2f", lastCoord.getTime())).equals(String.format("%.2f", endTime));
        if(!(b1 && b2 && b3)){
            coordinates.add(new Coordinate(force*endTime*Math.cos(angle), 0.0, endTime));
        }
        return coordinates;
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

    public void getCoordinatesFromServer(double angle, double force, String serverIPAddress){
        setUpVariables(angle, force);
        this.SERVER_IP = serverIPAddress;
        new RetrieveDataServer().execute("");
    }

    //todo encapsulate
    public class RetrieveDataServer extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                //CMD-->Right Click-->Run as Administrator-->Yes-->ipconfig-->Enter
                //a odtial si zapiseme IPv4
                //String SERVER_IP = "192.168.0.120";   //aj tato adresa funguje
                //toto fungovalo
                //String SERVER_IP = "169.254.174.223";   //adresa na ktorej vznikol server
                //InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                //Socket clientSocket = new Socket(serverAddr, SERVER_PORT);

                Log.i(TAG, "Creating connection " + SERVER_IP + "/" + SERVER_PORT);
                Socket clientSocket = null;
                try{
                    InetSocketAddress inetAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
                    clientSocket = new Socket();
                    clientSocket.connect(inetAddress, 5000);
                }catch (SocketTimeoutException e){
                    Log.e(TAG, "Socket timeout exception ");
                    Log.e(TAG, "Check your server IP address");
                    communicationDone = true;
                    clientSocket.close();   //close na null object
                    return null;
                }
                Log.i(TAG, "Connection created ");

                DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                outputStream.writeDouble(angle);
                outputStream.writeDouble(force);
                Log.i(TAG, "Angle and force sent");

                ArrayList<Coordinate> serverCoordinates = new ArrayList<>();
                int size = inputStream.readInt();
                for(int i = 0; i < size; i++){
                    double distance = inputStream.readDouble();
                    double height = inputStream.readDouble();
                    double time = inputStream.readDouble();
                    serverCoordinates.add(new Coordinate(distance, height, time));
                }
                coordinates.clear();
                coordinates = serverCoordinates;

                Log.i(TAG, "Data received");

                inputStream.close();
                outputStream.close();
                clientSocket.close();

                Log.i(TAG, "Connection closed");
                communicationDone = true;

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}