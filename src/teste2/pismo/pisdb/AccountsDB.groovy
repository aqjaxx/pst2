/**
 * 
 */
package teste2.pismo.pisdb

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

/**
 * @author marcelo junqueira
 *
 * classe para tratar os accounts no database
 */
class AccountsDB extends DBInstance {
	
	/**
	 * Insere novo account
	 * @return true OK ou false falha no database
	 */
	Boolean insertID(int Account_ID,int AvailableCreditLimit, int AvailableWithdrawalLimit ) {
		def sqlstring = "INSERT INTO Accounts(Account_ID, AvailableCreditLimit, AvailableWithdrawalLimit) VALUES " +
						"(${Account_ID}, ${AvailableCreditLimit}, ${AvailableWithdrawalLimit} )";
		return executedb(sqlstring);
	}
	
	/**
	 * Obtem dados do account no database 
	 * @return JSON format com os dados requisitados ou #Erro se requisição for desconhecida
	 */
	
	String get(whats) {

		switch(whats) {
			case "limits" : 
				return JsonOutput.toJson(sql.rows('select * from Accounts'));
			case "" :
				return JsonOutput.toJson(sql.rows('select Account_ID from Accounts'));
			case Number:
				return JsonOutput.toJson(sql.rows("select * from Accounts where Account_ID=${whats}"));
		}
		
		return "#Erro";
	}
	
	/**
	 * Altera os limites do account<ID>
	 * @return true se OK ou false se não fez alterações no database
	 */	
	Boolean changeLimit(int Account_ID, String camponm, BigDecimal valor)
	{

		String sgre = get(Account_ID);
		if(sgre.charAt(0)=='#' || sgre.charAt(1)==']') return false;
		
		def jsl = new JsonSlurper();
		Object account = jsl.parseText( sgre.substring(1, sgre.length()-1) );
		
		// def camponm = "";
		// if( whatscg.equalsIgnoreCase("available_credit_limit") ) camponm = "AvailableCreditLimit";
		// else if( whatscg.equalsIgnoreCase("available_withdrawal_limit") ) camponm = "AvailableWithdrawalLimit";
		// else return false;
		
		BigDecimal valora = new BigDecimal(valor);
		if( valor < 0) {
			BigDecimal valorac = account."${camponm}";
			valora = valorac.add( valora );
			if(valora < 0) valora = 0;
		}
		
		def strSql = "UPDATE Accounts SET ${camponm}=" + valora.toString() + " where Account_ID=${Account_ID}";
		return executedb(strSql);		
	}

}
