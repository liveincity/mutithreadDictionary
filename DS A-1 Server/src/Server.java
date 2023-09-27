/* This file is made by Tianchen FAN, 1166401
 * */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.exit;

public class Server {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Too less argument");
            return;
        }

        if (args.length > 2) {
            System.out.println("Too many argument");
            return;
        }

        String portString = args[0];

        int portInt = Integer.parseInt(portString);

        ServerSocket server = null;

        server = new ServerSocket(portInt);
        server.setReuseAddress(true);

        Path path = Paths.get("dictionaryFile.ser");
        if (!path.toFile().isFile()){
            BuildDictionary buildDictionary = new BuildDictionary();
            buildDictionary.build();
            System.out.println("Dictionary file not found, build a new dictionary file.");
        }

        DictionaryServer dictionaryServer;
        if (args.length == 2 ) {
            String expireTime = args[1];
            int expireTimeInt = Integer.parseInt(expireTime);
            dictionaryServer = new DictionaryServer("dictionaryFile.ser", expireTimeInt);
        } else {
            dictionaryServer = new DictionaryServer("dictionaryFile.ser");
        }

        while (true) {
            Socket client = server.accept();

            ThreadServer threadServer = new ThreadServer(client, dictionaryServer);
            new Thread(threadServer).start();
        }
    }
}
