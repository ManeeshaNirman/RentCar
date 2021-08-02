package Model;

public class Breakdown {

    String type;
    double latitude;
    double longitude;
    String customerdocid;
    String rentsheduleid;
    String date;
    String status;

    public Breakdown(String type,String status, double latitude, double longitude, String customerdocid, String rentsheduleid, String date) {
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.customerdocid = customerdocid;
        this.rentsheduleid = rentsheduleid;
        this.date = date;
        this.status=status;
    }

    public Breakdown() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCustomerdocid() {
        return customerdocid;
    }

    public void setCustomerdocid(String customerdocid) {
        this.customerdocid = customerdocid;
    }

    public String getRentsheduleid() {
        return rentsheduleid;
    }

    public void setRentsheduleid(String rentsheduleid) {
        this.rentsheduleid = rentsheduleid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
