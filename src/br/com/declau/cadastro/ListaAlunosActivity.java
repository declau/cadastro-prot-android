package br.com.declau.cadastro;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.com.declau.cadastro.adapter.AlunoAdapter;
import br.com.declau.cadastro.dao.AlunoDAO;
import br.com.declau.cadastro.modelo.Aluno;



public class ListaAlunosActivity extends Activity{
	private Intent intent;
	private ListView lista;
	private Aluno aluno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.listagem_alunos);
			
		lista = (ListView) findViewById(R.id.lista);
		registerForContextMenu(lista);
		
		
	     lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int posicao,
					long id) {
				
				Aluno alunoParaSerAlterado = (Aluno) adapter.getItemAtPosition(posicao);
				
				
				Intent irParaOFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
				
				irParaOFormulario.putExtra("alunoSelecionado", alunoParaSerAlterado);
				startActivity(irParaOFormulario);
				
			}
		});
	     
	     lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				
				aluno = (Aluno) adapter.getItemAtPosition(posicao);
				
				return false;
			}
		});
	}

@Override	 
    public void onResume(){
	        	 super.onResume();
					
			     carregaLista();
				}

private void carregaLista() {
	AlunoDAO dao = new AlunoDAO(this);
	 List<Aluno> estudantes = dao.getLista();
		
	 AlunoAdapter adapter = new AlunoAdapter(estudantes, this);
	 lista.setAdapter(adapter);
	 
	 
}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
       	switch (item.getItemId()) {
		case R.id.novo:
			Intent irParaFormulario = new Intent(this, FormularioActivity.class);
			Toast.makeText(ListaAlunosActivity.this,
                    "You clicked the new Student", Toast.LENGTH_LONG).show();
			startActivity(irParaFormulario);
			return false;
			//break;

		default:
			return super.onOptionsItemSelected(item);
		}
		
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		MenuItem Ligar = menu.add("Ligar");
		Ligar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				Intent irParaTelaDeDiscagem = new Intent(Intent.ACTION_CALL);
				
				Uri telefoneDoAluno = Uri.parse("tel:" + aluno.getTelefone());
				irParaTelaDeDiscagem.setData(telefoneDoAluno);
				
				startActivity(irParaTelaDeDiscagem);
				return false;
			}
		});
		
		menu.add("Enviar SMS");
		menu.add("Achar no Mapa");
		
		MenuItem site = menu.add("Navegar no site");
		site.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent abrirOSiteDoAluno = new Intent(Intent.ACTION_VIEW);
				
				
				Uri siteDoAluno =  Uri.parse("http://" + aluno.getSite());
				abrirOSiteDoAluno.setData(siteDoAluno);
				
				startActivity(abrirOSiteDoAluno);
				return false;
			}
		});
		
		
		
		MenuItem deletar = menu.add("Deletar");
		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {

				AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
				dao.deletar(aluno);
				dao.close();
				
				carregaLista();
				
				return false;
			}
		});
		
		menu.add("Enviar E-Mail");
		
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
}
