package com.garden.back.region;

import jakarta.persistence.*;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "regions")
public class Region {

    protected Region() {}

    @Id
    @Column(name = "code")
    private Long id;

    @Embedded
    private Address address;

    @Column(name = "point")
    private Point point;

    @Column(name = "area")
    private MultiPolygon area;

    public Region(Long id, Address address, Point point, MultiPolygon area) {
        this.id = id;
        this.address = address;
        this.point = point;
        this.area = area;
    }
}
