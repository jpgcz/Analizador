
package analizador;


public class Token {
    int est;
    String valor;
    int linea;
   
  
    
    public Token(int est, String valor, int linea) {
        this.est = est;
        this.valor = valor;
        this.linea = linea;
        
    }

    Token() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    public int getEst() {
        return est;
    }

    public void setEst(int est) {
        this.est = est;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }
    
 
 
    public String getValor() {
        return valor;
    }
 
    public void setValor(String valor) {
        this.valor = valor;
    }

    
 
    /*
    //        String total = "Parrafo::=Frase A findelinea\n" +
//"A::=Frase A\n" +
//"A::=EPSILON\n" +
//"Frase::=Clausula B punto\n" +
//"B::=coma Clausula B\n" +
//"B::=EPSILON\n" +
//"Clausula::=Palabra C\n" +
//"C::=espacio Palabra C\n" +
//"C::=EPSILON\n" +
//"Palabra::=letra D\n" +
//"D::=letra D\n" +
//"D::=EPSILON";
/*total=
"Program::=program id ; Bloque\n" +
"Bloque::=A B C A begin Estatutos D end\n" +
"A::=var DeclaracionDeVariables ; E\n" +
"A::=EPSILON\n" +
"B::=function id ListaDeParametros : Tipo ; F\n" +
"B::=procedure id ListaDeParametros : Tipo ; F\n" +
"B::=EPSILON\n" +
"C::=use id : = DeclaracionConstantes ; G\n" +
"C::=EPSILON\n" +
"D::=; Estatutos D\n" +
"D::=EPSILON\n" +
"E::=DeclaracionDeVariables ; E\n" +
"E::=EPSILON\n" +
"F::=Bloque\n" +
"F::=forward ;\n" +
"G::=id : = DeclaracionConstantes ; G\n" +
"G::=EPSILON\n" +
"Estatutos::=write H\n" +
"H::=( ExpPascal I )\n" +
"H::=EPSILON\n" +
"I::=, ExpPascal I )\n" +
"I::=EPSILON\n" +
"Estatutos::=read ( id J )\n" +
"J::=Arreglo\n" +
"J::=EPSILON\n"+
"Estatutos::=if ExpPascal then Estatutos K\n" +
"K::=else Estatutos\n" +
"K::=EPSILON\n" +
"Estatutos::=repeat Estatutos ; L until ExpPascal\n" +
"L::=Estatutos ; L\n" +
"L::=EPSILON\n" +
"Estatutos::=for id ; = DeclaracionConstantes to DeclaracionConstantes Estatutos fnpara\n" +
"Estatutos::=while ExpPascal do Estatutos\n" +
"Estatutos::=begin Estatutos ; M end\n" +
"M::=Estatutos ; M\n" +
"M::=EPSILON\n" +
"Estatutos::=case ExpPascal of CostanteSSigno N : Estatutos O end\n" +
"N::=, CostanteSSigno N\n" +
"N::=EPSILON\n" +
"O::=else Estatutos\n" +
"O::=romper CostanteSSigno N : Estatutos O\n" +
"O::=EPSILON\n" +
"Estatutos::=retur ExpPascal\n" +
"Estatutos::=ExpPascal\n" +
"Estatutos::=EPSILON\n" +
"ExpPascal::=SimpleExpPascal P\n" +//P
"P::=< ExpPascal\n" +
"P::=<= ExpPascal\n" +
"P::=== ExpPascal\n" +
"P::=!= ExpPascal\n" +
"P::=>= ExpPascal\n" +
"P::=> ExpPascal\n" +
"P::=EPSILON\n" +
"SimpleExpPascal::=Q TerminoPascal R S\n" +// R S
"Q::=+\n" +
"Q::=-\n" +
"Q::=EPSILON\n" +
"R::=|| TerminoPascal R S\n" +
"R::=EPSILON\n" +
"S::=+ TerminoPascal R S\n" +
"S::=- TerminoPascal R S\n" +
"S::=EPSILON\n" +
"TerminoPascal::=Elevacion T\n" +
"T::=*\n" +
"T::=/\n" +
"T::=#\n" +
"T::=&&\n" +
"T::=EPSILON\n" +
"Elevacion::=FactorPascal U\n" +
"U::=^\n" +
"U::=EPSILON\n" +
"FactorPascal::=CostanteSSigno\n" +
"FactorPascal::=id V\n" +
"FactorPascal::=++ id V\n" +
"FactorPascal::=-- id V\n" +
"V::=( X )\n" +
"V::=++\n" +
"V::=--\n" +
"V::=Arreglo W\n" +
"V::=EPSILON\n" +
"W::=Asignacion ExpPascal\n" +
"W::=EPSILON\n" +
"X::=ExpPascal Y\n" +
"X::=EPSILON\n" +
"Y::=, ExpPascal Y\n" +
"Y::=EPSILON\n" +
"FactorPascal::=! ( ExpPascal )\n" +
"FactorPascal::=Funcion\n" +
"CostanteSSigno::=constreal\n" +
"CostanteSSigno::=constcadena\n" +
"CostanteSSigno::=constcaracter\n" +
"CostanteSSigno::=constentero\n" +       
"CostanteSSigno::=constexponencial\n" +
"CostanteSSigno::=true\n" +
"CostanteSSigno::=false\n" +
"Arreglo::=[ AA ] AB\n" +
"AA::=todo\n" +
"AA::=ExpPascal\n" +
"AB::=[ AA ] AB\n" +
"AB::=EPSILON\n" +      
"Asignacion::==\n" +
"Asignacion::=+=\n" +
"Asignacion::=/=\n" +
"Asignacion::=*=\n" +
"Asignacion::=-=\n" +
"Funcion::=limpiarpantalla ( )\n" +
"Funcion::=sqrt ( ExpPascal )\n" +
"Funcion::=sqr ( ExpPascal )\n" +
"Funcion::=expo ( ExpPascal , ExpPascal )\n" +
"Funcion::=strcmp ( ExpPascal , ExpPascal )\n" +
"Funcion::=strcat ( ExpPascal , ExpPascal )\n" +
"Funcion::=strcpy ( ExpPascal , ExpPascal )\n" +
"Funcion::=strins ( ExpPascal , ExpPascal , ExpPascal )\n" +
"Funcion::=strlen ( ExpPascal )\n" +
"Funcion::=toupper ( ExpPascal )\n" +
"Funcion::=tolower ( ExpPascal )\n" +
"Funcion::=open ( id , ExpPascal , ExpPascal )\n" +
"Funcion::=close ( id )\n" +
"Funcion::=scanf ( id , ExpPascal AD )\n" +
"Funcion::=printf ( id , ExpPascal AD )\n" +
"AD::=, ExpPascal AD\n" +
"AD::=, ExpPascal AD\n" +
"AD::=EPSILON\n" +
"Funcion::=asc ExpPascal\n" +
"Funcion::=chr ExpPascal\n" +
"DeclaracionDeVariables::=id AE : Tipo\n" +
"AE::=, id AE\n" +
"AE::=EPSILON\n"+
"Tipo::=char AG\n"+
"Tipo::=exp AG\n"+
"Tipo::=integer AG\n"+
"Tipo::=string AG\n"+
"Tipo::=boolean AG\n"+
"Tipo::=file AG\n"+
"AG::=( ExpPascal . . ExpPascal AH )\n" +
"AG::=EPSILON\n" +
"AH::=, ExpPascal . . ExpPascal AH\n" +
"AH::=EPSILON\n" +
"DeclaracionConstantes::=+ AI\n" +
"DeclaracionConstantes::=- AI\n" +
"AI::=constreal\n" +
"AI::=constentero\n" +
"DeclaracionConstantes::=CostanteSSigno\n" +
"ListaDeParametros::=( AJ )\n" +
"AJ::=DeclaracionDeVariables AK\n" +
"AJ::=EPSILON\n" +
"AK::=; DeclaracionDeVariables AK\n"+
"AK::=EPSILON";*/
 
    /*total =  "Program::=program id ; Bloque\n" +
"Bloque::=A B C A begin Estatutos D end\n" +
"A::=var DeclaracionVariable ; E\n" +
"A::=EPSILON\n" +
"B::=function id Parametros : Tipo ; F \n" +
"B::=procedure id Parametros : Tipo ; F\n" +
"B::=EPSILON\n" +
"C::=use id : = DeclaracionConstantes ; G\n" +
"C::=EPSILON\n" +
"D::=; Estatutos D\n" +
"D::=EPSILON\n" +
"E::=DeclaracionVariable ; E\n" +
"E::=EPSILON\n" +
"F::=Bloque B\n" +
"F::=forward ; B\n" +
"G::=id : = DeclaracionConstantes ; G\n" +
"G::=EPSILON\n" +
"Estatutos::=write H\n" +
"Estatutos::=read ( id I )\n" +
"Estatutos::=if ExpPascal then Estatutos IfT\n" +
"Estatutos::=repeat Estatutos ; RepeatT until ExpPascal\n" +
"Estatutos::=for id ; = DeclaracionConstantes to DeclaracionConstantes Estatutos fnpara\n" +
"Estatutos::=while ExpPascal do Estatutos\n" +
"Estatutos::=begin Estatutos ; BeginT end\n" +
"Estatutos::=case ExpPascal of ConstanteSSigno CaseT : Estatutos CaseT2 end\n" +
"Estatutos::=retur ExpPascal\n" +
"Estatutos::=ExpPascal\n" +
"Estatutos::=EPSILON\n" +
"H::=( ExpPascal H2 )\n" +
"H::=EPSILON\n" +
"H2::=, ExpPascal H2\n" +
"H2::=EPSILON\n" +
"I::=Arreglo\n" +
"I::=EPSILON\n" +
"IfT::=else Estatutos\n" +
"IfT::=EPSILON\n" +
"RepeatT::=Estatutos ; RepeatT\n" +
"RepeatT::=EPSILON\n" +
"BeginT::=Estatutos ; BeginT\n" +
"BeginT::=EPSILON\n" +
"CaseT::=, ConstanteSSigno CaseT\n" +
"CaseT::=EPSILON\n" +
"CaseT2::=else Estatutos\n" +
"CaseT2::=romper ConstanteSSigno CaseT : Estatutos CaseT2\n" +
"CaseT2::=EPSILON\n" +
"DeclaracionVariable::=id DV : Tipo\n" +
"DV::=, id DV\n" +
"DV::=EPSILON\n" +
"Parametros::=( Parametros1 )\n" +
"Parametros1::=DeclaracionVariable Parametros2\n" +
"Parametros1::=EPSILON\n" +
"Parametros2::=; DeclaracionVariable Parametros2\n" +
"Parametros2::=EPSILON\n" +
"Tipo::=char Tipo1\n" +
"Tipo::=exp Tipo1\n" +
"Tipo::=integer Tipo1\n" +
"Tipo::=real Tipo1\n" +
"Tipo::=string Tipo1\n" +
"Tipo::=bool Tipo1\n" +
"Tipo::=file Tipo1\n" +
"Tipo1::=( ExpPascal . . ExpPascal Tipo2 )\n" +
"Tipo1::=EPSILON\n" +
"Tipo2::=, ExpPascal . . ExpPascal Tipo2\n" +
"Tipo2::=EPSILON \n" +
"DeclaracionConstantes::=+ DC\n" +
"DeclaracionConstantes::=- DC\n" +
"DeclaracionConstantes::=ConstanteSSigno\n" +
"DC::=constreal\n" +
"DC::=constentero\n" +
"ExpPascal::=SimpleExpPascal ExpPascal2\n" +
"ConstanteSSigno::=constreal\n" +
"ConstanteSSigno::=constcadena\n" +
"ConstanteSSigno::=constcaracter\n" +
"ConstanteSSigno::=constentero\n" +
"ConstanteSSigno::=constexponencial\n" +
"ConstanteSSigno::=true\n" +
"ConstanteSSigno::=false\n" +
"Arreglo::=[ Arreglo1 ] Arreglo2\n" +
"Arreglo1::=todo\n" +
"Arreglo1::=ExpPascal\n" +
"Arreglo2::=[ Arreglo1 ] Arreglo2\n" +
"Arreglo2::=EPSILON \n" +
"SimpleExpPascal::=+ TerminoPascal SimpleExpPascal2\n" +
"SimpleExpPascal::=- TerminoPascal SimpleExpPascal2\n" +
"SimpleExpPascal::=TerminoPascal SimpleExpPascal2\n" +
"SimpleExpPascal2::=|| TerminoPascal SimpleExpPascal2\n" +
"SimpleExpPascal2::=+ TerminoPascal SimpleExpPascal2\n" +
"SimpleExpPascal2::=- TerminoPascal SimpleExpPascal2\n" +
"SimpleExpPascal2::=EPSILON\n" +
"ExpPascal2::=< SimpleExpPascal ExpPascal2\n" +
"ExpPascal2::=<= SimpleExpPascal ExpPascal2\n" +
"ExpPascal2::=== SimpleExpPascal ExpPascal2\n" +
"ExpPascal2::=!= SimpleExpPascal ExpPascal2\n" +
"ExpPascal2::=>= SimpleExpPascal ExpPascal2\n" +
"ExpPascal2::=> SimpleExpPascal ExpPascal2\n" +
"ExpPascal2::=EPSILON\n" +
"TerminoPascal::=Elevacion TerminoPascal1\n" +
"TerminoPascal1::=* Elevacion TerminoPascal1\n" +
"TerminoPascal1::=/ Elevacion TerminoPascal1\n" +
"TerminoPascal1::=# Elevacion TerminoPascal1\n" +
"TerminoPascal1::=&& Elevacion TerminoPascal1\n" +
"TerminoPascal1::=EPSILON\n" +
"Elevacion::=FactorPascal Elevacion1\n" +
"Elevacion1::=^ FactorPascal Elevacion1\n" +
"Elevacion1::=EPSILON \n" +
"FactorPascal::=ConstanteSSigno\n" +
"FactorPascal::=id IdT\n" +
"FactorPascal::=++ id IdT\n" +
"FactorPascal::=-- id IdT\n" +
"FactorPascal::=! ( ExpPascal )\n" +
"FactorPascal::=Funcion\n" +
"IdT::=Arreglo Cosa\n" +
"IdT::=( IdT2 )\n" +
"IdT::=++\n" +
"IdT::=--\n" +
"IdT::=EPSILON\n" +
"Cosa::=Asignacion ExpPascal\n" +
"Cosa::=EPSILON \n" +
"Asignacion::==\n" +
"Asignacion::=+=\n" +
"Asignacion::=/=\n" +
"Asignacion::=*=\n" +
"Asignacion::=-=\n" +
"IdT2::=ExpPascal IdT3\n" +
"IdT2::=EPSILON \n" +
"IdT3::=, ExpPascal IdT3\n" +
"IdT3::=EPSILON \n" +
"Funcion::=limpiarpantalla ( )\n" +
"Funcion::=sqrt ( ExpPascal )\n" +
"Funcion::=sqr ( ExpPascal )\n" +
"Funcion::=expo ( ExpPascal , ExpPascal )\n" +
"Funcion::=strcmp ( ExpPascal , ExpPascal )\n" +
"Funcion::=strcat ( ExpPascal , ExpPascal )\n" +
"Funcion::=strcpy ( ExpPascal , ExpPascal )\n" +
"Funcion::=strins ( ExpPascal, ExpPascal , ExpPascal )\n" +
"Funcion::=strlen ( ExpPascal )\n" +
"Funcion::=toupper (ExpPascal )\n" +
"Funcion::=tolower ( ExpPascal )\n" +
"Funcion::=open ( id , ExpPascal, ExpPascal )\n" +
"Funcion::=close ( id )\n" +
"Funcion::=scanf ( id , ExpPascal ScanfT )\n" +
"Funcion::=printf ( id , ExpPascal ScanfT )\n" +
"Funcion::=asc ExpPascal\n" +
"Funcion::=chr ExpPascal\n" +
"ScanfT::=, ExpPascal ScanfT\n" +
"ScanfT::=EPSILON";
 
    */
    
    

 
 
    

}
