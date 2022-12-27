package com.example.dreamcar.schedulers;

import com.example.dreamcar.models.AuctionStatus;
import com.example.dreamcar.models.CarComponent;
import com.example.dreamcar.models.Offer;
import com.example.dreamcar.repositories.CarComponentRepository;
import com.example.dreamcar.repositories.OfferRepository;
import com.example.dreamcar.service.EmailService;
import com.example.dreamcar.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Configuration
public class TimeoutScheduler {
    @Autowired
    private CarComponentRepository carComponentRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private OfferService offerService;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void checkTimeoutComponents() {
        List<CarComponent> carComponentList = carComponentRepository.findAll();

        for (CarComponent carComponent : carComponentList) {

            if (DAYS.between(carComponent.getAddedDate(), LocalDateTime.now()) <= carComponent.getTimeout()) {
                continue;
            }

            List<Offer> offerList = offerRepository.findByCarComponentName(carComponent.getName());

            if (offerList.size() == 0) {
                continue;
            }

            Offer lowestOffer = offerService.getLowestOffer(offerList);
            emailService.sendEmail(lowestOffer.getUsername(), carComponent);
            lowestOffer.setApplicationStatus(true);
            offerRepository.save(lowestOffer);

            carComponent.setStatus(AuctionStatus.CLOSED_TIMEOUT);
            carComponentRepository.save(carComponent);
        }
    }
}
