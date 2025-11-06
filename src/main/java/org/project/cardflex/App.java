package org.project.cardflex;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.project.cardflex.Model.Cards;
import org.project.cardflex.Model.User;
import org.project.cardflex.Repository.CardRepository;
import org.project.cardflex.Repository.TransactionsRepository;
import org.project.cardflex.Repository.UserRepository;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.*;
import java.util.stream.Collectors;

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
        card1.put("name", "GOLD");
        card1.put("apr", "15.99");
        card1.put("creditLimit", "10000");
        creditCards.add(card1);

        // Card 2
        Map<String, String> card2 = new HashMap<>();
        card2.put("name", "BLACK");
        card2.put("apr", "18.49");
        card2.put("creditLimit", "7500");
        creditCards.add(card2);

        // Card 3
        Map<String, String> card3 = new HashMap<>();
        card3.put("name", "PLATINUM");
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
//            CardRepository.updateBalance(id);
            var transactions = TransactionsRepository.findById(id);
        });

        app.get("{userId}/dashboard", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("userId"));
            // total balance
            float totalBalance = UserRepository.findTotalBalance(id);

            // User owned cards
            List<Cards> ownedCards = UserRepository.getAllCardsByUserId(id);
            Set<String> ownedType = ownedCards.stream().map(Cards::getCardName).collect(Collectors.toSet());

            // User available cards
            List<Map<String, String>> availableCards = creditCards.stream().
                    filter(card -> !ownedType.contains(card.get("name"))).toList();

            System.out.println(ownedCards);

            ctx.render("/dashboard.html", Map.of("ownedCards", ownedCards, "availableCards", availableCards, "totalBalance", totalBalance));
        });

        app.get("/", ctx -> {

            ctx.render("/login.html");
        });

        app.get("/register", ctx -> {
            ctx.render("/register.html");
        });

//       app.post(
//               "/username",
//               ctx -> {
//                   var username = UserRepository.checkUsername("TrustFundBaby");
//                   System.out.println();
//                   ctx.json(username);
//               }
//       );

        app.post("/", ctx -> {
            String username = ctx.formParamAsClass("login", String.class).get();
            User user = UserRepository.checkUsername(username);

            if (user != null) {
                ctx.redirect(String.format("%d/dashboard", user.getId()));
            } else {
                ctx.render("/login.html", Map.of("error", "Invalid username"));
            }
        });

        app.post("/{userId}/dashboard/apply/{cardName}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("userId"));
            // Assigns the card to the User
            String cardName = ctx.pathParam("cardName");
            CardRepository.newCreditCard(id,cardName);
            //Redirecting to Dashboard with new assigned Credit Card Visable
            ctx.redirect(String.format("%d/dashboard",id));

        });
    }


}