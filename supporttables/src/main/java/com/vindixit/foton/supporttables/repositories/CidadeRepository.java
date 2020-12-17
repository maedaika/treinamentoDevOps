package com.vindixit.foton.supporttables.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.vindixit.foton.supporttables.domains.Cidade;

@Component("cidadeRepository")
public interface CidadeRepository extends CrudRepository<Cidade, Integer>{

}
