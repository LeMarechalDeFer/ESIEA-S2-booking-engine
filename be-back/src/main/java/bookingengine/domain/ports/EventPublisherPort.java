package bookingengine.domain.ports;

import bookingengine.domain.events.ChambreCreatedEvent;
import bookingengine.domain.events.PaymentCreatedEvent;
import bookingengine.domain.events.PaymentStatusChangedEvent;
import bookingengine.domain.events.PrixCalculatedEvent;
import bookingengine.domain.events.ReservationCancelledEvent;
import bookingengine.domain.events.ReservationCreatedEvent;
import bookingengine.domain.events.SaisonCreatedEvent;

public interface EventPublisherPort {

    void publish(ChambreCreatedEvent event);

    void publish(SaisonCreatedEvent event);

    void publish(PrixCalculatedEvent event);

    void publish(ReservationCreatedEvent event);

    void publish(ReservationCancelledEvent event);

    void publish(PaymentCreatedEvent event);

    void publish(PaymentStatusChangedEvent event);
}
