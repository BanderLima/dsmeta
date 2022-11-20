package com.primeirobackend.dsmeta.services;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.primeirobackend.dsmeta.entities.Sale;
import com.primeirobackend.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {
	
	@Autowired
	private SaleRepository repository;
	
	public Page<Sale> findSales(String minDate, String maxDate, Pageable pageable) { //esse pageable serve para implementar uma paginação com 20 itens de cada vez.
		
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());  //criando uma data no dia de hoje.
		
		
		
		//esse equals serve pra isso, fazer essa comparação. Se for igual...
		LocalDate min = minDate.equals("") ? today.minusDays(365) : LocalDate.parse(minDate);
		LocalDate max = maxDate.equals("") ? today : LocalDate.parse(maxDate);  //aqui é assim: se maxDate for igual ao texto vazio, ele recebe today, se nao segue o padrao do local date.
		
		
	return repository.findSales(min, max, pageable);
	}

}
