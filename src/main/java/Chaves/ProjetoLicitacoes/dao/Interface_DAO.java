package Chaves.ProjetoLicitacoes.dao;

import java.util.List;

public interface Interface_DAO <O> {
	public boolean insert(O object);
	public boolean delete(int id);
	public boolean update(O object);
	public List<O> list(int limit, int offset);
	public O get(int id);
}
