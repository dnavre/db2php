
	/**
	 * Query by Example.
	 *
	 * Match by attributes of passed example instance and return matched rows as an array of <type> instances
	 *
	 * @param PDO $db a PDO Database instance
	 * @param <type> $example an example instance defining the conditions. All non-null properties will be considered a constraint, null values will be ignored.
	 * @param boolean $and true if conditions should be and'ed, false if they should be or'ed
	 * @param array $sort array of DSC instances
	 * @return <type>[]
	 */
	public static function findByExample(PDO $db,<type> $example, $and=true, $sort=null) {
		$exampleValues=$example->toArray();
		$filter=array();
		foreach ($exampleValues as $fieldId=>$value) {
			if (null!==$value) {
				$filter[$fieldId]=$value;
			}
		}
		return self::findByFilter($db, $filter, $and, $sort);
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
	 * @param array $filter array of DFC instances defining the conditions
	 * @param boolean $and true if conditions should be and'ed, false if they should be or'ed
	 * @param array $sort array of DSC instances
	 * @return <type>[]
	 */
	public static function findByFilter(PDO $db, $filter, $and=true, $sort=null) {
		if ($filter instanceof DFC) {
			$filter=array($filter);
		}
		$sql='SELECT * FROM <tableNameQuoted>'
		. self::buildSqlWhere($filter, $and, false, true)
		. self::buildSqlOrderBy($sort);

		$stmt=self::prepareStatement($db, $sql);
		self::bindValuesForFilter($stmt, $filter);
		return self::fromStatement($stmt);
	}

	/**
	 * Will execute the passed statement and return the result as an array of <type> instances
	 *
	 * @param PDOStatement $stmt
	 * @return <type>[]
	 */
	public static function fromStatement(PDOStatement $stmt) {
		$affected=$stmt->execute();
		if (false===$affected) {
			$stmt->closeCursor();
			throw new Exception($stmt->errorCode() . ':' . var_export($stmt->errorInfo(), true), 0);
		}
		return self::fromExecutedStatement($stmt);
	}

	/**
	 * returns the result as an array of <type> instances without executing the passed statement
	 *
	 * @param PDOStatement $stmt
	 * @return <type>[]
	 */
	public static function fromExecutedStatement(PDOStatement $stmt) {
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
	 * @param bool $fullyQualifiedNames true if field names should be qualified by table name
	 * @param bool $prependWhere true if field names should be qualified by table name
	 * @return string
	 */
	public static function buildSqlWhere($filter, $and, $fullyQualifiedNames=true, $prependWhere=false) {
		$sql=null;
		$andString=$and ? ' AND ' : ' OR ';
		$first=true;
		foreach ($filter as $fieldId=>$value) {
			$dfc=$value instanceof DFC;
			$resolvedFieldId=$dfc ? $value->getField() : $fieldId;
			if (!array_key_exists($resolvedFieldId, self::$FIELD_NAMES)) {
				continue;
			}
			if ($first) {
				if ($prependWhere) {
					$sql.=' WHERE ';
				}
				$first=false;
			} else {
				$sql.=$andString;
			}
			if ($fullyQualifiedNames) {
				$sql.=self::SQL_IDENTIFIER_QUOTE . self::SQL_TABLE_NAME . self::SQL_IDENTIFIER_QUOTE . '.';
			}
			$sql.=self::SQL_IDENTIFIER_QUOTE . self::$FIELD_NAMES[$resolvedFieldId] . self::SQL_IDENTIFIER_QUOTE;
			if ($dfc) {
				/* @var $value DFC */
				$sql.=$value->getSqlOperatorPrepared();

			} else {
				$sql.='=?';
			}
		}
		return $sql;
	}

	/**
	 * get sql ORDER BY part from DSCs
	 *
	 * @param array $sort array of DSC instances
	 * @return string
	 */
	protected static function buildSqlOrderBy($sort) {
		if (null===$sort) {
			return '';
		}
		if ($sort instanceof DSC) {
			$sort=array($sort);
		}

		$sql=null;
		$first=true;
		foreach ($sort as $s) {
			/* @var $s DSC */
			if (!array_key_exists($s->getField(), self::$FIELD_NAMES)) {
				continue;
			}
			if ($first) {
				$sql.=' ORDER BY ';
				$first=false;
			} else {
				$sql.=',';
			}
			$sql.=self::SQL_IDENTIFIER_QUOTE . self::$FIELD_NAMES[$s->getField()] . self::SQL_IDENTIFIER_QUOTE . ' ' . $s->getModeSql();
		}
		return $sql;
	}

	/**
	 * bind values from filter to statement
	 *
	 * @param PDOStatement $stmt
	 * @param array $filter
	 */
	protected static function bindValuesForFilter(PDOStatement &$stmt, $filter) {
		$i=0;
		foreach ($filter as $fieldId=>$value) {
			$dfc=$value instanceof DFC;
			$resolvedFieldId=$dfc ? $value->getField() : $fieldId;
			if (($dfc && 0!=(DFC::IS_NULL&$value->getMode())) || !array_key_exists($resolvedFieldId, self::$FIELD_NAMES)) {
				continue;
			}
			$stmt->bindValue(++$i, $dfc ? $value->getSqlValue() : $value);
		}
	}

	/**
	 * Execute select query and return matched rows as an array of <type> instances.
	 *
	 * The query should of course be on the table for this entity class and return all fields.
	 *
	 * @param PDO $db a PDO Database instance
	 * @param string $sql
	 * @return <type>[]
	 */
	public static function findBySql(PDO $db, $sql) {
		$stmt=$db->query($sql);
		return self::fromExecutedStatement($stmt);
	}

	/**
	 * Delete rows matching the filter
	 *
	 * The filter can be either an hash with the field id as index and the value as filter value,
	 * or a array of DFC instances.
	 *
	 * @param PDO $db
	 * @param array $filter
	 * @param bool $and
	 * @return mixed
	 */
	public static function deleteByFilter(PDO $db, $filter, $and=true) {
		if ($filter instanceof DFC) {
			$filter=array($filter);
		}
		if (0==count($filter)) {
			throw new InvalidArgumentException('refusing to delete without filter'); // just comment out this line if you are brave
		}
		$sql='DELETE FROM <tableNameQuoted>'
		. self::buildSqlWhere($filter, $and, false, true);
		$stmt=self::prepareStatement($db, $sql);
		self::bindValuesForFilter($stmt, $filter);
		$affected=$stmt->execute();
		if (false===$affected) {
			$stmt->closeCursor();
			throw new Exception($stmt->errorCode() . ':' . var_export($stmt->errorInfo(), true), 0);
		}
		$stmt->closeCursor();
		return $affected;
	}
