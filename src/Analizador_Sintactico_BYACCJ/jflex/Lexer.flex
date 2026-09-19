package src.Analizador_Sintactico_BYACCJ;

import src.Analizador_Sintactico_BYACCJ.jflex.Token;
import src.Analizador_Sintactico_BYACCJ.jflex.ClaseLexica;
import src.Analizador_Sintactico_BYACCJ.utils.Colors;

%%

/**
 * Analizador Léxico para el Lenguaje
 * 
 * Implementa el análisis léxico para un lenguaje de programación.
 * Reconoce y clasifica los tokens del lenguaje, incluyendo:
 * 
 * Tipos básicos:
 * - Enteros, flotantes, dobles, complejos
 * - Runas (caracteres Unicode)
 * - Cadenas de texto
 * - Booleanos
 * 
 * Estructuras de control:
 * - Condicionales (if, else)
 * - Bucles (while, do)
 * - Switch-case
 * 
 * Declaraciones:
 * - Variables y funciones
 * - Prototipos
 * - Estructuras
 * 
 * @author steve-quezada
 * @author etnicst
 */

%{
/**
 * Referencia al token actual en proceso
 */
public Token actual;

/**
 * Control del estado de fin de archivo
 */
private boolean eofAlcanzado = false;

/**
 * Obtiene el número de línea actual del análisis
 * @return número de línea actual
 */
public int getLine() { 
    return yyline + 1; 
}

/**
 * Imprime información detallada sobre el token actual
 * @param lexema texto del token
 * @param claseLexica tipo del token
 */
public void printLexicalState(String lexema, ClaseLexica claseLexica) {
    String color = claseLexica.getColor();
    int valor = claseLexica.ordinal() + 257;
    Colors.printToken(lexema, claseLexica.getNombre(), getLine(), valor, color);
}
%}

%public
%class Lexer
%unicode
%line
%type Token

/* Definiciones regulares básicas */
espacio = [ \t\n\r]
letra = [a-záéíóúüñA-ZÁÉÍÓÚÜÑ_]
digito = [0-9]
exponente = [eE][+-]?{digito}+
identificador = {letra}({letra}|{digito})*
entero = {digito}+
flotante = {digito}+"."{digito}+{exponente}?
doble = {digito}+"."{digito}+{exponente}?[dD]
complejo_simple = ({entero}|{flotante})[iI]
complejo_doble = ({entero}|{doble})[iI]
cadena = \"([^\"\\]|\\.)*\"
runa = \'([^\'\\]|\\[ntr\"\'\\]|\\u[0-9a-fA-F]{4}|[@#$%&*+=<>?¿¡!])\'

%%

/* Reglas léxicas */

{espacio}+ { /* Ignorar espacios en blanco */ }

// Palabras reservadas
"func"      { printLexicalState(yytext(), ClaseLexica.FUNC); return new Token(ClaseLexica.FUNC, yytext(), getLine()); }
"int"       { printLexicalState(yytext(), ClaseLexica.INT); return new Token(ClaseLexica.INT, yytext(), getLine()); }
"float"     { printLexicalState(yytext(), ClaseLexica.FLOAT); return new Token(ClaseLexica.FLOAT, yytext(), getLine()); }
"double"    { printLexicalState(yytext(), ClaseLexica.DOUBLE); return new Token(ClaseLexica.DOUBLE, yytext(), getLine()); }
"complex"   { printLexicalState(yytext(), ClaseLexica.COMPLEX); return new Token(ClaseLexica.COMPLEX, yytext(), getLine()); }
"rune"      { printLexicalState(yytext(), ClaseLexica.RUNE); return new Token(ClaseLexica.RUNE, yytext(), getLine()); }
"void"      { printLexicalState(yytext(), ClaseLexica.VOID); return new Token(ClaseLexica.VOID, yytext(), getLine()); }
"string"    { printLexicalState(yytext(), ClaseLexica.STRING); return new Token(ClaseLexica.STRING, yytext(), getLine()); }
"struct"    { printLexicalState(yytext(), ClaseLexica.STRUCT); return new Token(ClaseLexica.STRUCT, yytext(), getLine()); }
"ptr"       { printLexicalState(yytext(), ClaseLexica.PTR); return new Token(ClaseLexica.PTR, yytext(), getLine()); }
"if"        { printLexicalState(yytext(), ClaseLexica.IF); return new Token(ClaseLexica.IF, yytext(), getLine()); }
"else"      { printLexicalState(yytext(), ClaseLexica.ELSE); return new Token(ClaseLexica.ELSE, yytext(), getLine()); }
"while"     { printLexicalState(yytext(), ClaseLexica.WHILE); return new Token(ClaseLexica.WHILE, yytext(), getLine()); }
"do"        { printLexicalState(yytext(), ClaseLexica.DO); return new Token(ClaseLexica.DO, yytext(), getLine()); }
"break"     { printLexicalState(yytext(), ClaseLexica.BREAK); return new Token(ClaseLexica.BREAK, yytext(), getLine()); }
"return"    { printLexicalState(yytext(), ClaseLexica.RETURN); return new Token(ClaseLexica.RETURN, yytext(), getLine()); }
"switch"    { printLexicalState(yytext(), ClaseLexica.SWITCH); return new Token(ClaseLexica.SWITCH, yytext(), getLine()); }
"case"      { printLexicalState(yytext(), ClaseLexica.CASE); return new Token(ClaseLexica.CASE, yytext(), getLine()); }
"default"   { printLexicalState(yytext(), ClaseLexica.DEFAULT); return new Token(ClaseLexica.DEFAULT, yytext(), getLine()); }
"print"     { printLexicalState(yytext(), ClaseLexica.PRINT); return new Token(ClaseLexica.PRINT, yytext(), getLine()); }
"scan"      { printLexicalState(yytext(), ClaseLexica.SCAN); return new Token(ClaseLexica.SCAN, yytext(), getLine()); }
"true"      { printLexicalState(yytext(), ClaseLexica.TRUE); return new Token(ClaseLexica.TRUE, yytext(), getLine()); }
"false"     { printLexicalState(yytext(), ClaseLexica.FALSE); return new Token(ClaseLexica.FALSE, yytext(), getLine()); }
"proto"     { printLexicalState(yytext(), ClaseLexica.PROTO); return new Token(ClaseLexica.PROTO, yytext(), getLine()); }

// Operadores
"||"        { printLexicalState(yytext(), ClaseLexica.OR); return new Token(ClaseLexica.OR, yytext(), getLine()); }
"&&"        { printLexicalState(yytext(), ClaseLexica.AND); return new Token(ClaseLexica.AND, yytext(), getLine()); }
"=="        { printLexicalState(yytext(), ClaseLexica.IGUAL); return new Token(ClaseLexica.IGUAL, yytext(), getLine()); }
"!="        { printLexicalState(yytext(), ClaseLexica.DIFERENTE); return new Token(ClaseLexica.DIFERENTE, yytext(), getLine()); }
"<="        { printLexicalState(yytext(), ClaseLexica.MENORIGUAL); return new Token(ClaseLexica.MENORIGUAL, yytext(), getLine()); }
">="        { printLexicalState(yytext(), ClaseLexica.MAYORIGUAL); return new Token(ClaseLexica.MAYORIGUAL, yytext(), getLine()); }
"//"        { printLexicalState(yytext(), ClaseLexica.DIVINT); return new Token(ClaseLexica.DIVINT, yytext(), getLine()); }
"<"         { printLexicalState(yytext(), ClaseLexica.MENOR); return new Token(ClaseLexica.MENOR, yytext(), getLine()); }
">"         { printLexicalState(yytext(), ClaseLexica.MAYOR); return new Token(ClaseLexica.MAYOR, yytext(), getLine()); }
"+"         { printLexicalState(yytext(), ClaseLexica.MAS); return new Token(ClaseLexica.MAS, yytext(), getLine()); }
"-"         { printLexicalState(yytext(), ClaseLexica.MENOS); return new Token(ClaseLexica.MENOS, yytext(), getLine()); }
"*"         { printLexicalState(yytext(), ClaseLexica.POR); return new Token(ClaseLexica.POR, yytext(), getLine()); }
"/"         { printLexicalState(yytext(), ClaseLexica.DIV); return new Token(ClaseLexica.DIV, yytext(), getLine()); }
"%"         { printLexicalState(yytext(), ClaseLexica.MOD); return new Token(ClaseLexica.MOD, yytext(), getLine()); }
"!"         { printLexicalState(yytext(), ClaseLexica.NOT); return new Token(ClaseLexica.NOT, yytext(), getLine()); }
"="         { printLexicalState(yytext(), ClaseLexica.ASIG); return new Token(ClaseLexica.ASIG, yytext(), getLine()); }
"."         { printLexicalState(yytext(), ClaseLexica.PUNTO); return new Token(ClaseLexica.PUNTO, yytext(), getLine()); }

// Delimitadores
"("         { printLexicalState(yytext(), ClaseLexica.PARI); return new Token(ClaseLexica.PARI, yytext(), getLine()); }
")"         { printLexicalState(yytext(), ClaseLexica.PARD); return new Token(ClaseLexica.PARD, yytext(), getLine()); }
"["         { printLexicalState(yytext(), ClaseLexica.CORI); return new Token(ClaseLexica.CORI, yytext(), getLine()); }
"]"         { printLexicalState(yytext(), ClaseLexica.CORD); return new Token(ClaseLexica.CORD, yytext(), getLine()); }
"{"         { printLexicalState(yytext(), ClaseLexica.LLAI); return new Token(ClaseLexica.LLAI, yytext(), getLine()); }
"}"         { printLexicalState(yytext(), ClaseLexica.LLAD); return new Token(ClaseLexica.LLAD, yytext(), getLine()); }
","         { printLexicalState(yytext(), ClaseLexica.COMA); return new Token(ClaseLexica.COMA, yytext(), getLine()); }
";"         { printLexicalState(yytext(), ClaseLexica.PYC); return new Token(ClaseLexica.PYC, yytext(), getLine()); }
":"         { printLexicalState(yytext(), ClaseLexica.DOSP); return new Token(ClaseLexica.DOSP, yytext(), getLine()); }

// Literales
{identificador} { printLexicalState(yytext(), ClaseLexica.ID); return new Token(ClaseLexica.ID, yytext(), getLine()); }
{entero}        { printLexicalState(yytext(), ClaseLexica.LIT_ENTERO); return new Token(ClaseLexica.LIT_ENTERO, yytext(), getLine()); }
{flotante}      { printLexicalState(yytext(), ClaseLexica.LIT_FLOAT); return new Token(ClaseLexica.LIT_FLOAT, yytext(), getLine()); }
{doble}         { printLexicalState(yytext(), ClaseLexica.LIT_DOUBLE); return new Token(ClaseLexica.LIT_DOUBLE, yytext(), getLine()); }
{complejo_simple} { 
    printLexicalState(yytext(), ClaseLexica.LIT_COMPLEX); 
    // Extraer parte real e imaginaria
    String valor = yytext();
    String parteReal = "0";
    String parteImaginaria = valor.substring(0, valor.length()-1); // Quitar la 'i'
    return new Token(ClaseLexica.LIT_COMPLEX, "Complex(" + parteReal + "," + parteImaginaria + ")", getLine()); 
}
{complejo_doble} { 
    printLexicalState(yytext(), ClaseLexica.LIT_COMPLEX);
    // Extraer parte real e imaginaria para complejos como (a+bi)
    String valor = yytext().substring(1, yytext().length()-1); // Quitar paréntesis
    String[] partes = valor.split("\\+");
    String parteReal = partes[0].trim();
    String parteImaginaria = partes[1].substring(0, partes[1].length()-1).trim(); // Quitar la 'i'
    return new Token(ClaseLexica.LIT_COMPLEX, "Complex(" + parteReal + "," + parteImaginaria + ")", getLine());
}
{cadena}        { printLexicalState(yytext(), ClaseLexica.LIT_STRING); return new Token(ClaseLexica.LIT_STRING, yytext(), getLine()); }
{runa}          { printLexicalState(yytext(), ClaseLexica.LIT_RUNE); return new Token(ClaseLexica.LIT_RUNE, yytext(), getLine()); }

/**
 * Manejo de fin de archivo
 * Genera un token EOF solo la primera vez que se alcanza el final
 */
<<EOF>> { 
    if (!eofAlcanzado) {
        eofAlcanzado = true;
        printLexicalState("EOF", ClaseLexica.EOF); 
        return new Token(ClaseLexica.EOF, "EOF", getLine());
    }
    return null;
}

/**
 * Manejo de errores léxicos
 * Genera un token de error para caracteres no reconocidos
 */
. { 
    System.out.println();
    Colors.println("\nError: Símbolo no reconocido '" + yytext() + 
                   "' en línea " + getLine() + ".", Colors.RED); 
    System.out.println();
    return new Token(ClaseLexica.UNKNOWN, yytext(), getLine()); 
}
