package com.itsupportme.gis.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = "Calendar"
)
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cdr_Id")
    public int id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "Cdr_Title", length = 50)
    public String title;

    @Column(name = "Cdr_Start")
    public String start;

    @Column(name = "Cdr_End")
    public String end;

    @Column(name = "Cdr_Color")
    public String color;


    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getStart() {
        return start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public String getEnd() {
        return end;
    }
    public void setEnd(String end) {
        this.end = end;
    }

}
