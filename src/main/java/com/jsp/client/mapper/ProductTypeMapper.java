package com.jsp.client.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jsp.client.dto.MasterProductCsvDTO;
import com.jsp.client.service.AttributeService;
import com.kibocommerce.sdk.catalogadministration.api.ProductAttributesApi;
import com.kibocommerce.sdk.catalogadministration.models.AttributeInProductType;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttribute;
import com.kibocommerce.sdk.catalogadministration.models.ProductType;
@Component
public class ProductTypeMapper {
	@Autowired
	private ProductAttributesApi productAttributesApi;
//	public ProductType map(MasterProductCsvDTO csv) {
//
//	    ProductType productType = new ProductType();
//
//	    productType.setName(csv.getProductTypeName());
//
//	    // ProductType must support BOTH Standard and Configurable
//	    productType.setProductUsages(List.of("Standard", "Configurable"));
//        productType.setOptions(null);
//	    return productType;
//	}
	private AttributeService service;
	ProductTypeMapper (AttributeService service){
		this.service=service;
	}
	
	public ProductType map(MasterProductCsvDTO csv) {

	    ProductType productType = new ProductType();
	  
	    
	    productType.setName(csv.getProductTypeName());
	    productType.setProductUsages(List.of("Standard", "Configurable"));

	    List<AttributeInProductType> optionList = new ArrayList<>();
	  
	    AttributeInProductType option = new AttributeInProductType();
	    option.setAttributeFQN("tenant~size9");
	    
	//    CatalogAdminsAttribute attribute = service.getAttribute("tenant~size7");

	    AttributeInProductType attr = new AttributeInProductType();
	  //  attr.setAttributeDetail(attribute);
	    
	    optionList.add(option);

	    productType.setOptions(optionList);

	    return productType;
	}
}