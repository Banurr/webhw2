CREATE TABLE exchange_rate (
       id            BIGINT          GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
       from_currency CHAR(3)         NOT NULL REFERENCES currency (code),
       to_currency   CHAR(3)         NOT NULL REFERENCES currency (code),
       rate          NUMERIC(15, 2) NOT NULL,
       rate_date     DATE            NOT NULL,
       create_date   TIMESTAMPTZ     NOT NULL DEFAULT now(),
       update_date   TIMESTAMPTZ     NOT NULL DEFAULT now()
);