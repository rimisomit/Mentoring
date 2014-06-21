package com.mentoring.battleship;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by user on 18.06.2014.
 */
public class FileSaveGame {
    /**
     * File => Class => Object
     */

    //private Board myBoard;
    //private Board compBoard;
    private String templateName;


    public FileSaveGame(String templName){
        this.templateName = templName;
    }

    public void saveBoardToFile(Board myBoard, Board compBoard) throws IOException, URISyntaxException {
        int dimension;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss").format(Calendar.getInstance().getTime());

        File file = new File("resources/template.xls");
        FileInputStream fileInputStream = new FileInputStream(file);

        POIFSFileSystem fs = new POIFSFileSystem(fileInputStream);
        HSSFWorkbook workbook = new HSSFWorkbook(fs, true);
        HSSFSheet sheetMyHits;
        HSSFSheet sheetMyShips;
        HSSFSheet sheetCompHits;
        HSSFSheet sheetCompShips;
        Row rowBodyMyShips;
        Row rowBodyMyHits;
        Row rowBodyCompShips;
        Row rowBodyCompHits;
        Cell cellBodyMyShips;
        Cell cellBodyMyHits;
        Cell cellBodyCompShips;
        Cell cellBodyCompHits;

        sheetMyHits = workbook.createSheet("My Hits");
        sheetMyShips = workbook.createSheet("My Ships");
        sheetCompHits = workbook.createSheet("Comp Hits");
        sheetCompShips = workbook.createSheet("Comp Ships");

        dimension = myBoard.getDim();

        rowBodyMyHits = sheetMyHits.createRow(0);
        cellBodyMyHits = rowBodyMyHits.createCell(0);
        cellBodyMyHits.setCellValue(dimension);
        rowBodyMyShips = sheetMyShips.createRow(0);
        cellBodyMyShips = rowBodyMyShips.createCell(0);
        cellBodyMyShips.setCellValue(dimension);

        rowBodyCompHits = sheetCompHits.createRow(0);
        cellBodyCompHits = rowBodyCompHits.createCell(0);
        cellBodyCompHits.setCellValue(dimension);
        rowBodyCompShips = sheetCompShips.createRow(0);
        cellBodyCompShips = rowBodyCompShips.createCell(0);
        cellBodyCompShips.setCellValue(dimension);

        for (int i = 0; i < dimension; i++ ) {
            rowBodyMyHits = sheetMyHits.createRow(i + 1);
            rowBodyMyShips = sheetMyShips.createRow(i + 1);
            cellBodyMyHits = rowBodyMyHits.createCell(i);
            cellBodyMyShips = rowBodyMyShips.createCell(i);
            cellBodyMyHits.setCellValue(i);
            cellBodyMyShips.setCellValue(i);

            rowBodyCompHits = sheetCompHits.createRow(i + 1);
            rowBodyCompShips = sheetCompShips.createRow(i + 1);
            cellBodyCompHits = rowBodyCompHits.createCell(i);
            cellBodyCompShips = rowBodyCompShips.createCell(i);
            cellBodyCompHits.setCellValue(i);
            cellBodyCompShips.setCellValue(i);

            for (int j = 0; j < dimension; j++) {
                BoardCell myPiece = myBoard.getPiece(i, j);
                BoardCell compPiece = compBoard.getPiece(i, j);

                cellBodyMyHits = rowBodyMyHits.createCell(j);
                cellBodyMyShips = rowBodyMyShips.createCell(j);
                cellBodyMyHits.setCellValue(myPiece.getVal(false));
                cellBodyMyShips.setCellValue(myPiece.getVal(true));

                cellBodyCompHits = rowBodyCompHits.createCell(j);
                cellBodyCompShips = rowBodyCompShips.createCell(j);
                cellBodyCompHits.setCellValue(compPiece.getVal(false));
                cellBodyCompShips.setCellValue(compPiece.getVal(true));
            }
        }
        FileOutputStream fileOut = new FileOutputStream("save/" + timeStamp + ".xls");
        workbook.write(fileOut);
        fileOut.close();
        System.out.println("Saved to " + timeStamp + ".xls");
    }
}
