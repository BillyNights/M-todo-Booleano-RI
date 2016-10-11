
/**

Authores: Diego Maicon Siva - 0011913
		  Rafael Rossato de Souza - 0002776

• Instruções:
Compilação no Console:
javac Index.java


Execução no Console:
java Index ri.txt 
Pasta com a coleção onde esta o projeto Trabalho1RI\Colecao.

Ambiente de Desenvolvimento Diego
• SO – Ubuntu 16.04 64 bits
• CPU – Dell inspiron 14z core i5 1.8 GHz 3ª geraçao
• Memória – 8 GB - DDR3


• Ferramentas IDE : Sublime Text 3, Build 3103, 64 bits;

					Eclipse Java EE IDE for Web Developers.
					Version: Mars.1 Release (4.5.1)
					Build id: 20150924-1200;

• javac versao#: javac 1.8.0_91;


Objetivo do arquivo:
Este programa, tem como objetivo receber uma coleção  de arquivos e realizar pesquisar neste arquivos utilizando metodo booleano.

*/

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class Index {

	static boolean DEGUG = false;
	public static int qtdArquivos = 120;
	public static HashSet<Palavra> hashList = new HashSet<Palavra>();
	static final String caminhoPasta = "Colecao/colecao";

	public void leAnalisa(String arquivo) throws IOException {
		// faz a leitura do arquivo
		String impressao = "";

		for (int i = 1; i <= qtdArquivos; i++) {
			arquivo += i + ".txt";

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"));

			String teste = " ";

			// equanto houver mais linhas
			try {
				while (br.ready()) {
					// lê a proxima linha

					String linha = br.readLine();

					StringTokenizer st = new StringTokenizer(limpaLinha(linha));

					while (st.hasMoreElements()) {

						teste = st.nextToken().toUpperCase();

						/* Captura palavras meiores ou igual a 5 caracter */
						if (teste.length() >= 5) {
							impressao += teste + "\n";
						}
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			br.close();

			arquivo = caminhoPasta;
		}
		saidaSemFiltro(impressao);
	}

	/**
	 * @author Diego
	 * 
	 * @return String
	 * 
	 */
	public String limpaLinha(String linha) {

		linha = linha.replace(",", " "); // tira virgula
		linha = linha.replace(";", " "); // tira ponto e virgula
		linha = linha.replace(".", " "); // tira ponto final
		linha = linha.replace(":", " "); // tira ponto final
		linha = linha.replace("?", " "); // tira ponto final
		linha = linha.replace("!", " ");
		linha = linha.replace("-", " ");
		linha = linha.replace("(", " ");
		linha = linha.replace(")", " ");
		linha = linha.replace("{", " ");
		linha = linha.replace("}", " ");
		linha = linha.replace("[", " ");
		linha = linha.replace("]", " ");
		linha = linha.replace("'", " ");
		linha = linha.replace("/", " ");
		linha = linha.replace("\"", " ");
		linha = linha.replace("\"", " ");
		linha = linha.replace("”", " ");
		linha = linha.replace("“", " ");
		linha.toUpperCase();

		return linha;
	}

	public void saidaSemFiltro(String texto) throws IOException {

		OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream("Palavras_Chave.txt"), "UTF-8");
		bufferOut.write(texto);

		System.out.println("Arquivo Criado.");
		bufferOut.close();

	}

	public void saidaHash(HashSet<Palavra> hashList) throws IOException {

		OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream("Palavras_Chave_Hash.txt"), "UTF-8");
		String texto = "";

		for (Palavra palavra : hashList) {
			texto += palavra.getNome() + "\n";
		}
		bufferOut.write(texto);
		System.out.println("Arquivo hash Criado.");
		bufferOut.close();

	}

	/**
	 * Cria Tabela Hash Abrindo arquivo Palavras_Chave.txt passado por
	 * parâmetro.
	 * 
	 * @author Diego
	 * @param String
	 *            - Nome do Arquivo vocabulário
	 * 
	 * @return Hashset<Palavra>- Tabela Hash com o Vocabulário
	 * @throws IOException
	 */

	public void criaTabelaHash(String caminho) throws IOException {
		// Create a hash map
		Palavra p;
		String linha = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(caminho), "UTF-8"));
		try {
			while (br.ready()) {
				// lê a proxima linha
				linha = br.readLine();
				p = new Palavra(linha);
				if (!hashList.contains(p)) {
					hashList.add(p);
					// hashLista.add(p);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br.close();
		return;
	}

	/**
	 * Varre todos os arquivos, procurando onde se existe a palavra, casoa
	 * exista, insere na lita da palavra o arquivo("D1" ou "D2");
	 *
	 * @param String
	 *            - Recebe caminho(constante) onde se encontra os arquivos a
	 *            serem varridos.
	 *
	 *
	 * @author Diego
	 * @throws IOException
	 */

	public void varrerArquivo(String arquivo) throws IOException {

		for (Palavra palavra : hashList) {
			if (DEGUG) {
				System.out.println("Procurando palavra : " + palavra.getNome());
			}

			int repeticao = 0;
			boolean temPalavra = false;
			for (int i = 1; i <= qtdArquivos; i++) {
				/* Se DEGUG(constante) Falso, não imprime. */
				if (DEGUG) {
					System.out.println("No documento colecao" + i);
				}
				arquivo += i + ".txt";

				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"));
				String teste = "";
				// equanto houver mais linhas
				try {
					while (br.ready()) {
						// lê a proxima linha
						String linha = br.readLine();
						StringTokenizer st = new StringTokenizer(limpaLinha(linha));

						while (st.hasMoreElements()) {
							teste = st.nextToken().toUpperCase();
							if (teste.equals(palavra.getNome())) { // Se tem a
																	// //
																	// palavra
								repeticao++; // qtd vezes palavra repete
								temPalavra = true;
							}
						}

					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				br.close();

				if (temPalavra) {
					palavra.insereDocumento(new Documento("D" + i, repeticao));
				}
				arquivo = caminhoPasta;
				temPalavra = false;
			}
		} // FIM FORECH
		if (DEGUG) {
			System.out.println("Documentos varridos");
		}
	}

	public void criaArquivoBolleano(HashSet<Palavra> hashlista) throws IOException {

		ArrayList<Documento> documentos;
		OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream("Tabela_Booleana.html"), "UTF-8");

		String imp = "<html> <meta charset=\"utf8\">\n  <head> <title> TABELA BOOLEANA </title> </head> \n <body> \n <table border=\"1\"> \n ";
		bufferOut.write(imp);
		imp = "";
		for (Palavra palavra : hashlista) {
			imp = "<tr> <td> " + palavra.getNome() + " </td>";
			documentos = palavra.getDocumentos();
			int i = 1;
			for (Documento d : documentos) {

				if (d.getNome().equals("D" + i)) {
					imp += " <td> " + 1 + " </td> ";
				} else
					imp += " <td> " + 0 + " </td> ";
				i += 1;
			}
			imp += "</tr> \n";
			bufferOut.write(imp);
			imp = "";
		}
		imp = "</table>\n</body>\n </html>\n";

		bufferOut.write(imp);
		System.out.println("Tabela HTML criada.");
		bufferOut.close();

	}

	public void consultar() {
		String opcao = null;
		int op;
		String consult = null;
		String imprime = null;

		do {

			opcao = (String) JOptionPane.showInputDialog(null, "============= Menu de Opções ==============\n"
					+ " 1 - Realizar ConsultaBooleana.                  \n" + " 0 - Sair                   \n");

			op = Integer.parseInt(opcao);
			switch (op) {
			case 1:
				boolean sair = false;
				do {
					consult = JOptionPane.showInputDialog(null, " Informe sua consulta - ou digite sair \n");

					if (consult.equals("sair")) {
						sair = true;
					} else {
						ConsultaBooleana c = new ConsultaBooleana();
						imprime = c.consultaBooleana(consult.toUpperCase());
						if (imprime == "") {
							imprime = "Nenhum documento encontrado !";
						}
						JOptionPane.showMessageDialog(null, "Os documentos da consulta são:\n" + imprime);

					}
				} while (!sair);
				break;

			}

		} while (op != 0);
	}

	public static void main(String[] args) throws IOException {
		Index i = new Index();

		/* Cria arquivos com todas as palavras */
		// i.leAnalisa(caminhoPasta);

		/*
		 * Cria tabela hashSet e o Arquivo de Saída com as palavas sem
		 * repetição.
		 * 
		 * Varre Todos os arquivos e identifica os documentos e insere na lista.
		 */

		i.criaTabelaHash("Palavras_Chave_Hash.txt");

		i.varrerArquivo(caminhoPasta);

		/*
		 * Cria arquico HTML com a Tabela Bolleana
		 * i.criaArquivoBolleano(hashList);
		 */

		i.consultar();

	}
}