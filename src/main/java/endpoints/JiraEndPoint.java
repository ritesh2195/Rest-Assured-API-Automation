package endpoints;

public enum JiraEndPoint {

    ISSUE("/issue"),
    USER("/user");

    private String endPoint;

    JiraEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getEndPoint(){
        return endPoint;
    }
}
