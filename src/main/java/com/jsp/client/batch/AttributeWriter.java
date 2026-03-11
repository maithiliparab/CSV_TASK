package com.jsp.client.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.jsp.client.service.AttributeService;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttribute;

@Component
public class AttributeWriter implements ItemWriter<CatalogAdminsAttribute> {

	
    private final AttributeService service;

    public AttributeWriter(AttributeService service) {
        this.service = service;
    }

    
    @Override
    public void write(List<? extends CatalogAdminsAttribute> items) {

        for (CatalogAdminsAttribute attribute : items) {
            service.createIfNotExists(attribute);
        }
    }
}