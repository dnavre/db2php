
	/**
	 * get object as string
	 *
	 * @return string
	 */
	public function __toString() {
		$s=null;
		foreach ($this->toHash() as $fieldName=>$value) {
			$s.=$fieldName . ": " . $value . "\n";
		}
		return $s;
	}
