package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		String user = "student";
		String pass = "student";
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sqltxt;
			sqltxt = "SELECT film.id, film.title, film.description, film.release_year, film.language_id, "
					+ "film.rental_duration, film.rental_rate, film.length, film.replacement_cost, "
					+ "film.rating, film.special_features, language.name "
					+ "FROM film JOIN language ON film.language_id = language.id AND film.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sqltxt);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				Film film = new Film(Integer.parseInt(rs.getString("id")), 
						rs.getString("title"), 
						rs.getString("description"), 
						rs.getInt("release_year"), 
						Integer.parseInt(rs.getString("language_id")), 
						rs.getString("name"), 
						Integer.parseInt(rs.getString("rental_duration")),
						Double.parseDouble(rs.getString("rental_rate")),
						Integer.parseInt(rs.getString("length")),
						Double.parseDouble(rs.getString("replacement_cost")),
						rs.getString("rating"),
						rs.getString("special_features"),
						findActorsByFilmId(filmId));
				
				rs.close();
			    stmt.close();
			    conn.close();
				return film;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Actor findActorById(int actorId) {
		String user = "student";
		String pass = "student";
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sqltxt;
			sqltxt = "SELECT * FROM actor WHERE actor.id =?";

			PreparedStatement stmt = conn.prepareStatement(sqltxt);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();

			if(rs.next()) {
				Actor actor = new Actor( 
						rs.getInt("id"), 
						rs.getString("first_name"), 
						rs.getString("last_name"));
				
				rs.close();
				stmt.close();
				conn.close();
				return actor;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		String user = "student";
		String pass = "student";
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sqltxt;
			sqltxt = "SELECT actor.id, actor.first_name, actor.last_name "
					+ "FROM actor JOIN film_actor ON actor.id = film_actor.actor_id "
					+ "AND film_actor.film_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sqltxt);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			
			List<Actor> actorList = new ArrayList<>();			

			while(rs.next()) {
				actorList.add(new Actor(rs.getInt("id"), 
						rs.getString("first_name"), 
						rs.getString("last_name")));
			}
				
				rs.close();
				stmt.close();
				conn.close();
				return actorList;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
