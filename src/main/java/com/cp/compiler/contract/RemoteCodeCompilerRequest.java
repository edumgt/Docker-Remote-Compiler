package com.cp.compiler.contract;

import com.cp.compiler.mappers.TestCaseMapper;
import com.cp.compiler.models.testcases.TransformedTestCase;
import com.cp.compiler.contract.testcases.TestCase;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The type Request.
 *
 * @author Zakaria Maaraki
 */
@Getter
@Setter // used by mapstruct
@NoArgsConstructor(force = true)
@EqualsAndHashCode
@AllArgsConstructor
public class RemoteCodeCompilerRequest {

    @ApiModelProperty(notes = "The sourcecode")
    @NonNull
    @JsonProperty("sourcecode")
    protected String sourcecode;

    @ApiModelProperty(notes = "The programming language")
    @NonNull
    @JsonProperty("language")
    protected Language language;

    @ApiModelProperty(notes = "The time limit in sec")
    @NonNull
    @JsonProperty("timeLimit")
    protected int timeLimit;

    @ApiModelProperty(notes = "The memory limit")
    @NonNull
    @JsonProperty("memoryLimit")
    protected int memoryLimit;

    @ApiModelProperty(notes = "The test cases")
    @NonNull
    @JsonProperty("testCases")
    protected LinkedHashMap<String, TestCase> testCases; // Note: test cases should be given in order

    /**
     * sourcecode 문자열을 MultipartFile로 변환 (spring-test 의존 없이)
     */
    public MultipartFile getSourcecodeFile() {
        String fileName = language.getDefaultSourcecodeFileName();
        byte[] bytes = (sourcecode == null ? "" : sourcecode).getBytes(StandardCharsets.UTF_8);

        return new InMemoryMultipartFile(
                "sourcecodeFile",   // form field name
                fileName,           // original filename
                "text/plain",       // content-type (원하면 language에 맞춰 변경 가능)
                bytes
        );
    }

    /**
     * Gets test cases.
     */
    public List<TransformedTestCase> getConvertedTestCases() throws IOException {
        return TestCaseMapper.toConvertedTestCases(testCases);
    }
}
