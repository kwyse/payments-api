package payments;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.hateoas.ResourceSupport;

public class PaymentResource extends ResourceSupport {
    private final Payment payment;

    public PaymentResource(Payment payment) {
        this.payment = payment;
        this.add(linkTo(PaymentsController.class).withSelfRel());
    }

    public Payment getPayment() {
        return payment;
    }
}
