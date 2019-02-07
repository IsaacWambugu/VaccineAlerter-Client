package com.example.vaccine_alerter_client.models;

import java.util.HashMap;
import java.util.Map;

public class ChildModel {

    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private Integer opv1Due;
    private Integer bcg1Due;
    private Integer hepB1Due;
    private Integer dpt1Due;
    private Integer hibB1Due;
    private Integer hepB2Due;
    private Integer opv2Due;
    private Integer pneuDue;
    private Integer rota1Due;
    private Integer dpt2Due;
    private Integer hibB2Due;
    private Integer hepB3Due;
    private Integer opv3Due;
    private Integer vitA1Due;
    private Integer rota2Due;
    private Integer vitA2Due;
    private Integer measlesDue;
    private Integer yellowDue;
    private Boolean vaccineDue;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public ChildModel(int id, String firstName, String  lastName, String gender,
                      int opv1Due, int bcg1Due, int hepB1Due, int dpt1Due, int hibB1Due, int hepB2Due,
                      int opv2Due, int pneuDue, int rota1Due, int dpt2Due, int hibB2Due, int hepB3Due,
                      int opv3Due, int vitA1Due, int rota2Due, int vitA2Due, int measlesDue, int yellowDue,
                      Boolean vaccineDue
    ){

        this.id        = id;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.gender    = gender;

        this.opv1Due   = opv1Due;
        this.bcg1Due   = bcg1Due;
        this.hepB1Due  = hepB1Due;

        this.dpt1Due   = dpt1Due;
        this.hibB1Due  = hibB1Due;
        this.hepB2Due  = hepB2Due;

        this.opv2Due   = opv2Due;
        this.pneuDue   = pneuDue;
        this.rota1Due  = rota1Due;

        this.dpt2Due   = dpt2Due;
        this.hibB2Due  = hibB2Due;
        this.hepB3Due  = hepB3Due;

        this.opv3Due   = opv3Due;
        this.vitA1Due  = vitA1Due;
        this.rota2Due  = rota2Due;

        this.vitA2Due  = vitA2Due;
        this.measlesDue= measlesDue;
        this.yellowDue = yellowDue;

        this.vaccineDue = vaccineDue;


    }
    public int getId(){
        return  id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public Integer getOpv1Due() {
        return opv1Due;
    }

    public Integer getBcg1Due() {
        return bcg1Due;
    }

    public Integer getHepB1Due() {
        return hepB1Due;
    }

    public Integer getDpt1Due() {
        return dpt1Due;
    }

    public Integer getHibB1Due() {
        return hibB1Due;
    }

    public Integer getHepB2Due() {
        return hepB2Due;
    }

    public Integer getOpv2Due() {
        return opv2Due;
    }

    public Integer getPneuDue() {
        return pneuDue;
    }

    public Integer getRota1Due() {
        return rota1Due;
    }

    public Integer getDpt2Due() {
        return dpt2Due;
    }

    public Integer getHibB2Due() {
        return hibB2Due;
    }

    public Integer getHepB3Due() {
        return hepB3Due;
    }

    public Integer getOpv3Due() {
        return opv3Due;
    }

    public Integer getVitA1Due() {
        return vitA1Due;
    }

    public Integer getRota2Due() {
        return rota2Due;
    }

    public Integer getVitA2Due() {
        return vitA2Due;
    }

    public Integer getMeaslesDue() {
        return measlesDue;
    }

    public Integer getYellowDue() {
        return yellowDue;
    }
    public Boolean getVaccineDue() {
        return  vaccineDue;
    }


}