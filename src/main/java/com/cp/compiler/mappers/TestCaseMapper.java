package com.cp.compiler.mappers;

import com.cp.compiler.consts.WellKnownFiles;
import com.cp.compiler.contract.InMemoryMultipartFile;
import com.cp.compiler.contract.testcases.TestCase;
import com.cp.compiler.models.testcases.TransformedTestCase;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Test case mapper.
 *
 * @author Zakaria Maaraki
 */
public abstract class TestCaseMapper {

    private TestCaseMapper() {}

    /**
     * Converts a testCase into an instance of ConvertedTestCase
     */
    public static TransformedTestCase toConvertedTestCase(TestCase testCase, String testCaseId) {
        var convertedTestCase = new TransformedTestCase();
        convertedTestCase.setTestCaseId(testCaseId);
        convertedTestCase.setInputFile(getInput(testCase.getInput(), testCaseId));
        convertedTestCase.setExpectedOutput(testCase.getExpectedOutput());
        return convertedTestCase;
    }

    /**
     * Converts a dictionary of TestCases into a list of ConvertedTestCases
     */
    public static List<TransformedTestCase> toConvertedTestCases(Map<String, TestCase> testCases) {
        List<TransformedTestCase> convertedTestCases = new ArrayList<>();
        for (String id : testCases.keySet()) {
            convertedTestCases.add(toConvertedTestCase(testCases.get(id), id));
        }
        return convertedTestCases;
    }

    private static MultipartFile getInput(String input, String id) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        String fileName = id + "-" + WellKnownFiles.INPUT_FILE_NAME;
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);

        // ✅ spring-test 없이도 MultipartFile 생성
        return new InMemoryMultipartFile(
                fileName,        // name
                fileName,        // originalFilename
                "text/plain",    // contentType
                bytes
        );
    }
}
