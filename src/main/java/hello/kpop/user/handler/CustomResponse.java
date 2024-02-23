package hello.kpop.user.handler;

public class CustomResponse {
    private int statusCode;
    private String message;
    private Object data; // Change the type to match the data you want to return

    // Constructors, getters, and setters

    public CustomResponse() {
        // Default constructor
    }

    public CustomResponse(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
