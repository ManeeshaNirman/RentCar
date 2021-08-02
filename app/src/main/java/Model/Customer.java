package Model;

public class Customer {

    String email;
    String firstname;
    String midname;
    String lastname;
    String password;
    String profileimagepath;
    String drivinglicenphotopathfront;
    String drivinglicennumber;
    int mobile;
    String no;
    String street;
    String city;
    String distric;
    String Status;
    String proof;
    String drivinglicenphotopathback;


    public Customer(String email, String firstname, String midname, String lastname, String password, String profileimagepath, String drivinglicenphotopathfront, String drivinglicennumber, int mobile, String no, String street, String city, String distric, String status, String proof, String drivinglicenphotopathback) {
        this.email = email;
        this.firstname = firstname;
        this.midname = midname;
        this.lastname = lastname;
        this.password = password;
        this.profileimagepath = profileimagepath;
        this.drivinglicenphotopathfront = drivinglicenphotopathfront;
        this.drivinglicennumber = drivinglicennumber;
        this.mobile = mobile;
        this.no = no;
        this.street = street;
        this.city = city;
        this.distric = distric;
        Status = status;
        this.proof = proof;
        this.drivinglicenphotopathback = drivinglicenphotopathback;
    }

    public Customer() {

    }

    public String getDrivinglicenphotopathfront() {
        return drivinglicenphotopathfront;
    }

    public void setDrivinglicenphotopathfront(String drivinglicenphotopathfront) {
        this.drivinglicenphotopathfront = drivinglicenphotopathfront;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public String getDrivinglicenphotopathback() {
        return drivinglicenphotopathback;
    }

    public void setDrivinglicenphotopathback(String drivinglicenphotopathback) {
        this.drivinglicenphotopathback = drivinglicenphotopathback;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileimagepath() {
        return profileimagepath;
    }

    public void setProfileimagepath(String profileimagepath) {
        this.profileimagepath = profileimagepath;
    }



    public String getDrivinglicennumber() {
        return drivinglicennumber;
    }

    public void setDrivinglicennumber(String drivinglicennumber) {
        this.drivinglicennumber = drivinglicennumber;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistric() {
        return distric;
    }

    public void setDistric(String distric) {
        this.distric = distric;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
