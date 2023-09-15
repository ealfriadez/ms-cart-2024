package pnp.gob.pe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pnp.gob.pe.mscart2023.service.ProductService;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class MsCart2024ApplicationTests {

	@Autowired
	private ProductService productService;

	@Test
	void contextLoads() {
		assertNull(productService.findById(6L));
	}

}
