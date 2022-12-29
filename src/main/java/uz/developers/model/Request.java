package uz.developers.model;

import com.pengrad.telegrambot.model.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.developers.enums.State;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private Update update;
    private State state;
    public Request (Update update){
        this.update = update;
        this.state = State.NONE;
    }
}
