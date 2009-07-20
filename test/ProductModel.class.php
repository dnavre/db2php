<?php
class ProductModel {
	const SQL_IDENTIFIER_QUOTE='';
	const SQL_TABLE_NAME='PRODUCT';
	const SQL_INSERT='INSERT INTO PRODUCT (PRODUCT_ID,MANUFACTURER_ID,PRODUCT_CODE,PURCHASE_COST,QUANTITY_ON_HAND,MARKUP,AVAILABLE,DESCRIPTION) VALUES (?,?,?,?,?,?,?,?)';
	const SQL_UPDATE='UPDATE PRODUCT SET PRODUCT_ID=?,MANUFACTURER_ID=?,PRODUCT_CODE=?,PURCHASE_COST=?,QUANTITY_ON_HAND=?,MARKUP=?,AVAILABLE=?,DESCRIPTION=? WHERE PRODUCT_ID=?';
	const SQL_SELECT_PK='SELECT * FROM PRODUCT WHERE PRODUCT_ID=?';
	const SQL_DELETE_PK='DELETE FROM PRODUCT WHERE PRODUCT_ID=?';
	const FIELD_PRODUCT_ID=0;
	const FIELD_MANUFACTURER_ID=1;
	const FIELD_PRODUCT_CODE=2;
	const FIELD_PURCHASE_COST=3;
	const FIELD_QUANTITY_ON_HAND=4;
	const FIELD_MARKUP=5;
	const FIELD_AVAILABLE=6;
	const FIELD_DESCRIPTION=7;
	private static $PRIMARY_KEYS=array(self::FIELD_PRODUCT_ID);
	private static $FIELD_NAMES=array(
		self::FIELD_PRODUCT_ID=>'PRODUCT_ID',
		self::FIELD_MANUFACTURER_ID=>'MANUFACTURER_ID',
		self::FIELD_PRODUCT_CODE=>'PRODUCT_CODE',
		self::FIELD_PURCHASE_COST=>'PURCHASE_COST',
		self::FIELD_QUANTITY_ON_HAND=>'QUANTITY_ON_HAND',
		self::FIELD_MARKUP=>'MARKUP',
		self::FIELD_AVAILABLE=>'AVAILABLE',
		self::FIELD_DESCRIPTION=>'DESCRIPTION');
	private $productId;
	private $manufacturerId;
	private $productCode;
	private $purchaseCost;
	private $quantityOnHand;
	private $markup;
	private $available;
	private $description;

	/**
	 * Get array with field id as index and field name as value
	 *
	 * @return array
	 */
	public static function getFieldNames() {
		return self::$FIELD_NAMES;
	}

	/**
	 * Get array with field ids of identifiers
	 *
	 * @return array
	 */
	public static function getIdentifierFields() {
		return self::$PRIMARY_KEYS;
	}

	/**
	 * store for old instance after object has been modified
	 *
	 * @var ProductModel
	 */
	private $oldInstance=null;

	/**
	 * get old instance if this has been modified, otherwise return null
	 *
	 * @return ProductModel
	 */
	public function getOldInstance() {
		return $this->oldInstance;
	}

	/**
	 * called when the field with the passed id has changed
	 *
	 * @param int $fieldId
	 */
	protected function notifyChanged($fieldId) {
		if (is_null($this->getOldInstance())) {
			$this->oldInstance=clone $this;
			$this->oldInstance->notifyPristine();
		}
	}

	/**
	 * return true if this instance has been modified since the last notifyPristine() call
	 *
	 * @return bool
	 */
	public function isChanged() {
		return !is_null($this->getOldInstance());
	}

	/**
	 * set this instance into pristine state
	 */
	public function notifyPristine() {
		$this->oldInstance=null;
	}

	/**
	 * set value for PRODUCT_ID 
	 *
	 * type:INTEGER,size:10,nullable:false,default:null,primary:true,unique:true,index:false
	 *
	 * @param mixed $productId
	 * @return ProductModel
	 */
	public function &setProductId($productId) {
		$this->notifyChanged(self::FIELD_PRODUCT_ID);
		$this->productId=$productId;
		return $this;
	}

	/**
	 * get value for PRODUCT_ID 
	 *
	 * type:INTEGER,size:10,nullable:false,default:null,primary:true,unique:true,index:false
	 *
	 * @return mixed
	 */
	public function getProductId() {
		return $this->productId;
	}

	/**
	 * set value for MANUFACTURER_ID 
	 *
	 * type:INTEGER,size:10,nullable:false,default:null,primary:false,unique:false,index:true
	 *
	 * @param mixed $manufacturerId
	 * @return ProductModel
	 */
	public function &setManufacturerId($manufacturerId) {
		$this->notifyChanged(self::FIELD_MANUFACTURER_ID);
		$this->manufacturerId=$manufacturerId;
		return $this;
	}

	/**
	 * get value for MANUFACTURER_ID 
	 *
	 * type:INTEGER,size:10,nullable:false,default:null,primary:false,unique:false,index:true
	 *
	 * @return mixed
	 */
	public function getManufacturerId() {
		return $this->manufacturerId;
	}

	/**
	 * set value for PRODUCT_CODE 
	 *
	 * type:CHAR,size:2,nullable:false,default:null,primary:false,unique:false,index:true
	 *
	 * @param mixed $productCode
	 * @return ProductModel
	 */
	public function &setProductCode($productCode) {
		$this->notifyChanged(self::FIELD_PRODUCT_CODE);
		$this->productCode=$productCode;
		return $this;
	}

	/**
	 * get value for PRODUCT_CODE 
	 *
	 * type:CHAR,size:2,nullable:false,default:null,primary:false,unique:false,index:true
	 *
	 * @return mixed
	 */
	public function getProductCode() {
		return $this->productCode;
	}

	/**
	 * set value for PURCHASE_COST 
	 *
	 * type:DECIMAL,size:12,nullable:true,default:null,primary:false,unique:false,index:false
	 *
	 * @param mixed $purchaseCost
	 * @return ProductModel
	 */
	public function &setPurchaseCost($purchaseCost) {
		$this->notifyChanged(self::FIELD_PURCHASE_COST);
		$this->purchaseCost=$purchaseCost;
		return $this;
	}

	/**
	 * get value for PURCHASE_COST 
	 *
	 * type:DECIMAL,size:12,nullable:true,default:null,primary:false,unique:false,index:false
	 *
	 * @return mixed
	 */
	public function getPurchaseCost() {
		return $this->purchaseCost;
	}

	/**
	 * set value for QUANTITY_ON_HAND 
	 *
	 * type:INTEGER,size:10,nullable:true,default:null,primary:false,unique:false,index:false
	 *
	 * @param mixed $quantityOnHand
	 * @return ProductModel
	 */
	public function &setQuantityOnHand($quantityOnHand) {
		$this->notifyChanged(self::FIELD_QUANTITY_ON_HAND);
		$this->quantityOnHand=$quantityOnHand;
		return $this;
	}

	/**
	 * get value for QUANTITY_ON_HAND 
	 *
	 * type:INTEGER,size:10,nullable:true,default:null,primary:false,unique:false,index:false
	 *
	 * @return mixed
	 */
	public function getQuantityOnHand() {
		return $this->quantityOnHand;
	}

	/**
	 * set value for MARKUP 
	 *
	 * type:DECIMAL,size:4,nullable:true,default:null,primary:false,unique:false,index:false
	 *
	 * @param mixed $markup
	 * @return ProductModel
	 */
	public function &setMarkup($markup) {
		$this->notifyChanged(self::FIELD_MARKUP);
		$this->markup=$markup;
		return $this;
	}

	/**
	 * get value for MARKUP 
	 *
	 * type:DECIMAL,size:4,nullable:true,default:null,primary:false,unique:false,index:false
	 *
	 * @return mixed
	 */
	public function getMarkup() {
		return $this->markup;
	}

	/**
	 * set value for AVAILABLE 
	 *
	 * type:VARCHAR,size:5,nullable:true,default:null,primary:false,unique:false,index:false
	 *
	 * @param mixed $available
	 * @return ProductModel
	 */
	public function &setAvailable($available) {
		$this->notifyChanged(self::FIELD_AVAILABLE);
		$this->available=$available;
		return $this;
	}

	/**
	 * get value for AVAILABLE 
	 *
	 * type:VARCHAR,size:5,nullable:true,default:null,primary:false,unique:false,index:false
	 *
	 * @return mixed
	 */
	public function getAvailable() {
		return $this->available;
	}

	/**
	 * set value for DESCRIPTION 
	 *
	 * type:VARCHAR,size:50,nullable:true,default:null,primary:false,unique:false,index:false
	 *
	 * @param mixed $description
	 * @return ProductModel
	 */
	public function &setDescription($description) {
		$this->notifyChanged(self::FIELD_DESCRIPTION);
		$this->description=$description;
		return $this;
	}

	/**
	 * get value for DESCRIPTION 
	 *
	 * type:VARCHAR,size:50,nullable:true,default:null,primary:false,unique:false,index:false
	 *
	 * @return mixed
	 */
	public function getDescription() {
		return $this->description;
	}


	/**
	 * return array with the field id as index and the field value as value.
	 *
	 * @return array
	 */
	public function toArray() {
		return array(
			self::FIELD_PRODUCT_ID=>$this->getProductId(),
			self::FIELD_MANUFACTURER_ID=>$this->getManufacturerId(),
			self::FIELD_PRODUCT_CODE=>$this->getProductCode(),
			self::FIELD_PURCHASE_COST=>$this->getPurchaseCost(),
			self::FIELD_QUANTITY_ON_HAND=>$this->getQuantityOnHand(),
			self::FIELD_MARKUP=>$this->getMarkup(),
			self::FIELD_AVAILABLE=>$this->getAvailable(),
			self::FIELD_DESCRIPTION=>$this->getDescription());
	}


	/**
	 * return array with the field id as index and the field value as value for the identifier fields.
	 *
	 * @return array
	 */
	public function getPrimaryKeyValues() {
		return array(
			self::FIELD_PRODUCT_ID=>$this->getProductId());
	}

	/**
	 * cached insert statement
	 *
	 * @var PDOStatement
	 */
	private static $stmtInsert=null;
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
			} else if($statement==self::SQL_UPDATE) {
				if (null==self::$stmtUpdate) {
					self::$stmtUpdate=$db->prepare($statement);
				}
				return self::$stmtUpdate;
			} else if($statement==self::SQL_SELECT_PK) {
				if (null==self::$stmtSelect) {
					self::$stmtSelect=$db->prepare($statement);
				}
				return self::$stmtSelect;
			} else if($statement==self::SQL_DELETE_PK) {
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

	/**
	 * Query by Example.
	 *
	 * Match by attributes of passed example instance and return matched rows as an array of ProductModel instances
	 *
	 * @param PDO $db a PDO Database instance
	 * @param ProductModel $example an example instance
	 * @return ProductModel[]
	 */
	public static function getByExample(PDO $db,ProductModel $example, $and=true) {
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
	 * Will return matched rows as an array of ProductModel instances.
	 *
	 * @param PDO $db a PDO Database instance
	 * @param array $filter
	 * @param boolean $and
	 * @return ProductModel[]
	 */
	public static function getByFilter(PDO $db, $filter, $and=true) {
		if ($filter instanceof DFC) {
			$filter=array($filter);
		}
		$sql='SELECT * FROM PRODUCT'
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
			$o=new ProductModel();
			$o->assignByHash($result);
			$o->notifyPristine();
			$resultInstances[]=$o;
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
	 * Execute select query and return matched rows as an array of ProductModel instances.
	 *
	 * The query should of course be on the table for this entity class and return all fields.
	 *
	 * @param PDO $db a PDO Database instance
	 * @param string $sql
	 * @return ProductModel[]
	 */
	public static function getBySql(PDO $db, $sql) {
		$stmt=$db->query($sql);
		$resultInstances=array();
		while($result=$stmt->fetch(PDO::FETCH_ASSOC)) {
			$o=new ProductModel();
			$o->assignByHash($result);
			$o->notifyPristine();
			$resultInstances[]=$o;
		}
		$stmt->closeCursor();
		return $resultInstances;
	}


	/**
	 * Assign values from hash where the indexes match the tables field names
	 *
	 * @param array $result
	 */
	public function assignByHash($result) {
		$this->setProductId($result['PRODUCT_ID']);
		$this->setManufacturerId($result['MANUFACTURER_ID']);
		$this->setProductCode($result['PRODUCT_CODE']);
		$this->setPurchaseCost($result['PURCHASE_COST']);
		$this->setQuantityOnHand($result['QUANTITY_ON_HAND']);
		$this->setMarkup($result['MARKUP']);
		$this->setAvailable($result['AVAILABLE']);
		$this->setDescription($result['DESCRIPTION']);
	}

	/**
	 * Get element instance by it's primary key(s).
	 * Will return null if no row was matched.
	 *
	 * @param PDO $db
	 * @return ProductModel
	 */
	public static function getById(PDO $db,$productId) {
		$stmt=self::prepareStatement($db,self::SQL_SELECT_PK);
		$stmt->bindValue(1,$productId);
		$affected=$stmt->execute();
		if (false===$affected) {
			$stmt->closeCursor();
			throw new Exception($stmt->errorCode() . ':' . var_export($stmt->errorInfo(), true), 0);
		}
		$result=$stmt->fetch(PDO::FETCH_ASSOC);
		$stmt->closeCursor();
		if(!$result) {
			return null;
		}
		$o=new ProductModel();
		$o->assignByHash($result);
		$o->notifyPristine();
		return $o;
	}

	/**
	 * Bind all values to statement
	 *
	 * @param PDOStatement $stmt
	 */
	protected function bindValues(PDOStatement &$stmt) {
		$stmt->bindValue(1,$this->getProductId());
		$stmt->bindValue(2,$this->getManufacturerId());
		$stmt->bindValue(3,$this->getProductCode());
		$stmt->bindValue(4,$this->getPurchaseCost());
		$stmt->bindValue(5,$this->getQuantityOnHand());
		$stmt->bindValue(6,$this->getMarkup());
		$stmt->bindValue(7,$this->getAvailable());
		$stmt->bindValue(8,$this->getDescription());
	}


	/**
	 * Insert this instance into the database
	 *
	 * @param PDO $db
	 * @return mixed
	 */
	public function insertIntoDatabase(PDO $db) {
		$stmt=self::prepareStatement($db,self::SQL_INSERT);
		$this->bindValues($stmt);
		$affected=$stmt->execute();
		if (false===$affected) {
			$stmt->closeCursor();
			throw new Exception($stmt->errorCode() . ':' . var_export($stmt->errorInfo(), true), 0);
		}
		$stmt->closeCursor();
		$this->notifyPristine();
		return $affected;
	}


	/**
	 * Update this instance into the database
	 *
	 * @param PDO $db
	 * @return mixed
	 */
	public function updateToDatabase(PDO $db) {
		$stmt=self::prepareStatement($db,self::SQL_UPDATE);
		$this->bindValues($stmt);
		$stmt->bindValue(9,$this->getProductId());
		$affected=$stmt->execute();
		if (false===$affected) {
			$stmt->closeCursor();
			throw new Exception($stmt->errorCode() . ':' . var_export($stmt->errorInfo(), true), 0);
		}
		$stmt->closeCursor();
		$this->notifyPristine();
		return $affected;
	}


	/**
	 * Delete this instance from the database
	 *
	 * @param PDO $db
	 * @return mixed
	 */
	public function deleteFromDatabase(PDO $db) {
		$stmt=self::prepareStatement($db,self::SQL_DELETE_PK);
		$stmt->bindValue(1,$this->getProductId());
		$affected=$stmt->execute();
		if (false===$affected) {
			$stmt->closeCursor();
			throw new Exception($stmt->errorCode() . ':' . var_export($stmt->errorInfo(), true), 0);
		}
		$stmt->closeCursor();
		return $affected;
	}
}
?>