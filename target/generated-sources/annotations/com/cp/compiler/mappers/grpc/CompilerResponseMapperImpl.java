package com.cp.compiler.mappers.grpc;

import com.cp.compiler.contract.CompilerProto;
import com.cp.compiler.contract.RemoteCodeCompilerExecutionResponse;
import com.cp.compiler.contract.RemoteCodeCompilerResponse;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-16T22:58:40+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Red Hat, Inc.)"
)
@Component
public class CompilerResponseMapperImpl implements CompilerResponseMapper {

    @Override
    public CompilerProto.RemoteCodeCompilerResponse ToCompilerResponseProto(RemoteCodeCompilerResponse response) {
        if ( response == null ) {
            return null;
        }

        CompilerProto.RemoteCodeCompilerResponse.Builder remoteCodeCompilerResponse = CompilerProto.RemoteCodeCompilerResponse.newBuilder();

        remoteCodeCompilerResponse.setExecution( toCompilerExecutionResponse( response.getExecution() ) );
        remoteCodeCompilerResponse.setError( CompilerResponseMapper.toError( response.getError() ) );

        return remoteCodeCompilerResponse.build();
    }

    @Override
    public CompilerProto.RemoteCodeCompilerExecutionResponse toCompilerExecutionResponse(RemoteCodeCompilerExecutionResponse executionResponse) {
        if ( executionResponse == null ) {
            return null;
        }

        CompilerProto.RemoteCodeCompilerExecutionResponse.Builder remoteCodeCompilerExecutionResponse = CompilerProto.RemoteCodeCompilerExecutionResponse.newBuilder();

        if ( remoteCodeCompilerExecutionResponse.getMutableTestCasesResult() != null ) {
            Map<String, CompilerProto.RemoteCodeCompilerExecutionResponse.TestCaseResult> map = CompilerResponseMapper.toTestCasesResult( executionResponse.getTestCasesResult() );
            if ( map != null ) {
                remoteCodeCompilerExecutionResponse.getMutableTestCasesResult().putAll( map );
            }
        }
        remoteCodeCompilerExecutionResponse.setLanguage( CompilerResponseMapper.toLanguages( executionResponse.getLanguage() ) );
        remoteCodeCompilerExecutionResponse.setVerdict( executionResponse.getVerdict() );
        remoteCodeCompilerExecutionResponse.setStatusCode( executionResponse.getStatusCode() );
        remoteCodeCompilerExecutionResponse.setError( executionResponse.getError() );
        remoteCodeCompilerExecutionResponse.setCompilationDuration( executionResponse.getCompilationDuration() );
        remoteCodeCompilerExecutionResponse.setTimeLimit( executionResponse.getTimeLimit() );
        remoteCodeCompilerExecutionResponse.setMemoryLimit( executionResponse.getMemoryLimit() );

        return remoteCodeCompilerExecutionResponse.build();
    }
}
