DROP TABLE IF EXISTS customer, car, mechanic, service_request CASCADE;

CREATE TABLE customer (
    id INT PRIMARY KEY,
    fname VARCHAR(255),
    lname VARCHAR(255),
    phone VARCHAR(20),
    address VARCHAR(255)
);

CREATE TABLE car (
    vin VARCHAR(255) PRIMARY KEY,
    make VARCHAR(255),
    model VARCHAR(255),
    customer_id INT,
    year INT,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE mechanic (
    employee_id INT PRIMARY KEY,
    fname VARCHAR(255),
    lname VARCHAR(255),
    years_of_experience INT,
    vin VARCHAR(255),
    FOREIGN KEY (vin) REFERENCES car(vin)
);

CREATE TABLE service_request (
    service_request_id INT PRIMARY KEY,
    service_date DATE,
    problem VARCHAR(255),
    odometer_reading INT,
    is_closed BOOLEAN,
    closed_date DATE,
    comments VARCHAR(255),
    final_bill DECIMAL(10, 2),
    vin VARCHAR(255),
    employee_id INT,
    FOREIGN KEY (vin) REFERENCES car(vin),
    FOREIGN KEY (employee_id) REFERENCES mechanic(employee_id)
);
