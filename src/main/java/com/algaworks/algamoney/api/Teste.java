package com.algaworks.algamoney.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Teste {

	public static String CharlietheDog(String[] strArr) {

		Queue<Integer> fila = new LinkedList<Integer>();

//		Tabuleiro tabuleiro = new Tabuleiro(strArr);

		return "";

	}

	public static Integer PermutationStep(int num) {

		Set<Integer> set = new TreeSet<Integer>();

		List<Integer> list = new LinkedList<Integer>();

		String str = String.valueOf(num);

		for (int i = 0; i < str.length(); i++) {

			Character atual = str.charAt(i);
			list.add(Character.getNumericValue(atual));

		}

		for (int i = 0; i < list.size(); i++) {

			for (int j = 0; j < list.size(); j++) {

				List<Integer> aux = new LinkedList<Integer>(list);

				int a = aux.get(i);
				int b = aux.get(j);
				aux.remove(i);
				aux.add(i, b);
				aux.remove(j);
				aux.add(j, a);

				String n = "";
				for (Integer k : aux) {
					n += k;
				}

				set.add(new Integer(n));

			}

		}

		Iterator<Integer> iterator = set.iterator();

		while (iterator.hasNext()) {

			Integer next = iterator.next();
			if (next > num) {
				return next;
			}

		}

		return -1;

	}

	public static String NonrepeatingCharacter(String str) {

		Map<Character, Integer> map = new TreeMap<Character, Integer>();

		for (int i = 0; i < str.length(); i++) {

			char atual = str.charAt(i);

			Integer quantidade = map.get(atual);

			if (quantidade == null) {
				quantidade = 0;
			}

			map.put(atual, ++quantidade);

		}

		for (Character atual : map.keySet()) {

			if (map.get(atual) == 1) {
				return atual.toString();
			}
		}

		return "";

	}

	public static void main(String[] args) {
		// keep this function call here
		Scanner s = new Scanner(System.in);
		System.out.print(CharlietheDog(new String[] { "OOOO", "OOFF", "OCHO", "OFOO" }));
	}

}

class Tabuleiro {

	private HashMap<Integer, Collection<Celula>> colunas;

	public Tabuleiro(String[] strArr) {

		colunas = new HashMap<Integer, Collection<Celula>>();

		int largura = strArr.length;

		No acima = null;
		No abaixo = null;

		for (int index = 0; index < largura; index++) {

			String str = strArr[index];

			ArrayList<Celula> linhas = (ArrayList<Celula>) criarCelulas(index + 1, str);

			for (Celula linha : linhas) {

				ArrayList<No> nos = obterNos(index, linha);

				for (No no : nos) {

					if (index == 0) {

						abaixo = nos.get(index + 1);
						acima = null;

					} else if (index == largura - 1) {

						abaixo = null;
						acima = nos.get(index - 1);

					} else {

						abaixo = nos.get(index + 1);
						acima = nos.get(index - 1);

					}

					if (abaixo != null) {
						abaixo.adicionarAdjacente(no);
					}

					if (acima != null) {
						acima.adicionarAdjacente(no);
					}

					System.out.println(no);

				}

			}

//			criarColunas(linhas);

			colunas.put(index + 1, linhas);

		}

		System.out.println(colunas);

	}

	private ArrayList<No> obterNos(int index, Celula linha) {

		ArrayList<No> nos = null;

		if (linha.getNumero() == index + 1) {

			nos = (ArrayList<No>) linha.getNos();

		}

		return nos;
	}

	private Collection<Celula> criarCelulas(int numeroLinha, String valores) {

		Collection<Celula> celulas = new ArrayList<Celula>();

		List<No> nos = (List<No>) criarNos(valores.toCharArray());

		int quantidade = nos.size();

		No no = null;

		No proximo = null;
		No anterior = null;

		for (int index = 0; index < quantidade; index++) {

			no = nos.get(index);

			if (index == 0) {

				proximo = nos.get(index + 1);
				anterior = null;

			} else if (index == quantidade - 1) {

				proximo = null;
				anterior = nos.get(index - 1);

			} else {

				proximo = nos.get(index + 1);
				anterior = nos.get(index - 1);

			}

			if (proximo != null) {
				proximo.adicionarAdjacente(no);
			}

			if (anterior != null) {
				anterior.adicionarAdjacente(no);
			}

		}

		Celula celula = new Celula(numeroLinha, nos);
		celulas.add(celula);

		return celulas;

	}

	private Collection<No> criarNos(char[] valores) {

		int quantidade = valores.length;
		Collection<No> nos = new ArrayList<No>();

		for (int index = 0; index < quantidade; index++) {

			No no = new No(String.valueOf(valores[index]));
			nos.add(no);

		}

		return nos;

	}

}

class Coluna {

	private Integer numero;
	private Collection<No> nos;

	public Coluna(Integer numero, Celula linha) {

		this.numero = numero;

	}

	public Integer getNumero() {
		return numero;
	}

	public Collection<No> getNos() {
		return this.nos;
	}

}

class Celula {

	private Integer numero;
	private Collection<No> nos;

	public Celula(Integer numero, Collection<No> nos) {
		this.numero = numero;
		this.nos = nos;
	}

	public Integer getNumero() {
		return numero;
	}

	public Collection<No> getNos() {
		return this.nos;
	}

	@Override
	public String toString() {
		return "Linha [numero=" + numero + "]";
	}

}

class No {

	private String valor;
	private boolean visitado;
	Collection<No> adjacentes = new ArrayList<No>();

	public No(String valor) {
		this.valor = valor;
		visitado = false;
	}

	public void adicionarAdjacente(No no) {
		this.adjacentes.add(no);
	}

	public Collection<No> adjacentes() {
		Collection<No> adjacentes = new ArrayList<No>();
		return adjacentes;
	}

	@Override
	public String toString() {
		return "No [valor=" + valor + ", visitado=" + visitado + ", adjacentes=" + adjacentes.size() + "]";
	}

}
