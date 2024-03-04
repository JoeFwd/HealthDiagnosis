# Système automatisé de diagnostic de santé

Ce système automatisé de diagnostic de santé est une application logicielle conçue pour aider au diagnostic des problèmes de santé basés sur un index de santé. Il suit une architecture modulaire et en couches, en s'appuyant sur des concepts de CQRS (Ségrégation de la Responsabilité de Commande et de Requête) et de DDD (Conception Pilotée par le Domaine) pour garantir l'extensibilité, la maintenabilité et la scalabilité.

## Aperçu de l'architecture

### Architecture en oignon

- **Couche métier**: 
    - Elle abrite les concepts de domaine fondamentaux tels que les entités `Diagnostic`, `HealthIssue`, `MedicalUnit` et l'objet de valeur `HealthIndex`. Ces entités encapsulent la logique métier et le comportement du domaine de diagnostic de santé, permettant la compréhension du métier en lisant le code.
    - L'implémentation actuelle utilise des interfaces pour représenter les pathologies et les unités médicales. Cela permet à l'équipe de développement de facilement augmenter le nombre de nouveaux objets de domaine et de nouveaux comportements unifiés au sein du domaine.

- **Couche d'application**: 
    - Elle fait office d'intermédiaire entre la couche métier et les systèmes externes. Elle contient les use-cases, les commandes et les requêtes pour interagir avec la couche métier. Par exemple, la commande `CreateDiagnosticCommand` coordonne le processus de création de diagnostics, garantissant que les règles métier sont respectées et correctement exécutées.
    - De plus, la validation de l'index de santé donné s'effectue également dans cette couche. En cas d'invalidité, la création du diagnostic est rejetée et une erreur est levée. Ce comportement est vérifié avec des tests unitaires.
    - Cependant, cette couche n'est pas responsable de la gestion de cette erreur car elle ne communique pas directement avec l'appelant externe. Ainsi, pour garantir la conformité avec la contrainte, l'application déclenche un événement "DiagnosticCreationFailed" que la couche externe traitera à sa discrétion.

- **Couche d'infrastructure**: 
    - Elle gère les interactions avec les ressources externes telles que les bases de données, les systèmes de messagerie et les API externes. Par exemple, le `InMemoryDiagnosticRepository` fournit un mécanisme de stockage en mémoire pour les diagnostics, tandis que le `InMemoryPubSubMessenger` facilite la communication orientée événement au sein de l'application.
    - Cette couche abstrait les détails des systèmes externes permettant à l'application de rester indépendante de l'infrastructure.

### Architecture DDD et CQRS

- **DDD**:
    - L'entité `Diagnostic` encapsule le comportement des diagnostics notamment la redirection des patients vers les unités médicales appropriées. Celles-ci ont été modélisées avec une relation 1 - N avec les pathologies.
    - De plus, un diagnostic peut avoir plusieurs unités médicales et vice-versa resultant à une relation M - N. Dans le cadre du use-case, il n'est pas pertinent de représenter la relation M à 1 (M diagnostics pour une unité médicale), d'où le choix de faire l'entité diagnostic la racine et de représenter la relation 1 - N.
    - Enfin, l'index de santé réprésenté par l'objet `HealthIndex` est par définition un entier immutable qui correspond parfaitement au rôle d'objet de valeur.

- **CQRS**:
    - Les opérations d'écriture et de lecture sont séparées respectivement en commande et en requête pour optimiser les performances. 
    - La commande `CreateDiagnosticCommand`, est responsable de la mutation de l'état de l'application, tandis que la requête `DiagnosticResultQuery`, récupère des informations sans modification.
    - La requête bénéficie principalement du contournement de la logique métier et du non-chargement de l'entité racine (Diagnostic) en mémoire, ce qui peut être une opération coûteuse.

### Flexibilité dans l'implémentation de l'infrastructure

La couche d'infrastructure est actuellement implémentée en mémoire mais est conçue pour être facilement remplaçable par de vrais composants tels que des bases de données, des systèmes de messagerie ou des API externes. Ainsi, cette couche peut évoluer aux exigences de la production sans impacter les couches intérieures de "l'oignon".

## Vue d'ensemble des classes Java

### Couche métier

#### Problèmes de santé

- **HealthIssue**: Une interface définissant le contrat pour tous les problèmes de santé. Elle sert de type de base pour des problèmes de santé spécifiques.
- **BoneFracture**: Une implémentation concrète de l'interface HealthIssue, représentant des problèmes de santé liés aux fractures osseuses.
- **HeartHealthIssue**: Une implémentation concrète de l'interface HealthIssue, représentant des problèmes de santé cardiaque.

#### Diagnostic

- **Diagnostic**: Il sert d'entité racine pour le domaine de diagnostic de santé.
- **HealthIndex**: Représente l'index de santé utilisé dans les diagnostics. Il garantit la validité de l'entrée de l'index de santé.
- **DiagnosticRepository**: Abstraction pour la persistance des diagnostics. Il définit une méthode pour sauvegarder des diagnostics.
- **DomainException**: Exception levée pour des opérations invalides dans le domaine. Le message d'erreur encapsulé fournit la raison de l'erreur métier.

### Couche d'application

#### Commandes

- **CreateDiagnosticCommand**: Commande pour créer des diagnostics basés sur l'index de santé. Il encapsule l'entrée requise pour créer un diagnostic.
- **CreateDiagnosticCommandHandler**: Gestionnaire pour exécuter la commande de création de diagnostic. Il valide l'entrée, crée un diagnostic et publie des événements en fonction du succès de l'opération.

#### Requêtes

- **DiagnosticResultReader**: Interface pour lire les résultats de diagnostic. Elle définit une méthode pour récupérer les résultats de diagnostic basés sur l'index de santé.
- **DiagnosticResultQuery**: Requête pour récupérer des résultats de diagnostic basés sur l'index de santé. Elle encapsule l'entrée requise pour interroger les résultats de diagnostic.
- **DiagnosticResultQueryHandler**: Gestionnaire pour exécuter la requête de résultat de diagnostic. Il récupère le résultat de diagnostic et publie des événements en fonction du résultat.

### Événements

- **Event**: Interface représentant un événement. Elle sert de type de base pour tous les événements de l'application.
- **EventPublisher**: Interface pour publier des événements. Elle définit une méthode pour publier des événements dans l'application.
- **DiagnosticCreated**: Événement déclenché lorsqu'un diagnostic est créé avec succès. Il contient des informations sur le nouveau diagnostic créé.
- **DiagnosticCreationFailed**: Événement déclenché lorsque la création de diagnostic échoue. Il contient des informations sur la raison de l'échec.
- **DiagnosticResultFound**: Événement déclenché lorsqu'un résultat de diagnostic est trouvé. Il contient des informations sur l'index de santé et le résultat du diagnostic.
- **DiagnosticResultNotFound**: Événement déclenché lorsqu'un résultat de diagnostic n'est pas trouvé. Il contient des informations sur l'index de santé.

### Couche d'infrastructure

#### Référentiels

- **InMemoryDiagnosticRepository**: Référentiel en mémoire pour stocker les diagnostics. Il stocke les diagnostics en mémoire à l'aide d'une map.

#### Événements

- **InMemoryPubSubMessenger**: Messagerie pub-sub en mémoire pour gérer les événements. Il permet aux composants de publier et de s'abonner aux événements au sein de l'application.
- **EventSubscriber**: Interface pour s'abonner aux événements d'un type spécifique.
- **DiagnosticCreationRequested**: Événement déclenché lorsqu'une création de diagnostic est demandée. Il contient des informations sur l'index de santé demandé pour la création de diagnostic.

#### Gestionnaires d'Événements

- **DiagnosticCreatedHandler**: Gère les conséquences de la création d'un diagnostic. Il traite l'événement de création de diagnostic et déclenche des actions en fonction du résultat.
- **DiagnosticCreationFailedHandler**: Gère les conséquences d'un échec de création de diagnostic. Il enregistre des messages d'erreur lorsque la création de diagnostic échoue.
- **DiagnosticCreationRequestedHandler**: Gère la création de diagnostics lorsqu'elle est demandée. Il traite l'événement de demande de création de diagnostic et lance le processus de création de diagnostic.
- **DiagnosticResultFoundHandler**: Gère les conséquences de la découverte d'un résultat de diagnostic. Il traite l'événement de résultat de diagnostic trouvé et déclenche des actions en fonction du résultat.
- **DiagnosticResultNotFoundHandler**: Gère les conséquences de la non-découverte d'un résultat de diagnostic. Il enregistre des messages d'erreur lorsqu'un résultat de diagnostic n'est pas trouvé.

### Logging

Cette application utilise la librairie Logback pour définir la stratégie de logging. Le fichier `logback.xml` se trouvant dans le dossier `\src\main\resources` permet de définir cette stratégie. Par défaut, elle est configurée pour écrire dans un fichier dans le dossier logs à la racine du projet.

Dans le cadre des tests, il serait judicieux de laisser le niveau de verbosité à `DEBUG` pour faciliter l'identification des anomalies. Pour la production, le niveau `ERROR` est recommandé.

## Getting Started

**Pré-requis:**

* **Java**: `version 21+`


1. Lancer la commande suivante pour build l'application:

```
mvn clean install
```


2. Lancer la commande suivante pour exécuter les tests unitaires:

```
mvn test
```


3. Lancer la commande suivante pour démarrer l'application:

```
java -jar target/health-diagnosis-0.1.0.jar
```

4. L'application permet la saisie d'un index de santé. Celle-ci retournera les unités médicales associées lorsque la touche entrée est utilisée:

```bash
3
Cardologie
5
Traumatologie
33
Cardologie
55
Traumatologie
15
Cardologie, Traumatologie
0
Cardologie, Traumatologie

```
