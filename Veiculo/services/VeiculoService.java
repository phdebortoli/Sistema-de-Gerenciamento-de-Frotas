package services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import entities.Carro;
import entities.Moto;
import entities.Veiculo;

public class VeiculoService {
    private List<Veiculo> veiculosDB;
    private Scanner scan;

    public List<Veiculo> getVeiculosDB() {
        return veiculosDB;
    }

    public VeiculoService() {
        this.veiculosDB = new ArrayList<>();
        this.scan = new Scanner(System.in);
    }
    
    public Veiculo save(Veiculo veiculo) throws Exception {
        validateVeiculo(veiculo);
        Optional<Veiculo> veiculoExistente = placaVeiculo(veiculo.getPlaca());
        if (veiculoExistente.isPresent()) {
            throw new Exception("Veículo com esta placa já existe");
        }
        veiculosDB.add(veiculo);
        return veiculo;
    }
    
    public void cadastrarVeiculo() {
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
            System.out.print("Digite um número dentro das opções acima: ");
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
            if (!modelo.trim().isEmpty()) {
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
            System.out.println("O ano não pode estar branco ou ser zero. Por favor, tente novamente");
        }

        String placa;
        while (true) {
            System.out.print("Digite a placa " + descricao);
            placa = scan.nextLine();
            if (!placa.trim().isEmpty()) {
                Optional<Veiculo> veiculoExistente = placaVeiculo(placa);
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
                scan.nextLine(); 
                System.out.println("Digite um número de portas válido.");
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
                System.out.print("Digite um número dentro das opções acima: ");
            }
            scan.nextLine(); 
            boolean partida = partidaEletrica == 1;
            veiculoAdd = new Moto(marca, modelo, ano, placa, partida);
        }
        try {
            save(veiculoAdd);
            System.out.println("Veículo cadastrado com sucesso!");
            System.out.print("Pressione Enter para continuar...");
            scan.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            scan.nextLine();
        }
    }

    public void imprimirVeiculos() {
        List<Veiculo> veiculos = getVeiculosDB();
        System.out.print("\033[H\033[2J");
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
        } else {
            for (Veiculo veiculo : veiculos) {
                System.out.println(veiculo);
                System.out.println("Tempo de uso: " + veiculo.calcularTempoDeUso() + " ano(s)");
                System.out.println("Imposto: " + veiculo.calcularImposto());
                System.out.println("-----------------------");
            }
        }
        System.out.print("Pressione Enter para continuar...");
        scan.nextLine();
    }

    public void placaVeiculo() {
        System.out.print("\033[H\033[2J");
        System.out.print("Digite a placa do veículo que deseja pesquisar: ");
        String placa = scan.nextLine();
        Optional<Veiculo> veiculo = placaVeiculo(placa);
        if (veiculo.isPresent()) {
            System.out.println(veiculo.get());
            System.out.println("Tempo de uso: " + veiculo.get().calcularTempoDeUso() + " ano(s)");
            System.out.println("Imposto: " + veiculo.get().calcularImposto());
        } else {
            System.out.println("Veículo não encontrado.");
        }
        System.out.print("Pressione Enter para continuar...");
        scan.nextLine();
    }

    public void removeVeiculo() {
        System.out.print("\033[H\033[2J");
        System.out.print("Digite a placa do veículo que deseja remover: ");
        String placa = scan.nextLine();
        try {
            removeVeiculo(placa);
            System.out.println("Veículo removido com sucesso.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Pressione Enter para continuar...");
        scan.nextLine();
    }

    public Optional<Veiculo> placaVeiculo(String placa) {
        return veiculosDB.stream().filter(veiculo -> veiculo.getPlaca().equalsIgnoreCase(placa)).findFirst();
    }

    public void removeVeiculo(String placa) throws Exception {
        Optional<Veiculo> veiculo = placaVeiculo(placa);
        if (veiculo.isPresent()) {
            veiculosDB.remove(veiculo.get());
        } else {
            throw new Exception("Veículo não encontrado.");
        }
    }

    private void validateVeiculo(Veiculo veiculo) throws Exception {
        if (veiculo == null)
            throw new Exception("Objeto nulo");
        if (veiculo.getModelo() == null || veiculo.getModelo().isEmpty())
            throw new Exception("Campo Modelo não pode ser em branco");
        if (veiculo.getMarca() == null || veiculo.getMarca().isEmpty())
            throw new Exception("Campo Marca não pode ser em branco");
        if (veiculo.getAno() == 0)
            throw new Exception("Campo Ano não pode ser 0");
        if (veiculo.getPlaca() == null || veiculo.getPlaca().isEmpty())
            throw new Exception("Campo Placa não pode ser em branco");
        if (veiculo instanceof Carro && ((Carro) veiculo).getNumeroPortas() == 0)
            throw new Exception("Número de portas não pode ser 0");
    }
}