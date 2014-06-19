package com.mentoring.battleship;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by user on 18.06.2014.
 */
public class FileSaveGame {
    /**
     * File => Class => Object
     */
    //OIFSFileSystem fs;
    //HSSFWorkbook workbook = new HSSFWorkbook(fs, true);
    //HSSFSheet sheetHits, sheetShips;

    private Board myBoard;
    private Board compBoard;
    private String templateN;

    public FileSaveGame(String templateName) throws IOException {
        this.templateN = templateName;
    }


    public void save(Board myBoard, Board compBoard){

    }



}
