package br.com.declau.cadastro.dao;




import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.declau.cadastro.modelo.Aluno;


public class AlunoDAO extends SQLiteOpenHelper {

	private static final String DATABASE = "NomeDoBanco";
	private static final int VERSAO = 1;
	private static final String TABELA = "Alunos";
	
	public AlunoDAO(Context ctx) {
		super(ctx, DATABASE, null, VERSAO);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database){
		
		String sql = "CREATE TABLE " + TABELA + " ("
		        + "id INTEGER PRIMARY KEY, "
				+ "nome TEXT UNIQUE NOT NULL, "
		        + "site TEXT, "
				+ "telefone TEXT, "
				+ "endereco TEXT, "
				+ "nota REAL, "
				+ "caminhoFOTO TEXT"
				+ ");";
		
		database.execSQL(sql);
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
				String sql = "DROP TABLE IF EXISTS" + TABELA;
		        database.execSQL(sql);
		        onCreate(database);
	}

	public void insere(Aluno aluno) {

		ContentValues cv = new ContentValues();
		cv.put("nome", aluno.getNome());
		cv.put("site", aluno.getSite());
		cv.put("telefone", aluno.getTelefone());
		cv.put("endereco", aluno.getEndereco());
		cv.put("nota", aluno.getNota());
		cv.put("caminhoFOTO", aluno.getFoto());
		
		getWritableDatabase().insert(TABELA, null, cv);
	}

	public List<Aluno> getLista() {
		
		List<Aluno> alunos = new ArrayList<Aluno>();
		String sql = "SELECT * FROM " + TABELA + ";";
		Cursor c = getReadableDatabase().rawQuery(sql, null);
		
		
		while (c.moveToNext()) {
			Aluno aluno = new Aluno();
			aluno.setId(c.getLong(c.getColumnIndex("id")));
			aluno.setNome(c.getString(c.getColumnIndex("nome")));
			aluno.setSite(c.getString(c.getColumnIndex("site")));
			aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
			aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
			aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
			aluno.setFoto(c.getString(c.getColumnIndex("caminhoFOTO")));
			
			alunos.add(aluno);
		}
		
		return alunos;
	}

	public void deletar(Aluno aluno) {
		String[] args = new String[] {String.valueOf(aluno.getId())};
		getWritableDatabase().delete(TABELA,"id=?", args);
		
	}

	public void atualizar(Aluno aluno) {

		ContentValues cv = new ContentValues();
		cv.put("nome", aluno.getNome());
		cv.put("site", aluno.getSite());
		cv.put("telefone", aluno.getTelefone());
		cv.put("endereco", aluno.getEndereco());
		cv.put("nota", aluno.getNota());
		cv.put("caminhoFOTO", aluno.getFoto());
		
		
		String[] args = {String.valueOf(aluno.getId())};
		getWritableDatabase().update(TABELA, cv, "id=?", args );
	}
	    	
}
