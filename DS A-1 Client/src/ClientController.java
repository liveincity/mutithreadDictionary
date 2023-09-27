/* This file is made by Tianchen FAN, 1166401
 * */
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ClientController {
    private String IP;
    private int port;
    private String cookie = "";

    public ClientController(String ip, int port) {
        this.IP = IP;
        this.port = port;
    }


    private ArrayList<String> communication(ArrayList<String> sendMsg) {
        Socket socket = null;
        ArrayList<String> receivedMsg = new ArrayList<String>();
        try
        {
            // Create a stream socket bounded to the address and the port
            socket = new Socket(this.IP, this.port);

            // Get the input/output streams for reading/writing data from/to the socket
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // combine the send msgs as a string
            String sendMsgString = "";
            for (String msg : sendMsg) {
                sendMsgString = sendMsgString + msg + "\n";
            }

            out.write(sendMsgString);
            out.flush();


            // receive the code and message body
            receivedMsg.add(in.readLine());
            receivedMsg.add(in.readLine());
            socket.close();


        }
        catch (UnknownHostException e)
        {
            receivedMsg.add("UnknownHostException\n");
            receivedMsg.add("Oops, something goes wrong. Please provide them with the following information: " + e.getMessage() + '\n');
        }
        catch (IOException e)
        {
            receivedMsg.add("IOException\n");
            receivedMsg.add("Oops, something goes wrong. Please provide them with the following information: " + e.getMessage() + '\n');
        }

        return receivedMsg;
    }

    public String query(String word) {
        ArrayList<String> sendMsg = new ArrayList<String>();
        sendMsg.add("query");
        sendMsg.add(word);

        ArrayList<String> receivedMsg = communication(sendMsg);

        return receivedMsg.get(1);
    }

    public String add(String word, String meanings) {
        if (!this.cookie.isEmpty()){
            ArrayList<String> sendMsg = new ArrayList<String>();
            sendMsg.add("add");
            sendMsg.add(word);
            sendMsg.add(meanings);
            sendMsg.add(this.cookie);

            ArrayList<String> receivedMsg = communication(sendMsg);

            return receivedMsg.get(1);
        } else {
            return "To use add function, please log in.";
        }
    }

    public String remove(String word) {
        if (!this.cookie.isEmpty()){
            ArrayList<String> sendMsg = new ArrayList<String>();
            sendMsg.add("remove");
            sendMsg.add(word);
            sendMsg.add(this.cookie);

            ArrayList<String> receivedMsg = communication(sendMsg);
            return receivedMsg.get(1);
        } else {
            return "To use remove function, please log in.";
        }

    }

    public String update(String word, String meanings) {
        if (!this.cookie.isEmpty()){
            ArrayList<String> sendMsg = new ArrayList<String>();
            sendMsg.add("update");
            sendMsg.add(word);
            sendMsg.add(meanings);
            sendMsg.add(this.cookie);

            ArrayList<String> receivedMsg = communication(sendMsg);

            return receivedMsg.get(1);
        } else {
            return "To use remove function, please log in.";
        }


    }

    public String Login(String password) {
        ArrayList<String> sendMsg = new ArrayList<String>();
        sendMsg.add("login");
        sendMsg.add(password);

        ArrayList<String> receivedMsg = communication(sendMsg);

        String code = receivedMsg.get(0);
        if (code.equals("success")) {
            this.cookie = receivedMsg.get(1);
            return "Login success. ";
        } else {
            return "Wrong password. Please try again. ";
        }
    }
}
