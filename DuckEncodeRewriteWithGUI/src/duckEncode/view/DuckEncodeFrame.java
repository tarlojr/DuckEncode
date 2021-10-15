package duckEncode.view;

import duckEncode.controller.DuckEncodeController;

import javax.swing.*;

public class DuckEncodeFrame extends JFrame {
    private DuckEncodePanel basePanel;

    public DuckEncodeFrame(DuckEncodeController baseController){
        basePanel = new DuckEncodePanel(baseController);
        setupFrame();
    }

    private void setupFrame(){
        this.setContentPane(basePanel);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(basePanel.fileLocation());
        this.getContentPane().add(basePanel.keyboardProperties());
        this.getContentPane().add(basePanel.keyboardLayout());
        this.getContentPane().add(basePanel.startEncryption());

        this.setVisible(true);
    }

}
