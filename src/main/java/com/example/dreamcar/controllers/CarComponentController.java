package com.example.dreamcar.controllers;

import com.example.dreamcar.models.AuctionStatus;
import com.example.dreamcar.models.CarComponent;
import com.example.dreamcar.models.Offer;
import com.example.dreamcar.repositories.CarComponentRepository;
import com.example.dreamcar.repositories.OfferRepository;
import com.example.dreamcar.service.EmailService;
import com.example.dreamcar.service.OfferService;
import com.example.dreamcar.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/component-api")
public class CarComponentController {
    @Autowired
    private CarComponentRepository carComponentRepository;
    @Autowired
    private OfferService offerService;
    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private EmailService emailService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getComponent(@RequestParam(name = "name", defaultValue = "") String name) {
        return ResponseEntity.ok(carComponentRepository.findByNameContaining(name));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> addComponent(@RequestParam(name = "name", defaultValue = "") String name,
                                               @RequestParam(name = "description", defaultValue = "") String description,
                                               @RequestParam(name = "timeout", defaultValue = "10") int timeout,
                                               @RequestParam(name = "price", defaultValue = "100") float price,
                                               @RequestParam(name = "quantity", defaultValue = "1") int quantity) {

        if (carComponentRepository.findByName(name).isPresent()) {
            return ResponseEntity.badRequest().body(Utils.composeJSONMessage("Car component already exists"));
        }

        carComponentRepository.save(new CarComponent(name, description, timeout, price, quantity));
        return ResponseEntity.ok(Utils.composeJSONMessage("Success"));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteComponent(@RequestParam(name = "name", defaultValue = "") String name) {
        carComponentRepository.deleteByName(name);
        return ResponseEntity.ok(Utils.composeJSONMessage("Success"));
    }

    @RequestMapping(value = "/offer", method = RequestMethod.GET)
    public ResponseEntity<Object> getOffers(@RequestParam(name = "name", defaultValue = "") String name) {
        return ResponseEntity.ok(offerRepository.findByCarComponentName(name));
    }

    @RequestMapping(value = "/offer", method = RequestMethod.PUT)
    public ResponseEntity<Object> addOffer(@RequestParam(name = "name", defaultValue = "") String name,
                                           @RequestParam(name = "price", defaultValue = "1") float price,
                                           Principal principal) {

        Optional<CarComponent> optionalCarComponent = carComponentRepository.findByName(name);

        if (!optionalCarComponent.isPresent()) {
            return ResponseEntity.badRequest().body(Utils.composeJSONMessage("Car component does not exist"));
        }

        if (principal == null) {
            return ResponseEntity.badRequest().body(Utils.composeJSONMessage("User is not logged in"));
        }

        CarComponent carComponent = optionalCarComponent.get();
        if (carComponent.getStatus() != AuctionStatus.ON_GOING) {
            return ResponseEntity.badRequest().body(Utils.composeJSONMessage("Auction is closed for this product"));
        }

        Optional<Offer> optionalOffer = offerRepository.findByCarComponentNameAndUsername(name, principal.getName());
        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();
            offer.setPrice(price);
            offer.setDateAdded(LocalDateTime.now());
            offerRepository.save(offer);
        } else {
            offerRepository.save(new Offer(name, price, principal.getName()));
        }

        return ResponseEntity.ok(offerService.computeBestOffer(name));
    }

    @RequestMapping(value = "/offer", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteOffer(@RequestParam(name = "name", defaultValue = "") String name,
                                              Principal principal) {

        if (!carComponentRepository.findByName(name).isPresent()) {
            return ResponseEntity.badRequest().body(Utils.composeJSONMessage("Car component does not exist"));
        }

        if (principal == null) {
            return ResponseEntity.badRequest().body(Utils.composeJSONMessage("User is not logged in"));
        }

        offerRepository.deleteByCarComponentNameAndUsername(name, principal.getName());
        return ResponseEntity.ok(offerService.computeBestOffer(name));
    }
}
