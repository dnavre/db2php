
	/**
	 * get elements by example
	 *
	 * @param PDO $db
	 * @param <type> $example
	 * @return <type>[]
	 */
	public static function getByExample(PDO $db,<type> $example, $conjunctive=true) {
		$instanceValues=$example->toArray();
		$filter=array();
		foreach ($instanceValues as $fieldId=>$value) {
			if (!is_null($value)) {
				$filter[$fieldId]=$value;
			}
		}
		return self::getByFilter($db, $filter, $conjunctive);
	}

	/**
	 * get elements by filter. The filter is an hash with the field id as index and the value as filter value
	 *
	 * @param PDO $db
	 * @param array $filter
	 * @param boolean $conjunctive
	 * @return <type>[]
	 */
	public static function getByFilter(PDO $db, $filter, $conjunctive=true) {
		$sql='SELECT * FROM `tbltarifeoffline`';
		$first=true;
		$usedValues=array();
		foreach ($filter as $fieldId=>$value) {
			if ($first) {
				$sql.=' WHERE ';
				$first=false;
			} else {
				$sql.=$conjunctive ? ' AND ' : ' OR ';
			}
			$sql.='`' . self::$FIELD_NAMES[$fieldId] . '`' . '=?';
			$usedValues[]=$value;
		}
		$stmt=self::prepareStatement($db, $sql);
		$ecnt=count($usedValues);
		for ($i=0; $i<$ecnt; ++$i) {
			$stmt->bindValue(1+$i, $usedValues[$i]);
		}
		$affected=$stmt->execute();
		$resultInstances=array();
		while($result=$stmt->fetch(PDO::FETCH_ASSOC)) {
			$o=new <type>();
			$o->assignByHash($result);
			$o->notifyPristine();
			$resultInstances[]=$o;
		}
		$stmt->closeCursor();
		return $resultInstances;
	}
