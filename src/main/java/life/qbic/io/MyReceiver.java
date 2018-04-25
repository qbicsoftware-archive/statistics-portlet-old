package life.qbic.io;

import com.vaadin.ui.Upload;
import life.qbic.presenter.utils.CustomNotification;
import submodule.data.MainConfig;

import java.io.*;

public  class MyReceiver implements Upload.Receiver, Upload.SucceededListener {
    
    private MainConfig mainConfig;
    private File file;

    public OutputStream receiveUpload(String filename, String mimetype) {
        System.out.println(mimetype);
        // Create upload stream
        FileOutputStream fos = null; // Stream to write to
        try {
            // Open the file for writing.
            file = new File(filename);

            fos = new FileOutputStream(file);
        } catch (final java.io.FileNotFoundException e) {
            CustomNotification.error("Error", e.toString());
            return null;
        }
        return fos;
    }

    public void uploadSucceeded(Upload.SucceededEvent event) {
        try {
            mainConfig = YAMLParser.parseConfig(new FileInputStream(file));
        }catch(IOException e){
            CustomNotification.error("Error", e.toString());
        }
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }
}


