package org.example;

public interface Processavel {
    void processarPedido() throws PedidoInvalidoException;
    double calcularValorFinal();
}