package duckEncode.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DuckEncodeFileHandling {

    public String[] importFileToString(String f) throws IOException {
        FileReader fileReader = new FileReader(f);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> fileToEncode = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            fileToEncode.add(line);
        }
        bufferedReader.close();
        return fileToEncode.toArray(new String[fileToEncode.size()]);
    }
    public Properties getInternalResources(String f){
        Properties keyboardProps = new Properties();
        InputStream in;
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        try {
            in = loader.getResourceAsStream(f);
            if (in != null) {
                keyboardProps.load(in);
                in.close();
                System.out.println("Loading Keyboard File .....\t[ OK ]");
            } else {
                System.out.println("Error with keyboard.properties!");
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Error with keyboard.properties!");
        }
        return keyboardProps;
    }
    public String getSaveLocation(String f){
        String saveFileLocation = "";
        String[] fileLocationTemp = f.split("/");
        for(int i = 1; i < fileLocationTemp.length - 1; i++){
            if(i == 1){
                saveFileLocation = "/";
            }
            saveFileLocation += fileLocationTemp[i] + "/";
        }
        return saveFileLocation;
    }
    public void writeToFile(List<Byte> file, String f){
        byte[] data = new byte[file.size()];
        for (int i = 0; i < file.size(); i++) {
            data[i] = file.get(i);
            //System.out.println(data[i]);
        }
        try {
            File someFile = new File( getSaveLocation(f) + "inject.bin");
            FileOutputStream fos = new FileOutputStream(someFile);
            //System.out.println(data);
            fos.write(data);
            fos.flush();
            fos.close();
            System.out.println("DuckyScript Complete.....\t[ OK ]\n");
        } catch (Exception e) {
            System.out.println(e);
            System.out.print("Failed to write hex file!");
        }
    }

}
