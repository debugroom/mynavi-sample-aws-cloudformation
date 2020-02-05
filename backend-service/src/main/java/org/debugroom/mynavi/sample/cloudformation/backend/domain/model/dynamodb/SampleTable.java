package org.debugroom.mynavi.sample.cloudformation.backend.domain.model.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;

import org.debugroom.mynavi.sample.cloudformation.common.model.SampleResource;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@DynamoDBTable(tableName = "sample-table")
public class SampleTable implements SampleResource {

    @Id
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private SampleTableKey sampleTableKey;
    @DynamoDBHashKey
    private String samplePartitionKey;
    @DynamoDBRangeKey
    private String sampleSortKey;
    @DynamoDBAttribute
    private String sampleText;

}
