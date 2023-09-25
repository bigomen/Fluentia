package br.com.fluentia.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePasswordUtils {

	
	public static String gerarSenha() {
		
		int qtdeMaximaCaracteres = 12;
		String[] caracteres = { "0", "1", "b", "2", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

		StringBuilder senha = new StringBuilder();

		for (int i = 0; i < qtdeMaximaCaracteres; i++) {
			int posicao = (int) (Math.random() * caracteres.length);
			senha.append(caracteres[posicao]);
		}
		return(senha.toString());

	}
	
	public static String crypt(String password) {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		return(bCrypt.encode(password));

	}

}
