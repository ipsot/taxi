package ru.taxi.orderprocessor.entity;

public class RateEntity extends BaseEntity{

    private Integer rate;
    private RateDetail rateDetail;
    private String extFeedback;

    public enum RateDetail{
        POLITE_DRIVE, BEST_DRIVER, TRIP_COMFORT, COMFORT_MUSIC
    }
}
