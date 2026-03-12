package com.jsp.client.mapper;

import java.util.*;
import org.springframework.stereotype.Component;

import com.jsp.client.dto.MasterProductCsvDTO;
import com.jsp.client.service.ProductTypeCache;

import com.kibocommerce.sdk.catalogadministration.models.*;

@Component
public class ProductMapper {

    private final ProductTypeCache productTypeCache;

    public ProductMapper(ProductTypeCache productTypeCache) {
        this.productTypeCache = productTypeCache;
    }

    // 1️⃣ STANDARD PRODUCT
    public CatalogAdminsProduct mapStandard(MasterProductCsvDTO csv) {

        CatalogAdminsProduct product = baseProduct(csv);

        product.setProductUsage("Standard");
        product.setIsVariation(false);
        product.setBaseProductCode(null);
        product.setHasConfigurableOptions(false);

        return product;
    }

    // 2️⃣ CONFIGURABLE PARENT
    public CatalogAdminsProduct mapConfigurableParent(MasterProductCsvDTO csv) {

        CatalogAdminsProduct product = baseProduct(csv);

        product.setProductUsage("Configurable");
        product.setIsVariation(false);
        product.setHasConfigurableOptions(true);
        product.setBaseProductCode(null);

        List<CatalogAdminsProductOption> options = new ArrayList<>();

        if (csv.getAttributeCode() != null && !csv.getAttributeCode().isBlank()) {

            String[] attrCodes = csv.getAttributeCode().split("\\|");

            String[] valuesArr =
                    csv.getValues() != null
                            ? csv.getValues().split("\\|")
                            : new String[attrCodes.length];

            for (int i = 0; i < attrCodes.length; i++) {

                CatalogAdminsProductOption option = new CatalogAdminsProductOption();

                option.setAttributeFQN("tenant~" + attrCodes[i].trim());

                List<CatalogAdminsProductOptionValue> optionValues = new ArrayList<>();

                if (i < valuesArr.length && valuesArr[i] != null && !valuesArr[i].isBlank()) {

                    for (String val : valuesArr[i].split(",")) {

                        CatalogAdminsProductOptionValue v = new CatalogAdminsProductOptionValue();
                        v.setValue(val.trim());

                        optionValues.add(v);
                    }
                }

                option.setValues(optionValues);
                options.add(option);
            }
        }

        product.setOptions(options);

        return product;
    }

    // 3️⃣ VARIANT
    public CatalogAdminsProduct mapVariant(MasterProductCsvDTO csv) {

        CatalogAdminsProduct product = baseProduct(csv);

        product.setProductUsage("Standard");
        product.setIsVariation(true);
        product.setBaseProductCode(csv.getParentProductCode());
        product.setHasConfigurableOptions(false);

        product.setOptions(null);

        return product;
    }

    // BASE PRODUCT (COMMON DATA)
    private CatalogAdminsProduct baseProduct(MasterProductCsvDTO csv) {

        CatalogAdminsProduct product = new CatalogAdminsProduct();

        product.setProductCode(csv.getProductCode());
        product.setUpc(csv.getUPC());
        product.setMasterCatalogId(parseInt(csv.getMasterCatalogId()));

        Integer productTypeId = productTypeCache.get(csv.getProductTypeName());

        if (productTypeId == null) {
            throw new RuntimeException(
                    "ProductTypeId not found for productTypeName="
                            + csv.getProductTypeName()
                            + " productCode="
                            + csv.getProductCode());
        }

        product.setProductTypeId(productTypeId);

        // CONTENT
        ProductLocalizedContent content = new ProductLocalizedContent();

        content.setLocaleCode(csv.getLocaleCode());
        content.setProductName(csv.getProductName());
        content.setProductShortDescription(csv.getProductShortDescription());
        content.setProductFullDescription(csv.getProductFullDescription());

        product.setContent(content);

        // PRICE
        CatalogAdminsProductPrice price = new CatalogAdminsProductPrice();

        price.setPrice(parseDouble(csv.getPrice()));
        price.setSalePrice(parseDouble(csv.getSalePrice()));
        price.setIsoCurrencyCode(csv.getIsoCurrencyCode());

        product.setPrice(price);

        // INVENTORY
        CatalogAdminsProductInventoryInfo inventory = new CatalogAdminsProductInventoryInfo();

        inventory.setManageStock(parseBool(csv.getManageStock()));
        inventory.setOutOfStockBehavior(csv.getOutOfStockBehavior());

        product.setInventoryInfo(inventory);

        // FULFILLMENT
        if (csv.getFulfillmentTypes() == null || csv.getFulfillmentTypes().isBlank()) {
            throw new RuntimeException(
                    "FulfillmentTypes missing for productCode=" + csv.getProductCode());
        }

        product.setFulfillmentTypesSupported(
                List.of(csv.getFulfillmentTypes().split("\\|")));

        // MEASUREMENTS
        product.setPackageWeight(parseMeasurement(csv.getPackageWeight(), csv.getWeightUnit()));
        product.setPackageLength(parseMeasurement(csv.getPackageLength(), csv.getLengthUnit()));
        product.setPackageHeight(parseMeasurement(csv.getPackageHeight(), csv.getHeightUnit()));
        product.setPackageWidth(parseMeasurement(csv.getPackageWidth(), csv.getWidthUnit()));

        // BUILD VARIANT ATTRIBUTE MAP
        if (notEmpty(csv.getParentProductCode())) {

            Map<String, String> variantMap = new LinkedHashMap<>();

            if (notEmpty(csv.getSize_1())) {
                variantMap.put("size_1", csv.getSize_1().trim());
            }

            if (notEmpty(csv.getColor_1())) {
                variantMap.put("color", csv.getColor_1().trim());
            }

            if (!variantMap.isEmpty()) {
                csv.setVariantAttributes(variantMap);
            }
        }

        boolean isVariant = notEmpty(csv.getParentProductCode());

        // VARIATION OPTIONS
        if (isVariant
                && csv.getVariantAttributes() != null
                && !csv.getVariantAttributes().isEmpty()) {

            List<ProductVariationOption> variationOptions = new ArrayList<>();

            for (Map.Entry<String, String> entry : csv.getVariantAttributes().entrySet()) {

                ProductVariationOption option = new ProductVariationOption();

                option.setAttributeFQN("tenant~" + entry.getKey());
                option.setValue(entry.getValue());

                variationOptions.add(option);
            }

            product.setVariationOptions(variationOptions);
        }
        
     // CUSTOM PROPERTY : RATING
        List<CatalogAdminsProductProperty> properties = new ArrayList<>();

        if (csv.getRating() != null && !csv.getRating().isBlank()) {

            CatalogAdminsProductProperty ratingProperty = new CatalogAdminsProductProperty();
            ratingProperty.setAttributeFQN("tenant~rating");

            CatalogAdminsProductPropertyValue value = new CatalogAdminsProductPropertyValue();
            value.setValue(csv.getRating());

            ratingProperty.setValues(List.of(value));

            properties.add(ratingProperty);
        }

        product.setProperties(properties);
        
     // EXTRAS
        List<ProductExtra> extras = new ArrayList<>();

        ProductExtra extra = new ProductExtra();
        extra.setAttributeFQN("tenant~giftwrap"); // attribute created in product type

        ProductExtraValue value = new ProductExtraValue();
        value.setValue("10");

        extra.setValues(List.of(value));

        extras.add(extra);

        product.setExtras(extras);


        return product;
    }

    // OPTION BUILDER
    public List<CatalogAdminsProductOption> buildOptions(List<CatalogAdminsProduct> variants) {

        Map<String, Set<String>> optionMap = new LinkedHashMap<>();

        for (CatalogAdminsProduct variant : variants) {

            if (variant.getVariationOptions() == null) continue;

            for (ProductVariationOption v : variant.getVariationOptions()) {

                optionMap
                        .computeIfAbsent(v.getAttributeFQN(), k -> new LinkedHashSet<>())
                        .add(String.valueOf(v.getValue()));
            }
        }

        List<CatalogAdminsProductOption> options = new ArrayList<>();

        optionMap.forEach((attr, values) -> {

            CatalogAdminsProductOption option = new CatalogAdminsProductOption();

            option.setAttributeFQN(attr);

            List<CatalogAdminsProductOptionValue> vals = new ArrayList<>();

            for (String v : values) {

                CatalogAdminsProductOptionValue val =
                        new CatalogAdminsProductOptionValue();

                val.setValue(v);

                vals.add(val);
            }

            option.setValues(vals);

            options.add(option);
        });

        return options;
    }

    // VARIATION OPTIONS BUILDER
    public List<ProductVariationOption> buildVariationOptions(
            List<CatalogAdminsProduct> variants) {

        List<ProductVariationOption> list = new ArrayList<>();

        for (CatalogAdminsProduct variant : variants) {

            if (variant.getVariationOptions() != null) {
                list.addAll(variant.getVariationOptions());
            }
        }

        return list;
    }

    private Boolean parseBool(String v) {
        return v != null && v.equalsIgnoreCase("true");
    }

    private Integer parseInt(String v) {
        return (v == null || v.isBlank()) ? null : Integer.parseInt(v);
    }

    private Double parseDouble(String v) {
        return (v == null || v.isBlank()) ? null : Double.parseDouble(v);
    }

    private boolean notEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static CommerceRuntimeMeasurement parseMeasurement(
            String value,
            String unit) {

        if (value == null || value.isBlank()) {
            return null;
        }

        CommerceRuntimeMeasurement measurement =
                new CommerceRuntimeMeasurement();

        measurement.setValue(Double.parseDouble(value));

        if (unit != null && !unit.isBlank()) {
            measurement.setUnit(unit);
        }

        return measurement;
    }
}