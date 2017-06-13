package zmq.com.model;

import java.io.Serializable;

/**
 * Created by zmq162 on 3/2/16.
 */
public class SputumInfo implements Serializable{

    private String sputumCounter;
    private String examinDate;
    private String sputumTakenDate;
    private String sputumSpecimen;
    private String visualAppearance;
    private String grading;
    private String result;
    private String scanty;
    private String SubmitStatus;

    public String getSputumCounter() {
        return sputumCounter;
    }

    public void setSputumCounter(String sputumCounter) {
        this.sputumCounter = sputumCounter;
    }

    public String getExaminDate() {
        return examinDate;
    }

    public void setExaminDate(String examinDate) {
        this.examinDate = examinDate;
    }

    public String getSputumTakenDate() {
        return sputumTakenDate;
    }

    public void setSputumTakenDate(String sputumTakenDate) {
        this.sputumTakenDate = sputumTakenDate;
    }

    public String getSputumSpecimen() {
        return sputumSpecimen;
    }

    public void setSputumSpecimen(String sputumSpecimen) {
        this.sputumSpecimen = sputumSpecimen;
    }

    public String getVisualAppearance() {
        return visualAppearance;
    }

    public void setVisualAppearance(String visualAppearance) {
        this.visualAppearance = visualAppearance;
    }

    public String getGrading() {
        return grading;
    }

    public void setGrading(String grading) {
        this.grading = grading;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getScanty() {
        return scanty;
    }

    public void setScanty(String scanty) {
        this.scanty = scanty;
    }

    public String getSubmitStatus() {
        return SubmitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        SubmitStatus = submitStatus;
    }
}
