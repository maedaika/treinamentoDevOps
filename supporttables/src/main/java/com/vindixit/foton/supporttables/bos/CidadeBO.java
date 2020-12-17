package com.vindixit.foton.supporttables.bos;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.vindixit.foton.supporttables.domains.Cidade;
import com.vindixit.foton.supporttables.exception.BDException;
import com.vindixit.foton.supporttables.repositories.CidadeRepository;




@Component("cidadeBO")
@Configuration
@ComponentScan("com.vindixit.foton.supporttables")
public class CidadeBO implements ICidadeBO{
	
	@Autowired
	CidadeRepository cidadeRepository;	
	

	@Override
	public Cidade cadastraCidade(Cidade cidade) throws BDException {
	
		Cidade cidadeRetornoPersit = null;
		try {
			 cidadeRetornoPersit = cidadeRepository.save(cidade);	
		} catch (Exception e) {
			throw new BDException(e.getMessage());
		}		
		return cidadeRetornoPersit;
	}

	@Override
	public void deleteTodasCidades() throws BDException {
		try {
			cidadeRepository.deleteAll();
		} catch (Exception e) {
			throw new BDException(e.getMessage());
		}
		
	}

	@Override
	public ArrayList<Cidade> buscaTodasCidades() throws BDException {
		
		ArrayList<Cidade> todasCidadesDoBanco =  new ArrayList<Cidade>();
		
		try {
			todasCidadesDoBanco = (ArrayList<Cidade>) cidadeRepository.findAll();			
		} catch (Exception e) {
			throw new BDException(e.getMessage());			
		}		
		
		return todasCidadesDoBanco;
	}

}
