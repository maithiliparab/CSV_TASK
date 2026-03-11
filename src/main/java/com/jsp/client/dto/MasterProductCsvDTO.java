package com.jsp.client.dto;

import java.util.Map;

import lombok.Data;

@Data
public class MasterProductCsvDTO {

     private String rowType;

private Map<String, String> variantAttributes;
	
	
	private String operation;
	private String attributeLabel;
	private String administrationName;
	private String attributeCode;
	private String inputType;
	private String dataType;
	private String isOption;
	private String isProperty;
	private String values;
	private String SearchableInStorefront;

	private String productTypeName;
	private String supportedUsageTypes;
    private String options;


	
	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}
	private String productCode;
	private String parentProductCode;
	private String productName;
	private String UPC;
	private String productShortDescription;
	private String productFullDescription;
	private String localeCode;
	private String isActive;
	private String price;
	private String salePrice;
	private String isoCurrencyCode;
	private String productUsage;

	private String masterCatalogId;
	private String catalogId;
	private String categoryId;


	private String manageStock;
	private String outOfStockBehavior;
	private String size_1;
	private String color_1;
	private String cost;

	private String packageWidth;
	private String packageLength;
	private String packageHeight;
	private String packageWeight;
	private String heightUnit;
	private String widthUnit;
	private String lengthUnit;
	private String weightUnit;

	private String hasConfigurableOptions;
	private String hasStandAloneOptions;
	private String fulfillmentTypes;
	private String rating;
	private String seoUrl;
	private String metaTitle;
	private String metaDescription;
	private String publishedState;
	

public Map<String, String> getVariantAttributes() {
    return variantAttributes;
}

public void setVariantAttributes(Map<String, String> variantAttributes) {
    this.variantAttributes = variantAttributes;
}
	
	public String getRowType() {
		return rowType;
	}
	public void setRowType(String rowType) {
		this.rowType = rowType;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getAttributeLabel() {
		return attributeLabel;
	}
	public void setAttributeLabel(String attributeLabel) {
		this.attributeLabel = attributeLabel;
	}
	public String getAdministrationName() {
		return administrationName;
	}
	public void setAdministrationName(String administrationName) {
		this.administrationName = administrationName;
	}
	public String getAttributeCode() {
		return attributeCode;
	}
	public void setAttributeCode(String attributeCode) {
		this.attributeCode = attributeCode;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getIsOption() {
		return isOption;
	}
	public void setIsOption(String isOption) {
		this.isOption = isOption;
	}
	public String getIsProperty() {
		return isProperty;
	}
	public void setIsProperty(String isProperty) {
		this.isProperty = isProperty;
	}
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}
	public String getSearchableInStorefront() {
		return SearchableInStorefront;
	}
	public void setSearchableInStorefront(String searchableInStorefront) {
		SearchableInStorefront = searchableInStorefront;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public String getSupportedUsageTypes() {
		return supportedUsageTypes;
	}
	public void setSupportedUsageTypes(String supportedUsageTypes) {
		this.supportedUsageTypes = supportedUsageTypes;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getParentProductCode() {
		return parentProductCode;
	}
	public void setParentProductCode(String parentProductCode) {
		this.parentProductCode = parentProductCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUPC() {
		return UPC;
	}
	public void setUPC(String uPC) {
		UPC = uPC;
	}
	public String getProductShortDescription() {
		return productShortDescription;
	}
	public void setProductShortDescription(String productShortDescription) {
		this.productShortDescription = productShortDescription;
	}
	public String getProductFullDescription() {
		return productFullDescription;
	}
	public void setProductFullDescription(String productFullDescription) {
		this.productFullDescription = productFullDescription;
	}
	public String getLocaleCode() {
		return localeCode;
	}
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getIsoCurrencyCode() {
		return isoCurrencyCode;
	}
	public void setIsoCurrencyCode(String isoCurrencyCode) {
		this.isoCurrencyCode = isoCurrencyCode;
	}
	public String getProductUsage() {
		return productUsage;
	}
	public void setProductUsage(String productUsage) {
		this.productUsage = productUsage;
	}
	public String getMasterCatalogId() {
		return masterCatalogId;
	}
	public void setMasterCatalogId(String masterCatalogId) {
		this.masterCatalogId = masterCatalogId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getManageStock() {
		return manageStock;
	}
	public void setManageStock(String manageStock) {
		this.manageStock = manageStock;
	}
	public String getOutOfStockBehavior() {
		return outOfStockBehavior;
	}
	public void setOutOfStockBehavior(String outOfStockBehavior) {
		this.outOfStockBehavior = outOfStockBehavior;
	}
	public String getSize_1() {
		return size_1;
	}
	public void setSize_1(String size_1) {
		this.size_1 = size_1;
	}
	public String getColor_1() {
		return color_1;
	}
	public void setColor_1(String color_1) {
		this.color_1 = color_1;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getPackageWidth() {
		return packageWidth;
	}
	public void setPackageWidth(String packageWidth) {
		this.packageWidth = packageWidth;
	}
	public String getPackageLength() {
		return packageLength;
	}
	public void setPackageLength(String packageLength) {
		this.packageLength = packageLength;
	}
	public String getPackageHeight() {
		return packageHeight;
	}
	public void setPackageHeight(String packageHeight) {
		this.packageHeight = packageHeight;
	}
	public String getPackageWeight() {
		return packageWeight;
	}
	public void setPackageWeight(String packageWeight) {
		this.packageWeight = packageWeight;
	}
	public String getHeightUnit() {
		return heightUnit;
	}
	public void setHeightUnit(String heightUnit) {
		this.heightUnit = heightUnit;
	}
	public String getWidthUnit() {
		return widthUnit;
	}
	public void setWidthUnit(String widthUnit) {
		this.widthUnit = widthUnit;
	}
	public String getLengthUnit() {
		return lengthUnit;
	}
	public void setLengthUnit(String lengthUnit) {
		this.lengthUnit = lengthUnit;
	}
	public String getWeightUnit() {
		return weightUnit;
	}
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	public String getHasConfigurableOptions() {
		return hasConfigurableOptions;
	}
	public void setHasConfigurableOptions(String hasConfigurableOptions) {
		this.hasConfigurableOptions = hasConfigurableOptions;
	}
	public String getHasStandAloneOptions() {
		return hasStandAloneOptions;
	}
	public void setHasStandAloneOptions(String hasStandAloneOptions) {
		this.hasStandAloneOptions = hasStandAloneOptions;
	}
	public String getFulfillmentTypes() {
		return fulfillmentTypes;
	}
	public void setFulfillmentTypes(String fulfillmentTypes) {
		this.fulfillmentTypes = fulfillmentTypes;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getSeoUrl() {
		return seoUrl;
	}
	public void setSeoUrl(String seoUrl) {
		this.seoUrl = seoUrl;
	}
	public String getMetaTitle() {
		return metaTitle;
	}
	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}
	public String getMetaDescription() {
		return metaDescription;
	}
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}
	public String getPublishedState() {
		return publishedState;
	}
	public void setPublishedState(String publishedState) {
		this.publishedState = publishedState;
	}

}