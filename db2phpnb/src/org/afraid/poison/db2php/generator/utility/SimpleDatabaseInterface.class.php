<?php
/**
 * interface to use with db2php's Simple Interface Database Layer
 */
interface SimpleDatabaseInterface {
	
	/**
	 * execute sql and return result as hash with the tables field name as index
	 *
	 * @param string $sql
	 * @return array
	 */
	public function getResult($sql);

	/**
	 * execute sql query and return whatever you like ;)
	 *
	 * @param execute $sql
	 * @return mixed
	 */
	public function executeSql($sql);
	
	/**
	 * escape and quote value for use in sql query. second parameter is the field id in case you need special handling for a particular field.
	 *
	 * @param mixed $value
	 * @param int $fieldId
	 * @return string
	 */
	public function escapeValue($value, $fieldId);

	/**
	 * get the last insert id
	 *
	 * @return mixed
	 */
	public function lastInsertId();
}
?>