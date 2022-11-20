package com.primeirobackend.dsmeta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.primeirobackend.dsmeta.entities.Sale;
import com.primeirobackend.dsmeta.repositories.SaleRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {  //todas essas variaveis sao de ambiente, q serao configuradas tanto no site do twilio e do heroku.
	//heroku é o serviço de nuvem.

	@Value("${twilio.sid}")
	private String twilioSid;

	@Value("${twilio.key}")
	private String twilioKey;

	@Value("${twilio.phone.from}")
	private String twilioPhoneFrom;

	@Value("${twilio.phone.to}")
	private String twilioPhoneTo;

	@Autowired
	private SaleRepository saleRepository;
	
	public void sendSms(Long saleId) {  //aqui pegando o id da venda, ou sale.
		
		Sale sale = saleRepository.findById(saleId).get(); //declarando a var pra fazer o find e get dentro do objeto saleId.
		
		String date = sale.getDate().getDayOfMonth() + "/" + sale.getDate().getYear();
		
		String msg = "Vendedor " + sale.getSellerName() + " Foi destaque em " + date + " com um total de R$ "
		+ String.format("%.2f", sale.getAmount());
		
		//essa parte foi pega de dentro do twilio no exemplo de utilização.

		Twilio.init(twilioSid, twilioKey);

		PhoneNumber to = new PhoneNumber(twilioPhoneTo);
		PhoneNumber from = new PhoneNumber(twilioPhoneFrom);

		Message message = Message.creator(to, from, msg).create();

		System.out.println(message.getSid());
	}
}

