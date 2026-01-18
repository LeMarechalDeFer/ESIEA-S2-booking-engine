┌──────────┬──────┬────────┐                                                                                                                                                                           
│ Service  │ Port │ Status │                                                                            
├──────────┼──────┼────────┤                                                                                                                                                                             │ frontend │ 3000 │ Up     │                                                                                                                                                                           
├──────────┼──────┼────────┤                                                                                                                                                                           
│ backend  │ 8081 │ Up     │                                                                                                                                                                             ├──────────┼──────┼────────┤    
│ kafka-ui │ 8080 │ Up     │                                                                                                                                                                           
├──────────┼──────┼────────┤                                                                                                                                                                           
│ kafka    │ 9092 │ Up     │
├──────────┼──────┼────────┤
│ postgres │ 5432 │ Up     │
└──────────┴──────┴────────┘
Tu peux accéder à :
- Frontend: http://localhost:3000
- Backend API: http://localhost:8081
- Kafka UI: http://localhost:8080


Nouveaux fichiers créés
- domain/ports/EventPublisherPort.java - interface pour la publication d'events
- domain/ports/PasswordEncoderPort.java - interface pour l'encodage mot de passe
- frameworks/security/PasswordEncoderAdapter.java - implémente PasswordEncoderPort
- frameworks/config/UseCaseConfig.java - instancie les UseCases comme beans Spring

UseCases nettoyés (Pure Java maintenant)

- Retiré @Service des 4 UseCases
- Remplacé imports frameworks.kafka.EventPublisher → domain.ports.EventPublisherPort
- Remplacé imports spring.security.crypto.password.PasswordEncoder → domain.ports.PasswordEncoderPort

Controllers améliorés

- Retiré / au début des @RequestMapping et endpoints
- Ajouté @ApiResponses avec codes 200, 400, 404, 500
- Ajouté descriptions détaillées sur @Operation

Architecture respectée

domain/     → Pure Java (entities, repositories, ports, events, exceptions)
usecase/    → Pure Java (business logic, dépend uniquement du domain)
adapters/   → Spring autorisé (persistence, web)
frameworks/ → Spring config (UseCaseConfig, SecurityConfig, Kafka)

