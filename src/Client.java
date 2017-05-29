
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private Socket socket = null;
    private Scanner console = null;
    private DataOutputStream streamOut = null;
    private ObjectInputStream streamIn = null;
    private boolean isLogged = false;

    public Client(String serverName, int serverPort) throws IOException, ClassNotFoundException {
        try {
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + socket);
            console = new Scanner(System.in);
            streamOut = new DataOutputStream(socket.getOutputStream());
            streamIn = new ObjectInputStream(socket.getInputStream());
            String line = "", cmd = null;
            while (true) {
                Object recv = ((Message<?>) (streamIn.readObject())).getPayload();
                if (recv instanceof String) {
                    System.out.println("String received:" + recv);
                } else if (recv instanceof File) {
                    File tmp = (File) recv;
                    FileInputStream fis = new FileInputStream(tmp);
                    FileOutputStream fos = new FileOutputStream("CLI_"+tmp.getName());
                    int bread = 0;
                    byte[] buf = new byte[666666];
                    while ((bread = fis.read(buf)) != -1) {
                        fos.write(buf, 0, bread);
                    }
                    fis.close();
                    fos.close();
                    System.out.println("File received:" + tmp.getName());
                }
                System.out.print("Enter ur command >> ");
                cmd = console.nextLine();
                streamOut.writeUTF(cmd);
                //System.out.println("Command sent:"+cmd);
            }
        } catch (UnknownHostException uhe) {
            System.out.println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Unexpected exception: " + ioe.getMessage());
        } finally {
            socket.close();
        }
    }

    public void stop() {
        try {
            if (console != null) {
                console.close();
            }
            if (streamOut != null) {
                streamOut.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ioe) {
            System.out.println("Error closing ...");
        }
    }

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        Client client = null;
        Scanner s = new Scanner(System.in);
        //System.oSCREEut.print("Enter ip:");
        //String host=s.next();
        //System.out.print("Enter port:");
        //int port=s.nextInt();
        client = new Client("10.31.4.98", 1567);
    }
}
