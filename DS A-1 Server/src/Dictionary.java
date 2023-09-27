/* This file is made by Tianchen FAN, 1166401
 * */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary implements Serializable {
    private ArrayList<String> password = new ArrayList<String>();
    private HashMap dictionary = new HashMap<String,String>();

    public void addPassword(String newPassword) {
        this.password.add(newPassword);
    }

    public boolean checkPassword(String password) {return this.password.contains(password);}

    public String checkMeanings(String word) {
        return (String) this.dictionary.get(word);
    }

    public void addNewWord(String word, String meanings) {
        this.dictionary.put(word, meanings);
    }

    public boolean containsWord(String word) {
        return this.dictionary.containsKey(word);
    }

    public void remove(String word) {
        this.dictionary.remove(word);
    }

    public void replace(String word, String meanings) {
        this.dictionary.replace(word,meanings);
    }
}
