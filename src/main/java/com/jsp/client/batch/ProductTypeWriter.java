package com.jsp.client.batch;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.jsp.client.mapper.AttributeMapper;
import com.jsp.client.service.ProductTypeCache;
import com.jsp.client.service.ProductTypeService;
import com.kibocommerce.sdk.catalogadministration.api.ProductTypesApi;
import com.kibocommerce.sdk.catalogadministration.models.AttributeInProductType;
import com.kibocommerce.sdk.catalogadministration.models.ProductType;
import com.kibocommerce.sdk.common.ApiException;
@Component
public class ProductTypeWriter implements ItemWriter<ProductType> {

    private final ProductTypeService service;
    private final ProductTypeCache cache;
  
    public ProductTypeWriter(ProductTypeService service, ProductTypeCache cache) {
        this.service = service;
        this.cache = cache;
      
    }

    @Override
    public void write(List<? extends ProductType> items) throws Exception {
        for (ProductType type : items) {
            service.createIfNotExists(type);

            Integer productTypeId = cache.get(type.getName().trim().toLowerCase());

            if (productTypeId != null) {
                service.attachAttributeToProductType(productTypeId, "size9");
            } else {
                System.out.println("ProductType ID not found in cache for: " + type.getName());
            }
        }
    }
}