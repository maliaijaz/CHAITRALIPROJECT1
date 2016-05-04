CREATE TABLE `moviedb`.`movies` (
	id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title varchar(100) NOT NULL,
    year int NOT NULL,
    director varchar(100) NOT NULL,
    banner_url varchar(200),
    trailer_url varchar(200)	
);

CREATE TABLE `moviedb`.`stars` (
	id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
	dob date,
    photo_url varchar(200)
);

CREATE TABLE `moviedb`.`stars_in_movies` (
	star_id int NOT NULL,
    movie_id int NOT NULL,
    FOREIGN KEY (star_id) REFERENCES stars(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);


CREATE TABLE `moviedb`.`genres` (
	id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(32) NOT NULL	
);

CREATE TABLE `moviedb`.`genres_in_movies` (
	genre_id INT NOT NULL,
    movie_id INT NOT NULL,
    FOREIGN KEY (genre_id) REFERENCES genres(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);



CREATE TABLE `moviedb`.`creditcards` (
	id varchar(20) PRIMARY KEY NOT NULL,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    expiration date NOT NULL
);

CREATE TABLE `moviedb`.`customers` (
	id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    cc_id varchar(20) NOT NULL,
    address varchar(200) NOT NULL,
    email varchar(50) NOT NULL,
    password varchar(20) NOT NULL,
    FOREIGN KEY (cc_id) REFERENCES creditcards(id)	
);

CREATE TABLE `moviedb`.`sales` (
	id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    customer_id int NOT NULL,
    movie_id int NOT NULL,
    sale_date date NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)	
);
