package projetaobcc20172.com.projetopetemfoco.adapter;

/**
 * Created by dario on 11/12/2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.model.Servico;

//Classe que monta uma View para exibir os serviços cadastrados do fornecedor
public class ServicoAdapter extends ArrayAdapter<Servico> {

    private ArrayList<Servico> mServicos;
    private Context mContext;

    public ServicoAdapter(Context c, ArrayList<Servico> objects) {
        super(c, 0, objects);
        this.mContext = c;
        this.mServicos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View view = null;

        // Verifica se a lista está preenchida
        if (mServicos != null) {

            // inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            assert inflater != null;
            view = inflater.inflate(R.layout.lista_itens, parent, false);

            // recupera elemento para exibição
            TextView nome = view.findViewById(R.id.tv_titulo);
            TextView tipo = view.findViewById(R.id.tv_subtitulo);

            Servico servico = mServicos.get(position);
            nome.setText(servico.getNome());
            String valorConvertido = servico.getValor();
            tipo.setText(valorConvertido);

        }

        return view;
    }
}

