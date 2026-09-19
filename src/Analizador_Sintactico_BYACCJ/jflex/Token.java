package src.Analizador_Sintactico_BYACCJ.jflex;

import src.Analizador_Sintactico_BYACCJ.utils.Colors;

/**
 * Representa un token del análisis léxico del compilador.
 * Almacena la información relevante de cada unidad léxica encontrada
 * durante el proceso de análisis, incluyendo su clasificación,
 * valor textual y ubicación en el código fuente.
 * 
 * @author steve-quezada
 * @author etnicst
 */
public class Token {
    private ClaseLexica clase;

    private String lexema;

    private int linea;

    public int valor;

    /**
     * Crea un nuevo token con la información especificada.
     * Realiza validaciones básicas de los parámetros y, en caso de
     * tokens numéricos, intenta convertir el lexema a un valor entero.
     *
     * @param clase  categoría léxica del token
     * @param lexema texto original del token
     * @param linea  número de línea donde se encontró
     * @throws IllegalArgumentException si el lexema es inválido o la línea es negativa,
     *                                  excepto para tokens EOF
     */
    public Token(ClaseLexica clase, String lexema, int linea) {
        if (clase == ClaseLexica.EOF) {
            this.clase = clase;
            this.lexema = lexema != null ? lexema : "EOF";
            this.linea = linea;
            return;
        }

        if (lexema == null || lexema.isEmpty()) {
            throw new IllegalArgumentException("El lexema no puede ser nulo o vacío.");
        }
        if (linea < 0) {
            throw new IllegalArgumentException("La línea no puede ser negativa.");
        }
        this.clase = clase;
        this.lexema = lexema;
        this.linea = linea;

        if (clase == ClaseLexica.LIT_ENTERO) {
            try {
                this.valor = Integer.parseInt(lexema);
            } catch (NumberFormatException e) {
                this.valor = 0;
            }
        }
    }

    /**
     * Crea un token especial que representa el fin de archivo.
     *
     * @return token EOF con valores predeterminados
     */
    public static Token EOF() {
        return new Token(ClaseLexica.EOF, "EOF", -1);
    }

    /**
     * Verifica si este token representa un error léxico.
     *
     * @return true si es un token de error, false en caso contrario
     */
    public boolean esError() {
        return this.clase == ClaseLexica.UNKNOWN;
    }

    /**
     * Crea un token que representa un error léxico.
     *
     * @param mensaje descripción del error encontrado
     * @param linea   línea donde ocurrió el error
     * @return token de error con el mensaje especificado
     */
    public static Token error(String mensaje, int linea) {
        return new Token(ClaseLexica.UNKNOWN, mensaje, linea);
    }

    /**
     * Obtiene la categoría léxica del token.
     *
     * @return clase léxica asignada al token
     */
    public ClaseLexica getClaseLexica() {
        return clase;
    }

    /**
     * Obtiene el texto original del token.
     *
     * @return lexema del token
     */
    public String getLexema() {
        return lexema;
    }

    /**
     * Obtiene la línea donde se encontró el token.
     *
     * @return número de línea en el código fuente
     */
    public int getLinea() {
        return linea;
    }

    /**
     * Obtiene el valor numérico del token.
     * Solo es relevante para tokens de tipo numérico.
     *
     * @return valor numérico del token o 0 si no es aplicable
     */
    public int getValor() {
        return valor;
    }

    /**
     * Genera una representación textual del token.
     * Incluye la clase léxica, el lexema y la línea.
     *
     * @return cadena con formato
     */
    @Override
    public String toString() {
        return "<" + this.clase.getNombre() + "," + this.lexema + "," + this.linea + ">";
    }

    /**
     * Verifica si el token pertenece a una categoría léxica específica.
     *
     * @param clase categoría léxica a comparar
     * @return true si el token es de la clase especificada
     */
    public boolean esDeClase(ClaseLexica clase) {
        return this.clase == clase;
    }

    /**
     * Compara este token con otro para determinar si son iguales.
     * Dos tokens son iguales si tienen la misma clase léxica,
     * lexema y número de línea.
     *
     * @param otro token con el que comparar
     * @return true si los tokens son iguales
     */
    public boolean equals(Token otro) {
        return this.clase == otro.clase && this.lexema.equals(otro.lexema) && this.linea == otro.linea;
    }

    /**
     * Realiza la verificación del token y muestra su información.
     * Imprime un mensaje formateado indicando si el token es válido
     * o inválido, junto con sus detalles.
     */
    public void verificarImprimirToken() {
        String color = this.clase.getColor();
        String mensaje;

        if (this.clase == ClaseLexica.UNKNOWN || this.lexema == null || this.lexema.isEmpty()) {
            mensaje = "\nToken inválido: " + this.toString();
        } else {
            mensaje = "\nToken válido: " + this.toString();
        }

        System.out.println(color + mensaje + Colors.RESTORE);
    }
}