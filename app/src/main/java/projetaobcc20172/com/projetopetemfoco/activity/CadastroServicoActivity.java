package projetaobcc20172.com.projetopetemfoco.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.database.services.ServicoDaoImpl;
import projetaobcc20172.com.projetopetemfoco.excecoes.ValidacaoException;
import projetaobcc20172.com.projetopetemfoco.model.Servico;
import projetaobcc20172.com.projetopetemfoco.utils.MascaraDinheiro;
import projetaobcc20172.com.projetopetemfoco.utils.Utils;
import projetaobcc20172.com.projetopetemfoco.utils.VerificadorDeObjetos;

/**
 * Activity de cadastro de serviços
 */
public class CadastroServicoActivity extends AppCompatActivity {

    private EditText mEtNome, mEtValor, mEtDescricao;
    private String mIdUsuarioLogado;
    private Servico mServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_servico);

        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_cadastro_serviço);

        // Configura toolbar
        toolbar.setTitle(R.string.tb_cadastro_serviço);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left_white);
        setSupportActionBar(toolbar);

        mEtNome = findViewById(R.id.etCadastroNomeServico);
        mEtValor = findViewById(R.id.etCadastroValorServico);
        mEtDescricao = findViewById(R.id.etCadastroDescricaoServico);

        Locale mLocal = new Locale("pt", "BR");

        mEtValor.addTextChangedListener(new MascaraDinheiro(mEtValor, mLocal));

        Button btnCadastrar = findViewById(R.id.btnSalvarServico);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarServico();
            }
        });
    }

    private boolean verificarCamposPreenchidos(){
        return (!mEtNome.getText().toString().isEmpty() ||
                !mEtValor.getText().toString().isEmpty()||
                !mEtDescricao.getText().toString().isEmpty());
    }

    private void salvarServico(){
        try {

            //Recuperar id do fornecedor logado
            mIdUsuarioLogado = getPreferences("idFornecedor", CadastroServicoActivity.this);

            mServico = new Servico(mEtNome.getText().toString(), mEtValor.getText().toString());
            VerificadorDeObjetos.vDadosServico(mServico,this);
            //Chamada do DAO para salvar no banco
            ServicoDaoImpl servicoDao =  new ServicoDaoImpl(this);
            servicoDao.inserir(mServico, mIdUsuarioLogado);
            abrirTelaPrincipal();

        } catch (ValidacaoException e) {
            e.printStackTrace();
            Utils.mostrarMensagemCurta(this, e.getMessage());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Método do botão voltar
    @Override
    public void onBackPressed(){
        if (verificarCamposPreenchidos()) confirmarSaida();
        else CadastroServicoActivity.super.onBackPressed();
    }

    //Método que exibe pergunta de confirmação ao fornecedor caso ele clique no botão de voltar com as
    //informações do serviço inseridas nos campos
    public void confirmarSaida(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        // Botão sim foi clicado
                        CadastroServicoActivity.super.onBackPressed();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // Botão não foi clicado
                        break;
                    default:
                        break;
                }
            }
        };

        Utils.mostrarPerguntaSimNao(this, getString(R.string.atencao),
                    getString(R.string.pergunta_confirma_dados_serao_perdidos), dialogClickListener,
                    dialogClickListener);
    }

    //Método que recupera o id do usuário logado, para salvar o mPet no nó do usuário que o está cadastrando
    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(CadastroServicoActivity.this, MainActivityFornecedor.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
