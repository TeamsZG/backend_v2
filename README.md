# 🚀 Initialisation Project – Backend Spring Boot

> Une **API RESTful** développée avec **Spring Boot**, **JPA** et une base de données **SQLite** locale.  
> Le projet permet de démarrer rapidement un backend Java prêt à gérer des entités, avec persistance via **Hibernate**.

---

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=openjdk" />
  <img src="https://img.shields.io/badge/Spring Boot-3.5.5-brightgreen?style=for-the-badge&logo=springboot" />
  <img src="https://img.shields.io/badge/SQLite-DB-blue?style=for-the-badge&logo=sqlite" />
  <img src="https://img.shields.io/badge/Postman-Test-orange?style=for-the-badge&logo=postman" />
</p>

---

## ⚙️ Prérequis

- ✅ **Java 17**  
- ✅ **Maven**  
- ✅ **Postman** ou tout autre client HTTP  
- ✅ **IDE** (IntelliJ, Eclipse, VSCode...) recommandé  

---

## 🚀 Démarrer le projet

```bash
# 1. Cloner le dépôt
git clone https://github.com/TeamsZG/backend_v2.git
git checkout developp

# 2. Lancer le backend
Ouvrir le projet dans votre IDE favori et lancer la classe principale via le bouton Run  
L'application démarre sur : http://localhost:9594
```
## 📚 Endpoints disponibles

### 👤 Personnes

| ⚡ Méthode | 🌍 Route                          | 📝 Description                                             |
|-----------|----------------------------------|------------------------------------------------------------|
| **GET**   | `/persons`                       | Obtenir la liste de toutes les personnes                   |
| **GET**   | `/persons/search?name=Sylas northall`      | Rechercher une personne par nom                            |
| **POST**  | `/persons`                       | ➕ Ajouter une nouvelle personne                            |
| **PUT**   | `/persons/{id}`                  | 🔄 Modifier une personne par son ID                        |
| **DELETE**| `/persons/{id}`                  | ❌ Supprimer une personne par son ID                       |
| **GET**   | `/persons/{id}/history`          | 📜 Obtenir l'historique de visionnage d'une personne       |
| **POST**  | `/persons/{id}/history/{seriesId}` | ➕ Ajouter une série à l'historique d'une personne         |

---

### 🎬 Séries

| ⚡ Méthode | 🌍 Route                                               | 📝 Description                                             |
|-----------|--------------------------------------------------------|------------------------------------------------------------|
| **GET**   | `/series`                                              | Lister toutes les séries                                  |
| **GET**   | `/series/{id}`                                         | Obtenir les détails d'une série par ID                    |
| **POST**  | `/series`                                              | ➕ Ajouter une nouvelle série                              |
| **PUT**   | `/series/{id}`                                         | 🔄 Modifier une série existante                           |
| **DELETE**| `/series/{id}`                                         | ❌ Supprimer une série par ID                             |
| **GET**   | `series/search?genre=Science Fiction&title=Breaking Future&minEpisode=10` | 🔍 Rechercher des séries par genre, titre ou nb épisodes  |

---

### 🤖 Recommandations

| ⚡ Méthode | 🌍 Route                                 | 📝 Description                                                       |
|-----------|------------------------------------------|----------------------------------------------------------------------|
| **GET**   | `/user/top3genre`                        | 🎯 Obtenir les 3 genres préférés (globaux)                          |
| **GET**   | `/user/{id}/recommendations`             | 🤝 Obtenir des recommandations personnalisées pour un utilisateur   |
| **GET**   | `/user/{id}/recommendations/criteria`    | 📊 Recommandations selon critères de l'utilisateur                  |
| **GET**   | `/user/{id}/recommendations/similar`     | 🧠 Recommandations selon utilisateurs similaires                    |

---

## 👥 Équipe

### Backend

- **Juba Redjradj**  
- **Chadi El-Chami**  

### Frontend

- **Nassim El Haji**  
- **Jayden-Isaiah Saint-Cyr**  

---

*Merci à toute l'équipe pour leur contribution à ce projet ! 🚀*




