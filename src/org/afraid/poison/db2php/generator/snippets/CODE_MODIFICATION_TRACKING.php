
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
	 */
	protected function notifyChanged($fieldId) {
		if (is_null($this->getOldInstance())) {
			$this->oldInstance=$this;
		}
	}

	/**
	 * return true if this instance has been modified since the last setPristine() call
	 *
	 * @return bool
	 */
	public function isChanged() {
		return !is_null($this->getOldInstance());
	}

	/**
	 * set this instance into pristine state
	 */
	public function setPristine() {
		$this->oldInstance=null;
	}
