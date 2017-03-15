package views;

import javax.swing.*;

public class NumWorkersDialog {
    private static String[] counts = new String[10];
    private String numWorkers;
    private JFrame frame;

    public void createDialog(){
        for(int i = 0; i < 10; i++){
            counts[i] = Integer.toString(i);
        }
        frame = new JFrame("Worker Count Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        numWorkers = (String) JOptionPane.showInputDialog(frame,
                "Select number of workers",
                "Worker count selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                counts,
                counts[0]);
    }

    public int returnWorkerCount(){
        return Integer.parseInt(numWorkers);
    }
    public void closeDialog(){
        frame.setVisible(false);
        frame.dispose();
    }
}
