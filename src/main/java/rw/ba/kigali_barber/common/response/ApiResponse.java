package rw.ba.kigali_barber.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor 
public class ApiResponse<T> {
    private  boolean success;
    private  String message;
    private T data;
}
