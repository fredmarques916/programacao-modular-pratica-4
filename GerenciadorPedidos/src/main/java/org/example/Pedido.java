package org.example;

public abstract class Pedido implements Processavel {
    protected int id;
    protected String nomeCliente;
    protected double valor;
    protected StatusPedido status;
    protected Categoria categoria;

    public Pedido(int id, String nomeCliente, double valor, Categoria categoria) throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("Erro: O valor do pedido não pode ser menor ou igual a zero.");
        }
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.valor = valor;
        this.status = StatusPedido.PENDENTE;
        this.categoria = categoria;
    }

    public void alterarStatus(StatusPedido novoStatus) throws PedidoInvalidoException {
        if (this.status == StatusPedido.ENTREGUE && novoStatus != StatusPedido.ENTREGUE) {
            throw new PedidoInvalidoException("Erro: Um pedido já entregue não pode ter seu status alterado.");
        }
        this.status = novoStatus;
    }

    @Override
    public void processarPedido() throws PedidoInvalidoException {
        if (this.status == StatusPedido.CANCELADO) {
            throw new PedidoInvalidoException("Erro: Um pedido cancelado não pode ser processado.");
        }
        if (this.status == StatusPedido.ENTREGUE) {
            throw new PedidoInvalidoException("Erro: O pedido já foi entregue.");
        }
        this.status = StatusPedido.PROCESSANDO;
    }

    public int getId() { return id; }
    public StatusPedido getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("ID: %d | Cliente: %s | Categoria: %s | Status: %s | Valor Base: R$%.2f | Valor Final: R$%.2f",
                id, nomeCliente, categoria, status, valor, calcularValorFinal());
    }
}