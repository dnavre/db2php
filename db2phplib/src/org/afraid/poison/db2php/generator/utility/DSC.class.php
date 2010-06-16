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