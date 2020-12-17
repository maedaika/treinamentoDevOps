package com.vindixit.foton.supporttables.bos;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.vindixit.foton.supporttables.domains.Cidade;
import com.vindixit.foton.supporttables.exception.BDException;
import com.vindixit.foton.supporttables.json.Response;

public interface ICidadeBO {
	
	public Cidade cadastraCidade(Cidade cidade) throws BDException;
	
	public void deleteTodasCidades() throws BDException;	
	
	public ArrayList<Cidade> buscaTodasCidades() throws BDException;

}
