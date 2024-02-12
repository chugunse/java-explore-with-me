package evajan.mainService.consumers.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "consumers")
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sure_name")
    private String sureName;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "consumers_phone_numbers",
            joinColumns = {@JoinColumn(name = "consumer_id")},
            inverseJoinColumns = {@JoinColumn(name = "number_id")}
    )
    private Set<PhoneNumber> phoneNumbers;
    private String comments;
}
