import com.jcraft.jsch.*;
import java.io.*;
import java.util.Vector;
public class SSH {
    public static void main(String args[]) {
        String user = "apiiit-rkv";
        String password = "rgukt2016";
        String host = "10.29.17.196";
        int port = 22;
        String remoteFile = "/home/apiiit-rkv/.bash_history";
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Crating SFTP Channel.");
            session.setPortForwardingL(1234, "10.20.3.2", 3128);
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");
            InputStream out = null;
            //out = sftpChannel.get(remoteFile);
            Vector s=sftpChannel.ls(sftpChannel.getHome());            
            int i;
            System.out.println("Listing files");
            for(i=0;i<s.size();i++){
                System.out.println(s.get(i));
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(out));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (JSchException | SftpException | IOException e) {
            System.err.println("Error occured:"+e.getMessage());
        }
    }
}