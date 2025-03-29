package be.icc.Pid_Reservations_2024.Controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin("http://localhost:63342/")
public class PaymentController {

    @PostMapping("/create-checkout-session")
    public RedirectView createCheckoutSession() throws StripeException {

        String nomShow = "Delta Force";
        long qty = 1;
        long price = (long) 10.00;
        String date = "Le 15 septembre 2025 à 20:35";


        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("EUR")
                                                .setUnitAmount(price * 100)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .addImage("http://bobleponge.fr/photos/humour-bob-eponge/Linux-patrick.jpg")
                                                                .setName(nomShow)
                                                                .setDescription("Date et Heure: " + date)
                                                                .build()
                                                )
                                                .build()
                                ).setQuantity(qty)
                                .build()
                ).build();

        Session session = Session.create(params);

        Map<String, Object> result = new HashMap<>();
        result.put("sessionId", session.getId());

        String url = session.getUrl();

        return new RedirectView(url);
    }

    @GetMapping("/success")
    public String success() {
        return "payment successful";
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "payment cancelled";
    }
}
