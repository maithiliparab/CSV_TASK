
package com.jsp.client.batch;

import java.util.*;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.jsp.client.mapper.ProductMapper;
import com.jsp.client.service.ProductService;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductOption;
import com.kibocommerce.sdk.catalogadministration.models.ProductVariationOption;
import com.kibocommerce.sdk.common.ApiException;

@Component
public class ProductWriter implements ItemWriter<ProductBatchWrapper> {

    private final ProductService productService;
    private final ProductMapper mapper;

    public ProductWriter(ProductService productService, ProductMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    private int apiHitCount = 0;

    @Override
    public void write(List<? extends ProductBatchWrapper> items) throws ApiException {

        Map<String, List<CatalogAdminsProduct>> variantGroup = new HashMap<>();

        // 1️⃣ Group variants
        for (ProductBatchWrapper wrapper : items) {

            CatalogAdminsProduct product = wrapper.getProduct();

            if (product == null)
                continue;

            if (Boolean.TRUE.equals(product.getIsVariation())) {
                variantGroup
                        .computeIfAbsent(product.getBaseProductCode(), k -> new ArrayList<>())
                        .add(product);
            }
        }

        // 2️⃣ STANDARD CREATE
        for (ProductBatchWrapper wrapper : items) {

            String operation = wrapper.getOperation();
            CatalogAdminsProduct product = wrapper.getProduct();

            if (!"Create".equalsIgnoreCase(operation))
                continue;

            if (!Boolean.TRUE.equals(product.getIsVariation())
                    && !Boolean.TRUE.equals(product.getHasConfigurableOptions())) {

                productService.createProductIfNotExist(product);
                System.out.println("STANDARD CREATED: " + product.getProductCode());
            }
        }

        // 3️⃣ CONFIGURABLE PARENTS
        for (ProductBatchWrapper wrapper : items) {

            String operation = wrapper.getOperation();
            CatalogAdminsProduct parent = wrapper.getProduct();

            if (!"Create".equalsIgnoreCase(operation) && 
                    !"Update".equalsIgnoreCase(operation))
                    continue;

            if (Boolean.TRUE.equals(parent.getHasConfigurableOptions())) {

                List<CatalogAdminsProduct> variants = variantGroup.get(parent.getProductCode());

                if (variants == null || variants.isEmpty())
                    continue;

                List<CatalogAdminsProductOption> options = mapper.buildOptions(variants);
                List<ProductVariationOption> variationOptions = mapper.buildVariationOptions(variants);

                parent.setOptions(options);
                parent.setVariationOptions(variationOptions);

                long start = System.currentTimeMillis();

                productService.createProductIfNotExist(parent);

                productService.enableAllVariations(parent.getProductCode());

                long end = System.currentTimeMillis();

                apiHitCount++;

                System.out.println(
                        "CONFIGURABLE CREATED: " + parent.getProductCode() +
                        " | Time: " + (end - start) +
                        " ms | API Hits: " + apiHitCount);
            }
        }

        // 4️⃣ UPDATE & DELETE
        for (ProductBatchWrapper wrapper : items) {

            String operation = wrapper.getOperation();
            CatalogAdminsProduct product = wrapper.getProduct();

            if ("Delete".equalsIgnoreCase(operation)) {

                productService.deleteProduct(product.getProductCode());

                System.out.println("DELETED: " + product.getProductCode());
                continue;
            }

            if ("Update".equalsIgnoreCase(operation)) {

                productService.updateProduct(product);

                System.out.println("UPDATED: " + product.getProductCode());
                continue;
            }
        }
    }
}





