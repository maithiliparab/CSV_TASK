package com.jsp.client.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.kibocommerce.sdk.catalogadministration.api.ProductTypesApi;
import com.kibocommerce.sdk.catalogadministration.models.AttributeInProductType;
import com.kibocommerce.sdk.catalogadministration.models.ProductType;
import com.kibocommerce.sdk.catalogadministration.models.ProductTypeCollection;
import com.kibocommerce.sdk.common.ApiException;

@Service
public class ProductTypeService {

	private final ProductTypesApi api;
	private final ProductTypeCache cache;

	public ProductTypeService(ProductTypesApi api, ProductTypeCache cache) {
		this.api = api;
		this.cache = cache;
	}

	
	//Preload all existing ProductTypes into cache at startup
	@PostConstruct
	public void preloadCache() throws ApiException {

		int startIndex = 0;
		int pageSize = 200;

		ProductTypeCollection response;

		do {
			response = api.getProductTypes(startIndex, pageSize, null, null, null);

			if (response.getItems() != null) {
				for (ProductType pt : response.getItems()) {
					cache.put(pt.getName(), pt.getId());
				}
			}

			startIndex += pageSize;

		} while (response.getItems() != null && response.getItems().size() == pageSize);

		System.out.println("ProductType cache preloaded with " + cache.getAll().size() + " items.");
	}

	
	 // Creates ProductType only if it does not exist in cache or API
	 
	public void createIfNotExists(ProductType productType) throws ApiException {

	    if (productType == null || productType.getName() == null) {
	        throw new IllegalArgumentException("ProductType or ProductType name cannot be null");
	    }

	    // Normalize name
	    String normalizedName = productType.getName().trim().toLowerCase();

	    // Check cache
	    if (cache.get(normalizedName) != null) {
	        System.out.println("ProductType already exists: " + productType.getName());
	        return;
	    }
         //  System.out.println(productType);
	    // Create ProductType (only API call)
	     ProductType created = api.addProductType(productType);

	    // Update cache
	    cache.put(created.getName(), created.getId());

	    System.out.println("ProductType created: " + created.getName());
	}
	
	public void attachAttributeToProductType(int productTypeId, String attributeCode) throws ApiException {

	    ProductType productType = api.getProductType(productTypeId);

	    List<AttributeInProductType> options = productType.getOptions();

	    if (options == null) {
	        options = new ArrayList<>();
	    }

	    String fqn = "tenant~" + attributeCode;

	    // prevent duplicate
	    boolean exists = options.stream()
	            .anyMatch(o -> fqn.equals(o.getAttributeFQN()));

	    if (!exists) {

	        AttributeInProductType option = new AttributeInProductType();
	        option.setAttributeFQN(fqn);

	        options.add(option);

	        // set order for ALL options
	        for (int i = 0; i < options.size(); i++) {
	            options.get(i).setOrder(i + 1);
	        }

	        productType.setOptions(options);

	        api.updateProductType(productTypeId, productType);

	        System.out.println("Attribute attached successfully");
	    }
	}
}