import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class ConsultaBooleana {

	/**
	 * Caso a consulta seja de apenas uma palavra essa função é chamada.
	 * Procura-se na hash a palavra e caso a encontre retorna seus documentos
	 * 
	 * @param palavraBusca
	 * @return
	 */
	public String consultaBooleana1(String palavraBusca) {
		ArrayList<Documento> aux = new ArrayList<Documento>();
		String imprime = "";
		for (Palavra palavra : Index.hashList) {
			if (palavra.getNome().equals(palavraBusca)) {
				aux = palavra.getDocumentos();

				for (Documento documento : aux) {
					imprime += documento.getNome() + "\n";
				}
			}
		}

		return imprime;

	}

	/**
	 * Caso a consulta possua mais de um parametro essa função é chamada. Nela é
	 * lida toda a consulta e separa-se os parametros em um array list.
	 * 
	 * @param palavraBusca
	 * @return
	 */

	public String consultaBooleana(String palavraBusca) {
		String resultConsulta = "";
		String token = " ";
		ArrayList<String> parametros = new ArrayList<>();

		StringTokenizer linha = new StringTokenizer(palavraBusca);

		if (linha != null) {
			while (linha.hasMoreTokens()) {
				token = linha.nextToken();
				parametros.add(token);
			}
		}
		/* Função com apenas uma palavra ok !!!! */
		if (parametros.size() == 1) {
			resultConsulta = consultaBooleana1(palavraBusca);
			return resultConsulta;

		}

		ArrayList<String> palavraNot1 = new ArrayList<String>();
		ArrayList<String> palavraNot2 = new ArrayList<String>();
		ArrayList<String> imprime = new ArrayList<String>();

		// CHAMA A FUNCAO somente NOT (ex: not informação)
		int i = 0;

		if (parametros.get(i).equals("NOT") && parametros.size() == 2) {
			palavraNot1 = funcaoNot(parametros.get(i + 1));

			for (String string : palavraNot1) {
				resultConsulta += string + "\n";

			}
			return resultConsulta;
		}

		// CHAMA FUNCAO AND
		i = 0;

		if (parametros.get(i + 1).equals("AND") && parametros.size() == 3) {

			// chamar a funcao (palavra and palavra) ok !!!!

			imprime = funcaAnd(parametros.get(i), parametros.get(i + 2));
			for (String string : imprime) {
				resultConsulta += string + "\n";
			}
			return resultConsulta;
		}

		// chama a função (palavra and palavra and palavra) ok !!!
		else if (parametros.get(i + 1).equals("AND") && parametros.get(i + 3).equals("AND") && parametros.size() == 5) {
			imprime = funcaAnd(parametros.get(i), parametros.get(i + 2));

			return funcaoAnd(imprime, parametros.get(i + 4));

		}

		// chama a função (palavra and palavra or palavra) ok !!!
		else if (parametros.get(i + 1).equals("AND") && parametros.get(i + 3).equals("OR") && parametros.size() == 5) {
			imprime = funcaAnd(parametros.get(i), parametros.get(i + 2));
			return funcaoOr(imprime, parametros.get(i + 4));
		}

		// chama a função (not palavra and palavra) ok !!!
		else if (parametros.get(i).equals("NOT") && parametros.get(i + 2).equals("AND") && parametros.size() == 4) {
			palavraNot1 = funcaoNot(parametros.get(i + 1));
			return funcaoAnd(palavraNot1, parametros.get(i + 3));
		}

		// chama a função (palavra and not palavra) ok !!!
		else if (parametros.get(i + 2).equals("NOT") && parametros.get(i + 1).equals("AND")) {
			palavraNot1 = funcaoNot(parametros.get(i + 3));
			return funcaoAnd(parametros.get(i), palavraNot1);
		}

		// chama a função (not palavra and not palavra)
		else if (parametros.get(i).equals("NOT") && parametros.get(i + 3).equals("NOT")
				&& parametros.get(i + 2).equals("AND")) {

			palavraNot1 = funcaoNot(parametros.get(i + 1));
			palavraNot2 = funcaoNot(parametros.get(i + 4));
			return funcaoAnd(palavraNot1, palavraNot2);
		}

		/* CHAMA FUNCAO OR */
		i = 0;

		// chamar a funcao (palavra or palavra)
		if (parametros.get(i + 1).equals("OR") && parametros.size() == 3) {
			imprime = funcaoOr(parametros.get(i), parametros.get(i + 2));
			for (String string : imprime) {
				resultConsulta += string + "\n";
			}
			return resultConsulta;
		}
		// chama a função (palavra or palavra or palavra)
		else if (parametros.get(i + 1).equals("OR") && parametros.get(i + 3).equals("OR") && parametros.size() == 5) {
			imprime = funcaoOr(parametros.get(i), parametros.get(i + 2));
			return funcaoOr(imprime, parametros.get(i + 4));

		}

		// chama a função (not palavra or palavra)
		else if (parametros.get(i).equals("NOT") && parametros.get(i + 2).equals("OR") && parametros.size() == 4) {
			palavraNot1 = funcaoNot(parametros.get(i + 1));
			return funcaoOr(palavraNot1, parametros.get(i + 3));
		}

		// chama a função (palavra or palavra and palavra) ok !!!
		else if (parametros.get(i + 1).equals("OR") && parametros.get(i + 3).equals("AND") && parametros.size() == 5) {
			imprime = funcaoOr(parametros.get(i), parametros.get(i + 2));
			return funcaoAnd(imprime, parametros.get(i + 4));
		}

		// chama a função (palavra or not palavra)
		else if (parametros.get(i + 1).equals("OR") && parametros.get(i + 2).equals("NOT")) {
			palavraNot1 = funcaoNot(parametros.get(i + 3));
			return funcaoOr(parametros.get(i + 1), palavraNot1);
		}

		// chama a função (not palavra or not palavra)
		else if ((parametros.get(i).equals("NOT")) && (parametros.get(i + 3).equals("NOT"))) {
			palavraNot1 = funcaoNot(parametros.get(i + 1));
			palavraNot2 = funcaoNot(parametros.get(i + 4));
			return funcaoOr(palavraNot1, palavraNot2);
		}

		return "Formato da consulta errado. ";
	}

	/**
	 * Função (palavra or palavra) Função or: recebe duas palavras e as procura
	 * na hashList adicionando-as em uma outra hashListaux para evitar
	 * repetições.
	 * 
	 * @author Rafael
	 * @return String
	 */

	private ArrayList<String> funcaoOr(String palavra1, String palavra2) {

		HashSet<String> hashListAux = new HashSet<String>();
		ArrayList<String> converte = new ArrayList<String>();
		ArrayList<Documento> aux = new ArrayList<Documento>();
		ArrayList<Documento> aux2 = new ArrayList<Documento>();

		for (Palavra palavra : Index.hashList) {
			if (palavra.getNome().equals(palavra1)) {
				aux = palavra.getDocumentos();
				for (Documento documento : aux) {
					hashListAux.add(documento.getNome());

				}

			}
		}

		for (Palavra palavra : Index.hashList) {
			if (palavra.getNome().equals(palavra2)) {
				aux2 = palavra.getDocumentos();
				for (Documento documento : aux2) {
					hashListAux.add(documento.getNome());

				}
			}
		}

		for (String string : hashListAux) {
			converte.add(string);
		}

		return converte;
	}

	/**
	 * Função (palavra and palavra) Função and: recebe duas palabras e as
	 * procura na hashList. Caso elas existam, então é comparado então seus
	 * documentos. Documentos iguais são impressos.
	 * 
	 * @author Rafael
	 * @return String
	 */

	private ArrayList<String> funcaAnd(String palavra1, String palavra2) {

		ArrayList<String> imprime = new ArrayList<String>();
		ArrayList<Documento> aux = new ArrayList<Documento>();
		ArrayList<Documento> aux2 = new ArrayList<Documento>();

		for (Palavra palavra : Index.hashList) {
			if (palavra.getNome().equals(palavra1)) {
				aux = palavra.getDocumentos();

			}
		}

		for (Palavra palavra : Index.hashList) {
			if (palavra.getNome().equals(palavra2)) {
				aux2 = palavra.getDocumentos();
			}
		}
		if ((aux != null) && (aux2 != null)) {
			for (Documento d : aux) {
				for (Documento d2 : aux2) {
					if (d.getNome().equals(d2.getNome())) {
						imprime.add(d.getNome());
					}
				}
			}
		} else
			imprime.add("");

		return imprime;

	}

	/**
	 * Função (not palavra) Função not: preenche um array list com 120 D : D1,D2
	 * ... Depois compara com os ducumentos das palavras, Os documentos iguais
	 * são descartados
	 * 
	 * @author Rafael
	 * @return String
	 */
	private ArrayList<String> funcaoNot(String palavra1) {

		ArrayList<String> documentos = new ArrayList<String>();
		ArrayList<Documento> aux = new ArrayList<Documento>();

		for (int i = 1; i <= Index.qtdArquivos; i++) { // alterar depois
			documentos.add("D" + i);
		}

		for (Palavra palavra : Index.hashList) {
			if (palavra.getNome().equals(palavra1)) {
				aux = palavra.getDocumentos();

			}
		}
		if (aux != null) {
			for (Documento d : aux) {
				if (documentos.contains(d.getNome())) {
					documentos.remove(d.getNome());
				}
			}
		}
		return documentos;

	}

	/**
	 * Função (palavra and palavra) Função and: O mesmo da outra função and
	 * porem recebe como parametro um arrayList de string
	 * 
	 * @author Rafael
	 * @return String
	 */
	private String funcaoAnd(ArrayList<String> palavra1, String palavra2) {

		String imprime = "";
		ArrayList<Documento> aux2 = new ArrayList<Documento>();
		aux2 = null;

		for (Palavra palavra : Index.hashList) {
			if (palavra.getNome().equals(palavra2)) {
				aux2 = palavra.getDocumentos();
			}
		}

		if (aux2 != null) {
			for (int i = 0; i < palavra1.size(); i++) {
				for (int j = 0; j < aux2.size(); j++)
					if (palavra1.get(i).equals(aux2.get(j).getNome())) {
						imprime += palavra1.get(i) + "\n";
					}

			}
		} else
			imprime = "";

		return imprime;

	}

	/**
	 * Função (palavra and not palavra) faz a operação and com um arrayList de
	 * string como segundo parametro
	 * 
	 * @author Rafael
	 * @return String
	 */

	private String funcaoAnd(String palavra2, ArrayList<String> palavra1) {

		String imprime = "";

		ArrayList<Documento> aux2 = new ArrayList<Documento>();

		for (Palavra palavra : Index.hashList) {
			if (palavra.getNome().equals(palavra2)) {
				aux2 = palavra.getDocumentos();
			}
		}

		for (int i = 0; i < palavra1.size(); i++) {
			for (int j = 0; j < aux2.size(); j++)
				if (palavra1.get(i).equals(aux2.get(j).getNome())) {
					imprime += palavra1.get(i) + "\n";
				}

		}

		return imprime;

	}

	/**
	 * Função (palavra and palavra) Função and: Receeb dois arraylist de string
	 * e faz a operação and
	 * 
	 * @author Rafael
	 * @return String
	 */
	private String funcaoAnd(ArrayList<String> palavra1, ArrayList<String> palavra2) {

		String imprime = "";

		for (int i = 0; i < palavra1.size(); i++) {
			for (int j = 0; j < palavra2.size(); j++)
				if (palavra1.get(i).equals(palavra2.get(j))) {
					imprime += palavra2.get(i) + "\n";
				}

		}

		return imprime;

	}

	/**
	 * Função (palavra or palavra) Função or: recebe um arrayList de strings e
	 * faz a operação or
	 * 
	 * @author Rafael
	 * @return String
	 */
	private String funcaoOr(ArrayList<String> palavra1, String palavra2) {
		String result = "";
		HashSet<String> hashListAux = new HashSet<String>();
		ArrayList<Documento> aux2 = new ArrayList<Documento>();

		for (int i = 0; i < palavra1.size(); i++) {
			hashListAux.add(palavra1.get(i));
		}

		for (Palavra palavra : Index.hashList) {
			if (palavra.getNome().equals(palavra2)) {
				aux2 = palavra.getDocumentos();
				for (Documento documento : aux2) {
					hashListAux.add(documento.getNome());

				}
			}
		}

		for (String documento : hashListAux) {
			result += documento + "\n";
		}
		return result;
	}

	/**
	 * Função (palavra or palavra) Função or: faz a operação or com um arrayList
	 * de strings como segundo no paramentro
	 * 
	 * @author Rafael
	 * @return String
	 */
	private String funcaoOr(String palavra2, ArrayList<String> palavra1) {
		String result = "";
		HashSet<String> hashListAux = new HashSet<String>();
		ArrayList<Documento> aux2 = new ArrayList<Documento>();

		for (int i = 0; i < palavra1.size(); i++) {
			hashListAux.add(palavra1.get(i));
		}

		for (Palavra palavra : Index.hashList) {
			if (palavra.getNome().equals(palavra2)) {
				aux2 = palavra.getDocumentos();
				for (Documento documento : aux2) {
					hashListAux.add(documento.getNome());

				}
			}
		}

		for (String documento : hashListAux) {
			result += documento + "\n";
		}
		return result;
	}

	/**
	 * Função (palavra or palavra) Função or: recebe dois arrayLists como
	 * parametro e faz a operação or
	 * 
	 * @author Rafael
	 * @return String
	 */
	private String funcaoOr(ArrayList<String> palavra1, ArrayList<String> palavra2) {
		String result = "";
		HashSet<String> hashListAux = new HashSet<String>();

		for (int i = 0; i < palavra1.size(); i++) {
			hashListAux.add(palavra1.get(i));
		}

		for (int i = 0; i < palavra2.size(); i++) {
			hashListAux.add(palavra2.get(i));
		}

		for (String documento : hashListAux) {
			result += documento + "\n";
		}
		return result;
	}
}// fim do programa
