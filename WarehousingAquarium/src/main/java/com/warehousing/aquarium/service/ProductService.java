package com.warehousing.aquarium.service;

import com.warehousing.aquarium.exception.MissingFieldException;
import com.warehousing.aquarium.model.request.ProductRequest;
import com.warehousing.aquarium.model.response.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts(int pageIndex, int pageSize);

    ProductDTO getProductById(Long id);

    Boolean createProduct (ProductRequest product) throws MissingFieldException;

}
