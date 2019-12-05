package intra.poleemploi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
@Table(name="content")
public class Content implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String idContentKM;
    private String contentName;
    private boolean published;
    private String typeService;
    //  @Column(name="NbAffichages")
    private int nbAffichages;
    //  @Column(name="NbLectures")
    private int nbLectures;
    //private String description;
    //private String icone;
    //private String contentURL;
    //private Date debut;
    //private Date fin;
    @ManyToOne
    @JoinColumn(name = "id_appli", nullable = false)
    private Appli appli;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "content", orphanRemoval = true)
    private Collection<StatistiquesParJour> statistiquesParJourList = new ArrayList<>();

    public Content(String c1, String c1_name, Appli appli) {
        this.idContentKM = c1;
        this.contentName = c1_name;
        this.appli = appli;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", idContentKM='" + idContentKM + '\'' +
                ", content Name='" + contentName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        Content objetATester = (Content) o;
        // field comparison
        return Objects.equals(id, objetATester.id)
                && Objects.equals(idContentKM, objetATester.idContentKM);
    }
}
