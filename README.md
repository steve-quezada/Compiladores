<p align="center">
  <img width="200" src="https://www.fciencias.unam.mx/sites/default/files/logoFC_2.png" alt="">
  <br><strong>Compiladores 2025-1</strong> <br>
  <strong>Profesora:</strong> Ariel Adara Mercado Martínez <br>
  <strong>Ayudante:</strong> Janeth Pablo Martínez <br>
  <strong>Ayud. Lab.:</strong> Carlos Gerardo Acosta Hernández <br>
  <br> 
  <strong>Alumnos:</strong> Kevin Steve Quezada Ordoñez & Etni Sarai Castro Sierra <br>

</p>

## Proyecto Final: Compilador
### Objetivo:
 Elaborar un compilador para la gramática descrita en la sección de gramática que genere código objeto para MIPS.

### Gramática

programa → decl_proto decl_var decl_func

decl_proto → <strong>proto</strong> tipo <strong>id</strong> <strong>(</strong> argumentos <strong>) ;</strong> decl_proto | ε
decl_var → tipo lista var ; decl var | ε

tipo → basico compuesto | <strong>struct { </strong>decl_var <strong>}</strong> | puntero

puntero → <strong>ptr</strong> basico

basico → <strong> int | float | double | complex | rune | void | string | </strong>

compuesto → <strong>[ literal_entera ]</strong> compuesto | ε

lista_var → lista_var <strong>, id | id</strong>

decl_func → <strong>func</strong> tipo <strong>id</strong> <strong>(</strong> argumentos <strong>)</strong> bloque decl_func | ε

argumentos→ lista_args | ε

lista_args→ lista_args, tipo <strong> id</strong> | tipo <strong>id</strong>

bloque → <strong>{</strong> declaraciones instrucciones <strong>}</strong>

instrucciones → instrucciones sentencia | sentencia

sentencia → parte_izquierda <strong>=</strong> exp <strong>;</strong> | <strong>if(</strong> exp <strong>)</strong> sentencia | if( exp ) sentencia <strong>else</strong> sentencia
| <strong>while</strong><strong>(</strong> exp <strong>)</strong> sentencia | <strong>do</strong> sentencia <strong>while(</strong> exp <strong>)</strong> | <strong>break ;</strong> | bloque
| <strong>return</strong> exp ; | <strong> return; | switch(</strong> exp <strong>) {</strong> casos <strong>} | print</strong> exp<strong> ; | scan</strong> parte_izquierda

casos → caso casos| ε | predeterminado

caso → <strong>case</strong> opcion : instrucciones

opcion → literal_entera | literal_runa

predeterminado → <strong>default:</strong> instrucciones

parte_izquierda → <strong>id</strong> localizacion |<strong>id</strong>

exp → exp <strong> || </strong> exp | exp <strong>&& </strong> exp | exp <strong>== </strong> exp | exp<strong> != </strong> exp | exp <strong> < </strong> exp | exp <strong> <= </strong> 
| exp <strong> >= </strong> exp | exp <strong> > </strong> exp | exp <strong>+ </strong> exp | exp <strong>- </strong> exp | exp <strong>* </strong> exp | exp <strong> / </strong> exp | exp <strong>% </strong> exp | exp <strong> // </strong> exp
| <strong>! </strong> exp | <strong>- </strong> exp | <strong> (</strong> exp <strong> )</strong> | <strong> id </strong> localizacion | <strong> false </strong> | <strong> literal_cadena </strong>| <strong> true</strong> | <strong> literal_runa</strong> | <strong> literal_entera</strong>
| <strong> literal_flotante </strong>| <strong>  literal_doble</strong> | <strong> literal_compleja </strong>| <strong> id ( </strong> parametros</strong>)| <strong>id </strong>

parametros → lista_param | ε

lista_param → lista_param , exp | exp

localizacion→ arreglo | estructurado

arreglo → arreglo [ exp ] | [ exp ]

estructurado → estructurado <strong>. id </strong> |<strong> .id </strong>


### Estructura de un archivo YACC
Un definición de BYACC/J consta de tres secciones:
```
Sección de declaraciones
%%
Sección de acciones (Producciones)
%%
Sección de código de usuario (código en lenguaje Java)
```

#### Sección de declaraciones
La primera parte del archivo es el área de DECLARACIONES, donde se definen los tokens, las precedencias, la asociatividad de operadores, etc.

* __Directivas de código.__ Se utilizan para incluir los archivos de biblioteca 
    ```yacc
    %{
    import java.io.*;
    %}
    ```

* __Directivas de YACC.__
    - Los Terminales (```%token[<Tipo>] lista_terminales```).
        ```
        %token<sval> ID 
        %token<ival> ENTERO
        %token IF ELSE
        ```
    - Los No Terminales (```%type<Tipo> lista_no_terminales```). Generalmente los no terminales no se declaran al menos que requieran de un tipo.
        ```
        %type<sval> expresion termino factor
        ``` 
    - la asociatividad y precedencia de los operadores (```%left, %right, %nonassoc```). La precedencia va de menor a mayor.
        ```
        %left MAS
        %left MUL
        %nonassoc LPAR RPAR
        ```


#### Sección de acciones
La segunda parte es el área de ACCIONES, donde se definen las producciones, y se ejecutan las acciones semánticas en Java del usuario.

- Utiliza la notación BNF simplificada:
```Java
/* Una sola regla */
simboloNoTerminal : simb1 sim2 . . . simN [ accion ] ;

/*Varias reglas */
simboloNoTerminal : regla1 [ accion1 ]
                  | regla2 [ accion2 ] 
                  .............
                  ;

/* la regla vacia */
simboloNoTerminal : /* vacio */ [ accion1 ]
                  | regla2 [ accion2 ];
```

- Acciones

    a. Se escriben usando código en Java.

    b. Para hacer referencia a los símbolos gramaticales se utilizan pseudovariables ```$1, $2, ...$N```

    c. ```$$``` representa el encabezado de la producción.

    d. ```$1``` representa el primer símbolo gramatical después de los ```:```



#### Sección de código

La tercera parte es el área de CÓDIGO, donde se agregan los métodos del usuario.
Hay tres métodos de la clase Parser que es necesario implementar para su funcionamiento:
- ```void yyerror(String msg)```: Se utiliza para proporcionar mensajes de error que se dirigirán a los canales que el usuario desee.
- ```int yylex()```: Este método es el que BYACC/J espera utilizar para obtener sus tokens de entrada. Podemos envolver cualquier código de escaneo de archivos/cadenas que tengas en este método. Este método debe devolver ```< 0``` si hay un error, y ```0``` cuando encuentre el final de la entrada. 
- ```public static void main(String args[])```: Es clara la necesidad de un método principal para la ejecución del análisis sintáctico, pero este método puede definirse en otro archivo o paquete. 


### Pasos para ejecutar el compilador
Nota: Aunque se muestran los pasos para ejecutar las pruebas unitarias, se pueden ejecutar directamente usando `ant test-all`, ya que las dependencias están configuradas para ejecutar los pasos previos automáticamente.

Limpiar archivos generados anteriormente:
   ```bash
   $ ant clean
   ```

Compilar los archivos:
   ```bash
   $ ant compile
   ```

* Probar el archivo de prueba 1:
   ```bash
   $ ant test1
   ```
*  Probar el archivo de prueba 2
   ```bash
   $ ant test2
   ```
   Y así consecutivamente con los 7 archivos
### Estructura del directorio
```c++
└── Proyecto_Final-Compiladores
    ├── README.md
    ├── build.xml
    ├── DiagramaSintaxis.html
    ├── pdf
    │   ├── DDS_2024.pdf
    │   └── Gramatica2024-Final.pdf
    └── src
        └── Analizador_Sintactico_BYACCJ
            ├── Main.java
            ├── byacc
            │   └── Parser.y
            ├── jflex
            │   ├── ClaseLexica.java
            │   ├── Lexer.flex
            │   └── Token.java
            ├── pilas
            │   └── Pilas.java
            ├── tablas
            │   └── Tablas.java
            ├── tst
            │   ├── prueba.txt
            │   ├── prueba1_Valida.txt
            │   ├── prueba2_Valida.txt
            │   ├── prueba3_Valida.txt
            │   ├── prueba4_Valida.txt
            │   ├── prueba5_Valida.txt
            │   └── prueba6_Valida.txt
            └── utils
                └── Colors.java

```

