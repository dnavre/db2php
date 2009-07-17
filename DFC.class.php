<?php
/**
 * single filter element to determine a criteria for matching a field
 */
class DFC {
	const EXACT=0;
	const CONTAINS=1;
	const BEGINS_WITH=2;
	const ENDS_WITH=4;
	const GREATER=8;
	const SMALLER=16;
	const WILDCARDS=32;
	const NOT=65536;

	/**
	 * fields id
	 *
	 * @var int
	 */
	private $field;
	/**
	 * fields value
	 *
	 * @var string
	 */
	private $value;
	/**
	 * match mode
	 *
	 * @var int
	 */
	private $mode;

	/**
	 * CTOR
	 *
	 * @param int $field
	 * @param string $value
	 * @param int $mode
	 */
	function __construct($field, $value, $mode=0) {
		$this->field=$field;
		$this->value=$value;
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
		$this->field = $field;
	}

	/**
	 * get the fields value
	 *
	 * @return string
	 */
	public function getValue() {
		return $this->value;
	}

	/**
	 * set the fields value
	 *
	 * @param string $value
	 */
	public function setValue($value) {
		$this->value = $value;
	}

	/**
	 * get the match mode
	 *
	 * @return int
	 */
	public function getMode() {
		return $this->mode;
	}

	/**
	 * set the match mode
	 *
	 * @param int $mode
	 */
	public function setMode($mode) {
		$this->mode = $mode;
	}

	/**
	 * get SQL operator
	 *
	 * @return string
	 */
	public function getSqlOperator() {
		$mode=$this->getMode();
		$not=0!=(self::NOT&$mode);
		if (self::EXACT==$mode) {
			return '=';
		} elseif (self::NOT==$mode) {
			return '!=';
		} elseif (0!=(self::GREATER&$mode)) {
			if ($not) {
				return '<=';
			}
			return '>';
		} elseif (0!=(self::SMALLER&$mode)) {
			if ($not) {
				return '>=';
			}
			return '<';
		} elseif (0!=((self::CONTAINS|self::BEGINS_WITH|self::ENDS_WITH|self::WILDCARDS)&$mode)) {
			if ($not) {
				return ' NOT LIKE ';
			}
			return ' LIKE ';
		}
		throw new UnexpectedValueException('can not handle mode:' . $mode);
	}

	/**
	 * get value for use in SQL
	 *
	 * @return string
	 */
	public function getSqlValue() {
		$mode=$this->getMode();
		if (0!=(self::CONTAINS&$mode)) {
			return '%' . $this->getValue() . '%';
		} elseif (0!=(self::BEGINS_WITH&$mode)) {
			return $this->getValue() . '%';
		} elseif (0!=(self::ENDS_WITH&$mode)) {
			return '%' . $this->getValue();
		} elseif (0!=(self::WILDCARDS&$mode)) {
			return str_replace(
				array('.', '*'),
				array('_', '%'),
				$this->getValue());
		}
		return $this->getValue();
	}

}

?>