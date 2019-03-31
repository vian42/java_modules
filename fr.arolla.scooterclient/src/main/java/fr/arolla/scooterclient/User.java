package fr.arolla.scooterclient;

import lombok.Data;

import java.math.BigDecimal;

@Data
class User {

    private String id;
    private String rentedLocomotionId;
    private boolean isUsingALocomotion;
    private boolean isJuicingALocomotion;
    private boolean isHappy;
    private BigDecimal creditAmount;
}
