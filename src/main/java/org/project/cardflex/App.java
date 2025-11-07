package org.project.cardflex;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.jetbrains.annotations.NotNull;
import org.project.cardflex.Model.Cards;
import org.project.cardflex.Model.User;
import org.project.cardflex.Repository.CardRepository;
import org.project.cardflex.Repository.TransactionsRepository;
import org.project.cardflex.Repository.UserRepository;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static final String USER_ID = "user-id";
    public static final String ERROR = "error";
    private final Javalin app;

    public static void main(String[] args) {
        Javalin app = new App().javalinApp();
        app.start(8080);
    }

    public Javalin javalinApp() {
        return app;
    }

    public App() {

        List<Map<String, String>> creditCards = getCreditCards();

        app = Javalin.create(
                config -> {
                    // FOR STYLING
                    config.staticFiles.add("src/main/resources/public", Location.EXTERNAL);

//                     config.staticFiles.add("/public", Location.CLASSPATH);

                    var resolver = new ClassLoaderTemplateResolver();
                    resolver.setPrefix("/templates/");
                    resolver.setSuffix(".html");
                    resolver.setTemplateMode("HTML");

                    var engine = new TemplateEngine();
                    engine.setTemplateResolver(resolver);

                    config.fileRenderer(new JavalinThymeleaf(engine));
                });

        app.get("/cardSummary/{cardId}/", ctx -> {
            int cardId = Integer.parseInt(ctx.pathParam("cardId"));
            var transactions = TransactionsRepository.findById(cardId);
            var card = CardRepository.getCardById(cardId);

            var available = card.getCreditLimit() - card.getBalance();
            ctx.render("/cardSummary.html", Map.of("cardId", cardId, "transactions", transactions, "card", card, "available", available));
        });

        app.get("/dashboard", ctx -> {
            Integer id = ctx.sessionAttribute(USER_ID);

            if (id == null) {
                ctx.redirect("/");
                return;
            }

            float totalBalance = UserRepository.findTotalBalance(id);

            List<Cards> ownedCards = UserRepository.getAllCardsByUserId(id);

            // Set of owned CardTypes for filtering available
            Set<String> ownedType = ownedCards.stream().map(Cards::getCardName).collect(Collectors.toSet());

            // Cards that are available to the User to apply for
            List<Map<String, String>> availableCards = creditCards.stream().
                    filter(card -> !ownedType.contains(card.get("name")))
                    .toList();

            ctx.render("/dashboard.html", Map.of("ownedCards", ownedCards,
                    "availableCards", availableCards,
                    "totalBalance", totalBalance));
        });

        app.get("/", ctx -> {
            ctx.sessionAttribute(USER_ID, null);
            String error = ctx.sessionAttribute(ERROR);

            if (error == null) {
                ctx.render("/login.html");
            } else {
                ctx.sessionAttribute(ERROR, null);
                ctx.render("/login.html", Map.of("error", error));
            }

        });

        app.get("/register", ctx -> {
            ctx.render("/register.html");
        });

        app.post("/", ctx -> {
            String username = ctx.formParamAsClass("login", String.class).get();
            User user = UserRepository.checkUsername(username);

            if (user != null) {
                ctx.sessionAttribute(USER_ID, user.getId());
                ctx.redirect("/dashboard");
            } else {
                ctx.sessionAttribute(ERROR, "Invalid username");
                ctx.redirect("/");
            }
        });

        app.post("/apply/{cardName}", ctx -> {
            Integer id = ctx.sessionAttribute(USER_ID);
            String cardName = ctx.pathParam("cardName");

            Set<String> cardTypes = new HashSet<>(List.of("BLACK", "PLATINUM", "GOLD"));

            if (cardTypes.contains(cardName) && id != null) {
                CardRepository.newCreditCard(id, cardName);
            }

            ctx.redirect("/dashboard");
        });


        app.post("/cards/{cardId}/delete", ctx -> {
            Integer id = ctx.sessionAttribute(USER_ID);

            if (id == null) {
                ctx.redirect("/");
                return;
            }

            int cardId = Integer.parseInt(ctx.pathParam("cardId"));
            try {
                CardRepository.deleteCard(cardId);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            ctx.redirect("/dashboard");
        });
        app.get("/test/interest/{cardId}", ctx -> {
            var id = Integer.parseInt(ctx.pathParam("cardId"));
            CardRepository.applyAPR(id);
            ctx.redirect("/dashboard");
            //this exists to demonstrate applying interest
        });
    }

    @NotNull
    private static List<Map<String, String>> getCreditCards() {
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
        return creditCards;
    }


}