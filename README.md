

# AEE: "Limpieza de Primavera"

## Refactorización de Código Legado

# 5\. Instrucciones Paso a Paso

## Fase 1: Análisis de la Deuda Técnica

1. **Verificación inicial.** Ejecuta los tests unitarios. Todo debe salir en verde. Esto os garantiza que el código original, por muy feo que sea, funciona.  
   Se ejecutó las pruebas unitarias provista en FacturacionLegacyTest.java antes de realizar modificaciones. Los tests se completaron con éxito (indicador verde), confirmando que el código original calcula correctamente los importes, a pesar de su pésima estructura interna.   
   

\-**Oler el código (*Code Smells*).** El Copiloto anotará en un bloc de notas los tres grandes problemas de este código:

* **Números mágicos.** ¿Qué significa 0.25 o 0.15? Son valores *hardcodeados* sin contexto. Si mañana el IVA o el descuento cambian, ¿dónde los buscamos?

  Los valores 0.25, 0.15 y 0.05 están incrustados directamente (*hardcodeados*) en las instrucciones de retorno. Carecen por completo de contexto semántico, lo que dificulta enormemente su mantenimiento si las políticas de descuento de la empresa cambian en el futuro.

  * **Variables sin significado.** Nombres como cT, m, tC o dV no aportan ninguna semántica. Nos obligan a adivinar.

  Los identificadores utilizados (cT, m, tc/tC, dV) son excesivamente cortos y no aportan información sobre las reglas de negocio que representan. Obligan al lector a realizar un esfuerzo cognitivo innecesario para deducir qué datos maneja el método.

  * **Código Spaghetti.** La anidación de múltiples if-else crea una estructura en forma de flecha \> que hace casi imposible seguir el flujo lógico de ejecución.

  La excesiva anidación de bloques if-else (hasta 3 niveles hacia la derecha) genera un flujo en forma de flecha (\>). Esto incrementa la complejidad del código y reduce de forma crítica su legibilidad.

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

