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

        app.get("/users/cards/balance/{balance}",
                ctx -> {
                    var balance = Integer.parseInt(ctx.pathParam("balance"));
                    ctx.status(200);
                });

        app.delete("/users/cards/{card_id}",
                ctx -> {
                    var id = Integer.parseInt(ctx.pathParam("card_id");
                            CardRepository.deleteCard(id,userId);
                    )
                });
    }
}