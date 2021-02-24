package com.treelevel.app.training;

import com.treelevel.app.training.controller.ProgramController;
import com.treelevel.app.training.model.Program;
import com.treelevel.app.training.model.Visibility;
import com.treelevel.app.training.model.auth.Role;
import com.treelevel.app.training.model.auth.User;
import com.treelevel.app.training.repository.ProgramRepository;
import com.treelevel.app.training.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.treelevel.app.training.storage.StorageProperties;
import com.treelevel.app.training.storage.StorageService;

import java.util.Set;
import java.util.UUID;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class TrainingApplication {

    @Autowired
    ProgramRepository trainingRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProgramController programController;

    public static void main(String[] args) {
        SpringApplication.run(TrainingApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws Exception {
        Role adminRole = new Role("ADMIN");
        Role readerRole = new Role("READER");

        userRepository.deleteAll();
        User userAdmin = new User("admin", "$2y$10$yhImhJRIev3Dj1L31f4nKOS1xFwS12A3QS54N3EQxsEPuGxOocqEu");
        userAdmin.setRoles(Set.of(adminRole));
        userRepository.save(userAdmin);

        User userBasic = new User("user", "$2y$10$yhImhJRIev3Dj1L31f4nKOS1xFwS12A3QS54N3EQxsEPuGxOocqEu");
        userBasic.setApikey("47a6e3de-c73f-423d-8218-32bb58aa6f81");
        userBasic.setRoles(Set.of(readerRole));
        userRepository.save(userBasic);

        trainingRepo.deleteAll();
        trainingRepo.save(
                new Program(
                        "Angular les bases",
                        "AGUB",
                        3,
                        "Etre capable de faire marcher angular de manière coooooooooooool",
                        "HTML/CSS/JS",
                        "<h1>Rappels</h1><ul><li>TypeScript</li><li>Outillage</li></ul><h1>Présentation</h1><ul><li>Points négatifs AngularJS</li><li>Points positifs Angular</li><li>Points négatifs Angular</li><li>Architecture</li></ul><h1>Démarrer une application Angular</h1><ul><li>Commencer un nouveau projet</li><li>Angular-CLI</li><li>Webpack</li></ul><h1>Tests</h1><ul><li>Jasmine</li><li>Karma</li></ul><h1>Composants</h1><ul><li>Syntaxe des templates</li><li>Propriétés</li><li>Evénements</li><li>Directives</li><li>Composants</li></ul><h1>Composants Angular</h1><ul><li>ngStyle</li><li>ngClass</li><li>ngFor</li><li>ngIf</li><li>ngSwitch</li><li>ngNonBindable</li></ul><h1>Injection de Dépendances</h1><ul><li>Injecteurs</li><li>Providers</li></ul><h1>Les Pipes</h1><ul><li>Création</li><li>Utilisation</li></ul><h1>Communication avec une API REST</h1><ul><li>Http</li><li>Observables</li></ul><h1>Router</h1><ul><li>Configuration du Router</li><li>Directive RouterOutlet</li><li>Directive RouterLink</li><li>Stratégies URL</li><li>Paramètres URL</li><li>Cycle de vie</li></ul><h1>Gestion des Formulaires</h1><ul><li>Directives</li><li>Validation</li></ul><h1>Server-side rendering</h1><ul><li>Angular Universal</li></ul>",
                        300,
                        400,
                        true,
                         Visibility.PUBLIC
                )
        );
        programController.generatePdf(1L);
    }
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return multipartResolver;
    }
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
