-- helps search customers by last name
CREATE INDEX idx_customer_lname
ON customer(lname);

-- helps find cars owned by a customer
CREATE INDEX idx_car_customer_id
ON car(customer_id);

-- helps join service_request to car
CREATE INDEX idx_service_request_vin
ON service_request(vin);

-- helps filter open/closed requests
CREATE INDEX idx_service_request_is_closed
ON service_request(is_closed);

-- helps verify mechanic when closing request
CREATE INDEX idx_service_request_employee
ON service_request(employee_id);
