package duckEncode.view;

import duckEncode.controller.DuckEncodeController;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DuckEncodePanel extends JPanel implements ActionListener {
    private DuckEncodeController baseController;

    /*
     * Files for the path locations of the imports
     */
    File fileExplorerFileImport;
    File keyboardPropertiesImportPath;
    File keyboardLayoutImportPath;

    /*
     * File Paths
     */
    String fileExplorerImportFile;
    String keyboardPropertiesImportFile = "Resources/wvk.properties";
    String keyboardLayoutImportFile;

    /*
     * booleans for buttons
     */
    boolean isEnableKeyboardProperties = false;
    boolean isEnableKeyboardLayout = false;

    /*
     * fileChooser is an import that makes it easy to select files with a gui
     */
    JFileChooser fileChooser = new JFileChooser();

    /*
     * all of the buttons on the gui
     */
    JButton fileExplorerImport = new JButton("Select a file to encode");
    JButton keyboardPropertiesImport = new JButton("Select a file to use as keyboard.properties");
    JButton existingKeyboardLayoutsButton = new JButton("Select a existing language");
    JButton keyboardLayoutImport = new JButton("Select a file to change languages");
    JButton startButton = new JButton("Start");

    /*
     * check boxes to enable / disable the default keyboard properties and keyboard
     * layout
     */
    JCheckBox enableKeyboardProperties = new JCheckBox();
    JCheckBox enableKeyboardLayout = new JCheckBox();

    public DuckEncodePanel(DuckEncodeController baseController) {
        this.baseController = baseController;

    }

    /*
     * panel for the file to import and export
     */
    public JPanel fileLocation() {
        JPanel fileLocation = new JPanel();

        fileExplorerImport.addActionListener(this);
        fileLocation.add(fileExplorerImport);

        return fileLocation;
    }

    /*
     * panel for the replacement keyboard properties
     */
    public JPanel keyboardProperties() {
        JPanel keyboardProperties = new JPanel();

        keyboardPropertiesImport.setEnabled(isEnableKeyboardProperties);
        keyboardPropertiesImport.addActionListener(this);
        keyboardProperties.add(keyboardPropertiesImport);

        enableKeyboardProperties.addActionListener(this);
        keyboardProperties.add(enableKeyboardProperties);
        return keyboardProperties;
    }

    /*
     * panel for the replacement keyboard layout
     */
    public JPanel keyboardLayout() {
        JPanel keyboardLayout = new JPanel();

        existingKeyboardLayoutsButton.setEnabled(isEnableKeyboardLayout);
        existingKeyboardLayoutsButton.addActionListener(this);
        keyboardLayout.add(existingKeyboardLayoutsButton);

        keyboardLayoutImport.setEnabled(isEnableKeyboardLayout);
        keyboardLayoutImport.addActionListener(this);
        keyboardLayout.add(keyboardLayoutImport);

        enableKeyboardLayout.setEnabled(false);
        enableKeyboardLayout.addActionListener(this);
        keyboardLayout.add(enableKeyboardLayout);

        return keyboardLayout;
    }

    public JPanel startEncryption() {
        JPanel startEncryption = new JPanel();
        startButton.setEnabled(false);
        startButton.addActionListener(this);
        startEncryption.add(startButton);
        return startEncryption;
    }

    public boolean isFileSelected() {
        if (fileExplorerImportFile != null) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * action listener for the buttons and check boxes
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /*
         * action listener for the import file
         */
        if (e.getSource() == fileExplorerImport) {

            int response = fileChooser.showOpenDialog(this);

            if (response == JFileChooser.APPROVE_OPTION) {
                fileExplorerFileImport = fileChooser.getSelectedFile();
                fileExplorerImportFile = fileExplorerFileImport.getPath();
                startButton.setEnabled(isFileSelected());
            }
        }

        /*
         * action listener for keyboard properties
         */
        if (e.getSource() == enableKeyboardProperties) {
            if (enableKeyboardProperties.isSelected()) {
                isEnableKeyboardProperties = true;
            } else {
                isEnableKeyboardProperties = false;
            }
        }
        if (e.getSource() == keyboardPropertiesImport) {

            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                keyboardPropertiesImportPath = fileChooser.getSelectedFile();
                keyboardPropertiesImportFile = keyboardPropertiesImportPath.getPath();
            }
        }

        /*
         * action listener for keyboard layout
         */
        if (e.getSource() == enableKeyboardLayout) {
            if (enableKeyboardLayout.isSelected()) {
                isEnableKeyboardLayout = true;
            } else {
                isEnableKeyboardLayout = false;
            }
        }
        if (e.getSource() == existingKeyboardLayoutsButton) {
            if (existingKeyboardLayoutsButton.isSelected()) {

            }
        }
        if (e.getSource() == keyboardLayoutImport) {

            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                keyboardLayoutImportPath = new File(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }

        /*
         * action listener for the start button
         */
        if (e.getSource() == startButton) {
            try {
                baseController.fileHandlerToEncoder(fileExplorerImportFile, keyboardPropertiesImportFile);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
