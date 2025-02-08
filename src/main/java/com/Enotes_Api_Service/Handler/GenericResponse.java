package com.Enotes_Api_Service.Handler;



import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {
    private HttpStatus httpStatus;
    private String status;
    private String message;
    private Object data;

    public static GenericResponse success(HttpStatus httpStatus, String message, Object data) {
        return new GenericResponse(httpStatus, "Success", message, data);
    }

    public static GenericResponse failed(HttpStatus httpStatus, String message) {
        return new GenericResponse(httpStatus, "Failed", message, null);
    }
}
