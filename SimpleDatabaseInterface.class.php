<?php
/**
 * interface for use with db2php
 */
interface SimpleDatabaseInterface {
	/**
	 * execute sql and return result as hash
	 *
	 * @param string $sql
	 * @return array
	 */
	public function getResult($sql) {
		return $x;
	}
	/**
	 * execute sql query
	 *
	 * @param execute $sql
	 * @return mixed
	 */
	public function executeSql($sql) {
		return $x;
	}
	/**
	 * escape and quote value for use in sql query
	 *
	 * @param mixed $value
	 * @return string
	 */
	public function escapeValue($value) {
		return $x;
	}
}
?>