package services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entities.Veiculo;

public class VeiculoService {
    private List<Veiculo> veiculosDB;

    public List<Veiculo> getVeiculosDB() {
        return veiculosDB;
    }

    public VeiculoService() {
        this.veiculosDB = new ArrayList<>();
    }
    
    public Veiculo save(Veiculo veiculo) throws Exception {
        if (veiculo == null)
            throw new Exception("Objeto nullo");
        if (veiculo.getModelo() == null || veiculo.getModelo().isEmpty())
            throw new Exception("Campo Modelo não pode ser em branco");
        veiculosDB.add(veiculo);
        return veiculo;
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
}