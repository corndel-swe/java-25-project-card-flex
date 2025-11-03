--insert example data into users table
INSERT INTO users (id, username, total_balance) VALUES
(1, 'TrustFundBaby', 1000.67),
(2, 'ScroogeMcHuman', 2000.25),
(3, 'FrugalNotCheap', 1500.80),
(4, 'RonaldStump', 800.97),
(5, 'MashFetchum', 980.59);

--insert example data into cards table
INSERT INTO cards (id, user_id, account_number, credit_limit, balance, apr, start_date, refresh_date, card_name) VALUES
(1, 1, 123456, 200.00, 500.00, 10.7, 10, 1, 'Gold'),
(2, 2, 246890, 1000.00, 2000.00, 15.1, 10, 1, 'Platinum'),
(3, 3, 369124, 2500.00, 3000.00, 18.0, 10, 1, 'Diamond');

--insert example data into transactions table
INSERT INTO transactions (id, senders_account_number, senders_username, senders_card_id, recipient_username,
recipient_account_number, amount, description, transaction_date) VALUES
(1, 135790, 'TrustFundBaby', 1, 'ScroogeMcHuman', 2, 380.99, 'Money for Scrooge', 1810),
(2, 098789, 'MashFetchum', 5, 'FrugalNotCheap', 998877, 60.00, 'Buying a Greatball', 1709 ),
(3, 300355, 'RonaldStump', 4, 'TrustFundBaby', 135790, 1000.67, 'Small donation', 0707);

--insert example data into users_cards
INSERT INTO users_cards (user_id, card_id) VALUES
(1, 1),
(2,2),
(3, 3),
(4, 4),
(5, 5);


--insert example data into cards transactions table
INSERT INTO cards_transactions (card_id, transactions_id) VALUES
(1, 1),
(5, 2),
(4, 3);