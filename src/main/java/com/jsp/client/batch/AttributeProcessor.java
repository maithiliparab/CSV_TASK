package com.jsp.client.batch;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.jsp.client.dto.MasterProductCsvDTO;
import com.jsp.client.mapper.AttributeMapper;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttribute;

@Component
public class AttributeProcessor implements ItemProcessor<MasterProductCsvDTO, CatalogAdminsAttribute> {

    private final AttributeMapper mapper;

    public AttributeProcessor(AttributeMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CatalogAdminsAttribute process(MasterProductCsvDTO item) {

        if (!"Create".equalsIgnoreCase(item.getOperation()))
            return null;

        if (item.getAttributeCode() == null || item.getAttributeCode().isEmpty())
            return null;

        return mapper.map(item);
    }
}