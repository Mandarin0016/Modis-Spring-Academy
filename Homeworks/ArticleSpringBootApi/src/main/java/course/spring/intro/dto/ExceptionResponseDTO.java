package course.spring.intro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ExceptionResponseDTO {
    @NonNull
    private int statusCode;
    @NonNull
    private String errorMessage;
    private List<String> violations = new ArrayList<>();
}
