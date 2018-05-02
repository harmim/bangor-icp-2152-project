package com.icp2152.dvthdh;


import java.sql.*;
import java.util.*;
import java.io.IOException;
import javafx.util.Pair;


/**
 * Database connection.
 *
 * @author DVT HDH
 */
public class Database implements AutoCloseable
{
	/**
	 * Database connection instance.
	 */
	private Connection connection;


	/**
	 * Creates connection to database. This connection have to be closed by caller!
	 *
	 * @throws IOException if an error occurred when reading properties file
	 * @throws ClassNotFoundException if the driver class can not be located
	 * @throws SQLException if there is a problem with SQL driver
	 */
	public Database() throws IOException, ClassNotFoundException, SQLException
	{
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/database.properties"));

		String driver = properties.getProperty("mysql.driver"),
			url = properties.getProperty("mysql.url"),
			user = properties.getProperty("mysql.user"),
			password = properties.getProperty("mysql.password");

		if (driver != null) {
			Class.forName(driver);
		}

		connection = DriverManager.getConnection(url, user != null ? user : "", password != null ? password : "");
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws Exception
	{
		if (!connection.isClosed()) {
			connection.close();
		}
	}


	/**
	 * Get all rows from given table.
	 *
	 * @param table database table name
	 * @return ArrayList with all rows from table
	 *
	 * @throws SQLException if there is connection or any other database access error
	 */
	public ArrayList<HashMap<String, Object>> getAllFromTable(String table) throws SQLException
	{
		String query =
			"SELECT * "
			+ "FROM `" + table + "`";

		PreparedStatement preparedStatement = connection.prepareStatement(query);

		return resultSetToArrayList(preparedStatement.executeQuery());
	}


	/**
	 * Returns rows of given table by given criteria.
	 *
	 * @param table database table name
	 * @param criteriaColumn criteria column name
	 * @param criteriaValue criteria value
	 * @return appropriate rows
	 *
	 * @throws SQLException if there is ResultSet access error
	 */
	public ArrayList<HashMap<String, Object>> getRowsByCriteria(
		String table, String criteriaColumn, Object criteriaValue
	) throws SQLException
	{
		String query =
			"SELECT `" + table + "`.* "
			+ "FROM `" + table + "` "
			+ "WHERE `" + table + "`.`" + criteriaColumn + "` = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setObject(1, criteriaValue);

		return resultSetToArrayList(preparedStatement.executeQuery());
	}


	/**
	 * Returns first row of given table by given criteria.
	 *
	 * @param table database table name
	 * @param criteriaColumn criteria column name
	 * @param criteriaValue criteria value
	 * @return row with appropriate row
	 *
	 * @throws SQLException if there is ResultSet access error
	 */
	public HashMap<String, Object> getFirstRowByCriteria(String table, String criteriaColumn, Object criteriaValue)
		throws SQLException
	{
		String query =
			"SELECT `" + table + "`.* "
			+ "FROM `" + table + "` "
			+ "WHERE `" + table + "`.`" + criteriaColumn + "` = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setObject(1, criteriaValue);

		return resultSetGetFirst(preparedStatement.executeQuery());
	}


	/**
	 * Inserts given values to given table.
	 *
	 * @param table database table name
	 * @param values values to be inserted
	 * @return number of affected rows
	 *
	 * @throws SQLException if there is connection or any other database access error
	 */
	public int insert(String table, List<Pair<String, Object>> values) throws SQLException
	{
		StringBuilder query = new StringBuilder("INSERT INTO `" + table + "` (");
		StringBuilder parameters = new StringBuilder("VALUES (");
		for (int i = 0; i < values.size(); i++) {
			query.append('`').append(values.get(i).getKey()).append('`');
			parameters.append('?');

			if (i != values.size() - 1) {
				query.append(", ");
				parameters.append(", ");
			}
		}
		query.append(')');
		parameters.append(')');
		query.append(parameters);

		PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
		for (int i = 1; i <= values.size(); i++) {
			preparedStatement.setObject(i, values.get(i - 1).getValue());
		}
		int result = preparedStatement.executeUpdate();
		preparedStatement.close();

		return result;
	}


	/**
	 * Updates given table with given values according to given where condition.
	 *
	 * @param table database table name
	 * @param key key of where condition
	 * @param value value of where condition
	 * @param values new values for update
	 * @return number of affected rows
	 *
	 * @throws SQLException if there is connection or any other database access error
	 */
	public int update(String table, String key, Object value, List<Pair<String, Object>> values) throws SQLException
	{
		StringBuilder query = new StringBuilder("UPDATE `" + table + "` SET ");
		for (int i = 0; i < values.size(); i++) {
			query.append('`').append(values.get(i).getKey()).append("` = ?");

			if (i != values.size() - 1) {
				query.append(", ");
			}
		}
		query.append(" WHERE `").append(key).append("` = ?");

		PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
		for (int i = 1; i <= values.size(); i++) {
			preparedStatement.setObject(i, values.get(i - 1).getValue());
		}
		preparedStatement.setObject(values.size() + 1, value);
		int result = preparedStatement.executeUpdate();
		preparedStatement.close();

		return result;
	}


	/**
	 * Deletes row in given table according to given where condition.
	 *
	 * @param table database table name
	 * @param key key of where condition
	 * @param value value of where condition
	 * @return number of affected rows
	 *
	 * @throws SQLException if there is connection or any other database access error
	 */
	public int delete(String table, String key, Object value) throws SQLException
	{
		String query =
			"DELETE "
			+ "FROM `" + table + "` "
			+ "WHERE `" + table + "`.`" + key + "` = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setObject(1, value);

		int result = preparedStatement.executeUpdate();
		preparedStatement.close();

		return result;
	}


	/**
	 * Returns all user tests by user ID.
	 *
	 * @param userId user ID
	 * @return Array list with all user tests
	 *
	 * @throws SQLException if there is connection or any other database access error
	 */
	public ArrayList<HashMap<String, Object>> getUsersTests(int userId) throws SQLException
	{
		String query =
			"SELECT `test`.* "
			+ "FROM `test` "
			+ "JOIN `user` ON `user`.`id` = `test`.`user_id` "
			+ "WHERE `user`.`id` = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, userId);

		return resultSetToArrayList(preparedStatement.executeQuery());
	}


	/**
	 * Returns first row of ResultSet, if any.
	 *
	 * @param resultSet ResultSet to be converted
	 * @return first row of ResultSet or null
	 *
	 * @throws SQLException if there is ResultSet access error
	 */
	private HashMap<String, Object> resultSetGetFirst(ResultSet resultSet) throws SQLException
	{
		HashMap<String, Object> result = null;
		ArrayList<HashMap<String, Object>> list = resultSetToArrayList(resultSet);

		if (!list.isEmpty()) {
			result = list.get(0);
		}

		resultSet.close();

		return result;
	}


	/**
	 * Coverts ResultSet to appropriate ArrayList.
	 *
	 * @param resultSet ResultSet to be converted
	 * @return converted ArrayList
	 *
	 * @throws SQLException if there is ResultSet access error
	 */
	private ArrayList<HashMap<String, Object>> resultSetToArrayList(ResultSet resultSet) throws SQLException
	{
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnsCount = metaData.getColumnCount();
		ArrayList<HashMap<String, Object>> list = new ArrayList<>();

		while (resultSet.next()) {
			HashMap<String, Object> row = new HashMap<>(columnsCount);
			for (int i = 1; i <= columnsCount; i++) {
				row.put(metaData.getColumnName(i), resultSet.getObject(i));
			}
			list.add(row);
		}

		resultSet.close();

		return list;
	}
}
