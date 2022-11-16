package client;

import server.ServerUDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientUDP {
    public static void main(String[] args) throws Exception {
        Scanner clavier= new Scanner(System.in);
        DatagramSocket dgSocket= new DatagramSocket();
        InetAddress ip=InetAddress.getLocalHost();
        byte[] by=null;
        boolean finGame=false;
        String msgRecuDuServer;
        while (!finGame){
            System.out.println("Saisir un nombre entre 0 et 100 au claviver");
            String str= clavier.nextLine();
            by=str.getBytes();
            DatagramPacket dgPacket= new DatagramPacket(by,str.length(),ip,1234);
            dgSocket.send(dgPacket);

            by=new byte[1000];
            dgPacket = new DatagramPacket(by, 0, by.length);
            dgSocket.receive(dgPacket);
            msgRecuDuServer=new String(by,0,dgPacket.getLength());
            if (msgRecuDuServer.equals("Bravo!")==true){
                finGame=true;
                System.out.println("Fin de Jeu le nombre trouvé est: "+Integer.parseInt(str));
            }

        }
        dgSocket.close();
    }
}
