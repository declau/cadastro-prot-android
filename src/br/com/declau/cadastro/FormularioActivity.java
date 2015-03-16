package br.com.declau.cadastro;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.declau.cadastro.dao.AlunoDAO;
import br.com.declau.cadastro.modelo.Aluno;



public class FormularioActivity extends Activity {
	
	private String CaminhoArquivo;
	private FormularioHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.formulario);
			
			helper = new FormularioHelper(this);
			
			final Aluno alunoParaSerAlterado = (Aluno) getIntent().getSerializableExtra("alunoSelecionado");
			
			if(alunoParaSerAlterado != null){
				
				helper.colocaAlunoNoFormulario(alunoParaSerAlterado);
			}
			
			final Button botao = (Button) findViewById(R.id.botao);				
            botao.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                	
                	Aluno aluno = helper.pegaAlunoDoFormulario(); 

                    Toast.makeText(FormularioActivity.this, aluno.getNome()+" was registered ",
                    Toast.LENGTH_LONG).show();
                                     
                                        
                    AlunoDAO dao = new AlunoDAO(FormularioActivity.this); 
                    
                    if (alunoParaSerAlterado != null) {
                    	//atualizar
                    	aluno.setId(alunoParaSerAlterado.getId());
                    	botao.setText("Alterar");
                    	dao.atualizar(aluno);
						
					}else {
						dao.insere(aluno);	
					}
                    
                    
                    dao.close();

                    finish();
                }
            });
            
            ImageView foto = helper.getFoto();
            foto.setOnClickListener(new OnClickListener() {
				
				
				@Override
				public void onClick(View v) {
					
					Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					
					CaminhoArquivo = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
					File arquivo = new File(CaminhoArquivo);
					
					Uri localFoto = Uri.fromFile(arquivo);
					irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
					
					startActivityForResult(irParaCamera, 2000);
					
				}
			});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == 2000) {
			if (resultCode == Activity.RESULT_OK) {
				helper.carregaImagem(this.CaminhoArquivo);
			}else {
				this.CaminhoArquivo = null; 
			}
		}

	}

}
