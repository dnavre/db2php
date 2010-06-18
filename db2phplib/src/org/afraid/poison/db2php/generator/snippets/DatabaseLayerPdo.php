
	/**
	 * cached insert statement
	 *
	 * @var PDOStatement[]
	 */
	private static $stmtInsert=array();
	/**
	 * cached insert statement with autoincrement fields omitted
	 *
	 * @var PDOStatement[]
	 */
	private static $stmtInsertAutoIncrement=array();
	/**
	 * cached update statement
	 *
	 * @var PDOStatement[]
	 */
	private static $stmtUpdate=array();
	/**
	 * cached delete statement
	 *
	 * @var PDOStatement[]
	 */
	private static $stmtDelete=array();
	/**
	 * cached select statement
	 *
	 * @var PDOStatement[]
	 */
	private static $stmtSelect=array();
	private static $cacheStatements=true;
	
	/**
	 * prepare passed string as statement or return cached if enabled and available
	 *
	 * @param PDO $db
	 * @param string $statement
	 * @return PDOStatement
	 */
	protected static function prepareStatement(PDO $db, $statement) {
		if(self::isCacheStatements()) {
			$dbInstanceId=spl_object_hash($db);
			if ($statement==self::SQL_INSERT) {
				if (null==self::$stmtInsert[$dbInstanceId]) {
					self::$stmtInsert[$dbInstanceId]=$db->prepare($statement);
				}
				return self::$stmtInsert[$dbInstanceId];
			} elseif($statement==self::SQL_INSERT_AUTOINCREMENT) {
				if (null==self::$stmtInsertAutoIncrement[$dbInstanceId]) {
					self::$stmtInsertAutoIncrement[$dbInstanceId]=$db->prepare($statement);
				}
				return self::$stmtInsertAutoIncrement[$dbInstanceId];
			} elseif($statement==self::SQL_UPDATE) {
				if (null==self::$stmtUpdate[$dbInstanceId]) {
					self::$stmtUpdate[$dbInstanceId]=$db->prepare($statement);
				}
				return self::$stmtUpdate[$dbInstanceId];
			} elseif($statement==self::SQL_SELECT_PK) {
				if (null==self::$stmtSelect[$dbInstanceId]) {
					self::$stmtSelect[$dbInstanceId]=$db->prepare($statement);
				}
				return self::$stmtSelect[$dbInstanceId];
			} elseif($statement==self::SQL_DELETE_PK) {
				if (null==self::$stmtDelete[$dbInstanceId]) {
					self::$stmtDelete[$dbInstanceId]=$db->prepare($statement);
				}
				return self::$stmtDelete[$dbInstanceId];
			}
		}
		return $db->prepare($statement);
	}

	/**
	 * Enable statement cache
	 *
	 * @param bool $cache
	 */
	public static function setCacheStatements($cache) {
		self::$cacheStatements=true==$cache;
	}

	/**
	 * Check if statement cache is enabled
	 *
	 * @return bool
	 */
	public static function isCacheStatements() {
		return self::$cacheStatements;
	}
