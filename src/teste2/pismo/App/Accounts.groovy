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

		int found = -1;
		try {
		    Integer accountid = Integer.valueOf(accountid_)
			Boolean ok = false;
			def jsl = new JsonSlurper();
			def camposcmd = jsl.parseText( conteudo_ );
			if( camposcmd.containsKey('available_credit_limit') ) {
				println "skdjaldjaskl"
				print camposcmd.available_credit_limit.amount;
				BigDecimal amount = new BigDecimal(camposcmd.available_credit_limit.amount); 
				AccountsDB dados = new AccountsDB();
				ok = dados.changeLimit(accountid, 'AvailableCreditLimit', amount);
				dados.Close();
				found++;
			}
			if( camposcmd.containsKey('available_withdrawal_limit') ) {
				BigDecimal amount = new BigDecimal(camposcmd.available_withdrawal_limit.amount);
				AccountsDB dados = new AccountsDB();
				ok = dados.changeLimit(accountid, 'AvailableWithdrawalLimit', amount);
				dados.Close();
				found++;
			} 
		 } catch(Exception ex) {
			println ex;
			return JsonOutput.toJson( "" );
		}
		
		if(found>=0) return JsonOutput.toJson( "" );
		else return "#Erro";
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
