<?php
/**
 * Description of SimpleDatabaseInterfacePDO
 *
 * @author poison
 */
class SimpleDatabaseInterfacePDO implements SimpleDatabaseInterface {
	/**
	 * instance of a PDO
	 *
	 * @var PDO
	 */
	private $pdo;

	function __construct($pdo) {
		$this->pdo=$pdo;
	}

    /**
	 * execute sql and return result as hash with the tables field name as index
	 *
	 * @param string $sql
	 * @return array
	 */
	public function getResult($sql) {
		$stmt=$this->pdo->query($sql);
		/* @var $stmt PDOStatement */
		$result=$stmt->fetchAll(PDO::FETCH_ASSOC);
		$stmt->closeCursor();
		return $result;
	}

	/**
	 * execute sql query and return whatever you like ;)
	 *
	 * @param execute $sql
	 * @return mixed
	 */
	public function executeSql($sql) {
		return $this->pdo->exec($sql);
	}

	/**
	 * escape and quote value for use in sql query. second parameter is the field id in case you need special handling for a particular field.
	 *
	 * @param mixed $value
	 * @param int $fieldId
	 * @return string
	 */
	public function escapeValue($value, $fieldId) {
		return $this->pdo->quote($value);
	}

	/**
	 * get the last insert id
	 *
	 * @return mixed
	 */
	public function lastInsertId() {
		return $this->pdo->lastInsertId();
	}
	
}
?>