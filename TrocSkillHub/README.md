# TrocSkillHub_Back-end

Est une plateforme d'échange de compétences entre particuliers - API REST Back-end.

## Stack technique

| Composant | Version |
|-----------|---------|
| Java | 21 |
| Spring Boot | 3.4.11 |
| PostgreSQL | 16+ |
| Flyway | 10.21.0 |
| MapStruct | 1.5.5 |
| Maven | 3.8+ |

## Prérequis

- Docker Desktop installé et démarré
- Java 21 (pour le développement local)
- Maven 3.8+ (pour le développement local)

## Démarrage rapide

### 1. Configurer les variables d'environnement

Créer un fichier `.env` à la racine du projet :
```dotenv
POSTGRES_DB=*****
POSTGRES_USER=*****
POSTGRES_PASSWORD=*****
```

> ⚠️ Ne jamais commiter le fichier `.env` (ajoutez-le au `.gitignore`)

### 2. Lancer l'application
```bash
# 1.Aller dans le dossier TrocSkillHub
# 2.Démarrer les conteneurs
docker compose up

# Vérifier que l'application fonctionne
# http://localhost:8080
```

### 3. Rebuild après modification du code
```bash
docker compose up --build
```

### 4. Arrêter l'application
```bash
# Arrêt simple (conserve les données)
docker compose down

# Arrêt avec suppression des volumes (reset BDD)
docker compose down -v
```

## Base de données

### Configuration

| Paramètre | Valeur |
|-----------|--------|
| Host | localhost (dev) / postgres (docker) |
| Port | 5432 |
| Database | trocskillhubdb |
| User | défini dans `.env` |
| Password | défini dans `.env` |

### Migrations Flyway

Les migrations SQL se trouvent dans `src/main/resources/db/migration/`.

**Convention de nommage** : `V{version}__{description}.sql`

Exemples :
- `V1__create_users_table.sql`
- `V2__create_skills_table.sql`

**Après chaque nouvelle migration** :
```bash
docker compose down -v
docker compose up --build
```

**Vérifier le statut des migrations** :
```bash
./mvnw flyway:info
```

## Structure du projet
```
src/
├── main/
│   ├── java/
│   │   └── RNCP/TrocSkillHub/
│   │       ├── controllers/    # Endpoints REST
│   │       ├── services/       # Logique métier
│   │       ├── repositories/    # Interfaces JPA
│   │       ├── models/         # Entités JPA
│   │       ├── dtos/           # Objets de transfert
│   │       └── mappers/        # Mappers MapStruct
│   └── resources/
│       ├── application.yml
│       └── db/migration/      # Scripts Flyway
└── test/
```
## API Endpoints

### Users

| Méthode | Endpoint | Description | Corps de requête | Réponse |
|---------|----------|-------------|------------------|---------|
| GET | `/api/users` | Liste tous les utilisateurs | - | `200` : Liste de UserDTO |
| GET | `/api/users/{id}` | Récupère un utilisateur | - | `200` : UserDTO / `404` |
| POST | `/api/users` | Crée un utilisateur | UserDTO | `201` : UserDTO / `409` |
| PUT | `/api/users/{id}` | Met à jour un utilisateur | UserDTO | `200` : UserDTO / `404` |
| DELETE | `/api/users/{id}` | Supprime un utilisateur | - | `200` / `404` |

### Exemples de requêtes

**Récupérer tous les utilisateurs**
```bash
curl http://localhost:8080/api/users
```


**Récupérer un utilisateur par ID**
```bash
curl http://localhost:8080/api/users/1
```

Réponse :
```json
[
  {
    "id": 1,
    "firstName": "Jean",
    "lastName": "Martin",
    "picture": null,
    "email": "jean.martin@example.fr",
    "address": "42 avenue des Champs",
    "city": "Lyon",
    "country": "France",
    "phoneNumber": "+33 6 23 45 67 89",
    "description": "Graphiste freelance, j'aime créer des designs modernes et épurés."
  }
]
```
**Créer un utilisateur**
exemple compe user pour le dev
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json; charset=utf-8" \
  -d '{"firstName":"Admin","lastName":"Dev","email":"admin@admin.fr","password":"Adminadmin12@","address":"1 rue Admin","city":"Paris","country":"France","phoneNumber":"+33 1 00 00 00 00","description":"Compte admin dev"}'
```

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json; charset=utf-8" \
  -d '{"firstName":"Marie","lastName":"Curie","picture":null,"email":"marie.curie@example.fr","password":"MonMotDePasse123","address":"12 rue de la Science","city":"Paris","country":"France","phoneNumber":"+33 6 98 76 54 32","description":"Passionnee de physique et chimie."}'
```

Réponse (201 Created) :
```json
{
  "id": 3,
  "firstName": "Marie",
  "lastName": "Curie",
  "picture": null,
  "email": "marie.curie@example.fr",
  "address": "12 rue de la Science",
  "city": "Paris",
  "country": "France",
  "phoneNumber": "+33 6 98 76 54 32",
  "description": "Passionnee de physique et chimie."
}
```

> 🔒 Le champ `password` est accepté en entrée mais jamais retourné dans les réponses.

**Mettre à jour un utilisateur**
```bash
curl -X PUT http://localhost:8080/api/users/5 \
  -H "Content-Type: application/json; charset=utf-8" \
  -d '{"firstName":"Marie","lastName":"Curie-Sklodowska","picture":null,"email":"marie.curie@example.fr","password":"NouveauMotDePasse456","address":"15 rue Pierre et Marie Curie","city":"Paris","country":"France","phoneNumber":"+33 6 98 76 54 32","description":"Physicienne et chimiste, double prix Nobel."}'
```

Réponse (200 OK) :
```json
{
  "id": 5,
  "firstName": "Marie",
  "lastName": "Curie-Sklodowska",
  "picture": null,
  "email": "marie.curie@example.fr",
  "address": "15 rue Pierre et Marie Curie",
  "city": "Paris",
  "country": "France",
  "phoneNumber": "+33 6 98 76 54 32",
  "description": "Physicienne et chimiste, double prix Nobel."
}
```

**Supprimer un utilisateur**
```bash
curl -X DELETE http://localhost:8080/api/users/5
```

Réponse (200 OK) :
```
Utilisateur supprimé avec succès
```

**Rechercher par ville**
```bash
curl http://localhost:8080/api/users/city/Paris
```

**Rechercher par pays**
```bash
curl http://localhost:8080/api/users/country/France
```

### Structure UserDTO

| Champ | Type | Requis | Description |
|-------|------|--------|-------------|
| id | Long | Non | Auto-généré |
| firstName | String | Oui | Prénom |
| lastName | String | Oui | Nom |
| picture | String | Non | URL de la photo |
| email | String | Oui | Email (unique) |
| password | String | Oui* | Mot de passe (write-only) |
| address | String | Oui | Adresse |
| city | String | Oui | Ville |
| country | String | Oui | Pays |
| phoneNumber | String | Oui | Téléphone |
| description | String | Non | Biographie |

> *Le password est requis à la création, jamais retourné dans les réponses.

### Codes de réponse

| Code | Signification |
|------|---------------|
| 200 | Succès |
| 201 | Ressource créée |
| 404 | Ressource non trouvée |
| 409 | Conflit (email déjà utilisé) |

## MapStruct

Les mappers convertissent les entités en DTOs et vice-versa.

Exemple de mapper :
```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO dto);
}
```

## Tests
```bash
# Lancer tous les tests
./mvnw test

# Lancer un test spécifique
./mvnw test -Dtest=NomDuTest
```

## Commandes Maven utiles
```bash
# Compiler sans tests
./mvnw clean package -DskipTests

# Vérifier les dépendances
./mvnw dependency:tree

# Mettre à jour les dépendances
./mvnw versions:display-dependency-updates
```

## Dépannage

| Problème | Solution |
|----------|----------|
| Le conteneur ne démarre pas | Vérifier que Docker Desktop est lancé |
| Port 8080 déjà utilisé | `lsof -i :8080` puis `kill -9 <PID>` |
| Erreur de connexion BDD | `docker compose down -v` puis `docker compose up --build` |
| Migration Flyway échoue | Vérifier la syntaxe SQL et le nommage du fichier |
| MapStruct ne génère pas | `./mvnw clean compile` |

## Contribution

1. Créer une branche depuis `main` : `git checkout -b feature/ma-fonctionnalite`
2. Commiter avec des messages clairs
3. Pousser et ouvrir une Pull Request vers `develop`

## Auteurs

- Sarra.B & Molid.A

## Licence

Projet RNCP niveau 6 - Ada tech School Nantes 2026-2027



## commande pour lancer le container postegresql_test
docker compose -f docker-compose.test.yml up -d
## arreter le container postegresql_test et les volumes
docker compose -f docker-compose.test.yml down -v