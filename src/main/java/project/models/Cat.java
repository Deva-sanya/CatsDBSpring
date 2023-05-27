package project.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Setter
@Getter
@Entity
@Table(name = "Cat")
public class Cat {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "age")
    @Positive(message = "Age should not be negative")
    private int age;

    @Column(name = "name")
    @NotEmpty(message = "Name of the cat should not be empty")
    @Size(min = 2, max = 100, message = "The name of the cat should be between two and 100 characters long.")
    private String name;

    @Column(name = "fatherId")
    private int fatherId;

    @Column(name = "motherId")
    private int motherId;

    @Column(name = "gender")
    private String gender;

    @Column(name = "color")
    private String color;

    public Cat() {

    }

}
