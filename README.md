# Card Flex

```

erDiagram
CARDTRANSACTIONS |{ --|{CARDS: cardId
CARDTRANSACTIONS |{ --|{TRANSACTIONS: transactionId
USERSCARDS |{--|{ CARDS : cardId
USERSCARDS |{ --|{USER: userId


USER {
string id PK
string username UK
float totalBalance
}
USERSCARDS {
    string userId FK
    string cardId FK
}
CARDS {
string id PK
string userId FK
integer accountNumber UK
float creditLimit
float balance
float APR
string startDate
string refreshDate
string cardName
}
TRANSACTIONS {
string id PK
integer sendersAccountNumber FK
string sendersCardId FK
string recepientUsername
string receipientAccountNumber
string sendersUsername
float amount
string description
string transactionDate
}
CARDTRANSACTIONS {
    string cardId FK
    string transactionId FK
}
```
