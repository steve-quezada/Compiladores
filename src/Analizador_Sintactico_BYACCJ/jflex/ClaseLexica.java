package src.Analizador_Sintactico_BYACCJ.jflex;

import src.Analizador_Sintactico_BYACCJ.utils.Colors;

/**
 * Enumeración que define las categorías léxicas del lenguaje.
 * Cada constante representa un tipo de token diferente y se asocia
 * con un nombre descriptivo y un color para su visualización.
 * 
 * Los tokens se agrupan en las siguientes categorías:
 * - Tipos de datos básicos (INT, FLOAT, etc.)
 * - Palabras reservadas (IF, WHILE, etc.)
 * - Identificadores y literales
 * - Operadores y delimitadores
 * - Tokens especiales (EOF, UNKNOWN)
 * 
 * @author steve-quezada
 * @author etnicst
 */
public enum ClaseLexica {
    // Tipos de datos básicos
    INT("INT", Colors.GOLD),
    FLOAT("FLOAT", Colors.GREEN),
    DOUBLE("DOUBLE", Colors.BLUE),
    COMPLEX("COMPLEX", Colors.MAGENTA),
    RUNE("RUNE", Colors.CYAN),
    VOID("VOID", Colors.PURPLE),
    STRING("STRING", Colors.ORANGE),
    STRUCT("STRUCT", Colors.BROWN),
    PTR("PTR", Colors.GRAY_DARK),

    // Palabras reservadas
    FUNC("FUNC", Colors.BLUE_LIGHT),
    IF("IF", Colors.YELLOW),
    ELSE("ELSE", Colors.MAGENTA_LIGHT),
    WHILE("WHILE", Colors.TEAL),
    DO("DO", Colors.LIME),
    BREAK("BREAK", Colors.CORAL),
    RETURN("RETURN", Colors.TURQUOISE),
    SWITCH("SWITCH", Colors.INDIGO),
    CASE("CASE", Colors.SALMON),
    DEFAULT("DEFAULT", Colors.MINT),
    PRINT("PRINT", Colors.EMERALD),
    SCAN("SCAN", Colors.CHOCOLATE),
    TRUE("TRUE", Colors.PLUM),
    FALSE("FALSE", Colors.AQUAMARINE),
    PROTO("PROTO", Colors.NAVY),

    // Identificadores y literales
    ID("ID", Colors.CYAN),
    LIT_ENTERO("LIT_ENTERO", Colors.BRIGHT_EMERALD),
    LIT_FLOAT("LIT_FLOAT", Colors.STEEL_GRAY),
    LIT_DOUBLE("LIT_DOUBLE", Colors.SAND),
    LIT_COMPLEX("LIT_COMPLEX", Colors.LIGHT_LAVENDER),
    LIT_STRING("LIT_STRING", Colors.FOREST_GREEN),
    LIT_RUNE("LIT_RUNE", Colors.SKY_BLUE),

    // Operadores
    OR("OR", Colors.DARK_PURPLE),
    AND("AND", Colors.LIGHT_PINK),
    IGUAL("IGUAL", Colors.ICE_BLUE),
    DIFERENTE("DIFERENTE", Colors.SLATE_GRAY),
    MENOR("MENOR", Colors.MOSS_GREEN),
    MENORIGUAL("MENORIGUAL", Colors.OLD_GOLD),
    MAYORIGUAL("MAYORIGUAL", Colors.LAVENDER),
    MAYOR("MAYOR", Colors.BEIGE),
    MAS("MAS", Colors.GOLD),
    MENOS("MENOS", Colors.OLIVE),
    POR("POR", Colors.CORAL),
    DIV("DIV", Colors.TEAL),
    MOD("MOD", Colors.BROWN),
    DIVINT("DIVINT", Colors.PINK),
    NOT("NOT", Colors.PURPLE),
    ASIG("ASIG", Colors.GREEN),

    // Delimitadores
    PARI("PARI", Colors.BLUE),
    PARD("PARD", Colors.BLUE),
    CORI("CORI", Colors.MAGENTA),
    CORD("CORD", Colors.MAGENTA),
    LLAI("LLAI", Colors.CYAN),
    LLAD("LLAD", Colors.CYAN),
    COMA("COMA", Colors.WHITE),
    PYC("PYC", Colors.WHITE),
    PUNTO("PUNTO", Colors.GRAY_LIGHT),
    DOSP("DOSP", Colors.GRAY_LIGHT),

    /**
     * Token especial que indica el fin de archivo.
     * Se utiliza para marcar el final del análisis léxico.
     */
    EOF("EOF", Colors.LOW_INTENSITY),

    /**
     * Token que representa un error léxico.
     * Se usa cuando se encuentra un carácter o secuencia no válida.
     */
    UNKNOWN("UNKNOWN", Colors.RESTORE);

    private final String nombre;
    private final String color;

    /**
     * Constructor de la enumeración ClaseLexica.
     * 
     * @param nombre nombre descriptivo del token
     * @param color código de color ANSI para la visualización
     */
    ClaseLexica(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
    }

    /**
     * Obtiene el nombre descriptivo del token.
     * 
     * @return cadena que describe el tipo de token
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el código de color asociado al token.
     * 
     * @return código ANSI para formatear el color del token
     */
    public String getColor() {
        return color;
    }

    /**
     * Obtiene el nombre de la clase léxica a partir de su valor enumerado.
     * 
     * @param claseLexica valor enumerado de la clase léxica
     * @return nombre descriptivo del token o "UNKNOWN" si no se reconoce
     */
    public static String getNombreClase(ClaseLexica claseLexica) {
        for (ClaseLexica clase : values()) {
            if (clase == claseLexica) {
                return clase.getNombre();
            }
        }
        return UNKNOWN.getNombre();
    }

    /**
     * Obtiene el color asociado a la clase léxica a partir de su valor enumerado.
     * 
     * @param claseLexica valor enumerado de la clase léxica
     * @return código de color ANSI o el color de UNKNOWN si no se reconoce
     */
    public static String getColorClase(ClaseLexica claseLexica) {
        for (ClaseLexica clase : values()) {
            if (clase == claseLexica) {
                return clase.getColor();
            }
        }
        return UNKNOWN.getColor();
    }
}