import java.net.*;
import java.io.*;

public class Client {

    Socket socket;

    BufferedReader br;
    PrintWriter out;
    

    public Client(){

        try {
            
            System.out.println("Sending req to server");
            socket = new Socket("192.168.171.96",7777);
            System.out.println("connection established");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void startReading(){

        Runnable r1 = ()->{
            System.out.println("reader started");

            try{
            while(true){
                String msg = br.readLine();
                if(msg.equals("exit")){
                    System.out.println("server terminated the chat");
                    break;
                }
                System.out.println("server: " + msg);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        };
        new Thread(r1).start();
    }

    public void startWriting(){

        Runnable r2 = ()->{
            
            while(true){

                try {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();

    }
    
    public static void main(String[] args) {
        System.out.println("this is client");
        new Client();
    }
}
