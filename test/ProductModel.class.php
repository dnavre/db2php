<?php

/**
 * 
 *
 * @version 1.2.7 - generated: 8/9/09 6:36 PM
 */
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
	private static $DEFAULT_VALUES=array(
		'PRODUCT_ID'=>0,
		'MANUFACTURER_ID'=>0,
		'PRODUCT_CODE'=>'',
		'PURCHASE_COST'=>null,
		'QUANTITY_ON_HAND'=>null,
		'MARKUP'=>null,
		'AVAILABLE'=>null,
		'DESCRIPTION'=>null);
	private $productId;
	private $manufacturerId;
	private $productCode;
	private $purchaseCost;
	private $quantityOnHand;
	private $markup;
	private $available;
	private $description;

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
	 * return array with the field id as index and the new value as value of values which have been changed since the last notifyPristine call
	 *
	 * @return array
	 */
	public function getFieldsValuesChanged() {
		$changed=array();
		if (!$this->isChanged()) {
			return $changed;
		}
		$old=$this->getOldInstance()->toArray();
		$new=$this->toArray();
		foreach ($old as $fieldId=>$value) {
			if ($new[$fieldId]!==$value) {
				$changed[$fieldId]=$new[$fieldId];
			}
		}
		return $changed;
	}

	/**
	 * return array with the field ids of values which have been changed since the last notifyPristine call
	 *
	 * @return array
	 */
	public function getFieldsChanged() {
		return array_keys($this->getFieldsValuesChanged());
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
	 * type:INTEGER,size:10,default:null,primary,unique
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
	 * type:INTEGER,size:10,default:null,primary,unique
	 *
	 * @return mixed
	 */
	public function getProductId() {
		return $this->productId;
	}

	/**
	 * set value for MANUFACTURER_ID 
	 *
	 * type:INTEGER,size:10,default:null,index
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
	 * type:INTEGER,size:10,default:null,index
	 *
	 * @return mixed
	 */
	public function getManufacturerId() {
		return $this->manufacturerId;
	}

	/**
	 * set value for PRODUCT_CODE 
	 *
	 * type:CHAR,size:2,default:null,index
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
	 * type:CHAR,size:2,default:null,index
	 *
	 * @return mixed
	 */
	public function getProductCode() {
		return $this->productCode;
	}

	/**
	 * set value for PURCHASE_COST 
	 *
	 * type:DECIMAL,size:12,default:null,nullable
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
	 * type:DECIMAL,size:12,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getPurchaseCost() {
		return $this->purchaseCost;
	}

	/**
	 * set value for QUANTITY_ON_HAND 
	 *
	 * type:INTEGER,size:10,default:null,nullable
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
	 * type:INTEGER,size:10,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getQuantityOnHand() {
		return $this->quantityOnHand;
	}

	/**
	 * set value for MARKUP 
	 *
	 * type:DECIMAL,size:4,default:null,nullable
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
	 * type:DECIMAL,size:4,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getMarkup() {
		return $this->markup;
	}

	/**
	 * set value for AVAILABLE 
	 *
	 * type:VARCHAR,size:5,default:null,nullable
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
	 * type:VARCHAR,size:5,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getAvailable() {
		return $this->available;
	}

	/**
	 * set value for DESCRIPTION 
	 *
	 * type:VARCHAR,size:50,default:null,nullable
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
	 * type:VARCHAR,size:50,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getDescription() {
		return $this->description;
	}

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
	 * Assign default values according to table
	 * 
	 */
	public function assignDefaultValues() {
		$this->assignByHash(self::$DEFAULT_VALUES);
	}


	/**
	 * return hash with the field name as index and the field value as value.
	 *
	 * @return array
	 */
	public function toHash() {
		$array=$this->toArray();
		$hash=array();
		foreach ($array as $fieldId=>$value) {
			$hash[self::$FIELD_NAMES[$fieldId]]=$value;
		}
		return $hash;
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
	 * @param ProductModel $example an example instance defining the conditions. All non-null values will be considered a constraint, null values will be ignored.
	 * @param boolean $and true if conditions should be and'ed, false if they should be or'ed
	 * @param array $sort array of DSC instances
	 * @return ProductModel[]
	 */
	public static function findByExample(PDO $db,ProductModel $example, $and=true, $sort=null) {
		$exampleValues=$example->toArray();
		$filter=array();
		foreach ($exampleValues as $fieldId=>$value) {
			if (!is_null($value)) {
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
	 * Will return matched rows as an array of ProductModel instances.
	 *
	 * @param PDO $db a PDO Database instance
	 * @param array $filter array of DFC instances defining the conditions
	 * @param boolean $and true if conditions should be and'ed, false if they should be or'ed
	 * @param array $sort array of DSC instances
	 * @return ProductModel[]
	 */
	public static function findByFilter(PDO $db, $filter, $and=true, $sort=null) {
		if ($filter instanceof DFC) {
			$filter=array($filter);
		}
		$sql='SELECT * FROM PRODUCT'
		. self::getSqlWhere($filter, $and)
		. self::getSqlOrderBy($sort);

		$stmt=self::prepareStatement($db, $sql);
		self::bindValuesForFilter($stmt, $filter);
		return self::fromStatement($stmt);
	}

	/**
	 * Will execute the passed statement and return the result as an array of ProductModel instances
	 *
	 * @param PDOStatement $stmt
	 * @return ProductModel[]
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
	 * returns the result as an array of ProductModel instances without executing the passed statement
	 *
	 * @param PDOStatement $stmt
	 * @return ProductModel[]
	 */
	public static function fromExecutedStatement(PDOStatement $stmt) {
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
	 * get sql ORDER BY part from DSCs
	 *
	 * @param ProductModel $sort
	 * @return string
	 */
	protected static function getSqlOrderBy($sort) {
		if (is_null($sort)) {
			return '';
		}
		if ($sort instanceof DSC) {
			$sort=array($sort);
		}

		$sql=null;
		$first=true;
		foreach ($sort as $s) {
			/* @var $s DSC */
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
		foreach ($filter as $value) {
			$dfc=$value instanceof DFC;
			if ($dfc && 0!=(DFC::IS_NULL&$value->getMode())) {
				continue;
			}
			$stmt->bindValue(++$i, $dfc ? $value->getSqlValue() : $value);
		}
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
		$sql='DELETE FROM PRODUCT'
		. self::getSqlWhere($filter, $and);
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

	/**
	 * Assign values from array with the field id as index and the value as value
	 *
	 * @param array $array
	 */
	public function assignByArray($array) {
		$result=array();
		foreach ($array as $fieldId=>$value) {
			$result[self::$FIELD_NAMES[$fieldId]]=$value;
		}
		$this->assignByHash($result);
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
	public static function findById(PDO $db,$productId) {
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


	/**
	 * get element as DOM Document
	 *
	 * @return DOMDocument
	 */
	public function toDOM() {
		$doc=new DOMDocument();
		$root=$doc->createElement('ProductModel');
		foreach ($this->toHash() as $fieldName=>$value) {
			$fElem=$doc->createElement($fieldName);
			$fElem->appendChild($doc->createTextNode($value));
			$root->appendChild($fElem);
		}
		$doc->appendChild($root);
		return $doc;
	}

	/**
	 * get single ProductModel instance from a DOMElement
	 *
	 * @param DOMElement $node
	 * @return ProductModel
	 */
	public static function fromDOMElement(DOMElement $node) {
		if ('ProductModel'!=$node->nodeName) {
			return new InvalidArgumentException('expected: ProductModel, got: ' . $node->nodeName, 0);
		}
		$result=array();
		foreach (self::$FIELD_NAMES as $fieldName) {
			$n=$node->getElementsByTagName($fieldName)->item(0);
			if (!is_null($n)) {
				$result[$fieldName]=$n->nodeValue;
			}
		}
		$o=new ProductModel();
		$o->assignByHash($result);
			$o->notifyPristine();
		return $o;
	}

	/**
	 * get all instances of ProductModel from the passed DOMDocument
	 *
	 * @param DOMDocument $doc
	 * @return ProductModel[]
	 */
	public static function fromDOMDocument(DOMDocument $doc) {
		$instances=array();
		foreach ($doc->getElementsByTagName('ProductModel') as $node) {
			$instances[]=self::fromDOMElement($node);
		}
		return $instances;
	}

}
?>