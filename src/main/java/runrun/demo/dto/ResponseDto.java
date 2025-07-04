package runrun.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private int status;
    private String message;
    private T data;

    public static <T> ResponseDto<T> success(String message, T data) {
        return new ResponseDto<>(200, message, data);
    }

    public static <T> ResponseDto<T> error(int status, String message) {
        return new ResponseDto<>(status, message, null);
    }


}
