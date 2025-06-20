package rs.ac.uns.ftn.db.school.service;

import java.sql.SQLException;
import java.util.ArrayList;

import rs.ac.uns.ftn.db.school.dao.InstrumentDAO;
import rs.ac.uns.ftn.db.school.dao.impl.InstrumentDAOImpl;
import rs.ac.uns.ftn.db.school.model.Instrument;

public class InstrumentService {
	
	private static final InstrumentDAO instrumentDAO = new InstrumentDAOImpl();

	public ArrayList<Instrument> getAll() throws SQLException {
		return (ArrayList<Instrument>) instrumentDAO.findAll();
	}
	
	public Instrument getById(int id) throws SQLException {
		return instrumentDAO.findById(id);
	}

}
