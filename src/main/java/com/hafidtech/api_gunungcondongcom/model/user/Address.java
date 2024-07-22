package com.hafidtech.api_gunungcondongcom.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hafidtech.api_gunungcondongcom.model.audit.UserDateAudit;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
public class Address extends UserDateAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String suite;

    private String city;

    private String postalCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geo_id")
    private Geo geo;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private User user;

    public Address(Long id, String street, String suite, String city, String postalCode, Geo geo, User user) {
        this.id = id;
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.postalCode = postalCode;
        this.geo = geo;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
