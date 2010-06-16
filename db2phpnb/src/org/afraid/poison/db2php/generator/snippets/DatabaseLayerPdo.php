
	/**
	 * cached insert statement
	 *
	 * @var PDOStatement
	 */
	private static $stmtInsert=null;
	/**
	 * cached insert statement with autoincrement fields omitted
	 *
	 * @var PDOStatement
	 */
	private static $stmtInsertAutoIncrement=null;
	/**
	 * cached update statement
	 *
	 * @var PDOStatement
	 */
	private static $stmtUpdate=null;
	/**
	 * cached delete statement
	 *
	 * @var PDOStatement
	 */
	private static $stmtDelete=null;
	/**
	 * cached select statement
	 *
	 * @var PDOStatement
	 */
	private static $stmtSelect=null;
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
			if ($statement==self::SQL_INSERT) {
				if (null==self::$stmtInsert) {
					self::$stmtInsert=$db->prepare($statement);
				}
				return self::$stmtInsert;
			} elseif($statement==self::SQL_INSERT_AUTOINCREMENT) {
				if (null==self::$stmtInsertAutoIncrement) {
					self::$stmtInsertAutoIncrement=$db->prepare($statement);
				}
				return self::$stmtInsertAutoIncrement;
			} elseif($statement==self::SQL_UPDATE) {
				if (null==self::$stmtUpdate) {
					self::$stmtUpdate=$db->prepare($statement);
				}
				return self::$stmtUpdate;
			} elseif($statement==self::SQL_SELECT_PK) {
				if (null==self::$stmtSelect) {
					self::$stmtSelect=$db->prepare($statement);
				}
				return self::$stmtSelect;
			} elseif($statement==self::SQL_DELETE_PK) {
				if (null==self::$stmtDelete) {
					self::$stmtDelete=$db->prepare($statement);
				}
				return self::$stmtDelete;
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
