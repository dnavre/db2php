<?php
class CustomerModel {
	const SQL_IDENTIFIER_QUOTE='';
	const SQL_TABLE_NAME='CUSTOMER';
	const SQL_INSERT='INSERT INTO CUSTOMER (CUSTOMER_ID,DISCOUNT_CODE,ZIP,NAME,ADDRESSLINE1,ADDRESSLINE2,CITY,STATE,PHONE,FAX,EMAIL,CREDIT_LIMIT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)';
	const SQL_UPDATE='UPDATE CUSTOMER SET CUSTOMER_ID=?,DISCOUNT_CODE=?,ZIP=?,NAME=?,ADDRESSLINE1=?,ADDRESSLINE2=?,CITY=?,STATE=?,PHONE=?,FAX=?,EMAIL=?,CREDIT_LIMIT=? WHERE CUSTOMER_ID=?';
	const SQL_SELECT_PK='SELECT * FROM CUSTOMER WHERE CUSTOMER_ID=?';
	const SQL_DELETE_PK='DELETE FROM CUSTOMER WHERE CUSTOMER_ID=?';
	const FIELD_CUSTOMER_ID=0;
	const FIELD_DISCOUNT_CODE=1;
	const FIELD_ZIP=2;
	const FIELD_NAME=3;
	const FIELD_ADDRESSLINE1=4;
	const FIELD_ADDRESSLINE2=5;
	const FIELD_CITY=6;
	const FIELD_STATE=7;
	const FIELD_PHONE=8;
	const FIELD_FAX=9;
	const FIELD_EMAIL=10;
	const FIELD_CREDIT_LIMIT=11;
	private static $PRIMARY_KEYS=array(self::FIELD_CUSTOMER_ID);
	private static $FIELD_NAMES=array(
		self::FIELD_CUSTOMER_ID=>'CUSTOMER_ID',
		self::FIELD_DISCOUNT_CODE=>'DISCOUNT_CODE',
		self::FIELD_ZIP=>'ZIP',
		self::FIELD_NAME=>'NAME',
		self::FIELD_ADDRESSLINE1=>'ADDRESSLINE1',
		self::FIELD_ADDRESSLINE2=>'ADDRESSLINE2',
		self::FIELD_CITY=>'CITY',
		self::FIELD_STATE=>'STATE',
		self::FIELD_PHONE=>'PHONE',
		self::FIELD_FAX=>'FAX',
		self::FIELD_EMAIL=>'EMAIL',
		self::FIELD_CREDIT_LIMIT=>'CREDIT_LIMIT');
	private static $DEFAULT_VALUES=array(
		'CUSTOMER_ID'=>0,
		'DISCOUNT_CODE'=>'',
		'ZIP'=>'',
		'NAME'=>null,
		'ADDRESSLINE1'=>null,
		'ADDRESSLINE2'=>null,
		'CITY'=>null,
		'STATE'=>null,
		'PHONE'=>null,
		'FAX'=>null,
		'EMAIL'=>null,
		'CREDIT_LIMIT'=>null);
	private $customerId;
	private $discountCode;
	private $zip;
	private $name;
	private $addressLine1;
	private $addressLine2;
	private $city;
	private $state;
	private $phone;
	private $fax;
	private $email;
	private $creditLimit;

	/**
	 * store for old instance after object has been modified
	 *
	 * @var CustomerModel
	 */
	private $oldInstance=null;

	/**
	 * get old instance if this has been modified, otherwise return null
	 *
	 * @return CustomerModel
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
	 * return array with the field ids of values which have been changed since the last notifyPristine call
	 *
	 * @return array
	 */
	public function getFieldsChanged() {
		$changed=array();
		if (!$this->isChanged()) {
			return $changed;
		}
		$old=$this->getOldInstance()->toArray();
		$new=$this->toArray();
		foreach ($old as $fieldId=>$value) {
			if ($new[$fieldId]!==$value) {
				$changed[]=$fieldId;
			}
		}
		return $changed;
	}

	/**
	 * set this instance into pristine state
	 */
	public function notifyPristine() {
		$this->oldInstance=null;
	}

	/**
	 * set value for CUSTOMER_ID 
	 *
	 * type:INTEGER,size:10,default:null,primary,unique
	 *
	 * @param mixed $customerId
	 * @return CustomerModel
	 */
	public function &setCustomerId($customerId) {
		$this->notifyChanged(self::FIELD_CUSTOMER_ID);
		$this->customerId=$customerId;
		return $this;
	}

	/**
	 * get value for CUSTOMER_ID 
	 *
	 * type:INTEGER,size:10,default:null,primary,unique
	 *
	 * @return mixed
	 */
	public function getCustomerId() {
		return $this->customerId;
	}

	/**
	 * set value for DISCOUNT_CODE 
	 *
	 * type:CHAR,size:1,default:null,index
	 *
	 * @param mixed $discountCode
	 * @return CustomerModel
	 */
	public function &setDiscountCode($discountCode) {
		$this->notifyChanged(self::FIELD_DISCOUNT_CODE);
		$this->discountCode=$discountCode;
		return $this;
	}

	/**
	 * get value for DISCOUNT_CODE 
	 *
	 * type:CHAR,size:1,default:null,index
	 *
	 * @return mixed
	 */
	public function getDiscountCode() {
		return $this->discountCode;
	}

	/**
	 * set value for ZIP 
	 *
	 * type:VARCHAR,size:10,default:null
	 *
	 * @param mixed $zip
	 * @return CustomerModel
	 */
	public function &setZip($zip) {
		$this->notifyChanged(self::FIELD_ZIP);
		$this->zip=$zip;
		return $this;
	}

	/**
	 * get value for ZIP 
	 *
	 * type:VARCHAR,size:10,default:null
	 *
	 * @return mixed
	 */
	public function getZip() {
		return $this->zip;
	}

	/**
	 * set value for NAME 
	 *
	 * type:VARCHAR,size:30,default:null,nullable
	 *
	 * @param mixed $name
	 * @return CustomerModel
	 */
	public function &setName($name) {
		$this->notifyChanged(self::FIELD_NAME);
		$this->name=$name;
		return $this;
	}

	/**
	 * get value for NAME 
	 *
	 * type:VARCHAR,size:30,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getName() {
		return $this->name;
	}

	/**
	 * set value for ADDRESSLINE1 
	 *
	 * type:VARCHAR,size:30,default:null,nullable
	 *
	 * @param mixed $addressLine1
	 * @return CustomerModel
	 */
	public function &setAddressLine1($addressLine1) {
		$this->notifyChanged(self::FIELD_ADDRESSLINE1);
		$this->addressLine1=$addressLine1;
		return $this;
	}

	/**
	 * get value for ADDRESSLINE1 
	 *
	 * type:VARCHAR,size:30,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getAddressLine1() {
		return $this->addressLine1;
	}

	/**
	 * set value for ADDRESSLINE2 
	 *
	 * type:VARCHAR,size:30,default:null,nullable
	 *
	 * @param mixed $addressLine2
	 * @return CustomerModel
	 */
	public function &setAddressLine2($addressLine2) {
		$this->notifyChanged(self::FIELD_ADDRESSLINE2);
		$this->addressLine2=$addressLine2;
		return $this;
	}

	/**
	 * get value for ADDRESSLINE2 
	 *
	 * type:VARCHAR,size:30,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getAddressLine2() {
		return $this->addressLine2;
	}

	/**
	 * set value for CITY 
	 *
	 * type:VARCHAR,size:25,default:null,nullable
	 *
	 * @param mixed $city
	 * @return CustomerModel
	 */
	public function &setCity($city) {
		$this->notifyChanged(self::FIELD_CITY);
		$this->city=$city;
		return $this;
	}

	/**
	 * get value for CITY 
	 *
	 * type:VARCHAR,size:25,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getCity() {
		return $this->city;
	}

	/**
	 * set value for STATE 
	 *
	 * type:CHAR,size:2,default:null,nullable
	 *
	 * @param mixed $state
	 * @return CustomerModel
	 */
	public function &setState($state) {
		$this->notifyChanged(self::FIELD_STATE);
		$this->state=$state;
		return $this;
	}

	/**
	 * get value for STATE 
	 *
	 * type:CHAR,size:2,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getState() {
		return $this->state;
	}

	/**
	 * set value for PHONE 
	 *
	 * type:CHAR,size:12,default:null,nullable
	 *
	 * @param mixed $phone
	 * @return CustomerModel
	 */
	public function &setPhone($phone) {
		$this->notifyChanged(self::FIELD_PHONE);
		$this->phone=$phone;
		return $this;
	}

	/**
	 * get value for PHONE 
	 *
	 * type:CHAR,size:12,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getPhone() {
		return $this->phone;
	}

	/**
	 * set value for FAX 
	 *
	 * type:CHAR,size:12,default:null,nullable
	 *
	 * @param mixed $fax
	 * @return CustomerModel
	 */
	public function &setFax($fax) {
		$this->notifyChanged(self::FIELD_FAX);
		$this->fax=$fax;
		return $this;
	}

	/**
	 * get value for FAX 
	 *
	 * type:CHAR,size:12,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getFax() {
		return $this->fax;
	}

	/**
	 * set value for EMAIL 
	 *
	 * type:VARCHAR,size:40,default:null,nullable
	 *
	 * @param mixed $email
	 * @return CustomerModel
	 */
	public function &setEmail($email) {
		$this->notifyChanged(self::FIELD_EMAIL);
		$this->email=$email;
		return $this;
	}

	/**
	 * get value for EMAIL 
	 *
	 * type:VARCHAR,size:40,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getEmail() {
		return $this->email;
	}

	/**
	 * set value for CREDIT_LIMIT 
	 *
	 * type:INTEGER,size:10,default:null,nullable
	 *
	 * @param mixed $creditLimit
	 * @return CustomerModel
	 */
	public function &setCreditLimit($creditLimit) {
		$this->notifyChanged(self::FIELD_CREDIT_LIMIT);
		$this->creditLimit=$creditLimit;
		return $this;
	}

	/**
	 * get value for CREDIT_LIMIT 
	 *
	 * type:INTEGER,size:10,default:null,nullable
	 *
	 * @return mixed
	 */
	public function getCreditLimit() {
		return $this->creditLimit;
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
			self::FIELD_CUSTOMER_ID=>$this->getCustomerId(),
			self::FIELD_DISCOUNT_CODE=>$this->getDiscountCode(),
			self::FIELD_ZIP=>$this->getZip(),
			self::FIELD_NAME=>$this->getName(),
			self::FIELD_ADDRESSLINE1=>$this->getAddressLine1(),
			self::FIELD_ADDRESSLINE2=>$this->getAddressLine2(),
			self::FIELD_CITY=>$this->getCity(),
			self::FIELD_STATE=>$this->getState(),
			self::FIELD_PHONE=>$this->getPhone(),
			self::FIELD_FAX=>$this->getFax(),
			self::FIELD_EMAIL=>$this->getEmail(),
			self::FIELD_CREDIT_LIMIT=>$this->getCreditLimit());
	}


	/**
	 * return array with the field id as index and the field value as value for the identifier fields.
	 *
	 * @return array
	 */
	public function getPrimaryKeyValues() {
		return array(
			self::FIELD_CUSTOMER_ID=>$this->getCustomerId());
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
	 * Match by attributes of passed example instance and return matched rows as an array of CustomerModel instances
	 *
	 * @param PDO $db a PDO Database instance
	 * @param CustomerModel $example an example instance
	 * @return CustomerModel[]
	 */
	public static function findByExample(PDO $db,CustomerModel $example, $and=true) {
		$exampleValues=$example->toArray();
		$filter=array();
		foreach ($exampleValues as $fieldId=>$value) {
			if (!is_null($value)) {
				$filter[$fieldId]=$value;
			}
		}
		return self::findByFilter($db, $filter, $and);
	}

	/**
	 * Query by filter.
	 *
	 * The filter can be either an hash with the field id as index and the value as filter value,
	 * or a array of DFC instances.
	 *
	 * Will return matched rows as an array of CustomerModel instances.
	 *
	 * @param PDO $db a PDO Database instance
	 * @param array $filter
	 * @param boolean $and
	 * @return CustomerModel[]
	 */
	public static function findByFilter(PDO $db, $filter, $and=true) {
		if ($filter instanceof DFC) {
			$filter=array($filter);
		}
		$sql='SELECT * FROM CUSTOMER'
		. self::getSqlWhere($filter, $and);

		$stmt=self::prepareStatement($db, $sql);
		self::bindValuesForFilter($stmt, $filter);
		$affected=$stmt->execute();
		if (false===$affected) {
			$stmt->closeCursor();
			throw new Exception($stmt->errorCode() . ':' . var_export($stmt->errorInfo(), true), 0);
		}
		$resultInstances=array();
		while($result=$stmt->fetch(PDO::FETCH_ASSOC)) {
			$o=new CustomerModel();
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
	 * bind values from filter to statement
	 *
	 * @param PDOStatement $stmt
	 * @param array $filter
	 */
	protected static function bindValuesForFilter(&$stmt, $filter) {
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
	 * Execute select query and return matched rows as an array of CustomerModel instances.
	 *
	 * The query should of course be on the table for this entity class and return all fields.
	 *
	 * @param PDO $db a PDO Database instance
	 * @param string $sql
	 * @return CustomerModel[]
	 */
	public static function findBySql(PDO $db, $sql) {
		$stmt=$db->query($sql);
		$resultInstances=array();
		while($result=$stmt->fetch(PDO::FETCH_ASSOC)) {
			$o=new CustomerModel();
			$o->assignByHash($result);
			$o->notifyPristine();
			$resultInstances[]=$o;
		}
		$stmt->closeCursor();
		return $resultInstances;
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
		$sql='DELETE FROM CUSTOMER'
		. self::getSqlWhere($filter, $and);
		$stmt=self::prepareStatement($db, $sql);
		self::bindValuesForFilter($stmt, $filter);
		$affected=$stmt->execute();
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
		$this->setCustomerId($result['CUSTOMER_ID']);
		$this->setDiscountCode($result['DISCOUNT_CODE']);
		$this->setZip($result['ZIP']);
		$this->setName($result['NAME']);
		$this->setAddressLine1($result['ADDRESSLINE1']);
		$this->setAddressLine2($result['ADDRESSLINE2']);
		$this->setCity($result['CITY']);
		$this->setState($result['STATE']);
		$this->setPhone($result['PHONE']);
		$this->setFax($result['FAX']);
		$this->setEmail($result['EMAIL']);
		$this->setCreditLimit($result['CREDIT_LIMIT']);
	}

	/**
	 * Get element instance by it's primary key(s).
	 * Will return null if no row was matched.
	 *
	 * @param PDO $db
	 * @return CustomerModel
	 */
	public static function findById(PDO $db,$customerId) {
		$stmt=self::prepareStatement($db,self::SQL_SELECT_PK);
		$stmt->bindValue(1,$customerId);
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
		$o=new CustomerModel();
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
		$stmt->bindValue(1,$this->getCustomerId());
		$stmt->bindValue(2,$this->getDiscountCode());
		$stmt->bindValue(3,$this->getZip());
		$stmt->bindValue(4,$this->getName());
		$stmt->bindValue(5,$this->getAddressLine1());
		$stmt->bindValue(6,$this->getAddressLine2());
		$stmt->bindValue(7,$this->getCity());
		$stmt->bindValue(8,$this->getState());
		$stmt->bindValue(9,$this->getPhone());
		$stmt->bindValue(10,$this->getFax());
		$stmt->bindValue(11,$this->getEmail());
		$stmt->bindValue(12,$this->getCreditLimit());
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
		$stmt->bindValue(13,$this->getCustomerId());
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
		$stmt->bindValue(1,$this->getCustomerId());
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
		$root=$doc->createElement(__CLASS__);
		foreach ($this->toHash() as $fieldName=>$value) {
			$fElem=$doc->createElement($fieldName);
			$fElem->appendChild($doc->createTextNode($value));
			$root->appendChild($fElem);
		}
		$doc->appendChild($root);
		return $doc;
	}

	/**
	 * get single CustomerModel instance from a DOMElement
	 *
	 * @param DOMElement $node
	 * @return CustomerModel
	 */
	public static function fromDOMElement(DOMElement $node) {
		if (__CLASS__!=$node->nodeName) {
			return new InvalidArgumentException('expected: CustomerModel, got: ' . $node->nodeName, 0);
		}
		$result=array();
		foreach (self::$FIELD_NAMES as $fieldName) {
			$n=$node->getElementsByTagName($fieldName)->item(0);
			if (!is_null($n)) {
				$result[$fieldName]=$n->nodeValue;
			}
		}
		$o=new CustomerModel();
		$o->assignByHash($result);
			$o->notifyPristine();
		return $o;
	}

	/**
	 * get all instances of CustomerModel from the passed DOMDocument
	 *
	 * @param DOMDocument $doc
	 * @return CustomerModel[]
	 */
	public static function fromDOMDocument(DOMDocument $doc) {
		$instances=array();
		foreach ($doc->getElementsByTagName('CustomerModel') as $node) {
			$instances[]=self::fromDOMElement($node);
		}
		return $instances;
	}

}
?>