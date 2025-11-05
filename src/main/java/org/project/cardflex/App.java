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

        app.get("/{cardId}/summary",ctx -> {
            var id = Integer.parseInt(ctx.pathParam("cardId"));
            var transactions = TransactionsRepository.findById(id);
        });

        app.post("/cards/{user_id}",
                ctx -> {
                    var id = Integer.parseInt(ctx.pathParam("user_id"));
                    CardRepository.newCreditCard(id, "Platinum");
                    ctx.status(200);
                    ctx.result("Card added");
                });

        app.get("/balance/{card_id}",
                ctx -> {
                var id = Integer.parseInt(ctx.pathParam("card_id"));
                CardRepository.checkBalance(id);
                ctx.status(200);
                });


        app.delete("/cards/{card_id}",
                ctx -> {
                    var id = Integer.parseInt(ctx.pathParam("card_id"));
                            CardRepository.deleteCard(id);
                            ctx.status(200);
                            ctx.result("Card deleted");

                });
    }
}