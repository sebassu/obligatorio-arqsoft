
package roi.utilities;

public class SupplyOrderNotification {

    private long orderNumber;
    private long servicePointId;
    
    public SupplyOrderNotification(long orderNumber, long servicePointId) {
        this.orderNumber = orderNumber;
        this.servicePointId = servicePointId;
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
}
