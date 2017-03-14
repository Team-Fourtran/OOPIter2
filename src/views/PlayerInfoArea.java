package views;

import javax.swing.*;
import java.awt.*;

public class PlayerInfoArea {
    public void generatePlayerInfoArea(Container cont){
        JLabel playerLabel = new JLabel("Player");
        JTextField playerTextField = new JTextField();
        playerLabel.setLabelFor(playerTextField);
        JPanel playerPane = new JPanel(new GridLayout(1, 2, 0, 0));
        playerPane.add(playerLabel);
        playerPane.add(playerTextField);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        cont.add(playerPane, c);

        JLabel energyLabel = new JLabel("Energy");
        JLabel oreLabel = new JLabel("Ore");
        JLabel foodLabel = new JLabel("Food");
        JTextField energyTextField = new JTextField();
        JTextField oreTextField = new JTextField();
        JTextField foodTextField = new JTextField();

        foodTextField.setEditable(false);
        oreTextField.setEditable(false);
        energyTextField.setEditable(false);

        energyLabel.setLabelFor(energyTextField);
        oreLabel.setLabelFor(oreTextField);
        foodLabel.setLabelFor(foodTextField);

        JPanel foodPane = new JPanel(new GridLayout(1, 2, 0, 0));
        foodPane.add(foodLabel);
        foodPane.add(foodTextField);
        JPanel energyPane = new JPanel(new GridLayout(1, 2, 0, 0));
        energyPane.add(energyLabel);
        energyPane.add(energyTextField);
        JPanel orePane = new JPanel(new GridLayout(1, 2,0 ,0 ));
        orePane.add(oreLabel);
        orePane.add(oreTextField);

        c.gridx = 0;
        c.gridy = 3;
        cont.add(foodPane, c);
        c.gridx = 1;
        c.gridy = 3;
        cont.add(energyPane, c);
        c.gridx = 2;
        c.gridy = 3;
        cont.add(orePane, c);
    }
}
