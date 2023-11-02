package OOP.ec22637.MP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class GUIVisitor implements Visitor {



    // Semaphore is used to communicate between application and event dispatched threads
    enum Semaphore {
        NORTH_WAITING, EAST_WAITING, SOUTH_WAITING, WEST_WAITING, CHOICE_WAITING,
        HEAD_NORTH, HEAD_EAST, HEAD_SOUTH, HEAD_WEST, CONFIRM_CHOICE
    }

    JFrame db = new JFrame();
    private JPanel mainPanel;
    private JButton northButton;
    private JButton eastButton;
    private JButton southButton;
    private JButton westButton;
    private JTextArea tellText;
    private JTextField textField;
    private JButton confirmChoiceButton;

    // Application thread is the code in main
    // Not allowed to use swing stuff in the application thread as it needs to be in the event dispatched thread
    Semaphore northSemaphore = Semaphore.NORTH_WAITING;
    Semaphore eastSemaphore = Semaphore.EAST_WAITING;
    Semaphore southSemaphore = Semaphore.SOUTH_WAITING;
    Semaphore westSemaphore = Semaphore.WEST_WAITING;
    Semaphore choiceSemaphore = Semaphore.CHOICE_WAITING;

    private PrintStream out;
    private Scanner in;
    private int purse;
    private List items = new ArrayList(1000);;

    private int next;

    GUIVisitor(PrintStream ps, InputStream is) {
        northButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                northSemaphore = Semaphore.HEAD_NORTH;
            }
        });

        eastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eastSemaphore = Semaphore.HEAD_EAST;
            }
        });

        southButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                southSemaphore = Semaphore.HEAD_SOUTH;
            }
        });

        westButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                westSemaphore = Semaphore.HEAD_WEST;
            }

            // can use final variables within addActionListener method
        });

        confirmChoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == confirmChoiceButton) {
                    tell(textField.getText());
                    choiceSemaphore = Semaphore.CONFIRM_CHOICE;
                }
            }
        });

        db.setTitle("GUIVisitor by ec22637");
        db.setContentPane(mainPanel);
        db.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        db.pack();
        db.setSize(1000, 400);

        // See stuff in the event dispatched thread
        db.setVisible(true);

        out = ps;
        in = new Scanner(is);
        purse = 0;
        next = 0;
    }

    private static final char[] yOrN = { 'y', 'n'};

    public void tell(String m) {
        tellText.append(m+"\n");
    }

    public char getChoice(String d, char[] a) {
        tell(d);

        // Want to wait for accept button press.

        northSemaphore = Semaphore.NORTH_WAITING;
        eastSemaphore = Semaphore.EAST_WAITING;
        southSemaphore = Semaphore.SOUTH_WAITING;
        westSemaphore = Semaphore.WEST_WAITING;
        choiceSemaphore = Semaphore.CHOICE_WAITING;

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    northButton.setEnabled(true);
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        while (northSemaphore == Semaphore.NORTH_WAITING &&
                eastSemaphore == Semaphore.EAST_WAITING &&
                southSemaphore == Semaphore.SOUTH_WAITING &&
                westSemaphore == Semaphore.WEST_WAITING &&
                choiceSemaphore == Semaphore.CHOICE_WAITING) {
            System.out.print("");
        }

        if (northSemaphore == Semaphore.HEAD_NORTH) {
            return 'N';
        }
        else if (eastSemaphore == Semaphore.HEAD_EAST) {
            return 'E';
        }
        else if (southSemaphore == Semaphore.HEAD_SOUTH) {
            return 'S';
        }
        else if (westSemaphore == Semaphore.HEAD_WEST) {
            return 'W';
        }

        if (choiceSemaphore == Semaphore.CONFIRM_CHOICE) {
            char choice = textField.getText().charAt(0);
            return choice;
        }

        if (!in.hasNextLine()) {
            tell("'No line' error");
            return '?';
        }
        String t = in.nextLine();
        if (t.length() > 0)
            return t.charAt(0);
        else {
            if (a.length > 0) {
                tell("Returning "+a[0]);
                return a[0];
            } else {
                tell("Returning '?'");
                return '?';
            }
        }
    }
    public boolean giveItem(Item x) {
        out.println("You are being offered: " + x.name);
        if (next >= items.size()) {
            out.println("But you have no space and must decline.");
            return false;
        }

        if (getChoice("Do you accept (y/n)?", yOrN) == 'y') {
            items.set(next, x);
            next++;
            return true;
        } else return false;
    }

    public boolean hasIdenticalItem(Item x) {
        for (int i=0; i<next;i++)
            if (x == items.get(i))
                return true;
        return false;
    }

    public boolean hasEqualItem(Item x) {
        for (int i=0; i<next;i++)
            if (x.equals(items.get(i)))
                return true;
        return false;
    }

    public void giveGold(int n) {
        out.println("You are given "+n+" gold pieces.");
        purse += n;
        out.println("You now have "+purse+" pieces of gold.");
    }

    public int takeGold(int n) {

        if (n<0) {
            out.println("A scammer tried to put you in debt to the tune off "+(-n)+"pieces of gold,");
            out.println("but you didn't fall for it and returned the 'loan'.");
            return 0;
        }

        int t = 0;
        if (n > purse) t = purse;
        else t = n;

        out.println(t+" pieces of gold are taken from you.");
        purse -= t;
        out.println("You now have "+purse+" pieces of gold.");

        return t;
    }
}
