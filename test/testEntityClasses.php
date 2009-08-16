<?php
require_once '../DFC.class.php';
require_once '../DSC.class.php';

$db=new PDO('sqlite:test.db');



require_once 'ProductModel.class.php';
function listProducts($products) {
	foreach ($products as $p) {
		/* @var $p ProductModel */
		echo  $p->getPurchaseCost() . "\t-\t" . $p->getDescription() . "\n";
	}
}

// query by identifier
echo ' -> get product with product id 948933' . "\n";
$product=ProductModel::findById($db, 948933);
echo $product->getDescription() . "\n";

// query by example
echo ' -> get all products with product code HW' . "\n";

// define sorting
$sort=array(
	new DSC(ProductModel::FIELD_DESCRIPTION, DSC::ASC),
	new DSC(ProductModel::FIELD_PURCHASE_COST, DSC::ASC)
);

$example=new ProductModel();
$example->setProductCode('HW');
$products=ProductModel::findByExample($db, $example, true, $sort);
listProducts($products);

// query with filter
echo ' -> get all products with a purchase cost of at least 1000' . "\n";
$filter=new DFC(ProductModel::FIELD_PURCHASE_COST, 1000, DFC::NOT|DFC::SMALLER);
$products=ProductModel::findByFilter($db, $filter, true, $sort);
listProducts($products);

echo ' -> get all products with a purchase cost smallther than 1000 and the description containg Computer' . "\n";
$filter=array(
	new DFC(ProductModel::FIELD_PURCHASE_COST, 1000, DFC::SMALLER),
	new DFC(ProductModel::FIELD_DESCRIPTION, 'Computer', DFC::CONTAINS),
);
$products=ProductModel::findByFilter($db, $filter);
listProducts($products);

// query with SQL
$sql="SELECT * FROM PRODUCT WHERE DESCRIPTION LIKE '%Sound%'";
echo ' -> get by SQL: ' . $sql . "\n";
$products=ProductModel::findBySql($db, $sql);
listProducts($products);

// insert new product
$product=new ProductModel();
$product->setProductId(123)
	->setManufacturerId(123)
	->setProductCode('HW')
	->setPurchaseCost(1234567890)
	->setQuantityOnHand(1)
	->setMarkup(1234567891)
	->setAvailable('TRUE')
	->setDescription('Test Product');
$product->insertIntoDatabase($db);

// update product
$product->setPurchaseCost(100);
$product->setMarkup(120);
$product->setQuantityOnHand(200);
// get changed fields
var_dump($product->getFieldsValuesChanged());
$product->updateToDatabase($db);

// delete product
$product->deleteFromDatabase($db);

// dom functions
echo $product->toDOM()->saveXML();
var_dump(ProductModel::fromDOMDocument($product->toDOM()));

?>