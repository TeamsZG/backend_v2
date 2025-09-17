DELETE FROM Person;
DELETE FROM series;

insert into person (name, age, gender) values ('Shandee Tatters', 30, 'Male');
insert into person (name, age, gender) values ('Sylas Northall', 32, 'Male');
insert into person (name, age, gender) values ('Leanora McGow', 72, 'Female');
insert into person (name, age, gender) values ('Taryn Alekseev', 35, 'Female');
insert into person (name, age, gender) values ('Norma Buntin', 44, 'Female');
insert into person (name, age, gender) values ('Cissy Larmett', 45, 'Female');
insert into person (name, age, gender) values ('Janelle Nuccii', 49, 'Female');
insert into person (name, age, gender) values ('Vite Cucuzza', 29, 'Male');
insert into person (name, age, gender) values ('Briggs Burdas', 49, 'Male');
insert into person (name, age, gender) values ('Marylin Blazek', 51, 'Female');
insert into person (name, age, gender) values ('Sofia Barrar', 43, 'Female');
insert into person (name, age, gender) values ('Farra Devey', 25, 'Female');
insert into person (name, age, gender) values ('Evelin Fairlaw', 61, 'Male');
insert into person (name, age, gender) values ('Alanson Iannelli', 71, 'Male');
insert into person (name, age, gender) values ('Chris Garcia', 32, 'Male');
insert into person (name, age, gender) values ('Guillermo McKeighen', 55, 'Male');
insert into person (name, age, gender) values ('Cliff Ruby', 27, 'Male');
insert into person (name, age, gender) values ('Luis Braunstein', 64, 'Male');
insert into person (name, age, gender) values ('Anet Czajka', 26, 'Female');
insert into person (name, age, gender) values ('Eda Houlison', 69, 'Female');


-- Généré par chatgpt pour des données plus réaliste

INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Breaking Future', 'Science Fiction', 45, 8.7);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Love in Paris', 'Romance', 12, 6.5);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Crime Files', 'Thriller', 24, 7.9);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Dragon Warriors', 'Fantasy', 60, 9.2);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Office Madness', 'Comedy', 36, 7.1);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('The Hacker Code', 'Techno-Thriller', 20, 8.3);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Desert Storms', 'Action', 15, 6.8);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Ghost Hunters', 'Horror', 18, 7.4);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Royal Blood', 'Drama', 50, 8.0);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Galaxy Patrol', 'Science Fiction', 25, 8.6);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Cooking Wars', 'Reality', 40, 6.9);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Street Dancers', 'Musical', 22, 7.2);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('The Silent Truth', 'Mystery', 30, 8.1);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Legends of Kyoto', 'Historical', 16, 8.8);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Cyber Dreams', 'Sci-Fi', 28, 7.7);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Wild Planet', 'Documentary', 12, 9.0);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Underworld Kings', 'Crime', 42, 8.2);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Pixel Quest', 'Animation', 55, 8.5);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Love & Lies', 'Drama', 14, 6.7);
INSERT INTO series (title, genre, nb_episodes, note) VALUES ('Dark Eclipse', 'Horror', 10, 7.8);


-- Généré par chatgpt
INSERT INTO historique (person_id, series_id) VALUES (15, 1);
INSERT INTO historique (person_id, series_id) VALUES (15, 2);
INSERT INTO historique (person_id, series_id) VALUES (15, 4);
INSERT INTO historique (person_id, series_id) VALUES (15, 10);
INSERT INTO historique (person_id, series_id) VALUES (11, 4);
INSERT INTO historique (person_id, series_id) VALUES (11, 14);
INSERT INTO historique (person_id, series_id) VALUES (11, 17);
INSERT INTO historique (person_id, series_id) VALUES (8, 8);
INSERT INTO historique (person_id, series_id) VALUES (8, 9);
INSERT INTO historique (person_id, series_id) VALUES (8, 20);
INSERT INTO historique (person_id, series_id) VALUES (1, 6);
INSERT INTO historique (person_id, series_id) VALUES (1, 7);
INSERT INTO historique (person_id, series_id) VALUES (1, 17);
INSERT INTO historique (person_id, series_id) VALUES (2, 1);
INSERT INTO historique (person_id, series_id) VALUES (2, 10);
INSERT INTO historique (person_id, series_id) VALUES (2, 18);
INSERT INTO historique (person_id, series_id) VALUES (5, 2);
INSERT INTO historique (person_id, series_id) VALUES (5, 9);
INSERT INTO historique (person_id, series_id) VALUES (5, 19);
INSERT INTO historique (person_id, series_id) VALUES (13, 16);
INSERT INTO historique (person_id, series_id) VALUES (13, 11);
INSERT INTO historique (person_id, series_id) VALUES (17, 3);
INSERT INTO historique (person_id, series_id) VALUES (17, 8);
INSERT INTO historique (person_id, series_id) VALUES (17, 20);
INSERT INTO historique (person_id, series_id) VALUES (14, 14);
INSERT INTO historique (person_id, series_id) VALUES (14, 17);
INSERT INTO historique (person_id, series_id) VALUES (12, 2);
INSERT INTO historique (person_id, series_id) VALUES (12, 12);


-- Avec SQLite, il faut fermer la connexion à la base de données et la remettre afin de pouvoir rafraîchir le schéma ou recharger les données. (Note pour l'équipe)
