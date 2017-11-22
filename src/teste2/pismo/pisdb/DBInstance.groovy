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
		String sqlLocal = "localhost";
		def paths = System.getProperty("user.dir");
		def fileLocalSql = paths + '/endereco_mysql';
		File f = new File(fileLocalSql);
		if( f.exists() ) {
			String fxl = f.text
			if(fxl != null && !fxl.isEmpty()) {
				sqlLocal = fxl;
			}
		}	
		sql = Sql.newInstance("jdbc:mysql://${sqlLocal}:3306/pisdb", 'root', 'pass123', 'com.mysql.jdbc.Driver');
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
