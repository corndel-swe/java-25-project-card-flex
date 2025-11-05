--insert example data into users table
INSERT INTO users (id, username, total_balance) VALUES
(1, 'TrustFundBaby', 1000.67),
(2, 'ScroogeMcHuman', 2000.25),
(3, 'FrugalNotCheap', 1500.80),
(4, 'RonaldStump', 800.97),
(5, 'MashFetchum', 980.59);

--insert example data into cards table
INSERT INTO cards (id, user_id, account_number, credit_limit, balance, apr, start_date, refresh_date, card_name) VALUES
(1, 1, 123456, 5000.00, 0, 10.7, '10-02-2025', 1, 'GOLD'),
(2, 2, 098789, 15000.00, 0, 15.1, '14-07-2025', 1, 'PLATINUM'),
(3, 3, 300355, 25000.00, 0, 18.0, '25-06-2025', 1, 'BLACK'),
(4, 4, 503935, 15000.00, 0, 15.1, '02-04-2025', 1, 'PLATINUM'),
(5, 5, 484820, 5000.00, 0, 10.7, '14-04-2024', 1, 'GOLD');


--insert example data into transactions table
INSERT INTO transactions (id, senders_account_number, senders_username, senders_card_id, recipient_username,
recipient_account_number, amount, description, transaction_date) VALUES
(1, 123456, 'TrustFundBaby', 1, 'ScroogeMcHuman', 098789, 380.99, 'Money for Scrooge', '18-06-2025'),
(2, 484820, 'MashFetchum', 5, 'FrugalNotCheap', 998877, 60.00, 'Buying a Greatball', '17-08-2025'),
(3, 503935, 'RonaldStump', 4, 'TrustFundBaby', 135790, 1000.67, 'Small donation', '07-07-2025'),
(4, 503935, 'FrugalNotCheap', 3, 'ScroogeMcHuman', 098789, 450, 'haircut', '15-06-2025'),
(5, 098789, 'ScroogeMcHuman', 2, 'MashFetchum', 484820, 50, 'Paint work', '04-03-2025');

--insert example data into users_cards
INSERT INTO users_cards (user_id, card_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);


--insert example data into cards transactions table
INSERT INTO cards_transactions (card_id, transactions_id) VALUES
(1, 1),
(5, 2),
(4, 3),
(3, 4),
(2, 5);