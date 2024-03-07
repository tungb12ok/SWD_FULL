-- Switching context or selecting a database in MySQL
USE `ShoppingCart`;
-- MySQL doesn't have a direct equivalent for `GO`, so it's omitted.

-- Creating the Categories table
CREATE TABLE `Categories` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Creating the product table
CREATE TABLE `product` (
	`pid` INT NOT NULL AUTO_INCREMENT,
	`pname` VARCHAR(100) NOT NULL,
	`pinfo` VARCHAR(350) DEFAULT NULL,
	`pprice` DECIMAL(12, 2) DEFAULT NULL,
	`pquantity` INT DEFAULT NULL,
	`image` VARCHAR(500) DEFAULT NULL,
	`status` VARCHAR(50) DEFAULT NULL,
	`sale` DECIMAL(2, 2) DEFAULT NULL,
	`cateId` INT DEFAULT NULL,
	PRIMARY KEY (`pid`),
	CONSTRAINT `FK_product_Categories` FOREIGN KEY (`cateId`) REFERENCES `Categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Creating the user table
CREATE TABLE `user` (
	`userId` INT NOT NULL AUTO_INCREMENT,
	`email` VARCHAR(60) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`mobile` BIGINT NOT NULL,
	`address` VARCHAR(250) NOT NULL,
	`pincode` INT NOT NULL,
	`password` VARCHAR(20) NOT NULL,
	`status` VARCHAR(20) NOT NULL,
	`role` INT NOT NULL,
	PRIMARY KEY (`userId`),
	UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- It seems there was a typo with the creation of a second categories table named "categries". Assuming it was unintended, I'll skip recreating it. If it was intended, you would follow a similar pattern as above but with the correct table name.

-- Creating the FeedBacks table
CREATE TABLE `FeedBacks` (
	`id` INT NOT NULL,
	`pId` INT NOT NULL,
	`userId` INT NOT NULL,
	`description` VARCHAR(2000) NOT NULL,
	`rate` INT NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_FeedBacks_product` FOREIGN KEY (`pId`) REFERENCES `product` (`pid`),
	CONSTRAINT `FK_FeedBacks_user` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Creating the orders table
CREATE TABLE `orders` (
	`orderid` VARCHAR(45) NOT NULL,
	`time` DATETIME NOT NULL,
	`email` VARCHAR(50) DEFAULT NULL,
	`updateTime` DATETIME DEFAULT NULL,
	`updateBy` INT DEFAULT NULL,
	`userId` INT DEFAULT NULL,
	`amount` DECIMAL(10, 2) DEFAULT NULL,
	`status` VARCHAR(50) DEFAULT NULL,
	`mobile` VARCHAR(10) DEFAULT NULL,
	`address` VARCHAR(200) DEFAULT NULL,
	`payment` VARCHAR(50) DEFAULT NULL,
	PRIMARY KEY (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Creating the orderDetail table
CREATE TABLE `orderDetail` (
	`odId` VARCHAR(50) NOT NULL,
	`orderId` VARCHAR(45) DEFAULT NULL,
	`productId` INT DEFAULT NULL,
	`quantity` INT DEFAULT NULL,
	`amount` DECIMAL(10, 2) DEFAULT NULL,
	PRIMARY KEY (`odId`),
	CONSTRAINT `FK_orderDetail_orders` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderid`),
	CONSTRAINT `FK_orderDetail_product1` FOREIGN KEY (`productId`) REFERENCES `product` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `orders` 
ADD CONSTRAINT `fk_userId`
FOREIGN KEY (`userId`) 
REFERENCES `user`(`userId`)
ON DELETE SET NULL
ON UPDATE CASCADE;
-- Inserting data into Categories table
INSERT INTO Categories (name) VALUES
('Electronics'),
('Clothing'),
('Books');

-- Inserting data into product table
INSERT INTO product (pname, pinfo, pprice, pquantity, image, status, sale, cateId) VALUES
('Laptop', 'A powerful laptop for all your computing needs', 999.99, 50, 'laptop.jpg', 'Active', 0.0, 1),
('T-Shirt', 'A comfortable cotton t-shirt', 19.99, 100, 'tshirt.jpg', 'Active', 0.0, 2),
('Java Programming Book', 'Learn Java programming from scratch', 29.99, 30, 'javabook.jpg', 'Active', 0.0, 3);

-- Inserting data into user table
INSERT INTO user (email, name, mobile, address, pincode, password, status, role) VALUES
('user1@example.com', 'User One', 1234567890, '123 Main St, City, Country', 12345, 'password123', 'Active', 1),
('user2@example.com', 'User Two', 9876543210, '456 Elm St, City, Country', 54321, 'password456', 'Active', 2);

-- Inserting data into FeedBacks table

-- Inserting data into orders table
INSERT INTO orders (orderid, time, email, updateTime, updateBy, userId, amount, status, mobile, address, payment) VALUES
('ORD123456', '2024-03-07 10:00:00', 'user1@example.com', '2024-03-07 10:05:00', 1, 1, 999.99, 'Completed', '1234567890', '123 Main St, City, Country', 'Credit Card'),
('ORD789012', '2024-03-08 09:00:00', 'user2@example.com', '2024-03-08 09:10:00', 2, 2, 49.98, 'Pending', '9876543210', '456 Elm St, City, Country', 'PayPal');

-- Inserting data into orderDetail table
INSERT INTO orderDetail (odId, orderId, productId, quantity, amount) VALUES
('OD12345601', 'ORD123456', 1, 1, 999.99),
('OD12345602', 'ORD123456', 2, 2, 39.98),
('OD78901201', 'ORD789012', 3, 1, 29.99);

INSERT INTO FeedBacks (pId, userId, description, rate) VALUES
(1, 1, 'Great laptop, fast delivery', 5),
(2, 1, 'Nice t-shirt, fits well', 4),
(3, 2, 'Excellent book, very informative', 5);

