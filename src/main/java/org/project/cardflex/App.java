package org.project.cardflex;

import io.javalin.Javalin;
import org.project.cardflex.Repository.TransactionsRepository;

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
        app = Javalin.create(config -> {});

        // Test for transactions
////        app.get(
////                "/transactions/{id}",
////                ctx -> {
////                    var id = Integer.parseInt(ctx.pathParam("id"));
////                    ctx.json(TransactionsRepository.findById(id));
//                }
//        );
    }
}