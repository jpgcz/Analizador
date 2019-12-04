/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

import SQL.TablaSimbolos;
import Vista.Lexico;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

/**
 *
 * @author samu_
 */
public class Compilador {

    // <editor-fold defaultstate="collapsed" desc="Variables de Lexico Y Sintactico">
    Sheet matrizLexica;
    Sheet matrizSintactica;
    Cell ncelLexico;
    Cell ncelSintaxis;
    boolean correccionL = false;
    boolean desmadre = false, desmadrefin = false;
    String aux = "";
    String texto = "";
    char carAnterior;
    public LinkedList<Token> tokeen = new LinkedList<Token>();
    LinkedList<Token> tokeen2 = new LinkedList<Token>();
    LinkedList<String> lex = new LinkedList<String>();
    public LinkedList<LE> errorxd = new LinkedList<LE>();
    public LinkedList<ContadoresSintaxis> contadoresSin = new LinkedList<ContadoresSintaxis>();
    public LinkedList<Contadores> contadores = new LinkedList<Contadores>();
    public LinkedList<contadorestotales> contadorestotales = new LinkedList<contadorestotales>();
    int error = 0, identificador = 0, comentarios = 0, palabrasreservadas = 0, cedec = 0, cebin = 0, cehex = 0, ceoct = 0, ctexto = 0, cfloat = 0, cncomp = 0, ccar = 0, aritmeticos = 0, monogamo = 0, logico = 0, relacionales = 0, bit = 0, identidad = 0, puntuacion = 0, agrupacion = 0, asignacion = 0;
    int pro = 0, con = 0, conen = 0, ltr = 0, terp = 0, elev = 0, sep = 0, fac = 0, not = 0, or = 0, opbit = 0, and = 0, andlog = 0, orlog = 0, xorlog = 0, estatutos = 0, asign = 0, fl = 0, arr = 0, funciones = 0, expas = 0;
    int i = 0, complexx = 0;
    int complej;
    public int[] cantidades = new int[21];
    Stack<Integer> pilaproducciones = new Stack<Integer>();
    int pes = -94;
    int fila = 0;

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="Arreglo de Producciones">
    int listap[][] = {
        {801, -41, 203, 246, 800, -40, 202},// Program::= A { Estatutos A2 }
        {202, -34, 201, -37, 204, 802, -36, -1, -68},// A::=def id (B) Program ; A
        {202, -34, 206, -42, -1}, // A::= id = Constante ; A
        {203, 246, -34},// A2::=; Estatutos A2
        {205, -1}, // B::= id C
        {205, -1, -35}, // C::=, id C
        {-8}, // Constante::=constflotante
        {-7}, // Constante::=constcadena
        {-10}, // Constante::=constcaracter
        {207}, // ConstEntero
        {-9}, // Constante::=constcompleja
        {-66}, // Constante::=true
        {-67}, // Constante::=false
        {208}, // Constante::=ListTupRangos
        {-65}, // Constante::=none
        {-3}, // ConstEntero::=decimal
        {-4}, // ConstEntero::=binario
        {-5}, // ConstEntero::=hexadecimal
        {-6}, // ConstEntero::=octal
        {803, -37, 209, 212, 802, -36}, // ListTupRangos::=( Or D )
        {214}, // ListTupRangos::=Arr
        {-37, 207, 215, -35, 207, 215, -35, 207, 215, -36, -69}, // 22)	ListTupRangos::=range ( H ConstEntero , H ConstEntero , H ConstEntero )
        {803, -41, 211, 210, 206, 802, -40}, // ListTupRangos::={ Constante E }
        {209, 212, -35}, // D::=, Or D
        {211, 206, -51}, // E::=: Constante F
        {211, 210, 206, -35}, // F::=, Constante E
        {213, 218}, // Or::=And G
        {213, 804, 218, -22}, // G::=|| And G
        {-39, 216, 814, 212, 215, -38}, // Arr::=[ H Or H2 ]
        {813, -12},// H::=-
        {217, 814, 212, 215, -51},// H2::=: H Or I
        {814, 212, 215, -51}, // I::=: H Or 
        {219, 220}, //And::=Not J
        {219, 804, 220, -21}, // J::=&& Not J
        {219, 804, 220, -2}, // J::=## Not J
        {221, 222}, // Not::=ExpPas K
        {221, 804, 222, -20}, // K::=! ExpPas K
        {223, 224}, // ExpPas::=Orlog L
        {223, 804, 224, -23}, // L::=< ExpPas
        {223, 804, 224, -24}, // L::=<= ExpPas
        {223, 804, 224, -27}, // L::=== ExpPas
        {223, 804, 224, -28}, // L::=!= ExpPas
        {223, 804, 224, -26}, // L::=>= ExpPas
        {223, 804, 224, -25}, // L::=> ExpPas
        {223, 224, -52}, // L::=is ExpPas
        {223, 224, -53}, // L::=isnot ExpPas
        {223, 224, -60}, // L::=in ExpPas
        {223, 224, -64}, // L::=innot ExpPas
        {225, 226}, // Orlog::=Xorlog M
        {225, 804, 226, -30}, // M::=| Xorlog M
        {227, 228}, // Xorlog::=Andlog N
        {227, 804, 228, -31}, // N::=^ Andlog N
        {229, 230}, // Andlog::=Opbit O
        {229, 804, 230, -29}, // O::=& Opbit O
        {231, 232}, // Opbit::=SimpleExpPas P
        {231, 804, 232, -32}, // P::=<< SimpleExpPas P
        {231, 804, 232, -33}, // P::=>> SimpleExpPas P
        {233, 234}, // SimpleExpPas::=TerminoPascal Q
        {233, 804, 234, -12}, // Q::=- TerminoPascal Q
        {233, 804, 234, -11}, // Q::=+ TerminoPascal Q
        {235, 236}, // TerminoPascal::=Elevacion R
        {235, 804, 236, -13}, // R::=* Elevacion R
        {235, 804, 236, -15}, // R::=/ Elevacion R
        {235, 804, 236, -16}, // R::=// Elevacion R
        {235, 236, -17}, // R::=% Elevacion R
        {237, 238}, // Elevacion::=Factor S
        {237, 238, -14}, // S::=** Factor S
        {206}, // Factor::=Constante
        {239, -1}, // Factor::=id T
        {239, -1, -18}, // Factor::=++ id T
        {239, -1, -19}, // Factor::=-- id T
        {245}, // Factor::=Funciones 
        {240, 214}, // T::=Arr U
        {805, 241, 243}, // T::= Asign V
        {251},//T::=SEM2
        {-37, 811, 209, 212, 810, -36},//T::=( OR D )
        {244, -50}, // T::=. Funlist
        {805, 241, 243}, // U::=Asign V
        {212}, // V::=Or
        {-37, 242, -36, -70}, // V::=input ( W )
        {-7}, // W::=constcadena
        {-42}, // Asign::==
        {-43}, // Asign::=+=
        {-47}, // Asign::=/=
        {-45}, // Asign::=*=
        {-44}, // Asign::=-=
        {-48}, // Asign::=//=
        {-46}, // Asign::=**=
        {-49}, // Asign::=%=
        {-37, -36, -82}, // Funlist::=sort ( )
        {-37, -36, -83}, // Funlist::=reverse ( )
        {-37, 212, -36, -84}, // Funlist::=count ( Or )
        {-37, 212, -36, -85}, // Funlist::=index ( Or )
        {-37, 212, -36, -86}, // Funlist::=append ( Or )
        {-37, 212, -36, -87}, // Funlist::=extend ( Or )
        {-37, 212, -36, -88}, // Funlist::=pop ( Or )
        {-37, 212, -36, -89}, // Funlist::=remove ( Or )
        {-37, 212, -35, 212, -36, -90}, // Funlist::=insert ( Or , Or )
        {-37, 212, -35, 212, -36, -71}, // Funciones::=findall ( Or , Or )
        {-37, 212, -35, 212, -36, -72}, // Funciones::=replace ( Or , Or )
        {-37, 212, -36, -73}, // Funciones::=len ( Or )
        {-37, 212, -35, 212, -36, -74}, // Funciones::=sample ( Or , Or )
        {-37, 212, -36, -75}, // Funciones::=choice ( Or )
        {-37, -36, -76}, // Funciones::=random ( )
        {-37, 212, -36, -77}, // Funciones::=randrange ( Or )
        {-37, 212, -36, -78}, // Funciones::=mean ( Or )
        {-37, 212, -36, -79}, // Funciones::=median ( Or )
        {-37, 212, -36, -80}, // Funciones::=variance ( Or )
        {-37, 212, -36, -81}, // Funciones::=sum ( Or )
        {-37, 247, 212, -36, -61}, // Estatutos::=print ( Or X )
        {-37, 248, -36, -91}, // Estatutos::=println ( Y )
        {-93, 250, 249, 246, -51, 806, 212, -59}, // Estatutos::=if Or : Estatutos Z Z2 end
        {-93, 249, 246, -51, 212, -95, 212, -58}, // Estatutos::=for Or to Or : Estatutos Z end
        {-92, 249, 246, -51, 808, 212, -63}, // Estatutos::=while Or : Estatutos Z wend
        {-54}, // Estatutos::=break
        {-55}, // Estatutos::=continue
        {809, 212, -62}, // Estatutos::=return Or
        {212}, // Estatutos::=Or
        {247, 212, -35}, // X::=, Or X
        {247, 212}, // Y::=Or X
        {249, 246, -34}, // Z::=; Estatutos Z
        {250, 249, 246, -51, 807, 212, -56}, // Z2::=elif Or : Estatutos Z Z2
        {249, 246, -57}, // Z2::=elseE statutos Z
        {-18},
        {-19},
        {},//epsilon
    };
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Variables de Ambito"> 
    Token idTemp;
    Token par1, par2, par3;
    String tipoVar;
    int ambTemp;
    int contParame = 0;
    boolean zDeclaracion = true;
    int tipoDeclaracion = 0;
    LinkedList<Token> parametros = new LinkedList();
    LinkedList<Token> llaves = new LinkedList();
    public LinkedList<ContadoresAmbito> contadoresAmbito = new LinkedList();
    int contAmbitos = 0;
    Stack<Integer> ambitos = new Stack();
    String paramArrAmbito = "";
    String paramRangoAmbito = "";
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Variables de Semantica 1">
    Stack<Token> operandos = new Stack();
    Stack<Token> signos = new Stack();
    public LinkedList<ContadoresSemantica1> contaSem1 = new LinkedList();
    // <editor-fold defaultstate="collapsed" desc="Arreglo suma"> 
    String[][] arrSuma
            = {
                {"TmpDec", "TmpFloat", "TmpCadena", "TmpDec", "Err", "Err", "Err", "TmpComplej", "Err", "Err", "TmpArr", "Err", "Err", "Err", "Err", "TmpRango", "TmpDec"},
                {"TmpFloat", "TmpFloat", "TmpCadena", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpFloat"},
                {"TmpCadena", "TmpCadena", "TmpCadena", "TmpCadena", "TmpCadena", "TmpCadena", "TmpCadena", "TmpCadena", "TmpCadena", "TmpCadena", "TmpCadena", "TmpCadena", "TmpCadena", "Err", "Err", "TmpCadena", "TmpCadena"},
                {"TmpDec", "Err", "TmpCadena", "TmpChar", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpChar",},
                {"Err", "Err", "TmpCadena", "Err", "TmpBinario", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBinario"},
                {"Err", "Err", "TmpCadena", "Err", "Err", "TmpHex", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpHex"},
                {"Err", "Err", "TmpCadena", "Err", "Err", "Err", "TmpOctal", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpOctal"},
                {"TmpComplej", "Err", "TmpCadena", "Err", "Err", "Err", "Err", "TmpComplej", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpComplej"},
                {"Err", "Err", "TmpCadena", "Err", "Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"Err", "Err", "TmpCadena", "Err", "Err", "Err", "Err", "Err", "Err", "TmpList", "Err", "Err", "Err", "Err", "Err", "Err", "TmpList"},
                {"TmpArr", "Err", "TmpCadena", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpArr", "Err", "Err", "Err", "Err", "Err", "TmpArr"},
                {"Err", "Err", "TmpCadena", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpTupla", "Err", "Err", "Err", "Err", "TmpTupla"},
                {"Err", "Err", "TmpCadena", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpConjunto", "Err", "Err", "Err", "TmpConjunto"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpDicc", "Err", "Err", "TmpDicc"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"TmpRango", "Err", "TmpCadena", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpRango", "TmpRango"},
                {"TmpDec", "TmpFloat", "TmpCadena", "TmpChar", "TmpBinario", "TmpHex", "TmpOctal", "TmpComplej", "TmpBool", "TmpList", "TmpArr", "TmpTupla", "TmpConjunto", "TmpDicc", "Err", "TmpRango", "Variant"}
            };
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Arreglo resta"> 
    String[][] arrResta
            = {
                {"TmpDec", "TmpFloat", "Err", "Err", "Err", "Err", "Err", "TmpComplej", "Err", "Err", "TmpArr", "Err", "Err", "Err", "Err", "Err", "TmpDec"},
                {"TmpFloat", "TmpFloat", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpFloat"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "TmpBinario", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "TmpHex", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "TmpOctal", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"TmpComplej", "Err", "Err", "Err", "Err", "Err", "Err", "TmpComplej", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"TmpArr", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"TmpDec", "TmpFloat", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Variant"}
            };
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Arreglo multi"> 
    String[][] arrMulti
            = {
                {"TmpDec", "TmpFloat", "Err", "Err", "Err", "Err", "Err", "TmpComplej", "Err", "Err", "TmpArr", "Err", "Err", "Err", "Err", "Err", "TmpDec"},
                {"TmpFloat", "TmpFloat", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpFloat"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "TmpBinario", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "TmpHex", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "TmpOctal", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"TmpComplej", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"TmpArr", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"TmpDec", "TmpFloat", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Variant"},};
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Arreglo dividir"> 
    String[][] arrDiv
            = {
                {"TmpFloat", "TmpFloat", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpArr", "Err", "Err", "Err", "Err", "Err", "TmpFloat"},
                {"TmpFloat", "TmpFloat", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpFloat"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"TmpArr", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"TmpFloat", "TmpFloat", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Variant"}
            };
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Arreglo rela"> 
    String[][] arrRela
            = {
                {"TmpBool", "TmpBool", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"TmpBool", "TmpBool", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"Err", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"TmpBool", "TmpBool", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"Err", "Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "Err", "Err", "TmpBool"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "Err", "TmpBool"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "TmpBool"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool", "TmpBool"},
                {"TmpBool", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},};
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Arreglo operadores"> 
    String[][] arrOpera
            = {
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBool"},};
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Arreglo Desplazamiento">
    String[][] arrDesplaza
            = {
                {"TmpDec", "Err", "Err", "Err", "TmpBinario", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpDec"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"TmpBinario", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpBinario"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"TmpDec", "Err", "Err", "Err", "TmpBinario", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpDec"},};
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Arreglo Division Entero"> 
    String[][] arrDivEnt
            = {
                {"TmpDec", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpDec"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err"},
                {"TmpDec", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "Err", "TmpDec"},};
    // </editor-fold>

    int tDec = 0, tBin = 0, tHex = 0, tOct = 0,
            tFlot = 0, tCaden = 0, tCarac = 0, tComple = 0, tBool = 0, tNone = 0,
            tLista = 0, tArr = 0, tTup = 0, tConj = 0, tRango = 0, tDicc = 0, tVariant = 0;
    //</editor-fold>        

    public Compilador() {
        try {
            matrizLexica = LectorExcel.definirMatrizLexico();
            matrizSintactica = LectorExcel.definirMatrizSintactica();
        } catch (WriteException ex) {
            Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
        }
        ambitos.add(0);
    }

    public void compilarLexico(JTextArea jTextArea1) {
        int lineas = 1, complemas = 0;
        boolean com = true;
        String descError = "", tipoError = "Léxico";
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

            ncelLexico = matrizLexica.getCell(columna, estado);
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

                    if (estado != -100) {
                        if (car == '\n') {
                            tokeen.add(new Token(estado, lexema, lineas - 1));
                            lineas--;
                        } else {
                            tokeen.add(new Token(estado, lexema, lineas));
                        }
                    } else if (estado == -100) {
                        lineas--;
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
                    estado = 82;
                    i = complexx;
                    jTextArea1.insert(" ", i);//agregar un espacio para volver a retokenizar facil
                    lexema = "(";
                    b = false;

                } else if (estado == 560) {

                    for (int m = 1; m < aux.length(); m++) {

                        if (aux.charAt(m) == '.') {
                            estado = 84;
                        }
                    }
                    if (estado != 84) {
                        estado = 83;
                    }
                    i = complemas - 1;
                    jTextArea1.insert(" ", i);
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
                    }
                }
            }
        }
        compilarSintaxis();
    }

    private void compilarSintaxis() {
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
                case 201:
                    pro++;
                    break;
                case 206:
                    con++;
                    break;
                case 207:
                    conen++;
                    break;
                case 208:
                    ltr++;
                    break;
                case 212:
                    or++;
                    break;
                case 214:
                    arr++;
                    break;
                case 218:
                    and++;
                    break;
                case 220:
                    not++;
                    break;
                case 222:
                    expas++;
                    break;
                case 224:
                    orlog++;
                    break;
                case 226:
                    xorlog++;
                    break;
                case 228:
                    andlog++;
                    break;
                case 230:
                    opbit++;
                    break;
                case 232:
                    sep++;
                    break;
                case 234:
                    terp++;
                    break;
                case 236:
                    elev++;
                    break;
                case 238:
                    fac++;
                    break;
                case 243:
                    asign++;
                    break;
                case 244:
                    fl++;
                    break;
                case 245:
                    funciones++;
                    break;
                case 246:
                    estatutos++;
                    break;

            }

            if (tokeen2.getFirst().est == pilaproducciones.peek() && pilaproducciones.peek() < 0) {

                if (pilaproducciones.pop() == pes) {
                    if (tokeen2.removeFirst().est == pes) {
                        break;
                    }
                } else {
                    Token tokenRemovido = tokeen2.removeFirst();
                    compilarAmbito(tokenRemovido);
                    compilarSemantica2(tokenRemovido);
                    compilarSemantica1(tokenRemovido);
                    
                }
            } else {

                if (tokeen2.getFirst().est != pilaproducciones.peek() && pilaproducciones.peek() < 0) {

                    if (n <= 126) {
                        descError = "FUERZA BRUTA";
                    }

                    errorxd.add(new LE(descError, tokeen2.getFirst().valor, tipoError2, n, tokeen2.getFirst().linea));

                    break;
                } else {
                    if (pilaproducciones.peek() > 799) {//Aqui se activan y desactivan las banderas haciendole un pop a la pila sintactica
                        arrobasAmbito(pilaproducciones.peek());
                        arrobasSemantica1(pilaproducciones.peek());
                        arrobasSemantica2(pilaproducciones.peek());
                    }
                    if (pilaproducciones.peek() >= 200 && pilaproducciones.peek() < 253 && tokeen2.getFirst().est < 0) {

                        fila = pilaproducciones.peek() - 201;

                        columna = Math.abs(tokeen2.getFirst().est);

                        //if(tokeen2.getFirst().est==-58){KKin=true;//System.out.println("ENTROOOO LPMMMMMMM");}
                        ncelSintaxis = matrizSintactica.getCell(columna - 1, fila);

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
        Lexico.labelError.setText(Integer.toString(cantidades[0]));
        Lexico.LabelID.setText(Integer.toString(cantidades[1]));
        Lexico.labelComen.setText(Integer.toString(cantidades[2]));
        Lexico.labelPR.setText(Integer.toString(cantidades[3]));
        Lexico.labelCEDec.setText(Integer.toString(cantidades[4]));
        Lexico.labelcebin.setText(Integer.toString(cantidades[5]));
        Lexico.labelCEHex.setText(Integer.toString(cantidades[6]));
        Lexico.labelCEOct.setText(Integer.toString(cantidades[7]));
        Lexico.labelCtexto.setText(Integer.toString(cantidades[8]));
        Lexico.labelAgrupacion.setText(Integer.toString(cantidades[19]));
        Lexico.lablelCFloat.setText(Integer.toString(cantidades[9]));
        Lexico.labelCNComp.setText(Integer.toString(cantidades[10]));
        Lexico.labelCcar.setText(Integer.toString(cantidades[11]));
        Lexico.labelAritmeticos.setText(Integer.toString(cantidades[12]));
        Lexico.labelMonogamo.setText(Integer.toString(cantidades[13]));
        Lexico.labelLogico.setText(Integer.toString(cantidades[14]));
        Lexico.labelbit.setText(Integer.toString(cantidades[16]));
        Lexico.lablePuntuacion.setText(Integer.toString(cantidades[18]));
        Lexico.labelidentidad.setText(Integer.toString(cantidades[17]));
        Lexico.labelRelacionales.setText(Integer.toString(cantidades[15]));
        Lexico.labelAsignacion.setText(Integer.toString(cantidades[20]));
        contadorestotales.add(new contadorestotales(cantidades[0], cantidades[1], cantidades[2], cantidades[3], cantidades[4], cantidades[5], cantidades[6], cantidades[7], cantidades[8], cantidades[9], cantidades[10], cantidades[11], cantidades[12], cantidades[13], cantidades[14], cantidades[15], cantidades[16], cantidades[17], cantidades[18], cantidades[19], cantidades[20]));
        for (int j = 0; j < 21; j++) {
            cantidades[j] = 0;
        }
        contadoresSin.add(new ContadoresSintaxis(pro, con, conen, ltr, terp, elev, sep, fac, not, or, opbit, and, andlog, orlog, xorlog, estatutos, asign, fl, arr, funciones, expas));
        pro = 0;
        con = 0;
        conen = 0;
        ltr = 0;
        terp = 0;
        elev = 0;
        sep = 0;
        fac = 0;
        not = 0;
        or = 0;
        opbit = 0;
        and = 0;
        andlog = 0;
        orlog = 0;
        xorlog = 0;
        estatutos = 0;
        asign = 0;
        fl = 0;
        arr = 0;
        funciones = 0;
        expas = 0;
    }

    private void compilarAmbito(Token token) {
        if (zDeclaracion) {
            if (tipoDeclaracion == 0) {
                if (token.est == -68) {
                    tipoDeclaracion = 1;
                } else if (token.est == -1) {
                    //Checar si existe ese id en la base de datos
                    if (TablaSimbolos.buscar(token.valor.trim(), ambitos.peek() + "")) {
                        errorxd.add(new LE("Identificador ya declarado", token.valor, "Ambito", 700, token.linea));
                        regla13(token,"Error");
                        tipoDeclaracion = 101;
                    } else {
                        idTemp = token;
                        tipoDeclaracion = 3;
                        regla13(token,"Acepta");
                    }
                }
            } else {
                if (tipoDeclaracion == 1 && token.est == -1) {
                    declarFuncion(token);
                } else if (tipoDeclaracion == 2) {
                    declarParamFun(token);
                } else if (tipoDeclaracion == 3) {
                    if (token.est < -2 && token.est > -11 || token.est < -64 && token.est > -68) {
                        declarVariable(token);
                    }
                    if (token.est == -69) {
                        tipoDeclaracion = 4;
                    }
                    if (token.est == -36) {
                        declarTupla();
                        tipoDeclaracion = 7;
                    }
                    if (token.est == -40) {
                        tipoDeclaracion = 8;
                        TablaSimbolos.registrarIdNoDefinido(idTemp.valor.trim(), ambitos.peek() + "", (contAmbitos + 1) + "");
                        ambTemp = ambitos.peek();
                    }
                    if (token.est == -38) {
                        tipoDeclaracion = 10;
                    }
                } else if (tipoDeclaracion > 3 && tipoDeclaracion < 7) { //Declarar rangos
                    declarRango(token);
                } else if (tipoDeclaracion == 7) { // Declarar Tuplas
                    declarDatoTupla(token);
                } else if (tipoDeclaracion == 8 || tipoDeclaracion == 9) {
                    declarConjunDicc(token);
                } else if (tipoDeclaracion == 10) {
                    declarArr(token);
                } else if (tipoDeclaracion == 100 && token.est == -37) {
                    tipoDeclaracion = 0;
                } else if (tipoDeclaracion == 101 && token.est == -34) {
                    tipoDeclaracion = 0;
                }
            }
        } else {
            //Aqui se verifican las variables en zona de ejecucion
            buscarIdEnAmbitos(token);
        }
    }

    private void compilarSemantica1(Token token) {
        if (!zDeclaracion && !zonaParam) {
            if (token.est == -1) {
                Token tokenGenerado = buscarGenerarToken(token);
                operandos.add(tokenGenerado);
            } else if (isOperando(token)) {
                operandos.add(token);
            } else if (isSigno(token)) {
                signos.add(token);
            }
        }
    }
    
    public LinkedList<TablaSem2> listaSem2 = new LinkedList();
    private int estadoSemantica2 = 0;
    Stack<Token> idsFuncPro = new Stack();
    Stack<Integer> ambTempSem2 = new Stack();
    Token tempReturn;
    boolean isReturn = false;
    boolean zonaParam = false;
    String param = "";
    Token idTmpSem2;
    int ambEncontrado;
    String paramRango = "";
    String paramArr = "";

    private void compilarSemantica2(Token token) {
        int numToken = token.est;
        if (!zDeclaracion) {
            if (estadoSemantica2 == 0) {
                if (numToken == -1) {
                    cambioEstadoPorID(token);
                }
                if (numToken == -58) {
                    estadoSemantica2 = 1;
                    listaSem2.add(new TablaSem2("1080", "For", token.valor.trim(), token.linea, "Acepta", ambitos.peek()));
                }
            } else {
                regla08(token);
                regla10(token);
                regla03(token);
            }
        }
    }

    private void regla01(String regla, Token resultado) {
        int linea = resultado.linea;
        String valor = resultado.valor.trim();
        String estado = "";
        if (resultado.est == -104) {
            estado = "Acepta";
        } else {
            estado = "Error";
            errorxd.add(new LE("Es espera un temporar Booleano", resultado.valor, "Semantica 2", 700, resultado.linea));
        }
        listaSem2.add(new TablaSem2(regla, "Boolean", valor, linea, estado, ambitos.peek()));
    }

    private void regla02(Token signo, Token val, String estado) {
        String regla = "";
        String valor = val.valor.trim();
        int linea = val.linea;
        if (signo.est == -42) {
            regla = "1020";
        } else if (signo.est > -43 && signo.est > -50) {
            regla = "1021";
        }
        listaSem2.add(new TablaSem2(regla, "Var/Par/Arr", valor, linea, estado, ambitos.peek()));
    }

    private void regla03(Token token) {
        if (estadoSemantica2 > 17 && estadoSemantica2 < 20) {
            if (estadoSemantica2 == 18) {
                if(token.est == -38){
                    estadoSemantica2 = 19;
                }else{
                    estadoSemantica2 = 0;
                    errorxd.add(new LE("Se espera un [ exp ]", token.valor, "Semantica 2", 706, token.linea));
                }
            } else if (estadoSemantica2 == 19) {
                if(token.est == -39){
                    int numParr = paramArr.split(":").length;
                    int numParamReal = TablaSimbolos.ObtenerNumParametros(idTmpSem2.valor.trim(), ambEncontrado + "");
                    if (numParr == numParamReal) {
                        listaSem2.add(new TablaSem2("1030", "ID", paramArr, token.linea, "Acepta", ambitos.peek()));
                    } else {
                        listaSem2.add(new TablaSem2("1030", "ID", paramArr, token.linea, "Error", ambitos.peek()));
                        errorxd.add(new LE("El numero de dimenciones no la misma a la declarada", token.valor, "Semantica 2", 707, token.linea));
                    }
                    estadoSemantica2 = 0;
                    paramArr = "";
                }else{
                    paramArr += token.valor;
                }
            }
        }
    }

    private void regla08(Token token) {
        if (estadoSemantica2 > 0 && estadoSemantica2 < 12) {
            if (estadoSemantica2 == 1) {
                if (token.est == -1) {
                    listaSem2.add(new TablaSem2("1081", "ID", token.valor.trim(), token.linea, "Acepta", ambitos.peek()));
                    estadoSemantica2 = 2;
                } else {
                    errorxd.add(new LE("Se espera un ID para el For", token.valor, "Semantica 2", 708, token.linea));
                    listaSem2.add(new TablaSem2("1081", "ID", token.valor.trim(), token.linea, "Error", ambitos.peek()));
                    estadoSemantica2 = 2;
                }
            } else if (estadoSemantica2 == 2) {
                if (token.est == -95) {
                    estadoSemantica2 = 3;
                }
            } else if (estadoSemantica2 == 3) {
                if (token.est == -7) {
                    listaSem2.add(new TablaSem2("1082", "Cadena", token.valor, token.linea, "Acepta", ambitos.peek()));
//                    operandos.pop();
                    estadoSemantica2 = 0;
                } else if (token.est == -69) {
                    System.out.println("Entroooooo!!");
                    listaSem2.add(new TablaSem2("1082", "Rango", token.valor, token.linea, "Acepta", ambitos.peek()));
                    estadoSemantica2 = 4;
                } else if (token.est == -1) {
                    buscarIdFor(token);
                }
            } else if (estadoSemantica2 == 4) {
                System.out.println("Entrooooo al 4");
                if (token.est == -12) {
                    paramRango += token.valor.trim();
                    estadoSemantica2 = 5;
                } else if (token.est == -35) {
                    paramRango += token.valor.trim();
                    estadoSemantica2 = 6;
                } else if(token.est == -3){
                    paramRango += token.valor.trim();
                }
            } else if (estadoSemantica2 == 5) {
                System.out.println("Entro al 5");
                if (token.est == -35) {
                    paramRango += token.valor.trim();
                    estadoSemantica2 = 6;
                } else {
                    paramRango += token.valor.trim();
                    listaSem2.add(new TablaSem2("1161", "CE", paramRango, token.est, "Acepta", ambitos.peek()));
                }
            } else if (estadoSemantica2 == 6) {
                System.out.println("Entro al 6");
                if (token.est == -12) {
                    estadoSemantica2 = 7;
                    paramRango += token.valor.trim();
                } else if (token.est == -35) {
                    paramRango += token.valor.trim();
                    estadoSemantica2 = 8;
                }else {
                    paramRango += token.valor.trim();
                }
            } else if (estadoSemantica2 == 7) {
                System.out.println("Entro al 7");
                if (token.est == -3) {
                    paramRango += token.valor.trim();
                    listaSem2.add(new TablaSem2("1161", "CE", paramRango, token.est, "Acepta", ambitos.peek()));
                } else if (token.est == -35) {
                    paramRango += token.valor.trim();
                    estadoSemantica2 = 8;
                }
            } else if (estadoSemantica2 == 8) {
                System.out.println("Entro al 8");
                if (token.est == -12) {
                    estadoSemantica2 = 9;
                    paramRango += token.valor.trim();
                } else if (token.est == -3) {
                    paramRango += token.valor.trim();
                    estadoSemantica2 = 10;
                } else if (token.est == -35) {
                    paramRango += token.valor.trim();
                    estadoSemantica2 = 10;
                }

            } else if (estadoSemantica2 == 9) {
                System.out.println("Entro al 9");
                if (token.est == -3) {
                    paramRango += token.valor.trim();
                    estadoSemantica2 = 10;
                }
            } else if (estadoSemantica2 == 10) {
                System.out.println("Entro al 10");
                if (token.est == -37) {
                    evaluarValoresRango(paramRango.split(","), token.linea, ambitos.peek());
                    estadoSemantica2 = 0;
                    paramRango = "";
                }
            }
        }
    }

    private void regla09(Token token, int ambito) {
        String valor = token.valor.trim();
        String dato = TablaSimbolos.ObtenerClase(valor, ambito + "");
        switch (dato) {
            case "Variable":
            case "Parametro":
            case "Arreglo":
            case "Diccionario":
            case "Rango":
                listaSem2.add(new TablaSem2("1090", valor, valor, token.linea, "Acepta", ambito));
                break;
            default:
                listaSem2.add(new TablaSem2("1090", valor, valor, token.linea, "Error", ambito));
                errorxd.add(new LE("El ID debe de ser Variable/Parametro/Arreglo/Diccionaio/Rango", token.valor, "Semantica 2", 710, token.linea));
        }
    }

//    private void regla07(Token token){
//        if(estadoSemantica2 > 11 && estadoSemantica2 < 15 ){
//            if(estadoSemantica2 ==12){
//                if(token.est == -12){
//                    listaSem2.add(new TablaSem2("1071", token.valor, token.valor, token.linea, "Acepta", ambitos.peek()));
//                    estadoSemantica2 = 13;
//                }else if(token.est == -3){
//                    
//                }
//            }
//        }
//    }
    private void regla10(Token token) {
        if (estadoSemantica2 > 15 && estadoSemantica2 < 18) {
            if (estadoSemantica2 == 16) {
                if (token.est == -36) {
                    estadoSemantica2 = 17;
                } else {
                    errorxd.add(new LE("Se espera un ( ", token.valor, "Semantica 2", 711, token.linea));
                    estadoSemantica2 = 0;
                }
            } else if (estadoSemantica2 == 17) {
                if (token.est == -37) {
                    int numParam = param.split(",").length;
                    int numParamReal = TablaSimbolos.ObtenerNumParametros(idTmpSem2.valor.trim(), ambEncontrado + "");
                    if (numParam == numParamReal) {
                        listaSem2.add(new TablaSem2("1100", "ID", param, token.linea, "Acepta", ambitos.peek()));
                    } else {
                        listaSem2.add(new TablaSem2("1100", "ID", param, token.linea, "Error", ambitos.peek()));
                        errorxd.add(new LE("El numero de parametros no es el mismo al declarado", token.valor, "Semantica 2", 712, token.linea));
                    }
                    estadoSemantica2 = 0;
                    param = "";
                } else {
                    param += token.valor;
                }
            }
        }
    }
    
    private void regla13(Token token, String estado){
        listaSem2.add(new TablaSem2("1130", "ID", token.valor.trim(), token.linea, estado, ambitos.peek()));
    }

    private void regla14And15() {
        if (ambTempSem2.size() > 0 && idsFuncPro.size() > 0) {
            if (isReturn) {
                String tipo = getTipoDato(tempReturn);
                int amb = ambTempSem2.pop();
                String id = idsFuncPro.pop().valor.trim();
                TablaSimbolos.actualizarFuncTipoClase(id, amb + "", tipo);
                listaSem2.add(new TablaSem2("1140", "CE", tempReturn.valor.trim(), tempReturn.linea, "Acepta", amb));
                isReturn = false;
            } else {
                int amb = ambTempSem2.pop();
                listaSem2.add(new TablaSem2("1150", "CE", "CE", idsFuncPro.pop().linea, "Acepta", amb));
                isReturn = false;
            }
        }
    }

    private void regla11And12(Token token, int ambito) {
        String clase = TablaSimbolos.ObtenerClase(token.valor.trim(), ambito + "");
        if (clase.equals("Procedimiento")) {
            listaSem2.add(new TablaSem2("1110", "CE", token.valor.trim(), token.linea, "Acepta", ambito));
        }
        if (clase.equals("Funcion")) {
            listaSem2.add(new TablaSem2("1120", "CE", token.valor.trim(), token.linea, "Acepta", ambito));
        }
    }
    
    private void evaluarValoresRango(String[] parametros, int linea, int ambito) {
        int tamaño = parametros.length;
        if (tamaño == 3) {
            int par1 = Integer.parseInt(parametros[0]);
            int par2 = Integer.parseInt(parametros[1]);
            int par3 = Integer.parseInt(parametros[2]);
            if (par3 < 0) {
                if (par1 > par2) {
                    listaSem2.add(new TablaSem2("1160", "CE", par2 + "", linea, "Acepta", ambito));
                } else {
                    //Marcar error
                    listaSem2.add(new TablaSem2("1160", "CE", par2 + "", linea, "Error", ambito));
                }
            } else {
                if (par1 < par2) {
                    listaSem2.add(new TablaSem2("1160", "CE", par2 + "", linea, "Acepta", ambito));
                } else {
                    //Marcar error
                    listaSem2.add(new TablaSem2("1160", "CE", par2 + "", linea, "Error", ambito));
                }
            }
        }
    }
    
    private void evaluarValoresArreglo(String[]parametros, int linea, int ambito){
        int tamaño = parametros.length;
        if (tamaño == 3) {
            int par1 = Integer.parseInt(parametros[0]);
            int par2 = Integer.parseInt(parametros[1]);
            int par3 = Integer.parseInt(parametros[2]);
            if (par3 < 0) {
                if (par1 > par2) {
                    listaSem2.add(new TablaSem2("1031", "CE", par2 + "", linea, "Acepta", ambito));
                } else {
                    //Marcar error
                    listaSem2.add(new TablaSem2("1031", "CE", par2 + "", linea, "Error", ambito));
                }
            } else {
                if (par1 < par2) {
                    listaSem2.add(new TablaSem2("1031", "CE", par2 + "", linea, "Acepta", ambito));
                } else {
                    //Marcar error
                    listaSem2.add(new TablaSem2("1031", "CE", par2 + "", linea, "Error", ambito));
                }
            }
        }
    }

    private void buscarIdFor(Token token) {
        if (TablaSimbolos.buscar(token.valor.trim(), ambitos.peek() + "")) {
            String tipo = TablaSimbolos.ObtenerTipo(token.valor.trim(), ambitos.peek() + "");
            if (tipo == "Cadena") {
                listaSem2.add(new TablaSem2("1082", "Cadena", token.valor, token.linea, "Acepta", ambitos.peek()));
                estadoSemantica2 = 0;
            } else if (tipo == "Struct") {
                tipo = TablaSimbolos.ObtenerClase(token.valor.trim(), ambitos.peek() + "");
                if (tipo == "Rango" || tipo == "Arreglo") {
                    listaSem2.add(new TablaSem2("1082", tipo, token.valor, token.linea, "Acepta", ambitos.peek()));
                    estadoSemantica2 = 0;
                } else {
                    errorxd.add(new LE("Se espera Cadena/rango/arreglo", token.valor, "Semantica 2", 709, token.linea));
                    listaSem2.add(new TablaSem2("1082", "Cadena, Arreglo, Rango", token.valor, token.linea, "Error", ambitos.peek()));
                    estadoSemantica2 = 0;
                }
            } else {
                listaSem2.add(new TablaSem2("1082", "Cadena, Arreglo, Rango", token.valor, token.linea, "Error", ambitos.peek()));
                estadoSemantica2 = 0;
            }
        } else if (buscarIdEnAmbitosSem1(token)) {
            int amb = buscarIdEnAmbitosSem2(token);
            String tipo = TablaSimbolos.ObtenerTipo(token.valor.trim(), amb + "");
            if (tipo == "Cadena") {
                listaSem2.add(new TablaSem2("1082", "Cadena", token.valor, token.linea, "Acepta", ambitos.peek()));
            } else if (tipo == "Struct") {
                tipo = TablaSimbolos.ObtenerClase(token.valor.trim(), amb + "");
                if (tipo == "Rango" || tipo == "Arreglo") {
                    listaSem2.add(new TablaSem2("1082", tipo, token.valor, token.linea, "Acepta", ambitos.peek()));
                } else {
                    //Marcar error
                    listaSem2.add(new TablaSem2("1082", "Cadena, Arreglo, Rango", token.valor, token.linea, "Error", ambitos.peek()));
                }
            } else {
                listaSem2.add(new TablaSem2("1082", "Cadena, Arreglo, Rango", token.valor, token.linea, "Error", ambitos.peek()));
            }
        } else {
            listaSem2.add(new TablaSem2("1082", "Cadena, Arreglo, Rango", token.valor, token.linea, "Error", ambitos.peek()));
        }
    }

    private void cambioEstadoPorID(Token token) {
        String clase = "";
        if (TablaSimbolos.buscar(token.valor.trim(), ambitos.peek() + "")) {
            idTmpSem2 = token;
            ambEncontrado = ambitos.peek();
            clase = TablaSimbolos.ObtenerClase(token.valor.trim(), ambitos.peek() + "");
            cambioEstadoPorClase(clase);
        } else if (buscarIdEnAmbitosSem1(token)) {
            idTmpSem2 = token;
            int amb = buscarIdEnAmbitosSem2(token);
            ambEncontrado = amb;
            clase = TablaSimbolos.ObtenerClase(token.valor.trim(), amb + "");
            cambioEstadoPorClase(clase);
        }
    }

    private void cambioEstadoPorClase(String clase) {
        switch (clase) {
            case "Funcion":
            case "Procedimiento":
                estadoSemantica2 = 16;
                break;
//            case "Tupla":
//                estadoSemantica2 = 12;
//                break;
//            case "Diccionario":
            case "Arreglo":
                estadoSemantica2 = 18;
                break;
        }
    }

    private void generarTemp() {
        Token operando1 = operandos.pop();
        Token operando2 = operandos.pop();
        Token signo = signos.pop();

        int fila = getNumeroFilaColumna(operando1);
        int columna = getNumeroFilaColumna(operando2);
//        guardarContadoresSem1(signo);
        Token tokenGenerado = compararOperandos(fila, columna, signo, operando1, operando2);
        System.out.println(operando2.valor.trim() + " " + signo.valor.trim() + " " + operando1.valor.trim() + "\tGenera: " + tokenGenerado.valor);
        operandos.add(tokenGenerado);
    }

    private void operacionAsignacion() {
        String asignacion = "";
        Token operando1 = operandos.pop();
        Token operando2 = operandos.pop();
        Token signo = signos.pop();
        if (operando1.est == operando2.est) {
            asignacion = getNombreAsignacion(operando2) + " <- " + getNombreAsignacion(operando1);
            regla09(operando2, ambitos.peek());
            regla02(signo, operando1, "Acepta");
//            System.out.println(asignacion);
//            System.out.println("");
            guardarContadoresSem1(operando2);
            setAsignacionFinal(asignacion, operando2.linea);
        } else {
            errorxd.add(new LE("Tipo de datos incompatibles", operando2.valor, "Semantica 1", 704, operando2.linea));
            asignacion = getNombreAsignacion(operando2) + " <- " + getNombreAsignacion(operando1);
            regla02(signo, operando1, "Error");
//            System.out.println(asignacion);
//            System.out.println("");
            guardarContadoresSem1(operando2);
            setAsignacionFinal(asignacion, operando2.linea);
        }
    }

    private void setAsignacionFinal(String asignacion, int numLinea) {
        for (int j = 0; j < contaSem1.size(); j++) {
            if (contaSem1.get(j).getNumLinea() == numLinea) {
//                System.out.println("Entrooooooo alv");
                contaSem1.get(j).setAsignacion(asignacion);
                break;
            }
        }
    }

    private String getNombreAsignacion(Token token) {
        String nombre = "";
        int num = token.est;
        if (num == -3) {
            nombre = "Entero";
        } else if (num == -8) {
            nombre = "Flotante";
        } else if (num == -7) {
            nombre = "Cadena";
        } else if (num == -10) {
            nombre = "Caracter";
        } else if (num == -4) {
            nombre = "Binario";
        } else if (num == -5) {
            nombre = "Hexadecimal";
        } else if (num == -6) {
            nombre = "Octal";
        } else if (num == -9) {
            nombre = "Complejo";
        } else if (num == -104) {
            nombre = "Booleano";
        } else if (num == -66) {
            nombre = "Booleano";
        } else if (num == -67) {
            nombre = "Booleano";
        } else if (num == -96) {
            nombre = "Lista";
        } else if (num == -97) {
            nombre = "Arreglo";
        } else if (num == -98) {
            nombre = "Tupla";
        } else if (num == -99) {
            nombre = "Conjunto";
        } else if (num == -100) {
            nombre = "Diccionario";
        } else if (num == -101) {
            nombre = "None";
        } else if (num == -102) {
            nombre = "Rango";
        } else if (num == -103) {
            nombre = "Variant";
        }
        return nombre;
    }

    private Token compararOperandos(int fila, int columna, Token signo, Token op1, Token op2) {
        Token tokenGenerado = null;
        int numToken = 0;
        String datoObtenido = "";
        String valorToken = "";
        if (signo.est == -11) {//Suma
            datoObtenido = arrSuma[fila][columna];
            numToken = getNumeroToken(datoObtenido);
            if (numToken == -105) {//Se genera un error
                errorxd.add(new LE("Error al sumar", op2.valor + " <- " + op1.valor, "Semantica 1", 705, signo.linea));
                numToken = -103;//Lo convierte en Variant
                valorToken = "Variant" + tVariant;
                tVariant++;
            } else {
                valorToken = generarValorToken(datoObtenido);
            }
            tokenGenerado = new Token(numToken, valorToken, signo.linea);

        } else if (signo.est == -12) { //Resta
            datoObtenido = arrResta[fila][columna];
            numToken = getNumeroToken(datoObtenido);
            if (numToken == -105) {//Se genera un error
                errorxd.add(new LE("Error al restar", op2.valor + " <- " + op1.valor, "Semantica 1", 706, signo.linea));
                numToken = -103;//Lo convierte en Variant
                valorToken = "Variant" + tVariant;
                tVariant++;
            } else {
                valorToken = generarValorToken(datoObtenido);
            }
            tokenGenerado = new Token(numToken, valorToken, signo.linea);
        } else if (signo.est == -13) {//Multiplicacion
            datoObtenido = arrMulti[fila][columna];
            numToken = getNumeroToken(datoObtenido);
            if (numToken == -105) {//Se genera un error
                errorxd.add(new LE("Error al multiplicar", op2.valor + " <- " + op1.valor, "Semantica 1", 707, signo.linea));
                numToken = -103;//Lo convierte en Variant
                valorToken = "Variant" + tVariant;
                tVariant++;
            } else {
                valorToken = generarValorToken(datoObtenido);
            }
            tokenGenerado = new Token(numToken, valorToken, signo.linea);
        } else if (signo.est == -15) {//Divicion
            datoObtenido = arrDiv[fila][columna];
            numToken = getNumeroToken(datoObtenido);
            if (numToken == -105) {//Se genera un error
                errorxd.add(new LE("Error al dividir", op2.valor + " <- " + op1.valor, "Semantica 1", 708, signo.linea));
                numToken = -103;//Lo convierte en Variant
                valorToken = "Variant" + tVariant;
                tVariant++;
            } else {
                valorToken = generarValorToken(datoObtenido);
            }
            tokenGenerado = new Token(numToken, valorToken, signo.linea);
        } else if (isRelacional(signo)) {//Relacionales
            datoObtenido = arrRela[fila][columna];
            numToken = getNumeroToken(datoObtenido);
            if (numToken == -105) {//Se genera un error
                errorxd.add(new LE("Error al relacionar", op2.valor + " <- " + op1.valor, "Semantica 1", 709, op2.linea));
                numToken = -103;//Lo convierte en Variant
                valorToken = "Variant" + tVariant;
                tVariant++;
            } else {
                valorToken = generarValorToken(datoObtenido);
            }
            tokenGenerado = new Token(numToken, valorToken, signo.linea);
        } else if (isLogico(signo)) {//Op Logicos
            datoObtenido = arrOpera[fila][columna];
            numToken = getNumeroToken(datoObtenido);
            if (numToken == -105) {//Se genera un error
                errorxd.add(new LE("Error al realizar operacion logica", op2.valor + " <- " + op1.valor, "Semantica 1", 710, signo.linea));
                numToken = -103;//Lo convierte en Variant
                valorToken = "Variant" + tVariant;
                tVariant++;
            } else {
                valorToken = generarValorToken(datoObtenido);
            }
            tokenGenerado = new Token(numToken, valorToken, signo.linea);
        } else if (signo.est == -32 || signo.est == -33) {//Desplaza
            datoObtenido = arrDesplaza[fila][columna];
            numToken = getNumeroToken(datoObtenido);
            if (numToken == -105) {//Se genera un error
                errorxd.add(new LE("Error al Desplazar", op2.valor + " <- " + op1.valor, "Semantica 1", 711, signo.linea));
                numToken = -103;//Lo convierte en Variant
                valorToken = "Variant" + tVariant;
                tVariant++;
            } else {
                valorToken = generarValorToken(datoObtenido);
            }
            tokenGenerado = new Token(numToken, valorToken, signo.linea);
        } else if (signo.est == -16) {//Divicion Entero
            datoObtenido = arrDivEnt[fila][columna];
            numToken = getNumeroToken(datoObtenido);
            if (numToken == -105) {//Se genera un error
                errorxd.add(new LE("Error al realizar divicion entera", op2.valor + " <- " + op1.valor, "Semantica 1", 712, op2.linea));
                numToken = -103;//Lo convierte en Variant
                valorToken = "Variant" + tVariant;
                tVariant++;
            } else {
                valorToken = generarValorToken(datoObtenido);
            }
            tokenGenerado = new Token(numToken, valorToken, signo.linea);
        }
        return tokenGenerado;
    }

    private String generarValorToken(String datoObtenido) {
        String valorToken = "";
        if (datoObtenido.equals("TmpDec")) {
            valorToken = datoObtenido + tDec;
        } else if (datoObtenido.equals("TmpFloat")) {
            valorToken = datoObtenido + tFlot;
        } else if (datoObtenido.equals("TmpCadena")) {
            valorToken = datoObtenido + tCaden;
        } else if (datoObtenido.equals("TmpChar")) {
            valorToken = datoObtenido + tCarac;
        } else if (datoObtenido.equals("TmpBinario")) {
            valorToken = datoObtenido + tBin;
        } else if (datoObtenido.equals("TmpHex")) {
            valorToken = datoObtenido + tHex;
        } else if (datoObtenido.equals("TmpOctal")) {
            valorToken = datoObtenido + tOct;
        } else if (datoObtenido.equals("TmpComplej")) {
            valorToken = datoObtenido + tComple;
        } else if (datoObtenido.equals("TmpBool")) {
            valorToken = datoObtenido + tBool;
        } else if (datoObtenido.equals("TmpList")) {
            valorToken = datoObtenido + tLista;
        } else if (datoObtenido.equals("TmpArr")) {
            valorToken = datoObtenido + tArr;
        } else if (datoObtenido.equals("TmpTupla")) {
            valorToken = datoObtenido + tTup;
        } else if (datoObtenido.equals("TmpConjunto")) {
            valorToken = datoObtenido + tConj;
        } else if (datoObtenido.equals("TmpDicc")) {
            valorToken = datoObtenido + tDicc;
        } else if (datoObtenido.equals("TmpRango")) {
            valorToken = datoObtenido + tRango;
        } else if (datoObtenido.equals("Variant")) {
            valorToken = datoObtenido + tVariant;
        }

        return valorToken;
    }

    private Token buscarGenerarToken(Token token) {
        Token tokenGenerado = null;
        int numToken = 0;
        if (TablaSimbolos.buscar(token.valor.trim(), ambitos.peek() + "")) {
            regla11And12(token, ambitos.peek());
//            String tipo = TablaSimbolos.ObtenerTipo(token.valor.trim(), ambitos.peek()+"");
            numToken = getNumTokenBaseDatos(token, ambitos.peek());
            tokenGenerado = new Token(numToken, token.valor.trim(), token.linea);
        } else if (buscarIdEnAmbitosSem1(token)) {
            int ambitoEncontrado = obtenerAmbitoEncontradoSem1(token);
            regla11And12(token, ambitoEncontrado);
//            String tipo = TablaSimbolos.ObtenerTipo(token.valor.trim(), ambitoEncontrado+"");
            numToken = getNumTokenBaseDatos(token, ambitoEncontrado);
            tokenGenerado = new Token(numToken, token.valor.trim(), token.linea);
        } else {
            numToken = -103;
            tokenGenerado = new Token(numToken, token.valor.trim(), token.linea);
        }
        return tokenGenerado;
    }

    private int getNumTokenBaseDatos(Token token, int ambito) {
        int numToken = 0;
        String dato = TablaSimbolos.ObtenerTipo(token.valor.trim(), ambito + "");
        if ("Decimal".equals(dato)) {
            numToken = -3;
        } else if ("Binario".equals(dato)) {
            numToken = -4;
        } else if ("Hexadecimal".equals(dato)) {
            numToken = -5;
        } else if ("Octal".equals(dato)) {
            numToken = -6;
        } else if ("Cadena".equals(dato)) {
            numToken = -7;
        } else if ("Flotante".equals(dato)) {
            numToken = -8;
        } else if ("Complejo".equals(dato)) {
            numToken = -9;
        } else if ("Caracter".equals(dato)) {
            numToken = -10;
        } else if ("None".equals(dato)) {
            numToken = -101;
        } else if ("Booleano".equals(dato)) {
            numToken = -104;
        } else if ("Struct".equals(dato)) {
            dato = TablaSimbolos.ObtenerClase(token.valor.trim(), ambito + "");
            if ("Rango".equals(dato)) {
                numToken = -102;
            } else if ("Tupla".equals(dato)) {
                numToken = -98;
            } else if ("Conjunto".equals(dato)) {
                numToken = -99;
            } else if ("Diccionario".equals(dato)) {
                numToken = -100;
            } else if ("Arreglo".equals(dato)) {
                numToken = -97;
            } else if ("Funcion".equals(dato)) {
                dato = TablaSimbolos.ObtenerTipo(token.valor.trim(), ambito + "");
                if ("Rango".equals(dato)) {
                    numToken = -102;
                } else if ("Tupla".equals(dato)) {
                    numToken = -98;
                } else if ("Conjunto".equals(dato)) {
                    numToken = -99;
                } else if ("Diccionario".equals(dato)) {
                    numToken = -100;
                } else if ("Arreglo".equals(dato)) {
                    numToken = -97;
                }
            }
        }
        return numToken;
    }

    private int getNumeroToken(String dato) {
        int numToken = 0;
        if (dato.equals("TmpDec")) {
            numToken = -3;
            tDec++;
        } else if (dato.equals("TmpFloat")) {
            numToken = -8;
            tFlot++;
        } else if (dato.equals("TmpCadena")) {
            numToken = -7;
            tCaden++;
        } else if (dato.equals("TmpChar")) {
            numToken = -10;
            tCarac++;
        } else if (dato.equals("TmpBinario")) {
            numToken = -4;
            tBin++;
        } else if (dato.equals("TmpHex")) {
            numToken = -5;
            tHex++;
        } else if (dato.equals("TmpOctal")) {
            numToken = -6;
            tOct++;
        } else if (dato.equals("TmpComplej")) {
            numToken = -9;
            tComple++;
        } else if (dato.equals("TmpBool")) {
            numToken = -104;
            tBool++;
        } else if (dato.equals("TmpList")) {
            numToken = -96;
            tLista++;
        } else if (dato.equals("TmpArr")) {
            numToken = -97;
            tArr++;
        } else if (dato.equals("TmpTupla")) {
            numToken = -98;
            tTup++;
        } else if (dato.equals("TmpConjunto")) {
            numToken = -99;
            tConj++;
        } else if (dato.equals("TmpDicc")) {
            numToken = -100;
            tDicc++;
        } else if (dato.equals("TmpRango")) {
            numToken = -102;
            tRango++;
        } else if (dato.equals("Variant")) {
            numToken = -103;
            tVariant++;
        } else if (dato.equals("Err")) {
            numToken = -105;
        }

        return numToken;
    }

    private int getNumeroFilaColumna(Token token) {
        int num = 0;
        switch (token.est) {
            case -3:
                num = 0;
                break;
            case -4:
                num = 4;
                break;
            case -5:
                num = 5;
                break;
            case -6:
                num = 6;
                break;
            case -7:
                num = 2;
                break;
            case -8:
                num = 1;
                break;
            case -9:
                num = 7;
                break;
            case -10:
                num = 3;
                break;
            case -66:
            case -67:
            case -104:
                num = 8;
                break;
            case -96:
                num = 9;
                break;
            case -97:
                num = 10;
                break;
            case -98:
                num = 11;
                break;
            case -99:
                num = 12;
                break;
            case -100:
                num = 13;
                break;
            case -101:
                num = 14;
                break;
            case -102:
                num = 15;
                break;
            case -103:
                num = 16;
                break;
        }
        return num;
    }

    private boolean isOperando(Token token) {
        if (token.est < -2 && token.est > -11 || token.est == -66 || token.est == -67) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isSigno(Token token) {
        boolean b = true;
        if (token.est == -2) {
            b = true;
        } else if (token.est < -10 && token.est > -18) {
            b = true;
        } else if (token.est < -19 && token.est > -34) {
            b = true;
        } else if (token.est < -41 && token.est > -50) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    private boolean isLogico(Token token) {
        boolean b = false;
        if (token.est == -20) {
            b = true;
        } else if (token.est == -21) {
            b = true;
        } else if (token.est == -22) {
            b = true;
        } else if (token.est == -29) {
            b = true;
        } else if (token.est == -30) {
            b = true;
        } else if (token.est == -31) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    private boolean isRelacional(Token token) {
        boolean b = false;
        if (token.est == -23) {
            b = true;
        } else if (token.est == -24) {
            b = true;
        } else if (token.est == -25) {
            b = true;
        } else if (token.est == -26) {
            b = true;
        } else if (token.est == -27) {
            b = true;
        } else if (token.est == -28) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    private boolean isAsignacion(Token token) {
        boolean b = false;
        if (token.est == -42) {
            b = true;
        } else if (token.est == -43) {
            b = true;
        } else if (token.est == -44) {
            b = true;
        } else if (token.est == -45) {
            b = true;
        } else if (token.est == -46) {
            b = true;
        } else if (token.est == -47) {
            b = true;
        } else if (token.est == -48) {
            b = true;
        } else if (token.est == -48) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

//    private boolean isOpBit(Token token){
//        boolean b = false;
//        if(token.est == -29) b = true;
//        else if(token.est == -30) b = true;
//        else if(token.est == -31) b = true;
//        else b = false;
//        return b;
//    }
    private void guardarContadoresSem1(Token signo) {
        contaSem1.add(new ContadoresSemantica1(tDec, tBin, tHex, tOct, tFlot, tCaden, tCarac, tComple, tBool, tNone, tLista, tArr, tTup, tConj, tRango, tDicc, tVariant, signo.linea));
        limpiarContadoresSem1();
    }

    private void limpiarContadoresSem1() {
        tDec = 0;
        tBin = 0;
        tHex = 0;
        tOct = 0;
        tFlot = 0;
        tCaden = 0;
        tCarac = 0;
        tComple = 0;
        tBool = 0;
        tNone = 0;
        tLista = 0;
        tArr = 0;
        tTup = 0;
        tConj = 0;
        tRango = 0;
        tDicc = 0;
        tVariant = 0;
    }

    private void declarFuncion(Token token) {
        if (TablaSimbolos.buscar(token.valor.trim(), ambitos.peek() + "")) {
            errorxd.add(new LE("Funcion ya declarada", token.valor, "Ambito", 701, token.linea));
            regla13(token,"Error");
            TablaSimbolos.registrarProcedimiento("~"+token.valor.trim(), ambitos.peek() + "", (contAmbitos + 1) + "");
            tipoDeclaracion = 2;
        } else {
            TablaSimbolos.registrarProcedimiento(token.valor.trim(), ambitos.peek() + "", (contAmbitos + 1) + "");
            idsFuncPro.add(token);
            ambTempSem2.add(ambitos.peek());
            idTemp = token;
            ambTemp = ambitos.peek();
            regla13(token,"Acepta");
            tipoDeclaracion = 2;
        }

    }

    private void declarParamFun(Token token) {
        if (token.est == -1) {
            if (TablaSimbolos.buscar(token.valor.trim(), ambitos.peek() + "")) {
                errorxd.add(new LE("Parametro ya declarado", token.valor, "Ambito", 702, token.linea));
                regla13(token,"Error");
            } else {
                TablaSimbolos.registrarParamFunc(token.valor.trim(), ambitos.peek() + "", idTemp.valor, contParame + "");
                regla13(token,"Acepta");
                contParame++;
            }

        } else if (token.est == -37) {
            TablaSimbolos.actualizarFuncion(idTemp.valor.trim(), ambTemp + "", contParame + "");//Actualiza los datos de la funcion (
            tipoDeclaracion = 0;
            contParame = 0;
        }
    }

    private void declarVariable(Token token) {
        defTipoDato(token);
        TablaSimbolos.registrarVariable(idTemp.valor.trim(), tipoVar, ambitos.peek() + "");
        tipoDeclaracion = 0;
    }

    private void declarRango(Token token) {
        if (tipoDeclaracion == 4) {
            if (isConstEnt(token)) {
                par1 = token;
                tipoDeclaracion = 5;
                paramRangoAmbito += par1.valor.trim()+",";
            }
        } else if (tipoDeclaracion == 5) {
            if (isConstEnt(token)) {
                par2 = token;
                tipoDeclaracion = 6;
                paramRangoAmbito += par2.valor.trim()+",";
            }
        } else if (tipoDeclaracion == 6) {
            if (isConstEnt(token)) {
                par3 = token;
                TablaSimbolos.registrarRango(idTemp.valor.trim(), ambitos.peek() + "", par1.valor.trim(), par2.valor.trim(), par3.valor.trim());
                evaluarValoresRango(paramRangoAmbito.split(","),par3.linea,ambitos.peek());
                tipoDeclaracion = 0;
                paramRangoAmbito = "";
            }
        }
    }

    private void declarTupla() {
        TablaSimbolos.registrarTupla(idTemp.valor.trim(), ambitos.peek() + "", (contAmbitos + 1) + "");
        ambTemp = ambitos.peek();
        tipoDeclaracion = 8;
    }

    private void declarDatoTupla(Token token) {
        if (isConstante(token)) {
            TablaSimbolos.registrarDatoTupla(tipoVar, ambitos.peek() + "", contParame + "", idTemp.valor.trim());
            contParame++;
        } else if (token.est == -37) {
            TablaSimbolos.actualizarFuncion(idTemp.valor.trim(), ambTemp + "", contParame + "");//Actualiza los datos de la tupla (
            tipoDeclaracion = 0;
            contParame = 0;
        }

    }

    private void declarConjunDicc(Token token) {
        if (tipoDeclaracion == 8) {
            if (isConstante(token)) {
                parametros.add(token);
            } else if (token.est == -51) {
                tipoDeclaracion = 9;
            } else if (token.est == -41) {
                if (llaves.isEmpty()) {
                    TablaSimbolos.actualizarConjunDicc("Conjunto", parametros.size() + "", idTemp.valor.trim(), ambTemp + "");
                    for (int j = 0; j < parametros.size(); j++) {
                        TablaSimbolos.registrarDatoConjun(verifTipoDato(parametros.get(j)), ambitos.peek() + "", j + "", idTemp.valor.trim());
                    }
                    tipoDeclaracion = 0;
                    parametros.clear();
                } else {
                    TablaSimbolos.actualizarConjunDicc("Diccionario", parametros.size() + "", idTemp.valor.trim(), ambTemp + "");
                    for (int j = 0; j < parametros.size(); j++) {
                        TablaSimbolos.registrarDatoDicc(verifTipoDato(parametros.get(j)), ambitos.peek() + "", j + "", idTemp.valor.trim(), llaves.get(j).valor, parametros.get(j).valor);
                    }
                    tipoDeclaracion = 0;
                    parametros.clear();
                    llaves.clear();
                }
            }
        } else if (tipoDeclaracion == 9) {
            if (isConstante(token)) {
                llaves.add(token);
                tipoDeclaracion = 8;
            }
        }
    }

    private void declarArr(Token token) {
        if (tipoDeclaracion == 10) {
            if (isConstEnt(token)) {
                parametros.add(token);
                paramArrAmbito += token.valor.trim();
            } else if (token.est == -51) {
                paramArrAmbito += token.valor.trim();
            } else if (token.est == -12) {
                paramArrAmbito += token.valor.trim();
            } else if (token.est == -39) {
                String[] parametros = paramArrAmbito.split(":");
                evaluarValoresArreglo(parametros,token.linea,ambitos.peek());
                int tarr = parametros.length;
                TablaSimbolos.registrarArr(idTemp.valor.trim(), ambitos.peek() + "", tarr + "", paramArrAmbito);
                tipoDeclaracion = 0;
                paramArrAmbito = "";
            }
        }
    }

    private void defTipoDato(Token token) {
        switch (token.est) {
            case -3:
                tipoVar = "Decimal";
                break;
            case -4:
                tipoVar = "Binario";
                break;
            case -5:
                tipoVar = "Hexadecimal";
                break;
            case -6:
                tipoVar = "Octal";
                break;
            case -7:
                tipoVar = "Cadena";
                break;
            case -8:
                tipoVar = "Flotante";
                break;
            case -9:
                tipoVar = "Complejo";
                break;
            case -10:
                tipoVar = "Caracter";
                break;
            case -65:
                tipoVar = "None";
                break;
            case -66:
            case -67:
                tipoVar = "Booleano";
                break;
        }
    }

    private String getTipoDato(Token token) {
        String tipoDato = "";
        switch (token.est) {
            case -3:
                tipoDato = "Decimal";
                break;
            case -4:
                tipoDato = "Binario";
                break;
            case -5:
                tipoDato = "Hexadecimal";
                break;
            case -6:
                tipoDato = "Octal";
                break;
            case -7:
                tipoDato = "Cadena";
                break;
            case -8:
                tipoDato = "Flotante";
                break;
            case -9:
                tipoDato = "Complejo";
                break;
            case -10:
                tipoDato = "Caracter";
                break;
            case -65:
            case -101:
                tipoDato = "None";
                break;
            case -66:
            case -67:
            case -104:
                tipoDato = "Booleano";
                break;
            case -96:
                tipoDato = "Lista";
                break;
            case -97:
                tipoDato = "Arreglo";
                break;
            case -98:
                tipoDato = "Tupla";
                break;
            case -99:
                tipoDato = "Conjunto";
                break;
            case -100:
                tipoDato = "Diccionario";
                break;
            case -102:
                tipoDato = "Rango";
                break;
            case -103:
                tipoDato = "Variant";
                break;

        }
        return tipoDato;
    }

    private void buscarIdEnAmbitos(Token token) {
        boolean b = false;
        if (token.est == -1) {
            for (int j = 0; j < ambitos.size(); j++) {
                b = TablaSimbolos.buscar(token.valor.trim(), ambitos.get(j) + "");
                if (b) {
                    regla13(token,"Acepta");
                    break;
                }
            }
            if (!b) {
                errorxd.add(new LE("Identificador no declarado", token.valor, "Ambito", 703, token.linea));
                regla13(token,"Error");
            }
        }
    }

    private boolean buscarIdEnAmbitosSem1(Token token) {
        boolean b = false;
        if (token.est == -1) {
            for (int j = 0; j < ambitos.size(); j++) {
                b = TablaSimbolos.buscar(token.valor.trim(), ambitos.get(j) + "");
                if (b) {
                    break;
                }
            }
        }
        return b;
    }

    private int buscarIdEnAmbitosSem2(Token token) {
        boolean b = false;
        int amb = -1;
        if (token.est == -1) {
            for (int j = 0; j < ambitos.size(); j++) {
                b = TablaSimbolos.buscar(token.valor.trim(), ambitos.get(j) + "");
                if (b) {
                    amb = ambitos.get(j);
                    break;
                }
            }
        }
        return amb;
    }

    private int obtenerAmbitoEncontradoSem1(Token token) {
        int ambito = -1;
        boolean b = true;
        if (token.est == -1) {
            for (int j = 0; j < ambitos.size(); j++) {
                b = TablaSimbolos.buscar(token.valor.trim(), ambitos.get(j) + "");
                if (b) {
                    ambito = ambitos.get(j);
                    return ambito;
                }
            }
        }
        return ambito;
    }

    private void guardarContadoresAmbito() {
        int ambito = this.ambitos.peek();
        int Decimales = TablaSimbolos.contarTipos("Decimal", ambito + "");
        int Binareos = TablaSimbolos.contarTipos("Binareo", ambito + "");
        int Octales = TablaSimbolos.contarTipos("Octal", ambito + "");
        int Hexadecimales = TablaSimbolos.contarTipos("Hexadecimal", ambito + "");
        int Flotantes = TablaSimbolos.contarTipos("Flotante", ambito + "");
        int Cadenas = TablaSimbolos.contarTipos("Cadena", ambito + "");
        int Caracteres = TablaSimbolos.contarTipos("Caracter", ambito + "");
        int Complejas = TablaSimbolos.contarTipos("Complejo", ambito + "");
        int Booleanas = TablaSimbolos.contarTipos("Booleano", ambito + "");
        int Nones = TablaSimbolos.contarTipos("None", ambito + "");
        int Arreglos = TablaSimbolos.contarClases("Arreglo", ambito + "");
        int Tuplas = TablaSimbolos.contarClases("Tupla", ambito + "");
        int Listas = TablaSimbolos.contarClases("Lista", ambito + "");
        int Rangos = TablaSimbolos.contarClases("Rango", ambito + "");
        int Conjuntos = TablaSimbolos.contarClases("Conjunto", ambito + "");
        int Diccionarios = TablaSimbolos.contarClases("Diccionario", ambito + "");
        int ambitosAlcanzados = ambitos.size();
        ContadoresAmbito contador = new ContadoresAmbito(ambito, Decimales, Binareos, Octales, Hexadecimales, Flotantes, Cadenas, Caracteres, Complejas, Booleanas, Nones, Arreglos, Tuplas, Listas, Rangos, Conjuntos, Diccionarios, ambitosAlcanzados);
        contadoresAmbito.add(contador);
    }

    private boolean isConstEnt(Token token) {
        boolean b = false;
        switch (token.est) {
            case -3:
                b = true;
                break;
            case -4:
                b = true;
                break;
            case -5:
                b = true;
                break;
            case -6:
                b = true;
                break;
            default:
                b = false;
        }
        return b;
    }

    private boolean isConstante(Token token) {
        boolean b = false;
        switch (token.est) {
            case -3:
                b = true;
                tipoVar = "Decimal";
                break;
            case -4:
                b = true;
                tipoVar = "Binareo";
                break;
            case -5:
                b = true;
                tipoVar = "Hexadecimal";
                break;
            case -6:
                b = true;
                tipoVar = "Octal";
                break;
            case -7:
                b = true;
                tipoVar = "Cadena";
                break;
            case -8:
                b = true;
                tipoVar = "Flotante";
                break;
            case -9:
                b = true;
                tipoVar = "Complejo";
                break;
            case -10:
                b = true;
                tipoVar = "Caracter";
                break;
            case -65:
                b = true;
                tipoVar = "None";
                break;
            case -66:
            case -67:
                b = true;
                tipoVar = "Booleano";
                break;
            default:
                b = false;
        }
        return b;
    }

    private String verifTipoDato(Token token) {
        String tipoDato = "";
        switch (token.est) {
            case -3:
                tipoDato = "Decimal";
                break;
            case -4:
                tipoDato = "Binareo";
                break;
            case -5:
                tipoDato = "Hexadecimal";
                break;
            case -6:
                tipoDato = "Octal";
                break;
            case -7:
                tipoDato = "Cadena";
                break;
            case -8:
                tipoDato = "Flotante";
                break;
            case -9:
                tipoDato = "Complejo";
                break;
            case -10:
                tipoDato = "Caracter";
                break;
            case -65:
                tipoDato = "None";
                break;
            case -66:
            case -67:
                tipoDato = "Booleano";
                break;
        }
        return tipoDato;
    }

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

    public void sumarContadores(int estado) {
        if (estado >= 500 && estado <= 640) {
            ++cantidades[0];
        }
        if (estado == -1) {
            ++cantidades[1];
            identificador++;
        }
        if (estado == -52 || estado == -53) {
            ++cantidades[17];
        }
        if (estado <= -54 && estado >= -93 || estado == -95) {
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
        if (estado <= -20 && estado >= -22 || estado == -2) {
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

        if (estado <= -34 && estado >= -35 || estado == -50 || estado == -51) {
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

    public boolean prod(int i) {
        return i != 126 && i < 500;//qui pongo el numero de epsilon 
        // y que i sea menor a 500 para que agarre solo producciones validas
    }

    public void cargarProd(int n) {
        //System.out.println("CARGAPROD: " + n);
        for (int i = 0; i < listap[n - 1].length; i++) {//n-1
            pilaproducciones.push(listap[n - 1][i]);//n-1
        }
//        for (Integer p : pilaproducciones) {
//            //System.out.println("ps = " + p);
//        }
    }

    public boolean epsilon(int n) {
        return n == 126;//124 es mi numero de epsilon

    }

    public boolean error(int n) {
        return n > 500;
    }

    private void arrobasAmbito(int peek) {
        if (peek == 800) {
            zDeclaracion = false;
            pilaproducciones.pop();
        }
        if (peek == 801) {
            zDeclaracion = true;
            guardarContadoresAmbito();
            regla14And15();
            ambitos.pop();
            pilaproducciones.pop();
        }
        if (peek == 802) {
            if (zDeclaracion) {
                contAmbitos += 1;
                ambitos.add(contAmbitos);
            }
            pilaproducciones.pop();
        }
        if (peek == 803) {
            if (zDeclaracion) {
                guardarContadoresAmbito();
                ambitos.pop();
            }
            pilaproducciones.pop();
        }
    }

    private void arrobasSemantica1(int peek) {
        if (peek == 804) {
            generarTemp();
            pilaproducciones.pop();
        }
        if (peek == 805) {
            operacionAsignacion();
            pilaproducciones.pop();
        }
    }

    private void arrobasSemantica2(int peek) {
        if (peek == 806) {//if
            regla01("1010", operandos.pop());
            pilaproducciones.pop();
        }
        if (peek == 807) {//elif
            regla01("1012", operandos.pop());
            pilaproducciones.pop();
        }
        if (peek == 808) {//while
            regla01("1011", operandos.pop());
            pilaproducciones.pop();
        }
        if (peek == 809) {
            tempReturn = operandos.pop();
            isReturn = true;
            pilaproducciones.pop();
        }
        if (peek == 810) {
            zonaParam = true;
            pilaproducciones.pop();
        }
        if (peek == 811) {
            zonaParam = false;
            pilaproducciones.pop();
        }
        if (peek == 812) {
            if (!zDeclaracion) {
                operandos.pop();
            }
            pilaproducciones.pop();
        }
        if (peek == 813) {
            if (!zDeclaracion) {
                signos.pop();
            }
            pilaproducciones.pop();
        }
        if (peek == 814) {
            if (!zDeclaracion) {
                Token pop = operandos.pop();
                System.out.println("***********Valor expulsado " + pop.valor);
                if (pop.est == -3) {
                    listaSem2.add(new TablaSem2("1031", "Decimal", pop.valor.trim(), pop.linea, "Aceptar", ambitos.peek()));
                } else {
                    listaSem2.add(new TablaSem2("1031", "Decimal", pop.valor.trim(), pop.linea, "Error", ambitos.peek()));
                }
            }
            pilaproducciones.pop();
        }
    }

    public void limpiarVariables() {
        tokeen.removeAll(tokeen);
        errorxd.removeAll(errorxd);
        contadores.removeAll(contadores);
        contadorestotales.removeAll(contadorestotales);
        contadoresSin.removeAll(contadoresSin);
        aux = "";
        complej = 0;
        i = 0;
        pilaproducciones.push(pes);
        pilaproducciones.push(201);//<------ numero de bloque de tu PROGRAM
        for (int j = 0; j < 21; j++) {
            cantidades[j] = 0;
        }
        TablaSimbolos.borrarTabla();
        zDeclaracion = true;
        tipoDeclaracion = 0;
        parametros.clear();
        contAmbitos = 0;
        ambitos.clear();
        ambitos.add(0);
        contadoresAmbito.clear();
        contaSem1.clear();
        
        limpiarContadoresSem1();
        listaSem2.clear();
        idsFuncPro.clear();
        ambTempSem2.clear();
        tempReturn = null;
        isReturn = false;
        zonaParam = false;
        param = "";
        idTmpSem2 = null;
        ambEncontrado = -1;
        paramRango = "";
        paramArr = "";
    }
}
