package com.mycompany.coderefactorizado;

/**
 *
 * @author Jesús Cabeza Carballar y Lucia Lopez Alonso
 */
public class FacturacionLegacy {

    // Constantes de negocio extraídas para evitar números mágicos
    private static final double DESCUENTO_VIP = 0.25;
    private static final double DESCUENTO_ESTANDAR = 0.15;
    private static final double DESCUENTO_TIPO2 = 0.05;

    /**
     * Calcula el importe total final aplicando los descuentos correspondientes 
     * según el tipo de cliente y su condición de socio VIP.
     *
     * @param importeBase   El coste inicial de la factura antes de aplicar descuentos.
     * @param tipoCliente   Identificador numérico del tipo de cliente (1, 2 u otros).
     * @param esSocioVip    Indicador booleano que determina si el cliente cuenta con suscripción VIP.
     * @return              El importe final calculado tras aplicar las deducciones.
     */
    public double calcularTotal(double importeBase, int tipoCliente, boolean esSocioVip) {
        // Cláusula de guarda para filtrar casos no válidos de forma temprana
        if (importeBase <= 0) {
            return 0;
        }

        // Procesamiento del Cliente Tipo 1
        if (tipoCliente == 1) {
            if (esSocioVip) {
                return importeBase - (importeBase * DESCUENTO_VIP);
            }
            return importeBase - (importeBase * DESCUENTO_ESTANDAR);
        }

        // Procesamiento del Cliente Tipo 2
        if (tipoCliente == 2) {
            return importeBase - (importeBase * DESCUENTO_TIPO2);
        }

        // Caso por defecto (sin descuentos aplicables)
        return importeBase;
    }
}
