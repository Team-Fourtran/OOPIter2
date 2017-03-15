package views;

import javax.swing.*;
import java.awt.*;

public class ControllerInfoArea {
    private JLabel modeLabel;
    private JLabel typeLabel;
    private JLabel typeInstanceLabel;
    private JLabel commandsLabel;
    private JTextField modeTextField;
    private JTextField typeTextField;
    private JTextField typeInstanceTextField;
    private JTextField commandsTextField;
    private JPanel modePane;
    private JPanel typePane;
    private JPanel typeInstancePane;
    private JPanel commandsPane;

    public ControllerInfoArea(Container cont){
        modeLabel = new JLabel("Mode");
        typeLabel = new JLabel("Type");
        typeInstanceLabel = new JLabel("Type Instance");
        commandsLabel = new JLabel("Commands");

        modeTextField = new JTextField();
        typeTextField = new JTextField();
        typeInstanceTextField = new JTextField();
        commandsTextField = new JTextField();
        modeTextField.setEditable(false);
        typeTextField.setEditable(false);
        typeInstanceTextField.setEditable(false);
        commandsTextField.setEditable(false);

        modeLabel.setLabelFor(modeTextField);
        typeLabel.setLabelFor(typeTextField);
        typeInstanceLabel.setLabelFor(typeInstanceTextField);
        commandsLabel.setLabelFor(commandsTextField);

        modePane = new JPanel(new GridLayout(1, 2, 0, 0));
        modePane.add(modeLabel);
        modePane.add(modeTextField);
        typePane = new JPanel(new GridLayout(1, 2, 0, 0));
        typePane.add(typeLabel);
        typePane.add(typeTextField);
        typeInstancePane = new JPanel(new GridLayout(1, 2,0 ,0 ));
        typeInstancePane.add(typeInstanceLabel);
        typeInstancePane.add(typeInstanceTextField);
        commandsPane = new JPanel(new GridLayout(1, 2,0 ,0 ));
        commandsPane.add(commandsLabel);
        commandsPane.add(commandsTextField);

        GridBagConstraints b = new GridBagConstraints();
        b.fill = GridBagConstraints.HORIZONTAL;
        b.weightx = 1;
        b.gridx = 0;
        b.gridy = 2;
        cont.add(modePane, b);
        b.gridx = 1;
        b.gridy = 2;
        cont.add(typePane, b);
        b.gridx = 2;
        b.gridy = 2;
        cont.add(typeInstancePane, b);
        b.gridx = 3;
        b.gridy = 2;
        cont.add(commandsPane, b);
    }
}
