CREATE TABLE IF NOT EXISTS transactions (
    id SERIAL PRIMARY KEY,
    transaction_type VARCHAR(50) NOT NULL,
    car_id INT NOT NULL,
    client_id INT NOT NULL,
    price_in_cents INT NOT NULL,
    date_time TIMESTAMP NOT NULL DEFAULT now(),

    FOREIGN KEY (car_id) REFERENCES cars(id),
    FOREIGN KEY (client_id) REFERENCES clients(id)
);