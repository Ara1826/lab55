package sources.commands;

import sources.client.Client;
import sources.exceptions.io.WrongArgumentException;
import sources.exceptions.receiver.CollectionKeyException;
import sources.models.MovieGenre;
import sources.models.MpaaRating;
import sources.receiver.Receiver;

import java.time.LocalDateTime;

public class Update extends AbstractCommand {
    private final Integer id;
    private final String movieName;
    private final Integer x;
    private final Integer y;
    private final long oscarsCount;
    private final MovieGenre movieGenre;
    private final MpaaRating mpaaRating;
    private final String actorName;
    private final LocalDateTime birthday;
    private final Integer weight;
    private final String salary;

    public Update(Client client, Receiver receiver, Integer id, String movieName, Integer x, Integer y,
                  long oscarsCount, MovieGenre movieGenre, MpaaRating mpaaRating, String actorName,
                  LocalDateTime birthday, Integer weight, String salary) {
        super("update", client, receiver);
        this.id = id;
        this.movieName = movieName;
        this.x = x;
        this.y = y;
        this.oscarsCount = oscarsCount;
        this.movieGenre = movieGenre;
        this.mpaaRating = mpaaRating;
        this.actorName = actorName;
        this.birthday = birthday;
        this.weight = weight;
        this.salary = salary;
    }

    @Override
    public void execute() throws CollectionKeyException, WrongArgumentException {
        receiver.update(id, movieName, x, y, oscarsCount, movieGenre,
                mpaaRating, actorName, birthday, weight, salary);
    }

    @Override
    public String toString() {
        return name + " {" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", oscarsCount=" + oscarsCount +
                ", movieGenre=" + movieGenre +
                ", mpaaRating=" + mpaaRating +
                ", actorName='" + actorName + '\'' +
                ", birthday=" + birthday +
                ", weight=" + weight +
                ", salary='" + salary + '\'' +
                '}';
    }
}
