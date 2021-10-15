package duckEncode.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DuckEncodeModel {
    String filePath;
    String[] importFile;
    Properties keyboardProperties;
    List<Byte> file = new ArrayList<Byte>();

    private DuckEncodeFileHandling fileHandler = new DuckEncodeFileHandling();

    public void getImportString(String[] fileToEncode, Properties keyboardProperties, String filePath) {
        importFile = fileToEncode;
        this.keyboardProperties = keyboardProperties;
        this.filePath = filePath;
    }

    public void testImportString() {
        for (int i = 0; i < importFile.length; i++) {
            System.out.println(importFile[i]);
        }
    }

    public void testKeyboardProperties() {
        System.out.println(keyboardProperties);
    }

    public void stringToChar(String s) {
        String[] tempString = s.split(" ");
        String[] updateString;

        if (tempString.length > 1) {
            for (int i = 1; i < tempString.length; i++) {
                updateString = tempString[i].split("");
                // System.out.println(tempString[i] + '\n' + '\n');
                for (int z = 0; z < updateString.length; z++) {
                    if (isUpperCase(updateString[z].charAt(0))) {
                        file.add(getCommand("SHIFT"));
                        file.add(charToByte(updateString[z].charAt(0)));
                    } else {
                        file.add(charToByte(updateString[z].charAt(0)));
                    }
                    // System.out.println(updateString[z]);
                }
            }
        } else {
            updateString = tempString[1].split("");
            for (int x = 0; x < updateString.length; x++) {
                if (isUpperCase(updateString[x].charAt(0))) {
                    file.add(getCommand("SHIFT"));
                    file.add(charToByte(updateString[x].charAt(0)));
                } else {
                    file.add(charToByte(updateString[x].charAt(0)));
                }
            }
        }
    }

    public boolean isUpperCase(char c) {
        if (Character.isUpperCase(c)) {
            return true;
        } else {
            return false;
        }
    }

    public Byte charToByte(char s) {
        String[] tempString;
        byte tempByte;

        if (this.keyboardProperties.getProperty("VK_" + s) != null) {
            tempString = this.keyboardProperties.getProperty("VK_" + s).split(" = ");
            if (tempString.length > 1) {
                tempByte = Byte.parseByte(tempString[1]);
            } else {
                tempByte = (byte) Integer.parseInt(tempString[0].substring(2), 16);
            }
        } else {
            tempByte = 0;
            // System.out.println('\n' + s + " not found in char to byte" + '\n');
        }
        return tempByte;
    }

    public Byte getCommand(String s) {
        String temp;
        Byte value;
        if (this.keyboardProperties.getProperty("VK_" + s) != null) {
            temp = this.keyboardProperties.getProperty("VK_" + s);
            value = (byte) Integer.parseInt(temp.substring(2), 16);
            // System.out.println(value);
        } else {
            value = 0;
            // System.out.println(s + " not Found in properties");
        }
        return value;
    }

    public void convertToHex() {
        int defaultDelay = 0;
        int tempInt = 0;
        boolean isDefaultDelay = false;
        String tempString;
        for (int i = 0; i < this.importFile.length; i++) {
            String[] temp = this.importFile[i].split(" ");
            if (this.importFile[i].equals("REM")) {
                continue;
            }
            if (this.importFile[i].equals("")) {
                continue;
            } else if (this.importFile[i].equals("REPEAT")) {
                String[] rp = this.importFile[i].split(" ");
                int rpl = Integer.parseInt(rp[1]);
                for (int z = 0; z < i; z++) {
                    for (int x = 0; x < rpl; x++) {
                        file.add(getCommand(importFile[x]));
                    }
                }
            } else if (temp[0].equals("DEFAULTDELAY")) {
                defaultDelay = Integer.parseInt(temp[1]);
                isDefaultDelay = true;
            } else if (temp[0].equals("DELAY")) {
                if (isDefaultDelay) {
                    tempInt = defaultDelay;
                    if (tempInt != 0) {
                        while (tempInt > 0) {
                            file.add((byte) 0x00);
                            if (tempInt > 255) {
                                file.add((byte) 0xFF);
                                tempInt -= tempInt;
                            } else {
                                file.add((byte) tempInt);
                                tempInt = 0;
                            }
                        }
                    }
                } else if (temp[1] != null) {
                    tempInt = Integer.parseInt(temp[1].trim());
                    while (tempInt > 0) {
                        file.add((byte) 0x00);
                        // System.out.println(tempInt);
                        if (tempInt >= 255) {
                            file.add((byte) 0xFF);
                            tempInt = tempInt - 255;
                        } else if (tempInt < 255) {
                            file.add((byte) tempInt);
                            tempInt = 0;
                        }
                    }
                }
            } else if (temp[0].equals("STRING")) {
                stringToChar(importFile[i]);
            } else if (this.importFile[i].equals("CONTROL") || this.importFile[i].equals("CTRL")) {
                tempString = "CONTROL";
                file.add(getCommand(tempString));
            } else if (this.importFile[i].equals("ALT")) {
                tempString = "MENU";
                file.add(getCommand(tempString));
            } else if (this.importFile[i].equals("SHIFT")) {
                tempString = "SHIFT";
                file.add(getCommand(tempString));
            } else if (this.importFile[i].equals("ENTER")) {
                tempString = "RETURN";
                file.add(getCommand(tempString));
            } else if (this.importFile[i].equals("CTRL-ALT")) {
                tempString = "CONTROL";
                file.add(getCommand(tempString));
                file.add(getCommand("MENU"));
            } else if (this.importFile[i].equals("CTRL-SHIFT")) {
                tempString = "CONTROL";
                file.add(getCommand(tempString));
                file.add(getCommand("SHIFT"));
            } else if (this.importFile[i].equals("COMMAND-OPTION")) {
                tempString = "CONTROL";
                file.add(getCommand(tempString));
                file.add(getCommand("MENU"));
            } else if (this.importFile[i].equals("ALT-SHIFT")) {
                tempString = "MENU";
                file.add(getCommand(tempString));
                file.add(getCommand("SHIFT"));
            } else if (this.importFile[i].equals("ALT-TAB")) {
                tempString = "MENU";
                file.add(getCommand(tempString));
                file.add(getCommand("TAB"));
            } else if (this.importFile[i].equals("WINDOWS")) {
                tempString = "LWIN";
                file.add(getCommand(tempString));
            } else if (this.importFile[i].equals("COMMAND")) {
                tempString = "RWIN";
                file.add(getCommand(tempString));
            } else if (this.importFile[i].equals("UPARROW")) {
                tempString = "UP";
                file.add(getCommand(tempString));
            } else if (this.importFile[i].equals("DOWNARROW")) {
                tempString = "DOWN";
                file.add(getCommand(tempString));
            } else if (this.importFile[i].equals("LEFTARROW")) {
                tempString = "LEFT";
                file.add(getCommand(tempString));
            } else if (this.importFile[i].equals("RIGHTARROW")) {
                tempString = "RIGHT";
                file.add(getCommand(tempString));
            } else {
                if (getCommand(importFile[i].toUpperCase()) != 0) {
                    file.add(getCommand(importFile[i].toUpperCase()));
                } else {
                    System.out.println(importFile[i] + " not found in file");
                }
            }
        }
        fileHandler.writeToFile(file, filePath);
    }
}
