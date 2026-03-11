package com.jsp.client.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.jsp.client.dto.MasterProductCsvDTO;
import com.jsp.client.mapper.ProductMapper;
import com.jsp.client.service.ParentProductTypeHolder;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;

@Component
public class ProductProcessor implements ItemProcessor<MasterProductCsvDTO, ProductBatchWrapper> {

    private final ProductMapper mapper;

    public ProductProcessor(ProductMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ProductBatchWrapper process(MasterProductCsvDTO item) {

        String operation = item.getOperation();

        if (operation == null) {
            throw new RuntimeException(
                "Operation missing for productCode=" + item.getProductCode());
        }

        // DELETE
        if ("Delete".equalsIgnoreCase(operation)) {

            CatalogAdminsProduct product = new CatalogAdminsProduct();
            product.setProductCode(item.getProductCode());

            return new ProductBatchWrapper(operation, product);
        }

        // UPDATE
        if ("Update".equalsIgnoreCase(operation)) {

            String rowType = deriveRowType(item);
            item.setRowType(rowType);

            CatalogAdminsProduct product = null;

            switch (rowType) {
                case "STANDARD":
                    product = mapper.mapStandard(item);
                    break;

                case "CONFIGURABLE":
                    product = mapper.mapConfigurableParent(item);
                    break;

                case "VARIANT":
                    product = mapper.mapVariant(item);
                    break;

                default:
                    return null;
            }

            return new ProductBatchWrapper(operation, product);
        }

        // CREATE
        if ("Create".equalsIgnoreCase(operation)) {

            String rowType = deriveRowType(item);
            item.setRowType(rowType);

            if ("VARIANT".equals(rowType)
                    && (item.getProductTypeName() == null
                    || item.getProductTypeName().isBlank())) {

                item.setProductTypeName(
                    ParentProductTypeHolder.get(item.getParentProductCode())
                );
            }

            if ("CONFIGURABLE".equals(rowType)) {
                ParentProductTypeHolder.put(
                    item.getProductCode(),
                    item.getProductTypeName()
                );
            }

            CatalogAdminsProduct product = null;

            switch (rowType) {
                case "STANDARD":
                    product = mapper.mapStandard(item);
                    break;

                case "CONFIGURABLE":
                    product = mapper.mapConfigurableParent(item);
                    break;

                case "VARIANT":
                    product = mapper.mapVariant(item);
                    break;

                default:
                    return null;
            }

            return new ProductBatchWrapper(operation, product);
        }

        return null;
    }

    String deriveRowType(MasterProductCsvDTO item) {

        if (item.getProductUsage() == null || item.getProductUsage().isBlank()) {
            throw new RuntimeException(
                "ProductUsage is missing for productCode=" + item.getProductCode()
            );
        }

        String usage = item.getProductUsage().trim();

        if ("Standard".equalsIgnoreCase(usage)) {
            return "STANDARD";
        }

        if ("Configurable".equalsIgnoreCase(usage)
                && (item.getParentProductCode() == null
                || item.getParentProductCode().isBlank())) {
            return "CONFIGURABLE";
        }

        if ("Configurable".equalsIgnoreCase(usage)
                && item.getParentProductCode() != null
                && !item.getParentProductCode().isBlank()) {
            return "VARIANT";
        }

        throw new RuntimeException(
            "Invalid ProductUsage '" + usage +
            "' for productCode=" + item.getProductCode()
        );
    }
}