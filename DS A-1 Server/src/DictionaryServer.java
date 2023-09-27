/* This file is made by Tianchen FAN, 1166401
 * */
import java.io.*;

public class DictionaryServer {
    private Dictionary dictionary;
    private String dicFilePath;
    private Object lock = new Object();
    private Cookies cookies;

    public DictionaryServer(String filePath) {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.dicFilePath = filePath;
            this.dictionary = (Dictionary) in.readObject();
            in.close();
            fileIn.close();
            this.cookies = new Cookies();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
    }

    public DictionaryServer(String filePath, int time) {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.dicFilePath = filePath;
            this.dictionary = (Dictionary) in.readObject();
            in.close();
            fileIn.close();
            this.cookies = new Cookies(time);
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
    }

    public String query(String word) {
        if (this.dictionary.containsWord(word)) {
            return "success\n" + this.dictionary.checkMeanings(word) + "\n";
        } else {
            return "notFound\nSorry, the word \"" + word + "\"is not found in this dictionary\n";
        }
    }

    public String add(String word, String meanings, String cookiesString) {
        if (cookies.checkIfCookieNotxpired(cookiesString)) {
            if (this.dictionary.containsWord(word)) {
                return "duplicate\n" + "The word \"" + word + "\" is already in this dictionary with its meanings: " + this.dictionary.checkMeanings(word) + "\n";
            } else {
                synchronized (lock) {
                    this.dictionary.addNewWord(word, meanings);
                    save();
                    return "success\nThe word \"" + word + "\" has been added to the dictionary.\n";
                }
            }
        } else {
            return "passwordExpired\nSorry, your login expired. Please login again\n";
        }

    }

    public synchronized String remove(String word, String cookiesString) {
        if (cookies.checkIfCookieNotxpired(cookiesString)) {
            if (this.dictionary.containsWord(word)) {
                synchronized (lock) {
                    dictionary.remove(word);
                    save();
                    return "success\nWord \"" + word + "\" has been removed from the dictionary.\n";
                }
            } else {
                return "notFound\nThere is no \"" + word + "\"in this dictionary.\n";
            }
        } else {
            return "passwordExpired\nSorry, your login expired. Please login again\n";
        }
    }

    public synchronized String update(String word, String meanings, String cookiesString) {
        if (cookies.checkIfCookieNotxpired(cookiesString)) {
            if (this.dictionary.containsWord(word)) {
                synchronized (lock) {
                    dictionary.replace(word,meanings);
                    save();
                    return "success\nWord \"" + word + "\" has been updated.\n";
                }
            } else {
                return "notFound\nThere is no \"" + word + "\" in this dictionary.\n";
            }
        } else {
            return "passwordExpired\nSorry, your login expired. Please login again\n";
        }

    }

    public String login(String password) {
        if (this.dictionary.checkPassword(password)) {
            return "success\n"+ cookies.generateCookies() + "\n";
        } else {
            return "notFound\n\n";
        }
    }

    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.dicFilePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.dictionary);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
