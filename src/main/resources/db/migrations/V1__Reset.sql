
--delete all tables if they are already there
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS users_cards;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS card_transactions;

--create users table
CREATE TABLE IF NOT EXISTS users(
    id TEXT PRIMARY KEY NOT NULL UNIQUE AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    total_balance FLOAT
);

--create cards table
CREATE TABLE IF NOT EXISTS cards(
    id TEXT PRIMARY KEY NOT NULL UNIQUE AUTOINCREMENT,
    userid TEXT FOREIGN KEY NOT NULL, --does this need to be unique? as it will be same as PK of user
    account_number INTEGER UNIQUE,
    credit_limit FLOAT,
    balance FLOAT,
    apr FLOAT,
    start_date TEXT NOT NULL,
    refresh_date TEXT NOT NULL,
    card_name TEXT NOT NULL,
);

--create user cards table
CREATE TABLE IF NOT EXISTS users_cards(
    user_id TEXT FOREIGN KEY NOT NULL,
    card_id TEXT FOREIGN KEY NOT NULL,
);

--create transactions table
CREATE TABLE IF NOT EXISTS transactions(
    id TEXT PRIMARY KEY NOT NULL UNIQUE AUTOINCREMENT,
    senders_account_number INTEGER FOREIGN KEY NOT NULL UNIQUE,
    senders_username TEXT NOT NULL,
    senders_card_id TEXT NOT NULL,
    recipient_username TEXT NOT NULL, --does this need to be unique? as it will be same as uname of a user
    recipient_account_number NOT NULL,
    amount FLOAT NOT NULL,
    description TEXT NOT NULL,
    transactionDate TEXT NOT NULL,
);

--create cards transactions table
CREATE TABLE IF NOT EXISTS cards_transactions(
    card_id TEXT FOREIGN KEY NOT NULL,
    transaction_id FOREIGN KEY NOT NULL,
);

