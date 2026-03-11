package com.jsp.client.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.jsp.client.dto.MasterProductCsvDTO;
import com.kibocommerce.sdk.catalogadministration.models.AttributeInProductType;
import com.kibocommerce.sdk.catalogadministration.models.ProductType;
@Component
public class ProductTypeMapper {
	
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
	public ProductType map(MasterProductCsvDTO csv) {

	    ProductType productType = new ProductType();
	    productType.setName(csv.getProductTypeName());
	    productType.setProductUsages(List.of("Standard", "Configurable"));

	    List<AttributeInProductType> optionList = new ArrayList<>();

	    AttributeInProductType option = new AttributeInProductType();
	    option.setAttributeFQN("tenant~size7");

	    optionList.add(option);

	    productType.setOptions(optionList);

	    return productType;
	}
}