package org.example;

import org.example.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Pedido> listaPedidos = new ArrayList<>();
        int contadorId = 1;
        int opcao = 0;

        while (opcao != 7) {
            System.out.println("\n=== Sistema Gerenciador de Pedidos ===");
            System.out.println("1. Cadastrar pedido");
            System.out.println("2. Alterar o status de um pedido");
            System.out.println("3. Processar pedidos");
            System.out.println("4. Remover cancelados");
            System.out.println("5. Exibir lista de pedidos (sem os cancelados)");
            System.out.println("6. Exibir pedidos entregáveis");
            System.out.println("7. Encerrar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Nome do Cliente: ");
                    String nome = scanner.nextLine();
                    System.out.print("Valor do Pedido: ");
                    double valor = Double.parseDouble(scanner.nextLine());

                    System.out.println("Escolha a categoria (1-Eletrônico, 2-Roupa, 3-Alimento): ");
                    int cat = Integer.parseInt(scanner.nextLine());

                    try {
                        Pedido novoPedido = null;
                        if (cat == 1) {
                            novoPedido = new PedidoEletronico(contadorId++, nome, valor);
                        } else if (cat == 2) {
                            novoPedido = new PedidoRoupa(contadorId++, nome, valor);
                        } else if (cat == 3) {
                            novoPedido = new PedidoAlimento(contadorId++, nome, valor);
                        } else {
                            System.out.println("Categoria inválida.");
                            break;
                        }
                        listaPedidos.add(novoPedido);
                        System.out.println("Pedido cadastrado com sucesso! ID: " + novoPedido.getId());
                    } catch (ValorInvalidoException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Erro ao cadastrar: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Digite o ID do pedido: ");
                    int idAlterar = Integer.parseInt(scanner.nextLine());
                    Pedido pedidoEncontrado = null;

                    for (Pedido p : listaPedidos) {
                        if (p.getId() == idAlterar) {
                            pedidoEncontrado = p;
                            break;
                        }
                    }

                    if (pedidoEncontrado != null) {
                        System.out.println("Status atuais (1-PENDENTE, 2-PROCESSANDO, 3-ENVIADO, 4-ENTREGUE, 5-CANCELADO): ");
                        int st = Integer.parseInt(scanner.nextLine());
                        StatusPedido novoStatus = StatusPedido.values()[st - 1];

                        try {
                            pedidoEncontrado.alterarStatus(novoStatus);
                            System.out.println("Status atualizado.");
                        } catch (PedidoInvalidoException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Pedido não encontrado.");
                    }
                    break;

                case 3:
                    Iterator<Pedido> itProcessar = listaPedidos.iterator();
                    int processados = 0;
                    while (itProcessar.hasNext()) {
                        Pedido p = itProcessar.next();
                        try {
                            // Tenta processar apenas os que não estão cancelados ou entregues
                            if(p.getStatus() != StatusPedido.CANCELADO && p.getStatus() != StatusPedido.ENTREGUE && p.getStatus() != StatusPedido.PROCESSANDO){
                                p.processarPedido();
                                processados++;
                            }
                        } catch (PedidoInvalidoException e) {
                            System.out.println("Não foi possível processar pedido ID " + p.getId() + ": " + e.getMessage());
                        }
                    }
                    System.out.println(processados + " pedido(s) movidos para PROCESSANDO.");
                    break;

                case 4:
                    Iterator<Pedido> itRemover = listaPedidos.iterator();
                    int removidos = 0;
                    while (itRemover.hasNext()) {
                        Pedido p = itRemover.next();
                        if (p.getStatus() == StatusPedido.CANCELADO) {
                            itRemover.remove();
                            removidos++;
                        }
                    }
                    System.out.println(removidos + " pedido(s) cancelado(s) removido(s) do sistema.");
                    break;

                case 5:
                    System.out.println("\n--- Lista de Pedidos ---");
                    for (Pedido p : listaPedidos) {
                        if (p.getStatus() != StatusPedido.CANCELADO) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 6:
                    System.out.println("\n--- Pedidos Entregáveis ---");
                    for (Pedido p : listaPedidos) {
                        if (p instanceof Entregavel) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 7:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }
}