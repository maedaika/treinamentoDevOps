package com.vindixit.foton.supporttables.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.vindixit.foton.supporttables.bos.CidadeBO;
import com.vindixit.foton.supporttables.domains.Cidade;
import com.vindixit.foton.supporttables.exception.BDException;
import com.vindixit.foton.supporttables.json.Response;



@RestController
@RequestMapping("/api/supporttables/v1")
@Configuration
@ComponentScan("com.vindixit.foton.supporttables")
public class CidadeController implements ICidadeController{
	
	@Autowired
	CidadeBO cidadeBO = null;

	//TESTE
	@Override
	@RequestMapping(value = "/cidade/cadastro", method =  RequestMethod.POST)
	public ResponseEntity<Response> cadastraCidade(@RequestParam String nomeCidade, @RequestParam String v10m,@RequestParam String temperatura) throws BDException {
		
		Cidade cidade = new Cidade();
		Response response = new Response();
		try {
			cidade.setDcNome(nomeCidade);	
			cidade.setV10m(new Double(v10m));
			cidade.setDcTemperatura(new Double(temperatura));			
			response.setModeloRetorno(cidadeBO.cadastraCidade(cidade));
			response.setMensagensRetorno("Cadastro realizado com sucesso!!!");
			
		} catch (Exception e) {
			response.setMensagensRetorno(e.getMessage());			
		}
		
		
		return ResponseEntity.ok(response);
	}

	@Override
	@RequestMapping(value = "/cidade/delete/todas", method =  RequestMethod.DELETE)
	public ResponseEntity<Response> deleteTodasCidades() throws BDException {
		Response response = new Response();
		
		try {
			cidadeBO.deleteTodasCidades();			
			response.setMensagensRetorno("Todas as cidades foram apagadas da base de dados!!!");
			
		} catch (Exception e) {
			response.setMensagensRetorno(e.getMessage());			
		}		
		
		return ResponseEntity.ok(response);
	}

	@Override
	@RequestMapping(value = "/cidade/busca/todas", method =  RequestMethod.GET)
	public ResponseEntity<Response> buscaTodasCidades() throws BDException {
		Response response = new Response();
		
		try {			
			response.setModeloRetorno(cidadeBO.buscaTodasCidades());
			response.setMensagensRetorno("Todas as cidades contidas na base de dados!!!");
			
		} catch (Exception e) {
			response.setMensagensRetorno(e.getMessage());			
		}		
		
		return ResponseEntity.ok(response);
		
	}

}
