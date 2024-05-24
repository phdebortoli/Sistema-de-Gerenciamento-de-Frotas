import java.util.Optional;
import java.util.Scanner;

import entities.Carro;
import entities.Moto;
import entities.Veiculo;

public class CadVeiculo {
    private static Scanner scan;
    private static VeiculoService veiculoService;

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        veiculoService = new VeiculoService();
        int opcao;
        do {
            System.out.print("\033[H\033[2J");
            System.out.println("Sistema de Gerenciamento de Frotas");
            System.out.println("MENU DE OPÇÕES:");
            System.out.println("1 - Cadastrar Novo Veículo");
            System.out.println("2 - Listar todos os Veículos");
            System.out.println("3 - Pesquisar Veículo por Placa");
            System.out.println("4 - Remover Veículo");
            System.out.println("0 - Sair");
            System.out.print("Digite a opção desejada: ");
            do {
                if (scan.hasNextInt()) {
                    opcao = scan.nextInt();
                    if (opcao >= 0 && opcao <= 4)
                        break;
                }
                scan.nextLine();
                System.out.print("Digite um número dentro das Opções acima: ");
            } while (true);
            scan.nextLine();
            switch (opcao) {
                case 1:
                    save();
                    break;
                case 2:
                    veiculoService.imprimirVeiculos();
                    break;
                case 3:
                    veiculoService.placaVeiculo();
                    break;
                case 4:
                    veiculoService.removePlaca();
                    break;
                case 0:
                    System.out.print("\033[H\033[2J");
                    System.out.println("Volte logo!");
                    break;
            }
        } while (opcao != 0);
    }

    public static void save() {
        Veiculo veiculoAdd;
        System.out.print("\033[H\033[2J");
        System.out.println("CADASTRO DE VEÍCULO");
        System.out.println("Qual o tipo de veículo");
        System.out.println("1 - Carro");
        System.out.println("2 - Moto");
        System.out.print("Digite a opção desejada: ");
        int tipoVeiculo;
        do {
            if (scan.hasNextInt()) {
                tipoVeiculo = scan.nextInt();
                if (tipoVeiculo >= 1 && tipoVeiculo <= 2)
                    break;
            }
            scan.nextLine();
            System.out.print("Digite um número dentro das Opções acima: ");
        } while (true);
        scan.nextLine();

        String descricao = tipoVeiculo == 1 ? "do carro: " : "da moto: ";

        String marca;
        while (true) {
            System.out.print("Digite a marca " + descricao);
            marca = scan.nextLine();
            if (!marca.trim().isEmpty()) {
                break;
            }
            System.out.println("A marca não pode estar em branco. Por favor, tente novamente.");
        }

        String modelo;
        while (true) {
            System.out.print("Digite o modelo " + descricao);
            modelo = scan.nextLine();
           if (!modelo.trim().isEmpty()) { // linha comentada, para mostrar outro erro que seria ao final do programa
                break;
            } 
           System.out.println("O modelo não pode estar em branco. Por favor, tente novamente.");
        }
        int ano;
        while (true) {
            System.out.print("Digite o ano " + descricao);
            String anoVeiculo = scan.nextLine();
            if (!anoVeiculo.trim().isEmpty()) {
                try {
                    ano = Integer.parseInt(anoVeiculo);
                    if (ano > 0 && ano <= 9999) {
                        break;
                    }
                } catch (NumberFormatException e) {
                }
            }
            System.out.print("Digite um ano válido: ");
        }

        String placa;
        while (true) {
            System.out.print("Digite a placa " + descricao);
            placa = scan.nextLine();
            if (!placa.trim().isEmpty()) {
                Optional<Veiculo> veiculoExistente = veiculoService.placaVeiculo(placa);
                if (veiculoExistente.isPresent()) {
                    System.out.println("A placa já existe. Por favor, tente novamente.");
               } else {
                    break;
               }
           } else {
               System.out.println("A placa não pode estar em branco. Por favor, tente novamente.");
            }
        }

        if (tipoVeiculo == 1) {
            int numeroPortas;
            while (true) {
                System.out.print("Digite o número de portas: ");
                if (scan.hasNextInt()) {
                    numeroPortas = scan.nextInt();
                    if (numeroPortas == 2 || numeroPortas == 4) {
                        break;
                    }
                }
                System.out.print("Digite um número de portas válido: ");
            }
            scan.nextLine();
            veiculoAdd = new Carro(marca, modelo, ano, placa, numeroPortas);
        } else {
            int partidaEletrica;
            while (true) {
                System.out.print("A moto possui partida elétrica? 1-Sim, 2-Não: ");
                if (scan.hasNextInt()) {
                    partidaEletrica = scan.nextInt();
                    if (partidaEletrica == 1 || partidaEletrica == 2) {
                        break;
                    }
                }
                scan.nextLine();
                System.out.println("Digite um número dentro das opções acima: ");
            }
            scan.nextLine();
            boolean partida = partidaEletrica == 1;
            veiculoAdd = new Moto(marca, modelo, ano, placa, partida);
        }
        try {
            veiculoService.save(veiculoAdd);
            System.out.println("Veículo cadastrado com sucesso!");
            System.out.println("Pressione Enter para voltar ao Menu Inicial");
            scan.nextLine();
        } catch (Exception e) {
            System.out.println("NÃO FOI POSSÍVEL CADASTRAR O VEÍCULO");
            System.out.println(e.getMessage());
            System.out.println("Pressione Enter para voltar ao Menu Inicial");
            scan.nextLine();
        }
    }
}