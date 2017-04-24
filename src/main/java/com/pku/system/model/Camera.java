package com.pku.system.model;

/**
 * Created by jiangdongyu on 2017/4/24.
 */
public class Camera {
    private int id;
    private int cameraTypeId;
    private int cameraStatus;
    private String cameraAngle;
    private int did;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCameraTypeId() {
        return cameraTypeId;
    }

    public void setCameraTypeId(int cameraTypeId) {
        this.cameraTypeId = cameraTypeId;
    }

    public int getCameraStatus() {
        return cameraStatus;
    }

    public void setCameraStatus(int cameraStatus) {
        this.cameraStatus = cameraStatus;
    }

    public String getCameraAngle() {
        return cameraAngle;
    }

    public void setCameraAngle(String cameraAngle) {
        this.cameraAngle = cameraAngle;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }
}
