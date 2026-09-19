package src.Analizador_Sintactico_BYACCJ.tablas;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase que implementa las tablas necesarias para el compilador.
 * Proporciona estructuras de datos fundamentales para el análisis semántico
 * y la gestión de símbolos durante la compilación.
 * 
 * @author steve-quezada
 * @author etnicst
 */
public class Tablas {
    /**
     * Implementa una tabla de símbolos para el compilador.
     * Gestiona información sobre identificadores, incluyendo:
     * - Variables locales y globales
     * - Funciones y sus parámetros
     * - Tipos de datos y sus propiedades
     * - Ámbitos y visibilidad
     */
    public static class TablaSimbolos {
        /**
         * Estructura interna que almacena la información de cada símbolo.
         * Mantiene los atributos esenciales de cada identificador en el programa.
         */
        private class InfoSimbolo {
            String id;
            int direccion;
            int tipo;
            String clase;
            Object info;
        }

        private Map<String, InfoSimbolo> simbolos;
        private Map<String, Integer> numElementos;

        /**
         * Constructor de la tabla de símbolos
         */
        public TablaSimbolos() {
            simbolos = new HashMap<>();
            numElementos = new HashMap<>();
        }

        /**
         * Añade un símbolo a la tabla
         */
        public void add(String id, int dir, int tipo, String clase, List<Object> args) {
            InfoSimbolo info = new InfoSimbolo();
            info.id = id;
            info.direccion = dir;
            info.tipo = tipo;
            info.clase = clase;
            info.info = args;
            simbolos.put(id, info);

            if (clase.equals("func")) {
                info.info = args != null ? new ArrayList<>(args) : new ArrayList<>();
            }
        }

        /**
         * Verifica si existe un símbolo
         */
        public boolean existe(String id) {
            return simbolos.containsKey(id);
        }

        /**
         * Obtiene el tipo de un símbolo
         */
        public int getTipo(String id) {
            InfoSimbolo info = simbolos.get(id);
            return info != null ? info.tipo : -1;
        }

        /**
         * Obtiene la dirección de un símbolo
         */
        public int getDir(String id) {
            InfoSimbolo info = simbolos.get(id);
            return info != null ? info.direccion : -1;
        }

        /**
         * Obtiene la clase de un símbolo
         */
        public String getClase(String id) {
            InfoSimbolo info = simbolos.get(id);
            return info != null ? info.clase : null;
        }

        /**
         * Obtiene los argumentos de una función
         */
        public List<Object> getArgs(String id) {
            InfoSimbolo info = simbolos.get(id);
            if (info != null && info.clase.equals("func")) {
                @SuppressWarnings("unchecked")
                List<Object> args = (List<Object>) info.info;
                return args;
            }
            return null;
        }

        /**
         * Obtiene el número de elementos de un array
         */
        public int getNumElementos(String id) {
            return numElementos.getOrDefault(id, 0);
        }

        /**
         * Obtiene todos los campos de una estructura
         */
        public Map<String, Integer> getCampos() {
            Map<String, Integer> campos = new HashMap<>();
            for (Map.Entry<String, InfoSimbolo> entry : simbolos.entrySet()) {
                if (!entry.getValue().clase.equals("func")) {
                    campos.put(entry.getKey(), entry.getValue().tipo);
                }
            }
            return campos;
        }

        /**
         * Actualiza el tipo de un símbolo
         */
        public void setTipo(String id, int tipo) {
            InfoSimbolo info = simbolos.get(id);
            if (info != null) {
                info.tipo = tipo;
            }
        }

        /**
         * Actualiza la dirección de un símbolo
         */
        public void setDir(String id, int direccion) {
            InfoSimbolo info = simbolos.get(id);
            if (info != null) {
                info.direccion = direccion;
            }
        }

        /**
         * Actualiza la información adicional de un símbolo
         */
        public void setInfo(String id, Object info) {
            InfoSimbolo infoSimbolo = simbolos.get(id);
            if (infoSimbolo != null) {
                infoSimbolo.info = info;
                if (info != null && info instanceof Integer && infoSimbolo.clase.equals("array")) {
                    numElementos.put(id, (Integer) info);
                }
            }
        }

        /**
         * Elimina un símbolo de la tabla
         */
        public void remove(String id) {
            simbolos.remove(id);
            numElementos.remove(id);
        }

        /**
         * Limpia la tabla de símbolos
         */
        public void clear() {
            simbolos.clear();
            numElementos.clear();
        }

        /**
         * Obtiene una representación en string de la tabla
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Tabla de Símbolos:\n");
            for (Map.Entry<String, InfoSimbolo> entry : simbolos.entrySet()) {
                InfoSimbolo info = entry.getValue();
                sb.append(String.format("ID: %s, Dir: %d, Tipo: %d, Clase: %s\n",
                        info.id, info.direccion, info.tipo, info.clase));
            }
            return sb.toString();
        }

        /**
         * Obtiene todos los símbolos de un tipo específico
         */
        public List<String> getSimbolosPorClase(String clase) {
            List<String> result = new ArrayList<>();
            for (Map.Entry<String, InfoSimbolo> entry : simbolos.entrySet()) {
                if (entry.getValue().clase.equals(clase)) {
                    result.add(entry.getKey());
                }
            }
            return result;
        }

        /**
         * Verifica si un símbolo es de una clase específica
         */
        public boolean esDeClase(String id, String clase) {
            InfoSimbolo info = simbolos.get(id);
            return info != null && info.clase.equals(clase);
        }

        /**
         * Obtiene el tamaño total de todos los símbolos
         */
        public int getTamanoTotal() {
            int tamano = 0;
            for (InfoSimbolo info : simbolos.values()) {
                if (!info.clase.equals("func")) { // Excluir funciones del cálculo
                    tamano += info.direccion;
                }
            }
            return tamano;
        }
    }

    /**
     * Implementa una tabla de tipos para el compilador.
     * Gestiona la información sobre tipos de datos, incluyendo:
     * - Tipos básicos (int, float, double, etc.)
     * - Tipos compuestos (arrays, estructuras)
     * - Conversiones y compatibilidad entre tipos
     * - Tamaños y alineamiento en memoria
     */
    public static class TablaTipos {
        private Map<String, Integer> nombreATipo;
        private Map<Integer, String> tipoANombre;
        private Map<Integer, Integer> tamanos;
        private Map<Integer, String> categorias;
        private Map<Integer, Integer> tiposBase;
        private Map<Integer, TablaSimbolos> tablaSimbolosStruct;

        private int siguienteTipo;

        /**
         * Constantes que definen los tipos básicos del lenguaje.
         * Cada constante representa un tipo de dato fundamental.
         */
        public static final int T_ERROR = -1;
        public static final int T_INT = 0;
        public static final int T_FLOAT = 1;
        public static final int T_DOUBLE = 2;
        public static final int T_COMPLEX = 3;
        public static final int T_RUNE = 4;
        public static final int T_VOID = 5;
        public static final int T_STRING = 6;
        public static final int T_BOOL = 7;

        /**
         * Constructor de la tabla de tipos.
         * Inicializa los tipos básicos del lenguaje.
         */
        public TablaTipos() {
            nombreATipo = new HashMap<>();
            tipoANombre = new HashMap<>();
            tamanos = new HashMap<>();
            categorias = new HashMap<>();
            tiposBase = new HashMap<>();
            tablaSimbolosStruct = new HashMap<>();

            siguienteTipo = 0;

            addTipoBasico("int", 4);
            addTipoBasico("float", 4);
            addTipoBasico("double", 8);
            addTipoBasico("complex", 8);
            addTipoBasico("rune", 4);
            addTipoBasico("void", 0);
            addTipoBasico("string", 8);
            addTipoBasico("bool", 1);
        }

        /**
         * Añade un tipo básico a la tabla de tipos.
         */
        private void addTipoBasico(String nombre, int tamano) {
            int id = siguienteTipo++;
            nombreATipo.put(nombre, id);
            tipoANombre.put(id, nombre);
            tamanos.put(id, tamano);
            categorias.put(id, "basic");
            tiposBase.put(id, id);
        }

        /**
         * Obtiene el ID de un tipo a partir de su nombre.
         */
        public int getId(String nombre) {
            return nombreATipo.getOrDefault(nombre, T_ERROR);
        }

        /**
         * Verifica si dos tipos son compatibles para operaciones.
         * Define las reglas de compatibilidad entre tipos básicos y compuestos.
         */
        public boolean sonCompatibles(int tipo1, int tipo2) {
            if (tipo1 == tipo2)
                return true;

            if (esBasico(tipo1) && esBasico(tipo2)) {
                if ((tipo1 == T_INT || tipo1 == T_FLOAT || tipo1 == T_DOUBLE) &&
                        (tipo2 == T_INT || tipo2 == T_FLOAT || tipo2 == T_DOUBLE)) {
                    return true;
                }

                if ((tipo1 == T_COMPLEX || tipo2 == T_COMPLEX) &&
                        (tipo1 == T_INT || tipo1 == T_FLOAT || tipo1 == T_DOUBLE ||
                                tipo2 == T_INT || tipo2 == T_FLOAT || tipo2 == T_DOUBLE)) {
                    return true;
                }

                if ((tipo1 == T_BOOL && tipo2 == T_INT) ||
                        (tipo1 == T_INT && tipo2 == T_BOOL)) {
                    return true;
                }

                if ((tipo1 == T_RUNE && tipo2 == T_INT) ||
                        (tipo1 == T_INT && tipo2 == T_RUNE)) {
                    return true;
                }
            }

            if (esArray(tipo1) && esArray(tipo2)) {
                return sonCompatibles(getTipoBase(tipo1), getTipoBase(tipo2));
            }

            if (esStruct(tipo1) && esStruct(tipo2)) {
                return tipo1 == tipo2;
            }

            return false;
        }

        /**
         * Añade un nuevo tipo array a la tabla de tipos.
         */
        public int add(int elementos, int tipoBase, String categoria) {
            if (categoria.equals("array")) {
                int id = siguienteTipo++;
                String nombre = "array[" + elementos + "]of_" + tipoANombre.get(tipoBase);

                nombreATipo.put(nombre, id);
                tipoANombre.put(id, nombre);
                tamanos.put(id, elementos * tamanos.get(tipoBase));
                categorias.put(id, categoria);
                tiposBase.put(id, tipoBase);

                return id;
            }
            return T_ERROR;
        }

        /**
         * Añade un nuevo tipo estructura a la tabla de tipos.
         */
        public int add(String categoria, TablaSimbolos ts) {
            if (categoria.equals("struct")) {
                int id = siguienteTipo++;
                String nombre = "struct_" + id;

                nombreATipo.put(nombre, id);
                tipoANombre.put(id, nombre);
                tamanos.put(id, calcularTamanoStruct(ts));
                categorias.put(id, categoria);
                tiposBase.put(id, id);
                tablaSimbolosStruct.put(id, ts);

                return id;
            }
            return T_ERROR;
        }

        /**
         * Calcula el tamaño total de una estructura.
         * Suma los tamaños de todos los campos de la estructura.
         * 
         * @param ts TablaSimbolos que contiene los campos de la estructura
         * @return tamaño total de la estructura en bytes
         */
        private int calcularTamanoStruct(TablaSimbolos ts) {
            int tamanoTotal = 0;
            Map<String, Integer> campos = ts.getCampos();

            for (Map.Entry<String, Integer> campo : campos.entrySet()) {
                int tipoDelCampo = campo.getValue();

                int tamanoCampo = tamanos.getOrDefault(tipoDelCampo, 0);

                if (esArray(tipoDelCampo)) {
                    int numElementos = ts.getNumElementos(campo.getKey());
                    int tipoBase = getTipoBase(tipoDelCampo);
                    tamanoCampo = tamanos.get(tipoBase) * numElementos;
                } else if (esStruct(tipoDelCampo)) {
                    TablaSimbolos tsAnidada = getTablaSimbolos(tipoDelCampo);
                    tamanoCampo = calcularTamanoStruct(tsAnidada);
                }

                tamanoCampo = alinear(tamanoCampo, 4);

                tamanoTotal += tamanoCampo;
            }

            return alinear(tamanoTotal, 8);
        }

        /**
         * Alinea un tamaño a un límite específico.
         * 
         * @param tamano       tamaño a alinear
         * @param alineamiento valor de alineamiento (típicamente 4 u 8 bytes)
         * @return tamaño alineado
         */
        private int alinear(int tamano, int alineamiento) {
            return (tamano + alineamiento - 1) & ~(alineamiento - 1);
        }

        /**
         * Obtiene el nombre de un tipo.
         */
        public String getName(int tipo) {
            return tipoANombre.getOrDefault(tipo, "error");
        }

        /**
         * Obtiene el tipo base de un tipo.
         */
        public int getTipoBase(int tipo) {
            return tiposBase.getOrDefault(tipo, T_ERROR);
        }

        /**
         * Obtiene el tamaño de un tipo.
         */
        public int getTam(int tipo) {
            return tamanos.getOrDefault(tipo, 0);
        }

        /**
         * Obtiene la tabla de símbolos de una estructura.
         */
        public TablaSimbolos getTablaSimbolos(int tipo) {
            return tablaSimbolosStruct.get(tipo);
        }

        /**
         * Verifica si un tipo es básico.
         */
        public boolean esBasico(int tipo) {
            return "basic".equals(categorias.get(tipo));
        }

        /**
         * Verifica si un tipo es un array.
         */
        public boolean esArray(int tipo) {
            return "array".equals(categorias.get(tipo));
        }

        /**
         * Verifica si un tipo es una estructura.
         */
        public boolean esStruct(int tipo) {
            return "struct".equals(categorias.get(tipo));
        }

        /**
         * Obtiene el tipo resultante de una operación entre dos tipos.
         */
        public int getTipoResultante(int tipo1, int tipo2) {
            if (!sonCompatibles(tipo1, tipo2))
                return T_ERROR;

            if (tipo1 == tipo2)
                return tipo1;

            return tipo1 > tipo2 ? tipo1 : tipo2;
        }

        /**
         * Obtiene una representación en string de la tabla de tipos.
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Tabla de Tipos:\n");
            for (Map.Entry<Integer, String> entry : tipoANombre.entrySet()) {
                int id = entry.getKey();
                sb.append(String.format("ID: %d, Nombre: %s, Tamaño: %d, Categoría: %s\n",
                        id, entry.getValue(), tamanos.get(id), categorias.get(id)));
            }
            return sb.toString();
        }
    }

    /**
     * Define constantes para los tamaños de tipos básicos.
     * Especifica el tamaño en bytes de cada tipo de dato fundamental.
     */
    public static class TamanoTipos {
        public static final int TAM_INT = 4;
        public static final int TAM_FLOAT = 4;
        public static final int TAM_DOUBLE = 8;
        public static final int TAM_COMPLEX = 8;
        public static final int TAM_RUNE = 4;
        public static final int TAM_VOID = 0;
        public static final int TAM_STRING = 8;
        public static final int TAM_BOOL = 1;
    }

    /**
     * Define constantes para las categorías de tipos.
     * Clasifica los tipos de datos según su estructura.
     */
    public static class CategoriaTipos {
        public static final String CAT_BASICO = "basic";
        public static final String CAT_ARRAY = "array";
        public static final String CAT_STRUCT = "struct";
    }

    /**
     * Define constantes para las clases de símbolos.
     * Especifica los roles que puede tener un identificador.
     */
    public static class ClaseSimbolos {
        public static final String CLASE_VAR = "var";
        public static final String CLASE_FUNC = "func";
        public static final String CLASE_ARG = "arg";
    }
}