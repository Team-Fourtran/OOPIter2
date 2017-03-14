package views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by TK on 3/14/17.
 */
public class ControllerInfoArea {
    public void generateControllerInfoArea(Container cont){
        GridBagConstraints b = new GridBagConstraints();
        b.gridx = 0;
        b.gridy = 2;
        b.fill = GridBagConstraints.HORIZONTAL;
        JLabel modeLabel = new JLabel("Mode");
        JLabel typeLabel = new JLabel("Type");
        JLabel typeInstanceLabel = new JLabel("Type Instance");
        JLabel commandsLabel = new JLabel("Commands");

        JTextField modeTextField = new JTextField();
        JTextField typeTextField = new JTextField();
        JTextField typeInstanceTextField = new JTextField();
        JTextField commandsTextField = new JTextField();

        modeLabel.setLabelFor(modeTextField);
        typeLabel.setLabelFor(typeTextField);
        typeInstanceLabel.setLabelFor(typeInstanceTextField);
        commandsLabel.setLabelFor(commandsTextField);

        JPanel modePane = new JPanel(new GridLayout(1, 2, 0, 0));
        modePane.add(modeLabel);
        modePane.add(modeTextField);
        JPanel typePane = new JPanel(new GridLayout(1, 2, 0, 0));
        typePane.add(typeLabel);
        typePane.add(typeTextField);
        JPanel typeInstancePane = new JPanel(new GridLayout(1, 2,0 ,0 ));
        typeInstancePane.add(typeInstanceLabel);
        typeInstancePane.add(typeInstanceTextField);
        JPanel commandsPane = new JPanel(new GridLayout(1, 2,0 ,0 ));
        commandsPane.add(commandsLabel);
        commandsPane.add(commandsTextField);
        b.gridx = 0;
        b.gridy = 2;
        cont.add(modePane, b);
        b.gridx = 1;
        b.gridy = 2;
        cont.add(typePane, b);
        b.gridx = 2;
        b.gridy = 2;
        cont.add(typeInstancePane, b);
        b.weightx = 1;
        b.gridx = 3;
        b.gridy = 2;
        cont.add(commandsPane, b);
    }
}
