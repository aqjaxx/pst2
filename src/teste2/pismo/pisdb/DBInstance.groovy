/**
 * 
 */
package teste2.pismo.pisdb;

import java.sql.*;
import groovy.sql.Sql;


/**
 * @author marcelo junqueira
 *
 * classe abstrata de acesso ao database
 */
abstract class DBInstance {
	
	def sql;
	
	DBInstance() {
		sql = Sql.newInstance('jdbc:mysql://localhost:3306/pisdb', 'root', 'pass123', 'com.mysql.jdbc.Driver');
		sql.connection.autoCommit = false;
	}
	
	protected Boolean executedb(String strSql) {
		try {
			sql.execute(strSql);
			sql.commit();
			println("Successfully committed");
			return true;
		} catch(Exception ex) {
			sql.rollback()
			println("Transaction rollback")
		}
		return false;
	}
	
	void Close() {
		sql.close();
	}

}
