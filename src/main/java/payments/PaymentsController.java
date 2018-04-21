package payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;
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

    @RequestMapping(value = "/{paymentId}", method = RequestMethod.GET)
    ResponseEntity<?> getSinglePayment(@PathVariable UUID paymentId) {
        return this.paymentsRepository.findById(paymentId)
                .map(PaymentResource::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.GET)
    Resources<PaymentResource> getAllPayments() {
        List<PaymentResource> paymentResources = this.paymentsRepository.findAll()
                .stream()
                .map(PaymentResource::new)
                .collect(Collectors.toList());

        return new Resources<>(paymentResources, linkTo(PaymentsController.class).withSelfRel());
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity createNewPayment(@ModelAttribute Payment input) {
        Payment payment = this.paymentsRepository.save(input);
        Link forOnePayment = new PaymentResource(payment).getLink("self");

        return ResponseEntity.created(URI.create(forOnePayment.getHref())).build();
    }
}
