package br.com.diop.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.config.SpringDataWebConfigurationMixin;

import br.com.diop.product.model.Product;

public interface Products extends JpaRepository<Product, Long> {
	
	public List<Product> findByNameContainingIgnoreCase(String name);

}
