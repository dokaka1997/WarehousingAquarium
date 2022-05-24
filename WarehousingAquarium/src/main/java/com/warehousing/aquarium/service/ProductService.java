package com.warehousing.aquarium.service;

import com.warehousing.aquarium.exception.MissingFieldException;
import com.warehousing.aquarium.model.request.ProductRequest;
import com.warehousing.aquarium.model.response.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts(int pageIndex, int pageSize, String search);

    ProductDTO getProductById(Long id);

    ProductRequest createProduct (ProductRequest product) throws MissingFieldException;

    Boolean updateListProduct(List<ProductRequest> productRequests);

    Boolean deleteProductById(Long id);

}
