package org.example;

public class PedidoAlimento extends Pedido {

    public PedidoAlimento(int id, String nomeCliente, double valor) throws ValorInvalidoException {
        super(id, nomeCliente, valor, Categoria.ALIMENTO);
    }

    @Override
    public double calcularValorFinal() {
        return this.valor; // sem desconto
    }

    // Método específico da classe, já que não implementa a interface Entregavel
    public double calcularFrete() {
        return 5.0;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Frete Fixo: R$%.2f (Não utiliza serviço de entrega padrão)", calcularFrete());
    }
}