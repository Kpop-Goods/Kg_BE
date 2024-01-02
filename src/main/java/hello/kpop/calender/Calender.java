package hello.kpop.calender;

import hello.kpop.artist.Artist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
//@Data // -> @Getter + @Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "calendar")
public class Calender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // artist could have many scheduled events
    @JoinColumn(name = "artist_fk")
    private Artist artist; // fk, artist table's pk

    @Column
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start", nullable = false)
    private Date start; // start date, time

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end", nullable = false)
    private Date end;

    @Column
    private String link; // external URL
    private String meta; // reserved
}
