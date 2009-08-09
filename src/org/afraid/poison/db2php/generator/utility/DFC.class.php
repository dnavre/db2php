<?php
/**
* Copyright (c) 2009, Andreas Schnaiter
*
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice,
*    this list of conditions and the following disclaimer.
* 2. Redistributions in binary form must reproduce the above copyright notice,
*    this list of conditions and the following disclaimer in the documentation
*    and/or other materials provided with the distribution.
* 3. Neither the name of  Andreas Schnaiter nor the names
*    of its contributors may be used to endorse or promote products derived
*    from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/

/**
 * single filter element to determine a criteria for matching a field
 */
class DFC {
	/**
	 * match exact value
	 */
	const EXACT=0;
	/**
	 * fields value contains
	 */
	const CONTAINS=1;
	/**
	 * fields value begins with
	 */
	const BEGINS_WITH=2;
	/**
	 * fields value ends with
	 */
	const ENDS_WITH=4;
	/**
	 * fields value is greater than
	 */
	const GREATER=8;
	/**
	 * fields value is smaller than
	 */
	const SMALLER=16;
	/**
	 * fields value matches wildcards ('.' and '*')
	 */
	const WILDCARDS=32;
	/**
	 * fields value is null
	 */
	const IS_NULL=64;
	/**
	 * negate condition.
	 * For example: DFC::NOT|DFC::CONTAINS will match field not containing the value
	 */
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
		$this->field=$field;
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
		$this->value=$value;
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
		$this->mode=$mode;
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
		} elseif (0!=(self::IS_NULL&$mode)) {
			if ($not) {
				return ' IS NOT NULL';
			}
			return ' IS NULL';
		}
		throw new UnexpectedValueException('can not handle mode:' . $mode);
	}

	/**
	 * get SQL operator and value assignment
	 *
	 * @return string
	 */
	public function getSqlOperatorPrepared() {
		if (0!=(self::IS_NULL&$this->getMode())) {
			return $this->getSqlOperator();
		}
		return $this->getSqlOperator() . '?';
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
		} elseif (0!=(self::IS_NULL&$mode)) {
			return null;
		}
		return $this->getValue();
	}

}

?>