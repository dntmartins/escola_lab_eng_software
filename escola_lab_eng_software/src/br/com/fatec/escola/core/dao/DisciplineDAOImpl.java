package br.com.fatec.escola.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fatec.escola.api.dao.DisciplineDAO;
import br.com.fatec.escola.api.entity.ClassRoom;
import br.com.fatec.escola.api.entity.Discipline;
import br.com.fatec.escola.core.service.GeradorIdService;
import br.com.spektro.minispring.core.dbmapper.ConfigDBMapper;

public class DisciplineDAOImpl implements DisciplineDAO{

	@Override
	public Discipline save(Discipline discipline) {
		Connection conn = null;
		PreparedStatement insert = null;
		try {
			conn = ConfigDBMapper.getInstance().getDefaultConnection();
			insert = conn.prepareStatement("INSERT INTO DISCIPLINE VALUES(?,?)");
			//
			Long id = GeradorIdService.getInstance().getNextId(Discipline.TABLE_NAME);
			insert.setLong(1, id);
			insert.setString(2, discipline.getName());
			insert.execute();
			return this.findById(id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Discipline findById(Long id) {
		Connection conn = null;
		PreparedStatement selectStatement = null;
		try {
			conn = ConfigDBMapper.getInstance().getDefaultConnection();
			selectStatement = conn.prepareStatement("SELECT * FROM DISCIPLINE WHERE " + Discipline.COL_PK + " = ?");
			selectStatement.setLong(1, id);
			// selectStatement.execute();
			ResultSet resultSet = selectStatement.executeQuery();
			if (!resultSet.next()) {
				return null;
			}
			Discipline discipline = new Discipline();
			discipline.setId(resultSet.getLong(1));
			discipline.setName(resultSet.getString(2));
			return discipline;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Discipline> findAll() {
		Connection conn = null;
		PreparedStatement selectStatement = null;
		List<Discipline> disciplineFound = null;
		try {
			conn = ConfigDBMapper.getInstance().getDefaultConnection();
			selectStatement = conn.prepareStatement("SELECT * DISCIPLINE " + Discipline.TABLE_NAME + ";");
			ResultSet resultado = selectStatement.executeQuery();
			disciplineFound = new ArrayList<Discipline>();
			while (resultado.next()) {
				Discipline discipline = new Discipline();
				discipline.setId(resultado.getLong(ClassRoom.COL_PK));
				discipline.setName(resultado.getString(ClassRoom.COL_NAME));
				disciplineFound.add(discipline);
			}
			selectStatement.close();
			conn.close();

		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar disciplinas no sistema.", e);
		}
		return disciplineFound;
	}

	@Override
	public Discipline update(Discipline discipline) {
		Connection conn = null;
		PreparedStatement update = null;
		try {
			conn = ConfigDBMapper.getInstance().getDefaultConnection();
			update = conn.prepareStatement("UPDATE " + Discipline.TABLE_NAME + " SET " + Discipline.COL_NAME + " = ?,"
					+  " WHERE " + Discipline.COL_PK + " = ?;");
			update.setString(1, discipline.getName());
			update.setLong(4, discipline.getId());
			update.execute();
			conn.close();
			return this.findById(discipline.getId());
		} catch (SQLException e) {
			throw new RuntimeException("erro ao atualizar a disciplina:" + discipline.getId());
		}
	}

	@Override
	public Boolean delete(Discipline discipline) {
		Connection conn = null;
		PreparedStatement selectStatement = null;
		try {
			conn = ConfigDBMapper.getInstance().getDefaultConnection();
			selectStatement = conn
					.prepareStatement("DELETE FROM " + Discipline.TABLE_NAME + " WHERE " + Discipline.COL_PK + " = ?;");
			selectStatement.setLong(1, discipline.getId());
			return selectStatement.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
