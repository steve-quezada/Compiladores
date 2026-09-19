package src.Analizador_Sintactico_BYACCJ;

import java.io.FileReader;
import src.Analizador_Sintactico_BYACCJ.utils.Colors;

/**
 * Clase principal que implementa el compilador basado en BYACC/J y JFlex.
 * Proporciona la funcionalidad de análisis léxico, sintáctico y semántico
 * para el lenguaje de programación definido.
 * 
 * El compilador procesa un archivo fuente y realiza las siguientes fases:
 * 1. Análisis léxico mediante JFlex
 * 2. Análisis sintáctico mediante BYACC/J
 * 3. Análisis semántico y generación de código intermedio
 * 
 * @author steve-quezada
 * @author etnicst
 */
public class Main {

    /**
     * Método principal que inicia el proceso de compilación.
     * Verifica los argumentos de entrada, inicializa el analizador sintáctico
     * y maneja el proceso de compilación completo. El método termina con éxito
     * solo si no se encuentran errores durante el análisis.
     * 
     * El proceso incluye:
     * 1. Validación de argumentos
     * 2. Inicialización del compilador
     * 3. Análisis del archivo fuente
     * 4. Reporte de resultados
     * 
     * @param args argumentos de la línea de comandos. Debe contener exactamente
     *             un argumento que especifica la ruta al archivo fuente
     * @throws IllegalArgumentException si no se proporciona exactamente un argumento
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            printBox("Error", new String[] {
                    "Se requiere un archivo de entrada",
                    "Uso: java Main <archivo_entrada>"
            }, Colors.RED);
            System.exit(1);
        }

        printBox("Compilador BYACCJ + JFlex", new String[] {
                "@author steve-quezada",
                "@author etnicst",
                "Archivo: " + args[0]
        }, Colors.TEAL);

        try {
            Colors.println("\n[1] Iniciando análisis...", Colors.BLUE_LIGHT);
            Parser parser = new Parser(new FileReader(args[0]));

            Colors.println("\n[2] Procesando archivo...", Colors.MAGENTA_LIGHT);
            parser.yyparse();

            printBox("Compilación Exitosa", new String[] {
                    "El análisis ha finalizado correctamente",
                    "No se encontraron errores"
            }, Colors.GREEN);
        } catch (Exception e) {
            printBox("Error Fatal", new String[] {
                    e.getMessage()
            }, Colors.RED + Colors.HIGH_INTENSITY);
            System.exit(1);
        }
    }

    /**
     * Imprime un cuadro decorativo con título y contenido.
     * Genera una representación visual estructurada utilizando caracteres
     * Unicode para los bordes y aplica formato de color ANSI.
     * 
     * @param title   título que aparecerá en la parte superior del cuadro
     * @param content array de cadenas que formarán el contenido del cuadro
     * @param color   código de color ANSI para el formato del cuadro
     */
    private static void printBox(String title, String[] content, String color) {
        int maxLength = title.length();
        for (String line : content) {
            maxLength = Math.max(maxLength, line.length());
        }
        maxLength += 4;

        String border = "╔" + "═".repeat(maxLength) + "╗";
        String middleBorder = "╠" + "═".repeat(maxLength) + "╣";
        String bottomBorder = "╚" + "═".repeat(maxLength) + "╝";

        Colors.println("\n" + border, color);
        Colors.println("║ " + padString(title, maxLength - 2) + " ║", color);
        Colors.println(middleBorder, color);

        for (String line : content) {
            Colors.println("║ " + padString(line, maxLength - 2) + " ║", color);
        }

        Colors.println(bottomBorder, color);
    }

    /**
     * Aplica relleno a una cadena para alcanzar una longitud específica.
     * 
     * @param str    cadena a la que se aplicará el relleno
     * @param length longitud final deseada de la cadena
     * @return cadena con el relleno aplicado
     */
    private static String padString(String str, int length) {
        return String.format("%-" + length + "s", str);
    }
}