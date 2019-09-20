package Vista;

import Compilador.Compilador;
import Compilador.Contadores;
import Compilador.ContadoresSintaxis;
import Compilador.LE;
import Compilador.TextLineNumber;
import Compilador.Token;
import Compilador.contadorestotales;
import SQL.TablaSimbolos;
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
import java.util.ArrayList;
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
import jxl.read.biff.BiffException;

public class Lexico extends javax.swing.JFrame {

    Compilador compi;
    boolean ekizde = false;
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
    DefaultTableModel dtm = new DefaultTableModel();
    DefaultTableModel dtm2 = new DefaultTableModel();
    DefaultTableModel dtm3 = new DefaultTableModel();
    DefaultTableModel dtm4 = new DefaultTableModel();
    DefaultTableModel dtm5 = new DefaultTableModel();
    int vacio = 124;
    int B = 0, N = 0;
    Stack<Integer> pilaproducciones = new Stack<Integer>();
    Stack<String> desmadrestack = new Stack<String>();
    Stack<Integer> desmadreestado = new Stack<Integer>();
    String[] ConCombo[];
    Graphics g;
    Image bgImage;

    public Lexico() {
        initComponents();

        setLocationRelativeTo(this);

        jPanel6.setOpaque(false);
        compi = new Compilador();
        mostrar();

    }

    int k = 0;

    public void mostrartok(LinkedList<Token> tokeen) {
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

    public void mostrarError(LinkedList<LE> errorxd) {
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

    public void mostrarcontadores(LinkedList<Contadores> contadores) {
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

    public void mostrartotales(LinkedList<contadorestotales> contadorestotales) {
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

    public void mostrarconsin(LinkedList<ContadoresSintaxis> contadoresSin) {
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
        jScrollPane4 = new javax.swing.JScrollPane();
        jTablecontadorestotales = new javax.swing.JTable();
        btnsubir = new javax.swing.JButton();
        btnanalizar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablatokens = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaerrores = new javax.swing.JTable();
        Generar = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTablecontadoresSin = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea(){public void paint(Graphics g){
            super.paint(g);
            Graphics2D g2d =(Graphics2D)g;
            AlphaComposite opaco=AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.5f);
            g2d.setComposite(opaco);

            g2d.setColor(new Color(255,50,50));
            for(int i=0;i<=10000;i++){

                if((i%2!=0)){
                    Rectangle2D linea=new Rectangle2D.Double(0,(16*i),1000,16);
                    g2d.fill(linea);
                }
            }
        }

    };
    jScrollPane7 = new javax.swing.JScrollPane();
    jTablaAmbito = new javax.swing.JTable();
    jPanel6 = new javax.swing.JPanel();
    jScrollPane5 = new javax.swing.JScrollPane();
    ContadoresTB = new javax.swing.JTable();
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
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel25 = new javax.swing.JLabel();
    jLabel26 = new javax.swing.JLabel();
    jLabel27 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setAutoRequestFocus(false);
    setBackground(new java.awt.Color(0, 0, 153));
    setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    setMinimumSize(new java.awt.Dimension(1500, 920));
    setPreferredSize(new java.awt.Dimension(1400, 820));

    jPanel1.setBackground(new java.awt.Color(20, 173, 228));
    jPanel1.setMinimumSize(new java.awt.Dimension(1400, 669));
    jPanel1.setPreferredSize(new java.awt.Dimension(1400, 820));
    jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

    btnsubir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    btnsubir.setText("Subir archivo");
    btnsubir.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnsubirActionPerformed(evt);
        }
    });
    jPanel1.add(btnsubir, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 150, -1));

    btnanalizar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    btnanalizar.setText("Compilar");
    btnanalizar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnanalizarActionPerformed(evt);
        }
    });
    jPanel1.add(btnanalizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 110, -1));

    jScrollPane3.setBorder(null);

    tablatokens.setFont(new java.awt.Font("Dialog", 0, 17)); // NOI18N
    tablatokens.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null},
            {null, null, null},
            {null, null, null},
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

    jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 340, 540, 180));

    jScrollPane2.setBorder(null);

    tablaerrores.setFont(new java.awt.Font("Dialog", 0, 17)); // NOI18N
    tablaerrores.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
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

    jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, 590, 260));

    Generar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    Generar.setText("EXCEL");
    Generar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            GenerarActionPerformed(evt);
        }
    });
    jPanel1.add(Generar, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 20, 110, -1));

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

    jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

    jTextArea1.setBackground(new java.awt.Color(255,255,255));
    jTextArea1.setBorder(null);
    TextLineNumber tlna = new TextLineNumber(jTextArea1);
    jScrollPane1.setRowHeaderView(tlna);
    jTextArea1.setColumns(20);
    jTextArea1.setRows(5);
    jScrollPane1.setViewportView(jTextArea1);

    jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 890, 450));

    jScrollPane7.setBorder(null);
    jScrollPane7.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    jTablaAmbito.setFont(new java.awt.Font("Dialog", 0, 17)); // NOI18N
    jTablaAmbito.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null, null, null}
        },
        new String [] {
            "ID", "Tipo", "Clase", "Ambito", "Tarr", "AmbCreado", "Valor", "noPosicion", "Llave", "ListaPertenece", "Rango", "Avance"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, false, false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jTablaAmbito.setPreferredSize(new java.awt.Dimension(500, 10000));
    jTablaAmbito.getTableHeader().setReorderingAllowed(false);
    jScrollPane7.setViewportView(jTablaAmbito);
    if (jTablaAmbito.getColumnModel().getColumnCount() > 0) {
        jTablaAmbito.getColumnModel().getColumn(0).setPreferredWidth(20);
        jTablaAmbito.getColumnModel().getColumn(1).setPreferredWidth(40);
        jTablaAmbito.getColumnModel().getColumn(2).setPreferredWidth(40);
        jTablaAmbito.getColumnModel().getColumn(3).setPreferredWidth(20);
        jTablaAmbito.getColumnModel().getColumn(4).setPreferredWidth(20);
        jTablaAmbito.getColumnModel().getColumn(5).setPreferredWidth(20);
        jTablaAmbito.getColumnModel().getColumn(6).setPreferredWidth(50);
        jTablaAmbito.getColumnModel().getColumn(7).setPreferredWidth(20);
        jTablaAmbito.getColumnModel().getColumn(8).setPreferredWidth(20);
        jTablaAmbito.getColumnModel().getColumn(9).setPreferredWidth(60);
        jTablaAmbito.getColumnModel().getColumn(10).setPreferredWidth(30);
        jTablaAmbito.getColumnModel().getColumn(11).setPreferredWidth(20);
    }

    jPanel1.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 590, 840, 260));

    jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    jPanel6.setFocusable(false);

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

    jLabel4.setText("Identificadores:");

    LabelID.setText("0");

    jLabel5.setText("COMENTARIOS:");

    labelComen.setText("0");

    jLabel6.setText("PALABRAS RESERVADAS");

    labelPR.setText("0");

    jLabel7.setText("CE-Dec:");

    labelCEDec.setText("0");

    jLabel8.setText("Ctexto:");

    labelCtexto.setText("0");

    jLabel9.setText("CE-Hex:");

    labelCEHex.setText("0");

    jLabel10.setText("CE-Oct");

    labelCEOct.setText("0");

    jLabel12.setText("CFLotante:");

    lablelCFloat.setText("0");

    jLabel14.setText("CNComp");

    labelCNComp.setText("0");

    jLabel16.setText("Ccar:");

    labelCcar.setText("0");

    jLabel18.setText("Aritmeticos:");

    jLabel19.setText("Monogamo:");

    jLabel20.setText("Logico:");

    jLabel21.setText("Bit:");

    jLabel22.setText("puntuacion:");

    labelAritmeticos.setText("0");

    labelMonogamo.setText("0");

    labelLogico.setText("0");

    labelbit.setText("0");

    lablePuntuacion.setText("0");

    jLabel13.setText("Agrupacion:");

    labelAgrupacion.setText("0");

    jLabel11.setText("Asignacion:");

    labelAsignacion.setText("0");

    jLabel15.setText("Relacionales:");

    labelRelacionales.setText("0");

    jLabel17.setText("Error:");

    labelError.setText("0");

    jLabel23.setText("CE-Bin:");

    labelcebin.setText("0");

    jLabel24.setText("Identidad:");

    labelidentidad.setText("0");

    javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
    jPanel6.setLayout(jPanel6Layout);
    jPanel6Layout.setHorizontalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel6Layout.createSequentialGroup()
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addGap(19, 19, 19)
                            .addComponent(labelCNComp)
                            .addGap(154, 154, 154)
                            .addComponent(jLabel18)
                            .addGap(13, 13, 13)
                            .addComponent(labelAritmeticos)
                            .addGap(84, 84, 84)
                            .addComponent(jLabel24))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(13, 13, 13)
                            .addComponent(labelCtexto)
                            .addGap(164, 164, 164)
                            .addComponent(jLabel13)
                            .addGap(12, 12, 12)
                            .addComponent(labelAgrupacion))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(10, 10, 10)
                            .addComponent(labelCEHex)
                            .addGap(164, 164, 164)
                            .addComponent(jLabel11)
                            .addGap(15, 15, 15)
                            .addComponent(labelAsignacion))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(16, 16, 16)
                            .addComponent(labelCEOct)
                            .addGap(164, 164, 164)
                            .addComponent(jLabel4)
                            .addGap(14, 14, 14)
                            .addComponent(LabelID, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addGap(16, 16, 16)
                            .addComponent(lablelCFloat))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(12, 12, 12)
                                    .addComponent(labelComen)
                                    .addGap(124, 124, 124)
                                    .addComponent(jLabel20))
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(11, 11, 11)
                                    .addComponent(labelPR, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(61, 61, 61)
                                    .addComponent(jLabel21))
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(jLabel16)
                                    .addGap(24, 24, 24)
                                    .addComponent(labelCcar)
                                    .addGap(164, 164, 164)
                                    .addComponent(jLabel19)
                                    .addGap(14, 14, 14)
                                    .addComponent(labelMonogamo)))
                            .addGap(87, 87, 87)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel17)
                                .addComponent(jLabel15)
                                .addComponent(jLabel23)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelbit)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(21, 21, 21)
                                .addComponent(labelCEDec)
                                .addGap(154, 154, 154)
                                .addComponent(jLabel22)
                                .addGap(13, 13, 13)
                                .addComponent(lablePuntuacion))
                            .addComponent(labelLogico)))))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelRelacionales, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelcebin))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelidentidad, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(labelError, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addContainerGap())))
    );
    jPanel6Layout.setVerticalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel6Layout.createSequentialGroup()
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(6, 6, 6)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel14)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelCNComp)
                                .addComponent(jLabel18)
                                .addComponent(labelAritmeticos)
                                .addComponent(jLabel24))))
                    .addGap(3, 3, 3)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel16)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelCcar)
                                .addComponent(jLabel19)
                                .addComponent(labelMonogamo)
                                .addComponent(jLabel17)))))
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addComponent(labelidentidad)
                    .addGap(6, 6, 6)
                    .addComponent(labelError)))
            .addGap(3, 3, 3)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel5)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelComen)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(labelLogico))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(labelcebin)))))
            .addGap(3, 3, 3)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel6)
                .addComponent(labelPR)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel21)
                        .addComponent(labelbit, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel15)
                        .addComponent(labelRelacionales))))
            .addGap(3, 3, 3)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel7)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelCEDec)
                        .addComponent(jLabel22)
                        .addComponent(lablePuntuacion))))
            .addGap(3, 3, 3)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel8)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelCtexto)
                        .addComponent(jLabel13)
                        .addComponent(labelAgrupacion))))
            .addGap(3, 3, 3)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel9)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelCEHex)
                        .addComponent(jLabel11)
                        .addComponent(labelAsignacion))))
            .addGap(3, 3, 3)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel10)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelCEOct)
                        .addComponent(jLabel4)
                        .addComponent(LabelID))))
            .addGap(5, 5, 5)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel12)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(1, 1, 1)
                    .addComponent(lablelCFloat)))
            .addGap(8, 8, 8))
    );

    jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 70, 540, 210));

    jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(255, 255, 255));
    jLabel1.setText("COMPILADOR");
    jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

    jLabel2.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(255, 255, 255));
    jLabel2.setText(".jpg");
    jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1430, 30, -1, -1));

    jLabel3.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
    jLabel3.setForeground(new java.awt.Color(255, 255, 255));
    jLabel3.setText("ERRORES");
    jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 550, -1, -1));

    jLabel25.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
    jLabel25.setForeground(new java.awt.Color(255, 255, 255));
    jLabel25.setText("AMBITO");
    jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 550, -1, -1));

    jLabel26.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
    jLabel26.setForeground(new java.awt.Color(255, 255, 255));
    jLabel26.setText("CONTADORES");
    jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 30, -1, -1));

    jLabel27.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
    jLabel27.setForeground(new java.awt.Color(255, 255, 255));
    jLabel27.setText("TOKENS");
    jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 300, -1, -1));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1482, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    public void importarKK(String Datos) {

        try {
            Excel = Workbook.getWorkbook(inputWorkBook);

        } catch (IOException ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        reiniciarJTable(tablatokens);
        reiniciarJTable(tablaerrores);
        reiniciarJTable(ContadoresTB);
        reiniciarJTable(jTablecontadorestotales);
        reiniciarJTable(jTablecontadoresSin);
        compi.limpiarVariables();
        compi.compilarLexico(jTextArea1);
        mostrartok(compi.tokeen);//manda JTable tokens
        mostrarError(compi.errorxd);//manda JTable errores
        mostrarcontadores(compi.contadores);//contadores por linea
        mostrartotales(compi.contadorestotales);//totales
        mostrarconsin(compi.contadoresSin);//contadores sintaxis
        llenarTablaAmbito();
        
    }//GEN-LAST:event_btnanalizarActionPerformed

    private void llenarTablaAmbito(){
        LinkedList<String[]> listaDatos = TablaSimbolos.obtenerDatos();
        DefaultTableModel model = new DefaultTableModel();
            for (int i = 0; i < jTablaAmbito.getColumnCount(); i++) {
                model.addColumn(jTablaAmbito.getColumnName(i));
            }
            //System.out.println(listaDatos.size());
            for (int i = 0; i < listaDatos.size(); i++) {
                //System.out.println(listaDatos.get(i)[0]);
            Object[] fila = {
                listaDatos.get(i)[0],
                listaDatos.get(i)[1],
                listaDatos.get(i)[2],
                listaDatos.get(i)[3],
                listaDatos.get(i)[4],
                listaDatos.get(i)[5],
                listaDatos.get(i)[6],
                listaDatos.get(i)[7],
                listaDatos.get(i)[8],
                listaDatos.get(i)[9],
                listaDatos.get(i)[10],
                listaDatos.get(i)[11]};
            model.addRow(fila);
        }
            jTablaAmbito.setModel(model);
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
        XSSFWorkbook workbook;
        workbook = new XSSFWorkbook();
        // <editor-fold defaultstate="collapsed" desc="TOKENS">
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
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Errores">
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
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="CONTADORES">
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

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="TABLA DE CONTADORES">
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
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="SINTAXIS">
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
//            try {
//                workbook.write(new FileOutputStream(new File("C:\\Users\\samu_\\Desktop\\9no Semestre\\Compi 2\\Excel's Generados\\ResultadosCompi.xlsx")));
//                Desktop.getDesktop().open(new File("C:\\Users\\samu_\\Desktop\\9no Semestre\\Compi 2\\Excel's Generados\\ResultadosCompi.xlsx"));
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="TABLA DE AMBITO">
        XSSFSheet hoja7 = workbook.createSheet("TABLA DE AMBITO");

        try {

            XSSFRow fila = hoja7.createRow(0);
            fila.createCell(0).setCellValue("ID");
            fila.createCell(1).setCellValue("Tipo");
            fila.createCell(2).setCellValue("Clase");
            fila.createCell(3).setCellValue("Ambito");
            fila.createCell(4).setCellValue("Tarr");
            fila.createCell(5).setCellValue("Amb Creado");
            fila.createCell(6).setCellValue("Valor");
            fila.createCell(7).setCellValue("No. Posicion");
            fila.createCell(8).setCellValue("Llave");
            fila.createCell(9).setCellValue("ListaPertenece");
            fila.createCell(10).setCellValue("Rango");
            fila.createCell(11).setCellValue("Avance");
            XSSFRow filas;
            Rectangle rect;
            for (int j = 0; j < jTablaAmbito.getRowCount(); j++) {
                rect = jTablaAmbito.getCellRect(1, 0, true);
                try {
                    jTablaAmbito.scrollRectToVisible(rect);
                } catch (java.lang.ClassCastException e) {
                }
                jTablaAmbito.setRowSelectionInterval(j, j);
                filas = hoja7.createRow((j + 1));
                filas.createCell(0).setCellValue(jTablaAmbito.getValueAt(j, 0).toString());
                filas.createCell(1).setCellValue(jTablaAmbito.getValueAt(j, 1).toString());
                filas.createCell(2).setCellValue(jTablaAmbito.getValueAt(j, 2).toString());
                filas.createCell(3).setCellValue(jTablaAmbito.getValueAt(j, 3).toString());
                filas.createCell(4).setCellValue(jTablaAmbito.getValueAt(j, 4).toString());
                filas.createCell(5).setCellValue(jTablaAmbito.getValueAt(j, 5).toString());
                filas.createCell(6).setCellValue(jTablaAmbito.getValueAt(j, 6).toString());
                filas.createCell(7).setCellValue(jTablaAmbito.getValueAt(j, 7).toString());
                filas.createCell(8).setCellValue(jTablaAmbito.getValueAt(j, 8).toString());
                filas.createCell(9).setCellValue(jTablaAmbito.getValueAt(j, 9).toString());
                filas.createCell(10).setCellValue(jTablaAmbito.getValueAt(j, 10).toString());
                filas.createCell(11).setCellValue(jTablaAmbito.getValueAt(j, 11).toString());

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
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="AMBITO CONTADORES">
        XSSFSheet hoja6 = workbook.createSheet("CONTADORES AMBITO");
        try {
            XSSFRow fila = hoja6.createRow(0);
            fila.createCell(0).setCellValue("Ambito");
            fila.createCell(1).setCellValue("Decimal");
            fila.createCell(2).setCellValue("Binario");
            fila.createCell(3).setCellValue("Octal");
            fila.createCell(4).setCellValue("Hexadecimal");
            fila.createCell(5).setCellValue("Flotante");
            fila.createCell(6).setCellValue("Cadena");
            fila.createCell(7).setCellValue("Caracter");
            fila.createCell(8).setCellValue("Compleja");
            fila.createCell(9).setCellValue("Boolean");
            fila.createCell(10).setCellValue("None");
            fila.createCell(11).setCellValue("Arreglo");
            fila.createCell(12).setCellValue("Tuplas");
            fila.createCell(13).setCellValue("Lista");
            fila.createCell(14).setCellValue("Rango");
            fila.createCell(15).setCellValue("Conjuntos");
            fila.createCell(16).setCellValue("Diccionarios");
            fila.createCell(17).setCellValue("total/Amb");
            XSSFRow filas;
            int totalAmbitos = 0;
            for (int j = 0; j < compi.contadoresAmbito.size(); j++) {
                //System.out.println(compi.contadoresAmbito.get(j).toString());
                fila = hoja6.createRow(j + 1);
                fila.createCell(0).setCellValue(compi.contadoresAmbito.get(j).ambito);
                fila.createCell(1).setCellValue(compi.contadoresAmbito.get(j).decimal);
                totalAmbitos += compi.contadoresAmbito.get(j).decimal;
                fila.createCell(2).setCellValue(compi.contadoresAmbito.get(j).binario);
                totalAmbitos += compi.contadoresAmbito.get(j).binario;
                fila.createCell(3).setCellValue(compi.contadoresAmbito.get(j).octal);
                totalAmbitos += compi.contadoresAmbito.get(j).octal;
                fila.createCell(4).setCellValue(compi.contadoresAmbito.get(j).hexadecimal);
                totalAmbitos += compi.contadoresAmbito.get(j).hexadecimal;
                fila.createCell(5).setCellValue(compi.contadoresAmbito.get(j).flotante);
                totalAmbitos += compi.contadoresAmbito.get(j).flotante;
                fila.createCell(6).setCellValue(compi.contadoresAmbito.get(j).cadena);
                totalAmbitos += compi.contadoresAmbito.get(j).cadena;
                fila.createCell(7).setCellValue(compi.contadoresAmbito.get(j).caracter);
                totalAmbitos += compi.contadoresAmbito.get(j).caracter;
                fila.createCell(8).setCellValue(compi.contadoresAmbito.get(j).compleja);
                totalAmbitos += compi.contadoresAmbito.get(j).compleja;
                fila.createCell(9).setCellValue(compi.contadoresAmbito.get(j).booleana);
                totalAmbitos += compi.contadoresAmbito.get(j).booleana;
                fila.createCell(10).setCellValue(compi.contadoresAmbito.get(j).none);
                totalAmbitos += compi.contadoresAmbito.get(j).none;
                fila.createCell(11).setCellValue(compi.contadoresAmbito.get(j).arreglo);
                totalAmbitos += compi.contadoresAmbito.get(j).arreglo;
                fila.createCell(12).setCellValue(compi.contadoresAmbito.get(j).tuplas);
                totalAmbitos += compi.contadoresAmbito.get(j).tuplas;
                fila.createCell(13).setCellValue(compi.contadoresAmbito.get(j).lista);
                totalAmbitos += compi.contadoresAmbito.get(j).lista;
                fila.createCell(14).setCellValue(compi.contadoresAmbito.get(j).rango);
                totalAmbitos += compi.contadoresAmbito.get(j).rango;
                fila.createCell(15).setCellValue(compi.contadoresAmbito.get(j).conjunto);
                totalAmbitos += compi.contadoresAmbito.get(j).conjunto;
                fila.createCell(16).setCellValue(compi.contadoresAmbito.get(j).diccionarios);
                totalAmbitos += compi.contadoresAmbito.get(j).diccionarios;
                fila.createCell(17).setCellValue(totalAmbitos);
                totalAmbitos = 0;
            }
            fila = hoja6.createRow(compi.contadoresAmbito.size() + 1);
            int totalGeneral = 0;

            int decimales = TablaSimbolos.contarTiposTodos("Decimal");
            totalGeneral += decimales;
            fila.createCell(1).setCellValue(decimales);

            int binareos = TablaSimbolos.contarTiposTodos("Binareo");
            totalGeneral += binareos;
            fila.createCell(2).setCellValue(binareos);

            int octales = TablaSimbolos.contarTiposTodos("Octal");
            totalGeneral += octales;
            fila.createCell(3).setCellValue(octales);

            int hexadecimales = TablaSimbolos.contarTiposTodos("Hexadecimal");
            totalGeneral += hexadecimales;
            fila.createCell(4).setCellValue(hexadecimales);

            int flotantes = TablaSimbolos.contarTiposTodos("Flotante");
            totalGeneral += flotantes;
            fila.createCell(5).setCellValue(flotantes);

            int cadenas = TablaSimbolos.contarTiposTodos("Cadena");
            totalGeneral += cadenas;
            fila.createCell(6).setCellValue(cadenas);

            int caracteres = TablaSimbolos.contarTiposTodos("Caracter");
            totalGeneral += caracteres;
            fila.createCell(7).setCellValue(caracteres);

            int complejos = TablaSimbolos.contarTiposTodos("Complejo");
            totalGeneral += complejos;
            fila.createCell(8).setCellValue(complejos);

            int booleanos = TablaSimbolos.contarTiposTodos("Booleano");
            totalGeneral += booleanos;
            fila.createCell(9).setCellValue(booleanos);

            int nones = TablaSimbolos.contarTiposTodos("None");
            fila.createCell(10).setCellValue(nones);
            totalGeneral += nones;

            int arreglos = TablaSimbolos.contarClasesTodos("Arreglo");
            totalGeneral += arreglos;
            fila.createCell(11).setCellValue(arreglos);

            int tuplas = TablaSimbolos.contarClasesTodos("Tupla");
            totalGeneral += tuplas;
            fila.createCell(12).setCellValue(tuplas);

            int listas = TablaSimbolos.contarClasesTodos("Lista");
            totalGeneral += listas;
            fila.createCell(13).setCellValue(listas);

            int rangos = TablaSimbolos.contarClasesTodos("Rango");
            totalGeneral += rangos;
            fila.createCell(14).setCellValue(rangos);

            int conjuntos = TablaSimbolos.contarClasesTodos("Conjunto");
            totalGeneral += conjuntos;
            fila.createCell(15).setCellValue(conjuntos);

            int diccionarios = TablaSimbolos.contarClasesTodos("Diccionario");
            totalGeneral += diccionarios;
            fila.createCell(16).setCellValue(diccionarios);

            fila.createCell(17).setCellValue(totalGeneral);

            try {
                workbook.write(new FileOutputStream(new File("C:\\Users\\Computer\\Desktop\\Resultados en Excel compi\\Joceline Perez - 15130226 - Resultados.xlsx")));
                Desktop.getDesktop().open(new File("C:\\Users\\Computer\\Desktop\\Resultados en Excel compi\\Joceline Perez - 15130226 - Resultados.xlsx"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
        // </editor-fold>
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
    public static javax.swing.JLabel LabelID;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable jTablaAmbito;
    private javax.swing.JTable jTablecontadoresSin;
    private javax.swing.JTable jTablecontadorestotales;
    private javax.swing.JTextArea jTextArea1;
    public static javax.swing.JLabel labelAgrupacion;
    public static javax.swing.JLabel labelAritmeticos;
    public static javax.swing.JLabel labelAsignacion;
    public static javax.swing.JLabel labelCEDec;
    public static javax.swing.JLabel labelCEHex;
    public static javax.swing.JLabel labelCEOct;
    public static javax.swing.JLabel labelCNComp;
    public static javax.swing.JLabel labelCcar;
    public static javax.swing.JLabel labelComen;
    public static javax.swing.JLabel labelCtexto;
    public static javax.swing.JLabel labelError;
    public static javax.swing.JLabel labelLogico;
    public static javax.swing.JLabel labelMonogamo;
    public static javax.swing.JLabel labelPR;
    public static javax.swing.JLabel labelRelacionales;
    public static javax.swing.JLabel labelbit;
    public static javax.swing.JLabel labelcebin;
    public static javax.swing.JLabel labelidentidad;
    public static javax.swing.JLabel lablePuntuacion;
    public static javax.swing.JLabel lablelCFloat;
    private javax.swing.JTable tablaerrores;
    private javax.swing.JTable tablatokens;
    // End of variables declaration//GEN-END:variables
    char carAnterior;

}
