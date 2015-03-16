package br.com.declau.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import br.com.declau.cadastro.modelo.Aluno;



public class FormularioHelper {
	
		private EditText campoNome;
		private EditText campoSite;
		private EditText campoTelefone;
		private EditText campoEndereco;
		private RatingBar campoNota;
		private Aluno aluno;
		private ImageView campoFoto;

		public FormularioHelper(FormularioActivity activity){
			
			campoNome = (EditText) activity.findViewById(R.id.nome);
		    campoSite = (EditText) activity.findViewById(R.id.site);
		    campoTelefone = (EditText) activity.findViewById(R.id.telefone);
		    campoEndereco = (EditText) activity.findViewById(R.id.endereco);
		    campoNota = (RatingBar) activity.findViewById(R.id.nota);
		    campoFoto = (ImageView) activity.findViewById(R.id.foto);
		    
		    aluno = new Aluno();
		}
		public Aluno pegaAlunoDoFormulario() {
			
						
			String nome = campoNome.getText().toString();
			String site = campoSite.getText().toString();
			String telefone = campoTelefone.getText().toString();
			String endereco = campoEndereco.getText().toString();
			Double nota = (double) campoNota.getRating();
			
			
			
			aluno.setNome(nome);
			aluno.setSite(site);
			aluno.setTelefone(telefone);
			aluno.setEndereco(endereco);
			aluno.setNota(nota);
			
			
			return aluno;
			
		}
		public void colocaAlunoNoFormulario(Aluno alunoParaSerAlterado) {
			
			aluno = alunoParaSerAlterado;
			
			campoNome.setText(alunoParaSerAlterado.getNome());
			campoSite.setText(alunoParaSerAlterado.getSite());
			campoTelefone.setText(alunoParaSerAlterado.getTelefone());
			campoEndereco.setText(alunoParaSerAlterado.getEndereco());
			campoNota.setRating(alunoParaSerAlterado.getNota().floatValue());
			
			if (aluno.getFoto() != null) {
				carregaImagem(aluno.getFoto());
			}
		
			
		}
		public ImageView getFoto(){
			return campoFoto;
		}
		public void carregaImagem(String caminhoArquivo) {
			aluno.setFoto(caminhoArquivo);
			
			Bitmap imagem = BitmapFactory.decodeFile(caminhoArquivo);
			Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, 100, 100, true);
			
			aluno.setFoto(caminhoArquivo);
			campoFoto.setImageBitmap(imagemReduzida);
			
		}
	    

}
