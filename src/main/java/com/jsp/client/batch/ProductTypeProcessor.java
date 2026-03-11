package com.jsp.client.batch;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.jsp.client.dto.MasterProductCsvDTO;
import com.jsp.client.mapper.ProductTypeMapper;
import com.kibocommerce.sdk.catalogadministration.models.ProductType;


@Component
public class ProductTypeProcessor implements ItemProcessor<MasterProductCsvDTO, ProductType> {

    private final ProductTypeMapper mapper;

    public ProductTypeProcessor(ProductTypeMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ProductType process(MasterProductCsvDTO item) {

        if (item.getProductTypeName() == null ||
            item.getProductTypeName().trim().isEmpty()) {
            return null;
        }

        return mapper.map(item);
    }
}