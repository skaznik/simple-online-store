package pl.edu.wszib.simpleonlinestore.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.edu.wszib.simpleonlinestore.model.ShoppingCart;
import pl.edu.wszib.simpleonlinestore.model.ShoppingCartItem;
import pl.edu.wszib.simpleonlinestore.utils.ShoppingcartCalculator;

@Service
public class ShoppingCartSubmitService {

    public JavaMailSender emailSender;

    public ShoppingCartSubmitService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void submit(ShoppingCart shoppingCart, String email) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Twój koszyk został zamówiony");
        message.setText(toMessage(shoppingCart));

        emailSender.send(message);
    }

    private String toMessage(ShoppingCart shoppingCart) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("Twój koszyk zawiera : ")
                .append(shoppingCart.getId()).append("\n");

        for(ShoppingCartItem item : shoppingCart.getItems()) {
            stringBuilder
                    .append("Produkt : ")
                    .append(item.getProduct().getName())
                    .append(", Cena : ")
                    .append(item.getProduct().getPrice())
                    .append(", Ilość : ")
                    .append(item.getAmount())
                    .append("\n");
        }

        stringBuilder.append("Lącznie : ")
                .append(ShoppingcartCalculator.calculateTotal(shoppingCart));

        return stringBuilder.toString();
    }
}
