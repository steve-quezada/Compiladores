package src.Analizador_Sintactico_BYACCJ.pilas;

import java.util.Stack;
import java.util.EmptyStackException;
import src.Analizador_Sintactico_BYACCJ.tablas.Tablas.TablaSimbolos;

/**
 * Gestiona las estructuras de pila necesarias para el proceso de compilación.
 * Implementa pilas especializadas para manejar tablas de símbolos y direcciones
 * durante el análisis semántico y la generación de código.
 * 
 * @author steve-quezada
 * @author etnicst
 */
public class Pilas {
    /**
     * Implementa una pila de tablas de símbolos para gestionar ámbitos.
     * Maneja la jerarquía de ámbitos y el acceso a símbolos en diferentes niveles
     * del programa, permitiendo la búsqueda de identificadores en ámbitos anidados.
     */
    public static class PilaTs {
        private Stack<TablaSimbolos> stack;

        /**
         * Crea una nueva pila de tablas de símbolos vacía.
         */
        public PilaTs() {
            stack = new Stack<>();
        }

        /**
         * Añade una nueva tabla de símbolos al tope de la pila.
         * 
         * @param ts tabla de símbolos a añadir
         * @throws IllegalArgumentException si la tabla es nula
         */
        public void push(TablaSimbolos ts) {
            if (ts == null) {
                throw new IllegalArgumentException("No se puede añadir una tabla de símbolos nula");
            }
            stack.push(ts);
        }

        /**
         * Extrae y devuelve la tabla de símbolos del tope de la pila.
         * 
         * @return la tabla de símbolos del tope
         * @throws EmptyStackException si la pila está vacía
         */
        public TablaSimbolos pop() {
            if (stack.isEmpty()) {
                throw new EmptyStackException();
            }
            return stack.pop();
        }

        /**
         * Obtiene la tabla de símbolos del tope sin extraerla.
         * 
         * @return la tabla de símbolos del tope
         * @throws EmptyStackException si la pila está vacía
         */
        public TablaSimbolos top() {
            if (stack.isEmpty()) {
                throw new EmptyStackException();
            }
            return stack.peek();
        }

        /**
         * Obtiene la tabla de símbolos del fondo de la pila (ámbito global).
         * 
         * @return la tabla de símbolos del fondo
         * @throws EmptyStackException si la pila está vacía
         */
        public TablaSimbolos bottom() {
            if (stack.isEmpty()) {
                throw new EmptyStackException();
            }
            return stack.firstElement();
        }

        /**
         * Verifica si la pila está vacía.
         * 
         * @return true si la pila no contiene elementos
         */
        public boolean isEmpty() {
            return stack.isEmpty();
        }

        /**
         * Obtiene el número de tablas de símbolos en la pila.
         * 
         * @return cantidad de tablas en la pila
         */
        public int size() {
            return stack.size();
        }

        /**
         * Elimina todas las tablas de símbolos de la pila.
         */
        public void clear() {
            stack.clear();
        }

        /**
         * Verifica si un identificador existe en algún ámbito de la pila.
         * Busca el identificador en todos los niveles, comenzando por el más interno.
         * 
         * @param id identificador a buscar
         * @return true si el identificador existe en algún ámbito
         */
        public boolean existe(String id) {
            for (TablaSimbolos ts : stack) {
                if (ts.existe(id)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Obtiene el tipo de un identificador buscando en todos los ámbitos.
         * 
         * @param id identificador a buscar
         * @return tipo del identificador o -1 si no se encuentra
         */
        public int getTipo(String id) {
            for (TablaSimbolos ts : stack) {
                if (ts.existe(id)) {
                    return ts.getTipo(id);
                }
            }
            return -1;
        }

        /**
         * Obtiene una tabla de símbolos específica de la pila.
         * 
         * @param index índice de la tabla a obtener (0 es el fondo)
         * @return tabla de símbolos en el índice especificado
         * @throws IndexOutOfBoundsException si el índice está fuera de rango
         */
        public TablaSimbolos get(int index) {
            if (index < 0 || index >= stack.size()) {
                throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
            }
            return stack.get(index);
        }

        /**
         * Genera una representación en cadena de la pila completa.
         * 
         * @return cadena con la información de todas las tablas de símbolos
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("PilaTs:\n");
            for (int i = stack.size() - 1; i >= 0; i--) {
                sb.append("Nivel ").append(i).append(":\n");
                sb.append(stack.get(i).toString()).append("\n");
            }
            return sb.toString();
        }
    }

    /**
     * Implementa una pila para gestionar direcciones de memoria.
     * Mantiene un registro de las direcciones base y los desplazamientos
     * durante la generación de código y la asignación de memoria.
     */
    public static class PilaDir {
        private Stack<Integer> stack;

        /**
         * Crea una nueva pila de direcciones vacía.
         */
        public PilaDir() {
            stack = new Stack<>();
        }

        /**
         * Añade una dirección al tope de la pila.
         * 
         * @param dir dirección a añadir
         */
        public void push(int dir) {
            stack.push(dir);
        }

        /**
         * Extrae y devuelve la dirección del tope de la pila.
         * 
         * @return dirección del tope
         * @throws EmptyStackException si la pila está vacía
         */
        public int pop() {
            if (stack.isEmpty()) {
                throw new EmptyStackException();
            }
            return stack.pop();
        }

        /**
         * Obtiene la dirección del tope sin extraerla.
         * 
         * @return dirección del tope
         * @throws EmptyStackException si la pila está vacía
         */
        public int top() {
            if (stack.isEmpty()) {
                throw new EmptyStackException();
            }
            return stack.peek();
        }

        /**
         * Verifica si la pila está vacía.
         * 
         * @return true si la pila no contiene direcciones
         */
        public boolean isEmpty() {
            return stack.isEmpty();
        }

        /**
         * Obtiene el número de direcciones en la pila.
         * 
         * @return cantidad de direcciones almacenadas
         */
        public int size() {
            return stack.size();
        }

        /**
         * Elimina todas las direcciones de la pila.
         */
        public void clear() {
            stack.clear();
        }

        /**
         * Genera una representación en cadena de la pila de direcciones.
         * 
         * @return cadena con todas las direcciones almacenadas
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("PilaDir:\n");
            for (int i = stack.size() - 1; i >= 0; i--) {
                sb.append("Nivel ").append(i).append(": ");
                sb.append(stack.get(i)).append("\n");
            }
            return sb.toString();
        }
    }
}
