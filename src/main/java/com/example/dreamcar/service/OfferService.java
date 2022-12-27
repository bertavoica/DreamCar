package com.example.dreamcar.service;

import com.example.dreamcar.models.AuctionStatus;
import com.example.dreamcar.models.CarComponent;
import com.example.dreamcar.models.Offer;
import com.example.dreamcar.repositories.CarComponentRepository;
import com.example.dreamcar.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private CarComponentRepository carComponentRepository;

    @Autowired
    private EmailService emailService;

    public Offer getLowestOffer(List<Offer> offers) {
        offers.sort((o1, o2) -> -Float.compare(o2.getPrice(), o1.getPrice()));
        return offers.get(0);
    }

    public List<Offer> computeBestOffer(String name) {
        Optional<CarComponent> carComponentOptional = carComponentRepository.findByName(name);

        if (!carComponentOptional.isPresent()) {
            return Collections.emptyList();
        }

        CarComponent carComponent = carComponentOptional.get();

        List<Offer> offers = offerRepository.findByCarComponentName(name);
        if (offers.size() == 0) {
            return Collections.emptyList();
        }

        float lowestPrice = getLowestOffer(offers).getPrice();
        carComponent.setStatus(AuctionStatus.ON_GOING);

        offers.forEach(e -> {
            if (e.getPrice() == lowestPrice && e.getPrice() <= carComponent.getPrice()) {
                e.setApplicationStatus(true);
                carComponent.setStatus(AuctionStatus.CLOSED_TARGET_PRICE);
                emailService.sendEmail(e.getUsername(), carComponent);
            }

            offerRepository.save(e);
        });

        carComponentRepository.save(carComponent);
        return offers;
    }
}
