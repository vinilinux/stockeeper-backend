package br.com.stockeeper.gestao_inventario.exceptions;

public class InventarioException extends RuntimeException {

    public InventarioException(String message, Throwable cause) {
        super(message, cause);
    }

}
