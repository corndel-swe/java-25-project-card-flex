--insert example data into users table
INSERT INTO users (id, username, total_balance) VALUES
('A', 'TrustFundBaby', 1000.67),
('B', 'ScroogeMcHuman', 2000.25,
('C', 'FrugalNotCheap', 1500.80),
('D', 'RonaldStump', 800.97),
('E', 'MashFetchum', 980,59);

--insert example data into cards table
INSERT INTO cards (id, userid, account_number, credit_limit, balance, apr, start_date, refresh_date, card_name) VALUES
(),
(),
(),
(),
(),
(),
(),
(),
();

--insert example data into users_cards
INSERT INTO users_cards (userid, card_id) VALUES
(),
(),
();

--insert example data into transactions table
INSERT INTO transactions (id TEXT PRIMARY KEY NOT NULL UNIQUE AUTOINCREMENT,
                              senders_account_number INTEGER FOREIGN KEY NOT NULL UNIQUE,
                              senders_username TEXT NOT NULL,
                              senders_card_id TEXT NOT NULL,
                              recipient_username, recipient_account_number, amount, description, transactionDate)