package nl.tudelft.sem.template.reservations.controllers;

import nl.tudelft.sem.template.reservations.entities.Reservation;
import nl.tudelft.sem.template.reservations.exceptions.ReservationInputException;
import nl.tudelft.sem.template.reservations.exceptions.ReservationServerException;
import nl.tudelft.sem.template.reservations.factory.EquipmentReservationFactory;
import nl.tudelft.sem.template.reservations.factory.FieldReservationFactory;
import nl.tudelft.sem.template.reservations.factory.ReservationFactory;
import nl.tudelft.sem.template.reservations.services.ReservationService;
import nl.tudelft.sem.template.reservations.services.ServiceCommunication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * The type Reservation controller.
 */
@RestController
@RequestMapping(path = "/")
public class ReservationController {
    private final transient ReservationService reservationService;
    private final transient ServiceCommunication serviceCommunication;

    @Autowired
    private transient RestTemplate restTemplate;

    /**
     * Instantiates a new Reservation controller.
     *
     * @param reservationService the reservation service
     */
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
        this.serviceCommunication = new ServiceCommunication(restTemplate);
    }

    /**
     * Gets all field reservations.
     *
     * @return list of field reservations.
     */
    @GetMapping("getFields")
    public ResponseEntity<?> getFieldRes() {
        return new ResponseEntity<>(reservationService.getFields(), HttpStatus.OK);
    }

    /**
     * Gets all equipment reservations.
     *
     * @return list of equipment reservations.
     */
    @GetMapping("getEquipment")
    public ResponseEntity<?> getEquipmentRes() {
        return new ResponseEntity<>(reservationService.getEquipment(), HttpStatus.OK);
    }

    /**
     * Gets by id.
     *
     * @param id the id.
     * @return reservation. by id
     */
    @GetMapping("getById/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        try {
            Reservation reservation = reservationService.getById(id);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (ReservationInputException e) {
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Controller for making a field reservation.
     *
     * @param body request body.
     * @return response. response entity
     */
    @PostMapping(value = "makeFieldRes")
    public ResponseEntity<?> makeFieldReservation(@RequestBody String body) {
        ReservationFactory reservationFactory =
                new FieldReservationFactory(reservationService, serviceCommunication);
        return makeReservation(body, reservationFactory);
    }


    /**
     * Controller for making an equipment reservation.
     *
     * @param body request body.
     * @return response. response entity
     */
    @PostMapping(value = "makeEquipmentRes")
    public ResponseEntity<?> makeEquipmentReservation(@RequestBody String body) {
        ReservationFactory reservationFactory =
                new EquipmentReservationFactory(reservationService, serviceCommunication);
        return makeReservation(body, reservationFactory);
    }

    /**
     * Make reservation response entity.
     *
     * @param body               the body
     * @param reservationFactory the reservation factory
     * @return the response entity
     */
    public ResponseEntity<?> makeReservation(String body, ReservationFactory reservationFactory) {
        try {
            Reservation reservation = reservationService.save(body,
                    reservationFactory);
            return new ResponseEntity<>(reservation.getId(), HttpStatus.OK);
        } catch (ReservationInputException e) {
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.BAD_REQUEST);

        } catch (ReservationServerException e) {
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
