/* This file is made by Tianchen FAN, 1166401
 * */
import java.io.*;
import java.net.Socket;

public class ThreadServer implements Runnable{
    private final Socket clientSocket;
    private DictionaryServer dictionaryServer;
    public ThreadServer(Socket socket, DictionaryServer dictionary){
        this.clientSocket = socket;
        this.dictionaryServer = dictionary;
    }


    @Override
    public void run() {

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String requestMsg = in.readLine();
                //System.out.println(requestMsg);
                if (requestMsg.equals("query")) {
                    String word = in.readLine();
                    out.write(this.dictionaryServer.query(word));
                } else if (requestMsg.equals("add")) {
                    String word = in.readLine();
                    String meanings = in.readLine();
                    String cookiesString = in.readLine();
                    out.write(this.dictionaryServer.add(word,meanings,cookiesString));
                } else if (requestMsg.equals("remove")) {
                    String word = in.readLine();
                    String cookiesString = in.readLine();
                    out.write(this.dictionaryServer.remove(word,cookiesString));
                } else if (requestMsg.equals("update")) {
                    String word = in.readLine();
                    String meanings = in.readLine();
                    String cookiesString = in.readLine();
                    out.write(this.dictionaryServer.update(word,meanings, cookiesString));
                } else if (requestMsg.equals("login")) {
                    String password = in.readLine();
                    out.write(this.dictionaryServer.login(password));
                }
                out.flush();

                clientSocket.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }



    }
}
