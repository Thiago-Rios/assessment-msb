package com.infnet.veiculoservice.service.impl;

import com.infnet.veiculoservice.exception.ResourceNotFoundException;
import com.infnet.veiculoservice.model.Veiculo;
import com.infnet.veiculoservice.repository.VeiculoRepository;
import com.infnet.veiculoservice.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepository veiculoRepository;

    @Override
    public Optional<Veiculo> findById(Long id) {
        return veiculoRepository.findById(id);
    }

    @Override
    public Veiculo save(Veiculo veiculo) {
       return veiculoRepository.save(veiculo);
    }

    @Override
    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }

    @Override
    public Veiculo update(Long id, Veiculo veiculo) {
        Optional<Veiculo> existingVeiculo = veiculoRepository.findById(id);
        if (existingVeiculo.isPresent()) {
            veiculo.setId(id);
            return veiculoRepository.save(veiculo);
        } else {
            throw new ResourceNotFoundException("Veículo não encontrado com ID: " + id);
        }
    }

    @Override
    public void delete(long id) {
        Optional<Veiculo> existingVeiculo = veiculoRepository.findById(id);
        if (existingVeiculo.isPresent()) {
            veiculoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Veículo não encontrado com ID: " + id);
        }
    }
}
