/**
 * 
 */
package teste2.pismo.pisdb

/**
 * @author marcelo junqueira
 *
 * classe para tratar transações no database
 */
class TransactionsDB extends DBInstance {
	
	/**
	 * make - gera/faz transação
	 * 
	 * @return true OK ou false problemas no database
	 */	
	Boolean make(AccountId_, OperationTpId_, Amount_, EventDate_, DueDate_) {
		
		if(AccountId_==null || OperationTpId_==null || Amount_==null) return false;
		if(EventDate_==null || DueDate_==null) {
			def agora = new Date();
			def agorafmt = agora.format("yyyy-MM-dd");
			if(EventDate_==null) EventDate_ = agorafmt;
			if(DueDate_==null) DueDate_ = agorafmt;
		}
		BigDecimal Amount = new BigDecimal( Amount_ );
		if( (OperationTpId_==1 || OperationTpId_==2) && Amount>0) Amount = Amount.negate();
		
		def sqlstring = "INSERT INTO Transactions(Account_ID, OperationType_ID, Amount, Balance, EventDate, DueDate ) VALUES " +
		"(${AccountId_}, ${OperationTpId_}, ${Amount}, ${Amount}, '${EventDate_}', '${DueDate_}'  )";

		return executedb(sqlstring);
	}
	
	/**
	 * Atualiza transação com pagamentos e registra na tabela PaymentsTransactions
	 */
	private void Atualizadb( TransacaoPag_, TransactionId_, BigDecimal Balanco_, BigDecimal Diferenca_ ) 
	{ 
		// Balanco_ = Balanco_.add(Diferenca_);
		def strSql = "UPDATE Transactions SET Balance=${Balanco_} WHERE Transaction_ID=${TransactionId_}";
		executedb(strSql);
		
		// 
		strSql = "INSERT INTO PaymentsTracking(CreditTransaction_ID, DebitTransaction_ID, Amount ) VALUES " +
		"(${TransacaoPag_}, ${TransactionId_}, ${Diferenca_})";
		sql.execute(strSql);
	}
	
	/**
	 * Realiza pagamento
	 * @return true OK ou false problemas no database
	 */
	Boolean doPayment(AccountId_, Amount_) {
		def agora = new Date();
		def agorafmt = agora.format("yyyy-MM-dd");
		
		if(!make(AccountId_, 4, Amount_, agorafmt, agorafmt)) return false;
		
		def rows = sql.rows("SELECT MAX(Transaction_ID) FROM Transactions WHERE Account_ID=${AccountId_}");
		def NumTransacao = rows[0][0]; // guarda o indice da transação para atualizar o Balance no final do processamento
		
		BigDecimal total = new BigDecimal( Amount_ );
		sql.eachRow("SELECT * FROM Transactions WHERE Balance<0 AND Account_ID=${AccountId_} ORDER BY OperationType_ID DESC, EventDate ASC;") { row ->
			if(total>0) {
				BigDecimal balanco = new BigDecimal("${row.Balance}");
				total = total.add(balanco);
				if(total>=0) {
					Atualizadb( NumTransacao, "${row.Transaction_ID}", 0, balanco.negate() );
				} else {
					Atualizadb( NumTransacao, "${row.Transaction_ID}", total, balanco.negate().add(total) );
					total = 0;
				}
			}
		}
		def strSql = "UPDATE Transactions SET Balance=${total} WHERE Transaction_ID=${NumTransacao}";
		return executedb(strSql);
	}
	
	
}
