/**
 * 
 */
package teste2.pismo.App


import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import teste2.pismo.pisdb.*

/**
 * @author marcelo junqueira
 * 
 */
class Accounts {
	
	/**
	 * PATCH 
	 */
	String Patch(accountid_, conteudo_) {

		// println(conteudo_);
		Boolean ok = false;
		def jsl = new JsonSlurper();
		def camposcmd = jsl.parseText( conteudo_ );
		if( camposcmd.containsKey('available_credit_limit') ) {
			AccountsDB dados = new AccountsDB();
			ok = dados.changeLimit(accountid_, 'AvailableCreditLimit', camposcmd.available_credit_limit.amount);
			dados.Close();
		}
		if( camposcmd.containsKey('available_withdrawal_limit') ) {
			AccountsDB dados = new AccountsDB();
			ok = dados.changeLimit(accountid_, 'AvailableWithdrawalLimit', camposcmd.available_withdrawal_limit.amount);
			dados.Close();
		}
		
		return JsonOutput.toJson( "" );
	}
	
	/**
	 * GET
	 * @param comando_ <id> ou 'limits'
	 */	
	String Get(String comando_) {
		if(comando_==null) comando_ = ""
		AccountsDB dados = new AccountsDB();
		def result = dados.get(comando_);
		dados.Close();
		return result;
	}

}
