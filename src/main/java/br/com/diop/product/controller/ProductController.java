package br.com.diop.product.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.diop.product.model.Product;
import br.com.diop.product.model.ProductCategory;
import br.com.diop.product.repository.Products;
import br.com.diop.product.repository.filter.ProductFilter;

@Controller
@RequestMapping("/produtos")
public class ProductController {

	@Autowired
	private Products products;

	@GetMapping("/novo")
	public ModelAndView newProduct(Product product) {

		ModelAndView mv = new ModelAndView("product/register-product");
		mv.addObject(product);
		mv.addObject("categories", ProductCategory.values());
		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView save(@Valid Product product, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return newProduct(product);
		}

		products.save(product);
		attributes.addFlashAttribute("msg", "Produto Salvo com sucesso!");
		return new ModelAndView("redirect:/produtos/novo");
	}

	@GetMapping
	public ModelAndView search(ProductFilter filter) {
		ModelAndView mv = new ModelAndView("product/search-product");
		mv.addObject("products",
				products.findByNameContainingIgnoreCase(
						Optional.ofNullable(filter.getName()).orElse("%")));
		return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView edit(@PathVariable Long id){
		Product product = products.findOne(id);
		return newProduct(product);
	}
	
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes attributes){
		products.delete(id);
		attributes.addFlashAttribute("msg", "Produto excluído com sucessso!");
		return "redirect:/produtos";
	}
	

}
