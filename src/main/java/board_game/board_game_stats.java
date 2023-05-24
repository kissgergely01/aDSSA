package board_game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class board_game_stats {
    private Long id;
    private String Winner;
    private String Other_Player;
    private int steps_taken;


    public board_game_stats(String Winner,String Other_Player,int steps_taken){this(null,Winner,Other_Player,steps_taken);}

}
