package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerUDP {
    public static void main(String[] args) {
        boolean finJeuChezServer=false;
        int nbSecret= (int) (Math.random()*100);

        System.out.println(nbSecret);
        String msgRenseignement;
        byte[] donne;
        //Socket de service ainsi que paquet pour echanger de donnee entre client
        DatagramSocket dgSocketSer=null;
        DatagramPacket dgPaquet;
         try {
             dgSocketSer=new DatagramSocket(1234);
             System.out.println("On lance le jeu");

             while (!finJeuChezServer){
                 donne=new byte[1000];
                 dgPaquet=new DatagramPacket(donne, donne.length);
                 dgSocketSer.receive(dgPaquet);

                 String ch_nbr= new String(donne,0,dgPaquet.getLength());
                 int nbr_Essai= Integer.parseInt(ch_nbr);

                 if (nbr_Essai>nbSecret){
                     msgRenseignement="Merci de donner un nombre plus petit";
                     System.out.println("On attend une nouvelle tentative");
                 } else if (nbr_Essai<nbSecret) {
                     msgRenseignement="Merci de donner un nombre plus grand";
                     System.out.println("On attend une nouvelle tentative");
                 }else {
                     msgRenseignement="Bravo!";
                     finJeuChezServer=true;
                     System.out.println("Le jeu est terminé ! A la prochaine");
                 }

                 int portClient= dgPaquet.getPort();
                 InetAddress addressIpClient=dgPaquet.getAddress();
                 donne=new byte[msgRenseignement.length()];
                 msgRenseignement.getBytes(0,msgRenseignement.length(),donne,0);

                 dgPaquet=new DatagramPacket(donne,msgRenseignement.length(),addressIpClient,portClient);
                 dgSocketSer.send(dgPaquet);
             }
             dgSocketSer.close();
         }catch (Exception e){
                e.printStackTrace();
         }
    }
}