package payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payments")
public class PaymentsController {
    private final PaymentsRepository paymentsRepository;

    @Autowired
    public PaymentsController(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    @RequestMapping(value = "/{paymentId}")
    ResponseEntity<?> getSinglePayment(@PathVariable UUID paymentId) {
        return this.paymentsRepository.findById(paymentId)
                .map(PaymentResource::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "")
    Resources<PaymentResource> getAllPayments() {
        List<PaymentResource> paymentResources = this.paymentsRepository.findAll()
                .stream()
                .map(PaymentResource::new)
                .collect(Collectors.toList());

        return new Resources<>(paymentResources, linkTo(PaymentsController.class).withSelfRel());
    }
}
