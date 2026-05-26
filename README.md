

# AEE: "Limpieza de Primavera"

## Refactorización de Código Legado

# 5\. Instrucciones Paso a Paso

## Fase 1: Análisis de la Deuda Técnica

1. **Verificación inicial.** Ejecuta los tests unitarios. Todo debe salir en verde. Esto os garantiza que el código original, por muy feo que sea, funciona.  
   Se ejecutó las pruebas unitarias provista en FacturacionLegacyTest.java antes de realizar modificaciones. Los tests se completaron con éxito (indicador verde), confirmando que el código original calcula correctamente los importes, a pesar de su pésima estructura interna.   
   

\-**Oler el código (*Code Smells*).** El Copiloto anotará en un bloc de notas los tres grandes problemas de este código:

* **Números mágicos.** ¿Qué significa 0.25 o 0.15? Son valores *hardcodeados* sin contexto. Si mañana el IVA o el descuento cambian, ¿dónde los buscamos?

  -Los valores 0.25, 0.15 y 0.05 están incrustados directamente (*hardcodeados*) en las instrucciones de retorno. Carecen por completo de contexto semántico, lo que dificulta enormemente su mantenimiento si las políticas de descuento de la empresa cambian en el futuro.

  * **Variables sin significado.** Nombres como cT, m, tC o dV no aportan ninguna semántica. Nos obligan a adivinar.

  -Los identificadores utilizados (cT, m, tc/tC, dV) son excesivamente cortos y no aportan información sobre las reglas de negocio que representan. Obligan al lector a realizar un esfuerzo cognitivo innecesario para deducir qué datos maneja el método.

  * **Código Spaghetti.** La anidación de múltiples if-else crea una estructura en forma de flecha \> que hace casi imposible seguir el flujo lógico de ejecución.

  -La excesiva anidación de bloques if-else (hasta 3 niveles hacia la derecha) genera un flujo en forma de flecha (\>). Esto incrementa la complejidad del código y reduce de forma crítica su legibilidad.

## Fase 2: Refactorización Asistida por el IDE (Quirófano abierto)

1. **Renombrado Seguro.**

Se han modificado las variables de una sola letra por variables descriptivas en formato camelCase:

* cT → calcularTotal  
* m → importeBase  
* tc / tC → tipoCliente  
* dV → esSocioVip

2. **Extracción de Constantes.**

Se han extraído los literales numéricos a la parte superior de la clase en formato estático, inmutable y nombrados en SNAKE\_CASE:

Java  
private static final double DESCUENTO\_VIP \= 0.25;  
private static final double DESCUENTO\_ESTANDAR \= 0.15;  
private static final double DESCUENTO\_TIPO2 \= 0.05;

3. **Cláusulas de Guarda (*Guard Clauses*).**

Se invirtió la validación inicial de rango (`importeBase > 0`) para aplicar un retorno temprano si el importe es inválido (`importeBase <= 0`). Esto permitió desanidar la lógica completa del algoritmo eliminando el uso de bloques `else`. 

Código Refactorizado

package com.mycompany.coderefactorizado;

/\*\*  
 \*  
 \* @author Jesús Cabeza Carballar y Lucia Lopez Alonso  
 \*/  
public class FacturacionLegacy {

    // Constantes de negocio extraídas para evitar números mágicos  
    private static final double DESCUENTO\_VIP \= 0.25;  
    private static final double DESCUENTO\_ESTANDAR \= 0.15;  
    private static final double DESCUENTO\_TIPO2 \= 0.05;

    /\*\*  
     \* Calcula el importe total final aplicando los descuentos correspondientes   
     \* según el tipo de cliente y su condición de socio VIP.  
     \*  
     \* @param importeBase   El coste inicial de la factura antes de aplicar descuentos.  
     \* @param tipoCliente   Identificador numérico del tipo de cliente (1, 2 u otros).  
     \* @param esSocioVip    Indicador booleano que determina si el cliente cuenta con suscripción VIP.  
     \* @return              El importe final calculado tras aplicar las deducciones.  
     \*/  
    public double calcularTotal(double importeBase, int tipoCliente, boolean esSocioVip) {  
        // Cláusula de guarda para filtrar casos no válidos de forma temprana  
        if (importeBase \<= 0\) {  
            return 0;  
        }

        // Procesamiento del Cliente Tipo 1  
        if (tipoCliente \== 1\) {  
            if (esSocioVip) {  
                return importeBase \- (importeBase \* DESCUENTO\_VIP);  
            }  
            return importeBase \- (importeBase \* DESCUENTO\_ESTANDAR);  
        }

        // Procesamiento del Cliente Tipo 2  
        if (tipoCliente \== 2\) {  
            return importeBase \- (importeBase \* DESCUENTO\_TIPO2);  
        }

        // Caso por defecto (sin descuentos aplicables)  
        return importeBase;  
    }  
}

## 

## 

## Fase 3: Verificación, Documentación y Entrega

1. **Validación de la Red de Seguridad:** Se ejecutaron nuevamente los tests. La suite de pruebas continuó respondiendo de forma satisfactoria (100% Verde), haciendo que la refactorización haya sido un éxito.  
2. **Documentación:** Se siguió el paso a paso la documentación del trabajo, e identificando, renombrando y extrayendo constantes del código, para poder refactorizar sin que tenga ningún error al final, conforme haciamos algun cambio veiamos si habia un error e íbamos poco a poco.   
3. **Control de Versiones (Git):** El desarrollo e intercambio de roles de la *programación en parejas* quedó registrado mediante commits.

# 6\. Uso de la IA

Si necesitas consultar o proponer cualquier consulta en forma de *prompt* a cualquier *chatbot* o Agente, es necesario abrir un apartado en tu trabajo que se llame “Consultas IA”. 

Seguidamente, apunta el *prompt* que has diseñado así como la respuesta otorgada.

| \# 1 |  |
| ----- | :---- |
| **Agente:** | Gemini 1.5 Pro  |
| **Prompt:** | Tengo un método en Java donde un if inicial (importe \> 0\) envuelve todo el algoritmo de descuentos y termina en un else { return 0; }. ¿Cuál es la forma correcta de aplicar una cláusula de guarda según los estándares de Clean Code para eliminar ese bloque else general?  |
| **Respuesta textual:** | Para aplicar una cláusula de guarda de forma correcta, debes invertir el condicional inicial para evaluar el caso de error o salida inmediata en la primera línea del método. En lugar de envolver todo el bloque feliz dentro de un gran if (importe \> 0), escribe al inicio: if (importe \<= 0\) { return 0; }. De esta manera, destruyes la necesidad del bloque else final, reduces un nivel de indentación horizontal en el código restante y consigues un flujo de lectura puramente vertical, limpio y directo.  |

