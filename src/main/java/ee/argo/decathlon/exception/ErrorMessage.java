package ee.argo.decathlon.exception;

import lombok.Value;
import java.util.Date;

@Value
public class ErrorMessage {
    String message;
    Date timestamp;
    int status;
}
