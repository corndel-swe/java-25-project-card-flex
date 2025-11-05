package org.project.cardflex;

import io.javalin.Javalin;
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

        app.get("/summary/{cardId}",ctx -> {
            var id = Integer.parseInt(ctx.pathParam("cardId"));
            CardRepository.updateBalance(id);
            var transactions = TransactionsRepository.findById(id);
        });

        app.get("/statement/{cardId}", ctx -> {
           var id = Integer.parseInt((ctx.pathParam("cardId")));
           CardRepository.updateBalance(id);
           var statement = CardRepository.buildCardStatement(id);
           var transactions = TransactionsRepository.findMonthlyById(id);

        });
    }
}