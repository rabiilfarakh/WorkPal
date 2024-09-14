package org.example.entity;

import java.util.Date;

public class Reservation {
    private Integer reservation_id;
    private Date start_date;
    private Date end_date;
    private Boolean status;
    private Space space;
    private Member member;

    public Reservation(Integer reservation_id, Date start_date, Date end_date, Boolean status, Space space, Member member) {
        this.reservation_id = reservation_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.space = space;
        this.member = member;
    }

    public Reservation() {
    }


    public Integer getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Integer reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
