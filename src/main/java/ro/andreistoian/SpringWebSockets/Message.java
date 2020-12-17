package ro.andreistoian.SpringWebSockets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "message")
    private String text;


}
