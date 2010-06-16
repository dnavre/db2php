
	/**
	 * store for old instance after object has been modified
	 *
	 * @var <type>
	 */
	private $oldInstance=null;

	/**
	 * get old instance if this has been modified, otherwise return null
	 *
	 * @return <type>
	 */
	public function getOldInstance() {
		return $this->oldInstance;
	}

	/**
	 * called when the field with the passed id has changed
	 *
	 * @param int $fieldId
	 * @param mixed $oldValue
	 * @param mixed $newValue
	 */
	protected function notifyChanged($fieldId, $oldValue, $newValue) {
		if (null===$this->getOldInstance()) {
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
		return null!==$this->getOldInstance();
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
