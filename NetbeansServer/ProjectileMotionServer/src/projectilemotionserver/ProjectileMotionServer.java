package projectilemotionserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class ProjectileMotionServer {
    private static final int PORT = 8888;
    private static int clientCounter = 0;
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        
        try{
            serverSocket = new ServerSocket(PORT);
        }catch (IOException e){
            System.err.println("Could not listen to port " + PORT);
            System.exit(1);
        }
        
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("IP Address:- " + inetAddress.getHostAddress());
        System.out.println("Host Name:- " + inetAddress.getHostName());
        System.out.println("Server created on " + serverSocket.getInetAddress().getLocalHost().getHostAddress());
        System.out.println("Listening for connections...\n");
        while(true){
            Socket clientSocket = null;
            try{
                clientSocket = serverSocket.accept();
                
            }catch(IOException e){
                System.err.println("Accept failed");
                System.exit(1);
            }
            System.out.println("Client " + ++clientCounter + " wants to talk");
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
  
            double angle = inputStream.readDouble();
            double force = inputStream.readDouble();           
            System.out.println("Received some angle " + angle + " and force " + force);
            
            ProjectileMotion projectileMotion = new ProjectileMotion();
            outputStream.writeInt(projectileMotion.getCoordinates(angle, force).size());

            for(int i = 0; i < projectileMotion.getCoordinates().size(); i++){
                outputStream.writeDouble(projectileMotion.getDistance(i));
                outputStream.writeDouble(projectileMotion.getHeight(i));
                outputStream.writeDouble(projectileMotion.getTime(i));
            }
            
            inputStream.close();
            outputStream.close();
            clientSocket.close();
            System.out.println("Client " + clientCounter + " satisfied\n");
        }
    }
}

            /*//ak by sme chceli posielat celu triedu, tak by musela 
            //implementovat serializable
            //a musela by to byt uplne ta istat trieda v tom istom balicku
            //s tymi s tym istym serializableUID, nestaci ak su tie triedy totozne
            //v androide a v jave -> riesilo by sa to cez shared folder v oboch projektoch
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());            
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream.writeInt(projectileMotion.getCoordinates(angle, force).size());
            outputStream.flush();
            outputStream.writeObject(projectileMotion);
            outputStream.flush();*/
