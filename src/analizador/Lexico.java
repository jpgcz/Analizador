package analizador;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.Document;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import jxl.Workbook;
import jxl.Sheet;
import jxl.Cell;
import jxl.CellType;
import jxl.NumberCell;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class Lexico extends javax.swing.JFrame {

    boolean ekizde = false;
    boolean desmadre = false, desmadrefin = false;
    int complej;
    String aux = "";
    Cell ncelLexico;
    Cell ncelSintaxis;
    public static int contSombreado;
    boolean ciclo = false;
    JFileChooser ArchivoLeer;
    File archivo;
    FileReader fr;
    BufferedReader entrada;
    File leer;
    String linea;
    public int salto = 0;
    String buscar;
    LinkedList<Token> tokeen = new LinkedList<Token>();
    LinkedList<Token> tokeen2 = new LinkedList<Token>();
    LinkedList<String> lex = new LinkedList<String>();
    LinkedList<LE> errorxd = new LinkedList<LE>();
    LinkedList<ContadoresSintaxis> contadoresSin = new LinkedList<ContadoresSintaxis>();
    LinkedList<Contadores> contadores = new LinkedList<Contadores>();
    LinkedList<contadorestotales> contadorestotales = new LinkedList<contadorestotales>();
    DefaultTableModel dtm = new DefaultTableModel();
    DefaultTableModel dtm2 = new DefaultTableModel();
    DefaultTableModel dtm3 = new DefaultTableModel();
    DefaultTableModel dtm4 = new DefaultTableModel();
    DefaultTableModel dtm5 = new DefaultTableModel();
    
    int pes = -94;
    int vacio = 124;
    int B = 0, N = 0;
    Stack<Integer> pilaproducciones = new Stack<Integer>();
    Stack<String> desmadrestack = new Stack<String>();
    Stack<Integer> desmadreestado = new Stack<Integer>();
    int listap[][] = {
        {-41, 203, 246, -40, 202},
        {202, -34, 201, -37, 204, -36, -1, -68},
        {202, -34, 206, -42, -1},
        {203, 246, -34},
        {205, -1},
        {205, -1, -35},
        {-8},
        {-7},
        {-10},
        {207},
        {-9},
        {-66},
        {-67},
        {208},
        {-65},
        {-3},
        {-4},
        {-5},
        {-6},
        {-37, 209, 212, -36},
        {214},
        {-37, 207, -35, 207, -35, 207, -36, -69},
        {-41, 210, 206, -40},
        {209, 212, -35},
        {211, 206, -51},
        {210, 206, -35},
        {213, 218},
        {213, 218, -22},
        {-39, 216, 212, 215, -38},
        {-12},
        {217, 212, 215, -51},
        {212, 215, -51},
        {219, 220},
        {219, 220, -21},
        {219, 220, -2},
        {221, 222},
        {221, 222, -20},
        {223, 224},
        {223, 224, -23},
        {223, 224, -24},
        {223, 224, -27},
        {223, 224, -28},
        {223, 224, -26},
        {223, 224, -25},
        {223, 224, -52},
        {223, 224, -53},
        {223, 224, -60},
        {223, 224, -64},
        {225, 226},
        {225, 226, -30},
        {227, 228},
        {227, 228, -31},
        {229, 230},
        {229, 230, -29},
        {231, 232},
        {231, 232, -32},
        {231, 232, -33},
        {233, 234},
        {233, 234, -12},
        {233, 234, -11},
        {235, 236},
        {235, 236, -13},
        {235, 236, -15},
        {235, 236, -16},
        {235, 236, -17},
        {237, 238},
        {237, 238, -14},
        {206},
        {239, -1},
        {239, -1, -18},
        {239, -1, -19},
        {245},
        {240, 214},
        {244, -50},
        {-18},
        {-19},
        {241, 243},
        {241, 243},
        {212},
        {-37, 242, -36, -70},
        {-7},
        {-42},
        {-43},
        {-47},
        {-45},
        {-44},
        {-48},
        {-46},
        {-49},
        {-37, -36, -82},
        {-37, -36, -83},
        {-37, 212, -36, -84},
        {-37, 212, -36, -85},
        {-37, 212, -36, -86},
        {-37, 212, -36, -87},
        {-37, 212, -36, -88},
        {-37, 212, -36, -89},
        {-37, 212, -35, 212, -36, -90},
        {-37, 212, -35, 212, -36, -71},
        {-37, 212, -35, 212, -36, -72},
        {-37, 212, -36, -73},
        {-37, 212, -35, 212, -36, -74},
        {-37, 212, -36, -75},
        {-37, -36, -76},
        {-37, 212, -36, -77},
        {-37, 212, -36, -78},
        {-37, 212, -36, -79},
        {-37, 212, -36, -80},
        {-37, 212, -36, -81},
        {-37, 247, 212, -36, -61},
        {-37, 248, -36, -91},
        {-93, 250, 249, 246, -51, 212, -59},
        {-93, 249, 246, -51, 212, -95, 212, -58},
        {-92, 249, 246, -51, 212, -63},
        {-54},
        {-55},
        {212, -62},
        {212},
        {247, 212, -35},
        {247, 212},
        {249, 246, -34},
        {250, 249, 246, -56},
        {249, 246, -57},
        {},//epsilon
    };
    String[] ConCombo[];
    int fila = 0;
    int error = 0, identificador = 0, comentarios = 0, palabrasreservadas = 0, cedec = 0, cebin = 0, cehex = 0, ceoct = 0, ctexto = 0, cfloat = 0, cncomp = 0, ccar = 0, aritmeticos = 0, monogamo = 0, logico = 0, relacionales = 0, bit = 0, identidad = 0, puntuacion = 0, agrupacion = 0, asignacion = 0;
    int pro=0,con=0,conen=0,ltr=0,terp=0,elev=0,sep=0,fac=0,not=0,or=0,opbit=0,and=0,andlog=0,orlog=0,xorlog=0,estatutos=0,asign=0,fl=0,arr=0,funciones=0,expas=0;
    Graphics g;
    Image bgImage;

    public Lexico() {
        initComponents();

        setLocationRelativeTo(this);

        jPanel2.setOpaque(false);
        jPanel6.setOpaque(false);
        mostrar();

    }

    int k = 0;

    public void mostrartok() {
        Object mat[][] = new Object[tokeen.size()][3];
        for (int t = 0; t < tokeen.size(); t++) {

            mat[t][0] = tokeen.get(t).getEst();

            mat[t][1] = tokeen.get(t).getValor();

            mat[t][2] = tokeen.get(t).getLinea();
            //mat[t][3]=tokeen.get(t).getIncluido();

        }

        tablatokens.setModel(new javax.swing.table.DefaultTableModel(mat,
                new String[]{"Estado", "Lexema", "linea"}//,"incluido"
        ));
        //tablatokens.setModel(dtm);

    }

    public void mostrarError() {
        Object materror[][] = new Object[errorxd.size()][5];
        for (int i = 0; i < errorxd.size(); i++) {
            materror[i][0] = errorxd.get(i).getNo();
            materror[i][1] = errorxd.get(i).getDesc();
            materror[i][2] = errorxd.get(i).getLexico();
            materror[i][3] = errorxd.get(i).getTipoerror();
            materror[i][4] = errorxd.get(i).getLinea();

        }
        tablaerrores.setModel(new javax.swing.table.DefaultTableModel(materror,
                new String[]{"estado", "descError", "palabra", "tipoError", "linea"}
        ));

    }

    public void mostrarcontadores() {
        Object mcon[][] = new Object[contadores.size()][22];
        for (int i = 0; i < contadores.size(); i++) {

            mcon[i][0] = contadores.get(i).getLinea();
            mcon[i][1] = contadores.get(i).getError();
            mcon[i][2] = contadores.get(i).getIdentificador();
            mcon[i][3] = contadores.get(i).getComentarios();
            mcon[i][4] = contadores.get(i).getPalabrasreservadas();
            mcon[i][5] = contadores.get(i).getCedec();
            mcon[i][6] = contadores.get(i).getCebin();
            mcon[i][7] = contadores.get(i).getCehex();
            mcon[i][8] = contadores.get(i).getCeoct();
            mcon[i][9] = contadores.get(i).getCtexto();
            mcon[i][10] = contadores.get(i).getCfloat();
            mcon[i][11] = contadores.get(i).getCncomp();
            mcon[i][12] = contadores.get(i).getCcar();
            mcon[i][13] = contadores.get(i).getAritmeticos();
            mcon[i][14] = contadores.get(i).getMonogamo();
            mcon[i][15] = contadores.get(i).getLogico();
            mcon[i][16] = contadores.get(i).getRelacionales();
            mcon[i][17] = contadores.get(i).getBit();
            mcon[i][18] = contadores.get(i).getIdentidad();
            mcon[i][19] = contadores.get(i).getPuntucion();
            mcon[i][20] = contadores.get(i).getPuntucion();
            mcon[i][21] = contadores.get(i).getAsignacion();

        }
        //linea=1,error,identificador,comentarios,palabrasreservadas,cedec,cebin,cehex,ceoct,ctexto,cfloat,cncomp,ccar,aritmeticos,monogamo,logico,relacionales,bit,identidad,puntucion,agrupacion,asignacion;

        ContadoresTB.setModel(new javax.swing.table.DefaultTableModel(mcon,
                new String[]{"linea", "error", "identificador", " comentarios", "palabrasreservadas,", "cedec", "cebin", "cehex", "ceoct", "ctexto", "cfloat,", "cncomp", "ccar", "aritmeticos", "monogamo", "logico", "relacionales", "bit", "identidad", "puntucion", "agrupacion", "asignacion"}
        ));
    }

    public void mostrartotales() {
        Object mcon[][] = new Object[contadorestotales.size()][21];
        for (int i = 0; i < contadorestotales.size(); i++) {

            mcon[i][0] = contadorestotales.get(i).getError();
            mcon[i][1] = contadorestotales.get(i).getIdentificador();
            mcon[i][2] = contadorestotales.get(i).getComentarios();
            mcon[i][3] = contadorestotales.get(i).getPalabrasreservadas();
            mcon[i][4] = contadorestotales.get(i).getCedec();
            mcon[i][5] = contadorestotales.get(i).getCebin();
            mcon[i][6] = contadorestotales.get(i).getCehex();
            mcon[i][7] = contadorestotales.get(i).getCeoct();
            mcon[i][8] = contadorestotales.get(i).getCtexto();
            mcon[i][9] = contadorestotales.get(i).getCfloat();
            mcon[i][10] = contadorestotales.get(i).getCncomp();
            mcon[i][11] = contadorestotales.get(i).getCcar();
            mcon[i][12] = contadorestotales.get(i).getAritmeticos();
            mcon[i][13] = contadorestotales.get(i).getMonogamo();
            mcon[i][14] = contadorestotales.get(i).getLogico();
            mcon[i][15] = contadorestotales.get(i).getRelacionales();
            mcon[i][16] = contadorestotales.get(i).getBit();
            mcon[i][17] = contadorestotales.get(i).getIdentidad();
            mcon[i][18] = contadorestotales.get(i).getPuntucion();
            mcon[i][19] = contadorestotales.get(i).getPuntucion();
            mcon[i][20] = contadorestotales.get(i).getAsignacion();

        }
        //linea=1,error,identificador,comentarios,palabrasreservadas,cedec,cebin,cehex,ceoct,ctexto,cfloat,cncomp,ccar,aritmeticos,monogamo,logico,relacionales,bit,identidad,puntucion,agrupacion,asignacion;

        jTablecontadorestotales.setModel(new javax.swing.table.DefaultTableModel(mcon,
                new String[]{"error", "identificador", " comentarios", "palabrasreservadas,", "cedec", "cebin", "cehex", "ceoct", "ctexto", "cfloat,", "cncomp", "ccar", "aritmeticos", "monogamo", "logico", "relacionales", "bit", "identidad", "puntucion", "agrupacion", "asignacion"}
        ));
    }
    public void mostrarconsin(){
     Object mcon[][] = new Object[contadoresSin.size()][21];
         for (int i = 0; i < contadoresSin.size(); i++) {

            mcon[i][0] = contadoresSin.get(i).getPro();
            mcon[i][1] = contadoresSin.get(i).getCon();
            mcon[i][2] = contadoresSin.get(i).getConen();
            mcon[i][3] = contadoresSin.get(i).getLtr();
            mcon[i][4] = contadoresSin.get(i).getTerp();
            mcon[i][5] = contadoresSin.get(i).getElev();
            mcon[i][6] = contadoresSin.get(i).getSep();
            mcon[i][7] = contadoresSin.get(i).getFac();
            mcon[i][8] = contadoresSin.get(i).getNot();
            mcon[i][9] = contadoresSin.get(i).getOr();
            mcon[i][10] = contadoresSin.get(i).getOpbit();
            mcon[i][11] = contadoresSin.get(i).getAnd();
            mcon[i][12] = contadoresSin.get(i).getAndlog();
            mcon[i][13] = contadoresSin.get(i).getOrlog();
            mcon[i][14] = contadoresSin.get(i).getXorlog();
            mcon[i][15] = contadoresSin.get(i).getEstatutos();
            mcon[i][16] = contadoresSin.get(i).getAsign();
            mcon[i][17] = contadoresSin.get(i).getFl();
            mcon[i][18] = contadoresSin.get(i).getArr();
            mcon[i][19] = contadoresSin.get(i).getFunciones();
             mcon[i][20] = contadoresSin.get(i).getExpas();
        }
         jTablecontadoresSin.setModel(new javax.swing.table.DefaultTableModel(mcon,
                new String[]{"error", "identificador", " comentarios", "palabrasreservadas,", "cedec", "cebin", "cehex", "ceoct", "ctexto", "cfloat,", "cncomp", "ccar", "aritmeticos", "monogamo", "logico", "relacionales", "bit", "identidad", "puntucion", "agrupacion", "asignacion"}
        ));
    }

   
    Workbook Excel;
    File inputWorkBook;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea(){public void paint(Graphics g){
            super.paint(g);
            Graphics2D g2d =(Graphics2D)g;
            AlphaComposite opaco=AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.5f);
            g2d.setComposite(opaco);

            g2d.setColor(new Color(59, 196, 245));
            for(int i=0;i<=10000;i++){

                if((i%2!=0)){
                    Rectangle2D linea=new Rectangle2D.Double(0,(16*i),1000,16);
                    g2d.fill(linea);
                }
            }
        }

    };
    jPanel6 = new javax.swing.JPanel();
    jScrollPane5 = new javax.swing.JScrollPane();
    ContadoresTB = new javax.swing.JTable();
    jPanel3 = new javax.swing.JPanel();
    jLabel4 = new javax.swing.JLabel();
    LabelID = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    labelComen = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    labelPR = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    labelCEDec = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    labelCtexto = new javax.swing.JLabel();
    jLabel9 = new javax.swing.JLabel();
    labelCEHex = new javax.swing.JLabel();
    jLabel10 = new javax.swing.JLabel();
    labelCEOct = new javax.swing.JLabel();
    jLabel12 = new javax.swing.JLabel();
    lablelCFloat = new javax.swing.JLabel();
    jLabel14 = new javax.swing.JLabel();
    labelCNComp = new javax.swing.JLabel();
    jLabel16 = new javax.swing.JLabel();
    labelCcar = new javax.swing.JLabel();
    jLabel18 = new javax.swing.JLabel();
    jLabel19 = new javax.swing.JLabel();
    jLabel20 = new javax.swing.JLabel();
    jLabel21 = new javax.swing.JLabel();
    jLabel22 = new javax.swing.JLabel();
    labelAritmeticos = new javax.swing.JLabel();
    labelMonogamo = new javax.swing.JLabel();
    labelLogico = new javax.swing.JLabel();
    labelbit = new javax.swing.JLabel();
    lablePuntuacion = new javax.swing.JLabel();
    jLabel13 = new javax.swing.JLabel();
    labelAgrupacion = new javax.swing.JLabel();
    jLabel11 = new javax.swing.JLabel();
    labelAsignacion = new javax.swing.JLabel();
    jLabel15 = new javax.swing.JLabel();
    labelRelacionales = new javax.swing.JLabel();
    jLabel17 = new javax.swing.JLabel();
    labelError = new javax.swing.JLabel();
    jLabel23 = new javax.swing.JLabel();
    labelcebin = new javax.swing.JLabel();
    jLabel24 = new javax.swing.JLabel();
    labelidentidad = new javax.swing.JLabel();
    jScrollPane4 = new javax.swing.JScrollPane();
    jTablecontadorestotales = new javax.swing.JTable();
    btnsubir = new javax.swing.JButton();
    btnanalizar = new javax.swing.JButton();
    jScrollPane3 = new javax.swing.JScrollPane();
    tablatokens = new javax.swing.JTable();
    jScrollPane2 = new javax.swing.JScrollPane();
    tablaerrores = new javax.swing.JTable();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    Generar = new javax.swing.JButton();
    jScrollPane6 = new javax.swing.JScrollPane();
    jTablecontadoresSin = new javax.swing.JTable();
    jLabel25 = new javax.swing.JLabel();
    jLabel26 = new javax.swing.JLabel();
    jLabel27 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setAutoRequestFocus(false);
    setBackground(new java.awt.Color(0, 0, 153));
    setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    setMinimumSize(new java.awt.Dimension(1290, 720));
    getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jPanel1.setBackground(new java.awt.Color(59, 196, 245));
    jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1309, 340, -1, 329));

    jTextArea1.setBackground(new java.awt.Color(255,255,255));
    jTextArea1.setBorder(null);
    TextLineNumber tlna = new TextLineNumber(jTextArea1);
    jScrollPane1.setRowHeaderView(tlna);
    jTextArea1.setColumns(20);
    jTextArea1.setRows(5);
    jScrollPane1.setViewportView(jTextArea1);

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 3, Short.MAX_VALUE))
    );

    jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 790, 410));

    jPanel6.setBackground(new java.awt.Color(255, 255, 255));
    jPanel6.setFocusable(false);
    jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    ContadoresTB.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    jScrollPane5.setViewportView(ContadoresTB);

    jPanel6.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 1));

    jPanel3.setBackground(new java.awt.Color(255, 255, 255));

    jLabel4.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel4.setText("Identificadores:");

    LabelID.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    LabelID.setText("0");

    jLabel5.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel5.setText("Comentarios:");

    labelComen.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelComen.setText("0");

    jLabel6.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel6.setText("Palabras Reservadas:");

    labelPR.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelPR.setText("0");

    jLabel7.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel7.setText("CE-Dec:");

    labelCEDec.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelCEDec.setText("0");

    jLabel8.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel8.setText("Ctexto:");

    labelCtexto.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelCtexto.setText("0");

    jLabel9.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel9.setText("CE-Hex:");

    labelCEHex.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelCEHex.setText("0");

    jLabel10.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel10.setText("CE-Oct");

    labelCEOct.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelCEOct.setText("0");

    jLabel12.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel12.setText("CFLotante:");

    lablelCFloat.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    lablelCFloat.setText("0");

    jLabel14.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel14.setText("CNComp");

    labelCNComp.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelCNComp.setText("0");

    jLabel16.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel16.setText("Ccar:");

    labelCcar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelCcar.setText("0");

    jLabel18.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel18.setText("Aritmeticos:");

    jLabel19.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel19.setText("Monogamo:");

    jLabel20.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel20.setText("Logico:");

    jLabel21.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel21.setText("Bit:");

    jLabel22.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel22.setText("puntuacion:");

    labelAritmeticos.setText("0");

    labelMonogamo.setText("0");

    labelLogico.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelLogico.setText("0");

    labelbit.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelbit.setText("0");

    lablePuntuacion.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    lablePuntuacion.setText("0");

    jLabel13.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel13.setText("Agrupacion:");

    labelAgrupacion.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelAgrupacion.setText("0");

    jLabel11.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel11.setText("Asignacion:");

    labelAsignacion.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelAsignacion.setText("0");

    jLabel15.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel15.setText("Relacionales:");

    labelRelacionales.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelRelacionales.setText("0");

    jLabel17.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel17.setText("Error:");

    labelError.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelError.setText("0");

    jLabel23.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel23.setText("CE-Bin:");

    labelcebin.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelcebin.setText("0");

    jLabel24.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel24.setText("Identidad:");

    labelidentidad.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    labelidentidad.setText("0");

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addGap(30, 30, 30)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel24)
                            .addGap(4, 4, 4)
                            .addComponent(labelidentidad))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addGap(24, 24, 24)
                            .addComponent(labelError))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addGap(14, 14, 14)
                            .addComponent(labelcebin))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addGap(8, 8, 8)
                            .addComponent(labelRelacionales, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addGap(4, 4, 4)
                            .addComponent(labelCNComp))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel16)
                            .addGap(9, 9, 9)
                            .addComponent(labelCcar))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(13, 13, 13)
                            .addComponent(labelComen))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(9, 9, 9)
                            .addComponent(labelPR))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(6, 6, 6)
                            .addComponent(labelCEDec))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(0, 0, 0)
                            .addComponent(labelCtexto))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(0, 0, 0)
                            .addComponent(labelCEHex))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addGap(6, 6, 6)
                                    .addComponent(labelCEOct))
                                .addComponent(jLabel12))
                            .addGap(3, 3, 3)
                            .addComponent(lablelCFloat)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel18)
                            .addGap(4, 4, 4)
                            .addComponent(labelAritmeticos))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel19)
                            .addGap(2, 2, 2)
                            .addComponent(labelMonogamo))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel20)
                            .addGap(5, 5, 5)
                            .addComponent(labelLogico))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel21)
                            .addGap(9, 9, 9)
                            .addComponent(labelbit))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel22)
                            .addGap(5, 5, 5)
                            .addComponent(lablePuntuacion))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel13)
                            .addGap(4, 4, 4)
                            .addComponent(labelAgrupacion))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addGap(8, 8, 8)
                            .addComponent(labelAsignacion))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(10, 10, 10)
                            .addComponent(LabelID, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))
            .addGap(29, 29, 29))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
            .addContainerGap(63, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel18)
                        .addComponent(labelAritmeticos))
                    .addGap(1, 1, 1)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel19)
                        .addComponent(labelMonogamo))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel20)
                        .addComponent(labelLogico))
                    .addGap(7, 7, 7)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel21)
                        .addComponent(labelbit))
                    .addGap(1, 1, 1)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel22)
                        .addComponent(lablePuntuacion))
                    .addGap(1, 1, 1)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel13)
                        .addComponent(labelAgrupacion))
                    .addGap(8, 8, 8)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11)
                        .addComponent(labelAsignacion))
                    .addGap(11, 11, 11)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4)
                        .addComponent(LabelID)))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel14)
                        .addComponent(labelCNComp))
                    .addGap(4, 4, 4)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel16)
                        .addComponent(labelCcar))
                    .addGap(4, 4, 4)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(labelComen))
                    .addGap(4, 4, 4)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addComponent(labelPR))
                    .addGap(4, 4, 4)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7)
                        .addComponent(labelCEDec))
                    .addGap(4, 4, 4)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8)
                        .addComponent(labelCtexto))
                    .addGap(4, 4, 4)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9)
                        .addComponent(labelCEHex))
                    .addGap(4, 4, 4)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(labelCEOct))
                            .addGap(5, 5, 5)
                            .addComponent(jLabel12))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(24, 24, 24)
                            .addComponent(lablelCFloat)))
                    .addGap(8, 8, 8)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel24)
                        .addComponent(labelidentidad))
                    .addGap(1, 1, 1)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel17)
                        .addComponent(labelError))
                    .addGap(1, 1, 1)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel23)
                        .addComponent(labelcebin))
                    .addGap(1, 1, 1)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15)
                        .addComponent(labelRelacionales))))
            .addGap(56, 56, 56))
    );

    jPanel6.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 410));

    jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 130, 420, 410));

    jTablecontadorestotales.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    jScrollPane4.setViewportView(jTablecontadorestotales);

    jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(535, 411, -1, 0));

    btnsubir.setFont(new java.awt.Font("Felix Titling", 1, 18)); // NOI18N
    btnsubir.setText("Subir archivo");
    btnsubir.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnsubirActionPerformed(evt);
        }
    });
    jPanel1.add(btnsubir, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 200, 50));

    btnanalizar.setFont(new java.awt.Font("Felix Titling", 1, 18)); // NOI18N
    btnanalizar.setText("Compilar");
    btnanalizar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnanalizarActionPerformed(evt);
        }
    });
    jPanel1.add(btnanalizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, 150, 50));

    tablatokens.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null}
        },
        new String [] {
            "No", "Lex", "Linea"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jScrollPane3.setViewportView(tablatokens);

    jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 600, 570, 180));

    tablaerrores.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null, null}
        },
        new String [] {
            "No", "Descripcion", "Lex", "tipo de error", "Linea"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jScrollPane2.setViewportView(tablaerrores);

    jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 600, 640, 180));

    jLabel2.setFont(new java.awt.Font("Felix Titling", 1, 18)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(255, 255, 255));
    jLabel2.setText("CONTADORES");
    jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 100, 140, -1));

    jLabel3.setFont(new java.awt.Font("Felix Titling", 1, 18)); // NOI18N
    jLabel3.setForeground(new java.awt.Color(255, 255, 255));
    jLabel3.setText("TOKENS");
    jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 560, 80, 26));

    Generar.setFont(new java.awt.Font("Felix Titling", 1, 18)); // NOI18N
    Generar.setText("Exportar");
    Generar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            GenerarActionPerformed(evt);
        }
    });
    jPanel1.add(Generar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 20, 151, 50));

    jTablecontadoresSin.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    jScrollPane6.setViewportView(jTablecontadoresSin);

    jPanel1.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 480, 700, 0));

    jLabel25.setFont(new java.awt.Font("Tahoma", 0, 54)); // NOI18N
    jLabel25.setForeground(new java.awt.Color(255, 255, 255));
    jLabel25.setText("Compilador");
    jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

    jLabel26.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
    jLabel26.setForeground(new java.awt.Color(255, 255, 255));
    jLabel26.setText(".Jpg");
    jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 100, -1, -1));

    jLabel27.setFont(new java.awt.Font("Felix Titling", 1, 18)); // NOI18N
    jLabel27.setForeground(new java.awt.Color(255, 255, 255));
    jLabel27.setText("ERRORES");
    jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 560, 90, -1));

    getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 800));

    pack();
    }// </editor-fold>//GEN-END:initComponents

    public void importarKK(String Datos) {

        /* FileInputStream fis = new FileInputStream("C:\\Users\\said\\Documents\\LEXICO\\matrizprueba.xlsx");
            wb = new XSSFWorkbook(fis);*/
        try {
            Excel = Workbook.getWorkbook(inputWorkBook);

        } catch (IOException ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leerMatrizLexico() throws WriteException, IOException {
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File("src\\Recursos\\calarxd.xls"));
        } catch (IOException ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
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

        compilar(sheet);
    }

    public void ReadCellDataSin() throws IOException {
        //int ResultSIN = 0;
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File("D:\\Documents\\1_Compi_2019\\AnalizadorR\\Analizador\\src\\Recursos\\MatrizSinprueba.xls"));
        } catch (IOException ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
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
        compilarSin(sheetSIN);//<----------compilo sintaxis

    }

    public static void reiniciarJTable(javax.swing.JTable Tabla) {

        DefaultTableModel modelo = (DefaultTableModel) Tabla.getModel();
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }

        TableColumnModel modCol = Tabla.getColumnModel();
        while (modCol.getColumnCount() > 0) {
            modCol.removeColumn(modCol.getColumn(0));
        }

    }
    static int contador = 1;
    int i = 0, complexx = 0;
    String texto = "";

    void limpiaTabla() {
        try {
            dtm = (DefaultTableModel) tablatokens.getModel();
            int a = dtm.getRowCount() - 1;
            for (int i = 0; i < a; i++) {
                dtm.removeRow(0); //aquí estaba el error, antes pasaba la i como parametro.... soy un bacín  XD
            }
        } catch (Exception e) {
            //System.out.println(e);
        }
    }
    int nLineas = 0;
    boolean correccionL = false;
    private void btnanalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanalizarActionPerformed
        aux = "";
        complej = 0;

        reiniciarJTable(tablatokens);
        reiniciarJTable(tablaerrores);
        reiniciarJTable(ContadoresTB);
        reiniciarJTable(jTablecontadorestotales);
        reiniciarJTable(jTablecontadoresSin);
//        try {
//            leerMatrizLexico();//este llama compilar lexico
//
//        } catch (WriteException ex) {
//            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
//        }
        tokeen.removeAll(tokeen);
        errorxd.removeAll(errorxd);
        contadores.removeAll(contadores);
        contadorestotales.removeAll(contadorestotales);
        contadoresSin.removeAll(contadoresSin);

        try {
            leerMatrizLexico();//este llama compilar lexico

        } catch (WriteException ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
        }
        mostrartok();//manda JTable tokens
        mostrarError();//manda JTable errores
        mostrarcontadores();//contadores por linea
        mostrartotales();//totales
        mostrarconsin();//contadores sintaxis
        for (int j = 0; j < 21; j++) {
            cantidades[j] = 0;
        }

        tokeen.removeAll(tokeen);
        errorxd.removeAll(errorxd);
        contadores.removeAll(contadores);
        contadorestotales.removeAll(contadorestotales);
    }//GEN-LAST:event_btnanalizarActionPerformed

    public void compilar(Sheet s) {
        int lineas = 1, complemas = 0;
        boolean com = true, haymas = false;
        String palabratmp = "", descError = "", tipoError = "Léxico", palabra = "";
        String lexema = "";

        char car = ' ';
        texto = jTextArea1.getText();

        int columna = 0, estado = 0;
        boolean b = true;

        jTextArea1.setText(jTextArea1.getText() + " " + "\n");

        while (i < jTextArea1.getText().length()) {

            if (b) {

                car = jTextArea1.getText().charAt(i);

            }

            if (car == '\n') {

                contadores.add(new Contadores(lineas, error, identificador, comentarios, palabrasreservadas, cedec, cebin, cehex, ceoct, ctexto, cfloat, cncomp, ccar, aritmeticos, monogamo, logico, relacionales, bit, identidad, puntuacion, agrupacion, asignacion));
                error = 0;
                identificador = 0;
                comentarios = 0;
                palabrasreservadas = 0;
                cedec = 0;
                cebin = 0;
                cehex = 0;
                ceoct = 0;
                ctexto = 0;
                cfloat = 0;
                cncomp = 0;
                ccar = 0;
                aritmeticos = 0;
                monogamo = 0;
                logico = 0;
                relacionales = 0;
                bit = 0;
                //identidad = 0;
                puntuacion = 0;
                agrupacion = 0;
                asignacion = 0;
                lineas++;
            }

            b = true;

            columna = ContarC(car);

            ncelLexico = s.getCell(columna, estado);
            if (ncelLexico.getType() == CellType.NUMBER) {
                estado = Integer.parseInt(ncelLexico.getContents());
            }
            if (estado == 14 || estado == 15 || estado == 16 || estado == 18 || estado == 26) {
                aux += car;
            } else {
                aux = "";
            }

            if (estado == -1) {

                switch (lexema.trim().toLowerCase()) {

                    case "is":
                        estado = -52;
                        identidad++;
                        break;
                        
                    case "isnot":
                        estado = -53;
                        identidad++;
                        break;
                    case "break":
                        estado = -54;
                        palabrasreservadas++;
                        break;
                    case "continue":
                        estado = -55;
                        palabrasreservadas++;    
                        break;
                    case "elif":
                        estado = -56;
                        palabrasreservadas++;
                        break;
                    case "else":
                        estado = -57;
                        palabrasreservadas++;
                        break;
                    case "for":
                        estado = -58;
                        palabrasreservadas++;
                        break;
                    case "if":
                        estado = -59;
                        palabrasreservadas++;
                        break;
                    case "in":
                        estado = -60;
                        palabrasreservadas++;
                        break;
                    case "print":
                        estado = -61;
                        palabrasreservadas++;
                        break;
                    case "return":
                        estado = -62;
                        palabrasreservadas++;
                        break;
                    case "while":
                        estado = -63;
                        palabrasreservadas++;
                        break;
                    case "innot":
                        estado = -64;
                        palabrasreservadas++;
                        break;
                    case "none":
                        estado = -65;
                        palabrasreservadas++;
                        break;
                    case "true":
                        estado = -66;
                        palabrasreservadas++;
                        break;
                    case "false":

                        estado = -67;
                        palabrasreservadas++;
                        break;
                    case "def":
                        estado = -68;
                        palabrasreservadas++;
                        break;
                    case "range":
                        estado = -69;
                        palabrasreservadas++;
                        break;
                    case "input":
                        estado = -70;
                        palabrasreservadas++;
                        break;

                    case "findall":
                        estado = -71;
                        palabrasreservadas++;
                        break;
                    case "replace":
                        estado = -72;
                        palabrasreservadas++;
                        break;
                    case "len":
                        estado = -73;
                        palabrasreservadas++;
                        break;
                    case "sample":
                        estado = -74;
                        palabrasreservadas++;
                        break;
                    case "choice":
                        estado = -75;
                        palabrasreservadas++;
                        break;
                    case "random":
                        estado = -76;
                        palabrasreservadas++;
                        break;
                    case "randrange":
                        estado = -77;
                        palabrasreservadas++;
                        break;
                    case "mean":
                        estado = -78;
                        palabrasreservadas++;
                        break;
                    case "median":
                        estado = -79;
                        palabrasreservadas++;
                        break;
                    case "variance":
                        estado = -80;
                        palabrasreservadas++;
                        break;
                    case "sum":
                        estado = -81;
                        palabrasreservadas++;
                        break;
                    case "sort":
                        estado = -82;
                        palabrasreservadas++;
                        break;
                    case "reverse":
                        estado = -83;
                        palabrasreservadas++;
                        break;
                    case "count":
                        estado = -84;
                        palabrasreservadas++;
                        break;
                    case "index":
                        estado = -85;
                        palabrasreservadas++;
                        break;
                    case "append":
                        estado = -86;
                        palabrasreservadas++;
                        break;
                    case "extend":
                        estado = -87;
                        palabrasreservadas++;
                        break;
                    case "pop":
                        estado = -88;
                        palabrasreservadas++;
                        break;
                    case "remove":
                        estado = -89;
                        palabrasreservadas++;
                        break;
                    case "insert":
                        estado = -90;
                        palabrasreservadas++;
                        break;
                    case "println":
                        estado = -91;
                        palabrasreservadas++;
                        break;
                    case "wend":
                        estado = -92;
                        palabrasreservadas++;
                        break;
                    case "end":
                        estado = -93;
                        palabrasreservadas++;
                        break;
                    case "to":
                        estado = -95;
                        palabrasreservadas++;
                        break;
                    default:

                        estado = -1;

                }

            }

            if (estado < 0) {
                aux = "";
                sumarContadores(estado);
                if (correccionL) {
                    lineas -= 1;
                    correccionL = false;
                }

                if (desmadre == false) {
                    if (car == '\n') {

                        tokeen.add(new Token(estado, lexema, lineas - 1));

                        lineas--;
                    } else {
                        tokeen.add(new Token(estado, lexema, lineas));
                    }

                    estado = 0;
                    lexema = "";
                    b = false;
                }


            } else if (estado >= 500) {
                if (estado <= 520) {
                    sumarContadores(estado);
                }

                switch (estado) {
                    case 500:
                        descError = "Caracter no valido";
                        break;
                    case 501:
                        descError = "Error con salto de línea";
                        break;
                    case 502:
                        descError = "se espera ‘ (comilla simple)";
                        break;
                    case 503:
                        descError = "Se espera numero 0-9";
                        break;
                    case 504:
                        descError = "Se espera  - (menos) o numero 0-9";
                        break;
                    case 505:
                        descError = "Se espera 0 ó 1 ";
                        break;
                    case 506:
                        descError = "Se espera a-f, 0-9";
                        break;
                    case 507:
                        descError = "Error con salto de linea se espera “";
                        break;
                    default:
                        descError = "error";
                        break;

                }

                tipoError = "Lexico";
                if (estado == 500) {

                    lexema += car;

                    errorxd.add(new LE(descError, lexema, tipoError, estado, lineas));
                    estado = 0;
                    lexema = "";
                    b = true;
                    i++;
                    error++;

                } else if (estado == 550) {
                    //System.out.println("ENTROOOOO");

                    estado = 82;
                    i = complexx;
                    jTextArea1.insert(" ", i);//agregar un espacio para volver a retokenizar facil
                    lexema = "(";
//                                   
                    b = false;

                } else if (estado == 560) {

                    //System.out.println("lexemaaaa ENTRANDO A ERROR 560:" + lexema);
//                                    
                    /* 
                                        
                                    }*/

                    for (int m = 1; m < aux.length(); m++) {

                        if (aux.charAt(m) == '.') {
                            estado = 84;
                        }
                    }
                    if (estado != 84) {
                        estado = 83;
                    }

                    //System.out.println("ESTADO QUE ME DA:" + estado);

//                                    i=complexx; 
//                                    jTextArea1.insert(" ", i);
                    i = complemas - 1;
                    jTextArea1.insert(" ", i);

                    //complej=aux.length();
                    //jTextArea1.insert(" ", complej);
                    //System.out.println("tamaño aux" + aux.length() + "tamaño I:" + i + "complej:" + complej);

                    lexema = aux;
                    aux = "";
                    com = true;
                    b = true;

                } else if (estado == 501 || estado == 507) {

                    errorxd.add(new LE(descError, lexema, tipoError, estado, lineas - 1));
                    correccionL = true;
                    estado = 0;
                    lexema = "";
                    b = true;
                    error++;

                } else {

                    errorxd.add(new LE(descError, lexema, tipoError, estado, lineas));
                    aux = "";
                    estado = 0;
                    b = false;
                    lexema = "";
                    error++;
                }

            } else {

                if (car == ' ' || car == '\n') {//quitar los espacios 
                    car = '\00';
                    lexema += car;
                    desmadre = false;
                    i++;
                } else if (car != ' ') {
                    lexema += car;
                    i++;
                    if (car >= '0' && car <= '9' && com == true) {
                        complej = i;
                        com = false;
                    }
                    if (car == '(') {
                        complexx = i;
                    }
                    if (car == '+') {
                        complemas = i;
                        haymas = true;
                    }

                    //System.out.println("ASI VA LA CADENA: " + lexema);
                    //System.out.println("caracter agregado: " + car);
                    //System.out.println("lexema actual: " + car);
                    //System.out.println("ESTADO:" + columna + "/" + estado);
                    //System.out.println("AUX: " + aux);
                    //System.out.println("COMPLEJ: " + complej);
                }

            }

            //int ,errorL,identificadorL,comentariosL,palabrasreservadasL,cedecL,cebinL,cehexL,ceoctL,ctextoL,cfloatL,cncompL,ccarL,aritmeticosL,monogamoL,logicoL,relacionalesL,bitL,identidadL,puntuacionL,agrupacionL,asignacionL;
//            labelError.setText(Integer.toString(cantidades[0]));
//            LabelID.setText(Integer.toString(cantidades[1]));
//            labelComen.setText(Integer.toString(cantidades[2]));
//            labelPR.setText(Integer.toString(cantidades[3]));
//            labelCEDec.setText(Integer.toString(cantidades[4]));
//            labelcebin.setText(Integer.toString(cantidades[5]));
//            labelCEHex.setText(Integer.toString(cantidades[6]));
//            labelCEOct.setText(Integer.toString(cantidades[7]));
//            labelCtexto.setText(Integer.toString(cantidades[8]));
//            labelAgrupacion.setText(Integer.toString(cantidades[19]));
//            lablelCFloat.setText(Integer.toString(cantidades[9]));
//            labelCNComp.setText(Integer.toString(cantidades[10]));
//            labelCcar.setText(Integer.toString(cantidades[11]));
//            labelAritmeticos.setText(Integer.toString(cantidades[12]));
//            labelMonogamo.setText(Integer.toString(cantidades[13]));
//            labelLogico.setText(Integer.toString(cantidades[14]));
//            labelbit.setText(Integer.toString(cantidades[16]));
//            lablePuntuacion.setText(Integer.toString(cantidades[18]));
//            labelidentidad.setText(Integer.toString(cantidades[17]));
//            labelRelacionales.setText(Integer.toString(cantidades[15]));
//            labelAsignacion.setText(Integer.toString(cantidades[20]));

        }
        /*contadorestotales.add(new contadorestotales(cantidades[0], cantidades[1], cantidades[2], cantidades[3], cantidades[4], cantidades[5], cantidades[6], cantidades[7], cantidades[8], cantidades[9], cantidades[10], cantidades[11], cantidades[12], cantidades[13], cantidades[14], cantidades[15], cantidades[16], cantidades[17], cantidades[18], cantidades[19], cantidades[20]));
        for (int j = 0; j < 21; j++) {
            cantidades[j]=0;
        }*/
        try {
            ReadCellDataSin();
        } catch (IOException ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
        }

        i = 0;

        texto = jTextArea1.getText();

    }

    public void compilarSin(Sheet ss) {
        String tipoError2 = "Sintaxis";
       
        String descError = "";
        int columna;
        tokeen2 = (LinkedList<Token>) tokeen.clone();

        int n = 0;

        pilaproducciones.push(pes);
        pilaproducciones.push(201);//<------ numero de bloque de tu PROGRAM
        tokeen2.addLast(new Token(pes, "$", 0));

        int contador = 0;

        do {
            //System.out.println("Vez " + contador);
            contador++;
            //System.out.println("token= " + tokeen2.getFirst().est + "\npilaproducciones= " + pilaproducciones.peek());

            switch (pilaproducciones.peek()) {
                case 201:pro++;
                    break;
                case 206:con++;
                    break;
                case 207:conen++;
                    break;
                case 208:ltr++;
                    break;
                case 212:or++;
                    break;
                case 214:arr++;
                    break;
                case 218:and++;
                    break;
                case 220:not++;
                    break;
                case 222:expas++;
                    break;
                case 224:orlog++;
                    break;
                case 226:xorlog++;
                    break;
                case 228:andlog++;
                    break;
                case 230:opbit++;
                    break;
                case 232:sep++;
                    break;
                case 234:terp++;
                    break;
                case 236:elev++;
                    break;
                case 238:fac++;
                    break;
                case 243:asign++;
                    break;
                case 244:fl++;
                    break;
                case 245:funciones++;
                    break;
                case 246:estatutos++;
                    break;

            }

            if (tokeen2.getFirst().est == pilaproducciones.peek() && pilaproducciones.peek() < 0) {

                if (pilaproducciones.pop() == pes) {
                    if (tokeen2.removeFirst().est == pes) {
                        break;
                    }
                } else {

                    tokeen2.removeFirst();

                }
            } else {

                if (tokeen2.getFirst().est != pilaproducciones.peek() && pilaproducciones.peek() < 0) {

                    if (n <= 124) {
                        descError = "FUERZA BRUTA";
                    }

                    errorxd.add(new LE(descError, tokeen2.getFirst().valor, tipoError2, n, tokeen2.getFirst().linea));
                    //por aqui(? IDK
                    break;
                } else {

                    if (pilaproducciones.peek() >= 200 && tokeen2.getFirst().est < 0) {

                        fila = pilaproducciones.peek() - 201;

                        columna = Math.abs(tokeen2.getFirst().est);

                        //if(tokeen2.getFirst().est==-58){KKin=true;//System.out.println("ENTROOOO LPMMMMMMM");}
                        ncelSintaxis = ss.getCell(columna - 1, fila);

                        //System.out.println("columna" + columna);
                        //System.out.println("fila" + fila);
                        if (ncelSintaxis.getType() == CellType.NUMBER) {
                            n = Integer.parseInt(ncelSintaxis.getContents());

                        
                            //System.out.println("n:" + n);

                        }

                        if (prod(n)) {
                            pilaproducciones.pop();
                            cargarProd(n);

                        } else {

                            if (epsilon(n)) {
                                pilaproducciones.pop();
                            } else {
                                if (error(n)) {
                                    //System.out.println("ERROR");
                                    //generarError(tokeen2.getFirst(), n);
                                    cantidades[0]++;
                                    switch (n) {
                                        case 600:
                                            descError = "se puso de mas";
                                            break;
                                        case 601:
                                            descError = "Se espera def id {";
                                            break;
                                        case 602:
                                            descError = "Se espera def id ";
                                            break;
                                        case 603:
                                            descError = "se espera ;";
                                            break;
                                        case 604:
                                            descError = "se espera id";
                                            break;
                                        case 605:
                                            descError = "se espera ,";
                                            break;
                                        case 606:
                                            descError = "Se espera decimal binario hexadecimal octal constcadena constflotante constcompleja constcaracter ( [ { none true false range";
                                            break;
                                        case 607:
                                            descError = "se espera decimal binario hexadecimal octal";
                                            break;
                                        case 608:
                                            descError = "se espera ( [ range {";
                                            break;
                                        case 609:
                                            descError = "se espera :";
                                            break;
                                        case 610:
                                            descError = "se espera constflotante constcadena constcaracter decimal binario hexadecimal octal constcompleja true false ( [ range { none id ++ -- findall replace len sample choice random randrange mean median variance sum";
                                            break;
                                        case 611:
                                            descError = "se espera ||";
                                            break;
                                        case 612:
                                            descError = "se espera [";
                                            break;
                                        case 613:
                                            descError = "se espera -";
                                            break;
                                        case 614:
                                            descError = "se espera && ##";
                                            break;
                                        case 615:
                                            descError = "se espera !";
                                            break;
                                        case 616:
                                            descError = "se espera < <= == != >= > is isnot in innot";
                                            break;
                                        case 617:
                                            descError = "se espera |";
                                            break;
                                        case 618:
                                            descError = "se espera ^";
                                            break;
                                        case 619:
                                            descError = "se espera &";
                                            break;
                                        case 620:
                                            descError = "se espera << >>";
                                            break;
                                        case 621:
                                            descError = "se espera -  +";
                                            break;
                                        case 622:
                                            descError = "se espera * / // %";
                                            break;
                                        case 623:
                                            descError = "se espera **";
                                            break;
                                        case 624:
                                            descError = "se espera ++ -- [ = += -= *= **= /= //= %= .";
                                            break;
                                        case 625:
                                            descError = "se espera =  +=  /=  *=  -=  //=  **=  %=";
                                            break;
                                        case 626:
                                            descError = "Se espera id decimal binario hexadecimal octal constcadena constflotante constcompleja constcaracter ++ -- ( [ { none true false range input findall replace len sample choice random randrange mean median variance sum";
                                            break;
                                        case 627:
                                            descError = "se espera constcadena";
                                            break;
                                        case 628:
                                            descError = "se espera sort reverse count index append extend pop remove insert";
                                            break;
                                        case 629:
                                            descError = "se espera findall replace len sample choice random randrange mean median variance sum";
                                            break;
                                        case 630:
                                            descError = "se espera id decimal binario hexadecimal octal constcadena constflotante constcompleja constcaracter ++ -- ( [ { break continue for if print return while none true false range findall replace len sample choice random randrange mean median variance sum println";
                                            break;
                                        case 631:
                                            descError = "se espera elif else ";
                                            break;
                                    }

                                    errorxd.add(new LE(descError, tokeen2.getFirst().valor, tipoError2, n, tokeen2.getFirst().linea));

                                    if (tokeen2.getFirst().est == pes) {
                                        tokeen2.removeFirst();
                                        break;
                                    } else {
                                        tokeen2.removeFirst();
                                    }

                                }
                            }
                        }
                    }
                }
            }
            
        } while (true);
            labelError.setText(Integer.toString(cantidades[0]));
            LabelID.setText(Integer.toString(cantidades[1]));
            labelComen.setText(Integer.toString(cantidades[2]));
            labelPR.setText(Integer.toString(cantidades[3]));
            labelCEDec.setText(Integer.toString(cantidades[4]));
            labelcebin.setText(Integer.toString(cantidades[5]));
            labelCEHex.setText(Integer.toString(cantidades[6]));
            labelCEOct.setText(Integer.toString(cantidades[7]));
            labelCtexto.setText(Integer.toString(cantidades[8]));
            labelAgrupacion.setText(Integer.toString(cantidades[19]));
            lablelCFloat.setText(Integer.toString(cantidades[9]));
            labelCNComp.setText(Integer.toString(cantidades[10]));
            labelCcar.setText(Integer.toString(cantidades[11]));
            labelAritmeticos.setText(Integer.toString(cantidades[12]));
            labelMonogamo.setText(Integer.toString(cantidades[13]));
            labelLogico.setText(Integer.toString(cantidades[14]));
            labelbit.setText(Integer.toString(cantidades[16]));
            lablePuntuacion.setText(Integer.toString(cantidades[18]));
            labelidentidad.setText(Integer.toString(cantidades[17]));
            labelRelacionales.setText(Integer.toString(cantidades[15]));
            labelAsignacion.setText(Integer.toString(cantidades[20]));
        contadorestotales.add(new contadorestotales(cantidades[0], cantidades[1], cantidades[2], cantidades[3], cantidades[4], cantidades[5], cantidades[6], cantidades[7], cantidades[8], cantidades[9], cantidades[10], cantidades[11], cantidades[12], cantidades[13], cantidades[14], cantidades[15], cantidades[16], cantidades[17], cantidades[18], cantidades[19], cantidades[20]));
        for (int j = 0; j < 21; j++) {
            cantidades[j]=0;
        }
        contadoresSin.add(new ContadoresSintaxis(pro,con,conen,ltr,terp,elev,sep,fac,not,or,opbit,and,andlog,orlog,xorlog,estatutos,asign,fl,arr,funciones,expas));
        pro=0;con=0;conen=0;ltr=0;terp=0;elev=0;sep=0;fac=0;not=0;or=0;opbit=0;and=0;andlog=0;orlog=0;xorlog=0;estatutos=0;asign=0;fl=0;arr=0;funciones=0;expas=0;
    

    }

    public boolean error(int n) {
        return n > 500;
    }

    public boolean epsilon(int n) {
        return n == 124;//124 es mi numero de epsilon
         
    }

    public boolean prod(int i) {
        return i != 124 && i < 500;//qui pongo el numero de epsilon 
                                    // y que i sea menor a 500 para que agarre solo producciones validas
    }

    public void cargarProd(int n) {
        //System.out.println("CARGAPROD: " + n);
        for (int i = 0; i < listap[n - 1].length; i++) {//n-1
            pilaproducciones.push(listap[n - 1][i]);//n-1
        }
        for (Integer p : pilaproducciones) {
            //System.out.println("ps = " + p);
        }
    }

    public void abrir() {

        String aux = "";
        String texto = "", txt = "";
        try {
            JFileChooser file = new JFileChooser();
            file.showOpenDialog(null);
            File abre = file.getSelectedFile();

            if (abre != null) {
                FileReader archivos = new FileReader(abre);
                BufferedReader lee = new BufferedReader(archivos);
                while ((aux = lee.readLine()) != null) {
                    texto += aux + "\n";
                }
                lee.close();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex + ""
                    + "Error ¡¡¡Corre!!!!",
                    "!!!", JOptionPane.WARNING_MESSAGE);
        }
        int j = 0;

        while (j < texto.length()) {

            char car = texto.charAt(j);
            txt += car;
            j++;

            jTextArea1.setText(txt);
        }

    }

    JFileChooser cargar;

    public String getArchivo(String ruta) {
        FileReader fr = null;
        BufferedReader br = null;
        //Cadena de texto donde se guardara el contenido del archivo
        String contenido = "";
        try {
            //ruta puede ser de tipo String o tipo File
            fr = new FileReader(ruta);
            br = new BufferedReader(fr);

            String linea;
            //Obtenemos el contenido del archivo linea por linea
            while ((linea = br.readLine()) != null) {
                contenido += linea + "\n";
            }

        } catch (Exception e) {
        } //finally se utiliza para que si todo ocurre correctamente o si ocurre 
        //algun error se cierre el archivo que anteriormente abrimos
        finally {
            try {
                br.close();
            } catch (Exception ex) {
            }
        }
        return contenido;
    }

//////////////////////////////////////////////////////////////////////////////////
    private void botonCargarActionPerformed(java.awt.event.ActionEvent evt) {

    }

    public void mostrar() {

        try {

            tablaerrores.setModel(dtm2);
            tablatokens.setModel(dtm);
            ContadoresTB.setModel(dtm3);
            jTablecontadorestotales.setModel(dtm4);
            jTablecontadoresSin.setModel(dtm5);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error mostrar" + ex);
        }

    }

    public void paint(Graphics g) {
        super.paint(g);
        int start
                = jTextArea1.viewToModel(jScrollPane1.getViewport().getViewPosition());
        int end
                = jTextArea1.
                        viewToModel(new Point(jScrollPane1.getViewport().getViewPosition().x
                                + jTextArea1.getWidth(),
                                jScrollPane1.getViewport().getViewPosition().y
                                + jTextArea1.getHeight()));
        Document doc = jTextArea1.getDocument();
        int startline = doc.getDefaultRootElement().getElementIndex(start);
        int endline = doc.getDefaultRootElement().getElementIndex(end);
        int fontHeight = g.getFontMetrics(jTextArea1.getFont()).getHeight();	// font
        for (int line = startline, y = 0; line <= endline;
                line++, y += fontHeight) {
            g.drawString(Integer.toString(line), 0, y);
        }

    }


    private void btnsubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsubirActionPerformed

        String txt = "";
        cargar = new JFileChooser();
        cargar.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int selection = cargar.showOpenDialog(this);
        if (selection == JFileChooser.APPROVE_OPTION) {
            File f = cargar.getSelectedFile();
            try {
                String nombre = f.getName();
                String path = f.getAbsolutePath();
                String contenido = getArchivo(path);

                this.setTitle(nombre);

                jTextArea1.setText(contenido);

            } catch (Exception exp) {
            }
        }


    }//GEN-LAST:event_btnsubirActionPerformed

    private void GenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarActionPerformed
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet hoja = workbook.createSheet("TOKENS");

        try {

            XSSFRow fila = hoja.createRow(0);
            fila.createCell(0).setCellValue("Estado");
            fila.createCell(1).setCellValue("Lexema");
            fila.createCell(2).setCellValue("linea");
           
            XSSFRow filas;
            Rectangle rect;
            for (int j = 0; j < tablatokens.getRowCount(); j++) {
                rect = tablatokens.getCellRect(1, 0, true);
                try {
                    tablatokens.scrollRectToVisible(rect);
                } catch (java.lang.ClassCastException e) {
                }
                tablatokens.setRowSelectionInterval(j, j);
                filas = hoja.createRow((j + 1));
                filas.createCell(0).setCellValue(tablatokens.getValueAt(j, 0).toString());
                filas.createCell(1).setCellValue(tablatokens.getValueAt(j, 1).toString());
                filas.createCell(2).setCellValue(tablatokens.getValueAt(j, 2).toString());
                
            }
            /*workbook.write(new FileOutputStream(new File("C:\\Users\\said\\Desktop\\RESULTADOS EXCEL\\Tokens.xlsx")));
          Desktop.getDesktop().open(new File("C:\\Users\\said\\Desktop\\RESULTADOS EXCEL\\Tokens.xlsx"));*/
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
        XSSFSheet hoja2 = workbook.createSheet("Errores");
        try {

            XSSFRow fila = hoja2.createRow(0);
            fila.createCell(0).setCellValue("Token");
            fila.createCell(1).setCellValue("Descripcion");
            fila.createCell(2).setCellValue("lexema");
            fila.createCell(3).setCellValue("tipo de error");
            fila.createCell(4).setCellValue("linea");
            XSSFRow filas;
            Rectangle rect;
            for (int j = 0; j < tablaerrores.getRowCount(); j++) {
                rect = tablaerrores.getCellRect(1, 0, true);
                try {
                    tablaerrores.scrollRectToVisible(rect);
                } catch (java.lang.ClassCastException e) {
                }
                tablaerrores.setRowSelectionInterval(j, j);
                filas = hoja2.createRow((j + 1));
                filas.createCell(0).setCellValue(tablaerrores.getValueAt(j, 0).toString());
                filas.createCell(1).setCellValue(tablaerrores.getValueAt(j, 1).toString());
                filas.createCell(2).setCellValue(tablaerrores.getValueAt(j, 2).toString());
                filas.createCell(3).setCellValue(tablaerrores.getValueAt(j, 3).toString());
                filas.createCell(4).setCellValue(tablaerrores.getValueAt(j, 4).toString());

            }
            /* workbook.write(new FileOutputStream(new File("C:\\Users\\said\\Desktop\\RESULTADOS EXCEL\\Errores.xlsx")));
          Desktop.getDesktop().open(new File("C:\\Users\\said\\Desktop\\RESULTADOS EXCEL\\Errores.xlsx"));*/
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
        XSSFSheet hoja3 = workbook.createSheet("CONTADORES");

        try {

            XSSFRow fila = hoja3.createRow(0);

            fila.createCell(0).setCellValue("Errores");
            fila.createCell(1).setCellValue("identificadores");
            fila.createCell(2).setCellValue("comentarios");
            fila.createCell(3).setCellValue("palabras reservada");
            fila.createCell(4).setCellValue("CE-Dec");
            fila.createCell(5).setCellValue("CE-Bin");
            fila.createCell(6).setCellValue("CE-HEX");
            fila.createCell(7).setCellValue("CE-OCT");
            fila.createCell(8).setCellValue("CTexto");
            fila.createCell(9).setCellValue("CFloat");
            fila.createCell(10).setCellValue("CNComp");
            fila.createCell(11).setCellValue("Ccar");
            fila.createCell(12).setCellValue("Aritmeticos");
            fila.createCell(13).setCellValue("monogamo");
            fila.createCell(14).setCellValue("logico");
            fila.createCell(15).setCellValue("Relacionales");
            fila.createCell(16).setCellValue("bit");
            fila.createCell(17).setCellValue("identidad");
            fila.createCell(18).setCellValue("puntuacion");
            fila.createCell(19).setCellValue("agrupacion");
            fila.createCell(20).setCellValue("Asignacion");
            XSSFRow filas;
            Rectangle rect;
            for (int j = 0; j < jTablecontadorestotales.getRowCount(); j++) {
                rect = jTablecontadorestotales.getCellRect(1, 0, true);
                try {
                    jTablecontadorestotales.scrollRectToVisible(rect);
                } catch (java.lang.ClassCastException e) {
                }
                jTablecontadorestotales.setRowSelectionInterval(j, j);
                filas = hoja3.createRow((j + 1));
                filas.createCell(0).setCellValue(jTablecontadorestotales.getValueAt(j, 0).toString());
                filas.createCell(1).setCellValue(jTablecontadorestotales.getValueAt(j, 1).toString());
                filas.createCell(2).setCellValue(jTablecontadorestotales.getValueAt(j, 2).toString());
                filas.createCell(3).setCellValue(jTablecontadorestotales.getValueAt(j, 3).toString());
                filas.createCell(4).setCellValue(jTablecontadorestotales.getValueAt(j, 4).toString());
                filas.createCell(5).setCellValue(jTablecontadorestotales.getValueAt(j, 5).toString());
                filas.createCell(6).setCellValue(jTablecontadorestotales.getValueAt(j, 6).toString());
                filas.createCell(7).setCellValue(jTablecontadorestotales.getValueAt(j, 7).toString());
                filas.createCell(8).setCellValue(jTablecontadorestotales.getValueAt(j, 8).toString());
                filas.createCell(9).setCellValue(jTablecontadorestotales.getValueAt(j, 9).toString());
                filas.createCell(10).setCellValue(jTablecontadorestotales.getValueAt(j, 10).toString());
                filas.createCell(11).setCellValue(jTablecontadorestotales.getValueAt(j, 11).toString());
                filas.createCell(12).setCellValue(jTablecontadorestotales.getValueAt(j, 12).toString());
                filas.createCell(13).setCellValue(jTablecontadorestotales.getValueAt(j, 13).toString());
                filas.createCell(14).setCellValue(jTablecontadorestotales.getValueAt(j, 14).toString());
                filas.createCell(15).setCellValue(jTablecontadorestotales.getValueAt(j, 15).toString());
                filas.createCell(16).setCellValue(jTablecontadorestotales.getValueAt(j, 16).toString());
                filas.createCell(17).setCellValue(jTablecontadorestotales.getValueAt(j, 17).toString());
                filas.createCell(18).setCellValue(jTablecontadorestotales.getValueAt(j, 18).toString());
                filas.createCell(19).setCellValue(jTablecontadorestotales.getValueAt(j, 19).toString());
                filas.createCell(20).setCellValue(jTablecontadorestotales.getValueAt(j, 20).toString());

            }

            /*try {
        workbook.write(new FileOutputStream(new File("C:\\Users\\said\\Desktop\\RESULTADOS EXCEL\\Resultados.xlsx")));
        Desktop.getDesktop().open(new File("C:\\Users\\said\\Desktop\\RESULTADOS EXCEL\\Resultados.xlsx"));
    } catch (FileNotFoundException ex) {
        Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
    }*/
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }

        XSSFSheet hoja4 = workbook.createSheet("TABLA DE CONTADORES");

        try {

            XSSFRow fila = hoja4.createRow(0);
            fila.createCell(0).setCellValue("Linea");
            fila.createCell(1).setCellValue("Errores");
            fila.createCell(2).setCellValue("identificadores");
            fila.createCell(3).setCellValue("comentarios");
            fila.createCell(4).setCellValue("palabras reservada");
            fila.createCell(5).setCellValue("CE-Dec");
            fila.createCell(6).setCellValue("CE-Bin");
            fila.createCell(7).setCellValue("CE-HEX");
            fila.createCell(8).setCellValue("CE-OCT");
            fila.createCell(9).setCellValue("CTexto");
            fila.createCell(10).setCellValue("CFloat");
            fila.createCell(11).setCellValue("CNComp");
            fila.createCell(12).setCellValue("Ccar");
            fila.createCell(13).setCellValue("Aritmeticos");
            fila.createCell(14).setCellValue("monogamo");
            fila.createCell(15).setCellValue("logico");
            fila.createCell(16).setCellValue("Relacionales");
            fila.createCell(17).setCellValue("bit");
            fila.createCell(18).setCellValue("identidad");
            fila.createCell(19).setCellValue("puntuacion");
            fila.createCell(20).setCellValue("agrupacion");
            fila.createCell(21).setCellValue("Asignacion");
            XSSFRow filas;
            Rectangle rect;
            for (int j = 0; j < ContadoresTB.getRowCount(); j++) {
                rect = ContadoresTB.getCellRect(1, 0, true);
                try {
                    ContadoresTB.scrollRectToVisible(rect);
                } catch (java.lang.ClassCastException e) {
                }
                ContadoresTB.setRowSelectionInterval(j, j);
                filas = hoja4.createRow((j + 1));
                filas.createCell(0).setCellValue(ContadoresTB.getValueAt(j, 0).toString());
                filas.createCell(1).setCellValue(ContadoresTB.getValueAt(j, 1).toString());
                filas.createCell(2).setCellValue(ContadoresTB.getValueAt(j, 2).toString());
                filas.createCell(3).setCellValue(ContadoresTB.getValueAt(j, 3).toString());
                filas.createCell(4).setCellValue(ContadoresTB.getValueAt(j, 4).toString());
                filas.createCell(5).setCellValue(ContadoresTB.getValueAt(j, 5).toString());
                filas.createCell(6).setCellValue(ContadoresTB.getValueAt(j, 6).toString());
                filas.createCell(7).setCellValue(ContadoresTB.getValueAt(j, 7).toString());
                filas.createCell(8).setCellValue(ContadoresTB.getValueAt(j, 8).toString());
                filas.createCell(9).setCellValue(ContadoresTB.getValueAt(j, 9).toString());
                filas.createCell(10).setCellValue(ContadoresTB.getValueAt(j, 10).toString());
                filas.createCell(11).setCellValue(ContadoresTB.getValueAt(j, 11).toString());
                filas.createCell(12).setCellValue(ContadoresTB.getValueAt(j, 12).toString());
                filas.createCell(13).setCellValue(ContadoresTB.getValueAt(j, 13).toString());
                filas.createCell(14).setCellValue(ContadoresTB.getValueAt(j, 14).toString());
                filas.createCell(15).setCellValue(ContadoresTB.getValueAt(j, 15).toString());
                filas.createCell(16).setCellValue(ContadoresTB.getValueAt(j, 16).toString());
                filas.createCell(17).setCellValue(ContadoresTB.getValueAt(j, 17).toString());
                filas.createCell(18).setCellValue(ContadoresTB.getValueAt(j, 18).toString());
                filas.createCell(19).setCellValue(ContadoresTB.getValueAt(j, 19).toString());
                filas.createCell(20).setCellValue(ContadoresTB.getValueAt(j, 20).toString());
                filas.createCell(21).setCellValue(ContadoresTB.getValueAt(j, 21).toString());

            }

            /*try {
                workbook.write(new FileOutputStream(new File("C:\\Users\\said\\Desktop\\RESULTADOS EXCEL\\Resultados.xlsx")));
                Desktop.getDesktop().open(new File("C:\\Users\\said\\Desktop\\RESULTADOS EXCEL\\Resultados.xlsx"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
        XSSFSheet hoja5 = workbook.createSheet("SINTAXIS");

        try {

            XSSFRow fila = hoja5.createRow(0);
            fila.createCell(0).setCellValue("PROGRAM");
            fila.createCell(1).setCellValue("CONSTANTE");
            fila.createCell(2).setCellValue("CONST ENTERO");
            fila.createCell(3).setCellValue("LIST TUP RANGOS");
            fila.createCell(4).setCellValue("TERMINO PASCAL");
            fila.createCell(5).setCellValue("ELEVACION");
            fila.createCell(6).setCellValue("SIMPLE EXP-PAS");
            fila.createCell(7).setCellValue("FACTOR");
            fila.createCell(8).setCellValue("NOT");
            fila.createCell(9).setCellValue("OR");
            fila.createCell(10).setCellValue("OPBIT");
            fila.createCell(11).setCellValue("AND");
            fila.createCell(12).setCellValue("ANDLOG");
            fila.createCell(13).setCellValue("ORLOG");
            fila.createCell(14).setCellValue("XORLOG");
            fila.createCell(15).setCellValue("EST");
            fila.createCell(16).setCellValue("ASIGN");
            fila.createCell(17).setCellValue("FUNLIST");
            fila.createCell(18).setCellValue("ARR");
            fila.createCell(19).setCellValue("FUNCIONES");
            fila.createCell(20).setCellValue("EXP-PAS");
            
            XSSFRow filas;
            Rectangle rect;
            for (int j = 0; j < jTablecontadoresSin.getRowCount(); j++) {
                rect = jTablecontadoresSin.getCellRect(1, 0, true);
                try {
                    jTablecontadoresSin.scrollRectToVisible(rect);
                } catch (java.lang.ClassCastException e) {
                }
                jTablecontadoresSin.setRowSelectionInterval(j, j);
                filas = hoja5.createRow((j + 1));
                filas.createCell(0).setCellValue(jTablecontadoresSin.getValueAt(j, 0).toString());
                filas.createCell(1).setCellValue(jTablecontadoresSin.getValueAt(j, 1).toString());
                filas.createCell(2).setCellValue(jTablecontadoresSin.getValueAt(j, 2).toString());
                filas.createCell(3).setCellValue(jTablecontadoresSin.getValueAt(j, 3).toString());
                filas.createCell(4).setCellValue(jTablecontadoresSin.getValueAt(j, 4).toString());
                filas.createCell(5).setCellValue(jTablecontadoresSin.getValueAt(j, 5).toString());
                filas.createCell(6).setCellValue(jTablecontadoresSin.getValueAt(j, 6).toString());
                filas.createCell(7).setCellValue(jTablecontadoresSin.getValueAt(j, 7).toString());
                filas.createCell(8).setCellValue(jTablecontadoresSin.getValueAt(j, 8).toString());
                filas.createCell(9).setCellValue(jTablecontadoresSin.getValueAt(j, 9).toString());
                filas.createCell(10).setCellValue(jTablecontadoresSin.getValueAt(j, 10).toString());
                filas.createCell(11).setCellValue(jTablecontadoresSin.getValueAt(j, 11).toString());
                filas.createCell(12).setCellValue(jTablecontadoresSin.getValueAt(j, 12).toString());
                filas.createCell(13).setCellValue(jTablecontadoresSin.getValueAt(j, 13).toString());
                filas.createCell(14).setCellValue(jTablecontadoresSin.getValueAt(j, 14).toString());
                filas.createCell(15).setCellValue(jTablecontadoresSin.getValueAt(j, 15).toString());
                filas.createCell(16).setCellValue(jTablecontadoresSin.getValueAt(j, 16).toString());
                filas.createCell(17).setCellValue(jTablecontadoresSin.getValueAt(j, 17).toString());
                filas.createCell(18).setCellValue(jTablecontadoresSin.getValueAt(j, 18).toString());
                filas.createCell(19).setCellValue(jTablecontadoresSin.getValueAt(j, 19).toString());
                filas.createCell(20).setCellValue(jTablecontadoresSin.getValueAt(j, 20).toString());
                

            }

            try {
                workbook.write(new FileOutputStream(new File("C:\\Users\\Computer\\Desktop\\Resultados en Excel compi\\Resultados.xlsx")));
                Desktop.getDesktop().open(new File("C:\\Users\\Computer\\Desktop\\Resultados en Excel compi\\Resultados.xlsx"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }//GEN-LAST:event_GenerarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lexico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lexico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lexico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lexico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lexico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ContadoresTB;
    private javax.swing.JButton Generar;
    private javax.swing.JLabel LabelID;
    private javax.swing.JButton btnanalizar;
    private javax.swing.JButton btnsubir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTablecontadoresSin;
    private javax.swing.JTable jTablecontadorestotales;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelAgrupacion;
    private javax.swing.JLabel labelAritmeticos;
    private javax.swing.JLabel labelAsignacion;
    private javax.swing.JLabel labelCEDec;
    private javax.swing.JLabel labelCEHex;
    private javax.swing.JLabel labelCEOct;
    private javax.swing.JLabel labelCNComp;
    private javax.swing.JLabel labelCcar;
    private javax.swing.JLabel labelComen;
    private javax.swing.JLabel labelCtexto;
    private javax.swing.JLabel labelError;
    private javax.swing.JLabel labelLogico;
    private javax.swing.JLabel labelMonogamo;
    private javax.swing.JLabel labelPR;
    private javax.swing.JLabel labelRelacionales;
    private javax.swing.JLabel labelbit;
    private javax.swing.JLabel labelcebin;
    private javax.swing.JLabel labelidentidad;
    private javax.swing.JLabel lablePuntuacion;
    private javax.swing.JLabel lablelCFloat;
    private javax.swing.JTable tablaerrores;
    private javax.swing.JTable tablatokens;
    // End of variables declaration//GEN-END:variables
    char carAnterior;

    private int ContarC(char car) {

        int colum = 0;

        if (car == 'a') {
            colum = 0;
        } else if (car == 'b') {
            colum = 1;
        } else if (car >= 'c' & car <= 'd') {
            colum = 2;
        } else if (car == 'e') {
            colum = 3;
        } else if (car == 'f') {
            colum = 4;
        } else if (car >= 'g' & car <= 'i') {
            colum = 5;
        } else if (car == 'j') {
            colum = 6;
        } else if (car >= 'k' & car <= 'w' || car == 'ñ') {
            colum = 7;
        } else if (car == 'x') {
            colum = 8;
        } else if (car >= 'y' & car <= 'z') {
            colum = 9;
        } else if (car >= 'A' & car <= 'D') {
            colum = 10;
        } else if (car == 'E') {
            colum = 11;
        } else if (car == 'F') {
            colum = 12;
        } else if ((int) car == '_') {
            colum = 13;
        } else if (car == '#') {
            colum = 14;
        } else if (car == '\n') {
            colum = 15;
        } else if (car == '\'') {
            colum = 16;
        } else if (car == '0') {
            colum = 17;
        } else if (car == '1') {
            colum = 18;
        } else if (car >= '2' & car <= '7') {
            colum = 19;
        } else if (car >= '8' & car <= '9') {
            colum = 20;
        } else if (car == '"') {
            colum = 21;
        } else if (car == '+') {
            colum = 22;
        } else if (car == '-') {
            colum = 23;
        } else if (car == '*') {
            colum = 24;
        } else if (car == '/') {
            colum = 25;
        } else if (car == '%') {
            colum = 26;
        } else if (car == '&') {
            colum = 27;
        } else if (car == '|') {
            colum = 28;
        } else if (car == '<') {
            colum = 29;
        } else if (car == '=') {
            colum = 30;
        } else if (car == '>') {
            colum = 31;
        } else if (car == '!') {
            colum = 32;
        } else if (car == '^') {
            colum = 33;
        } else if (car == ';') {
            colum = 34;
        } else if (car == ',') {
            colum = 35;
        } else if (car == '(') {
            colum = 36;
        } else if (car == ')') {
            colum = 37;
        } else if (car == '[') {
            colum = 38;
        } else if (car == ']') {
            colum = 39;
        } else if (car == '{') {
            colum = 40;
        } else if (car == '}') {
            colum = 41;
        } else if (car == '.') {
            colum = 42;
        } else if (car == ' ' || car == '\t' || car == '\r') {
            colum = 43;
        } else if (car >= 'G' & car <= 'Z' || car == 'Ñ') {
            colum = 44;
        } else if (car == ':') {
            colum = 46;
        } else {
            colum = 45;
        }

        carAnterior = car;

        return colum;

    }
    int[] cantidades = new int[21];

    public void sumarContadores(int estado) {
        if (estado >= 500 && estado <= 640) {
            ++cantidades[0];
        }
        if (estado == -1) {
            ++cantidades[1];
            identificador++;
        }
        if(estado==-52||estado==-53){
        ++cantidades[17];
        }
        if (estado <= -54 && estado >= -93||estado==-95) {
            palabrasreservadas++;
            ++cantidades[3];
        }
        if (estado == -3) {
            cedec++;
            ++cantidades[4];
        }
        if (estado == -4) {
            cebin++;
            ++cantidades[5];
        }
        if (estado == -5) {
            cehex++;
            ++cantidades[6];
        }
        if (estado == -6) {
            ceoct++;
            ++cantidades[7];
        }
        if (estado == -7) {
            ctexto++;
            ++cantidades[8];
        }
        if (estado == -8) {
            cfloat++;
            ++cantidades[9];
        }
        if (estado == -9) {
            cncomp++;
            ++cantidades[10];
        }
        if (estado == -10) {
            ccar++;
            ++cantidades[11];
        }
        if (estado <= -11 && estado >= -17) {
            aritmeticos++;
            ++cantidades[12];
        }
        if (estado <= -18 && estado >= -19) {
            monogamo++;
            ++cantidades[13];
        }
        if (estado <= -20 && estado >= -22||estado==-2) {
            logico++;
            ++cantidades[14];
        }
        if (estado <= -23 && estado >= -28) {
            relacionales++;
            ++cantidades[15];
        }
        if (estado <= -29 && estado >= -33) {
            bit++;
            ++cantidades[16];
        }
        
        if (estado <= -34 && estado >= -35||estado == -50||estado==-51) {
            puntuacion++;
            ++cantidades[18];
        }
        if (estado <= -36 && estado >= -41) {
            agrupacion++;
            ++cantidades[19];
        }
        if (estado <= -42 && estado >= -49) {
            asignacion++;
            ++cantidades[20];
        }

    }

  
}
