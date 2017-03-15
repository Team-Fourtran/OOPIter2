package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private JPanel pane;

    private JButton endTurnButton;
    private TurnSwitchNotifier turnSwitchNotifier;
    public PlayerInfoArea(Container cont, TurnSwitchNotifier turnSwitchNotifier){

        this.turnSwitchNotifier = turnSwitchNotifier;

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

        endTurnButton = new JButton("End Turn");
        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnSwitchNotifier.notifyOfTurnSwitch();
            }
        });
        pane = new JPanel(new GridLayout(5, 1, 0, 0));
        pane.add(playerPane);
        pane.add(foodPane);
        pane.add(energyPane);
        pane.add(orePane);
        pane.add(endTurnButton);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 1;
        c.weightx = 0.5;
        c.fill = 2;
        cont.add(pane, c);
//        c.gridx = 0;
//        c.gridy = 3;
//        c.gridx = 4;
//        c.gridy = 0;
//        c.weightx = 1;
//        c.fill = GridBagConstraints.HORIZONTAL;
//        cont.add(playerPane, c);
////        c.gridx = 1;
//        c.gridy = 1;
//        cont.add(foodPane, c);
////        c.gridx = 2;
//        c.gridy = 2;
//        cont.add(energyPane, c);
////        c.gridx = 3;
//        c.gridy = 3;
//        cont.add(orePane, c);
    }
    public TurnSwitchNotifier getTurnSwitchNotifier(){
        return this.turnSwitchNotifier;
    }
    public void update(String playerName){
        this.playerName = playerName;
    }
}
