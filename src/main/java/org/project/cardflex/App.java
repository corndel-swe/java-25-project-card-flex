package org.project.cardflex;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.project.cardflex.Repository.CardRepository;
import org.project.cardflex.Repository.TransactionsRepository;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import static io.javalin.apibuilder.ApiBuilder.path;

public class App {
    private final Javalin app;

    public static void main(String[] args) {
        Javalin app = new App().javalinApp();
        app.start(8080);
    }

    public Javalin javalinApp() {
        return app;
    }

    public App() {


        List<Map<String, String>> creditCards = new ArrayList<>();

        // Card 1
        Map<String, String> card1 = new HashMap<>();
        card1.put("name", "Gold card");
        card1.put("apr", "15.99");
        card1.put("creditLimit", "10000");
        creditCards.add(card1);

        // Card 2
        Map<String, String> card2 = new HashMap<>();
        card2.put("name", "Black card");
        card2.put("apr", "18.49");
        card2.put("creditLimit", "7500");
        creditCards.add(card2);

        // Card 3
        Map<String, String> card3 = new HashMap<>();
        card3.put("name", "Platinum Card");
        card3.put("apr", "20.99");
        card3.put("creditLimit", "15000");
        creditCards.add(card3);

        // Example: sending to frontend or printing
        System.out.println(creditCards);// Map<String, String> testData = new HashMap<String, String>();
        // testData.put("name", "blue");

        app = Javalin.create(
                config -> {
                    // config.staticFiles.add("/public", Location.CLASSPATH);

                    var resolver = new ClassLoaderTemplateResolver();
                    resolver.setPrefix("/templates/");
                    resolver.setSuffix(".html");
                    resolver.setTemplateMode("HTML");

                    var engine = new TemplateEngine();
                    engine.setTemplateResolver(resolver);

                    config.fileRenderer(new JavalinThymeleaf(engine));
                });

        app.get("/{cardId}/summary", ctx -> {
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