package org.example;

public class PedidoEletronico extends Pedido implements Entregavel {

    public PedidoEletronico(int id, String nomeCliente, double valor) throws ValorInvalidoException {
        super(id, nomeCliente, valor, Categoria.ELETRONICO);
    }

    @Override
    public double calcularValorFinal() {
        return (this.valor > 100) ? this.valor * 0.90 : this.valor; // 10% de desconto
    }

    @Override
    public double calcularFrete() {
        return 20.0 + (this.valor * 0.10); // R$20 + 10%
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Frete: R$%.2f", calcularFrete());
    }
}