package Model;

public class RentSchedule {

    String vehicleno;
    String start;
    String end;
    double paid;
    double remain;
    String customerdocid;
    long invoiceno;
    String date;
    String status;
    double milage;
    double endmilage;

    public double getRemain() {
        return remain;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getMilage() {
        return milage;
    }

    public void setMilage(double milage) {
        this.milage = milage;
    }

    public double getEndmilage() {
        return endmilage;
    }

    public void setEndmilage(double endmilage) {
        this.endmilage = endmilage;
    }

    public RentSchedule(String vehicleno, String start, String end, double paid, double remain, String customerdocid, long invoiceno, String date, String status, double milage, double endmilage) {
        this.vehicleno = vehicleno;
        this.start = start;
        this.end = end;
        this.paid = paid;
        this.remain = remain;
        this.customerdocid = customerdocid;
        this.invoiceno = invoiceno;
        this.date = date;
        this.status = status;
        this.milage = milage;
        this.endmilage = endmilage;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

    public String getCustomerdocid() {
        return customerdocid;
    }

    public void setCustomerdocid(String customerdocid) {
        this.customerdocid = customerdocid;
    }

    public long getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(long invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }



    public RentSchedule() {
    }



    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
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
