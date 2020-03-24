package com.udemy.spring.api.cursomc.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Produto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	private BigDecimal preco;

	@JsonBackReference // Diz q do outro lado da associação já tem os obj, então n busca mais
	@ManyToMany
	@JoinTable(name = "PRODUTO_CATEGORIA", //Tabela Associativa - Feito na entidade q é dona do relacionamento?
				joinColumns = @JoinColumn(name = "produto_id"),//Fk correspondente a classe atual (Produto)
				inverseJoinColumns = @JoinColumn(name = "categoria_id")//Fk correspondente a classe associada (Categoria)
	)
	private List<Categoria> categorias = new ArrayList<Categoria>();
	
	@JsonIgnore // Sem necessidade de ver os item pelo produto
	@OneToMany(mappedBy = "id.produto")
	private Set<ItemPedido> itens = new HashSet<>();// Usar o set para garantir que n tenha um item repetido no mesmo Pedido

	public Produto() {
	}	

	public Produto(Integer id, String nome, BigDecimal preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	//Deve-se ignorar pra n ter um ref ciclcia
	@JsonIgnore
	public List<Pedido> getPedidos() {
		List<Pedido> pedidos = new ArrayList<Pedido>();
		itens.forEach(x -> pedidos.add(x.getPedido()));
		return pedidos;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
