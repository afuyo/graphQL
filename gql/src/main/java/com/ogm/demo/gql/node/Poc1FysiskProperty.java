package com.ogm.demo.gql.node;


import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

/**
 * Neo4j node representing entity
 */
@NodeEntity
public class Poc1FysiskProperty {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * Unique id
     */
    private String EAID;

    /**
     * Model name
     */
    private String ModelNavn;

    /**
     * Object name
     */
    private String Namn;

    public Poc1FysiskProperty(){}

    public Poc1FysiskProperty(Long id, String EAID, String modelNavn, String namn) {
        this.id = id;
        this.EAID = EAID;
        ModelNavn = modelNavn;
        Namn = namn;
    }

    public Long getId() {
        return id;
    }

    public String getEAID() {
        return EAID;
    }

    public String getModelNavn() {
        return ModelNavn;
    }

    public String getNamn() {
        return Namn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEAID(String EAID) {
        this.EAID = EAID;
    }

    public void setModelNavn(String modelNavn) {
        ModelNavn = modelNavn;
    }

    public void setNamn(String namn) {
        Namn = namn;
    }
}
