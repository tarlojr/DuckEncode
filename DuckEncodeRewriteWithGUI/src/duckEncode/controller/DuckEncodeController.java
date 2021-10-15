package duckEncode.controller;

import duckEncode.model.DuckEncodeFileHandling;
import duckEncode.model.DuckEncodeModel;
import duckEncode.view.DuckEncodeFrame;

import java.io.IOException;

public class DuckEncodeController {

    private DuckEncodeFrame appFrame;
    private DuckEncodeFileHandling fileHandler;
    private DuckEncodeModel mainModel;

    public void start() {
        appFrame = new DuckEncodeFrame(this);
    }

    public void fileHandlerToEncoder(String f, String p) throws IOException {
        fileHandler = new DuckEncodeFileHandling();
        fileHandler.getSaveLocation(f);
        mainModel = new DuckEncodeModel();
        mainModel.getImportString(fileHandler.importFileToString(f), fileHandler.getInternalResources(p), f);
        mainModel.convertToHex();
    }
}
