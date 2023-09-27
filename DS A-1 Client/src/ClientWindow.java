/* This file is made by Tianchen FAN, 1166401
* */
import javax.swing.*;
import java.awt.event.ActionEvent;

public class ClientWindow {
    private static ClientController clientController;
    private JPanel panel;
    private JTextField queryWord;
    private JTextArea queryMeanings;
    private JButton queryButton;
    private JTextField addWord;
    private JTextArea addMeanings;
    private JButton addButton;
    private JTextField removeWord;
    private JTextArea removeResult;
    private JButton removeButton;
    private JTextField updateWord;
    private JTextArea updateMeanings;
    private JButton updateButton;
    private JPasswordField loginPassword;
    private JButton loginButton;

    private JTextArea loginResult;
    private String IP;
    private int port;


    public ClientWindow() {
        queryButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String word = queryWord.getText();
                String outputMsg = "";
                if (word.isEmpty()) {
                    outputMsg = "The word input box cannot be empty";
                } else {
                    outputMsg = clientController.query(word);
                }
                queryMeanings.setText(outputMsg);
            }
        });

        addButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String word = addWord.getText();
                String meanings = addMeanings.getText().replaceAll("\\r?\\n","");
                String outputMsg = "";
                if (word.isEmpty()) {
                    outputMsg += "The word input box cannot be empty\n";
                }
                if (meanings.isEmpty()) {
                    outputMsg += "The meaning input box cannot be empty\n";
                }
                if (outputMsg.isEmpty()) {
                    outputMsg += clientController.add(word, meanings);
                }
                addMeanings.setText(outputMsg);
            }
        });

        removeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String word = removeWord.getText();
                String outputMsg = "";
                if (word.isEmpty()) {
                    outputMsg = "The word input box cannot be empty";
                } else {
                    outputMsg = clientController.remove(word);
                }
                removeResult.setText(outputMsg);
            }
        });

        updateButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String word = updateWord.getText();
                String meanings = updateMeanings.getText().replaceAll("\\r?\\n","");
                String outputMsg = "";
                if (word.isEmpty()) {
                    outputMsg += "The word input box cannot be empty\n";
                }
                if (meanings.isEmpty()) {
                    outputMsg += "The meaning input box cannot be empty\n";
                }
                if (outputMsg.isEmpty()) {
                    outputMsg += clientController.update(word, meanings);
                }
                updateMeanings.setText(outputMsg);
            }
        });

        loginButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password;
                password = loginPassword.getText();
                String outputMsg = "";
                if (password.isEmpty()) {
                    outputMsg += "To login, the Password cannot be empty.";
                } else {
                    outputMsg += clientController.Login(password);
                }
                loginResult.setText(outputMsg);
            }
        });
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            String IP = args[0];
            int port = Integer.parseInt(args[1]);
            clientController = new ClientController(IP, port);
            JFrame frame = new JFrame("ClientWindow");
            frame.setContentPane(new ClientWindow().panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } else {
            System.out.println("You should entre a IP address and a port.");
        }
    }
}
