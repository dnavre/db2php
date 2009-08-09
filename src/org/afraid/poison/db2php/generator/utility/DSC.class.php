<?php

/**
 * Data Sort Criteria
 *
 * @author poison
 */
class DSC {
	/**
	 * sort ascending
	 */
	const ASC=0;
	/**
	 * sort descending
	 */
	const DESC=1;
	/**
	 * sort modes to string
	 *
	 * @var array
	 */
	private static $SORT_MODES=array(
		self::ASC=>'ASC',
		self::DESC=>'DESC'
	);
	/**
	 * fields id
	 *
	 * @var int
	 */
	private $field;
	/**
	 * sort mode
	 *
	 * @var int
	 */
	private $mode;

	/**
	 * CTOR
	 *
	 * @param int $field
	 * @param int $mode
	 */
	public function __construct($field, $mode=0) {
		$this->field=$field;
		$this->mode=$mode;
	}

	/**
	 * get the fields id
	 *
	 * @return int
	 */
	public function getField() {
		return $this->field;
	}

	/**
	 * set the fields id
	 *
	 * @param int $field
	 */
	public function setField($field) {
		$this->field=$field;
	}

	/**
	 * get the sort mode
	 *
	 * @return int
	 */
	public function getMode() {
		return $this->mode;
	}

	/**
	 * set the sort mode
	 *
	 * @param int $mode
	 */
	public function setMode($mode) {
		$this->mode=$mode;
	}

	/**
	 * get sort mode as string for use in SQL
	 *
	 * @return string
	 */
	public function getModeSql() {
		return self::$SORT_MODES[$this->getMode()];
	}
}
?>