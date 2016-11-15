
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ModeloVetorial {

	ArrayList<TotalTF_IDF> totalTF_IDF = new ArrayList<TotalTF_IDF>();
	ArrayList<VetCosult> vet_Consulta = new ArrayList<VetCosult>();

	/**
	 * 
	 */
	public ModeloVetorial() {
		start();
	}

	/**
	 * 
	 */
	private void start() {
		calculaTF();
		calculaITF(Index.qtdArquivos);
		totalTF_IDF();
		if (Index.DEGUG) {
			frequencia();
		}
	}

	/**
	 * @param x
	 * @return
	 */
	private static float logBase2(int x) {
		return (float) (Math.log(x) / Math.log(2));
	}

	/**
	 * @param x
	 * @return
	 */
	private static float logBase2(float x) {
		return (float) (Math.log(x) / Math.log(2));
	}

	/**
	 * 
	 */
	public void calculaTF() {
		for (Palavra p : Index.hashList) {
			ArrayList<Documento> d = new ArrayList<Documento>(p.getDocumentos());

			for (Documento doc : d) {
				if (doc.getRepeticoes() > 0) {
					doc.setTf(1 + logBase2(doc.getRepeticoes()));
				}
			}
		}
	}

	/**
	 * @param qdtArquivo
	 */
	public void calculaITF(int qdtArquivo) {
		float idf = 0;

		for (Palavra p : Index.hashList) {
			ArrayList<Documento> d = new ArrayList<Documento>(p.getDocumentos());
			idf = 0;
			idf = (float) d.size() / (float) qdtArquivo;

			for (Documento doc : d) {
				if (doc.getRepeticoes() > 0) {
					doc.setIdf(logBase2(idf) * -1);
				}
			}
		}
	}

	/**
	 * 
	 */
	public void totalTF_IDF() {
		float soma = 0;
		for (int i = 1; i <= Index.qtdArquivos; i++) {

			for (Palavra p : Index.hashList) {
				ArrayList<Documento> d = new ArrayList<Documento>(p.getDocumentos());
				for (Documento doc : d) {
					if (doc.getNome().equals("D" + i))
						soma += Math.pow((doc.getTf() * doc.getIdf()), 2);
				}

			}
			totalTF_IDF.add(new TotalTF_IDF("D" + i, (float) Math.sqrt(soma)));
			soma = 0;
		}
	}

	/**
	 * @author Diego
	 * @param freqQn
	 * @param freqDoc
	 * @return float
	 */
	public float calcVetorConsult(int freqQn, int freqDoc) {

		return (1 + logBase2(freqQn) * logBase2(Index.qtdArquivos / freqDoc));
	}

	/**
	 * @param palaConsulta
	 * @return
	 */
	public float vetorCalc(ArrayList<String> palaConsulta) {
		int cont = 0;
		float somatorioVET = 0;
		if (palaConsulta.size() == 2) {
			if (palaConsulta.get(0).equals(palaConsulta.get(1))) {
				cont = 2;
			} else
				cont = 1;
		} else
			cont = 1;

		for (String string : palaConsulta) {
			if (consultaQtd(string) != 0) {
				vet_Consulta.add(new VetCosult(string, calcVetorConsult(cont, consultaQtd(string))));
			} else
				vet_Consulta.add(new VetCosult(string, 0.0));
		}

		for (VetCosult v : vet_Consulta) {
			somatorioVET += v.getValor();
		}

		return (float) Math.sqrt(somatorioVET);
	}

	/**
	 * @param consulta
	 * @return
	 */
	public String similaridade(String consulta) {

		String imprime = "";
		ArrayList<String> palaConsulta = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer((consulta));

		float som_tf_idf_mult_vetConsult = 0;
		float som_Vet_Consult = 0;

		while (st.hasMoreElements()) {
			palaConsulta.add(st.nextToken().toUpperCase());
		}

		som_Vet_Consult = vetorCalc(palaConsulta);

		for (int i = 1; i <= Index.qtdArquivos; i++) {

			for (VetCosult v : vet_Consulta) {
				som_tf_idf_mult_vetConsult += tf_idf_mult_vetConsult(v, i);
			}

			for (TotalTF_IDF t : totalTF_IDF) {
				if (t.getDocumento().equals("D" + i)) {
					imprime += "D" + i + " : " + som_tf_idf_mult_vetConsult / (t.getValor() * som_Vet_Consult) + "\n";
				}
			}
			som_tf_idf_mult_vetConsult = 0;
		}

		return imprime;
	}

	/**
	 * @param v
	 * @param i
	 * @return
	 */
	public float tf_idf_mult_vetConsult(VetCosult v, int i) {

		float tf_idf;

		for (Palavra p : Index.hashList) {
			if (v.getPalavra().equals(p.getNome())) {
				ArrayList<Documento> d = p.getDocumentos();
				for (Documento documento : d) {
					if (documento.getNome().equals("D" + i)) {
						tf_idf = documento.getTf() * documento.getIdf();
						return tf_idf * v.getValor();
					}
				}
			}
		}
		return 0;
	}

	/**
	 * @param p
	 * @return
	 */
	public int consultaQtd(String p) {
		ArrayList<Documento> aux = new ArrayList<Documento>();
		int cont = 0;
		for (Palavra palavra : Index.hashList) {
			if (palavra.getNome().equals(p)) {
				aux = palavra.getDocumentos();

				for (int i = 0; i < aux.size(); i++) {
					cont++;
				}
			}
		}
		return cont;
	}

	/**
	 * 
	 */
	public void frequencia() {

		for (Palavra p : Index.hashList) {
			System.out.print(p.getNome() + " - ");
			ArrayList<Documento> d = new ArrayList<Documento>(p.getDocumentos());

			for (Documento doc : d) {
				System.out.print(doc.getNome() + ", ");
				System.out.print(doc.getRepeticoes() + ", ");
				System.out.print(doc.getTf() + ", ");
				System.out.print(doc.getIdf() + " ");
			}
			System.out.println();
		}

	}

}
