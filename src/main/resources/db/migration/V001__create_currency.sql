CREATE TABLE currency (
      code CHAR(3) PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      is_active BOOLEAN NOT NULL DEFAULT TRUE,
      create_date TIMESTAMPTZ NOT NULL DEFAULT now(),
      update_date TIMESTAMPTZ NOT NULL DEFAULT now()
);