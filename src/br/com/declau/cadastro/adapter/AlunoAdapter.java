package br.com.declau.cadastro.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.declau.cadastro.R;
import br.com.declau.cadastro.modelo.Aluno;


public class AlunoAdapter extends BaseAdapter {

	private List<Aluno> estudantes;
	private Activity activity;
	private View findViewById;
	private Bitmap createScaledBitmap;

	public AlunoAdapter(List<Aluno> estudantes, Activity activity) {
		this.estudantes = estudantes;
		this.activity = activity;
		
	}

	@Override
	public int getCount() {
		return estudantes.size();
	}

	@Override
	public Object getItem(int position) {
		return estudantes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return estudantes.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = activity.getLayoutInflater();
		View linha = inflater.inflate(R.layout.item, null);
		
		Aluno aluno = estudantes.get(position);
				
		 if (position % 2 == 0) {
	            linha.setBackgroundColor(activity.getResources().
	                getColor(R.color.linha_par));
	        } 
		 
		 
						
		TextView nome = (TextView) linha.findViewById(R.id.nome);
		nome.setText(aluno.toString());
		
		ImageView foto = (ImageView) linha.findViewById(R.id.foto);
		
		if (aluno.getFoto() != null) {
			Bitmap imagem =  BitmapFactory.decodeFile(aluno.getFoto());
			Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, 100, 100, true);
			foto.setImageBitmap(imagemReduzida);
			}else {
				foto.setImageResource(R.drawable.ic_no_image);
			}
		

		return linha;
	}

}
