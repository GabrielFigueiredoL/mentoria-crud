CREATE TABLE IF NOT EXISTS cars (
    id SERIAL PRIMARY KEY,
    model VARCHAR(100) NOT NULL,
    model_year INT NOT NULL,
    manufacture_year INT NOT NULL,
    km INT NOT NULL,
    price_in_cents INT NOT NULL,
    color VARCHAR(50) NOT NULL,
    chassis VARCHAR(50) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    transmission_type VARCHAR(50) NOT NULL
);
