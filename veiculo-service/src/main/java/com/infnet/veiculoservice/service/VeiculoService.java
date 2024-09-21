package com.infnet.veiculoservice.service;


import com.infnet.veiculoservice.model.Veiculo;

import java.util.List;
import java.util.Optional;

public interface VeiculoService {
    Optional<Veiculo> findById(Long id);
    Veiculo save(Veiculo veiculo);
    List<Veiculo> findAll();
    Veiculo update(Long id,Veiculo veiculo);
    void delete(long id);
}
