import java.io.*;
import java.rmi.*;

public class FServer {
    public static void main(String argv[]) {
        try {
            //Names the server object as FileServer
            FileInterface fileInt = new FileImpl("FServer");
            Naming.rebind("FServer", fileInt);
        } catch (Exception e) {
            System.out.println("FServer :" + e.getMessage());
            e.printStackTrace();
        }
    }
}