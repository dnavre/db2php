
	/**
	 * Query by Example.
	 *
	 * Match by attributes of passed example instance and return matched rows as an array of <type> instances
	 *
	 * @param PDO $db a PDO Database instance
	 * @param <type> $example an example instance
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
	 * Query by filter.
	 *
	 * The filter can be either an hash with the field id as index and the value as filter value,
	 * or a array of DFC instances.
	 *
	 * Will return matched rows as an array of <type> instances.
	 *
	 * @param PDO $db a PDO Database instance
	 * @param array $filter
	 * @param boolean $and
	 * @return <type>[]
	 */
	public static function getByFilter(PDO $db, $filter, $and=true) {
		if ($filter instanceof DFC) {
			$filter=array($filter);
		}
		$sql='SELECT * FROM <tableNameQuoted>'
		. self::getSqlWhere($filter, $and);

		$stmt=self::prepareStatement($db, $sql);
		$i=0;
		foreach ($filter as $value) {
			$dfc=$value instanceof DFC;
			if ($dfc && 0!=(DFC::IS_NULL&$value->getMode())) {
				continue;
			}
			$stmt->bindValue(++$i, $dfc ? $value->getSqlValue() : $value);
		}
		$affected=$stmt->execute();
		if (false===$affected) {
			$stmt->closeCursor();
			throw new Exception($stmt->errorCode() . ':' . var_export($stmt->errorInfo(), true), 0);
		}
		$resultInstances=array();
		while($result=$stmt->fetch(PDO::FETCH_ASSOC)) {
			$o=new <type>();
			$o->assignByHash($result);
<pristine>			$resultInstances[]=$o;
		}
		$stmt->closeCursor();
		return $resultInstances;
	}

	/**
	 * Get sql WHERE part from filter.
	 *
	 * @param array $filter
	 * @param bool $and
	 * @return string
	 */
	protected static function getSqlWhere($filter, $and) {
		$sql=null;
		$andString=$and ? ' AND ' : ' OR ';
		$first=true;
		foreach ($filter as $fieldId=>$value) {
			if ($first) {
				$sql.=' WHERE ';
				$first=false;
			} else {
				$sql.=$andString;
			}
			if ($value instanceof DFC) {
				/* @var $value DFC */
				$sql.=self::SQL_IDENTIFIER_QUOTE . self::$FIELD_NAMES[$value->getField()] . self::SQL_IDENTIFIER_QUOTE
				. $value->getSqlOperatorPrepared();

			} else {
				$sql.=self::SQL_IDENTIFIER_QUOTE . self::$FIELD_NAMES[$fieldId] . self::SQL_IDENTIFIER_QUOTE . '=?';
			}
		}
		return $sql;
	}

	/**
	 * Execute query and return matched rows as an array of <type> instances.
	 *
	 * @param PDO $db a PDO Database instance
	 * @param string $sql
	 * @return <type>[]
	 */
	public static function getBySql(PDO $db, $sql) {
		$stmt=$db->query($sql);
		$resultInstances=array();
		while($result=$stmt->fetch(PDO::FETCH_ASSOC)) {
			$o=new <type>();
			$o->assignByHash($result);
<pristine>			$resultInstances[]=$o;
		}
		$stmt->closeCursor();
		return $resultInstances;
	}

