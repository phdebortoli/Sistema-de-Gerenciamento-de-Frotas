import java.util.Scanner;
import services.VeiculoService;

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
            System.out.print("Digite a opção desejada:");
            do {
                if (scan.hasNextInt()) {
                    opcao = scan.nextInt();
                    if (opcao >= 0 && opcao <= 4)
                        break;
                }
                scan.nextLine();
                System.out.print("Digite um número dentro das opções acima:");
            } while (true);
            scan.nextLine();
            switch (opcao) {
                case 1:
                    cadastrarVeiculo();
                    break;
                case 2:
                    veiculoService.imprimirVeiculos();
                    break;
                case 3:
                    veiculoService.placaVeiculo();
                    break;
                case 4:
                    veiculoService.removeVeiculo();
                    break;
                case 0:
                    System.out.print("\033[H\033[2J");
                    System.out.println("Volte logo!");
                    break;
            }
        } while (opcao != 0);
    }

    private static void cadastrarVeiculo() {
        veiculoService.cadastrarVeiculo();
    }
}