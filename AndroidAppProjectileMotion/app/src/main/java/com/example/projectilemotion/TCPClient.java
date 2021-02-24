package com.example.projectilemotion;

import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

public class TCPClient {

    public TCPClient(){
    }

  /*  public void talkToServer(){
        new RetrieveDataServer().execute("");
        System.out.println("skoncili sme ");
    }


    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public class RetrieveDataServer extends AsyncTask<String, Integer, String> {
        private int SERVER_PORT = 8888;

        @Override
        protected String doInBackground(String... strings) {
            try{

                //InetAddress serverAddr = InetAddress.getLocalHost();
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                //String SERVER_IP = getLocalIpAddress();   //192.168.232.2
                //String SERVER_IP = "localhost";
                //String SERVER_IP = "0.0.0.0";
                //String SERVER_IP = "192.168.0.1"; //toto bola gateway

                //CMD-->Right Click-->Run as Administrator-->Yes-->ipconfig-->Enter
                //a odtial si zapiseme IPv4
                String SERVER_IP = "192.168.0.120";

                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                System.out.println(serverAddr);
                //Socket clientSocket = new Socket("localhost", SERVER_PORT);
                //mobil sa nevie pripojit na lokalnu adresu
                Socket clientSocket = new Socket(serverAddr, SERVER_PORT);
                System.out.println("SERVER 2");


                DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

                outputStream.writeDouble(100.99);
                outputStream.flush();
                outputStream.writeDouble(66.66);
                outputStream.flush();


                System.out.println("zapisane3");


                List<Coordinate> coordinates = new ArrayList<>();

                System.out.println("prislo: "+ inputStream.readInt());
                System.out.println("prisiel objekt");

                inputStream.close();
                outputStream.close();
                clientSocket.close();


            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }*/
}
