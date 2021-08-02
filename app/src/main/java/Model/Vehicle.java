package Model;

public class Vehicle {
    String clientid;
    String vehicleno;
    String model;
    String brand;
    String type;
    String insuranceno;
    String revenueno;
    String insurancephotopath;
    String revenuephotopath;
    String vehiclefront;
    String vehicleback;
    String vehicleright;
    String vehicleleft;
    String interiorfront;
    String interiorback;
    String bookpath;
    double price;
    double additional;
    String bookstatus;
    String status;

    public Vehicle() {
    }


    public String getBookstatus() {
        return bookstatus;
    }

    public void setBookstatus(String bookstatus) {
        this.bookstatus = bookstatus;
    }

    public Vehicle(String clientid, String vehicleno, String model, String brand, String type, String insuranceno, String revenueno, String insurancephotopath, String revenuephotopath, String vehiclefront, String vehicleback, String vehicleright, String vehicleleft, String interiorfront, String interiorback, String bookpath, double price, double additional, String bookstatus, String status) {
        this.clientid = clientid;
        this.vehicleno = vehicleno;
        this.model = model;
        this.brand = brand;
        this.type = type;
        this.insuranceno = insuranceno;
        this.revenueno = revenueno;
        this.insurancephotopath = insurancephotopath;
        this.revenuephotopath = revenuephotopath;
        this.vehiclefront = vehiclefront;
        this.vehicleback = vehicleback;
        this.vehicleright = vehicleright;
        this.vehicleleft = vehicleleft;
        this.interiorfront = interiorfront;
        this.interiorback = interiorback;
        this.bookpath = bookpath;
        this.price = price;
        this.additional = additional;
        this.bookstatus = bookstatus;
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAdditional() {
        return additional;
    }

    public void setAdditional(double additional) {
        this.additional = additional;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInsuranceno() {
        return insuranceno;
    }

    public void setInsuranceno(String insuranceno) {
        this.insuranceno = insuranceno;
    }

    public String getRevenueno() {
        return revenueno;
    }

    public void setRevenueno(String revenueno) {
        this.revenueno = revenueno;
    }

    public String getInsurancephotopath() {
        return insurancephotopath;
    }

    public void setInsurancephotopath(String insurancephotopath) {
        this.insurancephotopath = insurancephotopath;
    }

    public String getRevenuephotopath() {
        return revenuephotopath;
    }

    public void setRevenuephotopath(String revenuephotopath) {
        this.revenuephotopath = revenuephotopath;
    }

    public String getVehiclefront() {
        return vehiclefront;
    }

    public void setVehiclefront(String vehiclefront) {
        this.vehiclefront = vehiclefront;
    }

    public String getVehicleback() {
        return vehicleback;
    }

    public void setVehicleback(String vehicleback) {
        this.vehicleback = vehicleback;
    }

    public String getVehicleright() {
        return vehicleright;
    }

    public void setVehicleright(String vehicleright) {
        this.vehicleright = vehicleright;
    }

    public String getVehicleleft() {
        return vehicleleft;
    }

    public void setVehicleleft(String vehicleleft) {
        this.vehicleleft = vehicleleft;
    }

    public String getInteriorfront() {
        return interiorfront;
    }

    public void setInteriorfront(String interiorfront) {
        this.interiorfront = interiorfront;
    }

    public String getInteriorback() {
        return interiorback;
    }

    public void setInteriorback(String interiorback) {
        this.interiorback = interiorback;
    }

    public String getBookpath() {
        return bookpath;
    }

    public void setBookpath(String bookpath) {
        this.bookpath = bookpath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
