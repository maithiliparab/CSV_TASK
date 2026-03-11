package com.jsp.client.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.jsp.client.dto.MasterProductCsvDTO;

@Configuration

public class ProductCsvReader {

	@Bean
	@StepScope
	public FlatFileItemReader<MasterProductCsvDTO> reader(@Value("#{jobParameters['filePath']}") String filePath) {

		FlatFileItemReader<MasterProductCsvDTO> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource(filePath));
		reader.setLinesToSkip(1);
		 // reader.setResource(new org.springframework.core.io.FileSystemResource(filePath));
		//reader.setResource(new ClassPathResource("PRODUCT.csv"));
		//reader.setLinesToSkip(1);
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(",");
		tokenizer.setNames("operation", "attributeLabel", "administrationName", "attributeCode", "inputType",
				"dataType", "isOption", "isProperty", "values", "SearchableInStorefront", "productTypeName",
				"supportedUsageTypes", "options","productCode", "parentProductCode", "productName", "UPC",
				"productShortDescription", "productFullDescription", "localeCode", "isActive", "price", "salePrice",
				"isoCurrencyCode", "productUsage", "masterCatalogId", "catalogId", "categoryId", "manageStock",
				"outOfStockBehavior", "size_1", "color_1", "cost", "packageWidth", "packageLength", "packageHeight",
				"packageWeight", "heightUnit", "widthUnit", "lengthUnit", "weightUnit", "hasConfigurableOptions",
				"hasStandAloneOptions", "fulfillmentTypes", "rating", "seoUrl", "metaTitle", "metaDescription",
				"publishedState"

		);

		BeanWrapperFieldSetMapper<MasterProductCsvDTO> mapper = new BeanWrapperFieldSetMapper<>();
		mapper.setTargetType(MasterProductCsvDTO.class);
		DefaultLineMapper<MasterProductCsvDTO> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(mapper);

		reader.setLineMapper(lineMapper);

		return reader;
	}
}