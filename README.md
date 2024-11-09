* Stack Technique: 
  - Java 17
  - Spring boot 3
  - base de donnees H2
  - SQL Server avec Docker

Projet de simulation des opérations sur un compte bancaire avec les operations suivante:
Les opérations sur les comptes bancaires sont les suivants: 
- creer un compte
- afficher tous les comptes
- crediter
- débiter
- afficher le releve d'un compte
- transferer un montant d'un compte vers un autre
Le projet utilise Java 17 et Spring Boot 3 et expose des API REST.
Il y'a les endpoints suivants:
** Connexion à la base H2 en local
 ouvrir: http://localhost:8080/h2-console
 Pour les paramètres de connexion, voir le fichier application.yml