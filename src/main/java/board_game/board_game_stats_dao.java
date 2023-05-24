package board_game;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterBeanMapper(board_game_stats.class)
public interface board_game_stats_dao {
    @SqlUpdate("""
        CREATE TABLE board_game_stats (
            id IDENTITY PRIMARY KEY,
            Winner VARCHAR NOT NULL,
            Other_Player VARCHAR NOT NULL,
            steps_taken INTEGER NOT NULL
        )
        """
    )
    void createTable();

    @SqlUpdate("INSERT INTO board_game_stats (Winner, Other_Player, steps_taken) VALUES (:Winner, :Other_Player, :steps_taken)")
    @GetGeneratedKeys
    long insertboard_game_stats(@Bind("Winner") String Winner, @Bind("Other_Player") String Other_Player, @Bind("steps_taken") int steps_taken);

    @SqlUpdate("INSERT INTO board_game_stats (Winner, Other_Player, steps_taken) VALUES (:Winner, :Other_Player, :steps_taken)")
    @GetGeneratedKeys
    long insertboard_game_stats(@BindBean board_game_stats boardgamestats);

    @SqlQuery("SELECT * FROM board_game_stats WHERE steps_taken = :steps_taken")
    Optional<board_game_stats> getboard_game_stats(@Bind("steps_taken") Integer steps_taken);

    @SqlQuery("SELECT * FROM board_game_stats ORDER BY steps_taken")
    List<board_game_stats> listboard_game_stats();
}
