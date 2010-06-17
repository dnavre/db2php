

	/**
	 * get element as DOM Document
	 *
	 * @return DOMDocument
	 */
	public function toDOM() {
		$doc=new DOMDocument();
		$root=$doc->createElement('<type>');
		foreach ($this->toHash() as $fieldName=>$value) {
			$fElem=$doc->createElement($fieldName);
			$fElem->appendChild($doc->createTextNode($value));
			$root->appendChild($fElem);
		}
		$doc->appendChild($root);
		return $doc;
	}

	/**
	 * get single <type> instance from a DOMElement
	 *
	 * @param DOMElement $node
	 * @return <type>
	 */
	public static function fromDOMElement(DOMElement $node) {
		if ('<type>'!=$node->nodeName) {
			throw new InvalidArgumentException('expected: <type>, got: ' . $node->nodeName, 0);
		}
		$result=array();
		foreach (self::$FIELD_NAMES as $fieldName) {
			$n=$node->getElementsByTagName($fieldName)->item(0);
			if (null!==$n) {
				$result[$fieldName]=$n->nodeValue;
			}
		}
		$o=new <type>();
		$o->assignByHash($result);
<pristine>		return $o;
	}

	/**
	 * get all instances of <type> from the passed DOMDocument
	 *
	 * @param DOMDocument $doc
	 * @return <type>[]
	 */
	public static function fromDOMDocument(DOMDocument $doc) {
		$instances=array();
		foreach ($doc->getElementsByTagName('<type>') as $node) {
			$instances[]=self::fromDOMElement($node);
		}
		return $instances;
	}

