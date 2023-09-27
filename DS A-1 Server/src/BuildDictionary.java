/* This file is made by Tianchen FAN, 1166401
 * */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BuildDictionary {

    public void build() {
        Dictionary myDictionary = new Dictionary();
        myDictionary.addNewWord("a","The meaning of a.");
        myDictionary.addNewWord("b","The meaning of b 1; The meaning of b 2.");
        myDictionary.addNewWord("c","The meaning of c.");

        myDictionary.addPassword("password");

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("dictionaryFile.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(myDictionary);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
