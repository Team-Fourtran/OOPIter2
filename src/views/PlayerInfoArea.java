package views;

import javax.swing.*;
import java.awt.*;

public class PlayerInfoArea {
    private String playerName = "";
    private JLabel playerLabel;
    private JLabel foodLabel;
    private JLabel energyLabel;
    private JLabel oreLabel;
    private JTextField playerTextField;
    private JTextField energyTextField;
    private JTextField oreTextField;
    private JTextField foodTextField;
    private JPanel playerPane;
    private JPanel foodPane;
    private JPanel energyPane;
    private JPanel orePane;

    public PlayerInfoArea(Container cont){
        playerLabel = new JLabel("Player");
        energyLabel = new JLabel("Energy");
        oreLabel = new JLabel("Ore");
        foodLabel = new JLabel("Food");

        playerTextField = new JTextField();
        energyTextField = new JTextField();
        oreTextField = new JTextField();
        foodTextField = new JTextField();

        playerTextField.setEditable(false);
        foodTextField.setEditable(false);
        oreTextField.setEditable(false);
        energyTextField.setEditable(false);

        playerLabel.setLabelFor(playerTextField);
        energyLabel.setLabelFor(energyTextField);
        oreLabel.setLabelFor(oreTextField);
        foodLabel.setLabelFor(foodTextField);

        playerPane = new JPanel(new GridLayout(1, 2, 0, 0));
        playerPane.add(playerLabel);
        playerPane.add(playerTextField);
        foodPane = new JPanel(new GridLayout(1, 2, 0, 0));
        foodPane.add(foodLabel);
        foodPane.add(foodTextField);
        energyPane = new JPanel(new GridLayout(1, 2, 0, 0));
        energyPane.add(energyLabel);
        energyPane.add(energyTextField);
        orePane = new JPanel(new GridLayout(1, 2,0 ,0 ));
        orePane.add(oreLabel);
        orePane.add(oreTextField);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        cont.add(playerPane, c);
        c.gridx = 1;
        c.gridy = 3;
        cont.add(foodPane, c);
        c.gridx = 2;
        c.gridy = 3;
        cont.add(energyPane, c);
        c.gridx = 3;
        c.gridy = 3;
        cont.add(orePane, c);
    }
    public void update(String playerName){
        this.playerName = playerName;
    }
}
