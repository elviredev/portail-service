package intra.poleemploi;

import intra.poleemploi.dao.AppliRepository;
import intra.poleemploi.dao.ContentRepository;
import intra.poleemploi.dao.UserAppRepository;
import intra.poleemploi.entities.Appli;
import intra.poleemploi.entities.Content;
import intra.poleemploi.entities.RoleApp;
import intra.poleemploi.entities.UserApp;
import intra.poleemploi.service.AuthService;
import intra.poleemploi.utility.ReadExcel;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;


@SpringBootApplication
public class PortailServiceApplication {
	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(PortailServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(AppliRepository appliRepository, ContentRepository contentRepository, AuthService authService, UserAppRepository userAppRepository){
		return args -> {
			repositoryRestConfiguration.exposeIdsFor(Appli.class, Content.class, UserApp.class, RoleApp.class);
			// A VIRER => supprime les données Appli avant chaque lancement de l'appli
			appliRepository.deleteAll();
			// save les données coachedAppli en BDD
//			appliRepository.save(new Appli(1, "APP01", "Profil de compétences"));
//			appliRepository.save(new Appli(2, "APP02", "MRS Digitale"));
//			appliRepository.save(new Appli(3, "APP03", "MAP DE"));
//			appliRepository.save(new Appli(4, "APP04", "AUDE Presta"));

			List<Appli> listAppli = new ArrayList<>();
			ReadExcel readListAppli = new ReadExcel();
			listAppli = readListAppli.getAppliList("C:/demo/KnowMore/knowMore CoachedAppli.xlsx");

			for (Appli tempAppli : listAppli) {
				if (tempAppli.getIdAppliKM() != "id")  {
					appliRepository.save(tempAppli);
				}
			}

		    // parcourt et affiche les données
			appliRepository.findAll().forEach(System.out::println);
			// supprime les données contentAppli avant chaque lancement de l'appli
			contentRepository.deleteAll();
            List<Content> listContent = new ArrayList<>();
			listAppli = readListAppli.getAppliList("C:/demo/KnowMore/knowMore ContentAppli.xlsx");

			for (Appli tempAppli : listAppli) {
				if (tempAppli. != "id")  {
					appliRepository.save(tempAppli);
				}
			}


//			Random rnd = new Random();
//			appliRepository.findAll().forEach(appli -> {
//				for (int i = 0; i < 5; i++) {
//					Content cnt = new Content();
//					cnt.setId(rnd.nextInt());
//					cnt.setContentName(RandomString.make(7));
//					cnt.setAppli(appli);
//					contentRepository.save(cnt);
//				}
//			});
//			contentRepository.findAll().forEach(cnt -> {
//				System.out.println(cnt.toString());
//			});

			// AUTHENTICATION
			// ajout de 2 roles
			authService.saveRoleApp(new RoleApp(null, "USER"));
			authService.saveRoleApp(new RoleApp(null, "ADMIN"));

			// ajout users
			Stream.of("user1", "user2", "user3", "user4", "admin").forEach(username -> {
				authService.saveUserApp(username, "1234", "1234");
			});

			// ajout role ADMIN a l'admin
			authService.addRoleToUser("admin", "ADMIN");

            // ajout appli à user
			authService.addAppliToUser("user1", "MAP DE");
            authService.addAppliToUser("user1", "Profil de compétences");
            authService.addAppliToUser("user1", "AUDE Presta");
			authService.addAppliToUser("user2", "MRS Digitale");
			authService.addAppliToUser("user2", "MAP DE");
			authService.addAppliToUser("user3", "Profil de compétences");


			userAppRepository.findAll().forEach(System.out::println);
		};
	}
	// créer BCryptPasswordEncoder au démarrage de l'appli pour injection dans couche Service
	@Bean
	BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}
}

