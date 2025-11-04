package org.project.cardflex;

import io.javalin.Javalin;
import org.project.cardflex.Repository.CardRepository;
import org.project.cardflex.Repository.TransactionsRepository;

import javax.smartcardio.Card;
import java.io.InputStream;
import java.util.List;

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
        app = Javalin.create(config -> {

        });

        app.get(
                "/cards/{userId}",
                ctx -> {
                    var id = Integer.parseInt(ctx.pathParam("userId"));
                    var cards = CardRepository.findCardsByUserID(id);
                    ctx.json(cards);
                }
        );

        app.post(
                "/cards/{cardId}",
                ctx ->{
                    var id = Integer.parseInt(ctx.pathParam("cardId"));
                    CardRepository.newCreditCard(id, "GOLD");
                }
        );


    }
}