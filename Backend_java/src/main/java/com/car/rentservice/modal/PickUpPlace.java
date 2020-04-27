package com.car.rentservice.modal;

import com.car.rentservice.audited.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pickup_place")
@Audited
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PickUpPlace extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "placeSequence")
    @SequenceGenerator(name = "placeSequence", sequenceName = "PLACE_SEQ", allocationSize = 1)
    private Long id;

    private String placeId;
    private String placeName;
    private BigDecimal latitude;
    private BigDecimal longitude;


}
