package com.vindixit.foton.supporttables.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.vindixit.foton.supporttables.exception.BDException;
import com.vindixit.foton.supporttables.json.Response;

public interface ICidadeController {
	
	
	 public ResponseEntity<Response> cadastraCidade(@RequestParam String nomeCidade, @RequestParam String v10m, @RequestParam String temperatura) throws BDException;
	 
	 public ResponseEntity<Response> deleteTodasCidades() throws BDException;
	 
	 public ResponseEntity<Response> buscaTodasCidades() throws BDException;

}
