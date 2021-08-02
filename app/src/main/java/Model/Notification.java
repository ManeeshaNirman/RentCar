package Model;

public class Notification {

    public Notification(String notification, String customerId, String type, String mark) {
        Notification = notification;
        this.customerId = customerId;
        this.type = type;
        this.mark = mark;
    }

    String Notification;
    String customerId;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    String type;
    String mark;

    public String getNotification() {
        return Notification;
    }

    public void setNotification(String notification) {
        Notification = notification;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Notification() {
    }
}
