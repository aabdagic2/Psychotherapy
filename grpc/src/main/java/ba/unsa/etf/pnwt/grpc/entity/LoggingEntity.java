package ba.unsa.etf.pnwt.grpc.entity;

import ba.unsa.etf.pnwt.grpc.constants.ApplicationConstants;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.ZonedDateTime;


@Entity
@Table(schema = ApplicationConstants.GRPC_SCHEMA, name = "logging" )
public class LoggingEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "serviceName")
    private String serviceName;

    @Column(name = "userUUID")
    private String userUUID;

    @Column(name = "timeStamp")
    private ZonedDateTime timeStamp;

    @Column(name = "controllerName")
    private String controllerName;

    @Column(name = "actionURL")
    private String url;

    @Column(name = "actionType")
    private String actionType;

    @Column(name = "actionResponse")
    private String actionResponse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionResponse() {
        return actionResponse;
    }

    public void setActionResponse(String actionResponse) {
        this.actionResponse = actionResponse;
    }
}
