package SpringDoc.uz.pdp.docs.dto;


import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AppErrorDto {
    private final String errorPath;
    private final String errorMessage;
    private final  int errorCode;
    private  final LocalDateTime timestamp;

    public AppErrorDto(String errorPath, String errorMessage, int errorCode) {
        this.errorPath = errorPath;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.timestamp=LocalDateTime.now(Clock.system(ZoneId.of("Asia/Tashkent")));
    }
}
