
	/**
	 * get elements by example
	 *
	 * @param PDO $db
	 * @param <type> $example
	 * @return <type>[]
	 */
	public static function getByExample(PDO $db,<type> $example, $and=true) {
		$exampleValues=$example->toArray();
		$filter=array();
		foreach ($exampleValues as $fieldId=>$value) {
			if (!is_null($value)) {
				$filter[$fieldId]=$value;
			}
		}
		return self::getByFilter($db, $filter, $and);
	}

	/**
	 * get elements by filter. The filter is an hash with the field id as index and the value as filter value
	 *
	 * @param PDO $db
	 * @param array $filter
	 * @param boolean $and
	 * @return <type>[]
	 */
	public static function getByFilter(PDO $db, $filter, $and=true) {
		$sql='SELECT * FROM <tableNameQuoted>';
		$first=true;
		foreach ($filter as $fieldId=>$value) {
			if ($first) {
				$sql.=' WHERE ';
				$first=false;
			} else {
				$sql.=$and ? ' AND ' : ' OR ';
			}
			$sql.=self::SQL_IDENTIFIER_QUOTE . self::$FIELD_NAMES[$fieldId] . self::SQL_IDENTIFIER_QUOTE . '=?';
		}
		$stmt=self::prepareStatement($db, $sql);
		$i=0;
		foreach ($filter as $value) {
			$stmt->bindValue(++$i, $value);
		}
		$affected=$stmt->execute();
		$resultInstances=array();
		while($result=$stmt->fetch(PDO::FETCH_ASSOC)) {
			$o=new <type>();
			$o->assignByHash($result);
<pristine>			$resultInstances[]=$o;
		}
		$stmt->closeCursor();
		return $resultInstances;
	}
