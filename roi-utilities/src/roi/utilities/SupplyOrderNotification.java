
package roi.utilities;

public class SupplyOrderNotification {

    private long orderNumber;
    private long servicePointId;
    private NotificationType type;
    
    public SupplyOrderNotification(long orderNumber, long servicePointId, NotificationType type) {
        this.orderNumber = orderNumber;
        this.servicePointId = servicePointId;
        this.type = type;
    }
    
    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getServicePointId() {
        return servicePointId;
    }

    public void setServicePointId(long servicePointId) {
        this.servicePointId = servicePointId;
    }

    public NotificationType getNotificationType() {
        return type;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.type = notificationType;
    }
    
    public enum NotificationType {
        CREATED, MODIFIED, REMOVED
    }
    
}
