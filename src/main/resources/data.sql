CREATE TABLE IF NOT EXISTS customers (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL
);

INSERT INTO customers (first_name, last_name) VALUES
  ('Mario', 'Rossi'),
  ('Bill', 'Gates'),
  ('Steve', 'Jobs');

CREATE TABLE IF NOT EXISTS orders(
  id INT AUTO_INCREMENT  PRIMARY KEY,
  description VARCHAR(250) NOT NULL,
  customer_id INT,
  foreign key (customer_id) references customers(id)
);