
	/**
	 * Fetch <fkType>'s which this <type> references.
	 * `<pkTableName>`.`<pkFieldName>` -> `<fkTableName>`.`<fkFieldName>`
	 *
	 * @param PDO $db a PDO Database instance
	 * @param array $sort array of DSC instances
	 * @return <fkType>[]
	 */
	public function fetch<fkType>Collection(PDO $db, $sort=null) {
		$filter=array(<fkType>::<fkFieldConst>=><pkGetter>);
		return <fkType>::findByFilter($db, $filter, true, $sort);
	}
