/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

/**
 *
 * @author samu_
 */
public class LectorExcel {
    
    public static Sheet definirMatrizLexico() throws WriteException, IOException, BiffException {
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File("src\\Recursos\\calarxd.xls"));
        } catch (IOException ex) {
            Logger.getLogger(LectorExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
        }
        Sheet sheet = workbook.getSheet(0);

        NumberCell ncelLexico = null;
        for (int fila = 0; fila < sheet.getRows(); fila++) {
            for (int columna = 0; columna < sheet.getColumns(); columna++) {
                if (sheet.getCell(columna, fila).getType() == CellType.NUMBER) {
                    ncelLexico = (NumberCell) sheet.getCell(columna, fila);
                }
            }
        }
        
        return sheet;
    }
    public static Sheet definirMatrizSintactica(){
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File("src\\Recursos\\MatrizSinprueba.xls"));
        } catch (IOException ex) {
            Logger.getLogger(LectorExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(LectorExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        Sheet sheetSIN = workbook.getSheet(0);
        NumberCell ncelSintaxis = null;
        for (int fila = 0; fila < sheetSIN.getRows(); fila++) {
            for (int columna = 0; columna < sheetSIN.getColumns(); columna++) {
                if (sheetSIN.getCell(columna, fila).getType() == CellType.NUMBER) {
                    ncelSintaxis = (NumberCell) sheetSIN.getCell(columna, fila);
                }

            }
        }
        return sheetSIN;
    }
    
}
