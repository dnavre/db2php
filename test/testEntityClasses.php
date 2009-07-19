<?php
require_once '../DFC.class.php';

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
$product=ProductModel::getById($db, 948933);
echo $product->getDescription() . "\n";

// query by example
echo ' -> get all products with product code HW' . "\n";
$example=new ProductModel();
$example->setProductCode('HW');
$products=ProductModel::getByExample($db, $example);
listProducts($products);

// query with filter
echo ' -> get all products with a purchase cost of at least 1000' . "\n";
$filter=new DFC(ProductModel::FIELD_PURCHASE_COST, 1000, DFC::NOT|DFC::SMALLER);
$products=ProductModel::getByFilter($db, $filter);
listProducts($products);

echo ' -> get all products with a purchase smallther than 1000 and the description containg Computer' . "\n";
$filter=array (
	new DFC(ProductModel::FIELD_PURCHASE_COST, 1000, DFC::SMALLER),
	new DFC(ProductModel::FIELD_DESCRIPTION, 'Computer', DFC::CONTAINS)
);
$products=ProductModel::getByFilter($db, $filter);
listProducts($products);

// query with SQL
$sql="SELECT * FROM PRODUCT WHERE DESCRIPTION LIKE '%Sound%'";
echo ' -> get by SQL: ' . $sql . "\n";
$products=ProductModel::getBySql($db, $sql);
listProducts($products);

// insert new product
$product=new ProductModel();
$product->setProductId(123);
$product->setManufacturerId(123);
$product->setProductCode('HW');
$product->setPurchaseCost(1234567890);
$product->setQuantityOnHand(1);
$product->setMarkup(1234567891);
$product->setAvailable('TRUE');
$product->setDescription('Test Product');
$product->insertIntoDatabase($db);

// update product
$product->setPurchaseCost(100);
$product->setMarkup(120);
$product->setQuantityOnHand(200);
$product->updateToDatabase($db);

// delete product
$product->deleteFromDatabase($db);


?>