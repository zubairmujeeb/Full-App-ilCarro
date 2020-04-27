package com.car.rentservice.modal;

import com.car.rentservice.audited.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Audited
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Comments extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentsSequence")
    @SequenceGenerator(name = "commentsSequence", sequenceName = "COMMENTS_SEQ", allocationSize = 1)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String serialNumber;
}
