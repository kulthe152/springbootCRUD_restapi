package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.entity.Product;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.repository.ProductRepository;


@Service
@Transactional
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product createProduct(Product product) {
		return this.productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		
		Optional<Product> productDB = this.productRepository.findById(product.getId());

		if(productDB.isPresent()) {
			Product productUpdate = productDB.get();
			productUpdate.setId(product.getId());
			productUpdate.setPname(product.getPname());
			productUpdate.setDesc(product.getDesc());
			productRepository.save(productUpdate);
			return productUpdate;
		}
		else {
			throw new ResourceNotFoundException("Record not found with id:-"+product.getId());
		}
	}

	@Override
	public List<Product> getAllProduct() {
		return this.productRepository.findAll();
	}

	@Override
	public Product getProductById(long productId) {

		Optional<Product> productdb = this.productRepository.findById(productId);
		
		if(productdb.isPresent()) {
			return productdb.get();
		}
		else {
			throw new ResourceNotFoundException("Record not found with id:-"+productId);
		}		
	}

	@Override
	public void deleteProduct(long id) {
		
		Optional<Product> productdb = this.productRepository.findById(id);
		
		if(productdb.isPresent()) {
			this.productRepository.delete(productdb.get());
		}
		else {
			throw new ResourceNotFoundException("Record not found with id:-"+id);
		}
		
	}
	
}
