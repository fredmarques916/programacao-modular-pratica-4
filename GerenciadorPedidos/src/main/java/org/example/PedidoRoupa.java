package org.example;

public class PedidoRoupa extends Pedido implements Entregavel {

    public PedidoRoupa(int id, String nomeCliente, double valor) throws ValorInvalidoException {
        super(id, nomeCliente, valor, Categoria.ROUPA);
    }

    @Override
    public double calcularValorFinal() {
        return (this.valor > 200) ? this.valor * 0.80 : this.valor; // 20% de desconto
    }

    @Override
    public double calcularFrete() {
        return 10.0 + (this.valor * 0.05); // R$10 + 5%
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Frete: R$%.2f", calcularFrete());
    }
}