package com.shoppingMall.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import static javax.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
@Table(name = "orders")
public class Order {
    @Id
    @SequenceGenerator(
            name = "orders_sequence",
            sequenceName = "orders_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy  = SEQUENCE,
            generator = "orders_sequence"
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "myOrder",
            nullable = false,
            columnDefinition = "BIGINT"
    )
    private Long myOrder;

    @OneToOne
    @JoinColumn(name = "personId", referencedColumnName = "id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMyOrder() {
        return myOrder;
    }

    public void setMyOrder(Long myOrder) {
        this.myOrder = myOrder;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
