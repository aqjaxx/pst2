package teste2.pismo.App

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import teste2.pismo.pisdb.*

/**
 * @author marcelo junqueira
 *
 */
class Transactions {
	
	/**
	 * POST
	 */
	String Post(String comando_, String conteudo_) {
		
		switch(comando_) {
			case 'transactions' :
				try {
					def jsl = new JsonSlurper();
					def camposcmd = jsl.parseText( conteudo_ );
					TransactionsDB dados = new TransactionsDB();
					def evdate = (camposcmd.containsKey('event_date')) ? camposcmd_event_date : null;
					def dudate = (camposcmd.containsKey('due_date')) ? camposcmd_due_date : null;
					if(dados.make( camposcmd.account_id, camposcmd.operation_type_id, camposcmd.amount, evdate, dudate)) {
						dados.Close();
					    return JsonOutput.toJson( camposcmd );
					}
					dados.Close();
				} catch(Exception ex) {
					println ex;
				}
				break;
			case 'payments' :
				try {
					def jsl = new JsonSlurper();
					def camposcmd = jsl.parseText( conteudo_ );
					TransactionsDB dados = new TransactionsDB();
					camposcmd.each() { walk ->
						dados.doPayment(walk.account_id, walk.amount);
					}
					dados.Close();				
				} catch(Exception ex) {
					println ex;
				}			
				break;
		}
		
		return JsonOutput.toJson( "" );
	}

}
