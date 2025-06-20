package rs.ac.uns.ftn.db.school.dao;

import java.sql.Connection;
import java.sql.SQLException;

import rs.ac.uns.ftn.db.school.model.Application;

public interface ApplicationDAO extends CRUDDao<Application, Integer> {
	public boolean deleteById(Integer korisnikId, Integer kursId) throws SQLException;
	public boolean existsByIdTransactional(Connection connection, Integer korisnikId, Integer kursId) throws SQLException;



}
