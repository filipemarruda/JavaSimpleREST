package meubar.servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import meubar.business.exceptions.DBException;
import meubar.cadastro.model.dao.UsuarioDAO;
import meubar.model.dao.BaseDAO;
import meubar.model.entity.BaseEntity;

import org.apache.commons.lang3.exception.ExceptionUtils;

@Stateless
public abstract class ServicoBase<S extends BaseDAO<T, Long>, T extends BaseEntity<Long>, J> {

	@EJB
	private UsuarioDAO usuarioDAO;

	// This method need to be overrided
	protected abstract S getItemDao();

	// This method need to be overrided
	protected abstract T createItemFromJson(J itemJson);

	// This method need to be overrided
	protected abstract T fillUpdatableFields(T item, J itemJson);

	// This method need to be overrided
	protected abstract J createItemJson(T item);

	public J getById(String id) throws DBException {
		try {
			long lId = Long.parseLong(id);
			T item = (T) getItemDao().findById(lId);
			return createItemJson(item);
		} catch (Exception e) {
			throw new DBException(ExceptionUtils.getRootCauseMessage(e));
		}
	}

	public List<J> getAll() throws DBException {
		try {

			List<T> results = getItemDao().findAll(null);
			List<J> itens = new ArrayList<>();
			for (T item : results) {
				J itemJson = createItemJson(item);
				itens.add(itemJson);
			}
			return itens;

		} catch (Exception e) {
			throw new DBException(ExceptionUtils.getRootCauseMessage(e));
		}
	}

	public T cadastrar(J itemJson) throws DBException {

		T item = createItemFromJson(itemJson);
		try {
			return getItemDao().save(item);
		} catch (Exception e) {
			throw new DBException(ExceptionUtils.getRootCauseMessage(e));
		}

	}

	public boolean deletar(String id) throws DBException {

		boolean result = false;
		long lId = Long.parseLong(id);
		T item = (T) getItemDao().findById(lId);

		if (item != null) {
			try {
				getItemDao().remove(item);
				result = true;
			} catch (Exception e) {
				throw new DBException(ExceptionUtils.getRootCauseMessage(e));
			}
		}
		return result;

	}

	public boolean update(String id, J itemJson) throws DBException {

		boolean result = false;
		long lId = Long.parseLong(id);
		T item = getItemDao().findById(lId);

		if (item != null) {
			item = fillUpdatableFields(item, itemJson);
			try {
				getItemDao().save(item);
				result = true;
			} catch (Exception e) {
				throw new DBException(ExceptionUtils.getRootCauseMessage(e));
			}

		}

		return result;

	}
	
	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}
	
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
}
