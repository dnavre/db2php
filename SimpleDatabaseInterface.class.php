<?php
/**
 * interface to use with db2php's Simple INterface Database Layer
 */
interface SimpleDatabaseInterface {
	
	/**
	 * execute sql and return result as hash
	 *
	 * @param string $sql
	 * @return array
	 */
	public function getResult($sql);

	/**
	 * execute sql query
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
}
?>