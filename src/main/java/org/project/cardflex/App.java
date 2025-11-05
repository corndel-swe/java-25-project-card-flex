package org.project.cardflex;

import io.javalin.Javalin;
import org.project.cardflex.Model.Transactions;
import org.project.cardflex.Repository.CardRepository;
import org.project.cardflex.Repository.TransactionsRepository;

import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.path;

public class App {
    private final Javalin app;
    public static void main(String[] args) {
        Javalin app = new App().javalinApp();
        app.start(8080);}

    public Javalin javalinApp() {
        return app;
    }

    public App() {
        app = Javalin.create(
                config -> {

                });

        app.get("/{cardId}/summary",ctx -> {
            var id = Integer.parseInt(ctx.pathParam("cardId"));
            var transactions = TransactionsRepository.findById(id);
        });

        app.post("/transactiontest",ctx -> {
            TransactionsRepository.addTransaction(new Transactions(1, 1,"Cardflex",  123456,"user", 125, "Test transaction", "01-01-0000"));


        });

    }
}