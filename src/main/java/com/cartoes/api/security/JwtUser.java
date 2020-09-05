package com.cartoes.api.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cartoes.api.entities.Regra;
import com.cartoes.api.entities.Usuario;

public class JwtUser implements UserDetails {

	private static final long serialVersionUID = -268046329085485932L;

	private int id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public JwtUser(int id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public JwtUser() {
	}

	public JwtUser(Usuario usuario) {

		this.id = usuario.getId();
		this.username = usuario.getCpf();
		this.password = usuario.getSenha();

		List<GrantedAuthority> lstRegras = new ArrayList<GrantedAuthority>();

		for (Regra regra : usuario.getRegras()) {
			lstRegras.add(new SimpleGrantedAuthority(regra.getNome()));
		}

		this.authorities = lstRegras;

	}

	public int getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
