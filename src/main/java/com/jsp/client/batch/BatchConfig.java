package com.jsp.client.batch;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jsp.client.csvmover.FileMove;
import com.jsp.client.csvmover.FileWatch;
import com.jsp.client.dto.MasterProductCsvDTO;

import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsAttribute;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;
import com.kibocommerce.sdk.catalogadministration.models.ProductType;
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
     private final FileMove fileMove;
    
    public BatchConfig(JobBuilderFactory jobBuilderFactory,
                       StepBuilderFactory stepBuilderFactory,
                       FileMove fileMove) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        
        this.fileMove=fileMove;
    }

    @Bean
    public Step attributeStep(ProductCsvReader reader,
                              AttributeProcessor processor,
                              AttributeWriter writer) {

        return stepBuilderFactory.get("attributeStep")
                .<MasterProductCsvDTO, CatalogAdminsAttribute>chunk(100)
                .reader(reader.reader(null)
                		 )
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step productTypeStep(ProductCsvReader reader,
                                ProductTypeProcessor processor,
                                ProductTypeWriter writer) {

        return stepBuilderFactory.get("productTypeStep")
                .<MasterProductCsvDTO, ProductType>chunk(100)
                .reader(reader.reader(null)
                		 )
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step productStep(ProductCsvReader reader,
                            ItemProcessor<? super MasterProductCsvDTO, ? extends ProductBatchWrapper> processor,
                            ItemWriter<? super ProductBatchWrapper> writer) {

        return stepBuilderFactory.get("productStep")
                .<MasterProductCsvDTO, ProductBatchWrapper>chunk(100)
                .reader(reader.reader(null))
                .processor(processor)
                .writer(writer)
                .listener(fileMove)
                .build();
    }
 
    @Bean
    public Job catalogJob(
            @Qualifier("attributeStep") Step attributeStep,
            @Qualifier("productTypeStep") Step productTypeStep,
            @Qualifier("productStep") Step productStep) {

        return jobBuilderFactory.get("catalogJob")
                .start(attributeStep)
                .next(productTypeStep)
                .next(productStep)
                
                .build();
    }
}