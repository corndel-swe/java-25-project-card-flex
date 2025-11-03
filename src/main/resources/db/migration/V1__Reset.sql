--remember add a .env file in root folder! inside put DB_URL=jdbc:sqlite:card-flex.db & in git ignore file add .env

--delete all tables if they are already there
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS users_cards;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS card_transactions;

--create users table
CREATE TABLE IF NOT EXISTS users(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE,
    total_balance FLOAT
);

--create cards table
CREATE TABLE IF NOT EXISTS cards(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    account_number INTEGER UNIQUE,
    credit_limit FLOAT,
    balance FLOAT,
    apr FLOAT,
    start_date TEXT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    refresh_date INTEGER NOT NULL,
    card_name TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

--create transactions table
CREATE TABLE IF NOT EXISTS transactions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    senders_card_id INTEGER NOT NULL,
    senders_username TEXT NOT NULL,
    senders_account_number INTEGER NOT NULL,
    recipient_username TEXT NOT NULL,
    recipient_account_number INTEGER NOT NULL,
    amount FLOAT NOT NULL,
    description TEXT NOT NULL,
    transaction_date TEXT NOT NULL,
    FOREIGN KEY (senders_card_id) REFERENCES cards (id)
);

--create user cards table
CREATE TABLE IF NOT EXISTS users_cards(
    user_id INTEGER NOT NULL,
    card_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (card_id) REFERENCES cards (id) ON DELETE CASCADE
);

--create cards transactions table
CREATE TABLE IF NOT EXISTS cards_transactions(
card_id INTEGER NOT NULL,
transactions_id INTEGER NOT NULL,
FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE,
FOREIGN KEY (transactions_id) REFERENCES transactions (id) ON DELETE CASCADE
);

