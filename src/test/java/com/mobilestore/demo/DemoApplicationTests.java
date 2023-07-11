package com.mobilestore.demo;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobilestore.demo.controller.ProductController;
import com.mobilestore.demo.model.Product;
import com.mobilestore.demo.repository.ProductRepository;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
class DemoApplicationTests {

	private MockMvc mvc;
	@Mock
	private ProductRepository prodRepo;

	@InjectMocks
	private ProductController productController;

	private JacksonTester<Product> jsonProduct;

	private JacksonTester<Collection<Product>> jsonProducts;

	@BeforeEach
	public void setUp() {
		JacksonTester.initFields(this, new ObjectMapper());
		mvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	public void canAddANewProduct() throws Exception {
		Product product = new Product(1L, "Aalu Gosht", 1000, "yummy", "YummyTareen", "www.aalushost.com");
		when(prodRepo.save(product)).thenReturn((product));
		mvc.perform(MockMvcRequestBuilders
				.post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonProduct.write(product).getJson()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void canGetAllProducts() throws Exception {
		Product product1 = new Product(1L, "Aalu Gosht", 1000, "yummy", "YummyTareen", "www.aalushost.com");
		Product product2 = new Product(1L, "Aalu", 100, "yum", "YummyTareen", "www.aalu.com");

		List<Product> listOfproducts = new ArrayList<>();
		listOfproducts.add(product1);
		listOfproducts.add(product2);

		when(prodRepo.findAll()).thenReturn(listOfproducts);

		mvc.perform(MockMvcRequestBuilders
				.get("/products/all")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(
				MockMvcResultMatchers.content().json(jsonProducts.write(listOfproducts).getJson()));
		}

		@Test
	public void canGetAProduct() throws Exception {
		Product product1 = new Product(1L, "Aalu Gosht", 1000, "yummy", "YummyTareen", "www.aalushost.com");
		when(prodRepo.findById(1L)).thenReturn(Optional.of(product1));
		mvc.perform(MockMvcRequestBuilders.get("/products/get/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(jsonProduct.write(product1).getJson()));
	}

	@Test
	public void canDeleteAProduct() throws Exception {
	
		doNothing().when(prodRepo).deleteById(1L);

		mvc.perform(MockMvcRequestBuilders
				.delete("/products/delete/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void canDeleteAllAvailabilities() throws Exception {
		doNothing().when(prodRepo).deleteAll();

		mvc.perform(MockMvcRequestBuilders
				.delete("/products/deleteAll"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}


}

