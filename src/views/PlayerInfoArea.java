package views;

import models.playerAsset.Assets.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerInfoArea {
    private Player player;
    private JLabel playerLabel;
    private JLabel wheatLabel;
    private JLabel foodLabel;
    private JLabel scienceLabel;
    private JLabel energyLabel;
    private JLabel metalLabel;
    private JLabel oreLabel;
    private JTextField playerTextField;
    private JTextField scienceTextField;
    private JTextField energyTextField;
    private JTextField metalTextField;
    private JTextField oreTextField;
    private JTextField wheatTextField;
    private JTextField foodTextField;
    private JPanel playerPane;
    private JPanel wheatPane;
    private JPanel foodPane;
    private JPanel sciencePane;
    private JPanel energyPane;
    private JPanel metalPane;
    private JPanel orePane;

    private JPanel pane;

    private JButton endTurnButton;
    private TurnSwitchNotifier turnSwitchNotifier;
    public PlayerInfoArea(Container cont, TurnSwitchNotifier turnSwitchNotifier){

        this.turnSwitchNotifier = turnSwitchNotifier;

        playerLabel = new JLabel("Player");
        scienceLabel = new JLabel("Science");
        energyLabel = new JLabel("Energy");
        metalLabel = new JLabel("Metal");
        oreLabel = new JLabel("Ore");
        wheatLabel = new JLabel("Wheat");
        foodLabel = new JLabel("Food");

        playerTextField = new JTextField();
        scienceTextField = new JTextField();
        energyTextField = new JTextField();
        metalTextField = new JTextField();
        oreTextField = new JTextField();
        wheatTextField = new JTextField();
        foodTextField = new JTextField();

        playerTextField.setEditable(false);
        wheatTextField.setEditable(false);
        foodTextField.setEditable(false);
        metalTextField.setEditable(false);
        oreTextField.setEditable(false);
        scienceTextField.setEditable(false);
        energyTextField.setEditable(false);

        playerTextField.setFocusable(false);
        wheatTextField.setFocusable(false);
        foodTextField.setFocusable(false);
        metalTextField.setFocusable(false);
        oreTextField.setFocusable(false);
        scienceTextField.setFocusable(false);
        energyTextField.setFocusable(false);

        playerLabel.setLabelFor(playerTextField);
        scienceLabel.setLabelFor(scienceTextField);
        energyLabel.setLabelFor(energyTextField);
        metalLabel.setLabelFor(metalTextField);
        oreLabel.setLabelFor(oreTextField);
        wheatLabel.setLabelFor(wheatTextField);
        foodLabel.setLabelFor(foodTextField);

        playerPane = new JPanel(new GridLayout(1, 2, 0, 0));
        playerPane.add(playerLabel);
        playerPane.add(playerTextField);
        wheatPane = new JPanel(new GridLayout(1, 2, 0, 0));
        wheatPane.add(wheatLabel);
        wheatPane.add(wheatTextField);
        foodPane = new JPanel(new GridLayout(1, 2, 0, 0));
        foodPane.add(foodLabel);
        foodPane.add(foodTextField);
        sciencePane = new JPanel(new GridLayout(1, 2, 0, 0));
        sciencePane.add(scienceLabel);
        sciencePane.add(scienceTextField);
        energyPane = new JPanel(new GridLayout(1, 2, 0, 0));
        energyPane.add(energyLabel);
        energyPane.add(energyTextField);
        metalPane = new JPanel(new GridLayout(1, 2,0 ,0 ));
        metalPane.add(metalLabel);
        metalPane.add(metalTextField);
        orePane = new JPanel(new GridLayout(1, 2,0 ,0 ));
        orePane.add(oreLabel);
        orePane.add(oreTextField);

        endTurnButton = new JButton("End Turn");
        endTurnButton.setFocusable(false);
        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnSwitchNotifier.notifyOfTurnSwitch();
            }
        });
        pane = new JPanel(new GridLayout(8, 1, 0, 0));
        pane.add(playerPane);
        pane.add(wheatPane);
        pane.add(foodPane);
        pane.add(sciencePane);
        pane.add(energyPane);
        pane.add(metalPane);
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
    public void update(Player player){
        this.player = player;
        playerTextField.setText(player.getName());
        wheatTextField.setText(Integer.toString(player.getWheat()));
        foodTextField.setText(Integer.toString(player.getFood()));
        scienceTextField.setText(Integer.toString(player.getScience()));
        oreTextField.setText(Integer.toString(player.getOre()));
        metalTextField.setText(Integer.toString(player.getMetal()));
        energyTextField.setText(Integer.toString(player.getEnergy()));
    }
}
