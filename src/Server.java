import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import javax.imageio.ImageIO;
public class Server {
    private static ServerSocket server = null;
    private static Socket client = null;
    private static DataInputStream in = null;
    private static ObjectOutputStream out=null;
    private static String line;
    private static int mouse_sensitivity =3;
    private static int lastXpos = 0;
    private static int lastYpos = 0;
    private static int lastScrollY;
    private static int counter=0;
    private static Robot robot;
    private static final int SERVER_PORT = 1567;
    private static HashMap<String, Integer> actions = new HashMap<>();
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static final int width = tk.getScreenSize().width, height = tk.getScreenSize().height;

    public static void main(String[] args) throws IOException, InterruptedException, Exception {
        try {
            robot = new Robot();
            robot.setAutoDelay(10);
            robot.setAutoWaitForIdle(true);
            init();           
            System.out.println("OS:"+System.getProperty("os.name"));
            //String a="1.2";
            //System.out.println(Float.parseFloat(a));
            server = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Error in opening Socket");
            System.exit(-1);
        } catch (AWTException e) {
            System.out.println("Error in creating robot instance");
            System.exit(-1);
        }
        while (true) {
            client = server.accept();
            in = new DataInputStream(client.getInputStream());
            out=new ObjectOutputStream(client.getOutputStream());
            System.out.println("Client connected");
            //String l=in.readLine();
           // String[] creds=l.split(" ");
            //if(!(creds[0].equals("admin") && creds[1].equals("pass"))){
             //   client.close();
               // System.out.println("Invalid username/password:"+l);
           // }else{
                //Message<String> str=new Message<>();
                //str.setPayload("You are connected");
                //out.writeObject(str);
                //out.flush();
                while (true) {
                    try {
                        line = in.readUTF();
                        System.out.println("Command:" + line);                    
                        processCommand(line, client);
                    } catch (IOException e) {
                        System.out.println("Client Disconnected");
                        System.exit(-1);
                    }
                }
            //}           
        }
    }

    private static void init() {
        actions.put("A", KeyEvent.VK_A);
        actions.put("B", KeyEvent.VK_B);
        actions.put("C", KeyEvent.VK_C);
        actions.put("D", KeyEvent.VK_D);
        actions.put("E", KeyEvent.VK_E);
        actions.put("F", KeyEvent.VK_F);
        actions.put("G", KeyEvent.VK_G);
        actions.put("H", KeyEvent.VK_H);
        actions.put("I", KeyEvent.VK_I);
        actions.put("J", KeyEvent.VK_J);
        actions.put("K", KeyEvent.VK_K);
        actions.put("L", KeyEvent.VK_L);
        actions.put("M", KeyEvent.VK_M);
        actions.put("N", KeyEvent.VK_N);
        actions.put("O", KeyEvent.VK_O);
        actions.put("P", KeyEvent.VK_P);
        actions.put("Q", KeyEvent.VK_Q);
        actions.put("R", KeyEvent.VK_R);
        actions.put("S", KeyEvent.VK_S);
        actions.put("T", KeyEvent.VK_T);
        actions.put("U", KeyEvent.VK_U);
        actions.put("V", KeyEvent.VK_V);
        actions.put("W", KeyEvent.VK_W);
        actions.put("X", KeyEvent.VK_X);
        actions.put("Y", KeyEvent.VK_Y);
        actions.put("Z", KeyEvent.VK_Z);
        //numbers
        actions.put("0", KeyEvent.VK_0);
        actions.put("1", KeyEvent.VK_1);
        actions.put("2", KeyEvent.VK_2);
        actions.put("3", KeyEvent.VK_3);
        actions.put("4", KeyEvent.VK_4);
        actions.put("5", KeyEvent.VK_5);
        actions.put("6", KeyEvent.VK_6);
        actions.put("7", KeyEvent.VK_7);
        actions.put("8", KeyEvent.VK_8);
        actions.put("9", KeyEvent.VK_9);
        //functional keys
        actions.put("F1", KeyEvent.VK_F1);
        actions.put("F2", KeyEvent.VK_F2);
        actions.put("F3", KeyEvent.VK_F3);
        actions.put("F4", KeyEvent.VK_F4);
        actions.put("F5", KeyEvent.VK_F5);
        actions.put("F6", KeyEvent.VK_F6);
        actions.put("F7", KeyEvent.VK_F7);
        actions.put("F8", KeyEvent.VK_F8);
        actions.put("F9", KeyEvent.VK_F9);
        actions.put("F10", KeyEvent.VK_F10);
        actions.put("F11", KeyEvent.VK_F11);
        actions.put("F12", KeyEvent.VK_F12);
        //special keys
        actions.put("SPACE", KeyEvent.VK_SPACE);
        actions.put("ESC", KeyEvent.VK_ESCAPE);
        actions.put("SHIFT", KeyEvent.VK_SHIFT);
        actions.put("TAB", KeyEvent.VK_TAB);
        actions.put("ALT", KeyEvent.VK_ALT);
        actions.put("CTRL", KeyEvent.VK_CONTROL);
        actions.put("CAPS", KeyEvent.VK_CAPS_LOCK);
        actions.put("NUMLOCK", KeyEvent.VK_NUM_LOCK);
        //actions.put("FN",KeyEvent.VK_);
        actions.put("ENTER", KeyEvent.VK_ENTER);
        actions.put("BACKSPACE", KeyEvent.VK_BACK_SPACE);
        actions.put("INSERT", KeyEvent.VK_INSERT);
        actions.put("DEL", KeyEvent.VK_DELETE);
        actions.put("PRINT", KeyEvent.VK_PRINTSCREEN);
        actions.put("HOME", KeyEvent.VK_HOME);
        actions.put("PGUP", KeyEvent.VK_PAGE_UP);
        actions.put("PGDWN", KeyEvent.VK_PAGE_DOWN);
        actions.put("PLUS", KeyEvent.VK_PLUS);
        actions.put("MINUS", KeyEvent.VK_MINUS);
        actions.put("END", KeyEvent.VK_END);
        actions.put("WIN", KeyEvent.VK_WINDOWS);
        //arrow keys
        actions.put("RIGHT", KeyEvent.VK_RIGHT);
        actions.put("LEFT", KeyEvent.VK_LEFT);
        actions.put("UP", KeyEvent.VK_UP);
        actions.put("DOWN", KeyEvent.VK_DOWN);
    }

    private static void sendResponse(String filename, String tag) throws IOException {
        System.out.println("Sending file..."+filename);
        File file=new File(filename);
        out.writeObject(new Message<>(file,tag));
        System.out.println("File sent : "+filename);
        out.flush();
    }
    private static void sendResponse(String msg) throws IOException {        
        out.writeObject(new Message<>(msg));
        System.out.println("Response sent : "+msg);
        out.flush();
    }
    private static void shell(String[] args) throws IOException {
        String cmd="";
        int i;
        for(i=1;i<args.length;i++){
            cmd+=args[i]+" ";
        }
        Process p=Runtime.getRuntime().exec(cmd);
        System.out.println("Command Executed:"+cmd);
    }


    public static void keyBoardPress(int key, int key2) {
        try {
            robot.keyPress(key);
            robot.keyPress(key2);
            robot.keyRelease(key2);
            robot.keyRelease(key);
        } catch (Exception e) {
        }
    }

    public static void keyBoardPress(int key) {
        try {
            robot.keyPress(key);
            robot.keyRelease(key);
        } catch (Exception e) {
        }
    }

    public static void type(char character) {
        switch (character) {
            case 'a':
                keyBoardPress(KeyEvent.VK_A);
                break;
            case 'b':
                keyBoardPress(KeyEvent.VK_B);
                break;
            case 'c':
                keyBoardPress(KeyEvent.VK_C);
                break;
            case 'd':
                keyBoardPress(KeyEvent.VK_D);
                break;
            case 'e':
                keyBoardPress(KeyEvent.VK_E);
                break;
            case 'f':
                keyBoardPress(KeyEvent.VK_F);
                break;
            case 'g':
                keyBoardPress(KeyEvent.VK_G);
                break;
            case 'h':
                keyBoardPress(KeyEvent.VK_H);
                break;
            case 'i':
                keyBoardPress(KeyEvent.VK_I);
                break;
            case 'j':
                keyBoardPress(KeyEvent.VK_J);
                break;
            case 'k':
                keyBoardPress(KeyEvent.VK_K);
                break;
            case 'l':
                keyBoardPress(KeyEvent.VK_L);
                break;
            case 'm':
                keyBoardPress(KeyEvent.VK_M);
                break;
            case 'n':
                keyBoardPress(KeyEvent.VK_N);
                break;
            case 'o':
                keyBoardPress(KeyEvent.VK_O);
                break;
            case 'p':
                keyBoardPress(KeyEvent.VK_P);
                break;
            case 'q':
                keyBoardPress(KeyEvent.VK_Q);
                break;
            case 'r':
                keyBoardPress(KeyEvent.VK_R);
                break;
            case 's':
                keyBoardPress(KeyEvent.VK_S);
                break;
            case 't':
                keyBoardPress(KeyEvent.VK_T);
                break;
            case 'u':
                keyBoardPress(KeyEvent.VK_U);
                break;
            case 'v':
                keyBoardPress(KeyEvent.VK_V);
                break;
            case 'w':
                keyBoardPress(KeyEvent.VK_W);
                break;
            case 'x':
                keyBoardPress(KeyEvent.VK_X);
                break;
            case 'y':
                keyBoardPress(KeyEvent.VK_Y);
                break;
            case 'z':
                keyBoardPress(KeyEvent.VK_Z);
                break;
            case 'A':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_A);
                break;
            case 'B':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_B);
                break;
            case 'C':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_C);
                break;
            case 'D':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_D);
                break;
            case 'E':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_E);
                break;
            case 'F':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_F);
                break;
            case 'G':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_G);
                break;
            case 'H':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_H);
                break;
            case 'I':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_I);
                break;
            case 'J':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_J);
                break;
            case 'K':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_K);
                break;
            case 'L':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_L);
                break;
            case 'M':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_M);
                break;
            case 'N':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_N);
                break;
            case 'O':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_O);
                break;
            case 'P':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_P);
                break;
            case 'Q':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_Q);
                break;
            case 'R':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_R);
                break;
            case 'S':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_S);
                break;
            case 'T':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_T);
                break;
            case 'U':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_U);
                break;
            case 'V':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_V);
                break;
            case 'W':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_W);
                break;
            case 'X':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_X);
                break;
            case 'Y':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_Y);
                break;
            case 'Z':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_Z);
                break;
            case '`':
                keyBoardPress(KeyEvent.VK_BACK_QUOTE);
                break;
            case '0':
                keyBoardPress(KeyEvent.VK_0);
                break;
            case '1':
                keyBoardPress(KeyEvent.VK_1);
                break;
            case '2':
                keyBoardPress(KeyEvent.VK_2);
                break;
            case '3':
                keyBoardPress(KeyEvent.VK_3);
                break;
            case '4':
                keyBoardPress(KeyEvent.VK_4);
                break;
            case '5':
                keyBoardPress(KeyEvent.VK_5);
                break;
            case '6':
                keyBoardPress(KeyEvent.VK_6);
                break;
            case '7':
                keyBoardPress(KeyEvent.VK_7);
                break;
            case '8':
                keyBoardPress(KeyEvent.VK_8);
                break;
            case '9':
                keyBoardPress(KeyEvent.VK_9);
                break;
            case '-':
                keyBoardPress(KeyEvent.VK_MINUS);
                break;
            case '=':
                keyBoardPress(KeyEvent.VK_EQUALS);
                break;
            case '~':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_QUOTE);
                break;
            case '!':
                keyBoardPress(KeyEvent.VK_EXCLAMATION_MARK);
                break;
            case '@':
                keyBoardPress(KeyEvent.VK_AT);
                break;
            case '#':
                keyBoardPress(KeyEvent.VK_NUMBER_SIGN);
                break;
            case '$':
                keyBoardPress(KeyEvent.VK_DOLLAR);
                break;
            case '%':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_5);
                break;
            case '^':
                keyBoardPress(KeyEvent.VK_CIRCUMFLEX);
                break;
            case '&':
                keyBoardPress(KeyEvent.VK_AMPERSAND);
                break;
            case '*':
                keyBoardPress(KeyEvent.VK_ASTERISK);
                break;
            case '(':
                keyBoardPress(KeyEvent.VK_LEFT_PARENTHESIS);
                break;
            case ')':
                keyBoardPress(KeyEvent.VK_RIGHT_PARENTHESIS);
                break;
            case '_':
                keyBoardPress(KeyEvent.VK_UNDERSCORE);
                break;
            case '+':
                keyBoardPress(KeyEvent.VK_PLUS);
                break;
            case '\t':
                keyBoardPress(KeyEvent.VK_TAB);
                break;
            case '\n':
                keyBoardPress(KeyEvent.VK_ENTER);
                break;
            case '[':
                keyBoardPress(KeyEvent.VK_OPEN_BRACKET);
                break;
            case ']':
                keyBoardPress(KeyEvent.VK_CLOSE_BRACKET);
                break;
            case '\\':
                keyBoardPress(KeyEvent.VK_BACK_SLASH);
                break;
            case '{':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_OPEN_BRACKET);
                break;
            case '}':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_CLOSE_BRACKET);
                break;
            case '|':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_SLASH);
                break;
            case ';':
                keyBoardPress(KeyEvent.VK_SEMICOLON);
                break;
            case ':':
                keyBoardPress(KeyEvent.VK_COLON);
                break;
            case '\'':
                keyBoardPress(KeyEvent.VK_QUOTE);
                break;
            case '"':
                keyBoardPress(KeyEvent.VK_QUOTEDBL);
                break;
            case ',':
                keyBoardPress(KeyEvent.VK_COMMA);
                break;
            case '<':
                keyBoardPress(KeyEvent.VK_LESS);
                break;
            case '.':
                keyBoardPress(KeyEvent.VK_PERIOD);
                break;
            case '>':
                keyBoardPress(KeyEvent.VK_GREATER);
                break;
            case '/':
                keyBoardPress(KeyEvent.VK_SLASH);
                break;
            case '?':
                keyBoardPress(KeyEvent.VK_SHIFT, KeyEvent.VK_SLASH);
                break;
            case ' ':
                keyBoardPress(KeyEvent.VK_SPACE);
                break;
            default:
                System.out.println("Character " + character);
                break;
        }
    }

    private static void processCommand(String line, Socket client) throws InterruptedException, FileNotFoundException, IOException, Exception {
        String args[] = line.split(" ");
        String cmd = args[0];
        if (args.length > 1) {
            System.out.println("No. of Arguments:" + args.length);
        }
        doAction(cmd, args, client);
    }

    private static void type(int i) {
        robot.delay(5);
        robot.keyPress(i);
        robot.keyRelease(i);
    }

    private static void rightClick() {
        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);
    }   
    private static void type(String s) {
        byte[] bytes = s.getBytes();
        for (byte b : bytes) {
            int code = b;
            // keycode only handles [A-Z] (which is ASCII decimal [65-90])
            if (code > 96 && code < 123) {
                code = code - 32;
            }
            robot.delay(100);
            robot.keyPress(code);
            robot.keyRelease(code);
        }
    }

    private static int getAction(String code) {
        int key = (int) actions.get(code);
        return key;
    }

    private static void doAction(String cmd, String[] args, Socket client) throws InterruptedException, FileNotFoundException, IOException, Exception {
        int k=0;
        String filename=null;
        switch (cmd) {
            case "ALERT":
                //new Document();
                typeMsg(args);
                break;
            case "NEXT":
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                break;
            case "UP":
                robot.keyPress(KeyEvent.VK_UP);
                robot.keyRelease(KeyEvent.VK_UP);
                break;
            case "DOWN":
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                break;
            case "ENTER":
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                break;
            case "SPACE":
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                break;                
            case "RIGHT_CLICK":
                rightClick();
                break;
            case "SHELL":
                shell(args);
                break;
            case "PREVIOUS":
                robot.keyPress(KeyEvent.VK_LEFT);
                robot.keyRelease(KeyEvent.VK_LEFT);
                break;
            case "FULL_SCREEN":
                robot.keyPress(KeyEvent.VK_F5);
                robot.keyRelease(KeyEvent.VK_F5);
                break;
            case "KEYCOMBO":
                keyCombo(args);
                break;
            case "SEND_FILE":
                //sendFile();
            case "CLICK":
                click();
                break;
            case "MOUSE":
                int x=Math.round(Float.parseFloat(args[1]));
                int y=Math.round(Float.parseFloat(args[2]));
                moveMouse(x, y);
                break;
            case "MPLAY":
                type(' ');
                break;
            case "MPAUSE":
                type(' ');
                break;
            case "MFORWARD":
                String[] a={"","CTRL","RIGHT"};
                keyCombo(a);
                break;
            case "MBACKWARD":
                String[] b={"","CTRL","LEFT"};
                keyCombo(b);
                break;
            case "MNEXT":
                robot.keyPress(KeyEvent.VK_N);
                robot.keyRelease(KeyEvent.VK_N);
                break;
            case "MPREVIOUS":
                robot.keyPress(KeyEvent.VK_P);
                robot.keyRelease(KeyEvent.VK_P);
                break;
            case "SCROLL":
                scrollMouseWheel(args);
                break;
            case "TYPE":
                typeMsg(args);
                break;
            case "SCREEN":
                filename = getScreenshot();
                System.out.println("Screen shot generated");
                filename=createThumbnail(filename, 1000, 750);
                System.out.println("Thumb generated");
                File f=new File(filename);
                if (!f.canRead()) {
                    f.setReadable(true);
                }             
                k=1;
                //System.out.println("Can read:"+new File("preview.jpg").isFile());                
                break;
            case "OFF_SCREEN":
                robot.keyPress(KeyEvent.VK_ESCAPE);
                robot.keyRelease(KeyEvent.VK_ESCAPE);
                break;
            default:
                //sendResponse("Inavlid command");
                System.out.println("Invalid command");
        }
        if(k==1){
            sendResponse(filename,"PREVIEW");
            k=0;
            filename=null;
        }else{
            sendResponse("OK");
        }            
    }

    private static String getScreenshot() throws IOException {
        String preview = "preview"+(counter++);
        BufferedImage img = robot.createScreenCapture(new Rectangle(0, 0, width, height));
        String filename = preview + ".jpg";
        File tmp=new File(filename);
        ImageIO.write(img, "jpg",tmp);            
        return filename;
    }

    private static String getDateTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("PST"));
        return df.format(new Date());
    }

    private static void keyCombo(String[] args) {
        int i;
        for (i = 1; i < args.length; i++) {
            robot.keyPress(getAction(args[i]));
        }
        for (i = args.length-1; i>=1; i--) {
            robot.keyRelease(getAction(args[i]));
        }
    }

    public static void moveMouse(int x, int y) {
        //get the current position of the mouse cursor
        int current_x_local = MouseInfo.getPointerInfo().getLocation().x;
        int current_y_local = MouseInfo.getPointerInfo().getLocation().y;

        //move the mouse relative to the current position
        robot.mouseMove(current_x_local + x * mouse_sensitivity, current_y_local + y * mouse_sensitivity);
    }

    private static String createThumbnail(String in, int thumbWidth, int thumbHeight) throws Exception {
        System.out.println("File name:"+in);
        BufferedImage bi = getThumb(ImageIO.read(Files.newInputStream(Paths.get(in))), thumbWidth, thumbHeight);
        String ext = in.substring(in.lastIndexOf(".") + 1);
        String out = in.replaceFirst(".([a-z]+)$", "_thumb." + ext);
        System.err.println(in + " --> " + out);
        ImageIO.write(bi, ext, Files.newOutputStream(Paths.get(out)));
        //System.out.println("Access:"+new File(in).canRead());
        return out;
    }

    public static BufferedImage getThumb(BufferedImage in, int w, int h) throws Exception {
        // scale w, h to keep aspect constant
        double outputAspect = 1.0 * w / h;
        double inputAspect = 1.0 * in.getWidth() / in.getHeight();
        if (outputAspect < inputAspect) {
            // width is limiting factor; adjust height to keep aspect
            h = (int) (w / inputAspect);
        } else {
            // height is limiting factor; adjust width to keep aspect
            w = (int) (h * inputAspect);
        }
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(in, 0, 0, w, h, null);
        g2.dispose();
        return bi;
    }

    public static void scrollMouseWheel(String args[]) {
        int notches = Integer.parseInt(args[0]);
        robot.mouseWheel(notches);
    }

    private static void click() {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private static void typeMsg(String[] args) {
        int i;
        for (i = 1; i < args.length; i++) {
            if (i != args.length - 1) {
                type(args[i] + " ");
            } else {
                type(args[i]);
            }
        }
    }
}