\copy customer FROM 'customer.csv' DELIMITER ',' CSV HEADER;
\copy car FROM 'car.csv' DELIMITER ',' CSV HEADER;
\copy mechanic FROM 'mechanic.csv' DELIMITER ',' CSV HEADER;
\copy service_request FROM 'service_request.csv' DELIMITER ',' CSV HEADER;