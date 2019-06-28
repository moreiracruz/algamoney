package com.algaworks.algamoney.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Stack;

public class Teste2 {

	public static String CharlietheDog(String[] strArr) {

		int tamanho = strArr.length;
		No[][] tabuleiro = new No[tamanho][tamanho];

		for (int x = 0; x < tamanho; ++x) {

			String linha = strArr[x];

			for (int y = 0; y < tamanho; ++y) {

				String valor = String.valueOf(linha.charAt(y));
				tabuleiro[x][y] = new No(x, y, valor);

			}

		}

		house = null;
		foods = new ArrayList<No>();

		for (int x = 0; x < tamanho; ++x) {

			for (int y = 0; y < tamanho; ++y) {

				No atual = tabuleiro[x][y];

				adicionarAlto(tabuleiro, atual, x, y + 1);
				adicionarDireita(tabuleiro, atual, x - 1, y);
				adicionarBaixo(tabuleiro, atual, x, y - 1);
				adicionarEsquerda(tabuleiro, atual, x + 1, y);

				if (atual.isHouse()) {
					house = atual;
				}

				if (atual.isFood()) {
					foods.add(atual);
				}

			}

		}

		return buscar(tabuleiro);

	}

	public static void main(String[] args) {
		// keep this function call here
		Scanner s = new Scanner(System.in);
		System.out.print(CharlietheDog(new String[] { "OOOO", "OOFF", "OCHO", "OFOO" }));
	}



	private static No adicionarAlto(No[][] tabuleiro, No atual, int i, int j) {

		try {

			No auxiliar = tabuleiro[i][j];
			atual.setAlto(auxiliar);

		} finally {

			return atual;

		}

	}

	private static No adicionarDireita(No[][] tabuleiro, No atual, int i, int j) {

		try {

			No auxiliar = tabuleiro[i][j];
			atual.setDireita(auxiliar);

		} finally {

			return atual;

		}

	}

	private static No adicionarBaixo(No[][] tabuleiro, No atual, int i, int j) {

		try {

			No auxiliar = tabuleiro[i][j];
			atual.setBaixo(auxiliar);

		} finally {

			return atual;

		}

	}

	private static No adicionarEsquerda(No[][] tabuleiro, No atual, int i, int j) {

		try {

			No auxiliar = tabuleiro[i][j];
			atual.setEsquerda(auxiliar);

		} finally {

			return atual;

		}

	}

	private static String buscar(No[][] tabuleiro) {

		int tamanho = tabuleiro.length;

		for (int x = 0; x < tamanho; ++x) {
			for (int y = 0; y < tamanho; ++y) {
				No no = tabuleiro[x][y];
				if (no.isCharlie()) {
					BuscaProfundidade busca = new BuscaProfundidade(house.getValor());
					busca.buscar(no);
					return String.valueOf(busca.getMenorCaminho());
				}
			}
		}

		return "0";

	}
	
	private static Collection<No> foods;
	private static No house;

	private static class BuscaProfundidade {

		private Stack<No> stack;

		private No atual;

		private String valorBuscado;

		private int menorCaminho = Integer.MAX_VALUE;

		public BuscaProfundidade(String valorBuscado) {
			this.valorBuscado = valorBuscado;
			stack = new Stack<No>();
		}

		private boolean resultadoEncontrado(No no) {

			return stack.containsAll(foods) && no.getValor().equals(valorBuscado);

		}

		public void buscar(No no) {

			if (no == null) {
				return;
			}

			if (stack.contains(no)) {
				return;
			}

			stack.add(no);

			if (resultadoEncontrado(no)) {

				int caminhoAtual = stack.size() - 1;
				if (caminhoAtual < menorCaminho) {
					menorCaminho = caminhoAtual;
				}

			} else {

				this.buscar(no.getAlto());
				this.buscar(no.getDireita());
				this.buscar(no.getEsquerda());
				this.buscar(no.getBaixo());

			}

			stack.pop();

		}

		public int getMenorCaminho() {
			return menorCaminho;
		}

	}

	private static class No {

		private int x;
		private int y;
		private String valor;
		private boolean visitado;

		private No alto;
		private No direita;
		private No baixo;
		private No esquerda;

		public No(int x, int y, String valor) {
			this.x = x;
			this.y = y;
			this.valor = valor;
		}

		public No getAlto() {
			return alto;
		}

		public void setAlto(No alto) {
			this.alto = alto;
		}

		public No getDireita() {
			return direita;
		}

		public void setDireita(No direita) {
			this.direita = direita;
		}

		public No getBaixo() {
			return baixo;
		}

		public void setBaixo(No baixo) {
			this.baixo = baixo;
		}

		public No getEsquerda() {
			return esquerda;
		}

		public void setEsquerda(No esquerda) {
			this.esquerda = esquerda;
		}

		public boolean isCharlie() {
			return valor.equals("C");
		}

		public boolean isHouse() {
			return valor.equals("H");
		}

		public boolean isFood() {
			return valor.equals("F");
		}

		public boolean isEmpty() {
			return valor.equals("O");
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public String getValor() {
			return valor;
		}

		public void setValor(String valor) {
			this.valor = valor;
		}

		public boolean isVisitado() {
			return visitado;
		}

		public void setVisitado(boolean visitado) {
			this.visitado = visitado;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			No other = (No) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "No [x=" + x + ", y=" + y + ", valor=" + valor + ", visitado=" + visitado + "]";
		}

	}

}
