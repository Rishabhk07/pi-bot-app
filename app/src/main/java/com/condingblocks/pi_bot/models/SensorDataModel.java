package com.condingblocks.pi_bot.models;

/**
 * Created by rishabhkhanna on 23/03/17.
 */

public class SensorDataModel {
    float accelX;
    float accelY;
    float accelZ;

    public SensorDataModel() {
    }

    public SensorDataModel(float accelX, float accelY, float accelZ) {
        this.accelX = accelX;
        this.accelY = accelY;
        this.accelZ = accelZ;
    }

    public float getAccelX() {
        return accelX;
    }

    public void setAccelX(float accelX) {
        this.accelX = accelX;
    }

    public float getAccelY() {
        return accelY;
    }

    public void setAccelY(float accelY) {
        this.accelY = accelY;
    }

    public float getAccelZ() {
        return accelZ;
    }

    public void setAccelZ(float accelZ) {
        this.accelZ = accelZ;
    }
}
