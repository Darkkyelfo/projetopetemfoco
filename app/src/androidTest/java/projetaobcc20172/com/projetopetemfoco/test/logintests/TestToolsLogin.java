package projetaobcc20172.com.projetopetemfoco.test.logintests;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.test.TestTools;

/**
 * Created by raul on 08/11/17.
 * Classe que armazena os métodos que interagem com a tela de login para realizar os teste.
 */
public class TestToolsLogin {

    protected static void preencherEclicar(String login, String senha){
        TestTools.digitarCampo(R.id.editText_email,login);
        TestTools.digitarCampo(R.id.editText_senha,senha);
        TestTools.clicarBotao(R.id.botao_login);
    }

}